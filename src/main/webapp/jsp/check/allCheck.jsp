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
    <title><fmt:message key="user.cashier.check.list.title" bundle="${title}"/></title>
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
                <label class="label" for="sort"><fmt:message key="table.sort" bundle="${table}"/>: </label>
                <form action="${pageContext.request.contextPath}/api?action=listCheck" method="post">
                    <select size="1" name="sort" id="sort">
                        <option value="id" ><fmt:message key="table.sort.id" bundle="${table}"/></option>
                        <option value="date" ><fmt:message key="table.sort.date" bundle="${table}"/></option>
                        <option value="status" ><fmt:message key="table.sort.status" bundle="${table}"/></option>
                        <option value="price" ><fmt:message key="table.sort.price" bundle="${table}"/></option>
                    </select>
                    <button type="submit" value="submit"><fmt:message key="button.navigation.sorted" bundle="${buttons}"/></button>
                </form>

                    <br>
                    <form name="newCheck" action="${pageContext.request.contextPath}/api?action=newCheck" method="post">
                        <button type="submit" name="submit"><fmt:message key="button.cashier.check.new" bundle="${buttons}"/></button>
                    </form>

                <form action="${pageContext.request.contextPath}/api?action=listCheck" method="post">
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
            </div>

<table>
    <tr>
        <td><fmt:message key="table.check.all.id" bundle="${table}"/></td>
        <td><fmt:message key="table.check.all.date" bundle="${table}"/></td>
        <td><fmt:message key="table.check.all.status" bundle="${table}"/></td>
        <td><fmt:message key="table.check.all.login" bundle="${table}"/></td>
        <td><fmt:message key="table.check.all.price" bundle="${table}"/></td>
    </tr>
    <c:forEach items="${checks}" var="check">
    <tr>
        <td><c:out value="${check.checkId}"/></td>
        <td><c:out value="${check.checkDate}"/></td>
        <td><tags:status check_status="${check.checkStatus}"/></td>
        <td><c:out value="${check.user.login}"/></td>
        <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${check.checkPrice}"/></td>
        <td>
            <form name="detailsCheck" action="${pageContext.request.contextPath}/api?action=detailsCheck" method="post">
                <button name = "checkId" value="${check.checkId}" class="inside" type="submit">
                    <fmt:message key="button.table.details" bundle="${buttons}"/>
                </button>
            </form>
        </td>
    </tr>
        </c:forEach>
</table>
</body>
</html>
