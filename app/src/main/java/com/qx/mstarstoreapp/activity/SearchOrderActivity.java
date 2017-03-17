package com.qx.mstarstoreapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.qx.mstarstoreapp.json.CustomerEntity;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.FlowLayout;

import java.util.ArrayList;
import java.util.Calendar;

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
    @Bind(R.id.rb_my_orders)
    RadioButton rbMyOrders;
    @Bind(R.id.rb_my_group_orders)
    RadioButton rbMyGroupOrders;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlStartDate.setOnClickListener(this);
        rlEndDate.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        llSearchType.setOnClickListener(this);
        igBtnSeach.setOnClickListener(this);
        tvStartDate.setText(getCurrentDate());
        tvEndDate.setText(getCurrentDate());
        initRadioGroup();
    }

    private void initRadioGroup() {
        RadioButton rb = new RadioButton(this);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(UIUtils.dip2px(32),0,0,0);
        rb.setLayoutParams(params);
        rb.setButtonDrawable(R.drawable.selector_radio);
        rb.setText("nihao");
        rgOrders.addView(rb);
    }


    @Override
    public void loadNetData() {

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
                loadNetData();
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
                    }
                    if (state == 1) {
                        ToastManager.showToastReal("有此客户");

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
        final ArrayList<String> list = new ArrayList<>();
        list.add("订单号");
        list.add("订单号");
        list.add("订单号");
        SearchTypeAdapter simpleAdapter = new SearchTypeAdapter(list);
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
                choosetype = (String) list.get(position);
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
                if (type.equals("start")) {
                    SearchOrderActivity.this.tvStartDate.setText(getDate(mYear, mMonth, mDay));
                } else {
                    SearchOrderActivity.this.tvEndDate.setText(getDate(mYear, mMonth, mDay));
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

        }


    }

    class SearchTypeAdapter extends BaseAdapter {
        ArrayList<String> list = new ArrayList<>();

        public SearchTypeAdapter(ArrayList<String> list) {
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
            viewHolder.tvItemType.setText(list.get(position));

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
