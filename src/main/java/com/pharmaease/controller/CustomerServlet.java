package com.pharmaease.controller;

import com.pharmaease.dao.CustomerDAO;
import com.pharmaease.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
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
                deleteCustomer(request, response);
                break;

            default:
                listCustomers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertCustomer(request, response);
                break;

            case "update":
                updateCustomer(request, response);
                break;
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("customers", customerDAO.getAll());
        request.getRequestDispatcher("customers.jsp").forward(request, response);
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Customer c = new Customer();
        c.setName(request.getParameter("name"));
        c.setPhone(request.getParameter("phone"));
        c.setEmail(request.getParameter("email"));
        c.setAddress(request.getParameter("address"));

        customerDAO.insert(c);
        response.sendRedirect("customer");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Customer c = customerDAO.getById(id);

        request.setAttribute("customer", c);
        request.getRequestDispatcher("edit-customer.jsp").forward(request, response);
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Customer c = new Customer();
        c.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        c.setName(request.getParameter("name"));
        c.setPhone(request.getParameter("phone"));
        c.setEmail(request.getParameter("email"));
        c.setAddress(request.getParameter("address"));

        customerDAO.update(c);
        response.sendRedirect("customer");
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        customerDAO.delete(id);
        response.sendRedirect("customer");
    }
}
