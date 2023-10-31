package com.web.todoweb.dao;

import com.web.todoweb.entity.TodoList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TodoListDao {

    private final EntityManager entityManager;

    public TodoListDao() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void createTodoList(TodoList todoList) {
        if (entityManager != null && todoList != null) {
            entityManager.getTransaction().begin();
            entityManager.persist(todoList);
            entityManager.getTransaction().commit();
        } else {
            // Log or handle the error
            System.err.println("EntityManager or TodoList is null");
        }
    }

    public TodoList findTodoListById(Long id) {
        return entityManager.find(TodoList.class, id);
    }

    // More CRUD operations can be added here later
}
