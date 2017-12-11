package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.ItemDecoration.SpaceItemDecoration;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.RecycleViewPartAdapter;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.manager.FitGridLayoutManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.BottmPartPopupWindow;

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
    @Bind(R.id.root_view)
    RelativeLayout rootView;
    private FitGridLayoutManager mLayoutManager;
    private BottmPartPopupWindow bottmPartPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making);
        ButterKnife.bind(this);
        initView();
        bottmPartPopupWindow = new BottmPartPopupWindow(this);
    }

    private void initView() {
        if(UIUtils.isScreenChange(this)){
            mLayoutManager = new FitGridLayoutManager(this, rvPart, 2, GridLayoutManager.VERTICAL, false);
        }else {
            mLayoutManager = new FitGridLayoutManager(this, rvPart, 2, GridLayoutManager.HORIZONTAL, false);
        }

        rvPart.setLayoutManager(mLayoutManager);
        ArrayList list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("1");
        }
        RecycleViewPartAdapter recycleViewPartAdapter = new RecycleViewPartAdapter(list, new RecycleViewPartAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                bottmPartPopupWindow.showPop(rootView);
            }
        });
        //设置item间距，30dp
        rvPart.addItemDecoration(new SpaceItemDecoration(4));
        rvPart.setAdapter(recycleViewPartAdapter);

    }

    @Override
    public void loadNetData() {

    }
}
