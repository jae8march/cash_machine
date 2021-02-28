<%--
Tag determines the role of users and displays it in the language selected by the user.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="user_role" type="com.company.app.dao.entity.enumeration.UserRole" required="true" %>

<fmt:setBundle basename="title"/>
<c:if test="${user_role=='MANAGER'}">
    <fmt:message key="user.manager.role" var="manager"/>
    ${manager}
</c:if>

<c:if test="${user_role=='ADMIN'}">
    <fmt:message key="user.admin.role" var="admin"/>
    ${admin}
</c:if>

<c:if test="${user_role=='CASHIER'}">
    <fmt:message key="user.cashier.role" var="cashier"/>
    ${cashier}
</c:if>

<c:if test="${user_role=='CHIEF_CASHIER'}">
    <fmt:message key="user.cashier.chief.role" var="chCashier"/>
    ${chCashier}
</c:if>

<c:if test="${user_role=='UNKNOWN'}">
    <fmt:message key="user.unknown.role" var="unknown"/>
    ${unknown}
</c:if>

<c:if test="${empty user_role}">
    <fmt:message key="user.unknown.role" var="unknown"/>
    ${unknown}
</c:if>