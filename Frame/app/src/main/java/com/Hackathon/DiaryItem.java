package com.Hackathon;

import androidx.room.Entity;

@Entity
class DiaryItem {
    public boolean isBuy;
    public String stockName;
    public int price;
    public int quantity;
    public String categoryName;
    public String categoryColor;
}