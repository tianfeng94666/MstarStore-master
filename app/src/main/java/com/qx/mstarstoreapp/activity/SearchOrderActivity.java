package com.qx.mstarstoreapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.bean.OrderSearchBean;
import com.qx.mstarstoreapp.json.CustomerEntity;
import com.qx.mstarstoreapp.json.SearchOrderResult;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.FlowLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class SearchOrderActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.tv_search_type)
    TextView tvSearchType;
    @Bind(R.id.et_search_key)
    EditText etSearchKey;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.iv_start_date)
    ImageView ivStartDate;
    @Bind(R.id.tv_start_date)
    TextView tvStartDate;
    @Bind(R.id.rl_start_date)
    RelativeLayout rlStartDate;
    @Bind(R.id.iv_end_date)
    ImageView ivEndDate;
    @Bind(R.id.tv_end_date)
    TextView tvEndDate;
    @Bind(R.id.rl_end_date)
    RelativeLayout rlEndDate;
    @Bind(R.id.ll_search_type)
    LinearLayout llSearchType;
    @Bind(R.id.iv_search_type)
    ImageView ivSearchType;
    @Bind(R.id.id_et_seach)
    EditText idEtSeach;
    @Bind(R.id.id_view_line)
    View idViewLine;
    @Bind(R.id.ig_btn_seach)
    ImageView igBtnSeach;
    @Bind(R.id.id_rl1)
    RelativeLayout idRl1;
    @Bind(R.id.rg_orders)
    RadioGroup rgOrders;


    private DatePicker datePicker;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private PopupWindow popupWindow;
    private String choosetype;
    CustomerEntity isDefaultCustomer;
    List<SearchOrderResult.DataBean.SearchKeywordBean> list;
    private SearchTypeAdapter simpleAdapter;
    private List<SearchOrderResult.DataBean.SearchScopeBean> searchScopeBeenlist;
    private OrderSearchBean orderSearchBean = new OrderSearchBean();//搜索数据类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_order);
        ButterKnife.bind(this);
        initView();
        loadNetData();
    }

    private void initView() {
        rlStartDate.setOnClickListener(this);
        rlEndDate.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        llSearchType.setOnClickListener(this);
        igBtnSeach.setOnClickListener(this);
        //监听radiogroup
        rgOrders.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radiobutton = (RadioButton) SearchOrderActivity.this.findViewById(group.getCheckedRadioButtonId());
                if (radiobutton != null) {
                    orderSearchBean.setSscopeid(getSscopeId(radiobutton.getText().toString()));
                }
            }
        });



        etSearchKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    goToSearchResultActivity();

                }
                return false;
            }

        });
    }

    /**
     * 跳转到搜索结果页面
     */
    private void goToSearchResultActivity() {
        orderSearchBean.setKeyword(etSearchKey.getText().toString());
        orderSearchBean.setSdate(tvStartDate.getText().toString());
        orderSearchBean.setEdate(tvEndDate.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("searchData", orderSearchBean);
        openActivity(SearchResultActivity.class, bundle);
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
     * 动态生成radiobutton控件
     * @param st
     * @param marginLeft
     * @param isChoose
     */
    private void initRadioGroup(String st, int marginLeft, boolean isChoose) {
        RadioButton rb = new RadioButton(this);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(UIUtils.dip2px(marginLeft), 0, 0, 0);
        rb.setLayoutParams(params);
        rb.setButtonDrawable(R.drawable.selector_radio);
        rb.setText(st);
        rb.setSelected(isChoose);
        rgOrders.addView(rb);
    }

    /**
     * 获取搜索界面最初信息
     */
    @Override
    public void loadNetData() {
        String url = AppURL.URL_CODE_ORDER_SEARCH + "tokenKey=" + BaseApplication.getToken();
        L.e("url" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    SearchOrderResult searchOrderResult = new Gson().fromJson(result, SearchOrderResult.class);
                    if(searchOrderResult.getData()==null){
                        return;
                    }
                    tvStartDate.setText(searchOrderResult.getData().getStartDate());
                    tvEndDate.setText(searchOrderResult.getData().getEndDate());
                    list = searchOrderResult.getData().getSearchKeyword();
                    if (list != null) {
                        tvSearchType.setText(list.get(0).getTitle());
                        orderSearchBean.setSkeyid(list.get(0).getId());
                        orderSearchBean.setKeyword(tvSearchType.getText().toString());
                    }
                    searchScopeBeenlist = searchOrderResult.getData().getSearchScope();
                    setRadioGroup();
                    orderSearchBean.setCustomerID(-1);
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
            case R.id.iv_right:
                goToSearchResultActivity();
                break;
            case R.id.ll_search_type:
                showPopWindow(view);
                break;
            case R.id.ig_btn_seach:
                boolean isFast = UIUtils.isFastDoubleClick();
                if (!isFast) {
                    seachCustom("");
                }
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
                        isDefaultCustomer.setCustomerID(-1);
                        orderSearchBean.setCustomerID(-1);
                    }
                    if (state == 1) {
                        ToastManager.showToastReal("有此客户");
                        orderSearchBean.setCustomerID(isDefaultCustomer.getCustomerID());

                    }
                    if (state == 2) {
                        Intent intent = new Intent(SearchOrderActivity.this, CustomersListActivity.class);
                        intent.putExtra("keyWord", keyWord);
                        startActivityForResult(intent, 11);
                    }
                } else if (error == 2) {
                    loginToServer(OrderActivity.class);
                } else {
                    ToastManager.showToastReal(OKHttpRequestUtils.getmInstance().getErrorMsg(result));
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    private void showPopWindow(View view) {
        getPopupWindow();
        popupWindow.showAsDropDown(view);
    }


    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /**
     * 创建popuWindow
     */
    private void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindowView = getLayoutInflater().inflate(R.layout.popupwindow_search_type, null,
                false);
        ListView listView = (ListView) popupWindowView.findViewById(R.id.lv_seach_type);
        if (list != null) {
            simpleAdapter = new SearchTypeAdapter(list);
        }
        listView.setAdapter(simpleAdapter);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindowView, UIUtils.dip2px(100), UIUtils.dip2px(28 + list.size() * 20), true);
        // 点击其他地方消失
        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosetype = list.get(position).getTitle();
                tvSearchType.setText(choosetype);
            }
        });
    }

    public void initData(final String type) {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                String s = getDate(mYear, mMonth, mDay);
                if (type.equals("start")) {
                    SearchOrderActivity.this.tvStartDate.setText(s);
                    orderSearchBean.setSdate(s);
                } else {
                    SearchOrderActivity.this.tvEndDate.setText(s);
                    orderSearchBean.setEdate(s);
                }
            }
        }, year, month, day);
        datePickerDialog.show();


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
     * 获得当前日期
     *
     * @return
     */
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return getDate(year, month, day);
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

    class SearchTypeAdapter extends BaseAdapter {
        List<SearchOrderResult.DataBean.SearchKeywordBean> list = new ArrayList<>();

        public SearchTypeAdapter(List<SearchOrderResult.DataBean.SearchKeywordBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (view == null) {
                view = View.inflate(context, R.layout.item_popupwinow_type, null);
                System.out.println("height=" + view.getMeasuredHeightAndState());
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tvItemType.setText(list.get(position).getTitle());

            return view;
        }

        class ViewHolder {
            @Bind(R.id.tv_item_type)
            TextView tvItemType;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
