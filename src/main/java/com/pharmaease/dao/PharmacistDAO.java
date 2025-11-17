package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.Pharmacist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacistDAO {

    private final Connection conn;

    public PharmacistDAO() {
        this.conn = DBConnection.getConnection();
    }

    // ================================
    // INSERT (Register Pharmacist)
    // ================================
    public boolean registerPharmacist(Pharmacist pharmacist) {
        String sql = "INSERT INTO Pharmacist (name, email, passwordHash, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pharmacist.getName());
            stmt.setString(2, pharmacist.getEmail());
            stmt.setString(3, pharmacist.getPasswordHash());
            stmt.setString(4, pharmacist.getRole());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error in registerPharmacist: " + e.getMessage());
            return false;
        }
    }

    // ================================
    // LOGIN (Email + Password)
    // ================================
    public Pharmacist login(String email, String passwordHash) {
        String sql = "SELECT * FROM Pharmacist WHERE email = ? AND passwordHash = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, passwordHash);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pharmacist p = new Pharmacist();

                p.setPharmacistId(rs.getInt("pharmacistId"));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPasswordHash(rs.getString("passwordHash"));
                p.setRole(rs.getString("role"));
                p.setCreatedAt(rs.getTimestamp("createdAt"));

                return p;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error in login: " + e.getMessage());
        }
        return null;
    }

    // ================================
    // CHECK IF EMAIL EXISTS
    // ================================
    public boolean emailExists(String email) {
        String sql = "SELECT pharmacistId FROM Pharmacist WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true if email already exists

        } catch (SQLException e) {
            System.out.println("❌ Error in emailExists: " + e.getMessage());
            return false;
        }
    }

    // ================================
    // GET BY ID
    // ================================
    public Pharmacist getById(int id) {
        String sql = "SELECT * FROM Pharmacist WHERE pharmacistId = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pharmacist p = new Pharmacist();

                p.setPharmacistId(rs.getInt("pharmacistId"));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPasswordHash(rs.getString("passwordHash"));
                p.setRole(rs.getString("role"));
                p.setCreatedAt(rs.getTimestamp("createdAt"));

                return p;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error in getById: " + e.getMessage());
        }
        return null;
    }

    // ================================
    // GET ALL PHARMACISTS
    // ================================
    public List<Pharmacist> getAll() {
        List<Pharmacist> list = new ArrayList<>();
        String sql = "SELECT * FROM Pharmacist ORDER BY createdAt DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pharmacist p = new Pharmacist();

                p.setPharmacistId(rs.getInt("pharmacistId"));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPasswordHash(rs.getString("passwordHash"));
                p.setRole(rs.getString("role"));
                p.setCreatedAt(rs.getTimestamp("createdAt"));

                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error in getAll: " + e.getMessage());
        }
        return list;
    }

    // ================================
    // UPDATE PHARMACIST
    // ================================
    public boolean updatePharmacist(Pharmacist pharmacist) {
        String sql = "UPDATE Pharmacist SET name = ?, email = ?, role = ? WHERE pharmacistId = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pharmacist.getName());
            stmt.setString(2, pharmacist.getEmail());
            stmt.setString(3, pharmacist.getRole());
            stmt.setInt(4, pharmacist.getPharmacistId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error in updatePharmacist: " + e.getMessage());
            return false;
        }
    }

    // ================================
    // DELETE PHARMACIST
    // ================================
    public boolean deletePharmacist(int id) {
        String sql = "DELETE FROM Pharmacist WHERE pharmacistId = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error in deletePharmacist: " + e.getMessage());
            return false;
        }
    }
}
