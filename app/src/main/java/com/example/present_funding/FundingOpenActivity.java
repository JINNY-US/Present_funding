package com.example.present_funding;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;

public class FundingOpenActivity extends AppCompatActivity {

    private Button btn_date, btn_open;
    private TextView txt_prod_price, txt_prod_name, txt_choicedate;
    private EditText txt_addr_detail, txt_addr;

    String get_name, get_price, get_img, get_brand;
    private int Month, Day;
    private int Collection;

    private BackPressCloseHandler backPressCloseHandler;

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

        get_name = intent.getStringExtra("send_name");
        get_price = intent.getStringExtra("send_price");
        get_img = intent.getStringExtra("send_img");
        get_img = intent.getStringExtra("send_brand");

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

                Collection = 0;
                FundingUpload(get_img, get_brand, get_name, get_price, Month, Day, txt_addr.getText(), txt_addr_detail.getText(), Collection);

            }

            private void FundingUpload(String get_img, String get_brand, String get_name, String get_price, int month, int day, Editable addr, Editable addr_detail, int collection) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(FundingOpenActivity.this , "펀딩 오픈 성공!", Toast.LENGTH_LONG).show();

                    String uid = user.getUid();

                    HashMap<Object, String> funding = new HashMap<>();

                    funding.put("uid", uid);
                    funding.put("prod_img", get_img);
                    funding.put("prod_brand", get_brand);
                    funding.put("prod_name", get_name);
                    funding.put("prod_price", get_price);
                    funding.put("month", String.valueOf(month));
                    funding.put("day", String.valueOf(day));
                    funding.put("addr", String.valueOf(addr));
                    funding.put("addr_detail", String.valueOf(addr_detail));
                    funding.put("collection", String.valueOf(collection));

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userRef = database.getReference("Funding");
                    userRef.child(uid).setValue(funding);

                    Intent intent2 = new Intent(FundingOpenActivity.this, MyFundingActivity.class);
                    intent2.putExtra("send_img", get_img);
                    startActivity(intent2);

                } else {
                    Toast.makeText(FundingOpenActivity.this , "로그인 상태 확인", Toast.LENGTH_LONG).show();// No user is signed in
                }
            }
        });


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

    // 뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}