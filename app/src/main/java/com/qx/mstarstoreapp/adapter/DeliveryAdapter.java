package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class DeliveryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Boolean> list;

    public DeliveryAdapter(Context context, ArrayList<Boolean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_delivery_product, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ibDeliveryDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i)){
                    finalViewHolder.llPriductMore.setVisibility(View.GONE);
                    finalViewHolder.tvDeliveryItemLess.setVisibility(View.VISIBLE);
                    finalViewHolder.ibDeliveryDetail.setImageResource(R.drawable.common_filter_arrow_down);
                    list.set(i,!list.get(i));
                }else {
                    finalViewHolder.llPriductMore.setVisibility(View.VISIBLE);
                    finalViewHolder.tvDeliveryItemLess.setVisibility(View.GONE);
                    list.set(i,!list.get(i));
                    finalViewHolder.ibDeliveryDetail.setImageResource(R.drawable.common_filter_arrow_up);
                }
            }
        });
        return view;
    }



    class ViewHolder {
        @Bind(R.id.tv_delivery_item_number)
        TextView tvDeliveryItemNumber;
        @Bind(R.id.tv_delivery_item_name)
        TextView tvDeliveryItemName;
        @Bind(R.id.tv_delivery_item_cost_tv)
        TextView tvDeliveryItemCostTv;
        @Bind(R.id.tv_delivery_item_cost)
        TextView tvDeliveryItemCost;
        @Bind(R.id.tv_delivery_item_product_inf)
        TextView tvDeliveryItemProductInf;
        @Bind(R.id.tv_delivery_item_remark)
        TextView tvDeliveryItemRemark;
        @Bind(R.id.ll_priduct_more)
        LinearLayout llPriductMore;
        @Bind(R.id.tv_delivery_item_less)
        TextView tvDeliveryItemLess;
        @Bind(R.id.ib_delivery_detail)
        ImageButton ibDeliveryDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
