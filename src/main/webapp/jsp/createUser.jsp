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
    <title>Create User</title>
</head>
<body>
<h1>Create User</h1>
<form action="${pageContext.request.contextPath}/createUser" method="post">
    <label for="firstName">First name:</label>
    <input type="text" id="firstName" name="firstName">
    <br><br>
    <label for="lastName">Last name:</label>
    <input type="text" id="lastName" name="lastName">
    <br><br>
       <label for="email">Email:</label>
    <input type="text" id="email" name="email">
    <br><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password">
    <br><br>
    <label for="phoneNumber">Phone number:</label>
    <input type="text" id="phoneNumber" name="phoneNumber">
    <br><br>
    <label for="address">Address:</label>
    <input type="text" id="address" name="address">
    <br><br>
    <label for="role">Role:</label>
    <select id="role" name="role">
        <option value="admin">Admin</option>
        <option value="manager">Manager</option>
        <option value="customer">Customer</option>
    </select><br><br>
    <input type="submit" value="Create User">
</form>

</body>
</html>
