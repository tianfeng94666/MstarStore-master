package com.qx.mstarstoreapp.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jkb.slidemenu.SlideMenuLayout;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.bean.OrderSearchBean;
import com.qx.mstarstoreapp.json.GetOrderDivideByCustomerResult;
import com.qx.mstarstoreapp.json.GetSixMonthOrderData;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.xListView.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class MyOrderActivity extends BaseActivity implements OnChartValueSelectedListener, XListView.IXListViewListener {

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
    @Bind(R.id.lv_order_by_customer)
    XListView lvOrderByCustomer;
    @Bind(R.id.mainSlideMenu)
    SlideMenuLayout mainSlideMenu;
    private GetSixMonthOrderData getSixMonthOrderData;
    private List<GetSixMonthOrderData.DataBean.DateTableCountBean> orderList;
    ArrayList<String> months;
    private Typeface mTfRegular;
    private Typeface mTfLight;
    private GetOrderDivideByCustomerResult getOrderDivideByCustuomer;
    private int page = 1;
    List<GetOrderDivideByCustomerResult.DataBean.CustomerOrderlistBean.OrderListBean> list = new ArrayList<>();
    private int totalCount;
    private CommonAdapter<GetOrderDivideByCustomerResult.DataBean.CustomerOrderlistBean.OrderListBean> adapter;
    private View view;
    private BarChart barchart;
    private String sDate, eDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        ButterKnife.bind(this);
        setMenu();
        loadNetData();
    }

    private void setMenu() {
        ivRight.setVisibility(View.VISIBLE);
    }

    private void initView() {
        List<Float> values1, values2;
        values1 = new ArrayList<>();
        values2 = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            values1.add(Float.valueOf(orderList.get(i).getMycount()));
            values2.add(Float.valueOf(orderList.get(i).getCount()));
        }
        view = View.inflate(this, R.layout.barchart_layout, null);
        barchart = (BarChart) view.findViewById(R.id.barchart);
        setTwoBarChart(barchart, months, values1, values2, "我的月订单量", "总月订单量");

        lvOrderByCustomer.setXListViewListener(this);
        lvOrderByCustomer.setAutoLoadEnable(false);
        lvOrderByCustomer.setPullRefreshEnable(true);
        lvOrderByCustomer.setPullLoadEnable(true);
        lvOrderByCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //xlistview自己的头部，还有我加的头部
                goToSearchResultActivity(list.get(position - 2));
            }
        });
        getListView();
    }

    /**
     * 跳转到搜索结果页面
     */
    private void goToSearchResultActivity(GetOrderDivideByCustomerResult.DataBean.CustomerOrderlistBean.OrderListBean item) {
        OrderSearchBean orderSearchBean = new OrderSearchBean();
        orderSearchBean.setCustomerID(item.getCustomerID());
        orderSearchBean.setSdate(sDate);
        orderSearchBean.setEdate(eDate);
        orderSearchBean.setSscopeid(1);
        orderSearchBean.setKeyword("");
        Bundle bundle = new Bundle();
        bundle.putSerializable("searchData", orderSearchBean);
        openActivity(SearchResultActivity.class, bundle);
    }

    private void getListView() {
        sDate = "2018-01-01";
        eDate = "2018-05-11";
        String lgUrl = AppURL.URL_GET_ORDER_BY_CUSTOMER + "tokenKey=" + BaseApplication.getToken() + "&scope=1" + "&sdate=" + sDate + "&edate=" + eDate + "&cpage=" + page + "&pageNum=30";
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {

                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    getOrderDivideByCustuomer = new Gson().fromJson(result, GetOrderDivideByCustomerResult.class);
                    if (getOrderDivideByCustuomer.getData() == null) {
                        return;
                    }
                    setListView();
                } else if (error.equals("2")) {
                    baseHideWatLoading();
                    loginToServer(MyOrderActivity.class);
                } else {
                    baseHideWatLoading();
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

    private void setListView() {
        List<GetOrderDivideByCustomerResult.DataBean.CustomerOrderlistBean.OrderListBean> tempList = getOrderDivideByCustuomer.getData().getCustomerOrderlist().getOrderList();
        totalCount = getOrderDivideByCustuomer.getData().getCustomerOrderlist().getList_count();
        list.addAll(tempList);
        if (adapter == null) {
            adapter = new CommonAdapter<GetOrderDivideByCustomerResult.DataBean.CustomerOrderlistBean.OrderListBean>(list, R.layout.item_order_divide_by_customer) {


                @Override
                public void convert(int position, BaseViewHolder helper, GetOrderDivideByCustomerResult.DataBean.CustomerOrderlistBean.OrderListBean item) {
                    helper.setText(R.id.tv_order_customer, item.getCustomerName());
                    helper.setText(R.id.tv_order_date_tv, "下单时间：" + item.getOrderDate());
                    helper.setText(R.id.tv_customer_name, item.getCount() + "");
                }
            };
            UIUtils.setListViewHeightBasedOnChildren(lvOrderByCustomer);
            lvOrderByCustomer.setAdapter(adapter);
            lvOrderByCustomer.addHeaderView(view);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    public static void setTwoBarChart(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2) {
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        barChart.setExtraBottomOffset(10);
        barChart.setExtraTopOffset(30);

        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xAxisValue.size());
        xAxis.setCenterAxisLabels(true);//设置标签居中
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));

        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawAxisLine(true);

        //设置坐标轴最大最小值
        Float yMin1 = Collections.min(yAxisValue1);
        Float yMin2 = Collections.min(yAxisValue2);
        Float yMax1 = Collections.max(yAxisValue1);
        Float yMax2 = Collections.max(yAxisValue2);
        Float yMin = Double.valueOf((yMin1 < yMin2 ? yMin1 : yMin2) * 0.1).floatValue();
        Float yMax = Double.valueOf((yMax1 > yMax2 ? yMax1 : yMax2) * 1.1).floatValue();
        leftAxis.setAxisMaximum(yMax);
        leftAxis.setAxisMinimum(yMin);

        barChart.getAxisRight().setEnabled(false);


        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);

        //设置柱状图数据
        setTwoBarChartData(barChart, xAxisValue, yAxisValue1, yAxisValue2, bartilte1, bartitle2);

        barChart.animateXY(3000, 3000);
        barChart.invalidate();
    }

    /**
     * 设置柱状图数据源
     */
    private static void setTwoBarChartData(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2) {
        float groupSpace = 0.04f;
        float barSpace = 0.03f;
        float barWidth = 0.45f;
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int i = 0, n = yAxisValue1.size(); i < n; ++i) {
            entries1.add(new BarEntry(i, yAxisValue1.get(i)));
            entries2.add(new BarEntry(i, yAxisValue2.get(i)));
        }

        BarDataSet dataset1, dataset2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            dataset1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            dataset2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            dataset1.setValues(entries1);
            dataset2.setValues(entries2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            dataset1 = new BarDataSet(entries1, bartilte1);
            dataset2 = new BarDataSet(entries2, bartitle2);

            dataset1.setColor(Color.rgb(129, 216, 200));
            dataset2.setColor(Color.rgb(181, 194, 202));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataset1);
            dataSets.add(dataset2);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return value + "";
                }
            });

            barChart.setData(data);
        }

        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + 0);
        barChart.groupBars(0, groupSpace, barSpace);

    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String lgUrl = AppURL.URL_GET_ORDERDATE_SCALECOUNT + "tokenKey=" + BaseApplication.getToken() + "&date=" + getSixMonthDate();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    getSixMonthOrderData = new Gson().fromJson(result, GetSixMonthOrderData.class);
                    if (getSixMonthOrderData.getData() == null) {
                        return;
                    }
                    orderList = getSixMonthOrderData.getData().getDateTableCount();
                    initView();
                } else if (error.equals("2")) {
                    baseHideWatLoading();
                    loginToServer(MyOrderActivity.class);
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

    private String getSixMonthDate() {
        months = new ArrayList();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuilder stringBuilder = new StringBuilder();
        calendar.add(Calendar.MONTH, -6);
        for (int i = 6; i > 0; --i) {
            calendar.add(Calendar.MONTH, 1);
            String lastDate = simpleDateFormat.format(calendar.getTime());
            months.add(lastDate);
            if (i == 1) {
                stringBuilder.append(lastDate + "-01," + simpleDateFormat2.format(lastDayOfMonth2(calendar)));
            } else {
                stringBuilder.append(lastDate + "-01," + simpleDateFormat2.format(lastDayOfMonth2(calendar)) + "|");
            }
        }
        L.e(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public Date lastDayOfMonth2(Calendar calendar) {
        // 返回指定日历字段可能捅有的最大值
        int value = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, value);
        return calendar.getTime();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }

    @Override
    public void onRefresh() {
        if (list != null) {
            page = 1;
            list.clear();

            getListView();
            lvOrderByCustomer.stopRefresh();
        }


    }

    @Override
    public void onLoadMore() {
        if (list != null) {
            if (list.size() > totalCount) {
                page++;

                getListView();
            } else {
                ToastManager.showToastReal("没有更多数据");
                lvOrderByCustomer.stopLoadMore();
                lvOrderByCustomer.stopRefresh();
            }
        }

    }

    @OnClick({R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                mainSlideMenu.toggleRightSlide();
                break;

        }
    }
}
