<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="order_status" type="com.company.app.dao.entity.enumeration.CheckStatus" required="true" %>
<%@ attribute name="curr_lang" type="java.util.Locale" required="true" %>

<c:choose>
    <c:when test="${curr_lang == 'ru'}">
        <fmt:setLocale value="ru"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="local"/>

<c:if test="${order_status=='CREATED'}">
    <fmt:message key="check.status.created" var="created"/>
    ${created}
</c:if>

<c:if test="${order_status=='CLOSED'}">
    <fmt:message key="check.status.closed" var="closed"/>
    ${closed}
</c:if>

<c:if test="${order_status=='CANCELLED'}">
    <fmt:message key="check.status.cancelled" var="cancelled"/>
    ${cancelled}
</c:if>