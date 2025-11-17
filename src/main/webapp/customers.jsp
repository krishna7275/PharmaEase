<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<div class="container mt-3">
    <div class="d-flex justify-content-between align-items-center">
        <h4>Customers</h4>
        <a class="btn btn-primary" href="customer?action=add">Add Customer</a>
    </div>

    <table class="table mt-3">
        <thead><tr><th>Name</th><th>Phone</th><th>Email</th><th>Action</th></tr></thead>
        <tbody>
        <c:forEach var="c" items="${customers}">
            <tr>
                <td>${c.name}</td>
                <td>${c.phone}</td>
                <td>${c.email}</td>
                <td>
                    <a class="btn btn-sm btn-outline-secondary" href="customer?action=edit&id=${c.customerId}">Edit</a>
                    <a class="btn btn-sm btn-danger" href="customer?action=delete&id=${c.customerId}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
