package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.json.DeliveryTableResult;
import com.qx.mstarstoreapp.net.ImageLoadOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class DeliveryAdapter extends BaseAdapter {
    private Context context;
    private List<DeliveryTableResult.DataBean.ModelListBean> list;

    public DeliveryAdapter(Context context, List<DeliveryTableResult.DataBean.ModelListBean> list) {
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
        final DeliveryTableResult.DataBean.ModelListBean bean = list.get(i);
        if (view == null) {
            view = View.inflate(context, R.layout.item_delivery_product, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvDeliveryItemNumber.setText(i + 1 + "");
        viewHolder.tvDeliveryItemName.setText(bean.getTypeName() + " " + bean.getModelNum());
        viewHolder.tvDeliveryItemCost.setText("成本：" + bean.getUnitPrice());
        ImageLoader.getInstance().displayImage(bean.getPic(), viewHolder.ivProduct, ImageLoadOptions.getOptions());
        viewHolder.tvDeliveryItemLess.setText(bean.getSInfo());
        viewHolder.tvDeliveryItemLess2.setText(bean.getSInfo());
        viewHolder.tvDeliveryItemProductInf.setText(bean.getDInfo());
        viewHolder.tvDeliveryItemRemark.setText(bean.getRemark());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.llItemDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setChoose(!bean.isChoose());
                if (!bean.isChoose()) {
                    finalViewHolder.llPriductMore.setVisibility(View.GONE);
                    finalViewHolder.tvDeliveryItemLess.setVisibility(View.VISIBLE);
                    finalViewHolder.ivDeliveryDetail.setImageResource(R.drawable.icon_fliter_down);
                } else {
                    finalViewHolder.llPriductMore.setVisibility(View.VISIBLE);
                    finalViewHolder.tvDeliveryItemLess.setVisibility(View.GONE);
                    finalViewHolder.ivDeliveryDetail.setImageResource(R.drawable.icon_fliter_up);
                }
            }
        });

        return view;
    }


     class ViewHolder {
        @Bind(R.id.tv_delivery_item_number)
        TextView tvDeliveryItemNumber;
        @Bind(R.id.tv_stone)
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
        ImageView ivDeliveryDetail;
        @Bind(R.id.tv_delivery_item_less2)
        TextView tvDeliveryItemLess2;
        @Bind(R.id.iv_product)
        ImageView ivProduct;
        @Bind(R.id.ll_item_delivery)
        LinearLayout llItemDelivery;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



}
