package com.pb.app.caloriecalculator;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pb.app.caloriecalculator.ui.fragments.FragmentChat;
import com.pb.app.caloriecalculator.ui.fragments.FragmentProduct;
import com.pb.app.caloriecalculator.ui.fragments.FragmentSettings;
import com.pb.app.caloriecalculator.ui.fragments.FragmentStatistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private FragmentTransaction ftransaction;
    private FragmentProduct fragmentProduct;
    private FragmentChat fragmentChat;
    private FragmentStatistic fragmentStatistic;
    private FragmentSettings fragmentSettings;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ftransaction = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_calculator:
                    ftransaction.replace(R.id.containerFragment, fragmentProduct);
                    mTextMessage.setText(R.string.title_calculator);
                    ftransaction.commit();
                    return true;
                case R.id.navigation_chat:
                    ftransaction.replace(R.id.containerFragment, fragmentChat);
                    mTextMessage.setText(R.string.title_chat);
                    ftransaction.commit();
                    return true;
                case R.id.navigation_timeline:
                    ftransaction.replace(R.id.containerFragment, fragmentStatistic);
                    mTextMessage.setText(R.string.title_statistic);
                    ftransaction.commit();
                    return true;
                case R.id.navigation_settings:
                    ftransaction.replace(R.id.containerFragment, fragmentSettings);
                    mTextMessage.setText(R.string.title_settings);
                    ftransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentProduct = new FragmentProduct(this);
        fragmentChat = new FragmentChat();
        fragmentStatistic = new FragmentStatistic(this);
        fragmentSettings = new FragmentSettings();
        ftransaction = getFragmentManager().beginTransaction();
        ftransaction.replace(R.id.containerFragment, fragmentProduct);
        ftransaction.commit();
    }

}
