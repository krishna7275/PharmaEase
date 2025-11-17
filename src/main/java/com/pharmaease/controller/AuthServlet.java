package com.pharmaease.controller;

import com.pharmaease.dao.PharmacistDAO;
import com.pharmaease.model.Pharmacist;
import com.pharmaease.utils.PasswordHash;
import com.pharmaease.utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private PharmacistDAO pharmacistDAO;

    @Override
    public void init() {
        pharmacistDAO = new PharmacistDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        switch (action) {
            case "login":
                login(request, response);
                break;

            case "logout":
                logout(request, response);
                break;

            default:
                response.sendRedirect("login.jsp");
        }
    }

    // ==========================================
    // LOGIN PROCESS
    // ==========================================
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null ||
                email.isEmpty() || password.isEmpty()) {

            request.setAttribute("error", "Email or password cannot be empty.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Hash entered password
        String passwordHash = PasswordHash.hash(password);

        Pharmacist pharmacist = pharmacistDAO.login(email, passwordHash);

        if (pharmacist == null) {
            request.setAttribute("error", "Invalid email or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Create session
        HttpSession session = request.getSession();
        session.setAttribute("user", pharmacist);
        session.setAttribute("role", pharmacist.getRole());

        // Redirect to dashboard
        response.sendRedirect("dashboard.jsp");
    }

    // ==========================================
    // LOGOUT
    // ==========================================
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        SessionUtils.destroySession(request);
        response.sendRedirect("login.jsp");
    }
}
