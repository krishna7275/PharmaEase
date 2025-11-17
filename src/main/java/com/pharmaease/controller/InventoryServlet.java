package com.pharmaease.controller;

import com.pharmaease.dao.InventoryDAO;
import com.pharmaease.dao.MedicineDAO;
import com.pharmaease.model.Inventory;
import com.pharmaease.model.Medicine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {

    private InventoryDAO inventoryDAO;
    private MedicineDAO medicineDAO;

    @Override
    public void init() {
        inventoryDAO = new InventoryDAO();
        medicineDAO = new MedicineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Medicine> medicines = medicineDAO.getAll();
        List<Inventory> inventoryList = inventoryDAO.getAll();

        request.setAttribute("medicines", medicines);
        request.setAttribute("inventoryList", inventoryList);

        request.getRequestDispatcher("inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            int medicineId = Integer.parseInt(request.getParameter("medicineId"));
            int newQty = Integer.parseInt(request.getParameter("quantity"));

            inventoryDAO.updateStock(medicineId, newQty);
        }

        response.sendRedirect("inventory");
    }
}
