package com.hrobbie.loadingproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * user:HRobbie
 * Date:2017/2/17
 * Time:16:27
 * 邮箱：hwwyouxiang@163.com
 * Description:Page Function.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<?> mFragment;
    private List<String> mTitleList;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<?> mFragment, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<?> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitleList!=null){
            return mTitleList.get(position);
        }
        return "";
    }
}
