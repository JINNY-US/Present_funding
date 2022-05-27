package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    ImageView product_img;
    TextView product_brand, product_name, product_price;
    Button open_funding;
    ImageButton wish_onoff;
    boolean i = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);

        product_img = findViewById(R.id.iv_img); //상품 이미지
        product_brand = findViewById(R.id.txt_brand); //상품 브랜드
        product_name = findViewById(R.id.txt_name); // 상품 이름
        product_price = findViewById(R.id.txt_price); // 상품 가격
        open_funding = findViewById(R.id.btn_open_funding); // 펀딩 오픈하기 버튼
        wish_onoff = findViewById(R.id.btn_wish); // 위시리스트 추가 버튼, 찜 버튼

        Intent intent = getIntent(); // ProductAdapter에서 데이터 가져옴

        product_brand.setText(intent.getStringExtra("brand")); // 브랜드 태그에서 브랜드 불러오기
        product_name.setText(intent.getStringExtra("name")); // 이름 태그에서 이름 불러오기
        product_price.setText(intent.getStringExtra("price")); // 가격 태그에서 가격 불러오기
        Serializable p_img = intent.getSerializableExtra("img"); // 이미지 가져오기
        Glide.with(this).load(p_img).into(product_img); // 이미지 적용

        open_funding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MarketActivity.class)); // 펀딩 오픈 activity 만든 후에 바꿔야함!!!!!!!!!!!!!
            }
        });

        wish_onoff.setOnClickListener(new View.OnClickListener() { // on off 이미지 바꾸는 건 성공
            @Override                                               // 바꾼 이미지 (on, off)의 정보를 DB에 저장해야 함!!!!!!!!!!!!
            public void onClick(View v) {
                if (i == true){
                    wish_onoff.setImageResource(android.R.drawable.btn_star_big_off);
                    i = false;
                }else {
                    wish_onoff.setImageResource(android.R.drawable.btn_star_big_on);
                    i = true;
                }
            }
        });
    }
}