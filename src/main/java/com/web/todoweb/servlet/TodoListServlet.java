package com.web.todoweb.servlet;

import com.web.todoweb.dao.TodoListDao;
import com.web.todoweb.entity.TodoList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/todolist")
public class TodoListServlet extends HttpServlet {

    private final TodoListDao todoListDao = new TodoListDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("create_todo_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        if (name != null && !name.trim().isEmpty()) {
            TodoList todoList = new TodoList(name);
            todoListDao.createTodoList(todoList);
            resp.sendRedirect("task?todoListId=" + todoList.getId());
        } else {
            // Log or handle the error
            System.err.println("Name parameter is null or empty");
        }
    }
}
