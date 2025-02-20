package com.timerg.repository;

import com.timerg.entity.MatchEntity;
import com.timerg.entity.PlayerEntity;
import org.hibernate.Session;

import java.util.List;

public class MatchRepository extends BaseRepository<Long, MatchEntity>{
    public MatchRepository(Session session) {
        super(session, MatchEntity.class);
    }

    public List<MatchEntity> findAll(String playerName, int limit, int offset) {
        List<MatchEntity> entities = session.createQuery(
                "SELECT p FROM MatchEntity p WHERE " +
                        "p.firstPlayerEntity.name LIKE :name OR " +
                        "p.secondPlayerEntity.name LIKE :name",
                        MatchEntity.class)
                .setParameter("name", "%" + playerName + "%")
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        return entities;
    }

    public Long countAll(String playerName) {
        return session.createQuery(
                        "SELECT COUNT(p) FROM MatchEntity p WHERE " +
                                "p.firstPlayerEntity.name LIKE :name OR " +
                                "p.secondPlayerEntity.name LIKE :name",
                        Long.class)
                .setParameter("name", "%" + playerName + "%")
                .getSingleResult();
    }
}
