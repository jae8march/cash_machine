<%--
Tag determines the status of checks and displays it in the language selected by the user.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="check_status" type="com.company.app.dao.entity.enumeration.CheckStatus" required="true" %>

<fmt:setBundle basename="title"/>
<c:if test="${check_status=='CREATED'}">
    <fmt:message key="check.status.created" var="created"/>
    ${created}
</c:if>

<c:if test="${check_status=='CLOSED'}">
    <fmt:message key="check.status.closed" var="closed"/>
    ${closed}
</c:if>

<c:if test="${check_status=='CANCELLED'}">
    <fmt:message key="check.status.cancelled" var="cancelled"/>
    ${cancelled}
</c:if>