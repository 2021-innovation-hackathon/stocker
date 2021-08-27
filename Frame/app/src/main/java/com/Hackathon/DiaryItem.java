package com.Hackathon;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiaryItem {
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
    @NonNull
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
}
