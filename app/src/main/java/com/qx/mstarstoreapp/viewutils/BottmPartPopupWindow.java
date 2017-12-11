package com.qx.mstarstoreapp.viewutils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.qx.mstarstoreapp.ItemDecoration.SpaceItemDecoration;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.RecycleViewPartAdapter;
import com.qx.mstarstoreapp.manager.FitGridLayoutManager;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class BottmPartPopupWindow {
    @Bind(R.id.rv_part)
    RecyclerView rvPart;
    private Context context;
    private View view;
    private PopupWindow popupWindow;
    private FitGridLayoutManager mLayoutManager;

    public BottmPartPopupWindow(Context context) {
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.bottom_popup_window, null);
        ButterKnife.bind(this, view);
        int width = UIUtils.getWindowWidth();
        popupWindow = new PopupWindow(view, width, width);

        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE && !popupWindow.isFocusable()) {
                    //如果焦点不在popupWindow上，且点击了外面，不再往下dispatch事件：
                    //不做任何响应,不 dismiss popupWindow
                    return true;
                }
                //否则default，往下dispatch事件:关掉popupWindow，
                return false;
            }
        });
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_bottom_style);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());     //点击外部消失这句很重要

    }

    public void showPop(View view) {

        mLayoutManager = new FitGridLayoutManager(context, rvPart, 2, GridLayoutManager.HORIZONTAL, false);
        rvPart.setLayoutManager(mLayoutManager);
        ArrayList list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("1");
        }
        RecycleViewPartAdapter recycleViewPartAdapter = new RecycleViewPartAdapter(list, new RecycleViewPartAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                ToastManager.showToastReal(postion);
                closePopupWindow();
            }
        });
        //设置item间距，30dp
        rvPart.addItemDecoration(new SpaceItemDecoration(4));
        rvPart.setAdapter(recycleViewPartAdapter);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    public void closePopupWindow() {
        popupWindow.dismiss();
    }
}
