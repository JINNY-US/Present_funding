package com.example.present_funding;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class MyFundingActivity extends AppCompatActivity {

    private ImageView iv_myfunding;
    private TextView txt_product_price, txt_product_name, txt_mycurrunt_price, txt_lack_price, txt_information;
    private ProgressBar my_progressBar;
    private Button btn_fund_share, btn_myfundcancle;
    private BackPressCloseHandler backPressCloseHandler;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    SharedPreferences.Editor editor;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_funding);

        SharedPreferences sharedPreferences= getSharedPreferences("sFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        backPressCloseHandler = new BackPressCloseHandler(this);

        iv_myfunding = findViewById(R.id.iv_myfunding_img); //펀딩을 진행하는 이미지 필요
        txt_product_price = findViewById(R.id.txt_myachieve_price); //목표 달성액
        txt_product_name = findViewById(R.id.txt_myitem_name); //상품명
        txt_mycurrunt_price = findViewById(R.id.txt_mycurrunt_price); //현재 달성액 -> 이건 collection 데이터 불러오면 될듯
        txt_lack_price = findViewById(R.id.txt_lack_price); //부족한 금액 = 목표-현재
        my_progressBar = findViewById(R.id.my_progressBar); // 진행 그래프?

        txt_information = findViewById(R.id.txt_information); // 이 펀딩은 -월 -일에 마감되고, -월-일에 결제됩니다.

        btn_fund_share = findViewById(R.id.btn_fund_share); // 펀딩 공유하기
        btn_myfundcancle = findViewById(R.id.btn_myfundcancle); // 펀딩 취소하기

        Intent intent = getIntent(); // ProductAdapter에서 데이터 가져옴
        Serializable p_img = intent.getSerializableExtra("img"); // 이미지 가져오기
        Glide.with(this).load(p_img).into(iv_myfunding); // 이미지 적용


        //펀딩 공유하기
        btn_fund_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                //startActivity(new Intent(getApplication(), JoinActivity.class)); //펀딩 공유의 건에 대한 아이디어 필요!!!!!!!!!!!!!!!!!!!!!

            }
        });

        //펀딩 취소하기
        btn_myfundcancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), JoinActivity.class)); // 펀딩이 취소되면 어떻게 할 건지, 데이터에 대한 건도 아이디어가 필요함!!!

            }
        });

    }

    //펀딩 취소 함수
    public void fundingCancle(View view) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(view.getContext());
        alt_bld.setMessage("펀딩을 취소하시겠습니까?").setCancelable(false)
                .setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {           // 펀딩을 취소하게 되었을 때, db에서 펀딩에 대한 내용을 삭제하는 코드 필요
                                //firebaseAuth.signOut();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(MyFundingActivity.this, MypageActivity.class);
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
        alert.setTitle("펀딩 취소");

        //대화창 아이콘 설정
        alert.setIcon(R.drawable.exclamation);

        //대화창 배경 색 설정
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(180, 180, 180)));
        alert.show();
    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}