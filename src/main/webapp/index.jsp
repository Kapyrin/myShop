<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="/language/main" />
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="main.title"/> </title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/jsp/languageSwitch.jsp"/>
<br><br>
<h1><fmt:message key="main.greetings"/></h1>
<br><br>
<h2><a href="jsp/login.jsp"><fmt:message key="main.login"/></a></h2>
<br><br>
<!--<form action="login" method="post">-->
<!--    <label for="email">Email:</label>-->
<!--    <input type="text" id="email" name="email" required>-->
<!--    <br><br>-->
<!--    <label for="password">Password:</label>-->
<!--    <input type="password" id="password" name="password" required><br><br>-->
<!--    <button type="submit">Login</button>-->
<!--    <br><br>-->
<!--</form>-->
<h2><a href="jsp/createUser.jsp"><fmt:message key="main.create"/></a></h2>
</body>
</html>

