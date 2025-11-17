<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<div class="container mt-3">
    <h4>Billing</h4>

    <form id="addItemForm" action="billing" method="post" class="row g-2 align-items-end">
        <input type="hidden" name="action" value="add"/>
        <div class="col-md-6">
            <label>Medicine</label>
            <select id="selMed" name="medicineId" class="form-select">
                <c:forEach var="m" items="${medicines}">
                    <option value="${m.medicineId}" data-price="${m.mrp}">${m.name} (${m.quantity}) - ₹${m.mrp}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <label>Qty</label>
            <input type="number" name="quantity" value="1" class="form-control" min="1"/>
        </div>
        <div class="col-md-2">
            <label>&nbsp;</label>
            <button class="btn btn-primary w-100" type="submit">Add</button>
        </div>
    </form>

    <hr/>

    <h5>Cart</h5>
    <table class="table">
        <thead><tr><th>#</th><th>Item</th><th>Qty</th><th>Rate</th><th>Subtotal</th><th>Action</th></tr></thead>
        <tbody>
        <c:forEach var="item" items="${sessionScope.cart}" varStatus="st">
            <tr>
                <td>${st.index + 1}</td>
                <td>${item.medicineId}</td>
                <td>${item.quantity}</td>
                <td>${item.unitPrice}</td>
                <td>${item.subtotal}</td>
                <td>
                    <form action="billing" method="post" style="display:inline">
                        <input type="hidden" name="action" value="remove"/>
                        <input type="hidden" name="index" value="${st.index}"/>
                        <button class="btn btn-sm btn-danger">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form action="billing" method="post">
        <input type="hidden" name="action" value="complete"/>
        <div class="row">
            <div class="col-md-4">
                <label>Customer</label>
                <select name="customerId" class="form-select">
                    <c:forEach var="c" items="${customers}">
                        <option value="${c.customerId}">${c.name} (${c.phone})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <label>Payment Method</label>
                <select name="paymentMethod" class="form-select">
                    <option>Cash</option><option>Card</option><option>UPI</option>
                </select>
            </div>
            <div class="col-md-4 d-flex align-items-end">
                <button class="btn btn-success w-100" type="submit">Complete Sale</button>
            </div>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
