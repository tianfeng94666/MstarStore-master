package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.SearchResultAdapter;
import com.qx.mstarstoreapp.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class SearchResultActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.lv_results)
    ListView lvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        idIgBack.setOnClickListener(this);
//        SearchResultAdapter searchResultAdapter =
    }

    @Override
    public void loadNetData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_ig_back:
                finish();
                break;

        }
    }

}
