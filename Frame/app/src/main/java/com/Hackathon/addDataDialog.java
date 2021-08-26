package com.Hackathon;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.Hackathon.ui.home.DateSingleton;
import com.Hackathon.ui.home.Home_addMemo;

import java.io.IOException;

import jxl.read.biff.BiffException;

public class addDataDialog extends Dialog implements AdapterView.OnItemSelectedListener{

    protected boolean risBuy;
    protected String rstockName,rcategoryName,rcategoryColor;
    protected int rprice,rquantity;

    private Home_addMemo home;
    protected boolean swittch = true;


    public addDataDialog(@NonNull Context context) {
        super(context);
        home=(Home_addMemo)context;

    }

    public addDataDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        home=(Home_addMemo)context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_add);
        setTitle("Daily Stock Note Add");


        Button selectbuy = (Button) findViewById(R.id.buyorsell);
        selectbuy.setOnClickListener(view -> {
            if(swittch){
                selectbuy.setBackgroundColor(Color.parseColor("#1e73be"));
                selectbuy.setText("매도");
                swittch = false;
            }else{
                selectbuy.setBackgroundColor(Color.parseColor("#dd3333"));
                selectbuy.setText("매수");
                swittch = true;
            }
        });

        Button b = (Button) findViewById(R.id.add_confirm);
        b.setOnClickListener(view -> {

            DiaryItem d = new DiaryItem();

            TextView r1,r2,r3,r4,r5;
            r1=(TextView)findViewById(R.id.tstockName);
            r2=(TextView)findViewById(R.id.tprice);
            r3=(TextView)findViewById(R.id.tquantity);
            r4=(TextView)findViewById(R.id.tcategoryName);
            r5=(TextView)findViewById(R.id.tcategoryColor);

            d.isBuy = swittch;
            d.stockName = r1.getText().toString();
            d.price = Integer.parseInt(r2.getText().toString());
            d.quantity = Integer.valueOf(r3.getText().toString());
            d.categoryName = r4.getText().toString();
            d.categoryColor = r5.getText().toString();
            d.date = DateSingleton.dateValue;
            System.out.println(d.date);
            System.out.println(DateSingleton.dateValue);

            Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DiaryItemDB.getInstance(getContext()).diaryItemDao().insert(d);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BiffException e) {
                            e.printStackTrace();
                        }
                    }
                }
            );
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //카테고리에 넣기
            dismiss();

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
