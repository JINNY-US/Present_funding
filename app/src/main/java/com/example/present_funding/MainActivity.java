package com.example.present_funding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
    private Button go_market, go_opened_funding;

    //private FirebaseAuth firebaseAuth;
    //DatabaseReference mDatabase;

    private ViewPager2 sliderViewPager, sliderViewPager2, sliderViewPager3;
    private LinearLayout layoutIndicator, layoutIndicator2, layoutIndicator3;

    //private GridView gv_student = null;
    //private GridViewAdapter adapter = null;
    private GridView gv_merry = null, gv_purchase = null;
    private GridViewAdapter adapter2 = null, adapter3 = null;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        String[] images = new String[] {"https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20211019161851_b057979a9f884eb9a9dd417dc22c5533.jpg",
                "https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20211005153721_31437fc99fd542efbb51259ad9d03708.jpg",
                "https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220325164122_066983f7d8aa45fb8d8bf81ca5563ab9.jpg",
                "https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220127104813_1f8ce3a35c5943b38bf5bea2ad580326.jpg"
        };

        String[] brands = new String[] {
                "Apple", "Apple", "삼성전자", "LG전자"
        };

        String[] names = new String[] {
                "Apple 아이패드 미니 6세대 WiFi 64GB",
                "Apple 애플워치 SE 40mm GPS 알루미늄 케이스+스포츠 밴드",
                "삼성전자 갤럭시탭 S8 SM-X700 WiFi 256GB 패드형 태블릿PC (갤럭시탭S8 Wi-Fi 256GB)",
                "LG전자 22년형 그램 (40.6cm) 인텔 11세대 i5 새학기 대학생 노트북 (16Z95P-GA5LK)"
        };

        String[] prices = new String[] {
                "636,000원", "355,000원", "979,000원", "1,849,000원"
        };
        /* 구분을 위한 줄 나눔!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

        ivMenu = findViewById(R.id.iv_menu);
        go_market = findViewById(R.id.btn_go_market);
        go_opened_funding = findViewById(R.id.btn_view_funding);

        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, images, brands, names, prices));


        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(images.length);

        //그리드뷰 관련
        gv_merry = (GridView) findViewById(R.id.grid_merry);
        gv_purchase = (GridView) findViewById(R.id.grid_purchase);

        adapter2 = new GridViewAdapter();
        adapter3 = new GridViewAdapter();

        adapter2.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220405211859_d58c0169fd9e4a42ba1931430cc8b14a.jpg", "삼성전자", "BESPOKE 냉장고 4도어 프리스탠딩 875L 쉬머바이올렛 /삼성물류직배송 (RF85B91113D)", "2,862,800원"));
        adapter2.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220331093421_abd4dca6cfe14326b76301fd46b0e38c.JPG", "LG전자", "LG 올레드 TV 55인치 (벽걸이형) (OLED55A1NNA)", "1,511,900원"));
        adapter2.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20210924130623_53409189f743403bae65eeae28e1c49a.jpg", "삼성전자", "그랑데 AI 드럼세탁기 23kg (AI WF23T8500KV)", "1,311,400원"));
        adapter2.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C600x600.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220307130953_257b590e5e6747d7b4b1b1e8276b78b8.jpg", "삼성전자", "그랑데 건조기 AI 17kg / 삼성물류직송", "1,368,500원"));
        gv_merry.setAdapter(adapter2);

        adapter3.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220117092145_8ef648db613a4843aea1187357ddc778.jpg", "하겐다즈", "하겐다즈 프리미엄 수제 아이스크림 케이크 리얼블랑 (바닐라+초코)", "29,900원"));
        adapter3.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220429162810_e75730d0e05a4068a01039c55d7c3ec9.jpg", "아스파시아", "[생일선물] 아름답게 피어나는 그대, 5월의꽃 장미 생일화 디퓨저(170ml) 선물세트", "20,000원"));
        adapter3.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220225140000_ce0138381dd949839999c071a3a08da5.jpg", "프렌즈리빙LC", "꿈나라 여행을 떠나고있는 카카오프렌즈 굿나잇 무드등 춘식이", "29,900원"));
        adapter3.addItem(new ProductItem("https://img1.kakaocdn.net/thumb/C276x276.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220419122011_e61427a51e3343de8281141e619977fd.jpg", "빚고을", "[생일선물] 사랑스런그대에게 꽃떡케이크 2호", "26,900원"));
        gv_purchase.setAdapter(adapter3);

        backPressCloseHandler = new BackPressCloseHandler(this);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MypageActivity.class));
            }
        });

        go_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MarketActivity.class));
            }
        });

        go_opened_funding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), OpenedFundingActivity.class));
            }
        });
    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

    /* 그리드뷰 어댑터 */
    class GridViewAdapter extends BaseAdapter {
        ArrayList<ProductItem> items = new ArrayList<ProductItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ProductItem item) {
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
            final ProductItem product = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_item, viewGroup, false);

                String g_brand = product.getBrand();

                TextView g_name = (TextView) convertView.findViewById(R.id.txt_grid_name);
                TextView g_price = (TextView) convertView.findViewById(R.id.txt_grid_price);
                ImageView g_img = (ImageView) convertView.findViewById(R.id.iv_grid_img);

                g_name.setText(product.getName());
                g_price.setText(product.getPrice());
                Glide.with(convertView).load(product.getImg()).into(g_img);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, DetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 데이터를 전송할 activity 설정

                    intent.putExtra("brand", product.getBrand());      // pos에 맞는 데이터의 브랜드값을 전송
                    intent.putExtra("name", product.getName());      // pos에 맞는 데이터의 이름값을 전송
                    intent.putExtra("price", product.getPrice());        // pos에 맞는 데이터의 가격값을 전송
                    intent.putExtra("img", product.getImg());           // pos에 맞는 데이터의 이미지값을 전송

                    context.startActivity(intent);
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

    // 뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}