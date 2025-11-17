package com.pharmaease.controller;

import com.pharmaease.dao.*;
import com.pharmaease.model.Medicine;
import com.pharmaease.model.Supplier;
import com.pharmaease.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private MedicineDAO medicineDAO;
    private SupplierDAO supplierDAO;
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        medicineDAO = new MedicineDAO();
        supplierDAO = new SupplierDAO();
        customerDAO = new CustomerDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Counts
        int medicineCount = medicineDAO.getAll().size();
        int supplierCount = supplierDAO.getAll().size();
        int customerCount = customerDAO.getAll().size();

        // Today analytics
        double todaySales = orderDAO.getTodaySales();
        int todayOrders = orderDAO.getTodayOrderCount();

        // Recent medicines
        List<Medicine> recentMeds = medicineDAO.getLatest(5);

        // Set attributes for JSP
        request.setAttribute("medicineCount", medicineCount);
        request.setAttribute("supplierCount", supplierCount);
        request.setAttribute("customerCount", customerCount);

        request.setAttribute("todaySales", todaySales);
        request.setAttribute("todayOrders", todayOrders);

        request.setAttribute("recentMeds", recentMeds);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
