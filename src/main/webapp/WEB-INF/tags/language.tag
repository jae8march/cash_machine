<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="curr_lang" type="java.util.Locale" required="true" %>
<%@ attribute name="curr_uri" type="java.lang.String" required="true" %>
<fmt:setBundle basename="buttons"/>

<c:choose>
    <c:when test="${curr_lang == 'ru'}">
        <fmt:setLocale value="ru"/>
        <fmt:setBundle basename="title"/>
        <fmt:setBundle basename="table"/>
        <fmt:setBundle basename="inscription"/>
        <fmt:setBundle basename="errors"/>
        <fmt:setBundle basename="buttons"/>
        <form action="" method="post">
            <input type="hidden" name="actionName" value="change_lang">
            <input type="hidden" name="from" value="${curr_uri}">
            <input type="hidden" name="locale" value="en"/>
            <input type="submit" value="<fmt:message key="lang.en" var="buttons"/>"/>
            <br>
        </form>
    </c:when>

    <c:otherwise>
        <fmt:setLocale value="en"/>
        <fmt:setLocale value="ru"/>
        <fmt:setBundle basename="title"/>
        <fmt:setBundle basename="table"/>
        <fmt:setBundle basename="inscription"/>
        <fmt:setBundle basename="errors"/>
        <fmt:setBundle basename="buttons"/>
        <form action="" method="post">
            <input type="hidden" name="actionName" value="change_lang">
            <input type="hidden" name="from" value="${curr_uri}">
            <input type="hidden" name="locale" value="ru"/>
            <input type="submit" value="<fmt:message key="lang.ru" var="buttons"/>"/><br>
        </form>
    </c:otherwise>
</c:choose>
