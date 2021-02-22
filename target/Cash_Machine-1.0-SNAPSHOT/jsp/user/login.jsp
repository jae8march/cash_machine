<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>--%>
<c:choose>
    <c:when test="${locale == 'ru'}">
        <fmt:setLocale value="ru"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<fmt:setBundle basename="errors" var="errors"/>
<html>
<head>
<%--    delete meta teg   ?  --%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="login.title" bundle="${title}"/></title>
    <script src="../../js/password.js"></script>
</head>

<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/formStyle.css' %>
</style>
<body>
    <ul class="menul">
        <li><a href="${pageContext.request.contextPath}/"><fmt:message key="menu.main" bundle="${buttons}"/></a></li>
        <li><a href="${pageContext.request.contextPath}/jsp/user/register.jsp"><fmt:message key="menu.register" bundle="${buttons}"/></a></li>
    </ul>
    <ul class="menur">
        <li><fmt:message key="lang.ru" bundle="${buttons}"/></li>
        <li><fmt:message key="lang.en" bundle="${buttons}"/></li>
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
                            <p for="show"><fmt:message key="register.form.password.show" bundle="${inscription}"/>
                                <input type="checkbox" onclick="password()" id="show">
                            </p>
                            <c:if test="${not empty requestScope.password_error_message}">
                                <div class="error"><fmt:message key="error.empty.password" bundle="${errors}"/></div>
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
