<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 09.10.2024
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="/language/orders" />

<html>
<head>
    <title><fmt:message key="orders.title"/> </title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
<jsp:include page="/jsp/languageSwitch.jsp"/>
<br><br>
<h1><fmt:message key="orders.helloUser"/> ${user.firstName} ${user.lastName}</h1>

<h2><fmt:message key="orders.title"/> </h2>

<table class="table table-bordered table-striped">
    <tr>
        <th><fmt:message key="orders.id"/> </th>
        <th><fmt:message key="orders.creationDate"/> </th>
        <th><fmt:message key="orders.status"/> </th>

    </tr>
    <c:if test="${empty orders}">
        <p><fmt:message key="orders.noOrdersFound"/></p>
    </c:if>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.id}</td>
            <td>${order.orderCreationDate}</td>
            <td>${order.status.statusName}</td>
        </tr>
    </c:forEach>
</table>
<br><br>
<a href="/createOrder"> <fmt:message key="orders.create"/></a>
</body>
</html>
