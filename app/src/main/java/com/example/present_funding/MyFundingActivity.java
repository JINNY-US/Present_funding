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
    private TextView txt_product_price, txt_product_name, txt_mycurrunt_price, txt_lack_price, txt_information, txt_myfunding, txt_myitem_name, txt_my_fid, textview6;
    private ProgressBar my_progressBar;
    private Button btn_fund_share, btn_myfundcancle;
    private BackPressCloseHandler backPressCloseHandler;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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
        iv_myfunding = findViewById(R.id.iv_myfunding_img); //????????? ???????????? ????????? ??????
        txt_product_price = findViewById(R.id.txt_myachieve_price); //?????? ?????????
        txt_product_name = findViewById(R.id.txt_myitem_name); //?????????
        txt_mycurrunt_price = findViewById(R.id.txt_mycurrunt_price); //?????? ????????? -> ?????? collection ????????? ???????????? ??????
        txt_lack_price = findViewById(R.id.txt_lack_price); //????????? ?????? = ??????-??????
        txt_myitem_name = findViewById(R.id.txt_myitem_name);
        txt_information = findViewById(R.id.txt_information); // ??? ????????? -??? -?????? ????????????, -???-?????? ???????????????.
        //btn_fund_share = findViewById(R.id.btn_fund_share); // ?????? ????????????
        btn_myfundcancle = findViewById(R.id.btn_myfundcancle); // ?????? ????????????
        txt_my_fid = findViewById(R.id.txt_my_fid);
        //textview6 = findViewById(R.id.textView6);

        //Intent intent = getIntent(); // ProductAdapter?????? ????????? ?????????

        user = firebaseAuth.getCurrentUser(); //???????????? ????????? ?????? ????????????
        uid = user != null ? user.getUid() : null;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Fundings").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(uid != null){
                    my_name = snapshot.child("host_name").getValue(String.class);
                    txt_myfunding.setText(my_name+"?????? ?????? ??????");
                    my_img = snapshot.child("img").getValue(String.class);
                    my_prod_name = snapshot.child("name").getValue(String.class);
                    my_price = snapshot.child("price").getValue(String.class);
                    my_collection = snapshot.child("collection").getValue(String.class);
                    my_month = snapshot.child("month").getValue(String.class);
                    my_day = snapshot.child("day").getValue(String.class);
                    my_fid = snapshot.child("fid").getValue(String.class);

                    txt_my_fid.setText(my_fid);

                    txt_myitem_name.setText("?????????: " + my_prod_name);

                    Glide.with(iv_myfunding).load(my_img).into(iv_myfunding); // ????????? ??????

                    txt_product_price.setText("?????? ?????????: " + my_price);
                    txt_mycurrunt_price.setText("?????? ?????????: " + my_collection + " ???");

                    int int_price = Integer.parseInt(my_price.replaceAll("[\\D]", ""));
                    int int_collection = Integer.parseInt(my_collection.replaceAll("[\\D]", ""));

                    int lack_money = int_price - int_collection;
                    if(int_collection > int_price) {
                        lack_money = 0;
                    }
                    //int collect_percent = int_price / int_collection;

                    txt_lack_price.setText("?????? ??????: "+lack_money+" ???");
                    //my_progressBar.setProgress(collect_percent);

                    txt_information.setText("??? ????????? "+my_month+"??? "+my_day+"?????? ???????????????.");
                }else {
                    Toast.makeText(MyFundingActivity.this, "????????? ??????????????????. ?????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    //?????? ?????? ??????
    public void fundingCancle(View view) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(view.getContext());
        alt_bld.setMessage("????????? ?????????????????????????").setCancelable(false)
                .setPositiveButton("???",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {           // ????????? ???????????? ????????? ???, db?????? ????????? ?????? ????????? ???????????? ?????? ??????
                                mDatabase.child("Fundings").child(uid).removeValue();
                                Intent intent = new Intent(MyFundingActivity.this, MypageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("?????????",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();

        //????????? ?????? ??? ??? ?????? ??????????????? ??? ??????
        alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //????????? ?????? ??????
        alert.setTitle("?????? ??????");

        //????????? ????????? ??????
        alert.setIcon(R.drawable.exclamation);

        //????????? ?????? ??? ??????
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
                                .setTitle("????????? ????????? ??????????????????!")
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
                                startActivity(Intent.createChooser(Sharing_Intent, "???????????? ????????????"));
                            }
                            catch (Exception e) {
                            }
                        }
                    }
                });
    }

    //???????????? ??? ?????????????????? ??????
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