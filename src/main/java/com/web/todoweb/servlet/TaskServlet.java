package com.web.todoweb.servlet;

import com.web.todoweb.entity.TodoList;
import com.web.todoweb.dao.TaskDao;
import com.web.todoweb.dao.TodoListDao;
import com.web.todoweb.entity.Task;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {

    private final TaskDao taskDao = new TaskDao();
    private final TodoListDao todoListDao = new TodoListDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long todoListId = Long.parseLong(req.getParameter("todoListId"));
            TodoList todoList = todoListDao.findTodoListById(todoListId);
            if (todoList != null) {
                req.setAttribute("todoList", todoList);
                req.getRequestDispatcher("create_task.jsp").forward(req, resp);
            } else {
                // Log or handle the error
                System.err.println("TodoList not found");
            }
        } catch (NumberFormatException e) {
            // Log or handle the error
            System.err.println("Invalid todoListId parameter");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String description = req.getParameter("description");
            boolean done = Boolean.parseBoolean(req.getParameter("done"));
            Long todoListId = Long.parseLong(req.getParameter("todoListId"));
            if (description != null) {
                TodoList todoList = todoListDao.findTodoListById(todoListId);
                if (todoList != null) {
                    Task task = new Task(description, done);
                    task.setTodoList(todoList);
                    taskDao.createTask(task);
                    resp.sendRedirect("task?todoListId=" + todoListId);
                } else {
                    // Log or handle the error
                    System.err.println("TodoList not found");
                }
            } else {
                // Log or handle the error
                System.err.println("Description parameter is null");
            }
        } catch (NumberFormatException e) {
            // Log or handle the error
            System.err.println("Invalid todoListId parameter");
        }
    }
}
