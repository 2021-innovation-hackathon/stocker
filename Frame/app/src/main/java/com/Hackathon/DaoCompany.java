package com.Hackathon;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoCompany {
    @Query("SELECT * FROM CompanyData")
    public List<CompanyData> getAll();

    @Query("SELECT * FROM CompanyData WHERE companyName IN (:data)")
    public List<CompanyData> loadAllBycompanyName(String[] data);

    @Query("SELECT CompanyData.companyID FROM CompanyData WHERE CompanyData.companyName = :key")
    public String getCompanyCode(String key);

    @Insert
    public void insertAll(CompanyData... data);

    @Delete
    public void delete(CompanyData data);
}
