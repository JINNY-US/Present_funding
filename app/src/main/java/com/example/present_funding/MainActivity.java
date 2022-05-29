package com.example.present_funding;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;

    private ImageView ivMenu;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ArrayList<String> LocationArr;
    private ArrayList<Integer> CostArr;

    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    private BackPressCloseHandler backPressCloseHandler;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    HomeFragment homeFragment = new HomeFragment();
    MarketFragment marketFragment = new MarketFragment();
    MypageFragment mypageFragment = new MypageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.nav_view);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_framelayout, homeFragment).commitAllowingStateLoss();

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        fragmentTransaction.replace(R.id.main_framelayout,homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.nav_market:
                        fragmentTransaction.replace(R.id.main_framelayout,marketFragment).commitAllowingStateLoss();
                        break;
                    case R.id.nav_mypage:
                        fragmentTransaction.replace(R.id.main_framelayout,mypageFragment).commitAllowingStateLoss();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}