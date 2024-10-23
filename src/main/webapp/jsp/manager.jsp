<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 09.10.2024
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="/language/manager" />
<html>
<head>
    <title><fmt:message key="managers.title"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
<jsp:include page="/jsp/languageSwitch.jsp"/>
<br><br>
<h1><fmt:message key="manager.greetings"/></h1>
<br><br>
<table class="table table-bordered table-striped">
    <tr>
        <th><fmt:message key="manager.id"/> </th>
        <th><fmt:message key="manager.first_name"/></th>
        <th><fmt:message key="manager.last_name"/></th>
        <th><fmt:message key="manager.email"/></th>
        <th><fmt:message key="manager.phone"/></th>
        <th><fmt:message key="manager.orders"/></th>
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