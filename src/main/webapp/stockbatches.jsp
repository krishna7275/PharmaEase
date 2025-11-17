<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<div class="container mt-3">
    <h4>Batches for: <c:out value="${medicineName}" /></h4>

    <table class="table mt-3">
        <thead><tr><th>Batch</th><th>Qty</th><th>Expiry</th><th>Added</th></tr></thead>
        <tbody>
        <c:forEach var="b" items="${batches}">
            <tr>
                <td>${b.batchNumber}</td>
                <td>${b.quantity}</td>
                <td>${b.expiryDate}</td>
                <td>${b.addedDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-secondary" href="inventory">Back</a>
</div>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
