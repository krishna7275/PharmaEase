package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private final Connection conn;

    public CustomerDAO() {
        this.conn = DBConnection.getConnection();
    }

    // INSERT
    public boolean insert(Customer c) {
        String sql = "INSERT INTO Customer (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getPhone());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getAddress());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Customer Insert Error: " + e.getMessage());
            return false;
        }
    }

    // UPDATE
    public boolean update(Customer c) {
        String sql = "UPDATE Customer SET name=?, phone=?, email=?, address=? WHERE customerId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getPhone());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getAddress());
            stmt.setInt(5, c.getCustomerId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Customer Update Error: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM Customer WHERE customerId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Customer Delete Error: " + e.getMessage());
            return false;
        }
    }

    // GET BY ID
    public Customer getById(int id) {
        String sql = "SELECT * FROM Customer WHERE customerId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customerId"));
                c.setName(rs.getString("name"));
                c.setPhone(rs.getString("phone"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setCreatedAt(rs.getTimestamp("createdAt"));
                return c;
            }

        } catch (SQLException e) {
            System.out.println("❌ Customer GetById Error: " + e.getMessage());
        }
        return null;
    }

    // GET ALL
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY createdAt DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customerId"));
                c.setName(rs.getString("name"));
                c.setPhone(rs.getString("phone"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setCreatedAt(rs.getTimestamp("createdAt"));
                list.add(c);
            }

        } catch (SQLException e) {
            System.out.println("❌ Customer GetAll Error: " + e.getMessage());
        }
        return list;
    }
}
