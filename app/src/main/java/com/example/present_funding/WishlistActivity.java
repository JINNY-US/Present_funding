package com.example.present_funding;

import android.app.Activity;

public class WishlistActivity extends Activity {

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        backPressCloseHandler = new BackPressCloseHandler(this);

    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
