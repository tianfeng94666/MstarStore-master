package com.qx.mstarstoreapp.viewutils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;

import butterknife.Bind;

import static com.qx.mstarstoreapp.utils.UIUtils.dip2px;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class LeftPopupWindow {

    private PopupWindow popupWindow;
    Context context;
    private View view;
    private LinearLayout llIsshow;

    public LeftPopupWindow(Context context) {
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.left_popup_window, null);
        popupWindow = new PopupWindow(view, dip2px(263), dip2px(320));
        llIsshow = (LinearLayout) view.findViewById(R.id.ll_isshow);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());     //点击外部消失这句很重要
        // 点击外面的控件也可以使得PopUpWindow dimiss
        popupWindow.setOutsideTouchable(true);
        initOnclic();
    }

    private void initOnclic() {
        llIsshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void showPop(View view) {
        popupWindow.showAtLocation(view, Gravity.LEFT, 0, 0);
    }


}
