package com.example.present_funding;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MypageActivity extends Activity {

    Button go_profile, go_funding, go_ask;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase;
    FirebaseUser user;
    String uid;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        go_profile = findViewById(R.id.btn_my_profile);
        go_funding = findViewById(R.id.btn_my_funding);
        go_ask = findViewById(R.id.btn_my_ask);

        SharedPreferences sharedPreferences= getSharedPreferences("sFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //사용자 설정 변경 (이름, 비밀번호 등)
        go_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MyProfileActivity.class));
                finish();
            }
        });

        //나의 펀딩
        go_funding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
                uid = user != null ? user.getUid() : null;
                mDatabase = FirebaseDatabase.getInstance().getReference("Fundings");

                startActivity(new Intent(MypageActivity.this, NotExistMyfundingActivity.class));
                if(mDatabase.child(uid) != null) {
                    startActivity(new Intent(MypageActivity.this, MyFundingActivity.class));
                }
            }
        });

        //승인 요청 페이지
        go_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MypageActivity.this, AskListActivity.class));
            }
        });

    }

    //로그아웃 함수
    public void signOut(View view) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(view.getContext());
        alt_bld.setMessage("로그아웃 하시겠습니까?").setCancelable(false)
                .setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firebaseAuth.signOut();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(MypageActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();

        //대화창 클릭 시 뒷 배경 어두워지는 것 막기
        alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //대화창 제목 설정
        alert.setTitle("로그아웃");

        //대화창 아이콘 설정
        alert.setIcon(R.drawable.exclamation);

        //대화창 배경 색 설정
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(180, 180, 180)));
        alert.show();
    }

    //뒤로가기 시 메인페이지로 이동
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent;
        intent = new Intent(getApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
