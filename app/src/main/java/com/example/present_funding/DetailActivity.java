package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
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
    ImageButton wish_off, wish_on;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);

        product_img = findViewById(R.id.iv_img);
        product_brand = findViewById(R.id.txt_brand);
        product_name = findViewById(R.id.txt_name);
        product_price = findViewById(R.id.txt_price);

        Intent intent = getIntent();

        product_brand.setText(intent.getStringExtra("brand"));
        product_name.setText(intent.getStringExtra("name"));
        product_price.setText(intent.getStringExtra("price"));
        Serializable p_img = intent.getSerializableExtra("img");
        Glide.with(this).load(p_img).into(product_img);


    }
}