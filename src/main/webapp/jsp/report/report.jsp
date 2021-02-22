<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Repost</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></head>
<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/tablePage.css' %>
    <%@include file='../../style/leftNavigation.css' %>
</style>

<body>
<ul class="menul">
    <li><a href="${pageContext.request.contextPath}/api?action=chiefCashier">Chief Cashier</a></li>
</ul>
<ul class="menur">
    <li>out</li>
</ul>
<br>
<br>

<div class="leftNav">
    <form name="findByData" action="${pageContext.request.contextPath}/api?action=chiefCashier/findByData" method="post">
        <label for="date">Date:</label>
        <input type="date" id="date" name="date">
        <br>
        <br>
        <label class="label" for="type">Type report: </label>
        <select size="1" name="type" id="type">
            <option value="X">X</option>
            <option value="Z">Z</option>
        </select>
        <button type="submit" name="findAll">Find all</button>
    </form>

    <form name="createReport" action="${pageContext.request.contextPath}/api?action=chiefCashier/createXReport" method="post">
        <button type="submit" value="X" name="XType">Add new X-report</button>
    </form>

    <form name="createReport" action="${pageContext.request.contextPath}/api?action=chiefCashier/createZReport" method="post">
    <button type="submit" value="Z" name="ZType">Add new z report</button>
    </form>

</div>
<c:choose>
    <c:when test="${empty reports}"> </c:when>
    <c:when test="${reportType =='X'}">
        <table>
            <tr>
                <td>Date</td>
                <td>Cash before shift</td>
                <td>Cash now</td>
            </tr>
            <c:forEach items="${reports}" var="reports">
                <tr>
                    <td><c:out value="${reports.date}"/></td>
                    <td><c:out value="${reports.beforeCash}"/></td>
                    <td><c:out value="${reports.nowCash}"/></td>
<%--                    <td><c:out value="${reports.totalCash}"/></td>--%>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <div class="center">
            <div class="block">
                <div class="d1">
                <ul class="report">
                    <c:forEach items="${reports}" var="reports">
                        <h1>Z-report for ${reports.date}</h1>
                    <li>
                        <label>Cash before shift: ${reports.beforeCash}</label>
                    </li>
                    <li>
                        <label>Cash total: ${report.totalCash}</label>
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
