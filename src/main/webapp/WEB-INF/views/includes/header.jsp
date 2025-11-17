<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Object userObj = session.getAttribute("user");
%>
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">PharmaEase</a>

        <div class="d-flex align-items-center">
            <c:if test="${not empty user}">
                <span class="me-3">Hello, ${user.name}</span>
                <a class="btn btn-outline-danger btn-sm" href="auth?action=logout">Logout</a>
            </c:if>
            <c:if test="${empty user}">
                <a class="btn btn-primary btn-sm" href="login.jsp">Login</a>
            </c:if>
        </div>
    </div>
</nav>
