<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tlds" uri="/WEB-INF/tlds/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="error.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='/style/main.css' %>
</style>
<body>
<ul class="menul">
    <li><a href="${pageContext.request.contextPath}/"><fmt:message key="menu.main" bundle="${buttons}"/></a></li>
    <li><a href="${pageContext.request.contextPath}/api?action=manager"><fmt:message key="user.role.manager" bundle="${buttons}"/></a></li>
    <li><a href="${pageContext.request.contextPath}/api?action=admin"><fmt:message key="user.role.admin" bundle="${buttons}"/></a></li>
    <li><a href="${pageContext.request.contextPath}/api?action=cashier"><fmt:message key="user.role.cashier" bundle="${buttons}"/></a></li>
    <li><a href="${pageContext.request.contextPath}/api?action=chiefCashier"><fmt:message key="user.role.cashier.chief" bundle="${buttons}"/></a></li>
</ul>
<ul class="menur">
    <form name="language">
        <li>
            <a href="${pageContext.request.contextPath}/api?action=language">
                <fmt:message key="lang.ru" bundle="${buttons}"/>
            </a>
        </li>
        <li><fmt:message key="lang.en" bundle="${buttons}"/></li>
    </form>
</ul>
<br>

<div class="fixed-overlay fixed-overlay__modal">
    <div class="modal">
        <div class="modal_container">
            <h1><fmt:message key="error.h1" bundle="${title}"/></h1>

            <form action="${pageContext.request.contextPath}/api?action=registerPage" method="post">
                <button type="submit">
                    <fmt:message key="button.register" bundle="${buttons}"/>
                </button>
            </form>

            <form action="${pageContext.request.contextPath}/api?action=loginPage" method="post">
                <button type="submit" name="submit">
                    <fmt:message key="button.sign" bundle="${buttons}"/>
                </button>
            </form>
        </div>
    </div>
</div>
<div class="footer">
    <tlds:tag/>
</div>
</body>
</html>
