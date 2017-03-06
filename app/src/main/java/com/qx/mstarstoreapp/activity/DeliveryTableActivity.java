package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.DeliveryAdapter;
import com.qx.mstarstoreapp.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class DeliveryTableActivity extends BaseActivity {

    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_table);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ArrayList<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(false);
        DeliveryAdapter deliveryAdapter = new DeliveryAdapter(this,list);
        lvProducts.setAdapter(deliveryAdapter);
    }

    @Override
    public void loadNetData() {

    }
}
