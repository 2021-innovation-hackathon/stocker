package com.Hackathon;

import java.util.Date;

public class StockBar {
    private String companyId;
    private Date date;
    private float currentStock;

    public StockBar(String companyId, Date date, String currentStock) {
        this.companyId = companyId;
        this.date = date;
        this.currentStock = Float.parseFloat(currentStock);
    }

    public float getCurrentStock() {
        return currentStock;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCurrentStock(float currentStock) {
        this.currentStock = currentStock;
    }
}
