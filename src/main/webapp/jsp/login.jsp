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
<fmt:setBundle basename="/language/login" />
<html>
<head>
    <title><fmt:message key="login.title"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
<jsp:include page="/jsp/languageSwitch.jsp"/>
<br><br>
<%
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<h1><%= errorMessage %>
</h1>

<%
        session.removeAttribute("errorMessage");
    }
%>

<form action="/login" method="post">
    <b><fmt:message key="login.greetings"/></b>
    <br><br>
    <label for="email"><fmt:message key="login.email"/>:</label>
    <input type="text" id="email" name="email"><br><br>
    <label for="password"> <fmt:message key="login.password"/>:</label>
    <input type="password" id="password" name="password"><br><br>
    <input type="submit" value=<fmt:message key="login.button"/>>
</form>
</body>
</html>
