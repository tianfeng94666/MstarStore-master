package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class DownloadActivity extends BaseActivity {
    @Bind(R.id.tv_download_name)
    TextView tvDownloadName;
    @Bind(R.id.iv_downlaoad)
    ImageView ivDownlaoad;
    @Bind(R.id.tv_websit)
    TextView tvWebsit;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }

    private void initView() {
        final String st;
        if (type.equals("1")) {
            tvDownloadName.setText("安卓最新版");
//            SpannableString ss = new SpannableString("");
            tvWebsit.setText(R.string.android_download);
         st = "https://www.pgyer.com/IGab";
            ivDownlaoad.setImageResource(R.drawable.android_download);
        } else {
            tvDownloadName.setText("IOS最新版");
//            SpannableString ss = new SpannableString("");
            tvWebsit.setText(R.string.ios_download);
            st = "https://www.pgyer.com/2H57";
            ivDownlaoad.setImageResource(R.drawable.ios_download);
        }
        tvWebsit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(st));
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadNetData() {

    }
}
