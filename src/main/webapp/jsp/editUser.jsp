<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/language/admin"/>
<jsp:include page="/jsp/languageSwitch.jsp"/>
<br><br>
<form action="/editUser" method="POST">
    <input type="hidden" name="userId" value="${user.id}"/>

    <label for="firstName"><fmt:message key="admin.user_firs_name"/></label>
    <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
    <br> <br>

    <label for="lastName"><fmt:message key="admin.user_last_name"/></label>
    <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
    <br> <br>

    <label for="email"><fmt:message key="admin.user_email"/></label>
    <input type="email" id="email" name="email" value="${user.email}" required>
    <br> <br>

    <label for="password"><fmt:message key="admin.user_password"/></label>
    <input type="password" id="password" name="password" value="${user.password}" required>
    <br>

    <label for="phoneNumber"><fmt:message key="admin.user_phone"/></label>
    <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
    <br> <br>

    <label for="address"><fmt:message key="admin.user_address"/>:</label>
    <input type="text" id="address" name="address" value="${user.address}">
    <br> <br>

    <label for="role"><fmt:message key="admin.user_role"/></label>
    <select id="role" name="role">
        <option value="admin">Admin</option>
        <option value="manager">Manager</option>
        <option value="customer">Customer</option>
    </select><br><br>
    <br> <br>

    <button type="submit" class="btn btn-info"><fmt:message key="admin.save_changes"/></button>
</form>
