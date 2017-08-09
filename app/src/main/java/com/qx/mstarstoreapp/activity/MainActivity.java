package com.qx.mstarstoreapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.MainPicResult;
import com.qx.mstarstoreapp.json.VersionResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.BadgeView;
import com.qx.mstarstoreapp.viewutils.FlyBanner;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    //新主页
    @Bind(R.id.fly_main)
    FlyBanner flyMain;
    @Bind(R.id.iv_home)
    TextView ivHome;
    @Bind(R.id.iv_stone)
    TextView ivStone;
    @Bind(R.id.iv_product)
    TextView ivProduct;
    @Bind(R.id.iv_mine)
    TextView ivMine;

    private int nowId;
    private String version;
    private VersionResult versionResult;
    private MainPicResult mainPics;
    private List<String> imgesUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        initView();
//        setChioceFragment(0);
        loadNetData();
        isNeedUpdate();
    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String lgUrl = AppURL.URL_GET_HOME_PIC + BaseApplication.getToken();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    baseHideWatLoading();
                    mainPics = new Gson().fromJson(result, MainPicResult.class);
                    if (mainPics.getData() == null) {
                        ToastManager.showToastReal("获取数据失败！");
                        return;
                    }
                    initView();

                } else if (error.equals("2")) {
                    baseHideWatLoading();
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



    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initView();
    }

    private void isNeedUpdate() {
        String lgUrl = AppURL.URL_CODE_VERSION + "device=" + "android";
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    versionResult = new Gson().fromJson(result, VersionResult.class);
                    if (versionResult.getData() == null) {
                        ToastManager.showToastReal("获取数据失败！");
                        return;
                    }
                    version = versionResult.getData().getVersion();
                    Double versionDouble = Double.parseDouble(version);
                    Double currentDouble = Double.parseDouble(getString(R.string.app_version));
                    if (versionDouble > currentDouble) {
                        showNoticeDialog();
                    }
                } else if (error.equals("2")) {

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
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(R.string.soft_update_info);
        // 更新
        builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(versionResult.getData().getUrl()));
                startActivity(intent);
            }
        });

        Dialog noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
    }

    public int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    "com.qx.mstarstoreapp", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("tage", e.getMessage());
        }
        return verCode;
    }

    private void initView() {

        if (UIUtils.isScreenChange(this)) {
            imgesUrl = mainPics.getData().getHorizontal();
        } else {
            imgesUrl = mainPics.getData().getVertical();
        }

        flyMain.setImagesUrl(imgesUrl);
        ivHome.setOnClickListener(this);
        ivMine.setOnClickListener(this);
        ivProduct.setOnClickListener(this);
        ivStone.setOnClickListener(this);
    }

    public static BadgeView badge1;

    public static void setOnInformationCountClick(OnInformationCountClick onInformationCountClick) {
        int count = onInformationCountClick.getCount();
        if (count == 0) {
            return;
        } else {
//            remind(count, badge1, true);
        }

    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_home:
                intent.setClass(MainActivity.this,MainActivity.class);
                break;
            case R.id.iv_stone:
                intent.setClass(MainActivity.this,StoneSearchInfoActivity.class);
                break;
            case R.id.iv_product:
                intent.setClass(MainActivity.this,OrderActivity.class);
                break;
            case R.id.iv_mine:
                intent.setClass(MainActivity.this,SettingActivity.class);
                break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }

    public interface OnInformationCountClick {
        int getCount();
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
