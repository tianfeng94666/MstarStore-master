package com.qx.mstarstoreapp.viewutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.bean.Type;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.wx.wheelview.adapter.BaseWheelAdapter;
import com.wx.wheelview.adapter.SimpleWheelAdapter;
import com.wx.wheelview.common.WheelData;
import com.wx.wheelview.widget.WheelItem;
import com.wx.wheelview.widget.WheelView;

import java.util.Arrays;
import java.util.List;

import static com.qx.mstarstoreapp.utils.UIUtils.*;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CustomSelectButton extends RelativeLayout {

    private View rootview;
    private String text;
    private Type type;
    private Button tv;
    private List<Type> types;
    private List<String> stringTypes;
    private Context mContext;
    String textName;
    float textSize;
    private PopupWindow popupWindow;
    private WheelView wheelView;
    private TextView tvTitle;
    private TextView tvConfirm;
    private int defaultPosition = 5;


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
            this.tv.setTextColor(getResources().getColor(R.color.text_color2));
        }
    }

    public String getTextName() {
        return this.tv.getText().toString();
    }

    public CustomSelectButton(Context context, View rootview) {
        super(context);
        this.rootview = rootview;
    }

    public CustomSelectButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSelectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSelectButton);
        try {
            textName = typedArray.getString(R.styleable.CustomSelectButton_tv_name);
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
                types = onSelectData.getData();
//                showDialog();
                showPopupWindow();
            }

        }
    }

    public void showPopupWindow() {
        String text = getTextName().toString();
        View view = View.inflate(mContext, R.layout.popupwindow_bottom, null);
        wheelView = (WheelView) view.findViewById(R.id.wv_popupwindwo);
        tvTitle = (TextView) view.findViewById(R.id.tv_title_popupwindow);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvTitle.setText(onSelectData.getTitle() + "");
        SimpleWheelAdapter arrayWheelAdapter = new SimpleWheelAdapter(mContext);
        wheelView.setWheelAdapter(arrayWheelAdapter);
        wheelView.setWheelSize(5);
        wheelView.setWheelData(types);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        wheelView.setStyle(style);
        int select = getSelect(text);
        if (select == -1) {
            wheelView.setSelection(defaultPosition);
        } else {
            wheelView.setSelection(select);
        }

        wheelView.setSkin(WheelView.Skin.Holo);
//        wheelView.setLoop(true);
        wheelView.setWheelClickable(true);
        UIUtils.setBackgroundAlpha(mContext, 0.5f);//设置屏幕透明度

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view1 = onSelectData.getRootView();
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.Animation);
        popupWindow.showAtLocation(view1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = types.get(wheelView.getCurrentPosition());
                if (type != null) {
                    if (!StringUtils.isEmpty(type.getTypeName())) {
                        setTextName(type.getTypeName());
                        setText(type.getTypeName());
                    }
                    if (onSelectData != null) {
                        onSelectData.getSelectId(type);
                    }
                }
                closePupupWindow();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.setBackgroundAlpha(mContext, 1.0f);//设置屏幕透明度
            }
        });
    }

    private int getSelect(String text) {
        int a = -1;
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getTypeName().equals(text)) {
                a = i;
                break;
            }
        }
        return a;
    }

    public void closePupupWindow() {
        UIUtils.setBackgroundAlpha(mContext, 1.0f);//设置屏幕透明度
        popupWindow.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((BaseActivity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((BaseActivity) mContext).getWindow().setAttributes(lp);
    }

    AlertDialog dialog;

    public void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_radiobutton, null);
        ListView listView = (ListView) view.findViewById(R.id.id_listview);
        TextView titleView = (TextView) view.findViewById(R.id.titleName);
        Button button = (Button) view.findViewById(R.id.id_cancle);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != null) {
                    if (!StringUtils.isEmpty(type.getTypeName())) {
                        setTextName(type.getTypeName());
                        setText(type.getTypeName());
                    }
                    if (onSelectData != null) {
                        onSelectData.getSelectId(type);
                    }
                }
                dialog.dismiss();
            }
        });
        if (onSelectData != null) {
            titleView.setText(onSelectData.getTitle());
            titleView.setTextColor(getResources().getColor(R.color.black));
        }
        final CustomListAdapter adapter = new CustomListAdapter(types);
        listView.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //创建一个自定义View
        builder.setView(view);
        //创建一个AlertDialog对象
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        //设置点击Dialog外部任意区域关闭Dialog，false为不会关闭
        dialog.show();
    }

    public class CustomListAdapter extends BaseAdapter {
        private List<Type> types;
        //标记位
        private int index = -1;

        public CustomListAdapter(List<Type> type) {
            this.types = type;
        }

        @Override
        public int getCount() {
            return types.size();
        }

        @Override
        public Object getItem(int position) {
            return types.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.adapter_custom_select, null);
                viewHolder.radioButton = (RadioButton) convertView.findViewById(R.id.id_rb);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.type = types.get(position);
            viewHolder.radioButton.setText(viewHolder.type.getTypeName());
            viewHolder.radioButton.setChecked(viewHolder.type.isSelect());
            viewHolder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        index = position;
                        // text=types.get(index).getTypeName();
                        type = types.get(index);
                        notifyDataSetChanged();
                        // index = buttonView.getId();//将temp重新赋值，记录下本次点击的RadioButton
                    }

                }
            });
            if (index == position) {
                viewHolder.radioButton.setChecked(true);
            } else {
                viewHolder.radioButton.setChecked(false);
            }
            return convertView;
        }
    }

    public class ViewHolder {
        RadioButton radioButton;
        Type type;
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
        List<Type> getData();

        void getSelectId(Type type);

        String getTitle();

        View getRootView();
    }

    class SimpleWheelAdapter extends com.wx.wheelview.adapter.SimpleWheelAdapter {

        private Context mContext;

        public SimpleWheelAdapter(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        public View bindView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new WheelItem(mContext);
            }
            WheelItem item = (WheelItem) convertView;

            item.setText(types.get(position).getTypeName());
            return convertView;
        }

    }

}
