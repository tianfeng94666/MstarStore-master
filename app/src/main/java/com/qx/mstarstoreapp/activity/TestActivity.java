package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class TestActivity extends AppCompatActivity {
    private ArrayList<Object> l;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        l = new ArrayList<>();
        for(int i = 0;i<10000;i++){
            l.add(new Object());
        }
        Log.v("ch","------------>"+l.size());
        AppManager.getAppManager().registActivity(this);
    }
}
