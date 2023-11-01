package com.web.todoweb.dao;

import com.web.todoweb.entity.TodoList;
import jakarta.persistence.*;

import java.util.List;

public class TodoListDAO {

    private final EntityManager entityManager;

    public TodoListDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createTodoList(TodoList todoList) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(todoList);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<TodoList> getAllTodoLists() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            TypedQuery<TodoList> query = entityManager.createQuery("SELECT t FROM TodoList t", TodoList.class);
            List<TodoList> todoLists = query.getResultList();
            transaction.commit();
            return todoLists;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public TodoList findTodoListById(Long id) {
        return entityManager.find(TodoList.class, id);
    }

    // Other CRUD methods for TodoList
}
