<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 09.10.2024
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<form action="login" method="post">
    <b>Please feel the form to login:</b>
    <br><br>
    <label for="email">email:</label>
    <input type="text" id="email" name="email"><br><br>
    <label for="password"> password:</label>
    <input type="password" id="password" name="password"><br><br>
    <input type="submit" value="Login">
</form>
</body>
</html>
