package com.example.books.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RepositoryOperations<T> {
    private final EntityManagerFactory entityManagerFactory;

    public T add(T entity) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert " + entity.getClass().getName()
                    + ": " + entity, e);
        }
    }

    public List<T> getAll(String query, Class<T> cls) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(query, cls)
                    .getResultList();
        }
    }

    public Optional<T> findById(Long id, Class<T> cls) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            T entity = entityManager.find(cls, id);
            return entity != null ? Optional.of(entity) : Optional.empty();
        }
    }
}
