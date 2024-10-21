<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 21.10.2024
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All products</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<h1> Available products</h1>
<br><br>

<table class="table table-bordered table-striped">
    <tr>
        <th>Product ID</th>
        <th>Product Name</th>
        <th>Product Description</th>
        <th>Price</th>
        <th>Product_remain</th>
    </tr>
    <c:if test="${empty products}">
        <p>No products found. Everyone bought it;</p>
    </c:if>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.productName}</td>
            <td>${product.productDescription}</td>
            <td>${product.price}</td>
            <td>${product.productRemain}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
