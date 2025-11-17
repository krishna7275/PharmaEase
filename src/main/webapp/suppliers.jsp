<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />
<div class="container mt-3">
    <div class="d-flex justify-content-between">
        <h4>Suppliers</h4>
        <a class="btn btn-primary" href="supplier?action=add">Add Supplier</a>
    </div>

    <table class="table mt-3">
        <thead><tr><th>Name</th><th>Contact</th><th>Phone</th><th>Action</th></tr></thead>
        <tbody>
        <c:forEach var="s" items="${suppliers}">
            <tr>
                <td>${s.name}</td>
                <td>${s.contactInfo}</td>
                <td>${s.phone}</td>
                <td>
                    <a class="btn btn-sm btn-outline-secondary" href="supplier?action=edit&id=${s.supplierId}">Edit</a>
                    <a class="btn btn-sm btn-danger" href="supplier?action=delete&id=${s.supplierId}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
