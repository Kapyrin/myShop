<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 25.10.2024
  Time: 09:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/language/orders" var="ordersLanguage"/>
<fmt:setBundle basename="/language/product" var="productsLanguage"/>
<html>
<head>
    <title><fmt:message key="orders.create" bundle="${ordersLanguage}"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<br><br>
<h1><fmt:message key="orders.create" bundle="${ordersLanguage}"/></h1>
<br><br>
<form action="/createOrder" method="post">
    <h2><fmt:message key="orders.choice" bundle="${ordersLanguage}"/></h2>
    <br><br>

    <table class="table table-bordered table-striped">
        <tr>
            <th><fmt:message key="product.name" bundle="${productsLanguage}"/></th>
            <th><fmt:message key="product.description" bundle="${productsLanguage}"/></th>
            <th><fmt:message key="product.price" bundle="${productsLanguage}"/></th>
            <th><fmt:message key="product.remain" bundle="${productsLanguage}"/></th>
            <th><fmt:message key="orders.quantity" bundle="${ordersLanguage}"/></th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.productName}</td>
                <td>${product.productDescription}</td>
                <td>${product.price}</td>
                <td>${product.productRemain}</td>
                <td>
                    <input type="number" name="quantity${product.id}" min="0" max="${product.productRemain}"
                           class="form-control" placeholder="0">
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="submit" class="btn btn-dark"><fmt:message key="orders.create" bundle="${ordersLanguage}"/></button>
</form>
<br><br>
<a href="/customerOrders"><fmt:message key="orders.back_to_orders" bundle="${ordersLanguage}"/></a>
</body>
</html>
