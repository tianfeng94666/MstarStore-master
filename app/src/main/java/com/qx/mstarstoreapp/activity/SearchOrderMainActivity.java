package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.CustomerListRestult;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class SearchOrderMainActivity extends BaseActivity {
    @Bind(R.id.id_et_seach)
    EditText idEtSeach;
    @Bind(R.id.id_view_line)
    View idViewLine;
    @Bind(R.id.ig_btn_seach)
    ImageView igBtnSeach;
    @Bind(R.id.id_lv_custom)
    ListView idLvCustom;
    String keyWord = "";
    SearchOrderMainActivity.CustomersListAdapter customersListAdapter;
    List<CustomerListRestult.DataEntity.ListEntity> madata;
    @Bind(R.id.iv_left)
    ImageView ivLeft;
    private int page = 1;
    private int visibleLastIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_customer);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        keyWord = getIntent().getStringExtra("keyWord");
        madata = new ArrayList<>();
        customersListAdapter = new SearchOrderMainActivity.CustomersListAdapter();
        idLvCustom.setAdapter(customersListAdapter);
        idLvCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idEtSeach.setText(madata.get(i).getCustomerName());
                Intent intent = new Intent();
                intent.putExtra("CustomerName", madata.get(i).getCustomerName());
                intent.putExtra("CustomerID", madata.get(i).getCustomerID());
                setResult(11, intent);
                finish();
            }
        });
        if (!StringUtils.isEmpty("keyWord")) {
            loadNetData(keyWord);
        }
        idLvCustom.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

                int lastIndex = customersListAdapter.getCount();        //数据集最后一项的索引
                if (visibleLastIndex == lastIndex) {
                    //如果是自动加载,可以在这里放置异步加载数据的代码
                    page++;
                    loadNetData(idEtSeach.getText().toString());
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                visibleLastIndex = firstVisibleItem + visibleItemCount;
                System.out.println("firstVisibleItem=" + firstVisibleItem);
                System.out.println("visibleItemCount=" + visibleItemCount);
                System.out.println("totalItemCount=" + totalItemCount);
            }
        });
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadNetData() {

    }

    public void loadNetData(final String keyWord) {
        //GetCustomerList?keyword=湖南|益阳&cpage=1&tokenKey=944df2f27ffce557042887589986c193
        String url = AppURL.URL_CUSTOMER_LIST + "tokenKey=" + BaseApplication.getToken() + "&keyword=" + StringUtils.replaceBlank(keyWord) + "&cpage=" + page;
        L.e("CustomersListActivity" + url);

        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    CustomerListRestult customerListRestult = new Gson().fromJson(result, CustomerListRestult.class);
                    List<CustomerListRestult.DataEntity.ListEntity> datas = customerListRestult.getData().getList();
                    madata.addAll(datas);
                    customersListAdapter.notifyDataSetChanged();
                }
                if (error == 2) {
                    loginToServer(CustomersListActivity.class);
                }
                if (error == 1) {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
            }

            @Override
            public void onFail(String fail) {

            }

        });
    }


    public class CustomersListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return madata.size();
        }

        @Override
        public CustomerListRestult.DataEntity.ListEntity getItem(int i) {
            return madata.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            TextView txt = new TextView(SearchOrderMainActivity.this);
            txt.setBackgroundColor(Color.WHITE);
            int padding = UIUtils.dip2px(15);
            txt.setPadding(padding, padding, padding, padding);
            txt.setText(getItem(position).getCustomerName());

            return txt;
        }
    }
}
