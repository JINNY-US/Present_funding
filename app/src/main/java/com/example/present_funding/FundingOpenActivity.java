package com.example.present_funding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FundingOpenActivity extends AppCompatActivity {

    private Button btn_date, btn_addr, btn_open;
    private TextView txt_prod_price, txt_prod_name,txt_addr_detail, txt_addr, txt_zipcode, txt_choicedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_funding_open);

        btn_date = findViewById(R.id.btn_datepicker);
        btn_addr = findViewById(R.id.btn_addr_search);
        btn_open = findViewById(R.id.btn_open_funding_final);

        txt_prod_price = findViewById(R.id.txt_cho_prod_price);
        txt_prod_name = findViewById(R.id.txt_cho_prod_name);
        txt_addr = findViewById(R.id.txt_addr);
        txt_addr_detail = findViewById(R.id.txt_addr_detail);
        txt_zipcode = findViewById(R.id.txt_zipcode);
        txt_choicedate = findViewById(R.id.txt_choicedate);


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
}