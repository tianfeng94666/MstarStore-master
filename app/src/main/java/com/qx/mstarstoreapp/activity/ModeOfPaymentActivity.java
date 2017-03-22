package com.qx.mstarstoreapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.dialog.ConfirmPwdDialog;
import com.qx.mstarstoreapp.inter.OnResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * 创建人：Yangshao
 * 创建时间：2016/10/17 9:45
 * @version    
 *    
 */
public class ModeOfPaymentActivity extends BaseActivity {


    @Bind(R.id.id_lv_pay)
    ListView idLvPay;
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.id_lay1)
    LinearLayout idLay1;
    private int[] payDrawables;//支付方式图标
    private String[] payNames;//支付方式 名称

    SelectDialog selectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mode_pay);
        ButterKnife.bind(this);

        titleText.setText("选择支付");
                payDrawables = new int[]{R.drawable.icon_small, R.drawable.icon_alipay, R.drawable.icon_wechat_pay};
        payNames = new String[]{"余额", "支付宝", "微信"};
        SimpleAdapter adapter = new SimpleAdapter(this, getListViewData(),
                R.layout.item_pay_mode, new String[]{"img", "info"},
                new int[]{R.id.image, R.id.info});
        idLvPay.setAdapter(adapter);
        idLvPay.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //默认选择第一行
        idLvPay.setItemChecked(0, true);
        // selectDialog=new SelectDialog(this);
        // selectDialog.show();


    }

    @Override
    public void loadNetData() {

    }

    private List<Map<String, Object>> getListViewData() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < payDrawables.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", payDrawables[i]);
            map.put("info", payNames[i]);
            list.add(map);
        }
        return list;
    }


    public class FilterFlowDialog extends PopupWindow {
        private View mPopView;
        ListView listView;

        public FilterFlowDialog(Context context) {
            super(context);
            init(context);
        }

        //new Screen(mctontext)).getHeight() - getStatusBarHeight()
        private void init(Context context) {
            mPopView = LayoutInflater.from(context).inflate(R.layout.dialog_pay, null);
            listView = (ListView) mPopView.findViewById(R.id.list);
            this.setContentView(mPopView);
            int width = getResources().getDimensionPixelOffset(R.dimen.m150);
            int height = getResources().getDimensionPixelOffset(R.dimen.m100);
            this.setWidth(width);
           this.setHeight(height);
            // 设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            // 点击外面的控件也可以使得PopUpWindow dimiss
            this.setOutsideTouchable(false);
            // 设置SelectPicPopupWindow弹出窗体动画效果
            ColorDrawable dw = new ColorDrawable(0xffffffff);//0xb0000000
           // this.setAnimationStyle(R.style.loading_dialog);
            // ColorDrawable dw = new ColorDrawable(0x00000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
           // this.setBackgroundDrawable(dw);//半透明颜色
        }


    }


    public class SelectDialog extends AlertDialog {

        public SelectDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_pay);
        }


    }


    public void onPay(View view){
       FilterFlowDialog filedialog = new FilterFlowDialog(this);
       // filedialog.showAtLocation(idLay1, Gravity.CENTER,0,0);

        ConfirmPwdDialog updateDialog=new ConfirmPwdDialog(this,"","");
        updateDialog.setConfirmListener(new OnResultListener<String>() {
            @Override
            public void onResult(boolean isSecceed, String obj) {
                if (isSecceed){
                    openActivity(PaySuccessActivity.class,null);
                }
            }
        });
        updateDialog.show(getFragmentManager(), "loginDialog");
        // updateDialog.
      //  updateDialog.onCreateDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
