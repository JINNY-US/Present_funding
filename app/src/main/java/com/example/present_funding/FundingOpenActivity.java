package com.example.present_funding;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FundingOpenActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;

    private Button btn_date, btn_open;
    private TextView txt_prod_price, txt_prod_name, txt_choicedate;
    private EditText txt_addr_detail, txt_addr;

    String get_name, get_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_funding_open);

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


    }




    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
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

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}