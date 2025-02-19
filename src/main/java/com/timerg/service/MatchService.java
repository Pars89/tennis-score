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
import java.util.Map;
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

    public static MatchService getInstance() {
        return INSTANCE;
    }

    public static ConcurrentMap<UUID, TennisGame> getCurrentMatches() {
        return currentMatches;
    }
}
