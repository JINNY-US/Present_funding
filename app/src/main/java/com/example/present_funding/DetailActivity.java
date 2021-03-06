package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView product_img;
    TextView product_brand, product_name, product_price;
    Button open_funding;

    String send_name, send_price, send_img, send_brand;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);

        backPressCloseHandler = new BackPressCloseHandler(this);

        product_img = findViewById(R.id.iv_img); //상품 이미지
        product_brand = findViewById(R.id.txt_brand); //상품 브랜드
        product_name = findViewById(R.id.txt_name); // 상품 이름
        product_price = findViewById(R.id.txt_price); // 상품 가격
        open_funding = findViewById(R.id.btn_open_funding); // 펀딩 오픈하기 버튼

        Intent intent = getIntent(); // ProductAdapter에서 데이터 가져옴


        send_name = intent.getStringExtra("name");
        send_price = intent.getStringExtra("price");
        send_img = intent.getStringExtra("img");
        send_brand = intent.getStringExtra("brand");

        product_brand.setText(send_brand); // 브랜드 태그에서 브랜드 불러오기
        product_name.setText(send_name); // 이름 태그에서 이름 불러오기
        product_price.setText(send_price); // 가격 태그에서 가격 불러오기
        Serializable p_img = intent.getSerializableExtra("img"); // 이미지 가져오기
        Glide.with(this).load(p_img).into(product_img); // 이미지 적용

        open_funding.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent2 = new Intent(this, FundingOpenActivity.class); // 데이터를 전송할 activity 설정
        intent2.putExtra("send_name", send_name);
        intent2.putExtra("send_price", send_price);
        intent2.putExtra("send_img", send_img);
        intent2.putExtra("send_brand", send_brand);
        startActivity(intent2);

    }

    //뒤로가기 시 메인페이지로 이동
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent;
        intent = new Intent(getApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}