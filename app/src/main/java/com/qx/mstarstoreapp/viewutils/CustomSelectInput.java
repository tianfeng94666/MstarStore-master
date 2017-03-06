package com.qx.mstarstoreapp.viewutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CustomSelectInput extends RelativeLayout {

    private String text;
    private String mValue;
    private Button tv;
    private Context mContext;
    String textName;
    float textSize;

    public Button getTv() {
        return tv;
    }

    public void setTv(Button tv) {
        this.tv = tv;
    }

    public void setTextName(String textName) {
        if (!StringUtils.isEmpty(textName)) {
            this.tv.setText(textName);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setBackgroundResource(R.drawable.btn_bg_while);
        }
    }


    public void setDefaultText(String textName) {
        if (!StringUtils.isEmpty(textName)) {
            this.tv.setText(textName);
        }
    }


    public String getTextName() {
        return this.tv.getText().toString();
    }

    public CustomSelectInput(Context context) {
        this(context, null);
    }

    public CustomSelectInput(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSelectInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSelectButton);
        try {
            textName = typedArray.getString(R.styleable.CustomSelectButton_tv_name);
            //  textSize=typedArray.getDimension(R.styleable.CustomSelectButton_tv_size,8);
            // textColor = typedArray.getColor(R.styleable.CustomSelectButton_tv_color,0);
        } finally {
            typedArray.recycle();
        }
        View rootView = View.inflate(context, R.layout.custom_select_button, this);
        tv = (Button) rootView.findViewById(R.id.id_cus_tv);
        if (!StringUtils.isEmpty(textName)) {
            tv.setText(textName);
        }
        // tv.setTextSize(textSize);
        tv.setOnClickListener(new RadioClickListener());
    }

    public class RadioClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (onSelectData != null) {
                showDialog();
            }

        }
    }

    AlertDialog dialog;

    public void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_input, null);
        final EditText editText = (EditText) view.findViewById(R.id.specTitle);
        TextView titleView = (TextView) view.findViewById(R.id.titleName);
        Button button = (Button) view.findViewById(R.id.id_cancle);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mValue = editText.getText().toString();
                if (!StringUtils.isEmpty(mValue)) {

                    checkSpeci(mValue);

                }

            }
        });
        if (onSelectData != null) {
            titleView.setText(onSelectData.getTitle());
            titleView.setTextColor(getResources().getColor(R.color.black));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //创建一个自定义View
        builder.setView(view);
        //创建一个AlertDialog对象
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        //设置点击Dialog外部任意区域关闭Dialog，false为不会关闭
        dialog.show();
    }


    public void checkSpeci(String key) {
        String url = AppURL.URL_CHECKSPEIC + "tokenKey=" + BaseApplication.getToken() + "&value=" + key;
        L.e("验证输入的规格:    " + url);
        VolleyRequestUtils.getInstance().getCookieRequest(mContext, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    setTextName(mValue);
                    setText(mValue);
                    if (onSelectData != null) {
                        onSelectData.getSelectId(mValue);
                    }
                    dialog.dismiss();
                } else if (error == 2) {
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    OnselectData onSelectData;

    public void setOnSelectData(OnselectData onSelectData) {
        this.onSelectData = onSelectData;
    }

    public interface OnselectData {
        void getSelectId(String key);

        String getTitle();
    }

}
