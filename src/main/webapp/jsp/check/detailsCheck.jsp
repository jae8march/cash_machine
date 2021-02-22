<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%--<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>--%>
<c:choose>
    <c:when test="${locale == 'ru'}">
        <fmt:setLocale value="ru"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<fmt:setBundle basename="errors" var="errors"/>
<fmt:setBundle basename="table" var="table"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Check ${check.checkId}</title>
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
    <p>Check id: ${check.checkId};<br>
        Cashier: ${loginUser};<br>
        Status: ${check.checkStatus};<br>
        Date: ${date};<br>
        Total price: ${check.checkPrice};</p><br>
    <form name="close" action="${pageContext.request.contextPath}/api?action=check/changeStatus" method="post">
        <input type="hidden" name="status" value="CLOSED">
        <button name = "changeStatusId" value="${check.checkId}" type="submit"><fmt:message key="button.navigation.check.closed" bundle="${buttons}"/></button>
    </form>
    <form name="cancel" action="${pageContext.request.contextPath}/api?action=check/changeStatus" method="post">
        <input type="hidden" name="status" value="CANCELLED">
        <button name="changeStatusId" value="${check.checkId}" type="submit"><fmt:message key="button.navigation.check.cancel" bundle="${buttons}"/></button>
    </form>

    <form action="${pageContext.request.contextPath}/api?action=check/listCheck" method="post">
        <c:if test="${page > 1}">
            <button class="inside"  type="submit" name="nextPage" value='previous'>
                « Previous
            </button>
        </c:if>
        <c:if test="${page < lastPage}">
            <button class="inside"  type="submit" name="nextPage" value='next'>
                Next »
            </button>
        </c:if>
        <input type="hidden" name = "page" value="${page}">
        <input type="hidden" name = "sort" value="${sort}">
    </form>
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
            <td><c:out value="${product.price}"/></td>
            <td><c:out value="${product.quantity}"/></td>
            <td><c:out value="${product.weight}"/></td>
            <td><c:out value="${product.weightSold}"/></td>
            <td>
                <form name="deleteProduct" action="${pageContext.request.contextPath}/api?action=chiefCashier/deleteFromCheck" method="post">
                    <input type="hidden" name="deleteFromCheckId" value="${check.checkId}">
                    <button name = "deleteCode" value="${product.code}" class="inside" type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="formForAdd">
    <form name="addInCheck" action="${pageContext.request.contextPath}/api?action=cashier/addInCheck" method="post">
        <select size="1" name="addBy" id="addBy">
            <option value="productCode" ><fmt:message key="table.sort.code" bundle="${table}"/></option>
            <option value="productName" ><fmt:message key="table.sort.name" bundle="${table}"/></option>
        </select>
        <input type="text" name="designation" id="designation">
<br>
        <label for="howMany">How many?</label>
        <input type="text" name="howMany" id="howMany">

        <button name="addProductInCheck" value="${check.checkId}" type="submit">Add product</button>
    </form>
</div>

</body>
</html>
