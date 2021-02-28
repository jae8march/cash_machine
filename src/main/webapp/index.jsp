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
<html lang="${language}">
<head>
    <title><fmt:message key="main.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='style/main.css' %>
    <%@include file='style/leftNavigation.css' %>
</style>
    <body>
        <ul class="menul">
            <li>
                <a href="${pageContext.request.contextPath}/">
                    <fmt:message key="menu.main" bundle="${buttons}"/>
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
                    <form class="language_form">
                        <select id="language" name="language">
                            <option value="en" ${language == 'en' ? 'selected' : ''}>
                                <fmt:message key="lang.en" bundle="${buttons}"/>
                            </option>
                            <option value="ru" ${language == 'ru' ? 'selected' : ''}>
                                <fmt:message key="lang.ru" bundle="${buttons}"/>
                            </option>
                        </select>
                        <button class="inside" type="submit" value="submit" style="display: ruby-text;">+</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="footer">
            <tlds:tag/>
        </div>
    </body>
</html>
