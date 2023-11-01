package com.web.todoweb.servlet;

import com.web.todoweb.dao.TodoListDAO;
import com.web.todoweb.entity.TodoList;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/todo")
public class TodoListServlet extends HttpServlet {

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
            TodoListDAO todoListDAO = new TodoListDAO(entityManager);
            List<TodoList> todoLists = todoListDAO.getAllTodoLists();
            request.setAttribute("todoLists", todoLists);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } finally {
            entityManager.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String name = request.getParameter("name");
            TodoList newTodoList = new TodoList();
            newTodoList.setName(name);

            TodoListDAO todoListDAO = new TodoListDAO(entityManager);
            todoListDAO.createTodoList(newTodoList);

            response.sendRedirect("todo");
        } finally {
            entityManager.close();
        }
    }
    // Additional CRUD operations
}
