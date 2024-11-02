<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 28.10.2024
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>
    <jsp:include page="/jsp/util/languageSwitch.jsp"/>
</div>
<div>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="/language/logout"/>
    <a href="/logout" class="btn btn-danger">
        <fmt:message key="logout.button"/>
    </a>
</div>

