package com.web.todoweb.dao;

import com.web.todoweb.entity.Task;
import jakarta.persistence.*;
import java.util.List;

public class TaskDAO {

    private final EntityManager entityManager;

    public TaskDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create a new task
    public void createTask(Task task) {
        entityManager.persist(task);
    }

    public void updateTask(Task task) {
        entityManager.merge(task);
    }


    // Find a task by its ID
    public Task findTaskById(Long id) {
        return entityManager.find(Task.class, id);
    }

    // Get all tasks for a specific TodoList
    public List<Task> getTasksByTodoListId(Long todoListId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Task> query = entityManager.createQuery(
                    "SELECT t FROM Task t WHERE t.todoList.id = :todoListId", Task.class);
            query.setParameter("todoListId", todoListId);
            List<Task> tasks = query.getResultList();
            transaction.commit();
            return tasks;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // Delete a task
    public void deleteTask(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Task task = findTaskById(id);
            if (task != null) {
                entityManager.remove(task);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}