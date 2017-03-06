package com.qx.mstarstoreapp.viewutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.bean.Type;
import com.qx.mstarstoreapp.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CustomSelectButton extends RelativeLayout{

    private String text;
    private Type type;
    private Button tv;
    private List<Type> types;
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
        if (!StringUtils.isEmpty(textName)){
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
         return  this.tv.getText().toString();
    }

    public CustomSelectButton(Context context) {
        this(context,null);
    }

    public CustomSelectButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomSelectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        initView(context,attrs);
    }


    private void initView(Context context ,AttributeSet attrs) {
            TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CustomSelectButton);
            try {
                textName=typedArray.getString(R.styleable.CustomSelectButton_tv_name);
              //  textSize=typedArray.getDimension(R.styleable.CustomSelectButton_tv_size,8);
               // textColor = typedArray.getColor(R.styleable.CustomSelectButton_tv_color,0);
            }finally {
                typedArray.recycle();
            }

            View rootView= View.inflate(context,R.layout.custom_select_button,this);
            tv= (Button) rootView.findViewById(R.id.id_cus_tv);
            if (!StringUtils.isEmpty(textName)){
                tv.setText(textName);
            }
           // tv.setTextSize(textSize);
            tv.setOnClickListener(new RadioClickListener());
    }

    public class RadioClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            if (onSelectData!=null){
                types=onSelectData.getData();
                showDialog();
            }

        }
    }
    AlertDialog dialog;
    public void showDialog(){
        View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_radiobutton,null);
        ListView listView= (ListView) view.findViewById(R.id.id_listview);
        TextView titleView= (TextView) view.findViewById(R.id.titleName);
        Button button=(Button) view.findViewById(R.id.id_cancle);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type!=null){
                    if (!StringUtils.isEmpty(type.getTypeName())){
                        setTextName(type.getTypeName());
                        setText(type.getTypeName());
                    }
                    if (onSelectData!=null){
                        onSelectData.getSelectId(type);
                    }
                }
                dialog.dismiss();
            }
        });
        if (onSelectData!=null){
            titleView.setText(onSelectData.getTitle());
            titleView.setTextColor(getResources().getColor(R.color.black));
        }
        final CustomListAdapter adapter=new CustomListAdapter(types);
        listView.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //创建一个自定义View
        builder.setView(view);
        //创建一个AlertDialog对象
        dialog= builder.create();
        dialog.setCanceledOnTouchOutside(true);
        //设置点击Dialog外部任意区域关闭Dialog，false为不会关闭
        dialog.show();
    }

    public class CustomListAdapter extends BaseAdapter {
        private List<Type> types;
        //标记位
        private  int index=-1;
        public CustomListAdapter(List<Type> type) {
            this.types=type;
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
            ViewHolder viewHolder=null;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=View.inflate(mContext,R.layout.adapter_custom_select,null);
                viewHolder.radioButton= (RadioButton) convertView.findViewById(R.id.id_rb);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.type=types.get(position);
            viewHolder.radioButton.setText(viewHolder.type.getTypeName());
            viewHolder.radioButton.setChecked(viewHolder.type.isSelect());
            viewHolder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        index=position;
                       // text=types.get(index).getTypeName();
                        type=types.get(index);
                        notifyDataSetChanged();
                       // index = buttonView.getId();//将temp重新赋值，记录下本次点击的RadioButton
                    }

                }
            });
            if (index==position){
                viewHolder.radioButton.setChecked(true);
            }else {
                viewHolder.radioButton.setChecked(false);
            }
            return convertView;
        }
    }

    public  class ViewHolder{
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

    public interface OnselectData{
        List<Type> getData();
        void getSelectId(Type type);
        String getTitle();
    }

}
