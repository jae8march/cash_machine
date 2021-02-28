<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<fmt:setBundle basename="errors" var="errors"/>
<fmt:setBundle basename="table" var="table"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="user.admin.user.all.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/tablePage.css' %>
    <%@include file='../../style/leftNavigation.css' %>
</style>
<body>
<ul class="menul">
    <li><a href="${pageContext.request.contextPath}/api?action=admin"><fmt:message key="user.admin.title" bundle="${title}"/></a></li>
</ul>
<ul class="menur">
    <li><a href="${pageContext.request.contextPath}/api?action=logout"><fmt:message key="menu.out" bundle="${buttons}"/></a></li>
</ul>
<br>
<br>
<div class="leftNav">
    <p><fmt:message key="statistic.p" bundle="${inscription}"/><br>
        <fmt:message key="user.role.manager" bundle="${buttons}"/>: ${manager};<br>
        <fmt:message key="user.role.admin" bundle="${buttons}"/>: ${admin};<br>
        <fmt:message key="user.role.cashier.chief" bundle="${buttons}"/>: ${chiefCashier};<br>
        <fmt:message key="user.role.cashier" bundle="${buttons}"/>: ${cashier};<br>
        <fmt:message key="statistic.all.p" bundle="${inscription}"/>: ${all};</p><br>
    <label class="label" for="sort"><fmt:message key="table.sort" bundle="${table}"/>: </label>
    <form action="${pageContext.request.contextPath}/api?action=listUsers" method="post">
        <select size="1" name="sort" id="sort">
            <option value="id" ><fmt:message key="table.sort.id" bundle="${table}"/></option>
            <option value="name" ><fmt:message key="table.sort.name" bundle="${table}"/></option>
            <option value="surname" ><fmt:message key="table.sort.surname" bundle="${table}"/></option>
            <option value="login" ><fmt:message key="table.sort.login" bundle="${table}"/></option>
            <option value="role"><fmt:message key="table.sort.role" bundle="${table}"/></option>
        </select>
        <button type="submit" value="submit"><fmt:message key="button.navigation.sorted" bundle="${buttons}"/></button>
    </form>
    <form action="${pageContext.request.contextPath}/api?action=listUsers" method="post">
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
    <c:if test="${not empty requestScope.created_check_exist}">
        <div class="error"><fmt:message key="error.cashier.delete.check.created" bundle="${errors}"/></div>
    </c:if>
</div>
<table>
    <tr>
        <td><fmt:message key="table.user.id" bundle="${table}"/></td>
        <td><fmt:message key="table.user.name" bundle="${table}"/></td>
        <td><fmt:message key="table.user.surname" bundle="${table}"/></td>
        <td><fmt:message key="table.user.login" bundle="${table}"/></td>
        <td><fmt:message key="table.user.role" bundle="${table}"/></td>
    </tr>
    <c:forEach items="${user}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.surname}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><tags:roles user_role="${user.userRole}"/></td>
            <td>
                <form name="deleteUser" action="${pageContext.request.contextPath}/api?action=deleteUser" method="post">
                    <button name = "idUser" value="${user.id}" class="inside" type="submit">
                        <fmt:message key="button.table.delete" bundle="${buttons}"/>
                    </button>
                    <input type="hidden" name="login" value="${user.login}">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>