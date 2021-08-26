package com.Hackathon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class DiaryItem {
    @PrimaryKey(autoGenerate = true)
    public int pid;
    //자동 부여 일련번호

    @ColumnInfo(name="date")
    public String date;
    //기본키 날짜 스트링

    @ColumnInfo(name="isBuy")
    public boolean isBuy;
    //매수 : true, 매도 : false

    @ColumnInfo(name="stockName")
    public String stockName;
    //종목명

    @ColumnInfo(name="price")
    public int price;
    //구매가/판매가

    @ColumnInfo(name="quantity")
    public int quantity;
    //구매량/판매량

    @ColumnInfo(name="categoryName")
    public String categoryName;
    //카테고리명

    @ColumnInfo(name="categoryColor")
    public String categoryColor;
    //카테고리컬러


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public DiaryItem(String date, boolean isBuy, String stockName, int price, int quantity, String categoryName, String categoryColor) {
        this.date = date;
        this.isBuy = isBuy;
        this.stockName = stockName;
        this.price = price;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    @Override
    public String toString() {
        return "DiaryItem{" +
                "pid=" + pid +
                ", date='" + date + '\'' +
                ", isBuy=" + isBuy +
                ", stockName='" + stockName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categoryName='" + categoryName + '\'' +
                ", categoryColor='" + categoryColor + '\'' +
                '}';
    }
}
