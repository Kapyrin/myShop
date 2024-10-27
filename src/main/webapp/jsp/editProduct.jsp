<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 24.10.2024
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/language/product"/>
<html>
<head>

    <title><fmt:message key="product.edit.title"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
</head>
<body>
<jsp:include page="/jsp/languageSwitch.jsp"/>
<br><br>

<h1><fmt:message key="product.edit.heading"/></h1>

<form action="/editProduct" method="post">
    <input type="hidden" name="productId" value="${product.id}"/>
    <div class="form-group">
        <label for="productName"><fmt:message key="product.name"/></label>
        <input type="text" class="form-control" id="productName" name="productName" value="${product.productName}" required>
    </div>

    <div class="form-group">
        <label for="productDescription"><fmt:message key="product.description"/></label>
        <input type="text" class="form-control" id="productDescription" name="productDescription"
               value="${product.productDescription}" required>
    </div>

    <div class="form-group">
        <label for="price"><fmt:message key="product.price"/></label>
        <input type="number" class="form-control" id="price" name="price" value="${product.price}" required>
    </div>

    <div class="form-group">
        <label for="productRemain"><fmt:message key="product.remain"/></label>
        <input type="number" class="form-control" id="productRemain" name="productRemain" value="${product.productRemain}" required>
    </div>

    <button type="submit"><fmt:message key="product.save"/></button>
</form>

<br><br>
<a href="/products"><fmt:message key="product.cancel"/></a>
</body>
</html>
