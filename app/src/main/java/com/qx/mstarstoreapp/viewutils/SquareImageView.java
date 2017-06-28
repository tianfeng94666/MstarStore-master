package com.qx.mstarstoreapp.viewutils;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.qx.mstarstoreapp.base.Global;

/**
 * Created by UlrichAbiguime at Shenzhen.
 */
public class SquareImageView extends ImageView {

    private static final int SPEC = 3;
    private static int SCREENWIDTH;
    private Context mContext;
    private int divideAmount;



    public SquareImageView(Context context) {
        super(context);
        init(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context mContext) {
        this.mContext = mContext;
        SCREENWIDTH = getScreenWidth();
    }
    public boolean isScreenChange() {

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {

//横屏
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {

//竖屏
            return false;
        }
        return false;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if( isScreenChange()){
           divideAmount=4;
        }else {
          divideAmount=2;
        }
        int width = (SCREENWIDTH - dp2pix(SPEC)) / divideAmount;
        setMeasuredDimension(width, width);
    }

    private int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    private int dp2pix(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
