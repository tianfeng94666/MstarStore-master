package com.qx.mstarstoreapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.json.SendingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class SearchResultAdapter extends BaseAdapter {
    Context context;
    private List<String> list;
    public SearchResultAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View view, ViewGroup parent) {
//        ViewHolder viewHolder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.item_search_results, null);
//            viewHolder = new SendingListAdater.ViewHolder(view);
//            view.setTag(viewHolder);
        } else {
//            viewHolder = (SendingListAdater.ViewHolder) view.getTag();
        }
        return null;
    }
}
