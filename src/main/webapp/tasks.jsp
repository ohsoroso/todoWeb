<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Tasks</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<h1>Tasks for Todo List: ${todoListName}</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Description</th>
        <th>Done</th>
    </tr>
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td>${task.id}</td>
            <td>${task.description}</td>
            <td>
                <form action="task" method="post">
                    <input type="checkbox" name="done" value="true"
                           <c:if test="${task.done}">checked</c:if>
                           onchange="this.form.submit()"/>
                    <input type="hidden" name="taskId" value="${task.id}"/>
                    <input type="hidden" name="todoListId" value="${todoListId}">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<h2>Create a new Task</h2>
<form action="task" method="post">
    <label for="description">Description:</label>
    <input type="text" id="description" name="description" required>
    <input type="hidden" name="todoListId" value="${todoListId}">
    <input type="submit" value="Create">
</form>
<br/>
<a href="todo">Back to Todo Lists</a>
</body>
</html>
