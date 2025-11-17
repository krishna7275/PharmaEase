<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<div class="container mt-4">
  <h3>Dashboard</h3>

  <div class="row g-3 mt-3">
    <div class="col-md-3">
      <div class="card p-3">
        <div class="h6">Medicines</div>
        <div class="fs-4">${medicineCount}</div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card p-3">
        <div class="h6">Suppliers</div>
        <div class="fs-4">${supplierCount}</div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card p-3">
        <div class="h6">Customers</div>
        <div class="fs-4">${customerCount}</div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card p-3">
        <div class="h6">Low stock</div>
        <div class="fs-4">${lowStockCount}</div>
      </div>
    </div>
  </div>

  <div class="mt-4">
    <h5>Recent Medicines</h5>
    <table class="table table-sm">
      <thead><tr><th>Name</th><th>Category</th><th>Qty</th><th>MRP</th></tr></thead>
      <tbody>
      <c:forEach var="m" items="${medicines}">
        <tr>
          <td>${m.name}</td>
          <td>${m.category}</td>
          <td>${m.quantity}</td>
          <td>${m.mrp}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
