package com.pharmaease.model;

import java.sql.Timestamp;

public class Inventory {
    private int inventoryId;
    private int medicineId;
    private int currentQty;
    private Timestamp lastUpdated;

    public int getInventoryId() { return inventoryId; }
    public void setInventoryId(int inventoryId) { this.inventoryId = inventoryId; }

    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }

    public int getCurrentQty() { return currentQty; }
    public void setCurrentQty(int currentQty) { this.currentQty = currentQty; }

    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }
}
