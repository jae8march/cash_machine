<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tlds" uri="/WEB-INF/tlds/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<html>
    <head>
        <title><fmt:message key="user.cashier.title" bundle="${title}"/></title>
    </head>
    <style>
        <%@include file='/style/main.css' %>
    </style>
    <body>
        <ul class="menul">
            <li><a href="${pageContext.request.contextPath}/api?action=cashier"><fmt:message key="user.cashier.title" bundle="${title}"/></a></li>
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

                    <form name="newCheck" action="${pageContext.request.contextPath}/api?action=newCheck" method="post">
                        <button type="submit" name="submit"><fmt:message key="button.cashier.check.new" bundle="${buttons}"/></button>
                    </form>
                    <form name="allCheck" action="${pageContext.request.contextPath}/api?action=listCheck" method="post">
                        <button type="submit" name="submit"><fmt:message key="button.cashier.check.all" bundle="${buttons}"/> ${sessionScope.user.name} ${sessionScope.user.surname}</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="footer">
            <tlds:tag/>
        </div>
    </body>
</html>
