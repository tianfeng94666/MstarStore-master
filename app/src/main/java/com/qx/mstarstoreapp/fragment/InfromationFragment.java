package com.qx.mstarstoreapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseFragment;

/*
 * 创建人：Yangshao
 * 创建时间：2016/9/5 16:22
 * @version    
 *    
 */
public class InfromationFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.infromation_fragment,container,false);
    }
}
