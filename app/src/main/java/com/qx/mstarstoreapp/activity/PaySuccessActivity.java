package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;

import static android.R.attr.id;

/**
 * Created by Administrator on 2016/10/28.
 */
public class PaySuccessActivity extends BaseActivity {
    private String id;
    private String type;//type为2裸石订单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pay_success);
        getDate();
    }

    private void getDate() {
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void loadNetData() {

    }

    public void onGotoModify(View view) {


            openActivity(MainActivity.class, null);

    }

    public void onGotoOrder(View view) {

        if(type.equals("2")){
            openActivity(StoneSearchInfoActivity.class, null);
        }else {
            openActivity(OrderActivity.class, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
