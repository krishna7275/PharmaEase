package com.pharmaease.controller;

import com.pharmaease.dao.MedicineDAO;
import com.pharmaease.dao.StockBatchDAO;
import com.pharmaease.dao.SupplierDAO;
import com.pharmaease.model.StockBatch;
import com.pharmaease.model.Medicine;
import com.pharmaease.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/batch")
public class StockBatchServlet extends HttpServlet {

    private StockBatchDAO stockBatchDAO;
    private MedicineDAO medicineDAO;
    private SupplierDAO supplierDAO;

    @Override
    public void init() {
        stockBatchDAO = new StockBatchDAO();
        medicineDAO = new MedicineDAO();
        supplierDAO = new SupplierDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "view":
                viewBatches(request, response);
                break;

            default:
                listData(request, response);
        }
    }

    private void listData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("medicines", medicineDAO.getAll());
        request.setAttribute("suppliers", supplierDAO.getAll());

        request.getRequestDispatcher("stock-batch.jsp").forward(request, response);
    }

    private void viewBatches(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int medicineId = Integer.parseInt(request.getParameter("medicineId"));
        List<StockBatch> batches = stockBatchDAO.getByMedicineId(medicineId);

        request.setAttribute("batches", batches);
        request.getRequestDispatcher("view-batch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        StockBatch batch = new StockBatch();

        batch.setMedicineId(Integer.parseInt(request.getParameter("medicineId")));
        batch.setSupplierId(Integer.parseInt(request.getParameter("supplierId")));
        batch.setBatchNumber(request.getParameter("batchNumber"));
        batch.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        batch.setPurchasePrice(Double.parseDouble(request.getParameter("purchasePrice")));
        batch.setExpiryDate(Date.valueOf(request.getParameter("expiryDate")));
        batch.setManufactureDate(Date.valueOf(request.getParameter("manufactureDate")));

        stockBatchDAO.insert(batch);

        response.sendRedirect("batch");
    }
}
