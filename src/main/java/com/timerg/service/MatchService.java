package com.timerg.service;

import com.timerg.dto.MatchCreateDto;
import com.timerg.dto.MatchReadDto;
import com.timerg.dto.PlayerReadDto;
import com.timerg.entity.MatchEntity;
import com.timerg.entity.TennisGame;
import com.timerg.mapper.MatchCreateToEntityMapper;
import com.timerg.mapper.MatchEntityToReadMapper;
import com.timerg.mapper.PlayerCreateToEntityMapper;
import com.timerg.mapper.PlayerEntityToReadMapper;
import com.timerg.repository.MatchRepository;
import com.timerg.repository.PlayerRepository;
import com.timerg.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchService {

    private static final MatchService INSTANCE = new MatchService();
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final MatchCreateToEntityMapper matchCreateToEntityMapper = MatchCreateToEntityMapper.getInstance();
    private final MatchEntityToReadMapper matchEntityToReadMapper = MatchEntityToReadMapper.getInstance();

    private static final ConcurrentMap<UUID, TennisGame> currentMatches = new ConcurrentHashMap<>();


    public void save(MatchCreateDto matchCreateDto) {
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {
            MatchRepository matchRepository = new MatchRepository(session);

            // mapping
            MatchEntity matchEntity = matchCreateToEntityMapper.from(matchCreateDto);
            MatchEntity savedMatch = matchRepository.save(matchEntity);
//            MatchReadDto matchReadDto = matchEntityToReadMapper.from(savedMatch);

            tx.commit();

//            return matchReadDto;
        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, save() error: " + e.getMessage());
            throw e;
        }
    }

    public List<MatchReadDto> findAll() {
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {

            MatchRepository matchRepository = new MatchRepository(session);

            List<MatchEntity> matches = matchRepository.findAll();

            List<MatchReadDto> matchesReadDto = matches.stream()
                    .map(matchEntityToReadMapper::from)
                    .toList();

            tx.commit();

            return matchesReadDto;

        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, findAll() error: " + e.getMessage());
            throw e;
        }
    }
    public List<MatchReadDto> findMatchesBySimilarName(String filterByName, int page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            MatchRepository matchRepository = new MatchRepository(session);
            List<MatchEntity> matches = matchRepository.findAll(filterByName, pageSize, offset);
            List<MatchReadDto> matchesReadDto = matches.stream()
                    .map(matchEntityToReadMapper::from)
                    .toList();
            tx.commit();
            return matchesReadDto;
        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, findAll(page, pageSize) error: " + e.getMessage());
            throw e;
        }
    }

    public List<MatchReadDto> findAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            MatchRepository matchRepository = new MatchRepository(session);
            List<MatchEntity> matches = matchRepository.findAll(pageSize, offset);
            List<MatchReadDto> matchesReadDto = matches.stream()
                    .map(matchEntityToReadMapper::from)
                    .toList();
            tx.commit();
            return matchesReadDto;
        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, findAll(page, pageSize) error: " + e.getMessage());
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

    public int countAllMatches() {
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {
            MatchRepository matchRepository = new MatchRepository(session);

            Long countAllMatches = matchRepository.countAll();

            tx.commit();

            return countAllMatches.intValue();

        } catch (Exception e) {
            tx.rollback();
            log.error("MatchService, findLimitedPlayersWithOffset() error: " + e.getMessage());
            throw e;
        }
    }

    public static MatchService getInstance() {
        return INSTANCE;
    }

    public static ConcurrentMap<UUID, TennisGame> getCurrentMatches() {
        return currentMatches;
    }
}
