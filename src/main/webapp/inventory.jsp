<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<div class="container mt-3">
    <div class="d-flex justify-content-between align-items-center">
        <h4>Inventory & Batches</h4>
        <a class="btn btn-primary" href="batch?action=add">Add Stock</a>
    </div>

    <table class="table mt-3">
        <thead><tr><th>Medicine</th><th>Batch</th><th>Qty</th><th>Expiry</th><th>Supplier</th><th>Action</th></tr></thead>
        <tbody>
        <c:forEach var="b" items="${batches}">
            <tr>
                <td>${b.medicineName != null ? b.medicineName : b.medicineId}</td>
                <td>${b.batchNumber}</td>
                <td>${b.quantity}</td>
                <td>${b.expiryDate}</td>
                <td>${b.supplierName}</td>
                <td>
                    <a class="btn btn-sm btn-success" href="batch?action=view&medicineId=${b.medicineId}">View</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
