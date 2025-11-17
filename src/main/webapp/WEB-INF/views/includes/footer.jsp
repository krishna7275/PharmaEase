<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.pharmaease.model.Pharmacist" %>

<%
    Pharmacist user = (Pharmacist) session.getAttribute("user");
    if (user != null) {
        response.sendRedirect("dashboard");
    } else {
        response.sendRedirect("login.jsp");
    }
%>
