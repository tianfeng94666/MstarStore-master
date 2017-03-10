package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.fragment.HelpFragment;
import com.qx.mstarstoreapp.fragment.HomeFragment;
import com.qx.mstarstoreapp.fragment.InfromationFragment;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.BadgeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    private RelativeLayout rlyTitle;
    private int red = 0xc3272b;
    Fragment homeFragment, informationFragment, helpFragment;
    FragmentManager fragmentMag;
    FrameLayout id_fl_tab1, id_fl_tab2;
    LinearLayout id_fl_tab3;

    ImageView igHome, igInformaction, igHelp;
    TextView tvHome, tvInformaction, tvHelp;
    private int nowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main_lay);
        ButterKnife.bind(this);

        initView();
        setChioceFragment(0);
    }

    private void initView() {
        idIgBack.setVisibility(View.GONE);
        fragmentMag = getSupportFragmentManager();
        id_fl_tab1 = (FrameLayout) findViewById(R.id.id_fl_tab1);
        id_fl_tab2 = (FrameLayout) findViewById(R.id.id_fl_tab2);
        id_fl_tab3 = (LinearLayout) findViewById(R.id.id_fl_tab3);

        igHome = (ImageView) findViewById(R.id.id_ig_home);
        igInformaction = (ImageView) findViewById(R.id.id_ig_information);
        igHelp = (ImageView) findViewById(R.id.id_ig_help);


        tvHome = (TextView) findViewById(R.id.id_tv_home);
        tvInformaction = (TextView) findViewById(R.id.id_tv_information);
        tvHelp = (TextView) findViewById(R.id.id_tv_help);

        rlyTitle = (RelativeLayout) findViewById(R.id.id_rel_title);
        id_fl_tab3.setOnClickListener(this);
        id_fl_tab2.setOnClickListener(this);
        id_fl_tab1.setOnClickListener(this);

        TextView hindInformation = (TextView) findViewById(R.id.tab2_count);
        badge1 = new BadgeView(MainActivity.this, hindInformation);// 创建一个BadgeView对象，view为你需要显示提醒的控件
    }

    public static BadgeView badge1;

    public static void setOnInformationCountClick(OnInformationCountClick onInformationCountClick) {
        int count = onInformationCountClick.getCount();
        if (count == 0) {
            return;
        } else {
            remind(count, badge1, true);
        }

    }

    public interface OnInformationCountClick {
        int getCount();
    }

    public void visableTitle() {
        rlyTitle.setVisibility(View.VISIBLE);
    }

    public void hideTitle() {
        rlyTitle.setVisibility(View.GONE);
    }

    private static void remind(int count, BadgeView badge, boolean isVisible) {
        //BadgeView的具体使用
        badge.setText(count + ""); // 需要显示的提醒类容
        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge.setTextColor(Color.WHITE); // 文本颜色
        int hint = Color.rgb(200, 39, 73);
        badge.setBadgeBackgroundColor(hint); // 提醒信息的背景颜色，自己设置
        badge.setTextSize(10); // 文本大小
        badge.setBadgeMargin(3, 3); // 水平和竖直方向的间距
        badge.setBadgeMargin(5); //各边间隔
        if (isVisible) {
            badge.show();// 只有显示
        } else {
            badge.hide();//影藏显示
        }
    }


    private void setChioceFragment(int index) {
        FragmentTransaction fragTrans = fragmentMag.beginTransaction();
        resetAllFragmentView();
        hideAllFragments(fragTrans);
        visableTitle();
        switch (index) {
            case 0:
                hideTitle();
                tvHome.setTextColor(getResources().getColor(R.color.theme_red));
                igHome.setImageResource(R.drawable.icon_home_down);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragTrans.add(R.id.content, homeFragment);
                } else {
                    fragTrans.show(homeFragment);
                }
                break;
            case 1:
                tvInformaction.setTextColor(getResources().getColor(R.color.theme_red));
                igInformaction.setImageResource(R.drawable.icon_infromation_down);
                if (informationFragment == null) {
                    informationFragment = new InfromationFragment();
                    fragTrans.add(R.id.content, informationFragment);
                } else {
                    fragTrans.show(informationFragment);
                }
                break;
            case 2:
                tvHelp.setTextColor(getResources().getColor(R.color.theme_red));
                igHelp.setImageResource(R.drawable.icon_help_down);
                if (helpFragment == null) {
                    helpFragment = new HelpFragment();
                    fragTrans.add(R.id.content, helpFragment);
                } else {
                    fragTrans.show(helpFragment);
                }
                break;
        }
        nowId=index;
        fragTrans.commit();

    }

    private void resetAllFragmentView() {
        igHome.setImageResource(R.drawable.icon_home_nor);
        igInformaction.setImageResource(R.drawable.icon_infromation_nor);
        igHelp.setImageResource(R.drawable.icon_help_nor);
        tvHome.setTextColor(getResources().getColor(R.color.text_color3));
        tvInformaction.setTextColor(getResources().getColor(R.color.text_color3));
        tvHelp.setTextColor(getResources().getColor(R.color.text_color3));
    }

    private void hideAllFragments(FragmentTransaction fragTrans) {
        if (homeFragment != null) {
            fragTrans.hide(homeFragment);
        }
        if (helpFragment != null) {
            fragTrans.hide(helpFragment);
        }
        if (informationFragment != null) {
            fragTrans.hide(informationFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_fl_tab1:
                setChioceFragment(0);
                break;
            case R.id.id_fl_tab2:
                setChioceFragment(1);
                break;
            case R.id.id_fl_tab3:
                setChioceFragment(2);
                break;
        }
    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 94) {
//            int fragment_id = -1;
//            if (data != null) {
//                fragment_id = data.getIntExtra("fragmentid", -1);
//            } else {
//                fragment_id = nowId;
//            }
//            setChioceFragment(fragment_id);
//        }
//    }

    private void loginToExchange(int position) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.putExtra("fragmentid", position);
        startActivityForResult(loginIntent, 94);
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
               ToastManager.showToastReal("再按一次退出程序");
                exitTime= System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
        }
        return true;
    }
}
