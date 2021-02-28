<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tlds" uri="/WEB-INF/tlds/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<html lang="${cookie['lang'].value}">
<head>
    <title><fmt:message key="main.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='style/main.css' %>
</style>
    <body>
        <ul class="menul">
            <li>
                <a href="${pageContext.request.contextPath}/">
                    <fmt:message key="menu.main" bundle="${buttons}"/>
                </a>
            </li>
        </ul>
        <ul class="menur">
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
        <br>

        <div class="fixed-overlay fixed-overlay__modal">
            <div class="modal">
                <div class="modal_container">
                    <h1><fmt:message key="main.h1" bundle="${title}"/></h1>
                    <p><fmt:message key="main.p" bundle="${inscription}"/></p>

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
