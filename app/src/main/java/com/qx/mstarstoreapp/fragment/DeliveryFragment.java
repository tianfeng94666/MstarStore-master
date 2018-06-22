package com.qx.mstarstoreapp.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.FinishTableLessActivity;
import com.qx.mstarstoreapp.activity.SearchOrderMainActivity;
import com.qx.mstarstoreapp.adapter.FinishTableLessAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.BaseFragment;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.json.FinishTableLessResult;
import com.qx.mstarstoreapp.json.RecListBean;
import com.qx.mstarstoreapp.json.SearchOrderMainResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.SpUtils;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class DeliveryFragment extends BaseFragment {


    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.layout_rl_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.lv_sending_tables)
    ListView lvSendingTables;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv_is_show_cost_price)
    TextView tvIsShowCostPrice;
    @Bind(R.id.iv_is_show_cost_price)
    ImageView ivIsShowCostPrice;
    @Bind(R.id.rl_is_show_cost_price)
    RelativeLayout rlIsShowCostPrice;
    private String orderNumber;
    private FinishTableLessAdapter finishTableLessAdapter;
    private SearchOrderMainResult.DataBean.OrderSendedBean sendBean;
    private String isMainAccent;
    private AlertDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.activity_finish_table_less, null);
        ButterKnife.bind(this, view);
        init();
        getDate();
        return view;
    }


    private void init() {
        idRelTitle.setVisibility(View.GONE);
        isMainAccent = Global.isMainAccount;
        if("1".equals(isMainAccent)){
            rlIsShowCostPrice.setVisibility(View.VISIBLE);
        }else {
            rlIsShowCostPrice.setVisibility(View.GONE);
        }
        rlIsShowCostPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goIntoEncryptionSettings();
            }
        });
    }

    private void getDate() {
        sendBean = ((SearchOrderMainActivity) getActivity()).getOrderSendedBean();
        if (sendBean != null && sendBean.getRecList() != null) {
            finishTableLessAdapter = new FinishTableLessAdapter(getActivity(), sendBean.getRecList(), 2 + "");
            lvSendingTables.setAdapter(finishTableLessAdapter);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void goIntoEncryptionSettings() {
        final EditText editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginStart(64);
        layoutParams.setMarginEnd(64);
        editText.setLayoutParams(layoutParams);
        LinearLayout ll = new LinearLayout(getActivity());
        ll.addView(editText);
        dialog = new AlertDialog.Builder(getActivity())
                .setTitle("用户密码")
                .setView(ll)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (passwordIsRight(editText.getText().toString())) {

                            if(Global.isShowCost==0){
                                Global.isShowCost = 1;
                                ivIsShowCostPrice.setImageResource(R.drawable.icon_switch_on);
                            }else {
                                Global.isShowCost = 0;
                                ivIsShowCostPrice.setImageResource(R.drawable.icon_switch_off);
                            }
                            if(finishTableLessAdapter!=null){
                                lvSendingTables.setAdapter(finishTableLessAdapter);
                            }else {
                                loadNetData();
                            }

                        } else {
                            ToastManager.showToastReal("密码错误！");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    private boolean passwordIsRight(String string) {
        String pwd = BaseApplication.spUtils.getString(SpUtils.key_password);
        if (pwd.equals(string)) {
            return true;
        }
        return false;
    }

    public void loadNetData() {
        String url = "";
        url = AppURL.URL_CODE_FINISH + "tokenKey=" + BaseApplication.getToken() + "&orderNum=" + orderNumber;
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(getActivity(), url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    FinishTableLessResult finishTableLessResult = new Gson().fromJson(result, FinishTableLessResult.class);
                    if (finishTableLessResult.getData() != null) {
                        List<RecListBean> list = finishTableLessResult.getData().getRecList();
                        if (list != null) {
                            finishTableLessAdapter = new FinishTableLessAdapter(getActivity(), list, 2 + "");
                            lvSendingTables.setAdapter(finishTableLessAdapter);
                        }
                    }
                } else if (error.equals("2")) {
                    loginToServer(FinishTableLessActivity.class);
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
}
