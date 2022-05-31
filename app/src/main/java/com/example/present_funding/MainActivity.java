package com.example.present_funding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private ImageView ivMenu;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    private GridView gv_student, gv_merry, gv_purchase = null;
    private GridViewAdapter adapter, adapter2, adapter3 = null;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ivMenu = findViewById(R.id.iv_menu);

        gv_student = (GridView) findViewById(R.id.grid_student);
        gv_merry = (GridView) findViewById(R.id.grid_merry);
        gv_purchase = (GridView) findViewById(R.id.grid_purchase);

        adapter = new GridViewAdapter();
        adapter2 = new GridViewAdapter();
        adapter3 = new GridViewAdapter();

        gv_student.setAdapter(adapter);
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20211019161851_b057979a9f884eb9a9dd417dc22c5533.jpg", "Apple 아이패드 미니 6세대 WiFi 64GB", "636,000"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20211005153721_31437fc99fd542efbb51259ad9d03708.jpg", "Apple 애플워치 SE 40mm GPS 알루미늄 케이스+스포츠 밴드", "355,000"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220325164122_066983f7d8aa45fb8d8bf81ca5563ab9.jpg", "삼성전자 갤럭시탭 S8 SM-X700 WiFi 256GB 패드형 태블릿PC (갤럭시탭S8 Wi-Fi 256GB)", "979,000"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220127104813_1f8ce3a35c5943b38bf5bea2ad580326.jpg", "LG전자 22년형 그램 (40.6cm) 인텔 11세대 i5 새학기 대학생 노트북 (16Z95P-GA5LK)", "1,849,000"));

        gv_merry.setAdapter(adapter2);
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220405211859_d58c0169fd9e4a42ba1931430cc8b14a.jpg", "BESPOKE 냉장고 4도어 프리스탠딩 875L 쉬머바이올렛 /삼성물류직배송 (RF85B91113D)", "2,862,800"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220331093421_abd4dca6cfe14326b76301fd46b0e38c.JPG", "LG 올레드 TV 55인치 (벽걸이형) (OLED55A1NNA)", "1,511,900"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20210924130623_53409189f743403bae65eeae28e1c49a.jpg", "그랑데 AI 드럼세탁기 23kg (AI WF23T8500KV)", "1,311,400"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220307130953_257b590e5e6747d7b4b1b1e8276b78b8.jpg", "그랑데 건조기 AI 17kg / 삼성물류직송", "1,368,500"));

        gv_purchase.setAdapter(adapter3);
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220117092145_8ef648db613a4843aea1187357ddc778.jpg", "하겐다즈 프리미엄 수제 아이스크림 케이크 리얼블랑 (바닐라+초코)", "29,900"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220429162810_e75730d0e05a4068a01039c55d7c3ec9.jpg", "[생일선물] 아름답게 피어나는 그대, 5월의꽃 장미 생일화 디퓨저(170ml) 선물세트", "20,000"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220225140000_ce0138381dd949839999c071a3a08da5.jpg", "꿈나라 여행을 떠나고있는 카카오프렌즈 굿나잇 무드등 춘식이", "29,900"));
        adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220419122011_e61427a51e3343de8281141e619977fd.jpg", "[생일선물] 사랑스런그대에게 꽃떡케이크 2호", "26,900"));
        //adapter.addItem(new Product("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220422153805_7c4d5eab8bdb4ed79386182d51be0efb.jpg", "[누적판매25만 리뷰이벤트] [건강선물] 인사이디 전동 마사지건 IMG-7PRO (전용 가방 포함) (INSIDY 전동마사지건 IMG-7)", "26,900"));

        backPressCloseHandler = new BackPressCloseHandler(this);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MypageActivity.class));
            }
        });

        //Adapter 안에 아이템의 정보 담기
//        mDatabase = FirebaseDatabase.getInstance().getReference("691");
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 = snapshot.child("brand").getValue(String.class);
//                txt.setText(brand);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //adapter.addItem(new Product("", "", ""));

    }

    /* 그리드뷰 어댑터 */
    class GridViewAdapter extends BaseAdapter {
        ArrayList<Product> items = new ArrayList<Product>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Product item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final Product product = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_item, viewGroup, false);

                TextView g_name = (TextView) convertView.findViewById(R.id.txt_grid_name);
                TextView g_price = (TextView) convertView.findViewById(R.id.txt_grid_price);
                ImageView g_img = (ImageView) convertView.findViewById(R.id.iv_grid_img);

                g_name.setText(product.getName());
                g_price.setText(product.getPrice());
                //g_img.setImageResource(Integer.parseInt(product.getImg()));
//                Glide.with(convertView.getContext())
//                        .load(product.getImg());
                //Log.d(TAG, "getView() - [ "+position+" ] "+product.getName());

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, product.getName()+" 상품의 가격은 - "+product.getPrice()+" 입니당! ", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getApplication(), DetailActivity.class);
                    //startActivity(intent);
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}