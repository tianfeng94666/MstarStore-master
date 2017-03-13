package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.FinishTableMoreResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class FinishTableMoreActivity extends BaseActivity {

    String recNumber;


    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.tv_customer_name)
    TextView tvCustomerName;
    @Bind(R.id.tv_finish_quality)
    TextView tvFinishQuality;
    @Bind(R.id.tv_finish_date)
    TextView tvFinishDate;
    @Bind(R.id.tv_finish_number)
    TextView tvFinishNumber;
    @Bind(R.id.tv_finish_table_number)
    TextView tvFinishTableNumber;
    @Bind(R.id.tv_finish_order_date)
    TextView tvFinishOrderDate;
    @Bind(R.id.tv_finish_order_number)
    TextView tvFinishOrderNumber;
    @Bind(R.id.tv_material)
    TextView tvMaterial;
    @Bind(R.id.tv_material_money_tv)
    TextView tvMaterialMoneyTv;
    @Bind(R.id.tv_material_money)
    TextView tvMaterialMoney;
    @Bind(R.id.ll_material_product_name)
    LinearLayout llMaterialProductName;
    @Bind(R.id.ll_material)
    LinearLayout llMaterial;
    @Bind(R.id.tv_work_cost)
    TextView tvWorkCost;
    @Bind(R.id.tv_work_money_tv)
    TextView tvWorkMoneyTv;
    @Bind(R.id.tv_work_cost_money)
    TextView tvWorkCostMoney;
    @Bind(R.id.ll_work_name)
    LinearLayout llWorkName;
    @Bind(R.id.ll_work_cost)
    LinearLayout llWorkCost;
    @Bind(R.id.tv_other_work_cost)
    TextView tvOtherWorkCost;
    @Bind(R.id.tv_other_work_money_tv)
    TextView tvOtherWorkMoneyTv;
    @Bind(R.id.tv_other_work_money)
    TextView tvOtherWorkMoney;
    @Bind(R.id.ll_other_cost_name)
    LinearLayout llOtherCostName;
    @Bind(R.id.ll_other_work_cost)
    LinearLayout llOtherWorkCost;
    @Bind(R.id.tv_stone)
    TextView tvStone;
    @Bind(R.id.tv_stone_money_tv)
    TextView tvStoneMoneyTv;
    @Bind(R.id.tv_stone_money)
    TextView tvStoneMoney;
    @Bind(R.id.ll_stone_product_name)
    LinearLayout llStoneProductName;
    @Bind(R.id.ll_stone)
    LinearLayout llStone;
    @Bind(R.id.tv_amount)
    TextView tvAmount;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_member)
    TextView tvMember;

    private FinishTableMoreResult finishTableMoreResult;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_table_more);
        ButterKnife.bind(this);
        init();
        getDate();
        loadNetData();
    }

    private void init() {
        titleText.setText("结算单");
        idIgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDate() {
        recNumber = getIntent().getStringExtra("recNumber");
    }

    @Override
    public void loadNetData() {
        String url = "";
        url = AppURL.URL_CODE_FINISH_DETAIL + "tokenKey=" + BaseApplication.getToken() + "&recNum=" + recNumber;
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    finishTableMoreResult = new Gson().fromJson(result, FinishTableMoreResult.class);
                    initView();

                } else if (error.equals("2")) {
                    loginToServer(CustomMadeActivity.class);
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastWhendebug(message);
                    L.e(message);
                }
            }

            @Override
            public void onFail(String fail) {
            }

        });
    }

    private void initView() {

        //材料列表
        FinishTableMoreResult.DataBean.RecMaterialsBean recMaterialsBean = finishTableMoreResult.getData().getRecMaterials();
        List<FinishTableMoreResult.DataBean.RecMaterialsBean.ListBean> materialList = recMaterialsBean.getList();
        tvMaterial.setText(recMaterialsBean.getTitle());
        tvMaterialMoney.setText("¥" + recMaterialsBean.getMoneySum());
        for (int i = 0; i < materialList.size(); i++) {
            FinishTableMoreResult.DataBean.RecMaterialsBean.ListBean bean = materialList.get(i);
            if (i == 0 || i == (materialList.size() - 1)) {
                llMaterialProductName.addView(setText(bean.getTypeName(), R.color.text_color, R.color.theme_bg));
                LinearLayout ll = getLinearLayout(R.color.theme_bg);
                setMaterialLl(ll, bean, R.color.theme_bg, R.color.text_color);
                llMaterial.addView(ll);
            } else {
                llMaterialProductName.addView(setText(bean.getTypeName(), R.color.text_color2, R.color.white));
                LinearLayout ll = getLinearLayout(R.color.white);
                setMaterialLl(ll, bean, R.color.white, R.color.text_color2);
                llMaterial.addView(ll);
            }
        }
        //获得加工费列表
        FinishTableMoreResult.DataBean.RecProcessExpensesesBean recProcessExpensesesBean = finishTableMoreResult.getData().getRecProcessExpenseses();
        List<FinishTableMoreResult.DataBean.RecProcessExpensesesBean.ListBeanXX> workcostList = recProcessExpensesesBean.getList();
        tvWorkCost.setText(recProcessExpensesesBean.getTitle());
        tvWorkCostMoney.setText("¥" + recProcessExpensesesBean.getMoneySum());
        for (int i = 0; i < workcostList.size(); i++) {
            FinishTableMoreResult.DataBean.RecProcessExpensesesBean.ListBeanXX bean = workcostList.get(i);
            if (i == 0 || i == (workcostList.size() - 1)) {
                llWorkName.addView(setText(bean.getTypeName(), R.color.text_color, R.color.theme_bg));
                LinearLayout ll = getLinearLayout(R.color.theme_bg);
                setWrokCostLl(ll, bean, R.color.theme_bg, R.color.text_color);
                llWorkCost.addView(ll);
            } else {
                llWorkName.addView(setText(bean.getTypeName(), R.color.text_color2, R.color.white));
                LinearLayout ll = getLinearLayout(R.color.white);
                setWrokCostLl(ll, bean, R.color.white, R.color.text_color2);
                llWorkCost.addView(ll);
            }
        }

        //其他加工费
        FinishTableMoreResult.DataBean.RecOtherProcessExpensesesBean recOtherProcessExpensesesBean = finishTableMoreResult.getData().getRecOtherProcessExpenseses();
        List<FinishTableMoreResult.DataBean.RecOtherProcessExpensesesBean.ListBeanX> otherWorkcostList = recOtherProcessExpensesesBean.getList();
        tvOtherWorkCost.setText(recOtherProcessExpensesesBean.getTitle());
        tvOtherWorkMoney.setText("¥" + recOtherProcessExpensesesBean.getMoneySum());
        for (int i = 0; i < otherWorkcostList.size(); i++) {
            FinishTableMoreResult.DataBean.RecOtherProcessExpensesesBean.ListBeanX bean = otherWorkcostList.get(i);
            if (i == 0 || i == (otherWorkcostList.size() - 1)) {
                llOtherCostName.addView(setText(bean.getEnChase(), R.color.text_color, R.color.theme_bg));
                LinearLayout ll = getLinearLayout(R.color.theme_bg);
                setOtherWrokCostLl(ll, bean, R.color.theme_bg, R.color.text_color);
                llOtherWorkCost.addView(ll);
            } else {
                llOtherCostName.addView(setText(bean.getEnChase(), R.color.text_color2, R.color.white));
                LinearLayout ll = getLinearLayout(R.color.white);
                setOtherWrokCostLl(ll, bean, R.color.white, R.color.text_color2);
                llOtherWorkCost.addView(ll);
            }
        }

        //宝石
        FinishTableMoreResult.DataBean.RecStonesBean recStonesBean = finishTableMoreResult.getData().getRecStones();
        List<FinishTableMoreResult.DataBean.RecStonesBean.ListBeanXXX> stoneList = recStonesBean.getList();
        tvStone.setText(recStonesBean.getTitle());
        tvStoneMoney.setText("¥" + recStonesBean.getMoneySum());
        for (int i = 0; i < stoneList.size(); i++) {
            FinishTableMoreResult.DataBean.RecStonesBean.ListBeanXXX bean = stoneList.get(i);
            if (i == 0 || i == (stoneList.size() - 1)) {
                llStoneProductName.addView(setText(bean.getStoneTypeName(), R.color.text_color, R.color.theme_bg));
                LinearLayout ll = getLinearLayout(R.color.theme_bg);
                setStoneLl(ll, bean, R.color.theme_bg, R.color.text_color);
                llStone.addView(ll);
            } else {
                llStoneProductName.addView(setText(bean.getStoneTypeName(), R.color.text_color2, R.color.white));
                LinearLayout ll = getLinearLayout(R.color.white);
                setStoneLl(ll, bean, R.color.white, R.color.text_color2);
                llStone.addView(ll);
            }
        }
        otherView();
    }

    private void otherView() {
        FinishTableMoreResult.DataBean.RecItemBean itemBean = finishTableMoreResult.getData().getRecItem();
        tvCustomerName.setText("客户:" + itemBean.getCustomerName());
        tvFinishQuality.setText("成色:" + itemBean.getPurityName());
        tvFinishDate.setText("结算日期:" + itemBean.getRecDate().substring(0, 10));
        tvFinishNumber.setText("订单编号:" + itemBean.getOrderNum());
        tvFinishTableNumber.setText("帐套号:"+itemBean.getAccountID());
        tvFinishOrderDate.setText("下单日期:" + itemBean.getOrderDate().substring(0, 10));
        tvFinishOrderNumber.setText("结算单号:" + itemBean.getRecNum());
        tvAmount.setText(itemBean.getNumber() + "件");
        tvMoney.setText("¥" + itemBean.getTotalPrice());
        tvMember.setText(itemBean.getRecOperator());

    }

    /**
     * 设置宝石列表
     *
     * @param ll
     * @param bean
     * @param textBackgroundColor
     * @param textColor
     */
    private void setStoneLl(LinearLayout ll, FinishTableMoreResult.DataBean.RecStonesBean.ListBeanXXX bean, int textBackgroundColor, int textColor) {
        if(UIUtils.isTabletDevice(this)){
            ll.addView(setText2(bean.getStoneTypeName(), textColor, textBackgroundColor));
        }
        ll.addView(setText2(bean.getComeFrom(), textColor, textBackgroundColor));
        ll.addView(setText2(bean.getRecSStoneSN(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecSMoney(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecSQuantity(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecSWeight(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecSUPrice(), textColor, textBackgroundColor));
    }

    /**
     * 设置其他加工费
     *
     * @param ll
     * @param bean
     * @param textBackgroundColor
     * @param textColor
     */
    private void setOtherWrokCostLl(LinearLayout ll, FinishTableMoreResult.DataBean.RecOtherProcessExpensesesBean.ListBeanX bean, int textBackgroundColor, int textColor) {
        if(UIUtils.isTabletDevice(this)){
            ll.addView(setText2(bean.getEnChase(), textColor, textBackgroundColor));
        }
        ll.addView(setText(bean.getRecOQuantity(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecOUPrice(), textColor, textBackgroundColor));
        ll.addView(setText("", textColor, textBackgroundColor));
        ll.addView(setText("", textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecOMoney(), textColor, textBackgroundColor));
    }

    /**
     * 设置加工费列表
     *
     * @param ll
     * @param bean
     * @param textBackgroundColor
     * @param textColor
     */
    private void setWrokCostLl(LinearLayout ll, FinishTableMoreResult.DataBean.RecProcessExpensesesBean.ListBeanXX bean, int textBackgroundColor, int textColor) {
        if(UIUtils.isTabletDevice(this)){
            ll.addView(setText2(bean.getTypeName(), textColor, textBackgroundColor));
        }
        ll.addView(setText(bean.getRecPQuantity(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecPUPrice(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecPFeeAddTotal(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getSampleFee(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecPMoney(), textColor, textBackgroundColor));
    }

    /**
     * 设置material Linearlayout
     *
     * @param ll
     * @param bean
     */
    private void setMaterialLl(LinearLayout ll, FinishTableMoreResult.DataBean.RecMaterialsBean.ListBean bean, int textBackgroundColor, int textColor) {
        if(UIUtils.isTabletDevice(this)){
            ll.addView(setText2(bean.getTypeName(), textColor, textBackgroundColor));
        }
        ll.addView(setText(bean.getRecMWeight(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecMRatio(), textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecGoldPrice(), textColor, textBackgroundColor));
        ll.addView(setText("", textColor, textBackgroundColor));
        ll.addView(setText(bean.getRecMMoney(), textColor, textBackgroundColor));
    }

    private LinearLayout getLinearLayout(int colorBG) {
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(params);
        ll.setBackgroundResource(colorBG);
        return ll;
    }

    private TextView setText(String st, int textcolor, int bgcolor) {
        TextView tv = new TextView(this);
        tv.setText(st);
        LinearLayout.LayoutParams params;
        if(UIUtils.isTabletDevice(this)){
            params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        }else {
           params = new LinearLayout.LayoutParams(UIUtils.dip2px(80), ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        tv.setTextColor(getResources().getColor(textcolor));
        tv.setBackgroundResource(bgcolor);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        return tv;

    }

    private TextView setText2(String st, int textcolor, int bgcolor) {
        TextView tv = new TextView(this);
        tv.setText(st);
        LinearLayout.LayoutParams params;
        if(UIUtils.isTabletDevice(this)){
            params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,2);
        }else {
            params = new LinearLayout.LayoutParams(UIUtils.dip2px(160), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        tv.setTextColor(getResources().getColor(textcolor));
        tv.setBackgroundResource(bgcolor);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        return tv;

    }
}
