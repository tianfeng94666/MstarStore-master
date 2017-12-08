package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.ItemDecoration.SpaceItemDecoration;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.RecycleViewPartAdapter;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.manager.FitGridLayoutManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class MakingActivity extends BaseActivity {


    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.tv_right)
    ImageView tvRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.rv_part)
    RecyclerView rvPart;
    private FitGridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mLayoutManager = new FitGridLayoutManager(this,rvPart,2,GridLayoutManager.HORIZONTAL,false);
        rvPart.setLayoutManager(mLayoutManager);
        ArrayList list = new ArrayList();
        for(int i =0;i<10;i++){
            list.add("1");
        }
        RecycleViewPartAdapter recycleViewPartAdapter = new RecycleViewPartAdapter(list);
        //设置item间距，30dp
        rvPart.addItemDecoration(new SpaceItemDecoration(4));
        rvPart.setAdapter(recycleViewPartAdapter);

    }

    @Override
    public void loadNetData() {

    }
}
