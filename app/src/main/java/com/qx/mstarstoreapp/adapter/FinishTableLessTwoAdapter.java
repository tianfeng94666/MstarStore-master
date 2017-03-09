package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.json.FinishTableLessResult;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class FinishTableLessTwoAdapter extends BaseAdapter {
    private Context context;
    private List<FinishTableLessResult.DataBean.RecListBean.MoListBean> list;

    public FinishTableLessTwoAdapter(Context context, List<FinishTableLessResult.DataBean.RecListBean.MoListBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        FinishTableLessResult.DataBean.RecListBean.MoListBean bean = list.get(i);
        if (view == null) {
            view = View.inflate(context, R.layout.item_finished_less, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvItemFinishDeliveryNumber.setText("出库单号"+bean.getMoNum());
        viewHolder.tvItemFinishDeliveryDate.setText("出库日期:"+bean.getMoDate());
        viewHolder.tvItemFinishDeliveryPrice.setText("价格："+bean.getTotalPrice()+"");
        viewHolder.tvItemFinishDeliveryAmount.setText("数量："+bean.getNumber());
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_item_finish_delivery_number)
        TextView tvItemFinishDeliveryNumber;
        @Bind(R.id.tv_item_finish_delivery_date)
        TextView tvItemFinishDeliveryDate;
        @Bind(R.id.tv_item_finish_delivery_price)
        TextView tvItemFinishDeliveryPrice;
        @Bind(R.id.tv_item_finish_delivery_amount)
        TextView tvItemFinishDeliveryAmount;
        @Bind(R.id.iv_item_goto_delivery)
        ImageView ivItemGotoDelivery;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
