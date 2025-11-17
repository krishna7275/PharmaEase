<%@ page import="com.pharmaease.model.Pharmacist" %>
<%@ page session="true" %>
<%
    Pharmacist user = (Pharmacist) session.getAttribute("pharmacist");
    if (user == null) {
        response.sendRedirect("login.jsp?error=session_expired");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Dashboard | PharmaEase</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
  <style>
    :root { --pharma-teal:#009688; --pharma-light:#f8fcfa; }
    body{ background:var(--pharma-light); font-family:'Poppins',sans-serif; color:#222; }
    .sidebar{ position:fixed; top:0;left:0;bottom:0;width:230px;background:#fff;border-right:1px solid #e0e0e0;padding:1.3rem .8rem;box-shadow:0 2px 14px rgba(0,184,148,0.06); }
    .sidebar .logo{font-size:1.4rem;font-weight:700;color:var(--pharma-teal);margin-bottom:22px;display:flex;align-items:center;gap:10px;}
    .nav .nav-link{ color:#444;border-radius:7px;padding:10px 14px; display:flex;align-items:center;gap:12px; }
    .nav .nav-link.active, .nav .nav-link:hover { background:linear-gradient(90deg,#e0f7fa 40%,#a7ffeb 100%); color:var(--pharma-teal); font-weight:500; }
    .sidebar-toggler{ display:none; position:fixed; top:16px; left:16px; background:var(--pharma-teal); color:#fff; border:none; width:42px; height:42px; border-radius:50%; z-index:1100; }
    main{ margin-left:230px; padding:2rem; transition:.2s; }
    .overview-card{ background:#fff;border-radius:13px;padding:1.2rem 1rem; box-shadow:0 4px 16px rgba(0,184,148,0.06); display:flex; align-items:center; gap:18px; }
    .chart-box{ background:#fff;border-radius:13px;padding:1.4rem; box-shadow:0 4px 16px rgba(0,184,148,0.06); min-height:320px; }
    @media(max-width:800px){ .sidebar{ left:-230px;} .sidebar.open{ left:0;} main{ margin-left:0; padding:1rem; } .sidebar-toggler{ display:block; } }
  </style>
</head>
<body>

  <!-- SIDEBAR with dropdowns -->
  <div class="sidebar open" id="sidebar">
    <div class="logo"><i class="fas fa-capsules"></i> PharmaEase</div>
    <nav class="nav flex-column">
      <a class="nav-link active" href="DashboardServlet"><i class="fas fa-home"></i> Dashboard</a>

      <!-- Medicines dropdown -->
      <a class="nav-link" data-bs-toggle="collapse" href="#medDrop" role="button" aria-expanded="false" aria-controls="medDrop">
        <i class="fas fa-pills"></i> Medicines <i class="fas fa-caret-down ms-auto"></i>
      </a>
      <div class="collapse ps-3" id="medDrop">
        <a class="nav-link" href="MedicinesServlet?action=list">List Medicines</a>
        <a class="nav-link" href="MedicinesServlet?action=addPage">Add Medicine</a>
      </div>

      <!-- Inventory dropdown -->
      <a class="nav-link" data-bs-toggle="collapse" href="#invDrop" role="button" aria-expanded="false" aria-controls="invDrop">
        <i class="fas fa-boxes-stacked"></i> Inventory <i class="fas fa-caret-down ms-auto"></i>
      </a>
      <div class="collapse ps-3" id="invDrop">
        <a class="nav-link" href="InventoryServlet?action=list">Stock List</a>
        <a class="nav-link" href="InventoryServlet?action=addPage">Add Stock</a>
      </div>

      <a class="nav-link" href="BillingServlet?action=bill"><i class="fas fa-cash-register"></i> Billing</a>

      <a class="nav-link" data-bs-toggle="collapse" href="#supDrop" role="button" aria-expanded="false" aria-controls="supDrop">
        <i class="fas fa-truck"></i> Suppliers <i class="fas fa-caret-down ms-auto"></i>
      </a>
      <div class="collapse ps-3" id="supDrop">
        <a class="nav-link" href="SuppliersServlet?action=list">Suppliers List</a>
        <a class="nav-link" href="SuppliersServlet?action=addPage">Add Supplier</a>
      </div>

      <a class="nav-link" href="ReportsServlet"><i class="fas fa-chart-bar"></i> Reports</a>

      <a class="nav-link text-danger" href="LogoutServlet"><i class="fas fa-lock"></i> Logout</a>
    </nav>
  </div>

  <button class="sidebar-toggler" id="sidebarToggler"><i class="fas fa-bars"></i></button>

  <main>
    <h2 style="color:#009688;font-weight:600;">Welcome, <%= user.getName() %> 👋</h2>

    <!-- Dashboard numbers from servlet attributes -->
    <div class="row g-3 mb-4">
      <div class="col-md-3 col-6">
        <div class="overview-card">
          <i class="fas fa-pills"></i>
          <div>
            <div class="card-title">Total Medicines</div>
            <div class="card-value" id="totalMeds"><%= request.getAttribute("totalMeds") %></div>
          </div>
        </div>
      </div>
      <div class="col-md-3 col-6">
        <div class="overview-card">
          <i class="fas fa-cash-register"></i>
          <div>
            <div class="card-title">Total Sales (Count)</div>
            <div class="card-value" id="totalSales"><%= request.getAttribute("totalSales") %></div>
          </div>
        </div>
      </div>
      <div class="col-md-3 col-6">
        <div class="overview-card">
          <i class="fas fa-triangle-exclamation"></i>
          <div>
            <div class="card-title">Low Stock</div>
            <div class="card-value" id="lowStock"><%= request.getAttribute("lowStock") %></div>
          </div>
        </div>
      </div>
      <div class="col-md-3 col-6">
        <div class="overview-card">
          <i class="fas fa-truck"></i>
          <div>
            <div class="card-title">Suppliers</div>
            <div class="card-value" id="suppliersCount"><%= request.getAttribute("supplierCount") %></div>
          </div>
        </div>
      </div>
    </div>

    <div class="chart-box">
      <h5 style="color:#009688;font-weight:600;">Sales Overview (Monthly)</h5>
      <canvas id="salesChart" height="90"></canvas>
    </div>

  </main>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <script>
    document.getElementById('sidebarToggler').addEventListener('click', ()=>{
      document.getElementById('sidebar').classList.toggle('open');
    });

    // Chart data supplied by the ReportsServlet endpoint or JSP attributes if you prefer.
    const ctx = document.getElementById('salesChart').getContext('2d');
    // If server sets 'chartLabels' and 'chartData' as JSON strings, parse them. Otherwise demo data:
    const labels = <%= request.getAttribute("chartLabels") != null ? request.getAttribute("chartLabels") : "['Apr','May','Jun','Jul','Aug','Sep','Oct']" %>;
    const chartData = <%= request.getAttribute("chartData") != null ? request.getAttribute("chartData") : "[8200,11000,7900,15300,12800,14800,8900]" %>;

    new Chart(ctx, {
      type: 'line',
      data: { labels: labels, datasets: [{ label:'Sales (₹)', data: chartData, fill:true, backgroundColor:'rgba(0,184,148,0.16)', borderColor:'#00b894', tension:0.35 }]},
      options:{ responsive:true, plugins:{legend:{display:false}}}
    });
  </script>
</body>
</html>
