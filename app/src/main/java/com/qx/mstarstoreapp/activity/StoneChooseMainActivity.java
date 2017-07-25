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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.fragment.StoneChooseFromSettingFragment;
import com.qx.mstarstoreapp.fragment.StoneFragment;
import com.qx.mstarstoreapp.json.StoneDetail;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class StoneChooseMainActivity extends BaseActivity {


    public List<Fragment> fragmentList = new ArrayList<>();
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private PagerAdapter pagerAdapter;
    private Fragment stoneChooseFromSettingFragment,stoneChooseFromStoneHouseFragment;
    private StoneDetail stoneDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stone_choose_main);
        ButterKnife.bind(this);
        getDate();
        initView();
    }

    private void getDate() {
        stoneDetail = (StoneDetail)getIntent().getSerializableExtra("stoneDetail");
    }

    @Override
    public void loadNetData() {

    }

    private void initView() {
        stoneChooseFromStoneHouseFragment = new StoneChooseFromSettingFragment();
        fragmentList.add(stoneChooseFromStoneHouseFragment);
        stoneChooseFromSettingFragment = new StoneFragment(2);
        fragmentList.add(stoneChooseFromSettingFragment);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tab.addTab(tab.newTab().setText("1"));
        tab.addTab(tab.newTab().setText("2"));
        tab.addTab(tab.newTab().setText("3"));
        tab.addTab(tab.newTab().setText("4"));
        //添加页卡标题
        mTitleList.add("选择主石规格");
        mTitleList.add("从裸钻库中挑选");

        tab.setupWithViewPager(viewPager);
        tab.setTabsFromPagerAdapter(pagerAdapter);
        idIgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    public StoneDetail getStoneDetail() {
        return stoneDetail;
    }

    public void setStoneDetail(StoneDetail stoneDetail) {
        this.stoneDetail = stoneDetail;
    }
}
