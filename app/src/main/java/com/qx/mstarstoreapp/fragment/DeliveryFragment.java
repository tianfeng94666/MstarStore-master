package com.qx.mstarstoreapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.CustomMadeActivity;
import com.qx.mstarstoreapp.adapter.DeliveryAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.BaseFragment;
import com.qx.mstarstoreapp.json.DeliveryTableResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class DeliveryFragment extends BaseFragment {

    String momNumber;//出库单号
    List<DeliveryTableResult.DataBean.ModelListBean> list;//出货单详情列表
    DeliveryTableResult deliveryTableResult;
    DeliveryTableResult.DataBean.MoItemBean moItemBean;
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.tv_customer_name)
    TextView tvCustomerName;
    @Bind(R.id.tv_delivery_order_number)
    TextView tvDeliveryOrderNumber;
    @Bind(R.id.tv_delivery_gold_price)
    TextView tvDeliveryGoldPrice;
    @Bind(R.id.tv_delivery_number)
    TextView tvDeliveryNumber;
    @Bind(R.id.tv_delivery_amount)
    TextView tvDeliveryAmount;
    @Bind(R.id.tv_delivery_quality)
    TextView tvDeliveryQuality;
    @Bind(R.id.tv_delivery_sum_money)
    TextView tvDeliverySumMoney;
    @Bind(R.id.lv_products)
    ListView lvProducts;
    private DeliveryAdapter deliveryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.activity_delivery_table, null);
        ButterKnife.bind(this, view);
        init();
        getDate();
        loadNetData();
        return view;
    }

    private void init() {
        titleText.setText("出货单");
        idIgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }


    private void getDate() {
        momNumber =  getActivity().getIntent().getStringExtra("momNumber");
    }

    private void initView() {
        tvCustomerName.setText("客户：" + moItemBean.getCustomerName());
        tvDeliveryOrderNumber.setText("订单编号：" + moItemBean.getOrderNum());
        tvDeliveryGoldPrice.setText("金价：" + moItemBean.getGoldPrice());
        tvDeliveryNumber.setText("出库单号：" + moItemBean.getMoNum());
        tvDeliveryAmount.setText("件数：" + moItemBean.getNumber());
        tvDeliveryQuality.setText("成色：" + moItemBean.getPurityName());
        tvDeliverySumMoney.setText(moItemBean.getTotalPrice());
        if (list != null) {
            deliveryAdapter = new DeliveryAdapter( getActivity(), list);
        }
        lvProducts.setAdapter(deliveryAdapter);
    }


    public void loadNetData() {
        String url = "";
        url = AppURL.URL_CODE_SENDING_DETAIL + "tokenKey=" + BaseApplication.getToken() + "&moNum=" + momNumber;
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest( getActivity(), url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    deliveryTableResult = new Gson().fromJson(result, DeliveryTableResult.class);
                    moItemBean = deliveryTableResult.getData().getMoItem();
                    list = deliveryTableResult.getData().getModelList();
                    if (moItemBean != null) {
                        initView();
                    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
