package com.qx.mstarstoreapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.json.GetPartListResult;
import com.qx.mstarstoreapp.json.GetRingPartResult;
import com.qx.mstarstoreapp.json.ModelPartsBean;
import com.qx.mstarstoreapp.net.ImageLoadOptions;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class RecycleViewPartListAdapter extends RecyclerView.Adapter <RecycleViewPartListAdapter.ViewHolder>{
    private List<ModelPartsBean> list;
    private RecycleViewPartListAdapter.PartListItemClickListener myItemClickListener;
    private int selectPosition;
    private boolean isClick= false;


    public RecycleViewPartListAdapter(List<ModelPartsBean> list, RecycleViewPartListAdapter.PartListItemClickListener myItemClickListener) {
        this.list = list;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public RecycleViewPartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_part, null);
        RecycleViewPartListAdapter.ViewHolder viewHolder = new RecycleViewPartListAdapter.ViewHolder(view,myItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewPartListAdapter.ViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(list.get(position).getPicm(),holder.mIv, ImageLoadOptions.getOptionsHight());
        holder.mTvName.setText(list.get(position).getTitle());
        if(isClick){
            if(position==selectPosition){
                holder.rootView.setBackgroundResource(R.drawable.border_gray_line);
                holder.mTvName.setTextColor(R.color.theme_red);
            }else {
                holder.rootView.setBackgroundResource(R.drawable.border_white_line);
                holder.mTvName.setTextColor(R.color.text_color);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setChoose(int postion) {
        selectPosition = postion;
        isClick=true;
        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIv;
        TextView mTvAmount;
        TextView mTvName;
        View rootView;
        private RecycleViewPartListAdapter.PartListItemClickListener mListener;

        public ViewHolder(View itemView,RecycleViewPartListAdapter.PartListItemClickListener listener) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_item_part);
            mTvAmount =(TextView)itemView.findViewById(R.id.tv_amount) ;
            mTvName =(TextView)itemView.findViewById(R.id.tv_name) ;
            mTvAmount.setVisibility(View.GONE);
            rootView = itemView.findViewById(R.id.root_view);
            this.mListener = listener;
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onItemClick(v, getPosition());
                    }
                }
            });
        }

    }




    public interface PartListItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
