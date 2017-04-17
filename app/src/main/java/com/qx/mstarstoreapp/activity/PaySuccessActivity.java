package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;

/**
 * Created by Administrator on 2016/10/28.
 */
public class PaySuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pay_success);
    }

    @Override
    public void loadNetData() {

    }

    public void onGotoModify(View view){
        openActivity(SettingActivity.class,null);
    }

    public void onGotoOrder(View view){
        openActivity(OrderActivity.class,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
