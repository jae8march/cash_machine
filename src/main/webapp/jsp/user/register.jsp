<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<fmt:setBundle basename="errors" var="errors"/>
<html lang="${cookie['lang'].value}">
<head>
    <title><fmt:message key="register.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/formStyle.css' %>
    <%@include file='../../style/leftNavigation.css' %>
</style>
<body>
    <ul class="menul">
        <li><a href="${pageContext.request.contextPath}"><fmt:message key="menu.main" bundle="${buttons}"/></a></li>
        <li><a href="${pageContext.request.contextPath}/api?action=loginPage"><fmt:message key="menu.sign" bundle="${buttons}"/></a></li>
    </ul>
    <ul class="menur">
        <li>
            <a href="cookieLocale=en">
                <fmt:message key="lang.en" bundle="${buttons}"/>
            </a>
        </li>
        <li>
            <a href="cookieLocale=ru">
                <fmt:message key="lang.ru" bundle="${buttons}"/>
            </a>
        </li>
    </ul>
    <br>


    <div class="fixed-overlay fixed-overlay__modal">
        <div class="modal">
            <div class="modal_container">
                <c:if test="${not empty requestScope.register_error_message}">
                    <div class="error"><fmt:message key="error.register" bundle="${errors}"/></div>
                </c:if>
                    <form class="block" action="${pageContext.request.contextPath}/api?action=register" method="post">
                    <ul>
                        <li>
                            <h1><fmt:message key="register.h1" bundle="${title}"/></h1>
                        </li>
                        <li>
                            <label for="name"><fmt:message key="register.form.name" bundle="${inscription}"/>: </label>
                            <input type="text" name="name" id="name" placeholder="<fmt:message key="register.form.name.placeholder" bundle="${inscription}"/>...">
                            <c:if test="${not empty requestScope.name_error_message}">
                                <div class="error"><fmt:message key="error.empty.name" bundle="${errors}"/></div>
                            </c:if>
                        </li>
                        <li>
                            <label for="surname"><fmt:message key="register.form.surname" bundle="${inscription}"/>: </label>
                            <input type="text" name="surname" id="surname" placeholder="<fmt:message key="register.form.surname.placeholder" bundle="${inscription}"/>...">
                            <c:if test="${not empty requestScope.surname_error_message}">
                                <div class="error"><fmt:message key="error.empty.surname" bundle="${errors}"/>!</div>
                            </c:if>
                        </li>
                        <li>
                            <label for="login">
                                <fmt:message key="register.form.login" bundle="${inscription}"/>:
                            </label>
                            <input type="text" name="login" id="login" placeholder="<fmt:message key="register.form.login.placeholder" bundle="${inscription}"/>...">
                            <c:if test="${not empty requestScope.login_error_message}">
                                <div class="error"><fmt:message key="error.empty.login" bundle="${errors}"/></div>
                            </c:if>
                        </li>
                        <li>
                            <label for="pass">
                                <fmt:message key="register.form.password" bundle="${inscription}"/>:
                            </label>
                            <input type="password" name="pass" id="pass" placeholder="<fmt:message key="register.form.password.placeholder" bundle="${inscription}"/>">
                            <c:if test="${not empty requestScope.password_error_message}">
                                <div class="error"><fmt:message key="error.empty.password" bundle="${errors}"/></div>
                            </c:if>
                            <c:if test="${not empty requestScope.password_length_error_message}">
                                <div class="error"><fmt:message key="error.length.password" bundle="${errors}"/></div>
                            </c:if>
                        </li>
                        <li>
                            <label for="role"><fmt:message key="register.form.role" bundle="${inscription}"/>: </label>

                            <select size="1" name="role" id="role">
                                <option value="MANAGER"><fmt:message key="register.form.role.option.manager" bundle="${inscription}"/></option>
                                <option value="ADMIN"><fmt:message key="register.form.role.option.admin" bundle="${inscription}"/></option>
                                <option value="CASHIER"><fmt:message key="register.form.role.option.cashier" bundle="${inscription}"/></option>
                                <option value="CHIEF_CASHIER"><fmt:message key="register.form.role.option.cashier.chief" bundle="${inscription}"/></option>
                            </select>
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
