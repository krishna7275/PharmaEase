package com.pharmaease.dao;

import com.pharmaease.config.DBConnection;
import com.pharmaease.model.StockBatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockBatchDAO {

    private final Connection conn;

    public StockBatchDAO() {
        this.conn = DBConnection.getConnection();
    }

    // INSERT STOCK BATCH
    public boolean insert(StockBatch b) {
        String sql = "INSERT INTO StockBatch (medicineId, supplierId, batchNumber, quantity, purchasePrice, expiryDate, manufactureDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, b.getMedicineId());
            stmt.setInt(2, b.getSupplierId());
            stmt.setString(3, b.getBatchNumber());
            stmt.setInt(4, b.getQuantity());
            stmt.setDouble(5, b.getPurchasePrice());
            stmt.setDate(6, b.getExpiryDate());
            stmt.setDate(7, b.getManufactureDate());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ StockBatch Insert Error: " + e.getMessage());
            return false;
        }
    }

    // GET BY MEDICINE
    public List<StockBatch> getByMedicineId(int medicineId) {
        List<StockBatch> list = new ArrayList<>();
        String sql = "SELECT * FROM StockBatch WHERE medicineId=? ORDER BY expiryDate ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medicineId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StockBatch b = new StockBatch();
                b.setBatchId(rs.getInt("batchId"));
                b.setMedicineId(rs.getInt("medicineId"));
                b.setSupplierId(rs.getInt("supplierId"));
                b.setBatchNumber(rs.getString("batchNumber"));
                b.setQuantity(rs.getInt("quantity"));
                b.setPurchasePrice(rs.getDouble("purchasePrice"));
                b.setExpiryDate(rs.getDate("expiryDate"));
                b.setManufactureDate(rs.getDate("manufactureDate"));
                b.setAddedDate(rs.getTimestamp("addedDate"));
                list.add(b);
            }

        } catch (SQLException e) {
            System.out.println("❌ StockBatch GetByMedicineId Error: " + e.getMessage());
        }
        return list;
    }
}
