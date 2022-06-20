package com.example.present_funding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AskListActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // 상품 나열할 뷰
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Temp> arrayList; // 상품 정보 저장할 리스트
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    String uid;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ask_list);

        backPressCloseHandler = new BackPressCloseHandler(this);

        layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.re_ask_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>(); //product 객체를 담는 리스트

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        uid = user != null ? user.getUid() : null;

        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        databaseReference = database.getReference("Temp");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //firebase의 데이터를 받아오는 곳
                arrayList.clear(); //초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Temp temp = snapshot.getValue(Temp.class);         // Funding에 대한 하위 데이터(getChildren)를 openedfunding(구조체? 형식)에 저장
                    arrayList.add(temp); //data를 recycler로 보낼 준비         // 미리 만들어둔 배열에 저장
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error 발생 시
                Log.e("AskListActivity", String.valueOf(error.toException()));        // 에러 로그 출력
            }
        });
        adapter = new TempAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //adapter 연결

        DividerItemDecoration div = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(div); // 아이템 사이에 구분을 주기 위한 줄나눔
    }

    private BackPressCloseHandler backPressCloseHandler;
    //뒤로가기 시 메인페이지로 이동
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}