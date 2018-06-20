package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.json.PurityEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class PurityRecycleViewAdapter extends RecyclerView.Adapter<PurityRecycleViewAdapter.ViewHolder> {
    private List<PurityEntity> modelPuritys;
    private Context context;
    private RecycleViewPartAdapter.MyItemClickListener myItemClickListener;
    private int selectPoint=-5;

    public PurityRecycleViewAdapter(List<PurityEntity> modelPuritys, Context context, RecycleViewPartAdapter.MyItemClickListener myItemClickListener) {
        this.modelPuritys = modelPuritys;
        this.context = context;
        this.myItemClickListener = myItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_color_tv, null);
        ViewHolder viewHolder = new ViewHolder(view, myItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTv.setText(modelPuritys.get(position).getTitle());
        if(position==selectPoint){
            holder.mTv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_gray));
            holder.mTv.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.mTv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_while));
            holder.mTv.setTextColor(context.getResources().getColor(R.color.text_color));
        }
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

        TextView mTv;
        LinearLayout rootView;


        public ViewHolder(View itemView, RecycleViewPartAdapter.MyItemClickListener listener) {
            super(itemView);

            mTv = (TextView) itemView.findViewById(R.id.tv_item_text);
            rootView = (LinearLayout) itemView.findViewById(R.id.ll_purty);


        }

    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
