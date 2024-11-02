<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 09.10.2024
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="/language/admin" />
<html>
<head>
    <title><fmt:message key="admin.create_user"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
<jsp:include page="/jsp/util/header.jsp"/>
<br><br>
<h1><fmt:message key="admin.create_user"/></h1>
<form action="/createUser" method="post">
    <label for="firstName"><fmt:message key="admin.user_firs_name"/></label>
    <input type="text" id="firstName" name="firstName">
    <br><br>
    <label for="lastName"><fmt:message key="admin.user_last_name"/></label>
    <input type="text" id="lastName" name="lastName">
    <br><br>
       <label for="email"><fmt:message key="admin.user_email"/></label>
    <input type="text" id="email" name="email">
    <br><br>
    <label for="password"><fmt:message key="admin.user_password"/></label>
    <input type="password" id="password" name="password">
    <br><br>
    <label for="phoneNumber"><fmt:message key="admin.user_phone"/></label>
    <input type="text" id="phoneNumber" name="phoneNumber">
    <br><br>
    <label for="address"><fmt:message key="admin.user_address"/></label>
    <input type="text" id="address" name="address">
    <br><br>
    <label for="role"><fmt:message key="admin.user_role"/></label>
    <select id="role" name="role">
        <option value="admin">Admin</option>
        <option value="manager">Manager</option>
        <option value="customer">Customer</option>
    </select><br><br>
    <input type="submit" class="btn btn-success" value="Create User">
</form>

</body>
</html>
