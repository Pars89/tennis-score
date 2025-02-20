package com.timerg.repository;

import com.timerg.entity.PlayerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PlayerRepository extends BaseRepository<Long, PlayerEntity>{
    public PlayerRepository(Session session) {
        super(session, PlayerEntity.class);
    }

    public Optional<PlayerEntity> findByName(String playerName) {
        try {
            return Optional.ofNullable(session.createQuery(
                            "SELECT p FROM PlayerEntity p WHERE p.name = :name", PlayerEntity.class)
                    .setParameter("name", playerName)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<PlayerEntity> findBySimilarName(String playerName) {
        return session.createQuery(
                        "SELECT p FROM PlayerEntity p WHERE p.name LIKE :name", PlayerEntity.class)
                .setParameter("name", "%" + playerName + "%")
                .getResultList();
    }
}
