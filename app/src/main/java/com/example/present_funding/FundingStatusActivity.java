package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class FundingStatusActivity extends AppCompatActivity {

    Button go_payment;
    TextView txt_funding_host, txt_achieve_price, txt_item_name, txt_currunt_price, txt_lack_price, txt_deadline_info;
    ImageView iv_funding_img;
    ProgressBar progressBar;

    private BackPressCloseHandler backPressCloseHandler;

    String get_host_name, get_prod_name, get_prod_price, get_prod_img, get_collection, get_month, get_day, get_fid, get_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_funding_status);

        backPressCloseHandler = new BackPressCloseHandler(this);
        Intent intent = getIntent(); // FundingAdapter에서 데이터 가져옴

        go_payment = findViewById(R.id.btn_go_payment); // 펀딩하기 버튼
        txt_funding_host = findViewById(R.id.txt_funding_host); // ~님의 펀딩
        txt_achieve_price = findViewById(R.id.txt_achieve_price); // 목표 달성액:
        txt_item_name = findViewById(R.id.txt_item_name);   // 상품명:
        txt_currunt_price = findViewById(R.id.txt_currunt_price); // 현재 달성액: (collection 사용)
        txt_lack_price = findViewById(R.id.txt_lack_price);     // 부족 금액:
        txt_deadline_info = findViewById(R.id.txt_deadline_info);   // 이 펀딩은 ~월 ~일에 마감됩니다.
        iv_funding_img = findViewById(R.id.iv_funding_img);
        //ProgressBar status_progressBar = (ProgressBar) findViewById(R.id.progressBar); // 진행 그래프?

        get_host_name = intent.getStringExtra("host_name");
        get_prod_name = intent.getStringExtra("name");
        get_prod_price = intent.getStringExtra("price");
        get_prod_img = intent.getStringExtra("img");
        get_collection = intent.getStringExtra("collection");
        get_month = intent.getStringExtra("month");
        get_day = intent.getStringExtra("day");
        get_fid = intent.getStringExtra("fid");
        get_uid = intent.getStringExtra("uid");

        int int_price = Integer.parseInt(get_prod_price.replaceAll("[\\D]", ""));
        int int_collection = Integer.parseInt(get_collection.replaceAll("[\\D]", ""));

        int lack_money = int_price - int_collection;

        Serializable p_img = intent.getSerializableExtra("img"); // 이미지 가져오기
        Glide.with(this).load(p_img).into(iv_funding_img); // 이미지 적용

        txt_funding_host.setText(get_host_name + "님의 펀딩");
        txt_achieve_price.setText("목표 달성액: " + get_prod_price + " 원");

        txt_item_name.setText("상품명: " + get_prod_name);

        txt_currunt_price.setText("현재 달성액: " + get_collection + " 원");
        txt_lack_price.setText("부족한 금액: " + lack_money + " 원");

        txt_deadline_info.setText("이 펀딩은 " + get_month + "월" + get_day + "일에 마감됩니다.");

        go_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplication(), FundingPaymentActivity.class));
                Intent intent2 = new Intent(getApplication(), FundingPaymentActivity.class); // 데이터를 전송할 activity 설정
                intent2.putExtra("send_host_name", get_host_name);
                intent2.putExtra("send_name", get_prod_name);
                intent2.putExtra("send_fid", get_fid);
                intent2.putExtra("send_uid", get_uid);
                startActivity(intent2);
            }
        });

//        go_payment.setOnClickListener((View.OnClickListener) this);
//        @Override
//        public void onClick(View view) {
//            Intent intent2 = new Intent(this, FundingOpenActivity.class); // 데이터를 전송할 activity 설정
//            intent2.putExtra("send_name", send_name);
//            intent2.putExtra("send_price", send_price);
//            intent2.putExtra("send_img", send_img);
//            intent2.putExtra("send_brand", send_brand);
//            startActivity(intent2);
//
//        }


    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //backPressCloseHandler.onBackPressed();
    }
}