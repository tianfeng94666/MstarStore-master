package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.SearchResultAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.bean.OrderSearchBean;
import com.qx.mstarstoreapp.json.SearchOrderResult;
import com.qx.mstarstoreapp.json.SearchResultResult;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.LoadingWaitDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.lv_results)
    ListView lvResults;
    private OrderSearchBean orderSearchBean;
    private List<SearchResultResult.DataBean.OrderListBean> list =new ArrayList<>();
    SearchResultAdapter searchResultAdapter;
    private LoadingWaitDialog dialog;
    private int page=1;
    private int visibleLastIndex;
    private int listcount;
    private int adapterCount;
    private boolean isfirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        getDate();
        initView();
        loadNetData();
    }

    private void getDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderSearchBean = (OrderSearchBean) bundle.getSerializable("searchData");
    }

    private void initView() {
        idIgBack.setOnClickListener(this);
        titleText.setText("搜索结果");
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultActivity.this, SearchOrderMainActivity.class);
                intent.putExtra("orderNum", list.get(position).getOrderNum() + "");
                startActivity(intent);
            }
        });

        lvResults.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

                int lastIndex = searchResultAdapter.getCount();        //数据集最后一项的索引
                if (visibleLastIndex == lastIndex&&listcount>adapterCount) {
                    //如果是自动加载,可以在这里放置异步加载数据的代码
                    page++;
                    loadNetData();
                }
                if(visibleLastIndex == lastIndex&&listcount==adapterCount&&isfirst){
                    isfirst=!isfirst;
                    ToastManager.showToastReal("数据已加载完！");
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                visibleLastIndex = firstVisibleItem + visibleItemCount;
                adapterCount = totalItemCount;
            }
        });
    }

    public void showWatiNetDialog() {
        dialog = new LoadingWaitDialog(this);
        dialog.show();
    }

    public void dismissWatiNetDialog() {
        if (dialog != null) {
            dialog.cancel();
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void loadNetData() {
        showWatiNetDialog();
        String url = AppURL.URL_CODE_ORDER_SEARCH_LIST + "tokenKey=" + BaseApplication.getToken() + "&customerID=" + orderSearchBean.getCustomerID() + "&skeyid=" + orderSearchBean.getSkeyid()
                + "&keyword=" + orderSearchBean.getKeyword() + "&sscopeid=" + orderSearchBean.getSscopeid() + "&sdate=" + orderSearchBean.getSdate() + "&edate=" + orderSearchBean.getEdate()
                +"&cpage="+page;
        L.e("url" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                dismissWatiNetDialog();
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    SearchResultResult searchResultResult = new Gson().fromJson(result, SearchResultResult.class);

                    if (searchResultResult.getData() != null) {
                        List<SearchResultResult.DataBean.OrderListBean> templist = searchResultResult.getData().getOrderList();
                        list.addAll(templist);
                    }
                    if (list.size()!=0) {
                        listcount= searchResultResult.getData().getList_count();
                        searchResultAdapter = new SearchResultAdapter(SearchResultActivity.this, list);
                        lvResults.setAdapter(searchResultAdapter);
                    }
                } else if (error == 2) {
                    loginToServer(SearchResultActivity.class);
                } else {
                    ToastManager.showToastReal(OKHttpRequestUtils.getmInstance().getErrorMsg(result));
                }
            }

            @Override
            public void onFail(String fail) {
                dismissWatiNetDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_ig_back:
                finish();
                break;

        }
    }

}
