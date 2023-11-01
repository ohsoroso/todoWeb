<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Todo Lists</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        h2 {
            margin-top: 20px;
        }
        form {
            margin-top: 10px;
        }
        input[type="text"] {
            padding: 5px;
            margin-right: 10px;
        }
        input[type="submit"] {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Todo Lists</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <!-- Iterate and display todo lists -->
    <c:forEach var="todoList" items="${todoLists}">
        <tr>
            <td><a href="task?listId=${todoList.id}">${todoList.id}</a></td>
            <td>${todoList.name}</td>
        </tr>
    </c:forEach>
</table>
<h2>Create a new Todo List</h2>
<form action="todo" method="post">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required>
    <input type="submit" value="Create">
</form>
</body>
</html>
