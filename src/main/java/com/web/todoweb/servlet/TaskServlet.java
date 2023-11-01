package com.web.todoweb.servlet;

import com.web.todoweb.dao.TaskDAO;
import com.web.todoweb.dao.TodoListDAO;
import com.web.todoweb.entity.Task;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void init() throws ServletException {
        // Manually creating EntityManagerFactory
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void destroy() {
        // Close EntityManagerFactory when the application is stopped
        entityManagerFactory.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Long todoListId = Long.parseLong(request.getParameter("listId"));
            TaskDAO taskDAO = new TaskDAO(entityManager);
            TodoListDAO todoListDAO = new TodoListDAO(entityManager);

            List<Task> tasks = taskDAO.getTasksByTodoListId(todoListId);
            String todoListName = todoListDAO.findTodoListById(todoListId).getName();

            request.setAttribute("tasks", tasks);
            request.setAttribute("todoListId", todoListId);
            request.setAttribute("todoListName", todoListName);

            RequestDispatcher dispatcher = request.getRequestDispatcher("tasks.jsp");
            dispatcher.forward(request, response);
        } finally {
            entityManager.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Long todoListId = Long.parseLong(request.getParameter("todoListId"));
            String description = request.getParameter("description");
            String taskIdStr = request.getParameter("taskId");
            String doneStr = request.getParameter("done");

            TaskDAO taskDAO = new TaskDAO(entityManager);
            TodoListDAO todoListDAO = new TodoListDAO(entityManager);

            if (description != null && !description.isEmpty()) {
                // Creating a new task
                Task newTask = new Task();
                newTask.setDescription(description);
                newTask.setDone(false);  // Ensure 'done' is set to false initially
                newTask.setTodoList(todoListDAO.findTodoListById(todoListId));
                taskDAO.createTask(newTask);
            } else if (taskIdStr != null && !taskIdStr.isEmpty() && doneStr != null) {
                // Updating task status
                Long taskId = Long.parseLong(taskIdStr);
                boolean done = Boolean.parseBoolean(doneStr);
                Task task = taskDAO.findTaskById(taskId);
                task.setDone(done);
                taskDAO.updateTask(task);
            }

            transaction.commit();
            response.sendRedirect("task?listId=" + todoListId);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }


}
