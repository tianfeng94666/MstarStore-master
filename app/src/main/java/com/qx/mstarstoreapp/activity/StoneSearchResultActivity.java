package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.StoneSearchResultAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.StoneSearchInfo;
import com.qx.mstarstoreapp.json.StoneSearchInfoResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.xListView.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class StoneSearchResultActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, StoneSearchResultAdapter.ChooseItemInterface {
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.lv_stone)
    XListView lvStone;
    @Bind(R.id.tv_quoted_price_all)
    TextView tvQutedPriceAll;
    @Bind(R.id.tv_ischeck_stone)
    TextView tvIscheckStone;
    @Bind(R.id.tv_item_weight)
    TextView tvItemWeight;
    @Bind(R.id.tv_item_shape)
    TextView tvItemShape;
    @Bind(R.id.tv_item_color)
    TextView tvItemColor;
    @Bind(R.id.tv_item_purity)
    TextView tvItemPurity;
    @Bind(R.id.tv_item_cut)
    TextView tvItemCut;
    @Bind(R.id.tv_item_polish)
    TextView tvItemPolish;
    @Bind(R.id.tv_item_symmetric)
    TextView tvItemSymmetric;
    @Bind(R.id.tv_item_fluorescence)
    TextView tvItemFluorescence;
    @Bind(R.id.tv_item_certauth)
    TextView tvItemCertauth;
    @Bind(R.id.tv_item_quoted_price)
    TextView tvItemQuotedPrice;
    @Bind(R.id.ll_title)
    LinearLayout llTitle;
    @Bind(R.id.tv_search_target)
    TextView tvSearchTarget;


    private boolean isLandscape;
    private StoneSearchInfo stoneSearchInfo;
    int page = 1;
    private StoneSearchInfoResult stoneSearchInfoResult;
    List<StoneSearchInfoResult.DataBean.StoneBean.ListBean> list = new ArrayList<>();
    private StoneSearchResultAdapter stoneSearchResultAdapter;
    private static final int PULL_REFRESH = 1;
    private static final int PULL_LOAD = 2;
    private int pullStatus;
    private int listCount;
    private List<String> listTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stone_search_result);
        ButterKnife.bind(this);
        init();
        getDate();
    }

    private void init() {
        idIgBack.setOnClickListener(this);
        lvStone.setXListViewListener(this);
        lvStone.setAutoLoadEnable(true);
        lvStone.setPullRefreshEnable(true);
        lvStone.setPullLoadEnable(true);
        tvQutedPriceAll.setOnClickListener(this);
        titleText.setText("搜索结果");
    }

    private void getDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("stoneInfo");
        stoneSearchInfo = (StoneSearchInfo) bundle.getSerializable("searchStoneInfo");
        Log.e("stoneSearchInfo", stoneSearchInfo.getCerAuth() + stoneSearchInfo.getPrice());
        loadNetData();
    }

    private void initTitleView() {
        StoneSearchInfoResult.DataBean dataBean = stoneSearchInfoResult.getData();
        tvRight.setOnClickListener(this);
        listTitle = dataBean.getStone().getHeadline();
        tvIscheckStone.setText(listTitle.get(0));
        tvItemWeight.setText(listTitle.get(1));
        tvItemShape.setText(listTitle.get(2));
        tvItemColor.setText(listTitle.get(3));
        tvItemPurity.setText(listTitle.get(4));
        tvItemCut.setText(listTitle.get(5));
        tvItemPolish.setText(listTitle.get(6));
        tvItemSymmetric.setText(listTitle.get(7));
        tvItemFluorescence.setText(listTitle.get(8));
        tvItemCertauth.setText(listTitle.get(9));
    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String url = "";
        url = AppURL.URL_STONE_LIST + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + page + "&certAuth=" + stoneSearchInfo.getCerAuth() + "&color=" + stoneSearchInfo.getColor() + "&shape=" + stoneSearchInfo.getShape()
                + "&purity=" + stoneSearchInfo.getPurity() + "&cut=" + stoneSearchInfo.getCut() + "&polishing=" + stoneSearchInfo.getPolishing() + "&symmetric=" + stoneSearchInfo.getSymmetric() + "&fluorescence=" + stoneSearchInfo.getFluorescence()
                + "&price=" + stoneSearchInfo.getPrice() + "&weight=" + stoneSearchInfo.getWeight();
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                L.e("result" + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    stoneSearchInfoResult = new Gson().fromJson(result, StoneSearchInfoResult.class);
                    if (stoneSearchInfoResult.getData() == null) {
                        ToastManager.showToastReal("没有数据！");
                        return;
                    }
                    List<StoneSearchInfoResult.DataBean.StoneBean.ListBean> templist = stoneSearchInfoResult.getData().getStone().getList();
                    listCount = Integer.parseInt(stoneSearchInfoResult.getData().getStone().getList_count());
                    list.addAll(templist);
                    if (listTitle == null) {
                        initTitleView();
                    }
                    String st = stoneSearchInfoResult.getData().getStone().getSearchKey();
                    if (!st.equals("")) {
                        tvSearchTarget.setVisibility(View.VISIBLE);
                        tvSearchTarget.setText(st);
                    } else {
                        tvSearchTarget.setVisibility(View.GONE);
                    }

                    stoneSearchResultAdapter = new StoneSearchResultAdapter(list, StoneSearchResultActivity.this);
                    lvStone.setAdapter(stoneSearchResultAdapter);
                    lvStone.stopRefresh();
                    lvStone.stopRefresh();

                } else if (error.equals("2")) {
                    loginToServer(FinishTableLessActivity.class);
                } else {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                changeOrientation(v);
                break;
            case R.id.id_ig_back:
                finish();
                break;
            case R.id.tv_quoted_price_all:
                quotedPrice(stoneSearchResultAdapter.getQuotedPriceId());
                break;
        }
    }

    private void changeOrientation(View v) {
        if (!isLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isLandscape = true;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isLandscape = false;
        }
    }


    @Override
    public void onRefresh() {
        page = 1;
        list.clear();
        pullStatus = PULL_REFRESH;
        loadNetData();

    }

    @Override
    public void onLoadMore() {
        if (listCount > stoneSearchResultAdapter.getCount()) {
            page++;
            pullStatus = PULL_LOAD;
            loadNetData();
        } else {
            ToastManager.showToastReal("没有更多数据");
            lvStone.stopLoadMore();
        }
    }

    @Override
    public void quotedPrice(String id) {
        Intent intent  = new Intent(this,StoneQuotedPriceActivity.class);
        intent.putExtra("stoneIds",id);
        startActivity(intent);
    }
}
