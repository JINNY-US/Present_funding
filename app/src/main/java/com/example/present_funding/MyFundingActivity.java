package com.example.present_funding;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class MyFundingActivity extends AppCompatActivity {

    private ImageView iv_myfunding;
    private TextView txt_product_price, txt_product_name, txt_mycurrunt_price, txt_lack_price, txt_information, txt_myfunding, txt_myitem_name;
    private ProgressBar my_progressBar;
    private Button btn_fund_share, btn_myfundcancle;
    private BackPressCloseHandler backPressCloseHandler;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //abstract class Context;

    WebView web;

    SharedPreferences.Editor editor;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference mDatabase;
    FirebaseUser user;
    String uid, my_name, my_img, my_price, my_collection, my_month, my_day, my_fid, my_prod_name;
    String subject, PageURL, ImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_funding);

        SharedPreferences sharedPreferences= getSharedPreferences("sFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        backPressCloseHandler = new BackPressCloseHandler(this);

        txt_myfunding = findViewById(R.id.txt_myfunding);
        iv_myfunding = findViewById(R.id.iv_myfunding_img); //펀딩을 진행하는 이미지 필요
        txt_product_price = findViewById(R.id.txt_myachieve_price); //목표 달성액
        txt_product_name = findViewById(R.id.txt_myitem_name); //상품명
        txt_mycurrunt_price = findViewById(R.id.txt_mycurrunt_price); //현재 달성액 -> 이건 collection 데이터 불러오면 될듯
        txt_lack_price = findViewById(R.id.txt_lack_price); //부족한 금액 = 목표-현재
        txt_myitem_name = findViewById(R.id.txt_myitem_name);
        //web = findViewById(R.id.wv_share);

        //ProgressBar my_progressBar = (ProgressBar) findViewById(R.id.my_progressBar); // 진행 그래프?

        txt_information = findViewById(R.id.txt_information); // 이 펀딩은 -월 -일에 마감되고, -월-일에 결제됩니다.

        btn_fund_share = findViewById(R.id.btn_fund_share); // 펀딩 공유하기
        btn_myfundcancle = findViewById(R.id.btn_myfundcancle); // 펀딩 취소하기

        //Intent intent = getIntent(); // ProductAdapter에서 데이터 가져옴

        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        uid = user != null ? user.getUid() : null;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Fundings").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(uid != null){
                    my_name = snapshot.child("host_name").getValue(String.class);
                    txt_myfunding.setText(my_name+"님의 펀딩 현황");
                    my_img = snapshot.child("img").getValue(String.class);
                    my_prod_name = snapshot.child("name").getValue(String.class);
                    my_price = snapshot.child("price").getValue(String.class);
                    my_collection = snapshot.child("collection").getValue(String.class);
                    my_month = snapshot.child("month").getValue(String.class);
                    my_day = snapshot.child("day").getValue(String.class);
                    my_fid = snapshot.child("fid").getValue(String.class);

                    txt_myitem_name.setText("상품명: " + my_prod_name);

                    Glide.with(iv_myfunding).load(my_img).into(iv_myfunding); // 이미지 적용

                    txt_product_price.setText("목표 달성액: " + my_price);
                    txt_mycurrunt_price.setText("현재 달성액: " + my_collection + " 원");

                    int int_price = Integer.parseInt(my_price.replaceAll("[\\D]", ""));
                    int int_collection = Integer.parseInt(my_collection.replaceAll("[\\D]", ""));

                    int lack_money = int_price - int_collection;
                    //int collect_percent = int_price / int_collection;

                    txt_lack_price.setText("부족 금액: "+lack_money+" 원");
                    //my_progressBar.setProgress(collect_percent);

                    txt_information.setText("이 펀딩은 "+my_month+"월 "+my_day+"일에 마감됩니다.");
                }else {
                    Toast.makeText(MyFundingActivity.this, "오류가 발생했습니다. 다시 시도 해주십시오.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //펀딩 공유하기
        btn_fund_share.setOnClickListener(new View.OnClickListener() {      // 팝업창 띄워서 초대 코드 복사하기

            @Override
            public void onClick(View v) {

                subject = my_name+"님의 " + my_prod_name + "을 위한 펀딩이 오픈되었어요! 선물 펀딩 앱에서 확인해보세요.";
                PageURL = String.valueOf(uid.startsWith(String.valueOf((FundingStatusActivity.class))));
                ImgUrl = my_img;
//                                        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                                        List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(1);
//                                        ComponentName componentName= info.get(0).topActivity;
//                                        String ActivityName = componentName.getShortClassName().substring(1);
                Create_DynamicLink(subject, PageURL, ImgUrl);


//                AlertDialog.Builder alt_bld = new AlertDialog.Builder(v.getContext());
//                alt_bld.setMessage("펀딩 고유 아이디를 복사하여 공유해보세요!").setCancelable(false)
//                        .setPositiveButton("네",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {   // 클립보드에 복사되는 기능 필요
////                                        Intent intent = new Intent(MyFundingActivity.this, MypageActivity.class);
////                                        startActivity(intent);
////                                        finish();
//
//                                    }
//                                }).setNegativeButton("아니오",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//                AlertDialog alert = alt_bld.create();
//
//                //대화창 클릭 시 뒷 배경 어두워지는 것 막기
//                alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//
//                //대화창 제목 설정
//                alert.setTitle("펀딩 공유");
//
//                //대화창 아이콘 설정
//                alert.setIcon(R.drawable.exclamation);
//
//                //대화창 배경 색 설정
//                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(180, 180, 180)));
//                alert.show();
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
                                mDatabase.child("Fundings").child(uid).removeValue();
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

    public void Create_DynamicLink(final String subject, String PageURL, String ImgUrl){
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(PageURL))
                .setDomainUriPrefix("https://presentfunding.page.link/Tbeh")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder(getPackageName())
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("오픈된 펀딩을 공유해보세요!")
                                .setImageUrl(Uri.parse(ImgUrl))
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            Uri ShortLink = task.getResult().getShortLink();
                            try {
                                Intent Sharing_Intent = new Intent();
                                Sharing_Intent.setAction(Intent.ACTION_SEND);
                                Sharing_Intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                Sharing_Intent.putExtra(Intent.EXTRA_TEXT, ShortLink.toString());
                                Sharing_Intent.setType("text/plain");
                                startActivity(Intent.createChooser(Sharing_Intent, "친구에게 공유하기"));
                            }
                            catch (Exception e) {
                            }
                        }
                    }
                });
    }

    //뒤로가기 시 마이페이지로 이동
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent;
        intent = new Intent(getApplication(), MypageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}