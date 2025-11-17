package com.pharmaease.controller;

import com.pharmaease.dao.*;
import com.pharmaease.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {

    private MedicineDAO medicineDAO;
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Override
    public void init() {
        medicineDAO = new MedicineDAO();
        customerDAO = new CustomerDAO();
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
    }

    // =====================================
    // GET → Show Billing Page
    // =====================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("medicines", medicineDAO.getAll());
        request.setAttribute("customers", customerDAO.getAll());

        HttpSession session = request.getSession();
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        request.getRequestDispatcher("billing.jsp").forward(request, response);
    }

    // =====================================
    // POST → Handle Actions
    // =====================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");

        switch (action) {

            case "add":
                addToCart(request, response);
                break;

            case "remove":
                removeFromCart(request, response);
                break;

            case "complete":
                completeOrder(request, response);
                break;

            default:
                response.sendRedirect("billing");
        }
    }

    // =====================================
    // ADD TO CART
    // =====================================
    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int medicineId = Integer.parseInt(request.getParameter("medicineId"));
        int qty = Integer.parseInt(request.getParameter("quantity"));

        Medicine medicine = medicineDAO.getById(medicineId);

        if (medicine == null) {
            response.sendRedirect("billing");
            return;
        }

        OrderItem item = new OrderItem();
        item.setMedicineId(medicineId);
        item.setQuantity(qty);
        item.setUnitPrice(medicine.getMrp());
        item.setDiscount(0);
        item.setSubtotal(medicine.getMrp() * qty);

        HttpSession session = request.getSession();
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        cart.add(item);

        response.sendRedirect("billing");
    }

    // =====================================
    // REMOVE ITEM
    // =====================================
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int index = Integer.parseInt(request.getParameter("index"));

        HttpSession session = request.getSession();
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");

        if (index >= 0 && index < cart.size()) {
            cart.remove(index);
        }

        response.sendRedirect("billing");
    }

    // =====================================
    // COMPLETE ORDER
    // =====================================
    private void completeOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("billing");
            return;
        }

        int customerId = Integer.parseInt(request.getParameter("customerId"));
        int pharmacistId = Integer.parseInt(request.getParameter("pharmacistId")); // session later
        String paymentMethod = request.getParameter("paymentMethod");

        double totalAmount = cart.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();

        // SAVE ORDER
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setPharmacistId(pharmacistId);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Completed");
        order.setTotalAmount(totalAmount);

        int orderId = orderDAO.insertOrder(order);


        // SAVE ITEMS
        for (OrderItem item : cart) {
            item.setOrderId(orderId);
            orderItemDAO.insert(item);
        }

        // Clear cart
        session.removeAttribute("cart");

        // Redirect to invoice (orders page)
        response.sendRedirect("order?action=view&id=" + orderId);
    }
}
