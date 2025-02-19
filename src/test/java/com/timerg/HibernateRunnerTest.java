package com.timerg;

import com.timerg.entity.PlayerEntity;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {

    @Test
    void checkPostgres() {
        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();
            var player = PlayerEntity.builder()
                    .name("ivan")
                    .build();

            session.persist(player);
            session.getTransaction().commit();
        }
    }
}