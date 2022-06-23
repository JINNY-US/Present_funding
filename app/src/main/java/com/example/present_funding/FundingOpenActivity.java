package com.example.present_funding;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

public class FundingOpenActivity extends AppCompatActivity {

    private Button btn_date, btn_open;
    private TextView txt_prod_price, txt_prod_name, txt_choicedate;
    private EditText txt_addr_detail, txt_addr;

    String get_name, get_price, get_img, get_brand, host_name, fid;
    private int Month, Day;
    private int collection;

    private BackPressCloseHandler backPressCloseHandler;

    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_funding_open);

        backPressCloseHandler = new BackPressCloseHandler(this);

        btn_date = findViewById(R.id.btn_datepicker); //날찌 선택 버튼
        btn_open = findViewById(R.id.btn_open_funding_final); // 펀딩 오픈 버튼

        txt_prod_name = findViewById(R.id.txt_cho_prod_name); //선택 상품명
        txt_prod_price = findViewById(R.id.txt_cho_prod_price); //선택 상품가격
        txt_choicedate = findViewById(R.id.txt_choicedate); //선택한 날짜
        txt_addr = findViewById(R.id.txt_addr); //주소 입력창
        txt_addr_detail = findViewById(R.id.txt_addr_detail); //상세 주소 입력창

        Intent intent = getIntent(); // ProductAdapter에서 데이터 가져옴

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        uid = user != null ? user.getUid() : null;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(uid != null){
                    host_name = snapshot.child("name").getValue(String.class);
                }else {
                    Toast.makeText(FundingOpenActivity.this, "오류가 발생했습니다. 다시 시도 해주십시오.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        get_name = intent.getStringExtra("send_name");
        get_price = intent.getStringExtra("send_price");
        get_img = intent.getStringExtra("send_img");
        get_brand = intent.getStringExtra("send_brand");

        if(get_name != null && get_price != null) {
            txt_prod_name.setText(get_name); // 이름 태그에서 이름 불러오기
            txt_prod_price.setText(get_price); // 가격 태그에서 가격 불러오기
        }

        txt_addr.setFocusable(false);
        txt_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FundingOpenActivity.this, AddrSearchActivity.class);
                getSearchResult.launch(intent);
            }
        });

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collection = 0;
                FundingUpload(host_name, get_img, get_brand, get_name, get_price, Month, Day, txt_addr.getText(), txt_addr_detail.getText(), collection, fid, uid);

            }

            private void FundingUpload(String host_name, String img, String brand, String name, String price, int month, int day, Editable addr, Editable addr_detail, int collection, String fid, String uid) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(FundingOpenActivity.this , "펀딩이 성공적으로 오픈되었습니다!", Toast.LENGTH_LONG).show();

                    uid = user.getUid();
                    fid = random();

                    HashMap<Object, String> funding = new HashMap<>();

                    funding.put("uid", uid);
                    funding.put("fid", fid);
                    funding.put("host_name", host_name);
                    funding.put("img", get_img);
                    funding.put("brand", get_brand);
                    funding.put("name", get_name);
                    funding.put("price", get_price);
                    funding.put("addr", String.valueOf(addr));
                    funding.put("addr_detail", String.valueOf(addr_detail));
                    funding.put("month", String.valueOf(month));
                    funding.put("day", String.valueOf(day));
                    funding.put("collection", String.valueOf(collection));

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userRef = database.getReference("Fundings");
                    userRef.child(uid).setValue(funding);

                    Intent intent2 = new Intent(FundingOpenActivity.this, MyFundingActivity.class);
//                    intent2.putExtra("send_img", get_img);
//                    intent2.putExtra("send_price", get_price);
//                    intent2.putExtra("send_hostname", host_name);
//                    intent2.putExtra("send_collection", String.valueOf(collection));
//                    intent2.putExtra("send_month", String.valueOf(month));
//                    intent2.putExtra("send_day", String.valueOf(day));
                    startActivity(intent2);

                } else {
                    Toast.makeText(FundingOpenActivity.this , "로그인 상태 확인", Toast.LENGTH_LONG).show();// No user is signed in
                }
            }
        });


    }

    //invite code random generator
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = (generator.nextInt(48) + 32);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        Month = month+1;
        Day = day;
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String dateMessage = (month_string + "월" + day_string + "일 펀딩 마감");

        txt_choicedate.setText(dateMessage);
    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    if(result.getData() != null){
                        String data =  result.getData().getStringExtra("data");
                        txt_addr.setText(data);
                    }
                }
            }
    );

    //뒤로가기 시 메인페이지로 이동
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent;
        intent = new Intent(getApplication(), MarketActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}