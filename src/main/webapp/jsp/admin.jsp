<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 10.10.2024
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/language/admin"/>

<html>
<head>
    <title><fmt:message key="admin.title"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>

<body>
<jsp:include page="/jsp/header.jsp"/>
<br><br>

<h1><fmt:message key="admin.greetings"/></h1>
<br><br>

<h2><fmt:message key="admin.find_user"/></h2>
<form action="/findUserById" method="GET">
    <label for="userId"><fmt:message key="admin.user_id"/></label>
    <input type="number" id="userId" name="userId" required>
    <button type="submit"><fmt:message key="admin.find_button"/></button>
</form>

<c:if test="${not empty foundUser}">
    <h2><fmt:message key="admin.user_detail"/></h2>
    <p><fmt:message key="admin.user_id"/> ${foundUser.id}</p>
    <p><fmt:message key="admin.user_firs_name"/> ${foundUser.firstName}</p>
    <p><fmt:message key="admin.user_last_name"/> ${foundUser.lastName}</p>
    <p><fmt:message key="admin.user_email"/> ${foundUser.email}</p>
    <p><fmt:message key="admin.user_phone"/> ${foundUser.phoneNumber}</p>
    <p>A<fmt:message key="admin.user_address"/> ${foundUser.address}</p>
    <p>R<fmt:message key="admin.user_role"/>${foundUser.role.userRole}</p>
</c:if>

<c:if test="${not empty errorMessage}">
    <p style="color:red">${errorMessage}</p>
</c:if>

<h2><fmt:message key="admin.all_users"/></h2>
<table class="table table-bordered table-striped">
    <tr>
        <th><fmt:message key="admin.user_id"/><</th>
        <th><fmt:message key="admin.user_firs_name"/></th>
        <th><fmt:message key="admin.user_last_name"/></th>
        <th><fmt:message key="admin.user_email"/></th>
        <th><fmt:message key="admin.user_phone"/></th>
        <th><fmt:message key="admin.user_address"/></th>
        <th><fmt:message key="admin.user_role"/></th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.phoneNumber}</td>
            <td>${user.address}</td>
            <td>${user.role.userRole}</td>
            <td>
                <form action="/deleteUser" method="get" style="display:inline;">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <button type="submit" class="btn btn-danger"><fmt:message key="admin.delete_user"/></button>
                </form>
                <form action="/editUser" method="GET" style="display:inline;">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <button type="submit" class="btn btn-secondary"><fmt:message key="admin.update_user"/></button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
