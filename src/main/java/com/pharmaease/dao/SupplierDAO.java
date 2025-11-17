package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    private final Connection conn;

    public SupplierDAO() {
        this.conn = DBConnection.getConnection();
    }

    // INSERT
    public boolean insert(Supplier supplier) {
        String sql = "INSERT INTO Supplier (name, contactInfo, address, email, phone) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getContactInfo());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getPhone());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Supplier Insert Error: " + e.getMessage());
            return false;
        }
    }

    // UPDATE
    public boolean update(Supplier supplier) {
        String sql = "UPDATE Supplier SET name=?, contactInfo=?, address=?, email=?, phone=? WHERE supplierId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getContactInfo());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getPhone());
            stmt.setInt(6, supplier.getSupplierId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Supplier Update Error: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(int supplierId) {
        String sql = "DELETE FROM Supplier WHERE supplierId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Supplier Delete Error: " + e.getMessage());
            return false;
        }
    }

    // GET BY ID
    public Supplier getById(int id) {
        String sql = "SELECT * FROM Supplier WHERE supplierId=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplierId"));
                s.setName(rs.getString("name"));
                s.setContactInfo(rs.getString("contactInfo"));
                s.setAddress(rs.getString("address"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                s.setCreatedAt(rs.getTimestamp("createdAt"));
                return s;
            }
        } catch (SQLException e) {
            System.out.println("❌ Supplier GetById Error: " + e.getMessage());
        }
        return null;
    }

    // GET ALL
    public List<Supplier> getAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM Supplier ORDER BY createdAt DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplierId"));
                s.setName(rs.getString("name"));
                s.setContactInfo(rs.getString("contactInfo"));
                s.setAddress(rs.getString("address"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                s.setCreatedAt(rs.getTimestamp("createdAt"));
                list.add(s);
            }

        } catch (SQLException e) {
            System.out.println("❌ Supplier GetAll Error: " + e.getMessage());
        }
        return list;
    }
}
