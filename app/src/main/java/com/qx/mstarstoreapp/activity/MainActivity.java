package com.qx.mstarstoreapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.adapter.MenuRecyclerViewAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.base.MenuItemBean;
import com.qx.mstarstoreapp.bean.Ring;
import com.qx.mstarstoreapp.bean.Type;
import com.qx.mstarstoreapp.greendao.gen.MenuItemBeanDao;
import com.qx.mstarstoreapp.json.CustomerEntity;
import com.qx.mstarstoreapp.json.GetAddressResult;
import com.qx.mstarstoreapp.json.MainPicResult;
import com.qx.mstarstoreapp.json.VersionResult;
import com.qx.mstarstoreapp.manager.FitGridLayoutManager;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.BadgeView;
import com.qx.mstarstoreapp.viewutils.FlyBanner;
import com.qx.mstarstoreapp.viewutils.LeftPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //新主页
    @Bind(R.id.fly_main)
    FlyBanner flyMain;
    @Bind(R.id.iv_home)
    TextView ivHome;
    @Bind(R.id.iv_stone)
    TextView ivStone;
    @Bind(R.id.iv_product)
    TextView ivProduct;
    @Bind(R.id.iv_mine)
    TextView ivMine;
    @Bind(R.id.iv_ring_stone)
    ImageView ivRingStone;
    @Bind(R.id.iv_stone_ring)
    ImageView ivStoneRing;
    @Bind(R.id.ll_main)
    LinearLayout llMain;
    @Bind(R.id.ll_banner)
    LinearLayout llBanner;
    @Bind(R.id.ll_show_less)
    LinearLayout llShowLess;
    @Bind(R.id.iv_make)
    TextView ivMake;
    @Bind(R.id.rv_menu)
    RecyclerView rvMenu;

    private int nowId;
    private String version;
    private VersionResult versionResult;
    private MainPicResult mainPics;
    private List<String> imgesUrl;
    private boolean isShowPic;
    private LeftPopupWindow leftPopupWindow;
    private CustomerEntity isDefaultCustomer;
    private String memberAreaId;
    private MenuItemBeanDao menuItemBeanDao;
    private List<MenuItemBean> menuList;
    private ArrayList<MenuItemBean> chooseMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListener();
    }

    private void addMenu() {
        menuItemBeanDao = BaseApplication.getApplication().getDaoSession().getMenuItemBeanDao();
        menuList = menuItemBeanDao.loadAll();
        getShowMenu(menuList);
        if (menuList != null) {
            FitGridLayoutManager mLayoutManager = new FitGridLayoutManager(this, rvMenu, 5, GridLayoutManager.VERTICAL, true);
            rvMenu.setLayoutManager(mLayoutManager);
            MenuRecyclerViewAdapter menuRecyclerViewAdapter = new MenuRecyclerViewAdapter(this, chooseMenuList);
            rvMenu.setAdapter(menuRecyclerViewAdapter);
        }
    }

    private void getShowMenu(List<MenuItemBean> menuList) {
        if (chooseMenuList == null) {
            chooseMenuList = new ArrayList<MenuItemBean>();
        } else {
            chooseMenuList.clear();
        }

        for (MenuItemBean menuItemBean : menuList) {
            if (menuItemBean.getIshow() == 1) {
                chooseMenuList.add(menuItemBean);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (leftPopupWindow != null) {
            leftPopupWindow.initPopupView();
        }
    }

    private void getAddress() {
        baseShowWatLoading();
        String lgUrl = AppURL.URL_GET_ADDRESS + "tokenKey=" + BaseApplication.getToken();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    GetAddressResult getAddressResult = new Gson().fromJson(result, GetAddressResult.class);
                    if (getAddressResult.getData() == null) {
                        return;
                    }
                    if (Global.ring == null) {
                        Global.ring = new Ring();
                    }
//                    if (getAddressResult.getData().getIsHaveSelectArea() == 0) {
//                        showChooseAearDialog(getAddressResult.getData().getMemberArealist());
//                    }
                    Global.isMainAccount = getAddressResult.getData().getIsMasterAccount();
                    Global.ring.setAddressEntity(getAddressResult.getData().getAddress());
                    isDefaultCustomer = getAddressResult.getData().getDefaultCustomer();
                    if (isDefaultCustomer != null) {
                        Global.ring.setCustomerEntity(isDefaultCustomer);
                    }
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


    /**
     * 显示选择区域对话框
     */
    private void showChooseAearDialog(final List<Type> list) {
        memberAreaId = null;
        View view = View.inflate(this, R.layout.list_itme, null);
        ListView listView = (ListView) view.findViewById(R.id.lv_aear);
        TextView tvComfirm = (TextView) view.findViewById(R.id.tv_comfirm);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        CommonAdapter commonAdapter = new CommonAdapter<Type>(list, R.layout.item_gv_text) {
            @Override
            public void convert(int position, BaseViewHolder helper, Type item) {
                helper.setText(R.id.tv_item_text, list.get(position).getTitle());
            }
        };
        listView.setAdapter(commonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memberAreaId = list.get(position).getId();
            }
        });

        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        tvTitle.setText("请选择该用户所属区域");
        builder.setView(view);
        final Dialog noticeDialog = builder.create();
        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commitAear()) {
                    noticeDialog.dismiss();
                }
            }
        });
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
    }

    private boolean commitAear() {
        boolean isOk = true;
        if (StringUtils.isEmpty(memberAreaId)) {
            showToastReal("所属区域未选择");
            return false;
        }
        String url = AppURL.URL_GET_PLACE_CHANGE + "tokenKey=" + BaseApplication.getToken() + "&memberAreaId=" + memberAreaId;
        L.e(url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                Gson gson = new Gson();
                int error = gson.fromJson(result, JsonObject.class).get("error").getAsInt();
                L.e("error" + error);
                if (error == 0) {
                    L.e("成功");
                    showToastReal("所属区域选择成功");
                }
                if (error == 1) {
                    String message = gson.fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastReal(message);
                    L.e(message);
                }
                if (error == 2) {
                    L.e("重新登录");
                }
                if (error == 3) {
                    L.e("未审核");
                } else {
                    L.e("操作失败");
                }
            }

            @Override
            public void onFail(String fail) {

            }

        });
        return true;
    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String lgUrl = AppURL.URL_GET_HOME_PIC + "tokenKey=" + BaseApplication.getToken();
        L.e("netLogin" + lgUrl);
        VolleyRequestUtils.getInstance().getCookieRequest(this, lgUrl, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    baseHideWatLoading();
                    mainPics = new Gson().fromJson(result, MainPicResult.class);
                    if (mainPics.getData() == null) {
                        ToastManager.showToastReal("获取数据失败！");
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


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initView();
    }


    public int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    "com.qx.mstarstoreapp", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("tage", e.getMessage());
        }
        return verCode;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNetData();
        getAddress();
        addMenu();
        if (Global.isShowPopup != 0) {
            if (leftPopupWindow != null) {
                leftPopupWindow.initPopupView();
            }
        }
    }


    private void initView() {

        if (UIUtils.isScreenChange(this)) {
            imgesUrl = mainPics.getData().getHorizontal();
        } else {
            imgesUrl = mainPics.getData().getVertical();
        }

        flyMain.setImagesUrl(imgesUrl);
        setListener();
        addMenu();
        //设置文字
//        setMustFill(ivHome);
//        setMustFill(ivMake);
//        setMustFill(ivMine);
//        setMustFill(ivProduct);
//        setMustFill(ivStone);
    }

    public void setListener() {
        ivHome.setOnClickListener(this);
        ivMine.setOnClickListener(this);
        ivProduct.setOnClickListener(this);
        ivStone.setOnClickListener(this);
        ivRingStone.setOnClickListener(this);
        ivStoneRing.setOnClickListener(this);
        ivMake.setOnClickListener(this);
        llShowLess.setOnClickListener(this);
        ivProduct.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bundle bundle = new Bundle();
                openActivity(QuickStyleInfromationActivity.class, bundle);
                return false;
            }
        });
    }

    private void setMustFill(TextView tv) {

        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
        builder.setSpan(new BackgroundColorSpan(R.color.main_text_background),
                0, (tv.getText().toString()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(builder);
    }

    public static BadgeView badge1;

    public static void setOnInformationCountClick(OnInformationCountClick onInformationCountClick) {
        int count = onInformationCountClick.getCount();
        if (count == 0) {
            return;
        } else {
//            remind(count, badge1, true);
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                if (Global.isShowPopup != 0) {
                    Global.isShowPopup = 0;
                    if (leftPopupWindow != null) {
                        leftPopupWindow.closePopupWindow();
                        llShowLess.setVisibility(View.GONE);
                    }
                } else {
                    Global.isShowPopup = 2;
                    showMakingRing();
                }
                break;
            case R.id.iv_stone:
                cancleMaking();
                openActivity(StoneSearchInfoActivity.class, null);
                break;
            case R.id.iv_product:
                cancleMaking();
                openActivity(OrderActivity.class, null);
                break;
            case R.id.iv_mine:
                openActivity(SettingActivity.class, null);
                break;
            case R.id.iv_stone_ring:
                openActivity(StoneSearchInfoActivity.class, null);
                Global.isShowPopup = 2;
                break;
            case R.id.iv_ring_stone:
                openActivity(OrderActivity.class, null);
                Global.isShowPopup = 2;
                break;
            case R.id.ll_show_less:
                initPopwindow();
                break;
            case R.id.iv_make:
                openActivity(MakingActivity.class, null);

                break;
        }
    }

    private void cancleMaking() {
        Global.isShowPopup = 0;
        llShowLess.setVisibility(View.GONE);
    }

    private void initPopwindow() {
        if (leftPopupWindow == null) {
            leftPopupWindow = new LeftPopupWindow(this);
        }
        llShowLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftPopupWindow.showPop(flyMain);
            }
        });
    }

    private void showMakingRing() {
//        if (!isShowPic) {
//            llBanner.setAlpha(80);
//            llBanner.setVisibility(View.VISIBLE);
//            ivStoneRing.setVisibility(View.VISIBLE);
//            ivRingStone.setVisibility(View.VISIBLE);
//            isShowPic = true;
//        } else {
//
//            llBanner.setVisibility(View.GONE);
//            ivStoneRing.setVisibility(View.GONE);
//            ivRingStone.setVisibility(View.GONE);
//            isShowPic = false;
//        }

        llShowLess.setVisibility(View.VISIBLE);
        if (leftPopupWindow == null) {
            leftPopupWindow = new LeftPopupWindow(this);
        }
        leftPopupWindow.showPop(flyMain);
    }

    public interface OnInformationCountClick {
        int getCount();
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastManager.showToastReal("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
        }
        return true;
    }
}
