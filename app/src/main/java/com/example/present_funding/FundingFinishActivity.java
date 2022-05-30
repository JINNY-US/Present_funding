package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FundingFinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_finish);
    }

    private BackPressCloseHandler backPressCloseHandler;
    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}