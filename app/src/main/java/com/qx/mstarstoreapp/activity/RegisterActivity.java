package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.CountTimerButton;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/7.
 */
public class RegisterActivity extends BaseActivity {


    Button tv_get_auth_code;
    CountTimerButton mCountDownTimerUtils;
    @Bind(R.id.id_ed_name)
    EditText idEdName;
    @Bind(R.id.id_ed_ername)
    EditText idEdErname;
    @Bind(R.id.id_ed_phone)
    EditText idEdPhone;
    @Bind(R.id.id_ed_password)
    EditText idEdPassword;
    @Bind(R.id.id_ed_repassword)
    EditText idEdRepassword;
    @Bind(R.id.id_ed_code)
    EditText idEdCode;
    @Bind(R.id.tv_get_auth_code)
    Button tvGetAuthCode;

    @Bind(R.id.title_text)
    TextView titleText;
    String userName,truName,phone,pwd,rePwd,code,userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);
        ButterKnife.bind(this);

        titleText.setText("用户注册");
        tv_get_auth_code = (Button) findViewById(R.id.tv_get_auth_code);
        mCountDownTimerUtils = new CountTimerButton(tv_get_auth_code, 60000, 1000);

        tv_get_auth_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimerUtils.start();
                getNetCode();
            }
        });
    }

    @Override
    public void loadNetData() {

    }

    public void getNetCode(){
        phone=idEdPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToastManager.showToastReal("请填写手机号码");
            return;
        }
        baseShowWatLoading();
        // 进行登录请求
        String lgUrl = AppURL.URL_REGISTER_CODE + "phone=" + phone;
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
                    mCountDownTimerUtils.onFinish();
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



    public void netRegister(View view){
        userName=idEdName.getText().toString();
        truName=idEdErname.getText().toString();
        phone=idEdPhone.getText().toString();
        pwd=idEdPassword.getText().toString();
        rePwd=idEdRepassword.getText().toString();
        code=idEdCode.getText().toString();
        if (StringUtils.isEmpty(userName)){
            return;
        }
        if (StringUtils.isEmpty(truName)){
            return;
        }
        if (StringUtils.isEmpty(phone)){
            return;
        }
        if (StringUtils.isEmpty(pwd)){
            return;
        }
        if (StringUtils.isEmpty(rePwd)){
            return;
        }
        if (StringUtils.isEmpty(code)){
            return;
        }
        String url=AppURL.URL_REGISTER+"userName="+userName+"&password="+pwd+"&trueName="+truName+"&phone="+phone+"&phoneCode="+code+"&userType=1";
        L.e(url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack(){
            @Override
            public void onSuccess(String result) {
                L.e(result);
                Gson gson=new Gson();
                int error=gson.fromJson(result, JsonObject.class).get("error").getAsInt();
                L.e("error"+error);
                if (error==0){
                    L.e("成功");
                    openActivity(MainActivity.class, null);
                }if(error==1) {
                    String message=gson.fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                }if (error==2){
                    L.e("重新登录");
                }if(error==3){
                    L.e("未审核");
                }else{
                    L.e("操作失败");
                }
            }

            @Override
            public void onFail(String fail) {

            }

        });


    }

    public void onLogin(View v) {
        openActivity(LoginActivity.class, null);
    }


}
