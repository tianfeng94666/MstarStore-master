package com.qx.mstarstoreapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppManager;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.json.UpdataVersionResult;
import com.qx.mstarstoreapp.json.VersionResult;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.SpUtils;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.CountTimerButton;
import com.qx.mstarstoreapp.viewutils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/7.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.id_btn_register)
    Button idBtnRegister;
    @Bind(R.id.id_ed_name)
    EditText idEdName;
    @Bind(R.id.id_ed_password)
    EditText idEdPassword;
    @Bind(R.id.id_ed_code)
    EditText idEdCode;
    String name, pwd, phone, code;
    CountTimerButton mCountDownTimerUtils;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    @Bind(R.id.line_bottom)
    View lineBottom;
    private String version;
    private VersionResult versionResult;
    private UpdataVersionResult updataVersionResult;
    private int updateValue;
    private FingerprintManagerCompat manager;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        setContentView(R.layout.activity_login_1);
        ButterKnife.bind(this);
        manager = FingerprintManagerCompat.from(this);
        isNeedUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();


    }


    private void initView() {
        token = BaseApplication.spUtils.getString(SpUtils.key_tokenKey);
        if (!StringUtils.isEmpty(token)) {
            BaseApplication.setToken(token);
            if (!manager.isHardwareDetected()) {
                //是否支持指纹识别
                showToastReal("不支持指纹登入，请验证登入");
//                getBackIntent();
            } else if (!manager.hasEnrolledFingerprints()) {
                //是否已注册指纹
                showToastReal("请录入指纹在手机，方便快速登录");
//                getBackIntent();
            } else {
                try {
                    //这里去新建一个结果的回调，里面回调显示指纹验证的信息
                    manager.authenticate(null, 0, null, new MyCallBack(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            llCode.setVisibility(View.VISIBLE);
            lineBottom.setVisibility(View.VISIBLE);
            showToastReal("要验证登录");
        }

        name = BaseApplication.spUtils.getString(SpUtils.key_username);
        pwd = BaseApplication.spUtils.getString(SpUtils.key_password);
        if (!StringUtils.isEmpty(name)) {
            idEdName.setText(name);
        }
        if (!StringUtils.isEmpty(pwd)) {
            idEdPassword.setText(pwd);
        }


        idBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNetData();
            }
        });

        Button textView = (Button) findViewById(R.id.tv_get_auth_code);
        mCountDownTimerUtils = new CountTimerButton(textView, 60000, 1000);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimerUtils.start();
                getNetCode();
            }
        });


    }

    public class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {
        private static final String TAG = "MyCallBack";

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Log.d(TAG, "onAuthenticationError: " + errString);
            showToastReal(errString.toString());
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed: " + "验证失败");
            showToastReal("验证失败");
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Log.d(TAG, "onAuthenticationHelp: " + helpString);
            showToastReal(helpString.toString());
        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult
                                                      result) {
            if (!StringUtils.isEmpty(token)) {
                getBackIntent();
            } else {
                showToastReal("要验证登录");
            }

        }
    }

    /*得到没登陆前的实例*/
    public void getBackIntent() {
        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.getExtras() != null) {
            nextActivity = (Class<?>) callingIntent.getExtras().get(JUMP_TO_ACTIVITY);
        } else {
            openActivity(MainActivity.class, null);
        }
        finish();
    }

    private void isNeedUpdate() {
        String lgUrl = AppURL.URL_GET_UPDATE_VERSION + "device=" + "android" + "&version=" + getResources().getString(R.string.app_version_detail);
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    updataVersionResult = new Gson().fromJson(result, UpdataVersionResult.class);
                    if (updataVersionResult.getData() == null) {
                        return;
                    }
                    updateValue = updataVersionResult.getData().getValue();
                    if (updateValue == 1) {
                        showNoticeDialog(true, updataVersionResult.getData().getMessage());
                    } else if (updateValue == 2) {
                        showNoticeDialog(false, updataVersionResult.getData().getMessage());
                    }

                } else if (error.equals("2")) {
                    loginToServer(LoginActivity.class);
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

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog(boolean isNeed, String string) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(string);
        // 更新
        builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pgyer.com/IGab"));
                startActivity(intent);
            }
        });

        Dialog noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(isNeed);
        noticeDialog.setCancelable(isNeed);
        noticeDialog.show();
    }

    @Override
    public void loadNetData() {
        name = idEdName.getText().toString().trim();
        pwd = idEdPassword.getText().toString().trim();
        code = idEdCode.getText().toString();
        Global.REGISTRATION = ((BaseApplication) getApplication()).getRegistrationID();
        if (StringUtils.isEmpty(name)) {
            showToastReal("用户名不能为空！");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToastReal("密码不能为空！");
            return;
        }

        if (StringUtils.isEmpty(Global.REGISTRATION)) {
            showToastReal("正在注册推送信息，请稍后登录");
            return;
        }
        baseShowWatLoading();
        // 进行登录请求
        String lgUrl = AppURL.URL_LOGIN + "userName=" + name + "&password=" + pwd + "&phoneCode=" +
                code + "&jRegid=" + Global.REGISTRATION + "&system=android" + "&tokenKey=" + BaseApplication.getToken();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e("OKHttpRequestUtils" + result);
                baseHideWatLoading();
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    String token = new Gson().fromJson(result, JsonObject.class).getAsJsonObject("data").get("tokenKey").getAsString();
                    L.e("成功" + token);
                    BaseApplication.spUtils.saveString(SpUtils.key_tokenKey, token);
                    BaseApplication.spUtils.saveString(SpUtils.key_username, name);
                    BaseApplication.spUtils.saveString(SpUtils.key_password, pwd);
                    BaseApplication.setToken(token);
                    openActivity(MainActivity.class, null);
                    finish();
                    return;
                    //loginSuccess();
                }
                if (error == 1) {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();

                    if ("noVerifyCode".equals(message)) {
                        llCode.setVisibility(View.VISIBLE);
                        lineBottom.setVisibility(View.VISIBLE);
                        showToastReal("请输入验证码");
                    } else {
                        ToastManager.showToastReal(message);
                    }
                    L.e(message);
                }
                if (error == 2) {
                    L.e("重新登录");
                    // netLogin();
                }
                if (error == 3) {
                    L.e("未审核");
                }
            }

            @Override
            public void onFail(String fail) {
                showToastReal(fail);
                baseHideWatLoading();
            }


        });

    }


    public void loginSuccess() {
        if (nextActivity != null) {
            final Intent intent = new Intent(LoginActivity.this, nextActivity);
            //intent.putExtra(GET_TO, "");
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, 1000);
            return;
        }
        openActivity(MainActivity.class, null);
        finish();
//        Intent intent = new Intent();
//        intent.putExtra("fragmentid", getIntent().getIntExtra("fragmentid", -1));
//        setResult(94, intent);
        //
    }

    public void getNetCode() {
        name = idEdName.getText().toString().trim();
        pwd = idEdPassword.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            return;
        }
        baseShowWatLoading();
        // 进行登录请求
        String lgUrl = AppURL.URL_LOGING_CODE + "userName=" + name + "&password=" + pwd;
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e("OKHttpRequestUtils" + result);
                baseHideWatLoading();
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                }
                if (error == 1) {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);

                    ToastManager.showToastReal(message);
                }
                if (error == 2) {
                    L.e("重新登录");
                }
                if (error == 3) {
                    L.e("未审核");
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    public void onRegister(View v) {
        openActivity(RegisterActivity.class, null);
    }

    public void onUpdatePassword(View v) {
        openActivity(FrogetPwdActivity.class, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BaseApplication.requestQueue.cancelAll(this);
//        finish();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastManager.showToastReal("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
        }
        return true;
    }
}
