package com.Hackathon;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CompanyData {
    @PrimaryKey
    @NonNull
    public String companyName;

    @NonNull
    public String companyID;
}
