package com.example.present_funding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FundingPaymentActivity extends AppCompatActivity {

    Button btn_go_finish, btn_funding_cancle;
    TextView txt_pay_for_host, txt_pay_prod_name, txt_pay_check, textView3, textView9, textView10;
    //EditText txt_pay_input;
    String get_host_name, get_prod_name, get_fid, get_uid;
    String pay_input;
    //int pay_input;

    private ArrayList<Fundings> arrayList; // 정보 저장할 리스트

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
        get_host_name = intent.getStringExtra("host_name");
        get_prod_name = intent.getStringExtra("name");
        get_fid = intent.getStringExtra("fid");
        get_uid = intent.getStringExtra("uid");
//
        txt_pay_for_host.setText("호스트명: " + get_host_name);
        txt_pay_prod_name.setText("상품명: " + get_prod_name);

        txt_pay_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //텍스트 입력이 모두 끝았을때 Call back
                //String pay_input = String.valueOf(txt_pay_input.getText());
                //txt_pay_check.setText(pay_input + " 원");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //텍스트가 변경 될때마다 Call back
                //pay_input = Integer.parseInt(String.valueOf(txt_pay_input.getText()));
                pay_input = String.valueOf(txt_pay_input.getText());
                txt_pay_check.setText(pay_input + " 원");
            }

            @Override
            public void afterTextChanged(Editable editable) {   //텍스트가 입력하기 전에 Call back

            }
        });


        //int collection_input = Integer.parseInt(pay_input.replaceAll("[\\D]", ""));
//
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기

        btn_go_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    database = FirebaseDatabase.getInstance(); //파이어베이스 연동

                    databaseReference = database.getReference("Fundings").child(get_uid).child("collection");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //firebase의 데이터를 받아오는 곳
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //        int collection = Integer.parseInt(snapshot.getValue().toString());
                                //String collection = snapshot.getValue().toString();
                                //int int_collection = Integer.parseInt(collection.replaceAll("[\\D]", ""));
                                //int collection_input = Integer.parseInt(pay_input.replaceAll("[\\D]", ""));
                        //        collection += pay_input;
                                //collection = String.valueOf(int_collection);
                        //        databaseReference.child("collection").setValue(collection);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //error 발생 시
                            Log.e("FundingPaymentActivity", String.valueOf(error.toException()));
                        }
                    });
                } else {
                    Toast.makeText(FundingPaymentActivity.this , "로그인 상태 확인", Toast.LENGTH_LONG).show();// No user is signed in
                }

                startActivity(new Intent(getApplication(), FundingFinishActivity.class));

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

    // collection update를 위한 함수
//    public void collectionUpdate(boolean add){
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        Map<String, Object> childUpdates = new HashMap<>();
//        Map<String, Object> fundingValues = null;
//        if(add){
//            Funding funding = new Funding(addr, addr_detail, collection, day, month, prod_img, prod_name, prod_price, host_name, fid);
//            fundingValues = Funding.toMap();
//        }
//        childUpdates.put("/id_list/" + ID, fundingValues);
//        mDatabase.updateChildren(childUpdates);
//    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //backPressCloseHandler.onBackPressed();
    }
}