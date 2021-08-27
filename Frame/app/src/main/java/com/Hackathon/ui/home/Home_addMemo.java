package com.Hackathon.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Hackathon.DiaryDataAdapter;
import com.Hackathon.DiaryItem;
import com.Hackathon.DiaryItemDB;
import com.Hackathon.R;
import com.Hackathon.addDataDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.read.biff.BiffException;

public class Home_addMemo extends AppCompatActivity {
    int date,month,year;
    String DateCode;
    String companyName;
    boolean flag;

    public List<DiaryItem> diaryItems = null;
    DiaryDataAdapter diaryDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_memo);

        Intent intent = getIntent();
        date = intent.getIntExtra("DATE",0);
        month = intent.getIntExtra("MONTH",0);
        year = intent.getIntExtra("YEAR",0);
        flag = intent.getBooleanExtra("FLAG", false);

        if(flag) {
            companyName = intent.getStringExtra("STOCKNAME");
            System.out.println("addMemo companyName : " + companyName);
        }


        if(month < 10) {
            DateCode = Integer.toString(year)+ "0" + Integer.toString(month)+Integer.toString(date) ;
        } else {
            DateCode = Integer.toString(year) + Integer.toString(month) + Integer.toString(date) ;
        }

        TextView target = findViewById(R.id.DateText);
        target.setText(Integer.toString(year)+"년 "+Integer.toString(month)+" 월 " +Integer.toString(date) + " 일");

        TextView comp = findViewById(R.id.wanted);

        Thread t = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        if(DiaryItemDB.getInstance(getApplicationContext()).diaryItemDao().getAll() != null) {
                            if(flag) {
                                diaryItems = DiaryItemDB.getInstance(getApplicationContext())
                                        .diaryItemDao().getSelectedData(companyName, DateCode);
                            } else {
                                diaryItems = DiaryItemDB.getInstance(getApplicationContext()).diaryItemDao().getAll();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        t.start();


        ListView listView = (ListView)findViewById(R.id.listView);
        diaryDataAdapter = new DiaryDataAdapter(this, diaryItems);
    }

    public void backTohome(View view){
        finish();
    }

    public void addRecord(View view) throws InterruptedException {
        DateSingleton.dateValue = DateCode;
        new addDataDialog(Home_addMemo.this).show();

        TextView comp = findViewById(R.id.wanted);

        Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(DiaryItemDB.getInstance(getApplicationContext()).diaryItemDao().getAll() != null) {
                                if(flag) {
                                    diaryItems = DiaryItemDB.getInstance(getApplicationContext())
                                            .diaryItemDao().getSelectedData(companyName, DateCode);
                                } else {
                                    diaryItems = DiaryItemDB.getInstance(getApplicationContext()).diaryItemDao().getAll();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BiffException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        t.start();
        t.join();

        ListView listView = (ListView)findViewById(R.id.listView);
        diaryDataAdapter = new DiaryDataAdapter(this, diaryItems);

        listView.setAdapter(diaryDataAdapter);
    }
}