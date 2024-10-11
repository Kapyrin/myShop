<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 10.10.2024
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Details</title>
</head>
<body>
<h1>User Details</h1>
<c:if test="${not empty user}">
    <p>ID: ${user.id}</p>
    <p>First Name: ${user.firstName}</p>
    <p>Last Name: ${user.lastName}</p>
    <p>Email: ${user.email}</p>
    <p>Phone Number: ${user.phoneNumber}</p>
    <p>Address: ${user.address}</p>
    <p>Role: ${user.role.userRole}</p>
</c:if>
<c:if test="${empty user}">
    <p>User not found.</p>
</c:if>
<a href="/users">Back to Users</a>
</body>
</html>

