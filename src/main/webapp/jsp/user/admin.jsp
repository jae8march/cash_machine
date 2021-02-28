<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tlds" uri="/WEB-INF/tlds/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<html>
<head>
    <title><fmt:message key="user.admin.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='/style/main.css' %>
</style>
<body>
<ul class="menul">
    <li><a href="${pageContext.request.contextPath}/api?action=admin"><fmt:message key="user.admin.title" bundle="${title}"/></a></li>
</ul>
<ul class="menur">
    <li><a href="${pageContext.request.contextPath}/api?action=logout"><fmt:message key="menu.out" bundle="${buttons}"/></a></li>
    <li><fmt:message key="lang.ru" bundle="${buttons}"/></li>
    <li><fmt:message key="lang.en" bundle="${buttons}"/></li>
</ul>

<div class="fixed-overlay fixed-overlay__modal">
    <div class="modal">
        <div class="modal_container">
            <h1><fmt:message key="user.h1" bundle="${title}"/>, ${sessionScope.user.name} ${sessionScope.user.surname}!</h1>
            <form name="listUsers" action="${pageContext.request.contextPath}/api?action=listUsers" method="post">
                <button type="submit" name="submit"><fmt:message key="button.admin.user.all" bundle="${buttons}"/></button>
            </form>
        </div>
    </div>
</div>
<div class="footer">
    <tlds:tag/>
</div>
</body>
</html>
