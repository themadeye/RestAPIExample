package com.example.restapiexample.activities;

import android.content.Intent;
import android.net.Uri;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.example.restapiexample.R;
import com.example.restapiexample.fragments.FemalePager;
import com.example.restapiexample.fragments.MalePager;
import com.example.restapiexample.fragments.AllPager;
import com.example.restapiexample.sqlite.UserDBHelper;

public class MainActivity extends AppCompatActivity implements
        MalePager.OnFragmentInteractionListener,
        FemalePager.OnFragmentInteractionListener,
        AllPager.OnFragmentInteractionListener{


    private Button profile;
    private Button message;
    private Button exit;
    private RequestQueue mQueue;

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

//        mQueue = Volley.newRequestQueue(this);
//        String URL = "https://reqres.in/api/users?page=2";
//        JsonObjectRequest job = new JsonObjectRequest(Request.Method.GET, URL, null,
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try{
//                            JSONArray jary = response.getJSONArray("data");
//                            for(int i = 0; i < jary.length(); i++){
//                                JSONObject data = jary.getJSONObject(i);
//                                Users users = new Users();
//                                users.setID(Integer.valueOf(data.getString("id")));
//                                users.setEmail(data.getString("email"));
//                                users.setFirstName(data.getString("first_name"));
//                                users.setLastName(data.getString("last_name"));
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        mQueue.add(job);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDBHelper dbHelp = new UserDBHelper(MainActivity.this);
                dbHelp.updateCredential("false");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
