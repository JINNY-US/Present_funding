package com.example.present_funding;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String brand, img_src;
    private TextView txt;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txt = findViewById(R.id.test_textview);
        imageView = findViewById(R.id.test_imageView);

        mDatabase = FirebaseDatabase.getInstance().getReference("10");

        /*ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                brand = snapshot.child("brand").getValue(String.class);
                Log.d("brand", brand);
                Toast.makeText(getApplication(), brand, Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };*/

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                brand = snapshot.child("brand").getValue(String.class);
                txt.setText(brand);

                img_src = snapshot.child("img").getValue(String.class);
                

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}