package com.example.books.repository;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryOperations<T> {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositoryOperations(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public T add(T entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert " + entity.getClass().getName()
                    + ": " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<T> getAll(String query, Class<T> cls) {
        try (Session session = sessionFactory.openSession()) {
            Query<T> allEntitiesQuery = session.createQuery(query, cls);
            return allEntitiesQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of all " + cls.getName(), e);
        }
    }
}
