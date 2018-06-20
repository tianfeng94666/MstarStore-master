package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.bean.StoneRangeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class ClassifyOrderAdapter extends RecyclerView.Adapter<ClassifyOrderAdapter.ViewHold> {

    private Context mContext;
    private List<StoneRangeBean> list;

    public ClassifyOrderAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_classify_order, null);
        ViewHold viewHold = new ViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, int position) {
        StoneRangeBean stoneRangeBean =list.get(position);
        holder.tvStoneRange.setText(stoneRangeBean.getStoneMin()+"~"+stoneRangeBean.getStoneMax());
        holder.tvAmount.setText(stoneRangeBean.getAmount());
        if(stoneRangeBean.getIsChoose()==1){
            holder.cbIschoose.setChecked(true);
        }else {
            holder.cbIschoose.setChecked(false);
        }
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwipeListener.onDel(holder.getAdapterPosition());
            }
        });
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwipeListener.edit(holder.getAdapterPosition());
            }
        });
        holder.cbIschoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbIschoose.isChecked()){
                    mOnSwipeListener.ischoose(holder.getAdapterPosition(),1);
                }else {
                    mOnSwipeListener.ischoose(holder.getAdapterPosition(),0);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHold extends RecyclerView.ViewHolder {
        TextView tvStoneRange;
        TextView tvAmount;
        CheckBox cbIschoose;
        Button btEdit;
        Button btDelete;

        public ViewHold(View itemView) {
            super(itemView);
            tvStoneRange = (TextView) itemView.findViewById(R.id.tv_stone_range);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_total_count);
            cbIschoose = (CheckBox) itemView.findViewById(R.id.cb_ischoose);
            btDelete = (Button) itemView.findViewById(R.id.bt_delete);
            btEdit = (Button) itemView.findViewById(R.id.bt_edit);

        }
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);

        void edit(int pos);
        void ischoose(int pos,int i);
    }

    private onSwipeListener mOnSwipeListener;

    public onSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

}
