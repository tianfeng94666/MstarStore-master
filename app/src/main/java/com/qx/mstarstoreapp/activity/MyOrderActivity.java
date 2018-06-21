package com.qx.mstarstoreapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.jkb.slidemenu.OnSlideChangedListener;
import com.jkb.slidemenu.SlideMenuLayout;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.bean.OrderSearchBean;
import com.qx.mstarstoreapp.json.CustomerEntity;
import com.qx.mstarstoreapp.json.GetDefaultCustomerResult;
import com.qx.mstarstoreapp.json.GetOrderDivideByCustomerResult;
import com.qx.mstarstoreapp.json.GetSixMonthOrderData;
import com.qx.mstarstoreapp.json.SearchOrderResult;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.MyMarkerView;
import com.qx.mstarstoreapp.viewutils.xListView.XListView;

import java.text.ParseException;
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

public class MyOrderActivity extends BaseActivity implements OnChartValueSelectedListener, XListView.IXListViewListener, View.OnClickListener {

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
    @Bind(R.id.tv_search_type)
    TextView tvSearchType;
    @Bind(R.id.iv_search_type)
    ImageView ivSearchType;
    @Bind(R.id.ll_search_type)
    LinearLayout llSearchType;
    @Bind(R.id.et_search_key)
    EditText etSearchKey;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.id_et_seach)
    EditText idEtSeach;
    @Bind(R.id.iv_delete2)
    ImageView ivDelete2;
    @Bind(R.id.id_rl1)
    RelativeLayout idRl1;
    @Bind(R.id.iv_seach_customer)
    ImageView ivSeachCustomer;
    @Bind(R.id.ll_date_type)
    LinearLayout llDateType;
    @Bind(R.id.tv_start_date)
    TextView tvStartDate;
    @Bind(R.id.rl_start_date)
    RelativeLayout rlStartDate;
    @Bind(R.id.tv_end_date)
    TextView tvEndDate;
    @Bind(R.id.rl_end_date)
    RelativeLayout rlEndDate;
    @Bind(R.id.rg_orders)
    RadioGroup rgOrders;
    @Bind(R.id.bt_confirm)
    Button btConfirm;
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

    private DatePicker datePicker;
    private PopupWindow popupWindow;
    private String choosetype;
    CustomerEntity isDefaultCustomer;
    List<SearchOrderResult.DataBean.SearchKeywordBean> searchKeylist;
    private SearchOrderActivity.SearchTypeAdapter simpleAdapter;
    private List<SearchOrderResult.DataBean.SearchScopeBean> searchScopeBeenlist;
    private OrderSearchBean orderSearchBean = new OrderSearchBean();//搜索数据类
    private List<SearchOrderResult.DataBean.SearchDateScopeBean> searchDateScopeBeen;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        ButterKnife.bind(this);
        setMenu();
        getDate();
        loadNetData();

    }

    private void setMenu() {
        ivRight.setVisibility(View.VISIBLE);
        mainSlideMenu.addOnSlideChangedListener(new OnSlideChangedListener() {
            @Override
            public void onSlideChanged(SlideMenuLayout slideMenu, boolean isLeftSlideOpen, boolean isRightSlideOpen) {

            }
        });
    }

    private void initView() {
        List<Float> appcounts, appmycounts, counts, mycount;
        mycount = new ArrayList<>();
        counts = new ArrayList<>();
        appmycounts = new ArrayList<>();
        appcounts = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            mycount.add(Float.valueOf(orderList.get(i).getMycount()));
            counts.add(Float.valueOf(orderList.get(i).getCount()));
            appmycounts.add(Float.valueOf(orderList.get(i).getAppmycount()));
            appcounts.add(Float.valueOf(orderList.get(i).getAppcount()));
        }
        view = View.inflate(this, R.layout.barchart_layout, null);
        barchart = (BarChart) view.findViewById(R.id.barchart);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(barchart); // For bounds control
        barchart.setMarker(mv);
        setTwoBarChart(barchart, months, mycount, counts, appmycounts, appcounts, "我的月订单", "总月订单","我的app月订单", "总app月订单");

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
        //leftview
        rlStartDate.setOnClickListener(this);
        rlEndDate.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        llSearchType.setOnClickListener(this);
        ivSeachCustomer.setOnClickListener(this);
        //监听radiogroup
        rgOrders.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radiobutton = (RadioButton) MyOrderActivity.this.findViewById(group.getCheckedRadioButtonId());
                if (radiobutton != null) {
                    orderSearchBean.setSscopeid(getSscopeId(radiobutton.getText().toString()));
                }
            }
        });
 /*搜索用户时间  失去焦点*/
        idEtSeach.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!StringUtils.isEmpty(idEtSeach.getText().toString()))
                        seachCustom(idEtSeach.getText().toString());
                }
            }
        });
        etSearchKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
//                    goToSearchResultActivity();

                }
                return false;
            }

        });
        ivDelete.setOnClickListener(this);
        ivDelete2.setOnClickListener(this);
    }

    /**
     * 跳转到搜索结果页面
     */
    private void goToSearchResultActivity(GetOrderDivideByCustomerResult.DataBean.CustomerOrderlistBean.OrderListBean item) {
        orderSearchBean.setCustomerID(item.getCustomerID());
        orderSearchBean.setSdate(sDate);
        orderSearchBean.setEdate(eDate);
        orderSearchBean.setKeyword(etSearchKey.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("searchData", orderSearchBean);
        openActivity(SearchResultActivity.class, bundle);
    }

    private void getListView() {
        sDate = tvStartDate.getText().toString();
        eDate = tvEndDate.getText().toString();
        String lgUrl = AppURL.URL_GET_ORDER_BY_CUSTOMER + "tokenKey=" + BaseApplication.getToken() + "&customerID=" + orderSearchBean.getCustomerID() + "&skeyid=" + orderSearchBean.getSkeyid()
                + "&keyword=" + orderSearchBean.getKeyword() + "&sscopeid=" + orderSearchBean.getSscopeid() + "&sdate=" + sDate + "&edate=" + eDate + "&cpage=" + page + "&pageNum=30";
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
                    helper.setText(R.id.tv_order_date_tv, item.getOrderDate());
                    helper.setText(R.id.tv_total_count, "" + item.getCount());
                    helper.setText(R.id.tv_order_count, item.getQuantity() + "");
                }
            };
            UIUtils.setListViewHeightBasedOnChildren(lvOrderByCustomer);
            lvOrderByCustomer.setAdapter(adapter);
            lvOrderByCustomer.addHeaderView(view);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    public static void setTwoBarChart(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, List<Float> yAxisValue3, List<Float> yAxisValue4, String bartilte1, String bartitle2, String bartilte3, String bartitle4) {
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
        ArrayList<Float> ymins = new ArrayList();
        Float yMin1 = Collections.min(yAxisValue1);
        Float yMin2 = Collections.min(yAxisValue2);
        Float yMin3 = Collections.min(yAxisValue3);
        Float yMin4 = Collections.min(yAxisValue4);
        ymins.add(yMin1);
        ymins.add(yMin2);
        ymins.add(yMin3);
        ymins.add(yMin4);
        ArrayList<Float> ymaxs = new ArrayList();
        Float yMax1 = Collections.max(yAxisValue1);
        Float yMax2 = Collections.max(yAxisValue2);
        Float yMax3 = Collections.max(yAxisValue3);
        Float yMax4 = Collections.max(yAxisValue4);
        ymaxs.add(yMax1);
        ymaxs.add(yMax2);
        ymaxs.add(yMax3);
        ymaxs.add(yMax4);
        Float yMin = Double.valueOf(Collections.min(ymins) * 0.1).floatValue();
        Float yMax = Double.valueOf(Collections.max(ymaxs) * 1.1).floatValue();
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
        setTwoBarChartData(barChart, xAxisValue, yAxisValue1, yAxisValue2,yAxisValue3, yAxisValue4, bartilte1, bartitle2, bartilte3, bartitle4);

        barChart.animateXY(3000, 3000);
        barChart.invalidate();
    }

    /**
     * 设置柱状图数据源
     */
    private static void setTwoBarChartData(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2,List<Float> yAxisValue3, List<Float> yAxisValue4, String bartilte1, String bartitle2,String bartilte3, String bartitle4) {
        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f;
        int groupCount = 6;
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        ArrayList<BarEntry> entries3 = new ArrayList<>();
        ArrayList<BarEntry> entries4 = new ArrayList<>();
        for (int i = 0, n = yAxisValue1.size(); i < n; ++i) {
            entries1.add(new BarEntry(i, yAxisValue1.get(i)));
            entries2.add(new BarEntry(i, yAxisValue2.get(i)));
            entries3.add(new BarEntry(i, yAxisValue3.get(i)));
            entries4.add(new BarEntry(i, yAxisValue4.get(i)));
        }

        BarDataSet dataset1, dataset2,dataset3, dataset4;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            dataset1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            dataset2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            dataset3 = (BarDataSet) barChart.getData().getDataSetByIndex(2);
            dataset4 = (BarDataSet) barChart.getData().getDataSetByIndex(3);
            dataset1.setValues(entries1);
            dataset2.setValues(entries2);
            dataset3.setValues(entries3);
            dataset4.setValues(entries4);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            dataset1 = new BarDataSet(entries1, bartilte1);
            dataset2 = new BarDataSet(entries2, bartitle2);
            dataset3 = new BarDataSet(entries3, bartilte3);
            dataset4 = new BarDataSet(entries4, bartitle4);
            dataset1.setColor(Color.rgb(60, 216, 200));
            dataset2.setColor(Color.rgb(120, 194, 202));
            dataset3.setColor(Color.rgb(180, 216, 200));
            dataset4.setColor(Color.rgb(240, 194, 202));
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataset1);
            dataSets.add(dataset2);
            dataSets.add(dataset3);
            dataSets.add(dataset4);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(8f);
            data.setBarWidth(1f);
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

    /**
     * 判断时间是否正确
     *
     * @param sDate
     * @param eDate
     * @return
     */
    private boolean dateIsRight(String sDate, String eDate) {
        Date startDate = dateFromString(sDate);
        Date endDate = dateFromString(eDate);
        if (endDate.before(startDate)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获得搜索范围id
     *
     * @param s
     * @return
     */
    private int getSscopeId(String s) {
        if (searchScopeBeenlist != null) {
            for (int i = 0; i < searchScopeBeenlist.size(); i++) {
                if (searchScopeBeenlist.get(i).getTitle().equals(s)) {
                    return searchScopeBeenlist.get(i).getId();
                }
            }
        }
        return searchScopeBeenlist.get(0).getId();
    }


    /**
     * 获取搜索界面最初信息
     */

    public void getDate() {
        baseShowWatLoading();
        String url = AppURL.URL_CODE_ORDER_SEARCH + "tokenKey=" + BaseApplication.getToken();
        L.e("url" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    SearchOrderResult searchOrderResult = new Gson().fromJson(result, SearchOrderResult.class);
                    if (searchOrderResult.getData() == null) {
                        return;
                    }
                    searchDateScopeBeen = searchOrderResult.getData().getSearchDateScope();
                    tvStartDate.setText(searchOrderResult.getData().getStartDate());
                    tvEndDate.setText(searchOrderResult.getData().getEndDate());
                    searchKeylist = searchOrderResult.getData().getSearchKeyword();
                    if (searchKeylist != null) {
                        tvSearchType.setText(searchKeylist.get(0).getTitle());
                        orderSearchBean.setSkeyid(searchKeylist.get(0).getId());
                        orderSearchBean.setKeyword(etSearchKey.getText().toString());
                    }
                    searchScopeBeenlist = searchOrderResult.getData().getSearchScope();
                    setRadioGroup();
                    setLlDateType();
                    orderSearchBean.setCustomerID(-1);
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

    private void setLlDateType() {
        if (searchDateScopeBeen == null) {
            return;
        }
        for (int i = 0; i < searchDateScopeBeen.size(); i++) {
            initTextView(searchDateScopeBeen.get(i), i);
        }
    }

    private void initTextView(final SearchOrderResult.DataBean.SearchDateScopeBean searchDateScopeBean, final int i) {
        final TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(UIUtils.dip2px(8), 0, 0, 0);
        params.gravity = Gravity.CENTER_VERTICAL;
        tv.setLayoutParams(params);
        int padding1 = UIUtils.dip2px(5);
        int padding2 = UIUtils.dip2px(10);
        tv.setPadding(padding2, padding1, padding2, padding1);
        tv.setTextSize(12);
        tv.setText(searchDateScopeBean.getTitle());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEndDate.setText(searchDateScopeBean.getEdate());
                tvStartDate.setText(searchDateScopeBean.getSdate());
                changeIsDefault(i, 1);
                clearLlDateType();
            }
        });
        if (searchDateScopeBean.getIsDefault() == 1) {
            tv.setTextColor(getResources().getColor(R.color.theme_red));
            tv.setBackgroundResource(R.drawable.board_red);
        } else {
            tv.setTextColor(getResources().getColor(R.color.text_color3));
            tv.setBackgroundResource(R.drawable.board_gray);
        }
        llDateType.addView(tv);

    }

    private void clearLlDateType() {
        llDateType.removeAllViews();
        setLlDateType();
    }

    private void changeIsDefault(int j, int type) {

        for (int i = 0; i < searchDateScopeBeen.size(); i++) {
            if (i == j) {
                searchDateScopeBeen.get(i).setIsDefault(type);
            } else {
                searchDateScopeBeen.get(i).setIsDefault(0);
            }
        }
    }

    /**
     * 动态生成radiobutton控件
     *
     * @param st
     * @param marginLeft
     * @param isChoose
     */
    private void initRadioGroup(String st, int marginLeft, boolean isChoose) {
        RadioButton rb = new RadioButton(this);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(UIUtils.dip2px(marginLeft), 0, 0, 0);
        params.gravity = Gravity.CENTER_VERTICAL;
        rb.setLayoutParams(params);
        rb.setButtonDrawable(R.drawable.selector_radio);
        rb.setText(st);
        rb.setSelected(isChoose);
        rgOrders.addView(rb);
    }

    /**
     * 设置RadioGroup，动态添加RadioButton和默认第一个选中
     */
    private void setRadioGroup() {
        if (searchScopeBeenlist != null) {
            for (int i = 0; i < searchScopeBeenlist.size(); i++) {
                if (i == 0) {
                    initRadioGroup(searchScopeBeenlist.get(i).getTitle(), 0, true);
                } else {
                    initRadioGroup(searchScopeBeenlist.get(i).getTitle(), 32, false);
                }

            }
            RadioButton radioButton = (RadioButton) rgOrders.getChildAt(0);
            radioButton.setChecked(true);
            orderSearchBean.setSscopeid(searchScopeBeenlist.get(0).getId());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_start_date:
                initData("start");
                break;
            case R.id.rl_end_date:
                initData("end");
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.bt_confirm:
                mainSlideMenu.toggleRightSlide();
                getListView();
                break;

            case R.id.iv_seach_customer:
                boolean isFast = UIUtils.isFastDoubleClick();
                if (!isFast) {
                    seachCustom("");
                }
                break;
            case R.id.iv_delete:
                etSearchKey.setText("");
                break;
            case R.id.iv_delete2:
                idEtSeach.setText("");
                orderSearchBean.setCustomerID(-1);
                break;
            case R.id.iv_right:
                mainSlideMenu.toggleRightSlide();
                break;
        }
    }

    /*
        * @version  搜索时候有客户
        */
    private void seachCustom(final String keyWord) {
        String url = AppURL.URL_HAVE_CUSTOMER + "tokenKey=" + BaseApplication.getToken() + "&keyword=" + keyWord;
        //keyword=广西|平果&tokenKey=944df2f27ffce557042887589986c193
        L.e("seachCustom" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                    JsonObject jsonObject = jsonResult.get("data").getAsJsonObject();
                    int state = jsonObject.get("state").getAsInt();
                    if (state == 0) {
                        ToastManager.showToastReal("没有此客户");
                        if (isDefaultCustomer == null) {
                            isDefaultCustomer = new CustomerEntity();
                        }
                        isDefaultCustomer.setCustomerID(-1);
                        orderSearchBean.setCustomerID(-1);
                    }
                    if (state == 1) {
                        ToastManager.showToastReal("只有一个客户");
                        GetDefaultCustomerResult getDefaultCustomerResult = new Gson().fromJson(result, GetDefaultCustomerResult.class);
                        orderSearchBean.setCustomerID(getDefaultCustomerResult.getData().getCustomer().getCustomerID());
                        idEtSeach.setText(getDefaultCustomerResult.getData().getCustomer().getCustomerName());

                    }
                    if (state == 2) {
                        Intent intent = new Intent(MyOrderActivity.this, CustomersListActivity.class);
                        intent.putExtra("keyWord", keyWord);
                        startActivityForResult(intent, 11);
                    }
                } else if (error == 2) {
                    loginToServer(SearchOrderActivity.class);
                } else {
                    ToastManager.showToastReal(OKHttpRequestUtils.getmInstance().getErrorMsg(result));
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
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
     * 根据类型设置对应的textview
     *
     * @param type
     */
    public void initData(final String type) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        if (type.equals("start")) {
            date = dateFromString(tvStartDate.getText().toString());
        } else {
            date = dateFromString(tvEndDate.getText().toString());
        }
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                String s = getDate(mYear, mMonth, mDay);
                if (type.equals("start")) {
                    tvStartDate.setText(s);
                    orderSearchBean.setSdate(s);
                } else {
                    tvEndDate.setText(s);
                    orderSearchBean.setEdate(s);
                }
            }
        }, year, month, day);
        datePickerDialog.show();


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e("onActivityResult" + requestCode);
        if (data == null) {
            return;
        }
        if (requestCode == 11) {
            String customerName = data.getStringExtra("CustomerName");
            int customerID = data.getIntExtra("CustomerID", -1);
            idEtSeach.setText(customerName);
            if (isDefaultCustomer == null) {
                isDefaultCustomer = new CustomerEntity();
            }
            isDefaultCustomer.setCustomerID(customerID);
            orderSearchBean.setCustomerID(customerID);
        }


    }
}
