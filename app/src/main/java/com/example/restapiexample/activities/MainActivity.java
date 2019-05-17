package com.example.restapiexample.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restapiexample.R;
import com.example.restapiexample.fragments.FemalePager;
import com.example.restapiexample.fragments.MalePager;
import com.example.restapiexample.fragments.AllPager;

public class MainActivity extends AppCompatActivity implements
        MalePager.OnFragmentInteractionListener,
        FemalePager.OnFragmentInteractionListener,
        AllPager.OnFragmentInteractionListener{


    private Button profile;
    private Button message;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        TabLayout tbl = (TabLayout)findViewById(R.id.tablayout);
        tbl.addTab(tbl.newTab().setText("Male"));
        tbl.addTab(tbl.newTab().setText("Female"));
        tbl.addTab(tbl.newTab().setText("All"));
        tbl.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tbl.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbl));

        tbl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void findViews(){
        profile = (Button)findViewById(R.id.profile);
        message = (Button)findViewById(R.id.message);
        exit = (Button)findViewById(R.id.exit);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
