<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />

<fmt:setBundle basename="buttons" var="buttons"/>
<fmt:setBundle basename="title" var="title"/>
<fmt:setBundle basename="inscription" var="inscription"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="main.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='style/main.css' %>
</style>
    <body>
        <ul class="menul">
            <li><a href="${pageContext.request.contextPath}/"><fmt:message key="menu.main" bundle="${buttons}"/></a></li>
        </ul>
        <ul class="menur">


            <form name="language" name="language">
<%--            <li><tags:language curr_lang="${locale}" curr_uri="${pageContext.request.requestURI}"/></li>--%>
            <li><a href="" value="ru" ${language == 'ru' ? 'selected' : ''}><fmt:message key="lang.ru" bundle="${buttons}"/></a></li>
            <li><a href="" value="en" ${language == 'en' ? 'selected' : ''}><fmt:message key="lang.en" bundle="${buttons}"/></a></li>
            </form>
        </ul>
        <br>

        <div class="fixed-overlay fixed-overlay__modal">
            <div class="modal">
                <div class="modal_container">
                    <h1><fmt:message key="main.h1" bundle="${title}"/></h1>
                    <p><fmt:message key="main.p" bundle="${inscription}"/></p>

                    <form action="${pageContext.request.contextPath}/jsp/user/register.jsp" method="post">
                        <button type="submit">
                            <fmt:message key="button.register" bundle="${buttons}"/>
                        </button>
                    </form>

                    <form action="${pageContext.request.contextPath}/jsp/user/login.jsp" method="post">
                        <button type="submit" name="submit">
                            <fmt:message key="button.sign" bundle="${buttons}"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
