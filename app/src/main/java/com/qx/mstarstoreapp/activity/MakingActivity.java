package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.ItemDecoration.SpaceItemDecoration;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.RecycleViewPartAdapter;
import com.qx.mstarstoreapp.adapter.RecycleViewPartListAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.json.GetPartListResult;
import com.qx.mstarstoreapp.json.GetRingPartResult;
import com.qx.mstarstoreapp.json.ModelPartsBean;
import com.qx.mstarstoreapp.json.ModelPicBean;
import com.qx.mstarstoreapp.json.StoneEntity;
import com.qx.mstarstoreapp.json.StoneSearchInfoResult;
import com.qx.mstarstoreapp.manager.FitGridLayoutManager;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.BottmPartPopupWindow;
import com.qx.mstarstoreapp.viewutils.CustomselectStringButton;
import com.qx.mstarstoreapp.viewutils.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.tv_choose_handsize)
    CustomselectStringButton tvChooseHandsize;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.ll_after_finish)
    LinearLayout llAfterFinish;


    private FitGridLayoutManager mLayoutManager;
    private BottmPartPopupWindow bottmPartPopupWindow;
    private GetRingPartResult getRingPartResult;
    private List<ModelPicBean> modelPicBeans;
    private List<ModelPartsBean> partsList;
    private List<String> countList;
    private GetPartListResult getPartListResult;
    private List<ModelPartsBean> partList;//弹窗中的组件list
    private String[] selectPids;
    private RecycleViewPartAdapter recycleViewPartAdapter;
    private boolean isScreenLand;
    private String makeProductId;
    private TranslateAnimation mHiddenAction;
    private String handsize;
    private String itemId;//编辑的订单Id
    private int type=3;//打开类型 3直接打开 4编辑过来的 5待审核过来编辑
    private Bundle extras;//记录打开类型


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_making);
        ButterKnife.bind(this);
        bottmPartPopupWindow = new BottmPartPopupWindow(this);
        getData(getIntent());

    }

    private void getData(Intent intent) {
         extras = intent.getExtras();
        if (extras != null) {
            itemId = extras.getString("itemId");
            type = extras.getInt("type");
        }
        init();
        loadNetData();
    }

    private void init() {
        if (type == 4||type==5) {
            tvBuy.setText("确定");
        } else if (type == 3) {
            tvBuy.setText("购买");
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData(intent);
    }

    private void initView() {


        idRelTitle.setBackgroundResource(R.color.white);
        isScreenLand = UIUtils.isScreenChange(this);
        if (isScreenLand) {
            mLayoutManager = new FitGridLayoutManager(this, rvPart, 2, GridLayoutManager.VERTICAL, false);
            mLayoutManager.setAutoMeasureEnabled(true);
        } else {
            mLayoutManager = new FitGridLayoutManager(this, rvPart, 2, GridLayoutManager.HORIZONTAL, false);
            mLayoutManager.setAutoMeasureEnabled(true);
        }
        //头部大图
        modelPicBeans = getRingPartResult.getData().getModelItem().getModelPic();
        setFlaybanner(modelPicBeans);
        //其他part
        rvPart.setLayoutManager(mLayoutManager);
        partsList = getRingPartResult.getData().getModelParts();
        countList = getRingPartResult.getData().getModelpartCount();
        selectPids = new String[countList.size()];
        if (partsList != null) {
            recycleViewPartAdapter = new RecycleViewPartAdapter(partsList, countList, new RecycleViewPartAdapter.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
//                    llAfterFinish.startAnimation(mHiddenAction);
                    llAfterFinish.setVisibility(View.GONE);
                    tvFinish.setVisibility(View.VISIBLE);
                    getItemSource(postion);
                }
            });
            //设置item间距，30dp
            rvPart.addItemDecoration(new SpaceItemDecoration(4));
            rvPart.setAdapter(recycleViewPartAdapter);
            //选择手寸
            if (type != 3) {
                tvChooseHandsize.setDefaultText(getRingPartResult.getData().getModelItem().getHandSize());
                handsize=getRingPartResult.getData().getModelItem().getHandSize();
            } else {
                tvChooseHandsize.setDefaultText("选择手寸");
            }
            tvChooseHandsize.setOnSelectData(new CustomselectStringButton.OnselectData() {
                @Override
                public List<String> getData() {
                    return getRingPartResult.getData().getHandSizeData();
                }

                @Override
                public void getSelectId(String type) {
                    handsize = type;
                }

                @Override
                public int defaultPosition() {
                    return 22;
                }

                @Override
                public String getTitle() {
                    return "选择手寸";
                }

                @Override
                public View getRootView() {
                    return rootView;
                }
            });
        }


    }

    private void setFlaybanner(List<ModelPicBean> modelPicBeans) {
        if (modelPicBeans != null) {
            List list = new ArrayList();
            for (int i = 0; i < modelPicBeans.size(); i++) {
                list.add(modelPicBeans.get(i).getPicm());
            }
            flybanner.setImagesUrl(list);
        }
    }

    private void getItemSource(final int postion) {
        baseShowWatLoading();
        String lgUrl = AppURL.URL_GET_PART_SOURCE + "tokenKey=" + BaseApplication.getToken() + "&partSort=" + partsList.get(postion).getPartSort() + "&selectPids=" + getSelectPids();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    getPartListResult = new Gson().fromJson(result, GetPartListResult.class);
                    if (getPartListResult.getData() == null) {
                        showToastReal("后台数据为空");
                        return;
                    }
                    partList = getPartListResult.getData().getList();
                    bottmPartPopupWindow.setData(partList, new RecycleViewPartListAdapter.PartListItemClickListener() {
                                @Override
                                public void onItemClick(View view, int postion) {
                                    bottmPartPopupWindow.setChoosePosition(postion);

                                }
                            },
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int selectPosition = bottmPartPopupWindow.getChoosePosition();
                                    if (selectPosition == -1) {
                                        return;
                                    }
                                    selectPids[postion] = partList.get(selectPosition).getPid();
                                    partsList.set(postion, partList.get(selectPosition));
                                    ModelPartsBean.SelectProItemBean selectProItem = partList.get(selectPosition).getSelectProItem();
                                    makeProductId = "";
                                    if (selectProItem != null) {
                                        setFlaybanner(selectProItem.getModelPic());
                                        makeProductId = selectProItem.getId();
                                    }

                                    bottmPartPopupWindow.closePopupWindow();
                                    countList.clear();
                                    countList.addAll(partList.get(selectPosition).getModelPartCount());
                                    recycleViewPartAdapter.notifyDataSetChanged();

                                }
                            }, isScreenLand ? rvPart : rootView);

                } else if (error.equals("2")) {
                    baseHideWatLoading();
                    loginToServer(LoginActivity.class);
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

    private String getSelectPids() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectPids.length; i++) {
            if (i != (selectPids.length - 1)) {
                sb.append((selectPids[i] == null ? "" : selectPids[i]) + ",");
            } else {
                sb.append(selectPids[i] == null ? "" : selectPids[i]);
            }
        }
        return sb.toString();
    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String lgUrl = "";
        if (type == 3) {
            lgUrl = AppURL.URL_GET_PART + "tokenKey=" + BaseApplication.getToken();
        } else if (type == 4) {
            lgUrl = AppURL.URL_PERSON_ORDER_INFOMATION + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId;
        } else if (type == 5) {
            lgUrl = AppURL.URL_PERSON_ORDER_FROM_WAIT_TO_INFOMATION + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId;
        }

        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    getRingPartResult = new Gson().fromJson(result, GetRingPartResult.class);
                    if (getRingPartResult.getData() == null) {
                        showToastReal("后台数据为空");
                        return;
                    }
                  makeProductId = getRingPartResult.getData().getModelItem().getId();
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


    @OnClick({R.id.tv_finish, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                if (StringUtils.isEmpty(makeProductId)) {
                    showToastReal("请将部件选全");
                } else {
//                    tvFinish.startAnimation(mHiddenAction);
                    tvFinish.setVisibility(View.GONE);
                    llAfterFinish.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_buy:
                addMakingOrder();
                break;
        }
    }

    private void addMakingOrder() {
        if (StringUtils.isEmpty(handsize)) {
            showToastReal("手寸选择错误");
            return;
        }
        String url = "";
        if (type == 3) {
            url = AppURL.URL_PERSON_ADD_ORDER + "tokenKey=" + BaseApplication.getToken() + "&productId=" + makeProductId +
                    "&number=" + "1" + "&handSize=" + handsize;
        } else if (type == 4) {
            //确认编辑
            url = AppURL.URL_PERSON_EDIT_ORDER + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId +
                    "&number=" + "1" + "&handSize=" + handsize+"&productId=" + makeProductId;
        } else if (type == 5) {
            //确认编辑
            url = AppURL.URL_PERSON_ORDER_FROM_WAIT_TO_INFOMATION_EDIT + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId +
                    "&number=" + "1" + "&handSize=" + handsize+"&productId=" + makeProductId;
        }
        L.e("url= " + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e("提交订单 result" + result);
                if (new Gson().fromJson(result, JsonObject.class).get("error").getAsString().equals("0")) {
                    if (type == 3) {
                        ToastManager.showToastReal("添加成功");
                    } else if (type == 4) {

                    } else if (type == 5) {

                    }
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", type);
                        openActivity(ConfirmOrderActivity.class, bundle);
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);

                }

            }

            @Override
            public void onFail(String fail) {
                showToastReal("数据获取失败");

            }

        });
    }


}
