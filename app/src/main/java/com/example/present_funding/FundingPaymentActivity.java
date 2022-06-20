package com.example.present_funding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FundingPaymentActivity extends AppCompatActivity {

    Button btn_go_finish, btn_funding_cancle;
    TextView txt_pay_for_host, txt_pay_prod_name, txt_pay_check, textView3, textView9, textView10;
    String get_host_name, get_prod_name, get_fid, get_uid, get_month, get_day, get_img, get_brand, get_price, get_addr, get_addr_detail, get_collection;
    String pay_input, collection, get_Age, get_Support_uid;
    int int_collection, collection_input, get_age;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser user;
    //String uid; //, addr, addr_detail, collection, day, month, prod_img, prod_name, prod_price, host_name, fid;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_funding_payment);

        backPressCloseHandler = new BackPressCloseHandler(this);

        btn_go_finish = findViewById(R.id.btn_fund_ok);
        btn_funding_cancle = findViewById(R.id.btn_fund_cancle);

        txt_pay_for_host = findViewById(R.id.txt_pay_for_host); //호스트명:
        txt_pay_prod_name = findViewById(R.id.txt_pay_prod_name); //상품명:
        txt_pay_check = findViewById(R.id.txt_pay_check);   // ~~원 확인창
        EditText txt_pay_input =  (EditText) findViewById(R.id.txt_pay_input); //~원 입력창

        textView3 = findViewById(R.id.textView3);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);

        Intent intent = getIntent();
//
        get_uid = intent.getStringExtra("uid");
        get_fid = intent.getStringExtra("fid");
        get_host_name = intent.getStringExtra("host_name");
        get_img = intent.getStringExtra("img");
        get_brand = intent.getStringExtra("brand");
        get_prod_name = intent.getStringExtra("name");
        get_price = intent.getStringExtra("price");
        get_addr = intent.getStringExtra("addr");
        get_addr_detail = intent.getStringExtra("addr_detail");
        get_month = intent.getStringExtra("month");
        get_day = intent.getStringExtra("day");
        get_collection = intent.getStringExtra("collection");
//
        txt_pay_for_host.setText("호스트명: " + get_host_name);
        txt_pay_prod_name.setText("상품명: " + get_prod_name);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        txt_pay_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //텍스트가 변경 될때마다 Call back
                pay_input = String.valueOf(txt_pay_input.getText());
                txt_pay_check.setText(pay_input + " 원");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        btn_go_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (user != null) {

                    Map<String, Object> map = new HashMap<String, Object>();

                    databaseReference = database.getReference();

//                    databaseReference.child("Users").child(get_Support_uid).child("age").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            get_Age = (String) snapshot.getValue(String.class);
//                            //get_age = Integer.parseInt(get_Age);
//
//                            //boolean cons_val = check_consume_val(get_age, pay_input, get_price);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                    int int_collection = Integer.parseInt(get_collection.replaceAll("[\\D]", ""));
                    int collection_input = Integer.parseInt(pay_input.replaceAll("[\\D]", ""));
                    int_collection += collection_input;
                    collection = String.valueOf(int_collection);
                    map.put("collection", collection);
                    databaseReference.child("Fundings").child(get_uid).updateChildren(map);


                } else {
                    Toast.makeText(FundingPaymentActivity.this , "로그인 후 이용해 주세요.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), LoginActivity.class));
                }

                Intent intent2 = new Intent(FundingPaymentActivity.this, FundingFinishActivity.class); // 데이터를 전송할 activity 설정
                intent2.putExtra("host_name", get_host_name);
                intent2.putExtra("payment", pay_input);
                intent2.putExtra("month", get_month);
                intent2.putExtra("day", get_day);

                startActivity(intent2);

            }
        });
//
        btn_funding_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FundingPaymentActivity.this , "펀딩 후원을 취소합니다.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(), OpenedFundingActivity.class));
            }
        });
    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //backPressCloseHandler.onBackPressed();
    }
}