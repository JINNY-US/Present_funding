package com.example.present_funding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class AskActivity extends AppCompatActivity {
    TextView textView13, txt_ask_support_name, txt_ask_support_collection;
    Button btn_ask_false, btn_ask_true;

    String uid, sid, s_name, collection, s_collection, my_name, my_img, my_price, my_collection, my_month, my_day, my_fid, my_prod_name;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ask);

        backPressCloseHandler = new BackPressCloseHandler(this);

        textView13 = findViewById(R.id.textView13); // 대충 잘 확인하라는 글
        txt_ask_support_name = findViewById(R.id.txt_ask_support_name);     // 후원자명
        txt_ask_support_collection = findViewById(R.id.txt_ask_support_collection);     // 후원 금액
        btn_ask_false = findViewById(R.id.btn_ask_false);       // 승인
        btn_ask_true = findViewById(R.id.btn_ask_true);         // 거절

        Intent intent = getIntent(); // ProductAdapter에서 데이터 가져옴


        //sid = intent.getStringExtra("sid");
        s_name = intent.getStringExtra("s_name");
        s_collection = intent.getStringExtra("s_collection");

        txt_ask_support_name.setText("후원자명: "+s_name);
        txt_ask_support_collection.setText("후원 금액: "+s_collection +" 원");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        uid = user != null ? user.getUid() : null;
        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        btn_ask_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AskActivity.this , "펀딩 후원을 승인합니다.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(), MypageActivity.class));

                Map<String, Object> map = new HashMap<String, Object>();

                databaseReference = database.getReference();
                databaseReference.child("Fundings").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(user != null) {
                            my_collection = snapshot.child("collection").getValue(String.class);
                            int int_collection = Integer.parseInt(my_collection.replaceAll("[\\D]", ""));
                            int collection_input = Integer.parseInt(s_collection.replaceAll("[\\D]", ""));
                            int_collection += collection_input;
                            collection = String.valueOf(int_collection);
                            map.put("collection", collection);
                            databaseReference.child("Fundings").child(uid).updateChildren(map);         // 모금액이 s_collection 만큼 무한 증식하는 오류가 있음
                        } else{
                            Toast.makeText(AskActivity.this, "오류가 발생했습니다. 다시 시도 해주십시오.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_ask_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Temp").child(uid).removeValue();
                Toast.makeText(AskActivity.this , "펀딩 후원을 거절합니다.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(), MypageActivity.class));
            }
        });

    }

    private BackPressCloseHandler backPressCloseHandler;
    //뒤로가기 시 메인페이지로 이동
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}