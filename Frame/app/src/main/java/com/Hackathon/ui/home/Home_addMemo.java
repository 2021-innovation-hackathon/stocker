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

public class Home_addMemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_memo);

        Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");
        String yearmonth = intent.getStringExtra("selected");

        TextView a = (TextView)findViewById(R.id.dd);
        a.setText(date);
        TextView b = (TextView)findViewById(R.id.ym);
        b.setText(yearmonth);

    }

    public void backTohome(View view){
        finish();
    }

    public void addRecord(View view){
        Toast.makeText(this,"addMemo executed.",Toast.LENGTH_LONG).show();
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