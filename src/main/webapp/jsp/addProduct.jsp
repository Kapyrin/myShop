<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 24.10.2024
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="/language/product" />
<html>
<head>
    <title><fmt:message key="product.title"/> </title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<jsp:include page="/jsp/languageSwitch.jsp"/>
<br><br>


<body>
<h2><fmt:message key="product.title"/> </h2>
<br><br>
<form action="/addProduct" method="post">
    <label for="productName"><fmt:message key="product.name"/></label>
    <input type="text" id="productName" name="productName" required><br><br>

    <label for="description"><fmt:message key="product.description"/></label>
    <textarea id="description" name="productDescription" required></textarea><br><br>

    <label for="price"><fmt:message key="product.price"/></label>
    <input type="number" id="price" name="productPrice"/><br><br>

    <label for="quantity"><fmt:message key="product.remain"/></label>
    <input type="number" id="quantity" name="productQuantity" required><br><br>

    <input type="submit" value="<fmt:message key="product.add"/>">
</form>
</body>
</html>