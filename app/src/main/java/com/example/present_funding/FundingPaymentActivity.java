package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FundingPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_payment);
    }

    private BackPressCloseHandler backPressCloseHandler;
    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}