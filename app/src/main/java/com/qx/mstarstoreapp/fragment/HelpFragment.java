package com.qx.mstarstoreapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseFragment;

/**
 * Created by Administrator on 2016/9/5.
 */
public class HelpFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.help_fragment,container,false);
    }
}
