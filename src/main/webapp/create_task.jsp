<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Task</title>
</head>
<body>
<h2>Create a New Task for Todo List: ${todoList.name}</h2>
<form action="task" method="post">
    <label for="description">Description:</label>
    <input type="text" id="description" name="description" required><br><br>
    <label for="done">Done:</label>
    <input type="checkbox" id="done" name="done" value="true"><br><br>
    <input type="hidden" id="todoListId" name="todoListId" value="${todoList.id}">
    <input type="submit" value="Create Task">
</form>
</body>
</html>
