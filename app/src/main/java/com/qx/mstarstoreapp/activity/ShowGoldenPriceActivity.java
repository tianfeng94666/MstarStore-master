package com.qx.mstarstoreapp.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qx.mstarstoreapp.ItemDecoration.SpaceItemDecoration;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.GetGoldenPriceRecyclerViewAdapter;
import com.qx.mstarstoreapp.adapter.RecycleViewPartAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.GetGoldenPriceResult;
import com.qx.mstarstoreapp.json.SearchOrderResult;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class ShowGoldenPriceActivity extends BaseActivity {

    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_right)
    TextView tvRight;

    @Bind(R.id.tv_start_date)
    TextView tvStartDate;
    @Bind(R.id.rl_start_date)
    RelativeLayout rlStartDate;
    @Bind(R.id.rv_gloden_price)
    RecyclerView rvGlodenPrice;
    private int year;
    private int month;
    private int day;
    private StringBuilder sb = new StringBuilder();
    List<GetGoldenPriceResult.DataBean.PurityGoldPriceBean> purityGoldPrice;
    private GetGoldenPriceRecyclerViewAdapter getGoldenPriceRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gloden_price);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleText.setText("金价查询");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        String s = getDate(year, month, day);
        getCurrentDateGoldenPrices(s);
    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String url = AppURL.URL_GET_GOLDEN_PRICE + "tokenKey=" + BaseApplication.getToken() + sb.toString();
        L.e("url" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    GetGoldenPriceResult getGoldenPriceResult = new Gson().fromJson(result, GetGoldenPriceResult.class);
                    if (getGoldenPriceResult.getData() == null) {
                        return;
                    }
                    purityGoldPrice = getGoldenPriceResult.getData().getPurityGoldPrice();
                    initRecyclerView();
                } else if (error == 2) {
                    loginToServer(SearchOrderActivity.class);
                } else {
                    ToastManager.showToastReal(OKHttpRequestUtils.getmInstance().getErrorMsg(result));
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }
        });
    }

    private void initRecyclerView() {
        if(getGoldenPriceRecyclerViewAdapter==null){
            rvGlodenPrice.addItemDecoration(new SpaceItemDecoration(UIUtils.dip2px(4)));
            rvGlodenPrice.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvGlodenPrice.setLayoutManager(linearLayoutManager);
        getGoldenPriceRecyclerViewAdapter = new GetGoldenPriceRecyclerViewAdapter(purityGoldPrice, this, new RecycleViewPartAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        rvGlodenPrice.setAdapter(getGoldenPriceRecyclerViewAdapter);


    }

    public void initData() {
        Date date = null;
        Calendar calendar = Calendar.getInstance();

        date = dateFromString(tvStartDate.getText().toString());

        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                String s = getDate(mYear, mMonth, mDay);
                getCurrentDateGoldenPrices(s);
            }
        }, year, month, day);
        datePickerDialog.show();


    }

    private void getCurrentDateGoldenPrices(String s) {
        tvStartDate.setText(s);
        sb.delete(0, sb.length());
        sb.append("&sdate=" + s);
        loadNetData();
    }

    /**
     * 将yyyy-MM-dd string转化为date
     *
     * @param st
     * @return
     */
    public Date dateFromString(String st) {
        Date date = null;
        // 设置传入的时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(st);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获得格式2015-09-01类似的String
     *
     * @param mYear
     * @param mMonth
     * @param mDay
     * @return
     */
    public String getDate(int mYear, int mMonth, int mDay) {
        StringBuilder sb = new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay);
        String st = sb.toString();
        return st;
    }


    @OnClick({R.id.rl_start_date, R.id.rv_gloden_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_start_date:
                initData();
                break;
            case R.id.rv_gloden_price:
                break;
        }
    }
}
