package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    private final Connection conn;

    public OrderItemDAO() {
        this.conn = DBConnection.getConnection();
    }

    // INSERT ORDER ITEM
    public boolean insert(OrderItem item) {
        String sql = "INSERT INTO OrderItem (orderId, medicineId, quantity, unitPrice, discount, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getMedicineId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.setDouble(5, item.getDiscount());
            stmt.setDouble(6, item.getSubtotal());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ OrderItem Insert Error: " + e.getMessage());
            return false;
        }
    }

    // GET ITEMS BY ORDER ID
    public List<OrderItem> getByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderItem WHERE orderId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem i = new OrderItem();
                i.setOrderItemId(rs.getInt("orderItemId"));
                i.setOrderId(rs.getInt("orderId"));
                i.setMedicineId(rs.getInt("medicineId"));
                i.setQuantity(rs.getInt("quantity"));
                i.setUnitPrice(rs.getDouble("unitPrice"));
                i.setDiscount(rs.getDouble("discount"));
                i.setSubtotal(rs.getDouble("subtotal"));
                list.add(i);
            }

        } catch (SQLException e) {
            System.out.println("❌ OrderItem GetByOrderId Error: " + e.getMessage());
        }
        return list;
    }
}
