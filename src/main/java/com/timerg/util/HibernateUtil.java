package com.timerg.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
@UtilityClass
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = getBaseConfiguration();
            configuration.configure();
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Configuration getBaseConfiguration() {
        Configuration configuration = new Configuration();
        // Add properties, mappings, etc.
        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

