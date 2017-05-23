package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.fragment.FragOrderListFragment;
import com.qx.mstarstoreapp.fragment.InfromationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class StoneHistoryOrder extends BaseActivity  {
    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private Fragment checkingFrament; //待审核
    private Fragment productingFragment;//生产中
    private Fragment sendingFragment;//已发货
    private Fragment finishedFragment;//已完成
    public List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stone_stone_histoty_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        checkingFrament = new InfromationFragment();
        fragmentList.add(checkingFrament);
        productingFragment = new InfromationFragment();
        fragmentList.add(productingFragment);
        sendingFragment = new InfromationFragment();
        fragmentList.add(sendingFragment);
        finishedFragment = new InfromationFragment();
        fragmentList.add(finishedFragment);
         pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tab.addTab(tab.newTab().setText("1"));
        tab.addTab(tab.newTab().setText("2"));
        tab.addTab(tab.newTab().setText("3"));
        tab.addTab(tab.newTab().setText("4"));
        //添加页卡标题
        mTitleList.add("No:1");
        mTitleList.add("No:2");
        mTitleList.add("No:3");
        mTitleList.add("No:4");
        mTitleList.add("No:5");
        tab.setupWithViewPager(viewPager);
       tab.setTabsFromPagerAdapter(pagerAdapter);
    }

    @Override
    public void loadNetData() {

    }
    public class PagerAdapter extends FragmentPagerAdapter {
        private List<?> fragments;

        public PagerAdapter(FragmentManager fm, List<?> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }
    }
}
