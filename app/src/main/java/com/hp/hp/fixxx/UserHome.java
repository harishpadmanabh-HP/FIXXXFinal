package com.hp.hp.fixxx;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserHome extends AppCompatActivity {
    private ViewPager mViewPager;
    private  SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mViewPager=findViewById(R.id.viewpagr);
        //adapter class created
        mSectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
        //set adapter
        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout=findViewById(R.id.tabLayout);
        //set viewpager to tabs
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
