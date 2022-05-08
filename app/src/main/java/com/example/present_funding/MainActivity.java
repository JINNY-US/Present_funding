package com.example.present_funding;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button goMarket, goWishlist; //마켓이동 버튼, 위시이동 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goMarket = findViewById(R.id.btn_market_go);
        goMarket.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), MarketActivity.class));

            }
        });

        goWishlist = findViewById(R.id.btn_wishlist_go);
        goWishlist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), WishlistActivity.class));

            }
        });
    }
}