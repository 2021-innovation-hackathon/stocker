package com.Hackathon.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Hackathon.R;
import com.Hackathon.addDataDialog;

public class Home_addMemo extends AppCompatActivity {
    int date,month,year;
    String DateCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_memo);

        DateCode = Integer.toString(year)+Integer.toString(month)+Integer.toString(date) ;
        Intent intent = getIntent();
        date = intent.getIntExtra("DATE",0);
        month = intent.getIntExtra("MONTH",0);
        year = intent.getIntExtra("YEAR",0);

        TextView target = findViewById(R.id.DateText);
        target.setText(Integer.toString(year)+"년 "+Integer.toString(month)+" 월 " +Integer.toString(date) + " 일");




    }

    public void backTohome(View view){
        finish();
    }

    public void addRecord(View view){
//        TextView ddd = (TextView)findViewById(R.id.fixedDate);
//        ddd.setText(DateCode);
        new addDataDialog(Home_addMemo.this).show();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_add_memo, menu);
//
//       if(getSupportActionBar() != null){
//           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//           getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_arrow);
//       }
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.add_memo:
//                Toast.makeText(this,"addMemo executed.",Toast.LENGTH_LONG).show();
//                break;
//            case R.id.home:
//                finish();
//                break;
//        }
//        return true;
//    }
}