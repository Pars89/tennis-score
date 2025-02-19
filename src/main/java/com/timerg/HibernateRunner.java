package com.timerg;

import com.timerg.entity.PlayerEntity;
import com.timerg.repository.PlayerRepository;
import com.timerg.util.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Proxy;
import java.util.Optional;

@Slf4j
public class HibernateRunner {
    @Transactional
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {

            var session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            PlayerRepository playerRepository = new PlayerRepository(session);

            Optional<PlayerEntity> misha2 = playerRepository.findByName("Misha2");
            playerRepository.delete(misha2.get().getId());

            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Something is wrong: " + e.getMessage());
        }
    }
}
