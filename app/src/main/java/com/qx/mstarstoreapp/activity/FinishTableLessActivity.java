package com.qx.mstarstoreapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.FinishTableLessAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.FinishTableLessResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class FinishTableLessActivity extends BaseActivity {
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.lv_sending_tables)
    ListView lvSendingTables;
    private String orderNumber;
    private FinishTableLessAdapter finishTableLessAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        context =this;
        init();
        getDate();
        loadNetData();
    }

    private void init() {
        titleText.setText("结算单");
        idIgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getDate() {
        orderNumber = getIntent().getStringExtra("orderNumber");
    }

    @Override
    public void loadNetData() {
        String url = "";
        url = AppURL.URL_CODE_FINISH + "tokenKey=" + BaseApplication.getToken() + "&orderNum=" + orderNumber;
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    FinishTableLessResult finishTableLessResult = new Gson().fromJson(result,FinishTableLessResult.class);
                    List<FinishTableLessResult.DataBean.RecListBean>  list = finishTableLessResult.getData().getRecList();
                    if(list !=null){
                        finishTableLessAdapter = new FinishTableLessAdapter(context,list);
                        lvSendingTables.setAdapter(finishTableLessAdapter);
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
