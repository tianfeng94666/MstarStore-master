package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.DeliveryTableActivity;
import com.qx.mstarstoreapp.activity.FinishTableLessActivity;
import com.qx.mstarstoreapp.activity.FinishTableMoreActivity;
import com.qx.mstarstoreapp.json.FinishTableLessResult;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class FinishTableLessAdapter extends BaseAdapter {
    Context context;
    List<FinishTableLessResult.DataBean.RecListBean> list;

    public FinishTableLessAdapter(Context context, List<FinishTableLessResult.DataBean.RecListBean> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        final FinishTableLessResult.DataBean.RecListBean recListBean = list.get(i);
        if (view == null) {
            view = View.inflate(context, R.layout.item_finish_table_one, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvFinishNumber.setText("结算单号：" + recListBean.getRecNum());
        viewHolder.tvFinishCustomerName.setText("客户名称：" + recListBean.getCustomerName());
        viewHolder.tvFinishDate.setText("下单日期：" + recListBean.getRecDate());
        viewHolder.tvFinishQuality.setText("成色：" + recListBean.getPurityName());
        viewHolder.tvFinishAmount.setText("数量：" + recListBean.getNumber());
        viewHolder.tvFinisShSumMoney.setText("¥" + recListBean.getTotalPrice());

        FinishTableLessTwoAdapter finishTableLessTwoAdapter = new FinishTableLessTwoAdapter(context, recListBean.getMoList());
        viewHolder.lvSendingTables.setAdapter(finishTableLessTwoAdapter);
        viewHolder.rlGotoFinishMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FinishTableMoreActivity.class);
                intent.putExtra("recNumber", recListBean.getRecNum() + "");
                ((FinishTableLessActivity) context).startActivity(intent);
            }
        });
        return view;

    }

    class ViewHolder {
        @Bind(R.id.rl_goto_finish_more)
        RelativeLayout rlGotoFinishMore;
        @Bind(R.id.tv_finish_number)
        TextView tvFinishNumber;
        @Bind(R.id.ib_right)
        ImageView ivRight;
        @Bind(R.id.tv_finish_customer_name)
        TextView tvFinishCustomerName;
        @Bind(R.id.tv_number)
        TextView tvNumber;
        @Bind(R.id.tv_finish_date)
        TextView tvFinishDate;
        @Bind(R.id.tv_finish_quality)
        TextView tvFinishQuality;
        @Bind(R.id.tv_finish_amount)
        TextView tvFinishAmount;
        @Bind(R.id.tv_finish_sum_money_tv)
        TextView tvFinishSumMoneyTv;
        @Bind(R.id.tv_finisSh_sum_money)
        TextView tvFinisShSumMoney;
        @Bind(R.id.lv_sending_tables)
        ListView lvSendingTables;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
