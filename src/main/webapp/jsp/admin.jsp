<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 10.10.2024
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin panel</title>
</head>
<body>
<h1>Users</h1>

<h2>Find users by ID</h2>
<form action="/findUserById" method="GET">
    <label for="userId">User id:</label>
    <input type="number" id="userId" name="userId" required>
    <button type="submit">Find</button>
</form>

<c:if test="${not empty foundUser}">
    <h2>Found User Details</h2>
    <p>ID: ${foundUser.id}</p>
    <p>First Name: ${foundUser.firstName}</p>
    <p>Last Name: ${foundUser.lastName}</p>
    <p>Email: ${foundUser.email}</p>
    <p>Phone Number: ${foundUser.phoneNumber}</p>
    <p>Address: ${foundUser.address}</p>
    <p>Role: ${foundUser.role.userRole}</p>
</c:if>

<c:if test="${not empty errorMessage}">
    <p style="color:red">${errorMessage}</p>
</c:if>

<h2>All users</h2>
<table>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Phone Number</th>
        <th>Address</th>
        <th>Role</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>${user.phoneNumber}</td>
            <td>${user.address}</td>
            <td>${user.role.userRole}</td>
            <td>
                <form action="/deleteUser" method="get" style="display:inline;">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <button type="submit">Delete</button>
                </form>
                <form action="/editUser" method="GET" style="display:inline;">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <button type="submit">Update User</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
