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
    <title><fmt:message key="user.manager.product.all.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/tablePage.css' %>
    <%@include file='../../style/leftNavigation.css' %>
</style>
<body>
    <ul class="menul">
        <li><a href="${pageContext.request.contextPath}/api?action=manager"><fmt:message key="user.manager.title" bundle="${title}"/></a></li>
    </ul>
    <ul class="menur">
        <li><a href="${pageContext.request.contextPath}/api?action=logout"><fmt:message key="menu.out" bundle="${buttons}"/></a></li>
    </ul>
    <br>
    <br>
            <div class="leftNav">
                <label class="label" for="sort"><fmt:message key="table.sort" bundle="${table}"/>: </label>
                <form action="${pageContext.request.contextPath}/api?action=listProduct" method="post">
                    <select size="1" name="sort" id="sort">
                        <option value="code" ><fmt:message key="table.sort.code" bundle="${table}"/></option>
                        <option value="name" ><fmt:message key="table.sort.name" bundle="${table}"/></option>
                        <option value="price" ><fmt:message key="table.sort.price" bundle="${table}"/></option>
                        <option value="weight" ><fmt:message key="table.sort.weight" bundle="${table}"/></option>
                        <option value="quantity"><fmt:message key="table.sort.quantity" bundle="${table}"/></option>
                    </select>
                    <button type="submit" value="submit"><fmt:message key="button.navigation.sorted" bundle="${buttons}"/></button>
                </form>

                    <br>
                    <form name="newProduct" action="${pageContext.request.contextPath}/api?action=newProduct" method="post">
                        <button type="submit" name="submit"><fmt:message key="button.manager.product.new" bundle="${buttons}"/></button>
                    </form>
                <form action="${pageContext.request.contextPath}/api?action=listProduct" method="post">
                    <c:if test="${page > 1}">
                        <button class="inside"  type="submit" name="nextPage" value='previous'>
                            « <fmt:message key="button.navigation.previous" bundle="${buttons}"/>
                        </button>
                    </c:if>
                    <c:if test="${page < lastPage}">
                        <button class="inside"  type="submit" name="nextPage" value='next'>
                            <fmt:message key="button.navigation.next" bundle="${buttons}"/> »
                        </button>
                    </c:if>
                    <input type="hidden" name = "page" value="${page}">
                    <input type="hidden" name = "sort" value="${sort}">
                </form>
                <c:if test="${not empty requestScope.product_exist_in_created_check_error}">
                    <div class="error"><fmt:message key="error.delete.product.in.created.check" bundle="${errors}"/></div>
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
        <td>
            <c:choose>
                <c:when test="${product.weightSold && true}">+</c:when>
                <c:otherwise>-</c:otherwise>
            </c:choose>
        </td>
        <td>
            <form name="deleteProduct" action="${pageContext.request.contextPath}/api?action=deleteProduct" method="post">
                <button name = "deleteCode" value="${product.code}" class="inside" type="submit">
                    <fmt:message key="button.table.delete" bundle="${buttons}"/>
                </button>
            </form>
        </td>
    </tr>
        </c:forEach>
</table>
</body>
</html>
