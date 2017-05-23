package com.qx.mstarstoreapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.alipay.Alipay;
import com.qx.mstarstoreapp.alipay.Keys;
import com.qx.mstarstoreapp.alipay.PayResult;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.ALipayResult;
import com.qx.mstarstoreapp.json.ComitOrderResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * 创建人：Yangshao
 * 创建时间：2016/10/17 9:45
 * @version    
 *    
 */
public class ModeOfPaymentActivity extends BaseActivity {


    @Bind(R.id.id_lv_pay)
    ListView idLvPay;
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.id_lay1)
    LinearLayout idLay1;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    private int[] payDrawables;//支付方式图标
    private String[] payNames;//支付方式 名称

    SelectDialog selectDialog;
    int choosePayWay = 0;
    private Double totalPrice;
    private String proName;
    private String proDesc;
    private String out_trade_no;
    String id;
    private ComitOrderResult comitOrderResult;
    private ALipayResult aLipayResult;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mode_pay);
        ButterKnife.bind(this);
        getDate();
        loadNetData();
        // selectDialog=new SelectDialog(this);
        // selectDialog.show();


    }

    private void getDate() {
//        out_trade_no = getIntent().getStringExtra("out_trade_no");
//        totalPrice = Double.parseDouble(getIntent().getStringExtra("totalPrice"));
//        proName = getIntent().getStringExtra("pr
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0);
    }

    private void initView() {
        titleText.setText("选择支付");
        payDrawables = new int[]{R.drawable.icon_alipay, R.drawable.icon_wechat_pay};
        payNames = new String[]{"支付宝", "微信"};
        SimpleAdapter adapter = new SimpleAdapter(this, getListViewData(),
                R.layout.item_pay_mode, new String[]{"img", "info"},
                new int[]{R.id.image, R.id.info});
        idLvPay.setAdapter(adapter);
        idLvPay.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //默认选择第一行
        idLvPay.setItemChecked(0, true);
        idLvPay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosePayWay = position;
            }
        });
        idIgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        tvMoney.setText(comitOrderResult.getData().getNeedPayPrice());
    }

    public void onBack() {
        finish();
    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String url = "";
        if (type == 0) {
            url = AppURL.URL_PAY_CURRENT_ORDER + "tokenKey=" + BaseApplication.getToken() + "&orderId=" + id;
        } else {
            url = AppURL.URL_PAY_CURRENT_STONE_ORDER + "tokenKey=" + BaseApplication.getToken() + "&orderId=" + id;
        }

        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                baseHideWatLoading();
                L.e("result" + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    comitOrderResult = new Gson().fromJson(result, ComitOrderResult.class);
                    if (comitOrderResult.getData() == null) {
                        return;
                    }
                    initView();
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
                baseHideWatLoading();
            }

        });

    }

    private List<Map<String, Object>> getListViewData() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < payDrawables.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", payDrawables[i]);
            map.put("info", payNames[i]);
            list.add(map);
        }
        return list;
    }


    public class FilterFlowDialog extends PopupWindow {
        private View mPopView;
        ListView listView;

        public FilterFlowDialog(Context context) {
            super(context);
            init(context);
        }

        //new Screen(mctontext)).getHeight() - getStatusBarHeight()
        private void init(Context context) {
            mPopView = LayoutInflater.from(context).inflate(R.layout.dialog_pay, null);
            listView = (ListView) mPopView.findViewById(R.id.list);
            this.setContentView(mPopView);
            int width = getResources().getDimensionPixelOffset(R.dimen.m150);
            int height = getResources().getDimensionPixelOffset(R.dimen.m100);
            this.setWidth(width);
            this.setHeight(height);
            // 设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            // 点击外面的控件也可以使得PopUpWindow dimiss
            this.setOutsideTouchable(false);
            // 设置SelectPicPopupWindow弹出窗体动画效果
            ColorDrawable dw = new ColorDrawable(0xffffffff);//0xb0000000
            // this.setAnimationStyle(R.style.loading_dialog);
            // ColorDrawable dw = new ColorDrawable(0x00000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            // this.setBackgroundDrawable(dw);//半透明颜色
        }


    }


    public class SelectDialog extends AlertDialog {

        public SelectDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_pay);
        }


    }


    public void onPay(View view) {
//       FilterFlowDialog filedialog = new FilterFlowDialog(this);
//       // filedialog.showAtLocation(idLay1, Gravity.CENTER,0,0);
//
//        ConfirmPwdDialog updateDialog=new ConfirmPwdDialog(this,"","");
//        updateDialog.setConfirmListener(new OnResultListener<String>() {
//            @Override
//            public void onResult(boolean isSecceed, String obj) {
//                if (isSecceed){
//
//                }
//            }
//        });
//        updateDialog.show(getFragmentManager(), "loginDialog");
        // updateDialog.
        //  updateDialog.onCreateDialog();
        switch (choosePayWay) {
            case 0:
                ToastManager.showToastReal("支付宝支付");
                topay();

                break;
            case 1:
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(ModeOfPaymentActivity.this, null);
                // 将该app注册到微信
                msgApi.registerApp("wxeb71d9304d351f4c");
                ToastManager.showToastReal("微信支付");
                topay();
                break;

        }
    }

    private void aliPay() {
//        ComitOrderResult.DataBean.AilpayBean ailpayBean = comitOrderResult.getData().getAilpay();
//        totalPrice = Double.parseDouble(ailpayBean.getTotal_fee());
//        totalPrice = 0.01;
//        proName = ailpayBean.getProName();
//        proDesc = ailpayBean.getProbody();
//        out_trade_no = ailpayBean.getOut_trade_no();
        new Alipay(ModeOfPaymentActivity.this, mHandler, aLipayResult.getData()).pay();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Keys.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent;
                        intent = new Intent(ModeOfPaymentActivity.this, PaySuccessActivity.class);
                        if (!id.equals("")) {
                            intent.putExtra("id", id);
                        }
                        startActivity(intent);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
//                            ToastManager.showToastReal("支付成功");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastManager.showToastReal("支付失败");
                        }
                    }
                    break;
                }
                case Keys.SDK_CHECK_FLAG: {

                    break;
                }
                default:
                    break;
            }
        }
    };

    private void topay() {
        baseShowWatLoading();
        String url = "";
        url = AppURL.URL_GETAILPAY + "tokenKey=" + BaseApplication.getToken() + "&orderId=" + id;
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                baseHideWatLoading();
                L.e("result" + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
//                    comitOrderResult = new Gson().fromJson(result, ComitOrderResult.class);
//                    if (comitOrderResult.getData() == null) {
//                        return;
//                    }
                    aLipayResult = new Gson().fromJson(result, ALipayResult.class);
                    aliPay();
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
                baseHideWatLoading();
            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
