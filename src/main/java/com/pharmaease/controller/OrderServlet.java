package com.pharmaease.controller;

import com.pharmaease.dao.OrderDAO;
import com.pharmaease.dao.OrderItemDAO;
import com.pharmaease.dao.CustomerDAO;
import com.pharmaease.model.Order;
import com.pharmaease.model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "view":
                viewOrder(request, response);
                break;

            default:
                listOrders(request, response);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("orders", orderDAO.getAll());
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    private void viewOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("id"));

        Order order = orderDAO.getById(orderId);
        List<OrderItem> items = orderItemDAO.getByOrderId(orderId);

        request.setAttribute("order", order);
        request.setAttribute("items", items);

        request.getRequestDispatcher("order-details.jsp").forward(request, response);
    }
}
