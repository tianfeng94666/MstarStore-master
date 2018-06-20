package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.ItemDecoration.SpaceItemDecoration;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.PurityRecycleViewAdapter;
import com.qx.mstarstoreapp.adapter.RecycleViewPartAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.CommitOrderResult;
import com.qx.mstarstoreapp.json.GetScanData;
import com.qx.mstarstoreapp.json.GetScanProductData;
import com.qx.mstarstoreapp.json.PurityEntity;
import com.qx.mstarstoreapp.manager.FitGridLayoutManager;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.CustomselectStringButton;
import com.qx.mstarstoreapp.viewutils.FlyBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;
import zxing.activity.CaptureActivity;

/**
 * Created by Administrator on 2018/2/1 0001.
 */

public class QuickStyleInfromationActivity extends BaseActivity {


    @Bind(R.id.flybanner)
    FlyBanner flybanner;
    @Bind(R.id.ll_banner)
    LinearLayout llBanner;
    @Bind(R.id.id_ig_sao)
    ImageView idIgSao;
    @Bind(R.id.id_store_title)
    TextView idStoreTitle;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.tv_amount_title)
    TextView tvAmountTitle;
    @Bind(R.id.iv_reduce)
    ImageView ivReduce;
    @Bind(R.id.et_ring_amount)
    EditText etRingAmount;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.ll_amount)
    LinearLayout llAmount;
    @Bind(R.id.tv_certcode)
    TextView tvCertcode;
    @Bind(R.id.ll_certcode)
    LinearLayout llCertcode;
    @Bind(R.id.id_cus_store_size)
    CustomselectStringButton idCusStoreSize;
    @Bind(R.id.line_making)
    View lineMaking;
    @Bind(R.id.tv_color)
    TextView tvColor;
    @Bind(R.id.ll_color)
    LinearLayout llColor;
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.tv_look)
    TextView tvLook;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;
    @Bind(R.id.ll_cancle)
    LinearLayout llCancle;
    @Bind(R.id.id_fr)
    FrameLayout idFr;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.tips_loading_msg)
    TextView tipsLoadingMsg;
    @Bind(R.id.lny_loading_layout)
    LinearLayout lnyLoadingLayout;
    @Bind(R.id.rc_color)
    RecyclerView rcColor;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_staticstics)
    TextView tvStaticstics;
    @Bind(R.id.et_remark)
    EditText etRemark;
    private GetScanProductData modelDetail;

    private List<PurityEntity> modelPuritys;
    private String itemId;
    private ArrayList<String> getPicm;
    private ArrayList<String> getPicB;
    private List<GetScanProductData.DataBean.ModelBean.PicsBean> pics;
    private PurityRecycleViewAdapter rcColorAdapter;
    private String orderId;
    private GetScanData getScanDate;
    private GetScanProductData.DataBean dataEntity;
    private GetScanProductData.DataBean.ModelBean modelEntity;
    private String productId;
    private String purityId;
    private int orderAmount;
    private QBadgeView badge;
    private FitGridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_styleinformation);
        ButterKnife.bind(this);
        getDate(getIntent());
        getViewData();
    }

    private void getDate(Intent intent) {
        itemId = intent.getStringExtra("itemId");
    }


    /**
     * 获取加载扫描页面的基本信息
     */
    private void getViewData() {
        baseShowWatLoading();
        String url;
        url = AppURL.URL_GET_MODELDETAIL_FORSCAN + "tokenKey=" + BaseApplication.getToken();
        //款号页面
        L.e("url=", url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.e("success", result);
                baseHideWatLoading();
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    getScanDate = new Gson().fromJson(result, GetScanData.class);

                    if (getScanDate.getData() == null) {
                        return;
                    }
                    orderAmount = Integer.parseInt(getScanDate.getData().getWaitOrderCount());
                    modelPuritys = getScanDate.getData().getModelPuritys();
                    initView();
                    scan();
                } else if (error == 2) {
                    ToastManager.showToastReal(getString(R.string.data_error));
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal("数据加载错误:+" + message);
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
                showToastReal(fail);
                showToastReal("数据获取失败");
            }

        });
    }


    @Override
    /**
     * 加载对应id的产品
     */
    public void loadNetData() {


    }

    /**
     * 处理后台数据
     *
     * @param result
     */
    private void dealData(String result) {

        L.e(result);
        int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
        if (error == 0) {
            modelDetail = new Gson().fromJson(result, GetScanProductData.class);
            dataEntity = modelDetail.getData();
            if (dataEntity == null) {
                return;
            }
            modelEntity = dataEntity.getModel();
            etRemark.setText(dataEntity.getModel().getRemarks());
            pics = modelEntity.getPics();
            productId = modelEntity.getId();
            idStoreTitle.setText(modelEntity.getTitle());
            tvWeight.setText(modelEntity.getWeight());
            initView();

        } else if (error == 2) {
            ToastManager.showToastReal(getString(R.string.data_error));
        } else {
            String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
            L.e(message);
            ToastManager.showToastReal("数据加载错误:+" + message);
        }
    }

    /**
     * 图片数据设置
     */
    public void initFlybanner() {
        if (!UIUtils.isScreenChange(this)) {
            ViewGroup.LayoutParams lp = flybanner.getLayoutParams();
            int screenWidth = UIUtils.getWindowWidth();
            lp.height = (int) (screenWidth);
            flybanner.setLayoutParams(lp);
        } else {
            ViewGroup.LayoutParams lp = llBanner.getLayoutParams();

            int screenHeight = UIUtils.getWindowHight();
            lp.height = screenHeight - UIUtils.dip2px(50);
            lp.width = screenHeight - UIUtils.dip2px(50);
            llBanner.setLayoutParams(lp);
        }
        /**
         * 创建多个item （每一条viewPager都是一个item） 从服务器获取完数据（图片url地址） 后，再设置适配器
         */
        getPicm = new ArrayList<>();
        getPicB = new ArrayList<>();
        if (pics == null || pics.size() == 0) {
            return;
        }
        for (int i = 0; i < pics.size(); i++) {
            getPicm.add(pics.get(i).getPicm());
            getPicB.add(pics.get(i).getPicb());
        }
        if (UIUtils.isPad(this)) {
            if (getPicB != null && getPicB.size() != 0) {
                flybanner.setImagesUrl(getPicB);
            }

        } else {
            if (getPicm != null && getPicm.size() != 0) {
                flybanner.setImagesUrl(getPicm);
            }

        }

        flybanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (pics.size() == 0 && StringUtils.isEmpty(pics.get(0).getPicb())) {
                    return;
                }
                //主页图片
                Intent intent = new Intent(QuickStyleInfromationActivity.this,
                        ImageBrowserActivity.class);
                intent.putExtra("photos", getPicB);
                intent.putExtra("position", position);
                startActivity(intent);
                //设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }

    private void initView() {
        initFlybanner();
        initListView();
        badge = new QBadgeView(this);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge.bindTarget(tvLook);
        remind(orderAmount);
        tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ConfirmOrderActivity.class, null);
            }
        });
        idIgSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

    }

    private void remind(int count) {
        boolean isVisible = false;
        if (count != 0) {
            isVisible = true;
        }

        //BadgeView的具体使用
        badge.setBadgeText(count + ""); // 需要显示的提醒类容
        badge.setBadgeGravity(Gravity.END | Gravity.TOP);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge.setBadgeTextColor(Color.WHITE); // 文本颜色
        int hint = Color.rgb(200, 39, 73);
        badge.setBadgeBackgroundColor(hint); // 提醒信息的背景颜色，自己设置
        badge.setBadgeTextSize(10, true); // 文本大小
        badge.setBadgePadding(3, true); // 水平和竖直方向的间距+-
        if (isVisible) {
            badge.setVisibility(View.VISIBLE);  // 只有显示
        } else {
            badge.setVisibility(View.GONE);//影藏显示
        }
    }

    private void initListView() {
        if (rcColorAdapter == null) {
            rcColorAdapter = new PurityRecycleViewAdapter(modelPuritys, this, new RecycleViewPartAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    tvColor.setText(modelPuritys.get(position).getTitle());
                    purityId = modelPuritys.get(position).getId();
                }
            });


            rcColor.addItemDecoration(new SpaceItemDecoration(UIUtils.dip2px(4)));

            mLayoutManager = new FitGridLayoutManager(this, rcColor, 3, GridLayoutManager.VERTICAL, false);
            rcColor.setLayoutManager(mLayoutManager);
            rcColor.setAdapter(rcColorAdapter);
        }else {
            rcColorAdapter.notifyDataSetChanged();
        }
    }


    /*扫描二维码页面*/
    public void scan() {
        Intent inten = new Intent(QuickStyleInfromationActivity.this, CaptureActivity.class);
        startActivityForResult(inten, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e("requestCode" + requestCode);
        if (requestCode == 0 && data != null) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            scanProduct(result);
            L.e("onActivityResult result" + result);
        }

    }

    private void scanProduct(String result) {

        String url = AppURL.URL_GET_MODELDETAIL_FORSCAN_INFO + "tokenKey=" + BaseApplication.getToken() + "&modelNum=" + result;

        L.e("scan获取款号" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.e("success", result);
                dealData(result);
            }

            @Override
            public void onFail(String fail) {
                lnyLoadingLayout.setVisibility(View.GONE);
                showToastReal(fail);
                showToastReal("数据获取失败");
            }

        });
    }


    /**
     * 添加到当前订单
     */
    private void addOrder() {
        baseShowWatLoading();
        String url = AppURL.URL_GET_MODELDETAIL_FORSCAN_COMMIT_CURRENT_ORDER + "tokenKey=" + BaseApplication.getToken() + "&productId=" + productId + "&number=" + etRingAmount.getText().toString() + "&modelPurityId=" + purityId+"&remarks="+etRemark.getText().toString();

        L.e("add" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                Log.e("success", result);
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    CommitOrderResult addOrderResult = new Gson().fromJson(result, CommitOrderResult.class);
                    showToastReal(addOrderResult.getMessage());
                    try {
                        remind(Integer.parseInt(addOrderResult.getData().getWaitOrderCount()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    scan();
                } else if (error == 2) {
                    ToastManager.showToastReal(getString(R.string.data_error));
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal("数据加载错误:+" + message);
                }

            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
                lnyLoadingLayout.setVisibility(View.GONE);
                showToastReal(fail);
                showToastReal("数据获取失败");
            }

        });
    }


    private void reduce() {

        String st = etRingAmount.getText().toString();
        double amount;
        if (st.equals("")) {
            amount = 0;
        } else {
            amount = Double.parseDouble(st);
        }

        if (amount > 0 && amount == 0.5) {
            etRingAmount.setText(amount - 0.5 + "");
        } else if (amount == 1) {
            etRingAmount.setText("");
        } else if (amount > 1) {
            if (amount % 1 == 0.5) {
                etRingAmount.setText(amount - 1 + "");
            } else {
                etRingAmount.setText((int) Math.floor(amount - 1) + "");
            }
        }

    }

    private void add() {

        String st = etRingAmount.getText().toString();
        double amount;
        if (st.equals("") || st.equals("0.0")) {
            amount = 0;
        } else {
            amount = Double.parseDouble(st);
        }

        if (amount >= 0 && amount < 0.5) {
            etRingAmount.setText((int) (amount + 1) + "");
        } else if (amount == 0.5) {
            etRingAmount.setText(amount + 1 + "");
        } else if (amount >= 1) {
            if (amount % 1 == 0.5) {
                etRingAmount.setText(amount + 1 + "");
            } else {
                etRingAmount.setText((int) Math.floor(amount + 1) + "");
            }

        }

    }

    @OnClick({R.id.iv_reduce, R.id.iv_add, R.id.id_ig_back, R.id.tv_look, R.id.tv_cancle, R.id.tv_confirm, R.id.iv_right, R.id.tv_staticstics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_reduce:
                reduce();
                break;
            case R.id.iv_add:
                add();
                break;
            case R.id.id_ig_back:
                finish();
                break;
            case R.id.tv_confirm:
                addOrder();
                break;
            case R.id.tv_cancle:
                scan();
                break;
            case R.id.iv_right:
                openActivity(ClassifyOrderActivity.class, null);
                break;
            case R.id.tv_staticstics:
                openActivity(StatisticsActivity.class, null);
                break;

        }
    }
}
