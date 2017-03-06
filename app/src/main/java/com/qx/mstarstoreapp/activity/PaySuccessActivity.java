package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.View;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;

/**
 * Created by Administrator on 2016/10/28.
 */
public class PaySuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
    }

    @Override
    public void loadNetData() {

    }

    public void onGotoModify(View view){
        openActivity(ModifyDataActivity.class,null);
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
