package com.qx.mstarstoreapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.StoneSearchInfo;
import com.qx.mstarstoreapp.json.StoneSearchInfoResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.LoadingWaitDialog;
import com.qx.mstarstoreapp.viewutils.xListView.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class StoneSearchResultActivity extends Activity implements View.OnClickListener, XListView.IXListViewListener, StoneSearchResultAdapter.ChooseItemInterface {
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
    @Bind(R.id.tv_item_price)
    TextView tvItemPrice;
    @Bind(R.id.tv_item_cerauth_number)
    TextView tvItemCerauthNumber;
    @Bind(R.id.tv_reset)
    TextView tvReset;


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
    private String orderby = "";
    int ordertimes = 0;

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

    private void getDate() {
        Intent intent = getIntent();
        Bundle stoneBundle = null;
        Bundle bundle = intent.getBundleExtra("stoneInfo");
        stoneSearchInfo = (StoneSearchInfo) bundle.getSerializable("searchStoneInfo");
        stoneSearchInfoResult = (StoneSearchInfoResult) getLastNonConfigurationInstance();
        if (stoneSearchInfoResult != null) {
            setXListview(stoneSearchInfoResult);
        } else {
            loadNetData();
        }
    }

    private void changeOrientation() {

        if (!isLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isLandscape = true;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isLandscape = false;
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return stoneSearchInfoResult;
    }

    private void init() {
        idIgBack.setOnClickListener(this);
        lvStone.setXListViewListener(this);
        lvStone.setAutoLoadEnable(true);
        lvStone.setPullRefreshEnable(true);
        lvStone.setPullLoadEnable(true);
        tvQutedPriceAll.setOnClickListener(this);
        titleText.setText("搜索结果");
        tvItemWeight.setOnClickListener(this);
        tvReset.setOnClickListener(this);
    }


    private void initTitleView() {
        StoneSearchInfoResult.DataBean dataBean = stoneSearchInfoResult.getData();
        tvRight.setOnClickListener(this);
        listTitle = dataBean.getStone().getHeadline();
        if (listTitle == null) {
            return;
        }
        tvIscheckStone.setText(listTitle.get(0));
        tvItemWeight.setText(listTitle.get(1));
        tvItemPrice.setText(listTitle.get(2));
        tvItemShape.setText(listTitle.get(3));
        tvItemColor.setText(listTitle.get(4));
        tvItemPurity.setText(listTitle.get(5));
        tvItemCut.setText(listTitle.get(6));
        tvItemPolish.setText(listTitle.get(7));
        tvItemSymmetric.setText(listTitle.get(8));
        tvItemFluorescence.setText(listTitle.get(9));
        tvItemCertauth.setText(listTitle.get(10));
        tvItemCerauthNumber.setText(listTitle.get(11));
    }

    public void loadNetData() {
//        baseShowWatLoading();
        String url = "";
        url = AppURL.URL_STONE_LIST + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + page + "&certAuth=" + stoneSearchInfo.getCerAuth() + "&color=" + stoneSearchInfo.getColor() + "&shape=" + stoneSearchInfo.getShape()
                + "&purity=" + stoneSearchInfo.getPurity() + "&cut=" + stoneSearchInfo.getCut() + "&polishing=" + stoneSearchInfo.getPolishing() + "&symmetric=" + stoneSearchInfo.getSymmetric() + "&fluorescence=" + stoneSearchInfo.getFluorescence()
                + "&price=" + stoneSearchInfo.getPrice() + "&weight=" + stoneSearchInfo.getWeight() + "&orderby=" + orderby + "&percent=" + stoneSearchInfo.getPercent();
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
//                baseHideWatLoading();
                L.e("result" + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    stoneSearchInfoResult = new Gson().fromJson(result, StoneSearchInfoResult.class);
                    if (stoneSearchInfoResult.getData() == null) {
                        ToastManager.showToastReal("没有数据！");
                        return;
                    }
                    init();
                    setXListview(stoneSearchInfoResult);

                } else if (error.equals("2")) {

                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastWhendebug(message);
                    L.e(message);
                }
            }

            @Override
            public void onFail(String fail) {
//                baseHideWatLoading();
            }

        });
    }

    private void setXListview(StoneSearchInfoResult stoneSearchInfoResult) {
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
        lvStone.stopLoadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                changeOrientation();
                break;
            case R.id.id_ig_back:
                finish();
                break;
            case R.id.tv_quoted_price_all:
                quotedPrice(stoneSearchResultAdapter.getQuotedPriceId());
                break;
            case R.id.tv_item_weight:
                setorderBy();
                break;
            case R.id.tv_reset:
                stoneSearchInfoResult = null;
                list.clear();
                loadNetData();
                break;
        }
    }

    private void setorderBy() {
        list.clear();
        page = 1;
        ordertimes++;
        Drawable drawable = null;
        switch (ordertimes % 3) {
            case 0:
                orderby = "";
                drawable = this.getResources().getDrawable(R.drawable.icon_sort);
                break;
            case 1:
                orderby = "weight_asc";
                drawable = this.getResources().getDrawable(R.drawable.icon_sort_d);
                break;
            case 2:
                orderby = "weight_desc";
                drawable = this.getResources().getDrawable(R.drawable.icon_sort_u);
                break;
        }
        loadNetData();
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        tvItemWeight.setCompoundDrawables(null, null, drawable, null);
    }


    @Override
    public void onRefresh() {
        page = 1;
        list.clear();
        pullStatus = PULL_REFRESH;
        loadNetData();
        lvStone.stopRefresh();

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
            lvStone.stopRefresh();
        }
    }

    @Override
    public void quotedPrice(String id) {
        if (id.equals("")) {
            ToastManager.showToastReal("您未选择产品！");
        } else {
            Intent intent = new Intent(this, StoneQuotedPriceActivity.class);
            intent.putExtra("stoneIds", id);
            intent.putExtra("percent", stoneSearchInfo.getPercent());
            startActivity(intent);
        }

    }

    private LoadingWaitDialog loadingDialog;

    protected void baseShowWatLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingWaitDialog(this, getString(R.string.pull_to_refresh_footer_refreshing_label));
            loadingDialog.show();
        }
        //SystemClock.sleep(1000);
    }

    public void baseHideWatLoading() {
        if (loadingDialog == null) return;
        if (loadingDialog != null || loadingDialog.isShowing()) {
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }
}
