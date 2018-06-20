package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.bean.StoneRangeBean;
import com.qx.mstarstoreapp.greendao.gen.StoneRangeBeanDao;
import com.qx.mstarstoreapp.json.StatisticsResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/11 0011.
 */

public class StatisticsActivity extends BaseActivity {

    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.layout_rl_title)
    RelativeLayout layoutRlTitle;
    @Bind(R.id.lv_order_text)
    ListView lvOrderText;
    private StoneRangeBeanDao stoneRangBeanDao;
    private List<StoneRangeBean> stoneRangeBeanList;
    private StatisticsResult statisticsResult;
    private List<StatisticsResult.DataBean.RangesBean> rangesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        stoneRangBeanDao = BaseApplication.getApplication().getDaoSession().getStoneRangeBeanDao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNetData();
    }

    @Override
    public void loadNetData() {
        stoneRangeBeanList = stoneRangBeanDao.loadAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stoneRangeBeanList.size(); i++) {
            StoneRangeBean stoneRangeBean = stoneRangeBeanList.get(i);
            if (stoneRangeBean.getIsChoose() == 1) {
                if (i == stoneRangeBeanList.size() - 1) {
                    stringBuilder.append(stoneRangeBean.getStoneMin() + "~" + stoneRangeBean.getStoneMax());
                } else {
                    stringBuilder.append(stoneRangeBean.getStoneMin() + "~" + stoneRangeBean.getStoneMax() + ",");
                }
            }


        }
        baseShowWatLoading();
        String lgUrl = AppURL.URL_ORDER_STATISTIC + "tokenKey=" + BaseApplication.getToken() + "&Ranges=" + stringBuilder.toString();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    statisticsResult = new Gson().fromJson(result, StatisticsResult.class);
                    if (statisticsResult.getData() == null) {
                        showToastReal("后台数据为空");
                        return;
                    }
                    rangesList =statisticsResult.getData().getRanges();
                    initView();

                } else if (error.equals("2")) {
                    baseHideWatLoading();
                    loginToServer(MakingActivity.class);
                } else {
                    baseHideWatLoading();
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastWhendebug(message);
                    L.e(message);
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }


        });
    }

    private void initView() {
        titleText.setText("当前订单分类");
        lvOrderText.setAdapter(new CommonAdapter<StatisticsResult.DataBean.RangesBean>(rangesList, R.layout.item_order_statistics) {
            @Override
            public void convert(int position, BaseViewHolder helper, StatisticsResult.DataBean.RangesBean item) {
                helper.setText(R.id.tv_lable, item.getRangeTitle() + ":");
                helper.setText(R.id.tv_value, item.getCount());
            }


        });
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ClassifyOrderActivity.class,null);
            }
        });
    }
}
