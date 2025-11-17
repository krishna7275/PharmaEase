package com.pharmaease.controller;

import com.pharmaease.dao.MedicineDAO;
import com.pharmaease.model.Medicine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/medicine")
public class MedicineServlet extends HttpServlet {

    private MedicineDAO medicineDAO;

    @Override
    public void init() {
        medicineDAO = new MedicineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteMedicine(request, response);
                break;
            default:
                listMedicines(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertMedicine(request, response);
                break;
            case "update":
                updateMedicine(request, response);
                break;
        }
    }

    private void listMedicines(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("medicines", medicineDAO.getAll());
        request.getRequestDispatcher("medicines.jsp").forward(request, response);
    }

    private void insertMedicine(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Medicine m = new Medicine();
        m.setName(request.getParameter("name"));
        m.setCategory(request.getParameter("category"));
        m.setDescription(request.getParameter("description"));
        m.setManufacturer(request.getParameter("manufacturer"));
        m.setMrp(Double.parseDouble(request.getParameter("mrp")));
        m.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        m.setMinStockLevel(Integer.parseInt(request.getParameter("minStockLevel")));

        medicineDAO.insert(m);

        response.sendRedirect("medicine");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Medicine m = medicineDAO.getById(id);

        request.setAttribute("medicine", m);
        request.getRequestDispatcher("edit-medicine.jsp").forward(request, response);
    }

    private void updateMedicine(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Medicine m = new Medicine();
        m.setMedicineId(Integer.parseInt(request.getParameter("medicineId")));
        m.setName(request.getParameter("name"));
        m.setCategory(request.getParameter("category"));
        m.setDescription(request.getParameter("description"));
        m.setManufacturer(request.getParameter("manufacturer"));
        m.setMrp(Double.parseDouble(request.getParameter("mrp")));
        m.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        m.setMinStockLevel(Integer.parseInt(request.getParameter("minStockLevel")));

        medicineDAO.update(m);
        response.sendRedirect("medicine");
    }

    private void deleteMedicine(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        medicineDAO.delete(id);

        response.sendRedirect("medicine");
    }
}
