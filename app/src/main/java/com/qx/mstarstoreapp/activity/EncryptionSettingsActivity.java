package com.qx.mstarstoreapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.json.SettingResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.SpUtils;
import com.qx.mstarstoreapp.utils.ToastManager;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class EncryptionSettingsActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.tv_is_customized)
    TextView tvIsCustomized;
    @Bind(R.id.bt_customized)
    ImageView btCustomized;
    @Bind(R.id.tv_is_show_price)
    TextView tvIsShowPrice;
    @Bind(R.id.iv_is_show_price)
    ImageView ivIsShowPrice;
    @Bind(R.id.tv_is_show_cost_price)
    TextView tvIsShowCostPrice;
    @Bind(R.id.iv_is_show_cost_price)
    ImageView ivIsShowCostPrice;
    @Bind(R.id.rl_is_show_cost_price)
    RelativeLayout rlIsShowCostPrice;
    @Bind(R.id.tv_is_product_point)
    TextView tvIsProductPoint;
    @Bind(R.id.iv_product_reduce)
    ImageView ivProductReduce;
    @Bind(R.id.et_product_spot)
    EditText etProductSpot;
    @Bind(R.id.iv_product_add)
    ImageView ivProductAdd;
    @Bind(R.id.rl_product_addtion)
    RelativeLayout rlProductAddtion;
    @Bind(R.id.tv_is_stone_point)
    TextView tvIsStonePoint;
    @Bind(R.id.iv_stone_reduce)
    ImageView ivStoneReduce;
    @Bind(R.id.et_stones_spot)
    EditText etStonesSpot;
    @Bind(R.id.iv_stone_add)
    ImageView ivStoneAdd;
    @Bind(R.id.rl_stone_addtion)
    RelativeLayout rlStoneAddtion;
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.tv_right)
    ImageView tvRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    private boolean isShowPrice = SpUtils.getInstace(this).getBoolean("isShowPrice", true);
    private boolean isCustomized = SpUtils.getInstace(this).getBoolean("isCustomized", true);
    private int isShowCostPrice ;
    private int COST_PRICE_TYPE = 1;
    private SettingResult settingResult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enctyption_settings);
        ButterKnife.bind(this);
        getDate();
        initView();
    }

    private void getDate() {
        settingResult = (SettingResult) getIntent().getSerializableExtra("settingResult");
    }


    private void initView() {
        if (isCustomized) {
            btCustomized.setImageResource(R.drawable.icon_switch_off);
        } else {
            btCustomized.setImageResource(R.drawable.icon_switch_on);
        }
        btCustomized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCustomized = !isCustomized;
                if (isCustomized) {
                    btCustomized.setImageResource(R.drawable.icon_switch_off);
                } else {
                    btCustomized.setImageResource(R.drawable.icon_switch_on);
                }
                Global.STONE_POINT_CHANGE = 1;
                SpUtils.getInstace(EncryptionSettingsActivity.this).saveBoolean("isCustomized", isCustomized);
            }
        });

        if (!isShowPrice) {
            ivIsShowPrice.setImageResource(R.drawable.icon_switch_off);
        } else {
            ivIsShowPrice.setImageResource(R.drawable.icon_switch_on);
        }
        ivIsShowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowPrice = !isShowPrice;
                if (!isShowPrice) {
                    ivIsShowPrice.setImageResource(R.drawable.icon_switch_off);
                } else {
                    ivIsShowPrice.setImageResource(R.drawable.icon_switch_on);
                }
                Global.STONE_POINT_CHANGE = 1;
                SpUtils.getInstace(EncryptionSettingsActivity.this).saveBoolean("isShowPrice", isShowPrice);
            }
        });
       isShowCostPrice= settingResult.getData().getIsShowOriginalPrice();
        if (isShowCostPrice==1) {
            ivIsShowCostPrice.setImageResource(R.drawable.icon_switch_on);
        } else {
            ivIsShowCostPrice.setImageResource(R.drawable.icon_switch_off);
        }
        ivIsShowCostPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowCostPrice==1) {
                    isShowCostPrice = 0;
                    ivIsShowCostPrice.setImageResource(R.drawable.icon_switch_off);
                } else {
                    isShowCostPrice = 1;
                    ivIsShowCostPrice.setImageResource(R.drawable.icon_switch_on);
                }
                commitIsShow(isShowCostPrice);
            }
        });
        if (settingResult.getData().getIsMasterAccount() == 1) {
            rlIsShowCostPrice.setVisibility(View.VISIBLE);
            rlProductAddtion.setVisibility(View.VISIBLE);
            rlStoneAddtion.setVisibility(View.VISIBLE);
        } else {
            rlIsShowCostPrice.setVisibility(View.GONE);
            rlProductAddtion.setVisibility(View.GONE);
            rlStoneAddtion.setVisibility(View.GONE);
        }

        etProductSpot.setText(settingResult.getData().getModelAddtion());
        etStonesSpot.setText(settingResult.getData().getStoneAddtion());

        initListener();
    }


    private void commitIsShow(int i) {
        Global.STONE_POINT_CHANGE=1;
        String url;
            url = AppURL.URL_ISHOW_COST_PRICE + "tokenKey=" + BaseApplication.getToken() + "&isShow=" + i;

        L.e("获取个人信息" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {

                L.e("loadNetData  " + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    ToastManager.showToastReal("修改成功");
                } else if (error.equals("2")) {
                    ToastManager.showToastReal("修改失败");
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
            }

            @Override
            public void onFail(String fail) {
                ToastManager.showToastReal("数据获取失败");
            }
        });

    }


    private void isCommitaddtion() {
        if (!etProductSpot.getText().toString().equals(settingResult.getData().getModelAddtion())) {
            String value = etProductSpot.getText().toString();
            commitAddtion(value, 0);
            Global.STONE_POINT_CHANGE = 1;
        }
        if (!etStonesSpot.getText().toString().equals(settingResult.getData().getStoneAddtion())) {
            String value = etStonesSpot.getText().toString();
            commitAddtion(value, 1);
            Global.STONE_POINT_CHANGE = 1;
        }
    }
    public void onBack(View view) {
        isCommitaddtion();
        finish();
    }

    private void initListener() {
        ivProductAdd.setOnClickListener(this);
        ivProductReduce.setOnClickListener(this);
        ivStoneAdd.setOnClickListener(this);
        ivStoneReduce.setOnClickListener(this);
    }

    private void commitAddtion(String value, int i) {
        String url;
        if (i == 0) {
            url = AppURL.URL_MODIFY_ADDTION + "tokenKey=" + BaseApplication.getToken() + "&value=" + value;
        } else {
            url = AppURL.URL_MODIFY_STONE_ADDTION + "tokenKey=" + BaseApplication.getToken() + "&value=" + value;
        }

        L.e("获取个人信息" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {

                L.e("loadNetData  " + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    ToastManager.showToastReal("修改成功");
                } else if (error.equals("2")) {
                    ToastManager.showToastReal("修改失败");
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
            }

            @Override
            public void onFail(String fail) {
                ToastManager.showToastReal("数据获取失败");
            }
        });
    }

    @Override
    public void onClick(View v) {
        int point = Integer.parseInt(etProductSpot.getText().toString());
        int stonePoint = Integer.parseInt(etStonesSpot.getText().toString());
        switch (v.getId()) {
            case R.id.iv_product_add:
                ++point;
                etProductSpot.setText(point + "");
                break;
            case R.id.iv_product_reduce:
                if (point > 1) {
                    --point;
                }
                etProductSpot.setText(point + "");
                break;
            case R.id.iv_stone_add:
                ++stonePoint;
                etStonesSpot.setText(stonePoint + "");
                break;
            case R.id.iv_stone_reduce:
                if (stonePoint > 1) {
                    --stonePoint;
                }
                etStonesSpot.setText(stonePoint + "");
                break;
        }
    }
}
