<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />
<div class="container mt-3">
    <h4>Reports</h4>

    <div class="row">
        <div class="col-md-4">
            <h6>Low stock</h6>
            <table class="table table-sm">
                <thead><tr><th>Medicine</th><th>Qty</th><th>Reorder</th></tr></thead>
                <tbody>
                <c:forEach var="r" items="${lowStockList}">
                    <tr>
                        <td>${r.name}</td>
                        <td>${r.quantity}</td>
                        <td>${r.reorderQuantity}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-md-8">
            <h6>Expiring soon</h6>
            <table class="table table-sm">
                <thead><tr><th>Medicine</th><th>Batch</th><th>Expiry</th><th>Days</th></tr></thead>
                <tbody>
                <c:forEach var="e" items="${expiryList}">
                    <tr>
                        <td>${e.medicineName}</td>
                        <td>${e.batchNumber}</td>
                        <td>${e.expiryDate}</td>
                        <td>${e.daysToExpiry}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <h6 class="mt-4">Custom sales (by date)</h6>
    <form action="reports" method="post" class="row g-2">
        <div class="col-md-3"><input type="date" name="startDate" class="form-control" required/></div>
        <div class="col-md-3"><input type="date" name="endDate" class="form-control" required/></div>
        <div class="col-md-2"><button class="btn btn-primary" type="submit">Run</button></div>
    </form>

    <c:if test="${not empty customReportList}">
        <h6 class="mt-3">Results</h6>
        <table class="table table-sm">
            <thead><tr><th>Date</th><th>Total Orders</th><th>Total Revenue</th></tr></thead>
            <tbody>
            <c:forEach var="row" items="${customReportList}">
                <tr><td>${row.orderDate}</td><td>${row.totalOrders}</td><td>${row.totalRevenue}</td></tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
