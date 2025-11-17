package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    // ==========================================================
    // INSERT MEDICINE
    // ==========================================================
    public boolean insert(Medicine m) {
        String sql = "INSERT INTO Medicine (name, category, description, manufacturer, mrp, quantity, minStockLevel) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getName());
            ps.setString(2, m.getCategory());
            ps.setString(3, m.getDescription());
            ps.setString(4, m.getManufacturer());
            ps.setDouble(5, m.getMrp());
            ps.setInt(6, m.getQuantity());
            ps.setInt(7, m.getMinStockLevel());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================================================
    // UPDATE MEDICINE
    // ==========================================================
    public boolean update(Medicine m) {
        String sql = "UPDATE Medicine SET name=?, category=?, description=?, manufacturer=?, " +
                "mrp=?, quantity=?, minStockLevel=? WHERE medicineId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getName());
            ps.setString(2, m.getCategory());
            ps.setString(3, m.getDescription());
            ps.setString(4, m.getManufacturer());
            ps.setDouble(5, m.getMrp());
            ps.setInt(6, m.getQuantity());
            ps.setInt(7, m.getMinStockLevel());
            ps.setInt(8, m.getMedicineId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================================================
    // DELETE MEDICINE
    // ==========================================================
    public boolean delete(int id) {
        String sql = "DELETE FROM Medicine WHERE medicineId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================================================
    // GET MEDICINE BY ID
    // ==========================================================
    public Medicine getById(int id) {
        Medicine m = null;
        String sql = "SELECT * FROM Medicine WHERE medicineId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = mapMedicine(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return m;
    }

    // ==========================================================
    // GET ALL MEDICINES
    // ==========================================================
    public List<Medicine> getAll() {
        List<Medicine> list = new ArrayList<>();

        String sql = "SELECT * FROM Medicine ORDER BY createdAt DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapMedicine(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================================================
    // SEARCH MEDICINE by name/category
    // ==========================================================
    public List<Medicine> search(String keyword) {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM Medicine WHERE name LIKE ? OR category LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapMedicine(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================================================
    // UPDATE QUANTITY (used by triggers, orders, batch add)
    // ==========================================================
    public boolean updateQuantity(int id, int qty) {
        String sql = "UPDATE Medicine SET quantity=? WHERE medicineId=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================================================
    // GET LATEST MEDICINES (Dashboard)
    // ==========================================================
    public List<Medicine> getLatest(int limit) {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM Medicine ORDER BY createdAt DESC LIMIT ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapMedicine(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================================================
    // MAP RESULTSET TO MODEL
    // ==========================================================
    private Medicine mapMedicine(ResultSet rs) throws SQLException {
        Medicine m = new Medicine();
        m.setMedicineId(rs.getInt("medicineId"));
        m.setName(rs.getString("name"));
        m.setCategory(rs.getString("category"));
        m.setDescription(rs.getString("description"));
        m.setManufacturer(rs.getString("manufacturer"));
        m.setMrp(rs.getDouble("mrp"));
        m.setQuantity(rs.getInt("quantity"));
        m.setMinStockLevel(rs.getInt("minStockLevel"));
        m.setCreatedAt(rs.getTimestamp("createdAt"));
        m.setUpdatedAt(rs.getTimestamp("updatedAt"));
        return m;
    }
}
