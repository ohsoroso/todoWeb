<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Todo List</title>
</head>
<body>
<h2>Create a New Todo List</h2>
<form action="todolist" method="post">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required><br><br>
    <input type="submit" value="Create Todo List">
</form>
</body>
</html>
