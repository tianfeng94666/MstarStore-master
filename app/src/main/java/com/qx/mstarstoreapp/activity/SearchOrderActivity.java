package com.qx.mstarstoreapp.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;

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

    private DatePicker datePicker;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private PopupWindow popupWindow;

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
        tvStartDate.setText(getCurrentDate());
        tvEndDate.setText(getCurrentDate());
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
        }
    }

    private void showPopWindow(View view) {
        getPopupWindow();
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
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
     * @return
     */
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return getDate(year, month, day);
    }

}
