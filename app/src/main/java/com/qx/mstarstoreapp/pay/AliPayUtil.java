package com.qx.mstarstoreapp.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.small.pay.AliPayUtil2;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class AliPayUtil {
    Activity mActivity = null;
    private AliPayUtil2.AliPayInfauce listener;
    private static final int SDK_PAY_FLAG = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    AliPayUtil.Result resultObj = AliPayUtil.this.new Result((String)msg.obj);
                    String resultStatus = resultObj.resultStatus;
                    if(TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AliPayUtil.this.mActivity, "支付成功",  Toast.LENGTH_SHORT).show();
                        if(AliPayUtil.this.listener != null) {
                            AliPayUtil.this.listener.onSussess();
                        }
                    } else {
                        if(TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(AliPayUtil.this.mActivity, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AliPayUtil.this.mActivity, "支付终止",  Toast.LENGTH_SHORT).show();
                        }

                        if(AliPayUtil.this.listener != null) {
                            AliPayUtil.this.listener.onError();
                        }
                    }
                default:
            }
        }
    };

    public void setListener(AliPayUtil2.AliPayInfauce listener) {
        this.listener = listener;
    }

    public AliPayUtil(Activity activity) {
        this.mActivity = activity;
    }

    public void pay(final String info) {
        Runnable payRunnable = new Runnable() {
            public void run() {
                PayTask alipay = new PayTask(AliPayUtil.this.mActivity);
                String result = alipay.pay(info,true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                AliPayUtil.this.mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public interface AliPayInfauce {
        void onSussess();

        void onError();
    }

    class Result {
        public String resultStatus;
        public String result;
        public String memo;

        public Result(String rawResult) {
            try {
                String[] e = rawResult.split(";");
                String[] var7 = e;
                int var6 = e.length;

                for(int var5 = 0; var5 < var6; ++var5) {
                    String resultParam = var7[var5];
                    if(resultParam.startsWith("resultStatus")) {
                        this.resultStatus = this.gatValue(resultParam, "resultStatus");
                    }

                    if(resultParam.startsWith("result")) {
                        this.result = this.gatValue(resultParam, "result");
                    }

                    if(resultParam.startsWith("memo")) {
                        this.memo = this.gatValue(resultParam, "memo");
                    }
                }
            } catch (Exception var8) {
                var8.printStackTrace();
            }

        }

        public String toString() {
            return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + "}";
        }

        private String gatValue(String content, String key) {
            String prefix = key + "={";
            return content.substring(content.indexOf(prefix) + prefix.length(), content.lastIndexOf("}"));
        }
    }
}
