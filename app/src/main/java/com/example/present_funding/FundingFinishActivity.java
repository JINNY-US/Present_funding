package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FundingFinishActivity extends AppCompatActivity {

    Button go_main;
    TextView textView, txt_fund_info;
    String get_host_name, get_payment, get_month, get_day;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_funding_finish);

        backPressCloseHandler = new BackPressCloseHandler(this);

        go_main = findViewById(R.id.btn_go_main);
        textView = findViewById(R.id.textView);
        txt_fund_info = findViewById(R.id.txt_fund_info);

        Intent intent = getIntent();

        get_host_name = intent.getStringExtra("host_name");
        get_payment = intent.getStringExtra("payment") + " 원";
        get_month = intent.getStringExtra("month") + "월 ";
        get_day = intent.getStringExtra("day") + "일";

        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MainActivity.class));
            }
        });

        txt_fund_info.setText("받는 사람: " + get_host_name +
                "\n\n후원금액: " + get_payment +
                "\n\n펀딩 마감일: " + get_month + get_day +
                "\n\n결제 예정일은 펀딩 마감 4일 전입니다.");
    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //backPressCloseHandler.onBackPressed();
        Intent intent;
        intent = new Intent(getApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}