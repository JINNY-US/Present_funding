package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyFundingActivity extends AppCompatActivity {

    private ImageView iv_myfunding;
    private TextView txt_product_price, txt_product_name, txt_mycurrunt_price, txt_lack_price, txt_information;
    private ProgressBar my_progressBar;
    private Button btn_fund_share, btn_myfundcancle;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_funding);

        backPressCloseHandler = new BackPressCloseHandler(this);

        iv_myfunding = findViewById(R.id.iv_myfunding_img); //펀딩을 진행하는 이미지 필요
        txt_product_price = findViewById(R.id.txt_myachieve_price); //목표 달성액
        txt_product_name = findViewById(R.id.txt_myitem_name); //상품명
        txt_mycurrunt_price = findViewById(R.id.txt_mycurrunt_price); //현재 달성액 -> 이건 collection 데이터 불러오면 될듯
        txt_lack_price = findViewById(R.id.txt_lack_price); //부족한 금액 = 목표-현재
        my_progressBar = findViewById(R.id.my_progressBar); // 진행 그래프?

        txt_information = findViewById(R.id.txt_information); // 이 펀딩은 -월 -일에 마감되고, -월-일에 결제됩니다.

        btn_fund_share = findViewById(R.id.btn_fund_share); // 펀딩 공유하기
        btn_myfundcancle = findViewById(R.id.btn_myfundcancle); // 펀딩 취소하기


        //펀딩 공유하기
        btn_fund_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), JoinActivity.class)); //펀딩 공유의 건에 대한 아이디어 필요!!!!!!!!!!!!!!!!!!!!!

            }
        });

        //펀딩 취소하기
        btn_myfundcancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), JoinActivity.class)); // 펀딩이 취소되면 어떻게 할 건지, 데이터에 대한 건도 아이디어가 필요함!!!

            }
        });

    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}