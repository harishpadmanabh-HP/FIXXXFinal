package com.hp.hp.fixxx;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//implement methods and constructor
class SectionPagerAdapter extends FragmentPagerAdapter {
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                UserPostJobFrag userPostJobFrag=new UserPostJobFrag();
                return  userPostJobFrag;

            case 1:

            UserViewJobFrag userViewJobFrag=new UserViewJobFrag();
            return userViewJobFrag;



            default: return null;
        }

        //return null;
    }

    @Override
    public int getCount() {
        //no of tabs
        return 2;
    }
    //set tab title
    public CharSequence getPageTitle(int position){

        switch (position)
        {
            case 0 :
                return "POST JOBS";
            case 1:
                return "VIEW JOBS";

            default:
                return null;
        }

    }
}
