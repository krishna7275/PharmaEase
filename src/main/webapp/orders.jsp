<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />
<div class="container mt-3">
    <h4>Orders</h4>
    <table class="table">
        <thead><tr><th>Order ID</th><th>Customer</th><th>Date</th><th>Total</th><th>Action</th></tr></thead>
        <tbody>
        <c:forEach var="o" items="${orders}">
            <tr>
                <td>${o.orderId}</td>
                <td>${o.customerId}</td>
                <td>${o.orderDate}</td>
                <td>${o.totalAmount}</td>
                <td><a href="order?action=view&id=${o.orderId}" class="btn btn-sm btn-outline-primary">View</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
