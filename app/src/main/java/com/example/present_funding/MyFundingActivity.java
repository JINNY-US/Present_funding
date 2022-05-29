package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyFundingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_funding);

    }
}