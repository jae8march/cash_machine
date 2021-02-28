<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title><fmt:message key="user.cashier.chief.report.title" bundle="${title}"/></title>
<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/tablePage.css' %>
    <%@include file='../../style/leftNavigation.css' %>
</style>
<body>
<ul class="menul">
    <li><a href="${pageContext.request.contextPath}/api?action=chiefCashier"><fmt:message key="user.cashier.chief.title" bundle="${title}"/></a></li>
</ul>
<ul class="menur">
    <li><a href="${pageContext.request.contextPath}/api?action=logout"><fmt:message key="menu.out" bundle="${buttons}"/></a></li>
</ul>
<br>
<br>

<div class="leftNav">
    <form name="findByData" action="${pageContext.request.contextPath}/api?action=findByData" method="post">
        <label for="date">
            <fmt:message key="user.cashier.detail.check.date" bundle="${inscription}"/>:
        </label>
        <input type="date" id="date" name="date">
        <br>
        <br>
        <label class="label" for="type">
            <fmt:message key="user.cashier.chief.report.type" bundle="${inscription}"/>:
        </label>
        <select size="1" name="type" id="type">
            <option value="X">X</option>
            <option value="Z">Z</option>
        </select>
        <button type="submit" name="findAll">
            <fmt:message key="button.report.find" bundle="${buttons}"/>
        </button>
    </form>
    <form name="createReport" action="${pageContext.request.contextPath}/api?action=createXReport" method="post">
        <button type="submit" value="X" name="XType">
            <fmt:message key="button.report.x.add" bundle="${buttons}"/>
        </button>
    </form>
    <form name="createReport" action="${pageContext.request.contextPath}/api?action=createZReport" method="post">
    <button type="submit" value="Z" name="ZType">
        <fmt:message key="button.report.z.add" bundle="${buttons}"/>
    </button>
    </form>
    <c:if test="${not empty requestScope.incorrect_date}">
        <div class="error"><fmt:message key="error.date.format" bundle="${errors}"/></div>
    </c:if>
</div>
<c:choose>
    <c:when test="${empty reports}">
    <br><br>
        <c:if test="${not empty requestScope.Xreport_error_Zreport}">
            <div class="error">
                <fmt:message key="error.report.create.Xreport.Zreport" bundle="${errors}"/>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.Zreport_error_Zreport}">
            <div class="error">
                <fmt:message key="error.report.create.Zreport.Zreport" bundle="${errors}"/>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.Zreport_error_created}">
            <div class="error">
                <fmt:message key="error.report.create.Zreport.created" bundle="${errors}"/>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.index_created_Xreport}">
            <div class="error">
                <fmt:message key="index.report.create.Xreport" bundle="${errors}"/>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.index_created_Zreport}">
            <div class="error">
                <fmt:message key="index.report.create.Zreport" bundle="${errors}"/>
            </div>
        </c:if>
    </c:when>
    <c:when test="${reportType =='X'}">
        <table>
            <tr>
                <td><fmt:message key="table.report.date" bundle="${table}"/></td>
                <td><fmt:message key="table.report.cash.before" bundle="${table}"/></td>
                <td><fmt:message key="table.report.cash.after" bundle="${table}"/></td>
            </tr>
            <c:forEach items="${reports}" var="report">
                <tr>
                    <td><c:out value="${report.date}"/></td>
                    <td><c:out value="${report.beforeCash}"/></td>
                    <td><c:out value="${report.nowCash}"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <div class="center">
            <div class="block">
                <div class="d1">
                <ul class="report">
                    <c:forEach items="${reports}" var="report">
                        <h1>
                            <fmt:message key="user.cashier.chief.report.z.h1" bundle="${title}"/> ${report.date}</h1>
                    <li>
                        <label>
                            <fmt:message key="table.report.cash.before" bundle="${table}"/>: ${report.beforeCash}
                        </label>
                    </li>
                    <li>
                        <label>
                            <fmt:message key="table.report.cash.total" bundle="${table}"/>: ${report.totalCash}
                        </label>
                    </li>
                    </c:forEach>
                </ul>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
