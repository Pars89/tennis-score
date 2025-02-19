package com.timerg.repository;

import com.timerg.entity.MatchEntity;
import org.hibernate.Session;

public class MatchRepository extends BaseRepository<Long, MatchEntity>{
    public MatchRepository(Session session) {
        super(session, MatchEntity.class);
    }
}
