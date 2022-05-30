package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.security.PublicKey;

public class AddrSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_addr_search);

        WebView wb_addr = findViewById(R.id.webView_addr);
        wb_addr.getSettings().setJavaScriptEnabled(true);
        wb_addr.addJavascriptInterface(new BridgeInterfase(), "Android");
        wb_addr.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                //안드로이드 -> 자바스크립트 함수 호출
                wb_addr.loadUrl("javascript:sample2_execDaumPostcode();");
                super.onPageFinished(view, url);
            }
        });
        //최초 웹뷰 로드
        wb_addr.loadUrl("https://prensentfunding.web.app");
    }

    private class BridgeInterfase {
        @JavascriptInterface
        //주소 검색 API의 결과 값이 브릿지 통로를 통해 전달 받음( from javascript )
        public void processDATA(String data){
            Intent intent = new Intent();
            intent.putExtra("data", data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}