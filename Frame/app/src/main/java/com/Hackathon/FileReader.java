package com.Hackathon;

import com.opencsv.CSVReader;

import java.util.Map;

public class FileReader {
    Map<String, String> data;

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
    public static synchronized FileReader getInstance(){
        if(null == instance){
            // csv 파일 가져오기
            String path = System.getProperty("user.dir");
            System.out.println("Working Directory = " + path);
            instance = new FileReader();
        }
        return instance;
    }
}
