<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tlds" uri="/WEB-INF/tlds/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<html>
<head>
    <title><fmt:message key="user.manager.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='/style/main.css' %>
</style>
<body>
    <ul class="menul">
        <li><a href="${pageContext.request.contextPath}/api?action=manager"><fmt:message key="user.manager.title" bundle="${title}"/></a></li>
    </ul>
    <ul class="menur">
        <li><a href="${pageContext.request.contextPath}/api?action=logout"><fmt:message key="menu.out" bundle="${buttons}"/></a></li>
        <li>
            <%--                <a href="${pageContext.request.contextPath}/api?action=language?en">--%>
            <%--                    <fmt:message key="lang.en" bundle="${buttons}"/>--%>
            <%--                </a>--%>
            <a href="?cookieLocale=en">
                <fmt:message key="lang.en" bundle="${buttons}"/>
            </a>
        </li>
        <li>
            <a href="?cookieLocale=ru">
                <fmt:message key="lang.ru" bundle="${buttons}"/>
            </a>
        </li>
    </ul>

    <div class="fixed-overlay fixed-overlay__modal">
        <div class="modal">
            <div class="modal_container">
                <h1><fmt:message key="user.h1" bundle="${title}"/>, ${sessionScope.user.name} ${sessionScope.user.surname}!</h1>
                <form name="newProduct" action="${pageContext.request.contextPath}/api?action=newProduct" method="post">
                    <button type="submit" name="submit"><fmt:message key="button.manager.product.new" bundle="${buttons}"/></button>
                </form>
                <form name="listProduct" action="${pageContext.request.contextPath}/api?action=listProduct" method="post">
                    <button type="submit" name="submit"><fmt:message key="button.manager.product.all" bundle="${buttons}"/></button>
                </form>
            </div>
        </div>
    </div>
    <div class="footer">
        <tlds:tag/>
    </div>
</body>
</html>
