package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.json.GetGoldenPriceResult;
import com.qx.mstarstoreapp.json.PurityEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class GetGoldenPriceRecyclerViewAdapter extends RecyclerView.Adapter<GetGoldenPriceRecyclerViewAdapter.ViewHolder> {
    private List<GetGoldenPriceResult.DataBean.PurityGoldPriceBean> modelPuritys;
    private Context context;
    private RecycleViewPartAdapter.MyItemClickListener myItemClickListener;
    private int selectPoint = -5;

    public GetGoldenPriceRecyclerViewAdapter(List<GetGoldenPriceResult.DataBean.PurityGoldPriceBean> modelPuritys, Context context, RecycleViewPartAdapter.MyItemClickListener myItemClickListener) {
        this.modelPuritys = modelPuritys;
        this.context = context;
        this.myItemClickListener = myItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_golden_price, null);
        ViewHolder viewHolder = new ViewHolder(view, myItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTvLable.setText(modelPuritys.get(position).getPurityName());
        holder.mTvValue.setText(modelPuritys.get(position).getUnitPrice() + "");
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onItemClick(v, position);
                    selectPoint = position;
                    notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelPuritys == null ? 0 : modelPuritys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvLable, mTvValue;
        RelativeLayout rootView;


        public ViewHolder(View itemView, RecycleViewPartAdapter.MyItemClickListener listener) {
            super(itemView);

            mTvLable = (TextView) itemView.findViewById(R.id.tv_lable);
            mTvValue = (TextView) itemView.findViewById(R.id.tv_value);
            rootView = (RelativeLayout) itemView.findViewById(R.id.root_view);


        }

    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
