package com.qx.mstarstoreapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.json.GetRingPartResult;
import com.qx.mstarstoreapp.net.ImageLoadOptions;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class RecycleViewPartAdapter extends RecyclerView.Adapter<RecycleViewPartAdapter.ViewHolder> {
    private List<GetRingPartResult.DataBean.ModelPartsBean> list;
    private List<String> countList;
    private MyItemClickListener myItemClickListener;


    public RecycleViewPartAdapter(List<GetRingPartResult.DataBean.ModelPartsBean> list,List<String> countList, MyItemClickListener myItemClickListener) {
        this.list = list;
        this.countList = countList;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_part, null);
        ViewHolder viewHolder = new ViewHolder(view,myItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(list.get(position).getPicm(),holder.mIv, ImageLoadOptions.getOptionsHight());
        holder.mTvAmount.setText(countList.get(position));
        holder.mTvName.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIv;
        TextView mTvAmount;
        TextView mTvName;
        View rootView;
        private MyItemClickListener mListener;

        public ViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_item_part);
            mTvAmount =(TextView)itemView.findViewById(R.id.tv_amount) ;
            mTvName =(TextView)itemView.findViewById(R.id.tv_name) ;
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




    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

}
