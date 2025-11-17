package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    private final Connection conn;

    public InventoryDAO() {
        this.conn = DBConnection.getConnection();
    }

    // UPDATE STOCK (manual update)
    public boolean updateStock(int medicineId, int newQty) {
        String sql = "UPDATE Inventory SET currentQty=? WHERE medicineId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQty);
            stmt.setInt(2, medicineId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Inventory UpdateStock Error: " + e.getMessage());
            return false;
        }
    }

    // GET BY MEDICINE ID
    public Inventory getByMedicineId(int medicineId) {
        String sql = "SELECT * FROM Inventory WHERE medicineId=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medicineId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Inventory i = new Inventory();
                i.setInventoryId(rs.getInt("inventoryId"));
                i.setMedicineId(rs.getInt("medicineId"));
                i.setCurrentQty(rs.getInt("currentQty"));
                i.setLastUpdated(rs.getTimestamp("lastUpdated"));
                return i;
            }

        } catch (SQLException e) {
            System.out.println("❌ Inventory GetByMedicineId Error: " + e.getMessage());
        }
        return null;
    }

    // GET ALL INVENTORY
    public List<Inventory> getAll() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inventory i = new Inventory();
                i.setInventoryId(rs.getInt("inventoryId"));
                i.setMedicineId(rs.getInt("medicineId"));
                i.setCurrentQty(rs.getInt("currentQty"));
                i.setLastUpdated(rs.getTimestamp("lastUpdated"));
                list.add(i);
            }

        } catch (SQLException e) {
            System.out.println("❌ Inventory GetAll Error: " + e.getMessage());
        }
        return list;
    }
}
