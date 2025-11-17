<%@ page import="java.util.List" %>
<%@ page import="com.pharmaease.model.Medicine" %>
<%@ page import="com.pharmaease.model.Pharmacist" %>
<%
    Pharmacist user = (Pharmacist) session.getAttribute("pharmacist");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Medicine> medicines = (List<Medicine>) request.getAttribute("medList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Medicines | PharmaEase</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap + FontAwesome -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">

  <style>
    :root { --pharma-teal:#009688; --pharma-light:#f8fcfa; }
    body { background:var(--pharma-light); font-family:'Poppins'; }

    /* Sidebar styling (same as dashboard) */
    .sidebar {
      position: fixed; top: 0; left: 0; bottom: 0;
      width: 230px; background: #fff;
      border-right: 1px solid #e0e0e0;
      padding: 1.3rem .8rem;
      box-shadow: 0 2px 14px rgba(0,184,148,0.06);
    }
    .sidebar .logo { font-size:1.4rem; font-weight:700; color:var(--pharma-teal); }
    .nav-link { color:#444; border-radius:7px; padding:10px 14px; }
    .nav-link:hover, .nav-link.active { background:linear-gradient(90deg,#e0f7fa 40%,#a7ffeb 100%); color:var(--pharma-teal); }

    main { margin-left:230px; padding:2rem; }

    .search-bar {
      border-radius: 8px; border: 1.5px solid #e0e0e0;
      max-width: 320px; padding-left: 36px;
      background: #fff url('https://cdn.jsdelivr.net/npm/bootstrap-icons/icons/search.svg') 10px 50% no-repeat;
      background-size: 18px;
    }

    .table th { background:#e0f7fa; color:#009688; font-weight:500; }
    .btn-action { color:#009688; background:#e0f7fa; border:none; padding:5px 9px; border-radius:5px; }
    .btn-action:hover { background:#a7ffeb; }
    .btn-danger { background:#ffebee; color:#d84315; }

    .add-btn {
      background: linear-gradient(90deg,#00b894 60%,#009688 100%);
      color:#fff; padding:8px 18px; border:none; border-radius:8px;
      font-weight:500;
    }
  </style>
</head>

<body>

  <!-- SIDEBAR (DROPDOWN ENABLED) -->
  <%@ include file="components/sidebar.jsp" %>

  <!-- MAIN CONTENT -->
  <main>

    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2 style="color:#009688;font-weight:600;">Medicines</h2>
      <button class="add-btn" data-bs-toggle="modal" data-bs-target="#addMedicineModal">
        <i class="fas fa-plus"></i> Add Medicine
      </button>
    </div>

    <!-- Search -->
    <input type="text" id="searchMed" class="form-control search-bar mb-3" placeholder="Search medicines...">

    <!-- TABLE -->
    <div class="table-responsive">
      <table class="table align-middle" id="medTable">
        <thead>
          <tr>
            <th>Name</th>
            <th>Generic</th>
            <th>Category</th>
            <th>Form</th>
            <th>Strength</th>
            <th>MRP</th>
            <th>Qty</th>
            <th>Reorder</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          <% if (medicines != null && !medicines.isEmpty()) {
               for (Medicine m : medicines) { %>
                 <tr>
                   <td><%= m.getName() %></td>
                   <td><%= m.getGenericName() %></td>
                   <td><%= m.getCategory() %></td>
                   <td><%= m.getForm() %></td>
                   <td><%= m.getStrength() %></td>
                   <td>₹<%= m.getMrp() %></td>
                   <td><%= m.getQty() %></td>
                   <td><%= m.getReorderLevel() %></td>
                   <td>
                     <a href="MedicinesServlet?action=edit&id=<%= m.getId() %>" class="btn-action"><i class="fas fa-edit"></i></a>
                     <a href="MedicinesServlet?action=delete&id=<%= m.getId() %>" class="btn-danger"><i class="fas fa-trash-alt"></i></a>
                   </td>
                 </tr>
          <% } } else { %>
            <tr><td colspan="9" class="text-center p-3 text-muted">No medicines found.</td></tr>
          <% } %>
        </tbody>
      </table>
    </div>

  </main>

  <!-- ADD MEDICINE MODAL -->
  <div class="modal fade" id="addMedicineModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="MedicinesServlet" method="post">

          <div class="modal-header">
            <h5 class="modal-title">Add Medicine</h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <div class="modal-body">

            <input type="hidden" name="action" value="add">

            <div class="mb-2">
              <label>Medicine Name</label>
              <input type="text" name="name" class="form-control" required>
            </div>

            <div class="mb-2">
              <label>Generic Name</label>
              <input type="text" name="generic" class="form-control">
            </div>

            <div class="mb-2">
              <label>Category</label>
              <input type="text" name="category" class="form-control">
            </div>

            <div class="mb-2">
              <label>Form</label>
              <input type="text" name="form" class="form-control">
            </div>

            <div class="mb-2">
              <label>Strength</label>
              <input type="text" name="strength" class="form-control">
            </div>

            <div class="row">
              <div class="col-md-6 mb-2">
                <label>MRP</label>
                <input type="number" step="0.01" name="mrp" class="form-control">
              </div>

              <div class="col-md-6 mb-2">
                <label>Quantity</label>
                <input type="number" name="qty" class="form-control">
              </div>
            </div>

            <div class="mb-2">
              <label>Reorder Level</label>
              <input type="number" name="reorder" class="form-control">
            </div>

          </div>

          <div class="modal-footer">
            <button class="btn btn-primary">Save</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          </div>

        </form>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <script>
    // SEARCH
    document.getElementById('searchMed').addEventListener('keyup', function(){
      let query = this.value.toLowerCase();
      document.querySelectorAll('#medTable tbody tr').forEach(row => {
        row.style.display = row.textContent.toLowerCase().includes(query) ? '' : 'none';
      });
    });
  </script>

</body>
</html>
