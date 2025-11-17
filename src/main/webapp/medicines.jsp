<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<div class="container mt-3">
  <div class="d-flex justify-content-between align-items-center">
    <h4>Medicines</h4>
    <a class="btn btn-primary" href="medicine?action=add">Add Medicine</a>
  </div>

  <table class="table mt-3">
    <thead><tr><th>Name</th><th>Category</th><th>Qty</th><th>MRP</th><th>Action</th></tr></thead>
    <tbody>
    <c:forEach var="m" items="${medicines}">
      <tr>
        <td>${m.name}</td>
        <td>${m.category}</td>
        <td>${m.quantity}</td>
        <td>${m.mrp}</td>
        <td>
          <a class="btn btn-sm btn-outline-secondary" href="medicine?action=edit&id=${m.medicineId}">Edit</a>
          <a class="btn btn-sm btn-danger" href="medicine?action=delete&id=${m.medicineId}">Delete</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
