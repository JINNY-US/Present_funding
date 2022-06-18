package com.example.present_funding;

import static android.os.Build.ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
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
    TextView txt_pay_for_host, txt_pay_prod_name, txt_pay_check;
    EditText txt_pay_input;
    String get_host_name, get_prod_name, get_fid, get_uid;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference mDatabase;
    FirebaseUser user;
    String uid, addr, addr_detail, collection, day, month, prod_img, prod_name, prod_price, host_name, fid;

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

        Intent intent = getIntent(); // FundingAdapter에서 데이터 가져옴

        get_host_name = intent.getStringExtra("send_host_name");
        get_prod_name = intent.getStringExtra("send_name");
        get_fid = intent.getStringExtra("send_fid");
        get_uid = intent.getStringExtra("send_uid");

        txt_pay_for_host.setText("호스트명: " + get_host_name);
        txt_pay_prod_name.setText("상품명: " + get_prod_name);
        String pay_input = String.valueOf(txt_pay_input.getText());
        txt_pay_check.setText(pay_input + " 원");

        int collection_input = Integer.parseInt(pay_input.replaceAll("[\\D]", ""));

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        uid = user != null ? user.getUid() : null;
        mDatabase = FirebaseDatabase.getInstance().getReference();



        btn_go_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Funding").child(get_uid); //.child("collection");

                ref.orderByChild("collection").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                String key = data.getKey();
                                String collection = data.child("collection").getValue().toString();
                                int int_collection = Integer.parseInt(collection.replaceAll("[\\D]", ""));
                                int_collection += collection_input;
                                collection = String.valueOf(int_collection);
                                ref.child("collection").setValue(collection);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }


//            private void FundingUpload(String host_name, String get_img, String get_brand, String get_name, String get_price, int month, int day, Editable addr, Editable addr_detail, int collection) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {
//                    String uid = user.getUid();
//
//                    HashMap<Object, String> funding = new HashMap<>();
//
//                    funding.put("collection", host_name);
//
//
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference userRef = database.getReference("Funding");
//                    userRef.child(uid).setValue(funding);
//
//                    startActivity(new Intent(getApplication(), FundingFinishActivity.class));
//
//                } else {
//                    Toast.makeText(FundingPaymentActivity.this , "로그인 상태 확인", Toast.LENGTH_LONG).show();// No user is signed in
//                }
//            }


        });

        btn_funding_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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