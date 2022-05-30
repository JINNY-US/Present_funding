package com.example.present_funding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView ivMenu;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

//    private RecyclerView re_student, re_merry, re_purchase; // 상품 나열할 뷰
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<Product> arrayList; // 상품 정보 저장할 리스트
//    private FirebaseDatabase database;
//    private DatabaseReference databaseReference;


    private BackPressCloseHandler backPressCloseHandler;

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ivMenu = findViewById(R.id.iv_menu);
//        re_student = (RecyclerView) findViewById(R.id.re_student);
//        re_merry = (RecyclerView) findViewById(R.id.re_merry);
//        re_purchase = (RecyclerView) findViewById(R.id.re_purchase);
//
//        layoutManager = new LinearLayoutManager(this);
//        re_student.setHasFixedSize(true);
//        re_merry.setHasFixedSize(true);
//        re_purchase.setHasFixedSize(true);
//
//        re_student.setLayoutManager(layoutManager);
//        re_merry.setLayoutManager(layoutManager);
//        re_purchase.setLayoutManager(layoutManager);
//
//        arrayList = new ArrayList<>(); //product 객체를 담는 리스트
//
//        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        backPressCloseHandler = new BackPressCloseHandler(this);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MypageActivity.class));
            }
        });

//        databaseReference = database.getReference("691");  // firebase에 저장되어 있는 Market에 대한 하위 데이터 수집
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //firebase의 데이터를 받아오는 곳
//                arrayList.clear(); //초기화
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Product product = snapshot.getValue(Product.class);         // Market에 대한 하위 데이터(getChildren)를 product(구조체? 형식)에 저장
//                    arrayList.add(product); //data를 recycler로 보낼 준비         // 미리 만들어둔 배열에 product 저장
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                //error 발생 시
//                Log.e("MarketActivity", String.valueOf(error.toException()));
//            }
//        });
//        adapter = new ProductAdapter(arrayList, this);
//        re_student.setAdapter(adapter); //adapter 연결
//        re_merry.setAdapter(adapter); //adapter 연결
//        re_purchase.setAdapter(adapter); //adapter 연결
//
//
//        DividerItemDecoration div = new DividerItemDecoration(re_student.getContext(), new LinearLayoutManager(this).getOrientation());
//        re_student.addItemDecoration(div); // 아이템 사이에 구분을 주기 위한 줄나눔
//        DividerItemDecoration div2 = new DividerItemDecoration(re_purchase.getContext(), new LinearLayoutManager(this).getOrientation());
//        re_purchase.addItemDecoration(div2); // 아이템 사이에 구분을 주기 위한 줄나눔
//        DividerItemDecoration div3 = new DividerItemDecoration(re_merry.getContext(), new LinearLayoutManager(this).getOrientation());
//        re_merry.addItemDecoration(div3); // 아이템 사이에 구분을 주기 위한 줄나눔


    }
    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}