package com.Hackathon;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import jxl.read.biff.BiffException;

@Database(entities = {CompanyData.class}, version = 1)
public abstract class CompanyDataDB extends RoomDatabase {
    private static CompanyDataDB instance = null;

    public abstract DaoCompany companyDao();

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        System.out.println(dbFile.getAbsolutePath());
        return dbFile.exists();
    }

    public static CompanyDataDB getInstance(Context context) throws IOException, BiffException {
        if(instance == null) {
            if(!doesDatabaseExist(context.getApplicationContext(), "company.db")) {
                FileReader fr = FileReader.getInstance(context);

                Map<String, String> datas = fr.getData();

                instance = Room.databaseBuilder(context.getApplicationContext(),
                        CompanyDataDB.class, "company.db").build();

                ArrayList<CompanyData> al = new ArrayList<>();

                for (Map.Entry<String, String> entry : datas.entrySet()) {
                    CompanyData cd = new CompanyData();

                    cd.companyName   = entry.getKey();
                    cd.companyID =  entry.getValue();
                    System.out.println(cd.companyName);

                    instance.companyDao().insertAll(cd);
                }
            } else {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        CompanyDataDB.class, "company.db").build();
            }
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
