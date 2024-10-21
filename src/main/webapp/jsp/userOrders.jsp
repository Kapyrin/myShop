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
    <title>Your Orders</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>

<h1>Hello ${user.firstName} ${user.lastName}</h1>

<h2>Your Orders</h2>

<table>
    <tr>
        <th>Order ID</th>
        <th>Date Creation</th>
        <th>Status</th>
    </tr>
    <c:if test="${empty orders}">
        <p>No orders found for this user.</p>
    </c:if>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.id}</td>
            <td>${order.orderCreationDate}</td>
            <td>${order.status.statusName}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
