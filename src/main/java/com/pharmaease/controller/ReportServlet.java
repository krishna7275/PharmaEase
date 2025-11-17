package com.pharmaease.controller;

import com.pharmaease.config.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

    private Connection conn;

    @Override
    public void init() {
        conn = DBConnection.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");

        if (type == null) {
            request.getRequestDispatcher("reports.jsp").forward(request, response);
            return;
        }

        switch (type) {

            case "lowStock":
                loadLowStockReport(request, response);
                break;

            case "expiry":
                loadExpiryReport(request, response);
                break;

            case "dailySales":
                loadDailySalesReport(request, response);
                break;

            case "medicineSales":
                loadMedicineSalesReport(request, response);
                break;

            default:
                request.getRequestDispatcher("reports.jsp").forward(request, response);
        }
    }

    // =========================================
    // LOW STOCK REPORT
    // =========================================
    private void loadLowStockReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<HashMap<String, Object>> list = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM LowStockMedicines");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("medicineId", rs.getInt("medicineId"));
                row.put("name", rs.getString("name"));
                row.put("category", rs.getString("category"));
                row.put("quantity", rs.getInt("quantity"));
                row.put("minStockLevel", rs.getInt("minStockLevel"));
                row.put("reorderQuantity", rs.getInt("reorderQuantity"));
                list.add(row);
            }

        } catch (SQLException e) {
            System.out.println("❌ LowStock Report Error: " + e.getMessage());
        }

        request.setAttribute("lowStockList", list);
        request.getRequestDispatcher("reports.jsp").forward(request, response);
    }

    // =========================================
    // EXPIRY REPORT (Next 90 days)
    // =========================================
    private void loadExpiryReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<HashMap<String, Object>> list = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ExpiringSoonMedicines");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("medicineName", rs.getString("medicineName"));
                row.put("batchNumber", rs.getString("batchNumber"));
                row.put("quantity", rs.getInt("quantity"));
                row.put("expiryDate", rs.getDate("expiryDate"));
                row.put("daysToExpiry", rs.getInt("daysToExpiry"));
                row.put("supplierName", rs.getString("supplierName"));
                list.add(row);
            }

        } catch (SQLException e) {
            System.out.println("❌ Expiry Report Error: " + e.getMessage());
        }

        request.setAttribute("expiryList", list);
        request.getRequestDispatcher("reports.jsp").forward(request, response);
    }

    // =========================================
    // DAILY SALES SUMMARY
    // =========================================
    private void loadDailySalesReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<HashMap<String, Object>> list = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM DailySalesSummary");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("saleDate", rs.getDate("saleDate"));
                row.put("totalOrders", rs.getInt("totalOrders"));
                row.put("totalRevenue", rs.getDouble("totalRevenue"));
                row.put("avgOrderValue", rs.getDouble("avgOrderValue"));
                list.add(row);
            }

        } catch (SQLException e) {
            System.out.println("❌ DailySales Report Error: " + e.getMessage());
        }

        request.setAttribute("dailySalesList", list);
        request.getRequestDispatcher("reports.jsp").forward(request, response);
    }


    // =========================================
    // MEDICINE SALES REPORT
    // =========================================
    private void loadMedicineSalesReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<HashMap<String, Object>> list = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM MedicineSalesReport");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("medicineName", rs.getString("medicineName"));
                row.put("category", rs.getString("category"));
                row.put("totalQuantitySold", rs.getInt("totalQuantitySold"));
                row.put("totalRevenue", rs.getDouble("totalRevenue"));
                row.put("numberOfOrders", rs.getInt("numberOfOrders"));
                list.add(row);
            }

        } catch (SQLException e) {
            System.out.println("❌ MedicineSales Report Error: " + e.getMessage());
        }

        request.setAttribute("medicineSalesList", list);
        request.getRequestDispatcher("reports.jsp").forward(request, response);
    }


    // =========================================
    // CUSTOM SALES REPORT (DATE RANGE)
    // =========================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");

        List<HashMap<String, Object>> list = new ArrayList<>();

        try {
            CallableStatement stmt = conn.prepareCall("{CALL GetSalesReport(?, ?)}");

            stmt.setString(1, start);
            stmt.setString(2, end);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("orderDate", rs.getDate("orderDate"));
                row.put("totalOrders", rs.getInt("totalOrders"));
                row.put("totalRevenue", rs.getDouble("totalRevenue"));
                row.put("totalTax", rs.getDouble("totalTax"));
                list.add(row);
            }

        } catch (SQLException e) {
            System.out.println("❌ Custom Sales Report Error: " + e.getMessage());
        }

        request.setAttribute("customReportList", list);
        request.setAttribute("startDate", start);
        request.setAttribute("endDate", end);

        request.getRequestDispatcher("reports.jsp").forward(request, response);
    }
}
