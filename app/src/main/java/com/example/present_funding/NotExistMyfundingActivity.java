package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NotExistMyfundingActivity extends AppCompatActivity {

    Button not_market, not_back;
    ImageView not_img;
    TextView not_info;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_not_exist_myfunding);
        backPressCloseHandler = new BackPressCloseHandler(this);

        not_market = findViewById(R.id.btn_nottomarket);
        not_back = findViewById(R.id.btn_not_back);
        not_img = findViewById(R.id.iv_not_img);
        not_info = findViewById(R.id.txt_not_info);

        not_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MarketActivity.class));
                finish();
            }
        });

        not_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MypageActivity.class));
                finish();
            }
        });
    }

    //뒤로가기 시 마이페이지로 이동
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent;
        intent = new Intent(getApplication(), MypageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}