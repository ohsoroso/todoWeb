package com.web.todoweb.dao;

import com.web.todoweb.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TaskDao {

    private final EntityManager entityManager;

    public TaskDao() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void createTask(Task task) {
        if (entityManager != null && task != null) {
            entityManager.getTransaction().begin();
            entityManager.persist(task);
            entityManager.getTransaction().commit();
        } else {
            // Log or handle the error
            System.err.println("EntityManager or Task is null");
        }
    }

    // More CRUD operations can be added here later
}
