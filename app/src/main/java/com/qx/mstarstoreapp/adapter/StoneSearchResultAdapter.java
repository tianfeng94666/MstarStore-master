package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.StoneSearchResultActivity;
import com.qx.mstarstoreapp.json.StoneSearchInfoResult;
import com.qx.mstarstoreapp.utils.ToastManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class StoneSearchResultAdapter extends BaseAdapter {
    Context context;
    List<StoneSearchInfoResult.DataBean.StoneBean.ListBean> list;
     ChooseItemInterface  chooseItem;

    public StoneSearchResultAdapter(List<StoneSearchInfoResult.DataBean.StoneBean.ListBean> list, Context context) {
        this.context = context;
        this.list = list;
        chooseItem = (ChooseItemInterface) context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final StoneSearchInfoResult.DataBean.StoneBean.ListBean bean = list.get(position);
        if (view == null) {
            view = View.inflate(context, R.layout.item_stone_search_result, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvItemWeight.setText(bean.getWeight());
        viewHolder.tvItemShape.setText(bean.getShape());
        viewHolder.tvItemColor.setText(bean.getColor());
        viewHolder.tvItemPurity.setText(bean.getPurity());
        viewHolder.tvItemCut.setText(bean.getCut());
        viewHolder.tvItemPolish.setText(bean.getPolishing());
        viewHolder.tvItemSymmetric.setText(bean.getSymmetric());
        viewHolder.tvItemFluorescence.setText(bean.getFluorescence());
        viewHolder.tvItemCertauth.setText(bean.getCertAuth());
        viewHolder.cbIscheckStone.setChecked(bean.ischeck());
        viewHolder.tvItemPrice.setText(bean.getPrice());
        viewHolder.tvItemCerauthNumber.setText(bean.getCertCode());
        viewHolder.cbIscheckStone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               bean.setIscheck(isChecked);
            }
        });
        viewHolder.tvItemQuotedPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastManager.showToastReal("diaji"+position);
                chooseItem.quotedPrice(bean.getId());
            }
        });
        return view;
    }

    public String getQuotedPriceId()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            StoneSearchInfoResult.DataBean.StoneBean.ListBean bean = list.get(i);
            if (bean.ischeck()) {
                if (sb.toString().equals("")) {
                    sb.append(bean.getId() + "");
                } else {
                    sb.append("," + bean.getId());
                }

            }
        }
        return sb.toString();
    }


    class ViewHolder {
        @Bind(R.id.cb_ischeck_stone)
        CheckBox cbIscheckStone;
        @Bind(R.id.tv_item_weight)
        TextView tvItemWeight;
        @Bind(R.id.tv_item_shape)
        TextView tvItemShape;
        @Bind(R.id.tv_item_color)
        TextView tvItemColor;
        @Bind(R.id.tv_item_purity)
        TextView tvItemPurity;
        @Bind(R.id.tv_item_cut)
        TextView tvItemCut;
        @Bind(R.id.tv_item_polish)
        TextView tvItemPolish;
        @Bind(R.id.tv_item_symmetric)
        TextView tvItemSymmetric;
        @Bind(R.id.tv_item_fluorescence)
        TextView tvItemFluorescence;
        @Bind(R.id.tv_item_certauth)
        TextView tvItemCertauth;
        @Bind(R.id.tv_item_price)
        TextView tvItemPrice;
        @Bind(R.id.tv_item_cerauth_number)
        TextView tvItemCerauthNumber;
        @Bind(R.id.tv_item_quoted_price)
        TextView tvItemQuotedPrice;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public interface ChooseItemInterface {
        void quotedPrice(String id);
    }
}
