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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    TextView txt_pay_for_host, txt_pay_prod_name, txt_pay_check, textView3, textView9, textView10;
    String get_host_name, get_prod_name, get_fid, get_uid, get_month, get_day, get_img, get_brand, get_price, get_addr, get_addr_detail, get_collection;
    String pay_input, collection, get_Age, get_Support_uid, get_Support_name;
    int int_collection, collection_input, get_age;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser user;
    //String uid; //, addr, addr_detail, collection, day, month, prod_img, prod_name, prod_price, host_name, fid;

    private BackPressCloseHandler backPressCloseHandler;

    boolean cons_val;

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
        get_uid = intent.getStringExtra("uid");
        get_fid = intent.getStringExtra("fid");
        get_host_name = intent.getStringExtra("host_name");
        get_img = intent.getStringExtra("img");
        get_brand = intent.getStringExtra("brand");
        get_prod_name = intent.getStringExtra("name");
        get_price = intent.getStringExtra("price");
        get_addr = intent.getStringExtra("addr");
        get_addr_detail = intent.getStringExtra("addr_detail");
        get_month = intent.getStringExtra("month");
        get_day = intent.getStringExtra("day");
        get_collection = intent.getStringExtra("collection");
//
        txt_pay_for_host.setText("호스트명: " + get_host_name);
        txt_pay_prod_name.setText("상품명: " + get_prod_name);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        get_Support_uid = user != null ? user.getUid() : null;
        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        txt_pay_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //텍스트가 변경 될때마다 Call back
                pay_input = String.valueOf(txt_pay_input.getText());
                txt_pay_check.setText(pay_input + " 원");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btn_go_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {

                    Map<String, Object> map = new HashMap<String, Object>();

                    databaseReference = database.getReference();
                    databaseReference.child("Users").child(get_Support_uid).child("age").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            get_Age = (String) snapshot.getValue(String.class);
                            get_age = Integer.parseInt(get_Age);

                            cons_val = check_consume_val(get_age, pay_input, get_price);

                            if(cons_val == false) {
                                int int_collection = Integer.parseInt(get_collection.replaceAll("[\\D]", ""));
                                int collection_input = Integer.parseInt(pay_input.replaceAll("[\\D]", ""));
                                int_collection += collection_input;
                                collection = String.valueOf(int_collection);
                                map.put("collection", collection);
                                databaseReference.child("Fundings").child(get_uid).updateChildren(map);
                            }

                            if(cons_val == true){
                                HashMap<Object, String> userTemp = new HashMap<>();

                                databaseReference.child("Users").child(get_Support_uid).child("name").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        get_Support_name = (String) snapshot.getValue(String.class);
                                        userTemp.put("support_uid", get_Support_uid);
                                        userTemp.put("supoort_name", get_Support_name); //후원자명
                                        userTemp.put("temp", pay_input);
                                        userTemp.put("val", String.valueOf(true));

                                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                        DatabaseReference userRef2 = database2.getReference("Temp");
                                        userRef2.child(get_uid).setValue(userTemp);
                                        Toast.makeText(FundingPaymentActivity.this, "이상치", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }else {
                                Intent intent2 = new Intent(FundingPaymentActivity.this, FundingFinishActivity.class); // 데이터를 전송할 activity 설정
                                intent2.putExtra("host_name", get_host_name);
                                intent2.putExtra("payment", pay_input);
                                intent2.putExtra("month", get_month);
                                intent2.putExtra("day", get_day);
                                startActivity(intent2);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    Toast.makeText(FundingPaymentActivity.this , "로그인 후 이용해 주세요.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), LoginActivity.class));
                }

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

    private boolean check_consume_val(int age, String pay_input, String get_price) {
        double input, price;
        boolean val = false;
        input = Double.parseDouble(pay_input.replaceAll("[\\D]", ""));
        price = Double.parseDouble(get_price.replaceAll("[\\D]", ""));

        double x_stdev, y_stdev, price_dist;


        if(age >19 && age <30){ //20대
            x_stdev = 17460.1909;
            y_stdev = 1151315.9728;

            input = input/x_stdev;
            price = price/y_stdev;

            double dist_array[][] = {
                    {1.6358, 0.5636, 3.563870},
                    {4.4338, 0.0836, 0.037761}
            } ;

            price_dist = Math.pow((input - dist_array[0][0]), 2) + Math.pow((price - dist_array[0][1]), 2);
            if(price_dist > dist_array[0][2]){
                val = true;
            }else {
                val = false;
                return val;
            }


            price_dist = Math.pow((input - dist_array[1][0]), 2) + Math.pow((price - dist_array[1][1]), 2);
            if(price_dist >= dist_array[1][2]){
                val = true;

            }else {
                val = false;
                return val;
            }


        }else if(age >29 && age <40){ //30대
            x_stdev = 57102.2015;
            y_stdev = 1155777.8769;

            input = input/x_stdev;
            price = price/y_stdev;

            double dist_array[][] = {
                    {1.78527676, 0.60761587, 1.0073201902962219},
                    {2.67949964, 2.38413458, 0.016353996610212487},
                    {4.29840584, 0.08815448, 0.009767710816821547},
                    {4.73168917, 0.05371274, 0.005959028048255301}
            } ;

            for(int i = 0; i < 4; i++){
                price_dist = Math.pow((input - dist_array[i][0]), 2) + Math.pow((price - dist_array[i][1]), 2);
                if(price_dist > dist_array[i][2]){
                    val = true;
                    //Toast.makeText(FundingPaymentActivity.this, "이상치", Toast.LENGTH_SHORT).show();
                }else {
                    val = false;
                    return val;

                }
            }



        }else if (age >39 && age <50){ //40대
            x_stdev = 323885.5509;
            y_stdev = 1099441.4471;

            input = input/x_stdev;
            price = price/y_stdev;

            double dist_array[][] = {
                    {0.76951721, 0.5085715, 0.5112885806166535},
                    {1.14730109, 2.34528288, 0.11437898481808602},
                    {2.39374745, 0.6506491, 0.08610169507386854},
                    {4.04491777, 0.09189212, 0.06153341727415036}
            } ;

            for(int i = 0; i < 4; i++){
                price_dist = Math.pow((input - dist_array[i][0]), 2) + Math.pow((price - dist_array[i][1]), 2);
                if(price_dist > dist_array[i][2]){
                    val = true;
                    //Toast.makeText(FundingPaymentActivity.this, "이상치", Toast.LENGTH_SHORT).show();
                }else {
                    val = false;
                    return val;

                }
            }



        }else{
            val = false;
        }

        return val;

    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //backPressCloseHandler.onBackPressed();
    }
}