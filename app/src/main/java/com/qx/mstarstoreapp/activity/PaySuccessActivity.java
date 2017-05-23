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
    }

    @Override
    public void loadNetData() {

    }

    public void onGotoModify(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putString("itemId", id);
        openActivity(ConfirmOrderActivity.class, bundle);
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
