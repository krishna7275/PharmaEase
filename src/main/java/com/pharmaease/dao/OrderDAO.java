package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.Order;
import com.pharmaease.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // ============================================
    // INSERT ORDER
    // ============================================
    public int insertOrder(Order order) {
        String sql = "INSERT INTO Orders (customerId, pharmacistId, orderDate, totalAmount, paymentMethod, status) " +
                "VALUES (?, ?, NOW(), ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getCustomerId());
            ps.setInt(2, order.getPharmacistId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getPaymentMethod());
            ps.setString(5, order.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // ============================================
    // INSERT ORDER ITEM
    // ============================================
    public void insertOrderItem(OrderItem item) {
        String sql = "INSERT INTO OrderItem (orderId, medicineId, quantity, unitPrice, discount, subtotal) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getMedicineId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());
            ps.setDouble(5, item.getDiscount());
            ps.setDouble(6, item.getSubtotal());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // GET ALL ORDERS
    // ============================================
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM Orders ORDER BY orderDate DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                o.setCustomerId(rs.getInt("customerId"));
                o.setPharmacistId(rs.getInt("pharmacistId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setTotalAmount(rs.getDouble("totalAmount"));
                o.setPaymentMethod(rs.getString("paymentMethod"));
                o.setStatus(rs.getString("status"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ============================================
    // GET ONE ORDER BY ID
    // ============================================
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE orderId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                o.setCustomerId(rs.getInt("customerId"));
                o.setPharmacistId(rs.getInt("pharmacistId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setTotalAmount(rs.getDouble("totalAmount"));
                o.setPaymentMethod(rs.getString("paymentMethod"));
                o.setStatus(rs.getString("status"));
                return o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ============================================
    // GET ORDER ITEMS
    // ============================================
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT * FROM OrderItem WHERE orderId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("orderItemId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setMedicineId(rs.getInt("medicineId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unitPrice"));
                item.setDiscount(rs.getDouble("discount"));
                item.setSubtotal(rs.getDouble("subtotal"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    // ============================================
    // DASHBOARD: TODAY SALES
    // ============================================
    public double getTodaySales() {
        String sql = "SELECT IFNULL(SUM(totalAmount), 0) AS todaySales " +
                "FROM Orders WHERE DATE(orderDate) = CURDATE() AND status='Completed'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble("todaySales");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ============================================
    // DASHBOARD: TODAY ORDER COUNT
    // ============================================
    public int getTodayOrderCount() {
        String sql = "SELECT COUNT(*) FROM Orders WHERE DATE(orderDate) = CURDATE()";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ============================================
    // DASHBOARD: TOTAL SALES
    // ============================================
    public double getTotalRevenue() {
        String sql = "SELECT IFNULL(SUM(totalAmount), 0) FROM Orders WHERE status='Completed'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
