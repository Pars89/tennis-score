package com.timerg.service;

import com.timerg.dto.PlayerCreateDto;
import com.timerg.mapper.PlayerEntityToReadMapper;
import com.timerg.dto.PlayerReadDto;
import com.timerg.mapper.PlayerCreateToEntityMapper;
import com.timerg.repository.PlayerRepository;
import com.timerg.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerService {
    private static final PlayerService INSTANCE = new PlayerService();
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final PlayerCreateToEntityMapper playerCreateToEntityMapper = PlayerCreateToEntityMapper.getInstance();
    private final PlayerEntityToReadMapper playerEntityToReadMapper = PlayerEntityToReadMapper.getInstance();

    public PlayerReadDto save(PlayerCreateDto playerCreateDto) {
        // validation будет в сервлете

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {
            PlayerRepository playerRepository = new PlayerRepository(session);

            // mapping
            var playerEntity = playerCreateToEntityMapper.from(playerCreateDto);
            var savedPlayerEntity = playerRepository.save(playerEntity);
            var playerReadDto = playerEntityToReadMapper.from(savedPlayerEntity);

            tx.commit();

            return playerReadDto;
        } catch (Exception e) {
            tx.rollback();
            log.error("PlayerService, save() error: " + e.getMessage());
            throw e;
        }
    }

    public Optional<PlayerReadDto> findByName(PlayerCreateDto playerCreateDto) {
        // validation будет в сервлете

        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {
            PlayerRepository playerRepository = new PlayerRepository(session);

            // mapping
            var playerEntity = playerCreateToEntityMapper.from(playerCreateDto);
            var foundedPlayerEntity = playerRepository.findByName(playerEntity.getName());

            tx.commit();

            if (foundedPlayerEntity.isPresent()) {
                var playerReadDto = playerEntityToReadMapper.from(foundedPlayerEntity.get());

                return Optional.ofNullable(playerReadDto);
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            tx.rollback();
            log.error("PlayerService, findByName() error: " + e.getMessage());
            throw e;
        }
    }

    public static PlayerService getInstance() {
        return INSTANCE;
    }
}
