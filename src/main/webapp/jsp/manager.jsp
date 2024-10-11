<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 09.10.2024
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<h1>Список пользователей</h1>
<table>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>email</th>
        <th>telephone</th>
        <th>Orders</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.phoneNumber}</td>
            <td>
                <c:set var="orders" value="${userOrders[user.id]}"/>
                <c:forEach var="order" items="${orders}">
                    Order ID: ${order.id}, Date creation: ${order.orderCreationDate}, Status: ${order.status.statusName}
                    <br/>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
  </table>
</body>
</html>