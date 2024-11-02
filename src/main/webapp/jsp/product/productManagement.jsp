<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 21.10.2024
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/language/product"/>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title><fmt:message key="product.title"/></title>
</head>
<body>
<jsp:include page="/jsp/util/header.jsp"/>
<br>

<H1><fmt:message key="product.greeting"/></H1>
<br><br>

<h2><fmt:message key="product.available"/></h2>
<br>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger" role="alert">
            ${errorMessage}
    </div>
</c:if>

<table class="table table-bordered table-striped">
    <tr>
        <th><fmt:message key="product.id"/></th>
        <th><fmt:message key="product.name"/></th>
        <th><fmt:message key="product.description"/></th>
        <th><fmt:message key="product.price"/></th>
        <th><fmt:message key="product.remain"/></th>
    </tr>
    <c:if test="${empty products}">
        <p><fmt:message key="product.not_fount"/></p>
    </c:if>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.productName}</td>
            <td>${product.productDescription}</td>
            <td>${product.price}</td>
            <td>${product.productRemain}</td>
            <td>
                <a href="/editProduct?id=${product.id}"><fmt:message key="product.edit"/></a>
                <form action="/products" method="post">
                    <input type="hidden"  name="action" value="delete"/>
                    <input type="hidden" name="productId" value="${product.id}"/>
                    <button class="btn btn-danger" type="submit"><fmt:message key="product.delete"/></button>
                </form>
            </td>
        </tr>
        </tr>
    </c:forEach>
</table>
<br>
<a href="/jsp/product/addProduct.jsp"><fmt:message key="product.add"/> </a>
<br><br>
<a href="/managers"><fmt:message key="product.back_to_users"/> </a>
</body>
</html>
