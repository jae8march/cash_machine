<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<fmt:setBundle basename="errors" var="errors"/>
<fmt:setBundle basename="table" var="table"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="user.cashier.check.title" bundle="${title}"/> ${check.checkId}</title>
</head>
<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/tablePage.css' %>
    <%@include file='../../style/leftNavigation.css' %>
</style>
<body>
<ul class="menul">
    <li><a href="${pageContext.request.contextPath}/api?action=chiefCashier"><fmt:message key="user.cashier.chief.title" bundle="${title}"/></a></li>
    <li><a href="${pageContext.request.contextPath}/api?action=cashier"><fmt:message key="user.cashier.title" bundle="${title}"/></a></li>
</ul>
<ul class="menur">
    <li><a href="${pageContext.request.contextPath}/api?action=logout"><fmt:message key="menu.out" bundle="${buttons}"/></a></li>
</ul>
<br>
<br>

<div class="leftNav">
    <p><fmt:message key="user.cashier.detail.check.id" bundle="${inscription}"/>: ${check.checkId};<br>
        <fmt:message key="user.cashier.detail.check.login" bundle="${inscription}"/>: ${loginUser};<br>
        <fmt:message key="user.cashier.detail.check.status" bundle="${inscription}"/>: <tags:status check_status="${check.checkStatus}"/>;<br>
        <fmt:message key="user.cashier.detail.check.date" bundle="${inscription}"/>: ${date};<br>
        <fmt:message key="user.cashier.detail.check.price" bundle="${inscription}"/>: <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${check.checkPrice}"/>;</p><br>

    <form name="close" action="${pageContext.request.contextPath}/api?action=changeStatus" method="post">
        <input type="hidden" name="status" value="CLOSED">
        <button class="inside" name = "changeStatusId" value="${check.checkId}" type="submit">
            <fmt:message key="button.navigation.check.closed" bundle="${buttons}"/>
        </button>
    </form>
    <form name="cancel" action="${pageContext.request.contextPath}/api?action=changeStatus" method="post">
        <input type="hidden" name="status" value="CANCELLED">
        <button class="inside" name="changeStatusId" value="${check.checkId}" type="submit">
            <fmt:message key="button.navigation.check.cancel" bundle="${buttons}"/>
        </button>
    </form>
    <br>
    <form action="${pageContext.request.contextPath}/api?action=detailsCheck" method="post">
        <c:if test="${page > 1}">
            <button class="inside"  type="submit" name="nextPage" value='previous'>
                « <fmt:message key="button.navigation.previous" bundle="${table}"/>
            </button>
        </c:if>
        <c:if test="${page < lastPage}">
            <button class="inside"  type="submit" name="nextPage" value='next'>
                <fmt:message key="button.navigation.next" bundle="${table}"/> »
            </button>
        </c:if>
        <input type="hidden" name = "page" value="${page}">
    </form>
    <c:if test="${not empty requestScope.check_status_not_created}">
        <div class="error"><fmt:message key="error.add.product.check.status.not.created" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.code_not_int}">
        <div class="error"><fmt:message key="error.int.code" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.product_not_exist}">
        <div class="error"><fmt:message key="error.product.check" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.product_exist_check}">
        <div class="error"><fmt:message key="error.add.product.exist.check" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.weight_number_error_message}">
        <div class="error"><fmt:message key="error.number.weight" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.quantity_error_message}">
        <div class="error"><fmt:message key="error.int.quantity" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.table_error_message}">
        <div class="error"><fmt:message key="error.add.product.check" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.check_closed}">
        <div class="error"><fmt:message key="error.check.closed" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.check_cancelled}">
        <div class="error"><fmt:message key="error.check.cancelled" bundle="${errors}"/></div>
    </c:if>
    <c:if test="${not empty requestScope.check_status_access}">
        <div class="error"><fmt:message key="error.h1" bundle="${title}"/></div>
    </c:if>
</div>

<table>
    <tr>
        <td><fmt:message key="table.product.all.code" bundle="${table}"/></td>
        <td><fmt:message key="table.product.all.name" bundle="${table}"/></td>
        <td><fmt:message key="table.product.all.price" bundle="${table}"/></td>
        <td><fmt:message key="table.product.all.quantity" bundle="${table}"/></td>
        <td><fmt:message key="table.product.all.weight" bundle="${table}"/></td>
        <td><fmt:message key="table.product.all.sold" bundle="${table}"/></td>
    </tr>
    <c:forEach items="${products}" var="product">
        <tr>
            <td><c:out value="${product.code}"/></td>
            <td><c:out value="${product.name}"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${product.price}"/></td>
            <td><c:out value="${product.quantity}"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${product.weight}"/></td>
            <td><c:out value="${product.weightSold}"/></td>
            <td>
                <form name="deleteFromCheck" action="${pageContext.request.contextPath}/api?action=deleteFromCheck" method="post">
                    <input type="hidden" name="deleteFromCheckId" value="${check.checkId}">
                    <button name = "deleteCode" value="${product.code}" class="inside" type="submit">
                        <fmt:message key="button.table.delete" bundle="${buttons}"/>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="formForAdd">
    <form name="addInCheck" action="${pageContext.request.contextPath}/api?action=addInCheck" method="post">
        <select size="1" name="addBy" id="addBy">
            <option value="productCode" ><fmt:message key="table.sort.code" bundle="${table}"/></option>
            <option value="productName" ><fmt:message key="table.sort.name" bundle="${table}"/></option>
        </select>
        <input type="text" name="designation" id="designation">
<br>
        <label for="howMany">
            <fmt:message key="button.check.add.many" bundle="${buttons}"/>?
        </label>
        <input type="text" name="howMany" id="howMany">

        <button name="addProductInCheck" value="${check.checkId}" type="submit">
            <fmt:message key="button.navigation.product.add" bundle="${buttons}"/>
        </button>
    </form>
</div>
</body>
</html>
