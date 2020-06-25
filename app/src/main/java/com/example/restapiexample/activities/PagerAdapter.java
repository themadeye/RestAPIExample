package com.example.restapiexample.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.restapiexample.fragments.AllPager;
import com.example.restapiexample.fragments.MalePager;
import com.example.restapiexample.fragments.FemalePager;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numoftabs){
        super(fm);
        this.numOfTabs = numoftabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0 :
                MalePager mp = new MalePager();
                return mp;
            case 1 :
                FemalePager fp = new FemalePager();
                return fp;
            case 2 :
                AllPager ap = new AllPager();
                return ap;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
