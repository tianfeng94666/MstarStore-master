package com.qx.mstarstoreapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.DownloadActivity;
import com.qx.mstarstoreapp.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/5.
 */
public class HelpFragment extends BaseFragment {


    @Bind(R.id.ll_android_download)
    LinearLayout llAndroidDownload;
    @Bind(R.id.ll_ios_download)
    LinearLayout llIosDownload;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help_fragment, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        llAndroidDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });

        llIosDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
