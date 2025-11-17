package com.pharmaease.controller;

import com.pharmaease.dao.SupplierDAO;
import com.pharmaease.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/supplier")
public class SupplierServlet extends HttpServlet {

    private SupplierDAO supplierDAO;

    @Override
    public void init() {
        supplierDAO = new SupplierDAO();
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
                deleteSupplier(request, response);
                break;

            default:
                listSuppliers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertSupplier(request, response);
                break;

            case "update":
                updateSupplier(request, response);
                break;
        }
    }

    private void listSuppliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("suppliers", supplierDAO.getAll());
        request.getRequestDispatcher("suppliers.jsp").forward(request, response);
    }

    private void insertSupplier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Supplier s = new Supplier();
        s.setName(request.getParameter("name"));
        s.setContactInfo(request.getParameter("contactInfo"));
        s.setAddress(request.getParameter("address"));
        s.setEmail(request.getParameter("email"));
        s.setPhone(request.getParameter("phone"));

        supplierDAO.insert(s);
        response.sendRedirect("supplier");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Supplier supplier = supplierDAO.getById(id);

        request.setAttribute("supplier", supplier);
        request.getRequestDispatcher("edit-supplier.jsp").forward(request, response);
    }

    private void updateSupplier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Supplier s = new Supplier();
        s.setSupplierId(Integer.parseInt(request.getParameter("supplierId")));
        s.setName(request.getParameter("name"));
        s.setContactInfo(request.getParameter("contactInfo"));
        s.setAddress(request.getParameter("address"));
        s.setEmail(request.getParameter("email"));
        s.setPhone(request.getParameter("phone"));

        supplierDAO.update(s);
        response.sendRedirect("supplier");
    }

    private void deleteSupplier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        supplierDAO.delete(id);
        response.sendRedirect("supplier");
    }
}
