<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />
<div class="container mt-3">
    <h4>Order #${order.orderId}</h4>
    <p>Customer: ${order.customerId} | Date: ${order.orderDate}</p>
    <table class="table">
        <thead><tr><th>Item</th><th>Qty</th><th>Price</th><th>Subtotal</th></tr></thead>
        <tbody>
        <c:forEach var="it" items="${items}">
            <tr>
                <td>${it.medicineId}</td>
                <td>${it.quantity}</td>
                <td>${it.unitPrice}</td>
                <td>${it.subtotal}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
