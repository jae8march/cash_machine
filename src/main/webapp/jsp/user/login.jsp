<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<fmt:setBundle basename="errors" var="errors"/>
<html>
<head>
    <title><fmt:message key="login.title" bundle="${title}"/></title>
</head>

<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/formStyle.css' %>
</style>
<body>
    <ul class="menul">
        <li><a href="${pageContext.request.contextPath}/"><fmt:message key="menu.main" bundle="${buttons}"/></a></li>
        <li><a href="${pageContext.request.contextPath}/api?action=registerPage"><fmt:message key="menu.register" bundle="${buttons}"/></a></li>
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
                <c:if test="${not empty requestScope.sign_error_message}">
                    <div class="error"><fmt:message key="error.login" bundle="${errors}"/></div>
                </c:if>
                <form class="block" action="${pageContext.request.contextPath}/api?action=sign" method="post">
                    <ul>
                        <li>
                            <h1>
                                <fmt:message key="login.h1" bundle="${title}"/>
                            </h1>
                        </li>
                        <li>
                            <label for="login">
                                <fmt:message key="login.form.login" bundle="${inscription}"/>:
                            </label>
                            <input type="text" name="login" id="login">
                            <c:if test="${not empty requestScope.login_error_message}">
                                <div class="error"><fmt:message key="error.empty.login" bundle="${errors}"/></div>
                            </c:if>
                        </li>
                        <li>
                            <label for="pass">
                                <fmt:message key="login.form.password" bundle="${inscription}"/>:
                            </label>
                            <input type="password" name="pass" id="pass">
                            <c:if test="${not empty requestScope.password_error_message}">
                                <div class="error"><fmt:message key="error.empty.password" bundle="${errors}"/></div>
                            </c:if>
                            <c:if test="${not empty requestScope.password_wrong_error_message}">
                                <div class="error"><fmt:message key="error.correct.password" bundle="${errors}"/></div>
                            </c:if>
                        </li>
                        <li>
                            <button type="submit" name="submit">
                                <fmt:message key="button.submit" bundle="${buttons}"/>
                            </button>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
