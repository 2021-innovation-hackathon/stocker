package com.Hackathon;

import android.content.Context;
import android.view.View;

import androidx.room.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class FileReader {
    private static Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public String getData(String key) {
        return data.get(key);
    }

    public void setData(String key, String value) {
        String val = data.get(key);

        if(val != null) {
            data.remove(key);
        }

        data.put(key, value);
    }

    private static FileReader instance = null;
    public static synchronized FileReader getInstance(Context context) throws IOException, BiffException {
        if(null == instance){
            data = new HashMap<>();

            // 파일 가져오기 위한 세팅
            InputStreamReader isr = new InputStreamReader(context.getResources().getAssets().open("kospi_data.txt"), "EUC-KR");
            BufferedReader b = new BufferedReader(isr);

            String str = "";
            while(str != null) {
                str = b.readLine();

                if(str == null) {
                    break;
                }

                String[] company = str.split("\t");

                data.put(company[0], company[1]);
            }

            instance = new FileReader();
        }
        return instance;
    }
}
