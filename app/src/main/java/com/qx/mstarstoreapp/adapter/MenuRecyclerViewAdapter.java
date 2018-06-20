package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.MyOrderActivity;
import com.qx.mstarstoreapp.activity.OrderReviewActivity;
import com.qx.mstarstoreapp.activity.ShowGoldenPriceActivity;
import com.qx.mstarstoreapp.base.MenuItemBean;
import com.qx.mstarstoreapp.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<MenuItemBean> list;

    public MenuRecyclerViewAdapter(Context context, List<MenuItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_mainacitvity_menu, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTv.setText(list.get(position).getName());
        ViewGroup.LayoutParams layoutParams = holder.mTv.getLayoutParams();
        layoutParams.width = UIUtils.getWindowWidth() / 5;
        holder.mTv.setLayoutParams(layoutParams);
        holder.mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity(list.get(position).getName());
            }
        });

    }

    private void gotoNextActivity(String name) {
        Intent i  =new Intent();
        switch (name){
            case "显示金价":
                i.setClass(context, ShowGoldenPriceActivity.class);
                break;
            case "订单审核":
                i.setClass(context, OrderReviewActivity.class);
                break;
            case "我的订单":
                i.setClass(context, MyOrderActivity.class);
                break;
        }
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;
        RelativeLayout rootView;


        public ViewHolder(View itemView) {
            super(itemView);

            mTv = (TextView) itemView.findViewById(R.id.tv_menu_itme);


        }

    }
}
