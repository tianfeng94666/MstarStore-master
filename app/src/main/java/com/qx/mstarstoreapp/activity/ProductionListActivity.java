package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.ModelListEntity;
import com.qx.mstarstoreapp.json.OrderInfoEntity;
import com.qx.mstarstoreapp.json.ProductListResult;
import com.qx.mstarstoreapp.net.ImageLoadOptions;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * 创建人：Yangshao
 * 创建时间：2016/12/5 14:42
 * @version  生产中 的订单
 *
 */
public class ProductionListActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {


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
    @Bind(R.id.id_pd_lv)
    ListView idPdLv;
    @Bind(R.id.pull_refresh_view)
    PullToRefreshView pullRefreshView;
    @Bind(R.id.id_tv_confirfilterr)
    TextView idTvConfirfilterr;
    @Bind(R.id.id_tv_showdialog)
    TextView idTvShowdialog;
    @Bind(R.id.tips_loading_msg)
    TextView tipsLoadingMsg;
    @Bind(R.id.lny_loading_layout)
    LinearLayout lnyLoadingLayout;
    @Bind(R.id.root_view)
    RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_production);
        ButterKnife.bind(this);
        lnyLoadingLayout.setVisibility(View.VISIBLE);
        getIntentData();
        initView();
        loadNetData();
    }


    public void onBack(View view) {
        finish();
    }

    public void getIntentData() {
        Bundle extras = getIntent().getExtras();
        orderNum = extras.getString("orderNum");

    }


    List<ModelListEntity> orderlList;
    String orderNum;
    OrderInfoEntity orderInfo;

    @Override
    public void loadNetData() {
        String url = AppURL.URL_PD_ORDER_DETAIL + "orderNum=" + orderNum + "&tokenKey=" + BaseApplication.getToken();
        L.e("ProductionListActivity" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    lnyLoadingLayout.setVisibility(View.GONE);
                    ProductListResult productListResult = new Gson().fromJson(result, ProductListResult.class);
                    if (productListResult.getData() == null) {
                        return;
                    }
                    ProductListResult.DataEntity productListResultData = productListResult.getData();
                    List<ModelListEntity> modelList = productListResultData.getModelList();
                    orderInfo = productListResultData.getOrderInfo();
                    initViewData(modelList);
                }
                if (error == 1) {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
                if (error == 2) {
                    loginToServer(ProductionListActivity.class);
                }

            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    public void loadHistoryNetData() {
        baseShowWatLoading();
        String url = AppURL.URL_PD_ORDER_DETAIL_HISTORY + "orderNum=" + orderNum + "&tokenKey=" + BaseApplication.getToken();
        L.e("ProductionListActivity" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    lnyLoadingLayout.setVisibility(View.GONE);
                    ProductListResult productListResult = new Gson().fromJson(result, ProductListResult.class);
                    if (productListResult.getData() == null) {
                        return;
                    }
                    ProductListResult.DataEntity productListResultData = productListResult.getData();
                    List<ModelListEntity> modelList = productListResultData.getModelList();
                    orderInfo = productListResultData.getOrderInfo();
                    initViewData(modelList);
                }
                if (error == 1) {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
                if (error == 2) {
                    loginToServer(ProductionListActivity.class);
                }

            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();

            }
        });
    }

    private void initViewData(List<ModelListEntity> modelList) {
        View view = View.inflate(this, R.layout.layout_order_detail, null);
        TextView idOrderNum = (TextView) view.findViewById(R.id.id_order_num);
        TextView idOrderDate = (TextView) view.findViewById(R.id.id_order_date);
        TextView idUpdateDate = (TextView) view.findViewById(R.id.id_update_date);
        TextView idTvInvo = (TextView) view.findViewById(R.id.id_tv_invo);
        TextView tvRemark = (TextView) view.findViewById(R.id.tv_remark);
        TextView idTvDetail = (TextView) view.findViewById(R.id.id_tv_detail);
        idOrderNum.setText("订单编号：" + orderInfo.getOrderNum() + "");
        idOrderDate.setText("下单日期：" + orderInfo.getOrderDate());
        idUpdateDate.setText("审核日期：" + orderInfo.getConfirmDate());
        idTvInvo.setText("发票： " + "类型：" + orderInfo.getInvoiceType() + " 抬头：" + orderInfo.getInvoiceTitle());
        tvRemark.setText("备注：" + orderInfo.getOrderNote());
        idTvDetail.setText(orderInfo.getOtherInfo());
        idPdLv.removeHeaderView(view);
        idPdLv.addHeaderView(view);
        if (pullStauts != PULL_LOAD) {
            orderlList.clear();
        }
        orderlList.addAll(modelList);
        adapter.setListData(orderlList);
        endNetRequse();
    }

    ProductionAdapter adapter;

    private void initView() {
        titleText.setText("生产中");
        idPdLv = (ListView) findViewById(R.id.id_pd_lv);
        pullRefreshView = (PullToRefreshView) findViewById(R.id.pull_refresh_view);
        pullRefreshView.setOnFooterRefreshListener(this);
        pullRefreshView.setOnHeaderRefreshListener(this);
        orderlList = new ArrayList<>();
        adapter = new ProductionAdapter(orderlList, R.layout.layout_order);
        idPdLv.setAdapter(adapter);
        idTvShowdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(ProductionListActivity.this, orderInfo.getOrderNum(), 1);
                progressDialog.showAsDropDown(rootView);
            }
        });
        idTvConfirfilterr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHistoryNetData();
            }
        });
    }


    public void endNetRequse() {
        tempCurpage = cupage;
        if (pullStauts == PULL_LOAD) {
            pullRefreshView.onFooterRefreshComplete();
        }
        if (pullStauts == PULL_REFRESH) {
            pullRefreshView.onHeaderRefreshComplete();
        }
        pullStauts = 0;
    }

    public static final int PULL_LOAD = 1;
    public static final int PULL_REFRESH = 2;
    public int pullStauts;
    public int cupage;
    public int tempCurpage;
    private int listCount = 0;

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        if (listCount > adapter.getCount()) {
            tempCurpage = cupage;
            cupage++;
            pullStauts = PULL_LOAD;
            loadNetData();
        } else {
            ToastManager.showToastReal("没有更多数据");
            pullRefreshView.onFooterRefreshComplete();
        }

    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        tempCurpage = cupage;
        cupage = 1;
        pullStauts = PULL_REFRESH;
        loadNetData();
    }

    public class ProductionAdapter extends CommonAdapter<ModelListEntity> {
        public ProductionAdapter(List<ModelListEntity> mDatas, int itemLayoutId) {
            super(mDatas, itemLayoutId);
            L.e("ProductionAdapter");
        }

        @Override
        public void convert(int position, BaseViewHolder helper, ModelListEntity item) {
            RelativeLayout reView = helper.getView(R.id.item_button_layout);
            ImageView productImg = helper.getView(R.id.product_img);
            reView.setVisibility(View.GONE);
            helper.setText(R.id.product_name, item.getTitle());
            helper.setText(R.id.product_price, item.getPrice());
            helper.setText(R.id.product_norms, item.getBaseInfo());
            TextView tvNumber = helper.getView(R.id.product_number);
            tvNumber.setTextColor(getResources().getColor(R.color.theme_red));
            helper.setText(R.id.product_number, "×" + item.getNumber() + "件");
            helper.setText(R.id.id_tv_information, item.getInfo());
            ImageLoader.getInstance().displayImage(item.getPic(), productImg, ImageLoadOptions.getOptions());
        }

    }
}
