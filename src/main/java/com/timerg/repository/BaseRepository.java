package com.timerg.repository;


import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<K extends Serializable, E> implements Repository<K, E>{

    protected final Session session;
    private final Class<E> clazz;
    @Override
    public E save(E entity) {
        session.persist(entity);
        return entity;
    }

    @Override
    public void update(E entity) {
        session.merge(entity);
    }

    @Override
    public void delete(K id) {
        findById(id).ifPresent(entity -> {
            session.remove(entity);
            session.flush();
        });
    }

    @Override
    public Optional<E> findById(K id) {
        E entity = session.find(clazz, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<E> findAll() {
        List<E> entities = session.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz)
                .getResultList();
        return entities;
    }
    public List<E> findAll(int limit, int offset) {
        List<E> entities = session.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        return entities;
    }

    public Long countAll() {
        return session.createQuery("SELECT COUNT(e) FROM " + clazz.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }
}
