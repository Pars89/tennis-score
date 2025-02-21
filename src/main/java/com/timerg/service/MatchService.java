package com.timerg.service;

import com.timerg.dto.MatchCreateDto;
import com.timerg.dto.MatchReadDto;
import com.timerg.dto.PlayerReadDto;
import com.timerg.entity.MatchEntity;
import com.timerg.entity.StatusGame;
import com.timerg.entity.TennisGame;
import com.timerg.exception.MatchNotFoundException;
import com.timerg.exception.PlayerWinnerNotFoundException;
import com.timerg.mapper.MatchCreateToEntityMapper;
import com.timerg.mapper.MatchEntityToReadMapper;
import com.timerg.repository.MatchRepository;
import com.timerg.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchService {
    private static final MatchService INSTANCE = new MatchService();
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final MatchCreateToEntityMapper matchCreateToEntityMapper = MatchCreateToEntityMapper.getInstance();
    private final MatchEntityToReadMapper matchEntityToReadMapper = MatchEntityToReadMapper.getInstance();
    private static final ConcurrentMap<UUID, TennisGame> currentMatches = new ConcurrentHashMap<>();
    private static final Integer PAGE_SIZE = 3;

    public UUID createNewMatch(PlayerReadDto playerReadDto1, PlayerReadDto playerReadDto2) {
        TennisGame tennisGame = TennisGame.builder()
                .firstPlayer(playerReadDto1)
                .secondPlayer(playerReadDto2)
                .build();
        UUID uuid = UUID.randomUUID();
        currentMatches.put(uuid, tennisGame);

        return uuid;
    }
    public void save(MatchCreateDto matchCreateDto) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            MatchRepository matchRepository = new MatchRepository(session);

            MatchEntity matchEntity = matchCreateToEntityMapper.from(matchCreateDto);
            MatchEntity savedMatch = matchRepository.save(matchEntity);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, save() error: " + e.getMessage());
            throw e;
        }
    }

    public int countPagesBySimilarName(int totalMatches) {
        // Вычисляем общее количество страниц
        int totalPages = (int) Math.ceil((double) totalMatches / PAGE_SIZE);
        return totalPages;
    }

    public List<MatchReadDto> findMatchesBySimilarName(String filterByName, int page) {
        int offset = (page - 1) * PAGE_SIZE;
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            MatchRepository matchRepository = new MatchRepository(session);
            List<MatchEntity> matches = matchRepository.findAll(filterByName, PAGE_SIZE, offset);
            List<MatchReadDto> matchesReadDto = matches.stream()
                    .map(matchEntityToReadMapper::from)
                    .toList();
            tx.commit();
            return matchesReadDto;
        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, findAll(filterByName, page, pageSize) error: " + e.getMessage());
            throw e;
        }
    }

    public int countMatchesBySimilarName(String filterByName) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            MatchRepository matchRepository = new MatchRepository(session);
            Long countAllMatches = matchRepository.countAll(filterByName);

            tx.commit();

            return countAllMatches.intValue();
        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, findLimitedPlayersWithOffset() error: " + e.getMessage());
            throw e;
        }
    }

    public void addPointWinner(String uuidParam, String winnerId) {
        if (!isMatchExisting(uuidParam)) {
            throw new MatchNotFoundException();
        }

        TennisGame tennisGame = currentMatches.get(UUID.fromString(uuidParam));

        if (winnerId.equals(String.valueOf(tennisGame.getFirstPlayer().getId()))) {
            tennisGame.addPointFirstPlayer();
        } else if (winnerId.equals(String.valueOf(tennisGame.getSecondPlayer().getId()))){
            tennisGame.addPointSecondPlayer();
        } else {
            throw new PlayerWinnerNotFoundException();
        }
    }

    public boolean checkWinner(String uuidParam) {
        if (!isMatchExisting(uuidParam)) {
            throw new MatchNotFoundException();
        }

        TennisGame tennisGame = currentMatches.get(UUID.fromString(uuidParam));

        return isGameFinished(tennisGame.getStatusGame());
    }

    private boolean isGameFinished(StatusGame statusGame) {
        return !StatusGame.ONGOING.equals(statusGame);
    }

    public boolean isMatchExisting(String uuid) {
        return currentMatches.containsKey(UUID.fromString(uuid));
    }

    public static MatchService getInstance() {
        return INSTANCE;
    }

    // Потом удалить
    public ConcurrentMap<UUID, TennisGame> getCurrentMatches() {
        return currentMatches;
    }

    public TennisGame getTennisGame(String uuid) {
        return currentMatches.get(UUID.fromString(uuid));
    }
}
