package com.qx.mstarstoreapp.viewutils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by UlrichAbiguime at Shenzhen.
 */
public class SquareImageView extends ImageView {

    private static final int SPEC = 3;
    private static int SCREENWIDTH;
    private Context mContext;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (SCREENWIDTH - dp2pix(SPEC)) / 2;
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
