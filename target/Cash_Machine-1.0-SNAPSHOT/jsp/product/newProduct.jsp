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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="user.manager.product.new.title" bundle="${title}"/></title>
</head>
<style>
    <%@include file='../../style/main.css' %>
    <%@include file='../../style/formStyle.css' %>
    <%@include file='../../style/leftNavigation.css' %>
</style>
<body>
    <ul class="menul">
        <li><a href="${pageContext.request.contextPath}/api?action=manager"><fmt:message key="user.role.manager" bundle="${buttons}"/></a></li>
    </ul>
    <ul class="menur">
        <li><a href="${pageContext.request.contextPath}/api?action=logout"><fmt:message key="menu.out" bundle="${buttons}"/></a></li>
        <li><fmt:message key="lang.ru" bundle="${buttons}"/></li>
        <li><fmt:message key="lang.en" bundle="${buttons}"/></li>
    </ul>
    <div class="center">
        <div class="fixed-overlay fixed-overlay__modal">
            <div class="modal">
                <div class="modal_container">
                    <c:if test="${not empty requestScope.product_error_message}">
                        <div class="error"><fmt:message key="error.product" bundle="${errors}"/></div>
                    </c:if>

                    <form class="block" action="${pageContext.request.contextPath}/api?action=manager/addProduct" method="post" name="newProductForm">
                        <ul>
                            <li>
                                <h1><fmt:message key="user.manager.product.new.h1" bundle="${title}"/></h1>
                            </li>
                            <li>
                                <label for="code"><fmt:message key="user.manager.product.new.form.code" bundle="${inscription}"/>: </label>
                                <input type="text" name="code" id="code" placeholder="<fmt:message key="user.manager.product.new.form.code.placeholder" bundle="${inscription}"/>">
                                <c:if test="${not empty requestScope.code_error_message}">
                                    <div class="error"><fmt:message key="error.empty.code" bundle="${errors}"/></div>
                                </c:if>
                                <c:if test="${not empty requestScope.code_int_error_message}">
                                    <div class="error"><fmt:message key="error.int.code" bundle="${errors}"/></div>
                                </c:if>
                            </li>
                            <li>
                                <label for="name"><fmt:message key="user.manager.product.new.form.name" bundle="${inscription}"/>: </label>
                                <input type="text" name="name" id="name">
                                <c:if test="${not empty requestScope.name_error_message}">
                                    <div class="error"><fmt:message key="error.empty.name" bundle="${errors}"/></div>
                                </c:if>
                            </li>
                            <li>
                                <label for="price"><fmt:message key="user.manager.product.new.form.price" bundle="${inscription}"/>: </label>
                                <input type="text" name="price" id="price" placeholder="<fmt:message key="user.manager.product.new.form.price.placeholder" bundle="${inscription}"/>">
                                <c:if test="${not empty requestScope.price_error_message}">
                                    <div class="error"><fmt:message key="error.empty.price" bundle="${errors}"/></div>
                                </c:if>
                                <c:if test="${not empty requestScope.price_int_error_message}">
                                    <div class="error"><fmt:message key="error.number.price" bundle="${errors}"/></div>
                                </c:if>
                            </li>
                            <li>
                                <label for="quantity"><fmt:message key="user.manager.product.new.form.quantity" bundle="${inscription}"/>: </label>
                                <input type="text" name="quantity" id="quantity" value="0" placeholder="<fmt:message key="user.manager.product.new.form.code.placeholder" bundle="${inscription}"/>">
                                <c:if test="${not empty requestScope.quantity_error_message}">
                                    <div class="error"><fmt:message key="error.empty.quantity" bundle="${errors}"/></div>
                                </c:if>
                                <c:if test="${not empty requestScope.quantity_int_error_message}">
                                    <div class="error"><fmt:message key="error.int.quantity" bundle="${errors}"/></div>
                                </c:if>
                            </li>
                            <li>
                                <label for="weight"><fmt:message key="user.manager.product.new.form.weight" bundle="${inscription}"/>: </label>
                                <input type="text" name="weight" id="weight" value="0" placeholder="<fmt:message key="user.manager.product.new.form.price.placeholder" bundle="${inscription}"/>">
                                <c:if test="${not empty requestScope.weight_error_message}">
                                    <div class="error"><fmt:message key="error.empty.weight" bundle="${errors}"/></div>
                                </c:if>
                                <c:if test="${not empty requestScope.weight_number_error_message}">
                                    <div class="error"><fmt:message key="error.number.weight" bundle="${errors}"/></div>
                                </c:if>
                            </li>
                            <li>
                                <label for="weightSold"><fmt:message key="user.manager.product.new.form.sold" bundle="${inscription}"/> </label>

                                <select size="1" name="weightSold" id="weightSold">
                                    <option name="no"><fmt:message key="user.manager.product.new.form.sold.no" bundle="${inscription}"/></option>
                                    <option name="yes"><fmt:message key="user.manager.product.new.form.sold.yes" bundle="${inscription}"/></option>
                                </select>
                                <c:if test="${not empty requestScope.how_sold_weight_error_message}">
                                    <div class="error"><fmt:message key="error.sold.weight" bundle="${errors}"/></div>
                                </c:if>
                                <c:if test="${not empty requestScope.how_sold_quantity_error_message}">
                                    <div class="error"><fmt:message key="error.sold.quantity" bundle="${errors}"/></div>
                                </c:if>
                            </li>
                            <li>
                                    <button type="submit" name="submit"><fmt:message key="button.manager.product.new" bundle="${buttons}"/></button>

                            </li>
                        </ul>
                    </form>

            </div>
        </div>
        </div>
    </div>
</body>
</html>
