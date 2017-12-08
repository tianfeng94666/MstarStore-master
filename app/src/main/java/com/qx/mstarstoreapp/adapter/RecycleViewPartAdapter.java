package com.qx.mstarstoreapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qx.mstarstoreapp.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class RecycleViewPartAdapter extends RecyclerView.Adapter<RecycleViewPartAdapter.ViewHolder> {
    private ArrayList<String> list;
    public RecycleViewPartAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_part,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          holder.mIv.setImageResource(R.mipmap.dz_03);

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIv;

        public ViewHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_item_part);

        }
    }


}
