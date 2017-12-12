package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.ItemDecoration.SpaceItemDecoration;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.RecycleViewPartAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.GetRingPartResult;
import com.qx.mstarstoreapp.manager.FitGridLayoutManager;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.BottmPartPopupWindow;
import com.qx.mstarstoreapp.viewutils.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class MakingActivity extends BaseActivity {


    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.tv_right)
    ImageView tvRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.flybanner)
    FlyBanner flybanner;
    @Bind(R.id.rv_part)
    RecyclerView rvPart;
    @Bind(R.id.tv_finish)
    TextView tvFinish;
    @Bind(R.id.root_view)
    RelativeLayout rootView;
    private FitGridLayoutManager mLayoutManager;
    private BottmPartPopupWindow bottmPartPopupWindow;
    private GetRingPartResult partResult;
    private List<GetRingPartResult.DataBean.ModelItemBean.ModelPicBean> modelPicBeans;
    private List<GetRingPartResult.DataBean.ModelPartsBean> partsList;
    private List<String> countList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making);
        ButterKnife.bind(this);
        bottmPartPopupWindow = new BottmPartPopupWindow(this);
        loadNetData();
    }

    private void initView() {
        if (UIUtils.isScreenChange(this)) {
            mLayoutManager = new FitGridLayoutManager(this, rvPart, 2, GridLayoutManager.VERTICAL, false);
        } else {
            mLayoutManager = new FitGridLayoutManager(this, rvPart, 2, GridLayoutManager.HORIZONTAL, false);
        }
        //头部大图
        modelPicBeans = partResult.getData().getModelItem().getModelPic();
        if (modelPicBeans != null) {
            List list = new ArrayList();
            for (int i = 0; i < modelPicBeans.size(); i++) {
                list.add(modelPicBeans.get(i).getPicm());
            }
            flybanner.setImagesUrl(list);
        }

        //其他part
        rvPart.setLayoutManager(mLayoutManager);
        partsList = partResult.getData().getModelParts();
        countList = partResult.getData().getModelpartCount();
        if(partsList!=null){
            RecycleViewPartAdapter recycleViewPartAdapter = new RecycleViewPartAdapter(partsList,countList, new RecycleViewPartAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    getItemSource(postion);
//                    bottmPartPopupWindow.showPop(rootView);
                }
            });
            //设置item间距，30dp
            rvPart.addItemDecoration(new SpaceItemDecoration(4));
            rvPart.setAdapter(recycleViewPartAdapter);
        }


    }

    private void getItemSource(int postion) {
        baseShowWatLoading();
        String lgUrl = AppURL.URL_GET_PART_SOURCE + "tokenKey=" + BaseApplication.getToken()+"&partSort="+partsList.get(postion).getPartSort();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    partResult = new Gson().fromJson(result, GetRingPartResult.class);
                    if (partResult.getData() == null) {
                        showToastReal("后台数据为空");
                        return;
                    }
                    initView();
                } else if (error.equals("2")) {
                    baseHideWatLoading();
                } else {
                    baseHideWatLoading();
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastWhendebug(message);
                    L.e(message);
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }


        });
    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String lgUrl = AppURL.URL_GET_PART + "tokenKey=" + BaseApplication.getToken();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    partResult = new Gson().fromJson(result, GetRingPartResult.class);
                    if (partResult.getData() == null) {
                        showToastReal("后台数据为空");
                        return;
                    }
                    initView();
                } else if (error.equals("2")) {
                    baseHideWatLoading();
                } else {
                    baseHideWatLoading();
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastWhendebug(message);
                    L.e(message);
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }


        });
    }
}
