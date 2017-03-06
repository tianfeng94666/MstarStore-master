//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.qx.mstarstoreapp.viewutils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class CustomListView extends RelativeLayout {
    private String TAG = CustomListView.class.getSimpleName();
    private CustomAdapter myCustomAdapter;
    private static boolean addChildType;
    private int dividerHeight = 0;
    private int dividerWidth = 0;
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            try {
                if(msg.getData().containsKey("getRefreshThreadHandler")) {
                    CustomListView.addChildType = false;
                    CustomListView.this.myCustomAdapter.notifyCustomListView(CustomListView.this);
                }
            } catch (Exception var3) {
                Log.w(CustomListView.this.TAG, var3);
            }

        }
    };

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onLayout(boolean arg0, int argLeft, int argTop, int argRight, int argBottom) {
        Log.i(this.TAG, "L:" + argLeft + " T:" + argTop + " R:" + argRight + " B:" + argBottom);
        int count = this.getChildCount();
        int row = 0;
        int lengthX = 0;
        int lengthY = 0;

        for(int lp = 0; lp < count; ++lp) {
            View child = this.getChildAt(lp);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if(lengthX == 0) {
                lengthX += width;
            } else {
                lengthX += width + this.getDividerWidth();
            }

            if(lp == 0 && lengthX <= argRight) {
                lengthY += height;
            }

            if(lengthX > argRight) {
                lengthX = width;
                lengthY += this.getDividerHeight() + height;
                ++row;
                child.layout(width - width, lengthY - height, width, lengthY);
            } else {
                child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
            }
        }

        ViewGroup.LayoutParams var14 = this.getLayoutParams();
        var14.height = lengthY;
        this.setLayoutParams(var14);
        if(isAddChildType()) {
            (new Thread(new CustomListView.RefreshCustomThread((CustomListView.RefreshCustomThread)null))).start();
        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(width, height);

        for(int i = 0; i < this.getChildCount(); ++i) {
            View child = this.getChildAt(i);
            child.measure(0, 0);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    static final boolean isAddChildType() {
        return addChildType;
    }

    public static void setAddChildType(boolean addChildType) {
        addChildType = addChildType;
    }

    final int getDividerHeight() {
        return this.dividerHeight;
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }

    final int getDividerWidth() {
        return this.dividerWidth;
    }

    public void setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
    }

    public void setAdapter(CustomAdapter adapter) {
        this.myCustomAdapter = adapter;
        setAddChildType(true);
        adapter.notifyCustomListView(this);
    }


    private final void sendMsgHanlder(Handler handler, Bundle data) {
        Message msg = handler.obtainMessage();
        msg.setData(data);
        handler.sendMessage(msg);
    }

    private final class RefreshCustomThread implements Runnable {
        private RefreshCustomThread(RefreshCustomThread refreshCustomThread) {
        }

        public void run() {
            Bundle b = new Bundle();

            try {
                Thread.sleep(50L);
            } catch (Exception var6) {
                ;
            } finally {
                b.putBoolean("getRefreshThreadHandler", true);
                CustomListView.this.sendMsgHanlder(CustomListView.this.handler, b);
            }

        }
    }

}
