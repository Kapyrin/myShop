<%--
  Created by IntelliJ IDEA.
  User: vladimirkapyrin
  Date: 09.10.2024
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/language/manager" var="managerLanguage"/>
<fmt:setBundle basename="/language/orders" var="ordersLanguage"/>
<html>
<head>
    <title><fmt:message key="manager.greetings" bundle="${managerLanguage}"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">

</head>
<body>
<%--<jsp:include page="/jsp/languageSwitch.jsp"/>--%>
<jsp:include page="/jsp/util/header.jsp"/>


<br>
<div class="text-right mt-4">
    <a href="/products" class="btn btn-dark"><fmt:message key="manager.product_management"
                                                          bundle="${managerLanguage}"/></a>
</div>
<h2><fmt:message key="managers.title" bundle="${managerLanguage}"/></h2>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6">
            <form action="/managers" method="get">
                <div class="form-group">
                    <label for="productId"><fmt:message key="manager.sort_orders_by_selecting_product"
                                                        bundle="${managerLanguage}"/></label>
                    <select id="productId" name="productId" class="form-control">
                        <c:forEach var="product" items="${products}">
                            <option value="${product.id}">${product.productName}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" name="action" value="filterByProduct" class="btn btn-primary">
                    <fmt:message key="manager.filter_by_product" bundle="${managerLanguage}"/>
                </button>


            </form>

            <c:if test="${not empty filteredOrders}">
                <h2><fmt:message key="orders.filter" bundle="${ordersLanguage}"/></h2>
                <table class="table table-bordered table-striped">
                    <tr>
                        <th><fmt:message key="orders.id" bundle="${ordersLanguage}"/></th>
                        <th><fmt:message key="orders.creationDate" bundle="${ordersLanguage}"/></th>
                        <th><fmt:message key="orders.status" bundle="${ordersLanguage}"/></th>
                        <th><fmt:message key="orders.products" bundle="${ordersLanguage}"/></th>
                    </tr>
                    <c:forEach var="order" items="${filteredOrders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.orderCreationDate}</td>
                            <td>${order.status.statusName}</td>
                            <td>
                                <c:forEach var="product" items="${orderProducts[order.id]}">
                                    <li>${product.productName} - ${product.price}</li>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <c:if test="${empty filteredOrders}">
                <h2><fmt:message key="orders.all_orders" bundle="${ordersLanguage}"/></h2>
                <table class="table table-bordered table-striped">
                    <tr>
                        <th><fmt:message key="orders.id" bundle="${ordersLanguage}"/></th>
                        <th><fmt:message key="orders.creationDate" bundle="${ordersLanguage}"/></th>
                        <th><fmt:message key="orders.status" bundle="${ordersLanguage}"/></th>
                        <th><fmt:message key="orders.products" bundle="${ordersLanguage}"/></th>
                    </tr>
                    <c:forEach var="order" items="${allOrders}" begin="0" end="9">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.orderCreationDate}</td>
                            <td>${order.status.statusName}</td>
                            <td>
                                <ul>
                                    <c:forEach var="product" items="${orderProducts[order.id]}">
                                        <li>${product.productName} - ${product.price}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <br><br>
            <form action="/managers" method="post">
                <div class="form-group">
                    <label for="deleteBeforeDate"><fmt:message key="manager.delete_before_date"
                                                               bundle="${managerLanguage}"/></label>
                    <input type="date" id="deleteBeforeDate" name="deleteBeforeDate" class="form-control" required/>
                </div>
                <button type="submit" name="action" value="deleteBeforeDate" class="btn btn-danger">
                    <fmt:message key="manager.delete_orders" bundle="${managerLanguage}"/>
                </button>
            </form>
        </div>
        <div class="col-md-6">
            <h2><fmt:message key="manager.greetings" bundle="${managerLanguage}"/>
                <form action="/report" method="get" class="float-right">
                    <input type="hidden" name="reportType" value="usersOrders"/>
                    <button type="submit" class="tn-secondary btn-sm"><fmt:message key="manager.download_report"
                                                                                       bundle="${managerLanguage}"/></button>
                </form>
            </h2>
            <table class="table table-bordered table-striped">
                <tr>
                    <th><fmt:message key="manager.id" bundle="${managerLanguage}"/></th>
                    <th><fmt:message key="manager.first_name" bundle="${managerLanguage}"/></th>
                    <th><fmt:message key="manager.last_name" bundle="${managerLanguage}"/></th>
                    <th><fmt:message key="manager.email" bundle="${managerLanguage}"/></th>
                    <th><fmt:message key="manager.phone" bundle="${managerLanguage}"/></th>
                    <th><fmt:message key="manager.orders" bundle="${managerLanguage}"/></th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                        <td>
                            <c:set var="orders" value="${userOrders[user.id]}"/>
                            <c:forEach var="order" items="${orders}">
                            <div>
                                Order ID: ${order.id}, Date creation: ${order.orderCreationDate},
                                Status: ${order.status.statusName}
                                <ul>
                                    <c:forEach var="product" items="${orderProducts[order.id]}">
                                        <li>${product.productName} - ${product.price}</li>
                                    </c:forEach>
                                </ul>
                                <form action="/managers" method="post">
                                    <input type="hidden" name="action" value="closeOrder"/>
                                    <input type="hidden" name="orderId" value="${order.id}"/>
                                    <button type="submit" class="btn btn-success btn-sm"><fmt:message
                                            key="manager.close_order" bundle="${managerLanguage}"/></button>
                                </form>
                                <form action="/managers" method="post">
                                    <input type="hidden" name="action" value="updateStatus"/>
                                    <input type="hidden" name="orderId" value="${order.id}"/>
                                    <select name="statusId" class="form-control">
                                        <c:forEach var="status" items="${statuses}">
                                            <option value="${status.id}"
                                                    <c:if test="${status.id == order.status.id}">selected</c:if>>${status.statusName}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="submit" class="btn btn-primary btn-sm"><fmt:message
                                            key="manager.update_status" bundle="${managerLanguage}"/></button>
                                </form>
                                <h2>__________________</h2>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
    </div>
</div>
<br>
</body>
</html>
