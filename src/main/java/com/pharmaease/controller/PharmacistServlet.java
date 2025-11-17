package com.pharmaease.controller;

import com.pharmaease.dao.PharmacistDAO;
import com.pharmaease.model.Pharmacist;
import com.pharmaease.utils.PasswordHash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/pharmacist")
public class PharmacistServlet extends HttpServlet {

    private PharmacistDAO pharmacistDAO;

    @Override
    public void init() {
        pharmacistDAO = new PharmacistDAO();
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
                deletePharmacist(request, response);
                break;

            case "add":
                request.getRequestDispatcher("register.jsp").forward(request, response);
                break;

            default:
                listPharmacists(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertPharmacist(request, response);
                break;

            case "update":
                updatePharmacist(request, response);
                break;

            default:
                response.sendRedirect("pharmacist");
        }
    }

    // ============================================
    // LIST ALL PHARMACISTS
    // ============================================
    private void listPharmacists(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("pharmacists", pharmacistDAO.getAll());
        request.getRequestDispatcher("pharmacists.jsp").forward(request, response);
    }

    // ============================================
    // INSERT NEW PHARMACIST
    // ============================================
    private void insertPharmacist(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");

        // Check email exists
        if (pharmacistDAO.emailExists(email)) {
            request.setAttribute("error", "Email already exists!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Pharmacist p = new Pharmacist();
        p.setName(name);
        p.setEmail(email);
        p.setPasswordHash(PasswordHash.hash(pass));
        p.setRole(role);

        pharmacistDAO.registerPharmacist(p);

        response.sendRedirect("pharmacist");
    }

    // ============================================
    // SHOW EDIT FORM
    // ============================================
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Pharmacist pharmacist = pharmacistDAO.getById(id);

        request.setAttribute("pharmacist", pharmacist);
        request.getRequestDispatcher("edit-pharmacist.jsp").forward(request, response);
    }

    // ============================================
    // UPDATE PHARMACIST (except password)
    // ============================================
    private void updatePharmacist(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Pharmacist p = new Pharmacist();
        p.setPharmacistId(Integer.parseInt(request.getParameter("pharmacistId")));
        p.setName(request.getParameter("name"));
        p.setEmail(request.getParameter("email"));
        p.setRole(request.getParameter("role"));

        pharmacistDAO.updatePharmacist(p);
        response.sendRedirect("pharmacist");
    }

    // ============================================
    // DELETE PHARMACIST
    // ============================================
    private void deletePharmacist(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        pharmacistDAO.deletePharmacist(id);

        response.sendRedirect("pharmacist");
    }
}
