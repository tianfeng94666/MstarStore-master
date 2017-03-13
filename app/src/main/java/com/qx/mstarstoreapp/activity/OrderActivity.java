package com.qx.mstarstoreapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.MyAction;
import com.qx.mstarstoreapp.dialog.GridMenuDialog;
import com.qx.mstarstoreapp.inter.ClassifyOnSeachListener;
import com.qx.mstarstoreapp.inter.OnSeachListener;
import com.qx.mstarstoreapp.json.ClassTypeFilerEntity;
import com.qx.mstarstoreapp.json.ModeListResult;
import com.qx.mstarstoreapp.json.SearchValue;
import com.qx.mstarstoreapp.json.TypeFiler;
import com.qx.mstarstoreapp.net.ImageLoadOptions;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.BadgeView;
import com.qx.mstarstoreapp.viewutils.GridViewWithHeaderAndFooter;
import com.qx.mstarstoreapp.viewutils.ListMenuDialog;
import com.qx.mstarstoreapp.viewutils.LoadingWaitDialog;
import com.qx.mstarstoreapp.viewutils.PullToRefreshView;
import com.qx.mstarstoreapp.viewutils.SideFilterDialog;
import com.qx.mstarstoreapp.viewutils.SquareImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zxing.activity.CaptureActivity;

/*
 * 创建人：Yangshao
 * 创建时间：2016/10/13 11:04
 * @version    
 *    
 */
public class OrderActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {


    private LinearLayout layAllOrder, layFilter, layGvFileter, layout1;
    private GridViewWithHeaderAndFooter mCustomGridView;
    private TextView tvCclassify, tvCurentOrder, id_tv_select;
    private Context context;
    private ImageView igNor, igNor1;
    private RelativeLayout layout2, root_view;
    private SideFilterDialog filterDialog;
    private ListMenuDialog listMenuDialog;
    private PullToRefreshView mRefreshView;
    private GridMenuDialog gridMenuDialog;
    private List<ModeListResult.DataEntity.ModelEntity.ModelListEntity> data = new ArrayList<>();
    /*推荐款  最新款*/


    private static final int PULL_REFRESH = 1;
    private static final int PULL_LOAD = 2;
    private int tempCurpage = 1;
    private int pullState = 1;
    private int curpage = 1;
    private int list_count;
    @Bind(R.id.id_et_seach)
    EditText idEtSeach;
    @Bind(R.id.ig_btn_seach)
    ImageView idIgSeach;
    @Bind(R.id.id_tv_filter)
    TextView idTvFilter;
    @Bind(R.id.id_his_order)
    TextView idTvHisOrder;
    @Bind(R.id.id_classify)
    TextView idTvClassify;
    @Bind(R.id.id_tv_curorder)
    TextView idTvCurOrder;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    /*搜索过的多选历史记录*/
    public static List<TypeFiler> multiselectKey = new ArrayList<>();
    /*搜索过的单选历史记录*/
    public static List<SearchValue> singleKey = new ArrayList<>();

    private int waitOrderCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        context = this;
        initView();
        initListener();
        loadNetData(getInitUrl());
    }


    private void remind(int count) {
        boolean isVisible = false;
        if (count != 0) {
            isVisible = true;
        }
        //BadgeView的具体使用
        badge.setText(count + ""); // 需要显示的提醒类容
        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge.setTextColor(Color.WHITE); // 文本颜色
        int hint = Color.rgb(200, 39, 73);
        badge.setBadgeBackgroundColor(hint); // 提醒信息的背景颜色，自己设置
        badge.setTextSize(10); // 文本大小
        badge.setBadgeMargin(3, 3); // 水平和竖直方向的间距
        badge.setBadgeMargin(5); //各边间隔
        if (isVisible) {
            badge.show();// 只有显示
        } else {
            badge.hide();//影藏显示
        }
    }

    public void onBack(View view) {
        finish();
    }

    private String getInitUrl() {
        String url = AppURL.URL_MODE_LIST + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + curpage;
        return url;
    }

    private void initListMenuDialog(List<ModeListResult.DataEntity.CustomList> customList) {
        listMenuDialog = new ListMenuDialog(OrderActivity.this, customList);
        listMenuDialog.setOnListMenuSelectCloseClick(new ListMenuDialog.OnListMenuSelectCloseClick() {
            @Override
            public void onClose() {
                L.e("onClose");
                backgroundAlpha(1f);
                igNor.setImageResource(R.drawable.icon_list_nor);
            }

            @Override
            public void onSelect(final ModeListResult.DataEntity.CustomList select) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        id_tv_select.setText(StringUtils.idgui(select.getTitle()));
                        mcategory = select.getId() + "";
                        String url = getInitUrl();
                        url += "&category=" + mcategory;
                        loadNetData(url);
                    }
                });
            }
        });
    }


    private void initFilterDialog(List<ClassTypeFilerEntity> typeList) {
        filterDialog = new SideFilterDialog(context, typeList, MyAction.filterDialogAction, getStatusBarHeight());
         /*筛选界面      关闭事件*/
        filterDialog.setOnListMenuSelectCloseClick(new SideFilterDialog.OnListMenuSelectCloseClick() {
            @Override
            public void onClose() {
                L.e("关闭  filterDialog");
                backgroundAlpha(1f);
            }
        });

        /*筛选界面      确认搜索事件*/
        filterDialog.setOnSeachListener(new ClassifyOnSeachListener() {
            @Override
            public void onSeach(String action) {
                myAction = action;
                curpage = 1;
                String url = getInitUrl();
                url += getCheckBoxUrl();
                url += getRadioGroupUrl();
                loadNetData(url);
            }
        });

    }

    LoadingWaitDialog dialog;

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

    }

    private void loadNetData(String url) {
        showWatiNetDialog();
        L.e("开启搜索" + url);
        // 进行登录请求
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                dismissWatiNetDialog();
                L.e("loadNetData  " + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    ModeListResult modeListResult = new Gson().fromJson(result, ModeListResult.class);
                    ModeListResult.DataEntity dataEntity = modeListResult.getData();
                    ModeListResult.DataEntity.ModelEntity modeEntity = dataEntity.getMode();
                    if (curpage == 1) {
                          /*搜索过的单选历史记录*/
                        ClassifyActivity.singleKey = dataEntity.getSearchKeyword();
                        singleKey = dataEntity.getSearchKeyword();
                        /*搜索过的多选历史记录*/
                        multiselectKey = dataEntity.getCategoryFiler();
                        waitOrderCount = Integer.valueOf(dataEntity.getWaitOrderCount());
                        if (waitOrderCount != 0) {
                            remind(waitOrderCount);
                        }
                    }
                    //下啦分类筛选
                    List<ModeListResult.DataEntity.CustomList> customList = dataEntity.getCustomList();
                    if (customList != null && customList.size() != 0) {
                        initListMenuDialog(customList);
                    }

                    List<ClassTypeFilerEntity> typeList = dataEntity.getTypeList();
                    if (typeList != null && typeList.size() != 0) {
                        initFilterDialog(typeList);
                    }
                    if (pullState != PULL_LOAD) {
                        data.clear();
                    }
                    list_count = Integer.valueOf(modeEntity.getList_count());
                    if (list_count == 0) {
                        data.clear();
                    } else {
                        List<ModeListResult.DataEntity.ModelEntity.ModelListEntity> modelList = modeEntity.getModelList();
                        data.addAll(modelList);
                    }
                    endNetRequest();
                } else if (error.equals("2")) {
                    L.e("重新登录");
                    ToastManager.showToastReal(jsonResult.get("message").getAsString());
                    loginToServer(OrderActivity.class);
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
            }

            @Override
            public void onFail(String fail) {
                dismissWatiNetDialog();
            }

        });


    }


    private void endNetRequest() {
        mGvAdapter.notifyDataSetChanged();
        tempCurpage = curpage;
        if (pullState == PULL_LOAD) {
            mRefreshView.onFooterRefreshComplete();
        } else if (pullState == PULL_REFRESH) {
            mRefreshView.onHeaderRefreshComplete();
        }
        pullState = 0;
    }

    View loadStateView;
    TextView hint_txt;

    public void initView() {
        mRefreshView = (PullToRefreshView) findViewById(R.id.pull_refresh_view);
        mRefreshView.setOnHeaderRefreshListener(this);
        mRefreshView.setOnFooterRefreshListener(this);
        mRefreshView.setVisibility(View.VISIBLE);
        igNor = (ImageView) findViewById(R.id.id_ig_nor);
        igNor1 = (ImageView) findViewById(R.id.id_ig_nor1);
        layAllOrder = (LinearLayout) findViewById(R.id.id_ly_all);
        layGvFileter = (LinearLayout) findViewById(R.id.id_gv_fileter);
        id_tv_select = (TextView) findViewById(R.id.id_tv_select);
        root_view = (RelativeLayout) findViewById(R.id.root_view);
        layout1 = (LinearLayout) findViewById(R.id.id_rel2);
        layout2 = (RelativeLayout) findViewById(R.id.id_rel3);
        //筛选
        layFilter = (LinearLayout) findViewById(R.id.id_ly_filter);
        tvCclassify = (TextView) findViewById(R.id.id_tv_classify);
        tvCurentOrder = (TextView) findViewById(R.id.id_cur_order);
        mCustomGridView = (GridViewWithHeaderAndFooter) findViewById(R.id.id_gv_menu);
        loadStateView = View.inflate(this, R.layout.grid_food_layout, null);
        hint_txt = (TextView) loadStateView.findViewById(R.id.tv_hint_txt);
        //mCustomGridView.addFooterView(loadStateView);
        //没有数据显示
        mCustomGridView.setEmptyView(findViewById(R.id.lny_no_result));
        mCustomGridView.setAdapter(mGvAdapter);

        badge = new BadgeView(OrderActivity.this, idTvCurOrder);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        //remind(1,badge1,true);

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idEtSeach.setText("");
            }
        });
        idEtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener(){



            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if(arg1 == EditorInfo.IME_ACTION_SEARCH)
                {
                    curpage = 1;
                    String url = getInitUrl();
                    url += getkeyWordUrl();
                    loadNetData(url);
                    // search pressed and perform your functionality.
                }
                return false;
            }

        });
    }

    BadgeView badge;

    String mcategory;   /*下啦筛选关键字*/
    String myAction;   /*判断是哪个页面的action*/

    public String getCheckBoxUrl() {
        List<TypeFiler> filterList;
        if (myAction.equals(MyAction.classifyActivityAction)) {
            filterList = ClassifyActivity.hisCategoryFilterList;
        } else {
            filterList = OrderActivity.multiselectKey;
        }
        List<String> list = new ArrayList<>();
        int count = filterList.size();
        for (int i = 0; i < count; i++) {
            TypeFiler typeFiler = filterList.get(i);
            list.add(typeFiler.getGroupKey());
            // L.e(""+typeFiler.toString());
        }
        HashSet<String> hs = new HashSet<>(list); //此时已经去掉重复的数据保存在hashset中
        Iterator<String> iterator = hs.iterator();
        StringBuffer sbbuf = new StringBuffer();
        while (iterator.hasNext()) {
            StringBuffer sb = new StringBuffer();
            String tag = iterator.next();
            sb.append("&" + tag + "=");
            for (int i = 0; i < count; i++) {
                TypeFiler typeFiler = filterList.get(i);
                if (typeFiler.getGroupKey().equals(tag)) {
                    sb.append(typeFiler.getValue() + "|");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sbbuf.append(sb.toString());
        }
        L.e("typeFiler  check" + sbbuf.toString());
        return sbbuf.toString();
    }

    public String getRadioGroupUrl() {
        List<SearchValue> keywordList;
        if (myAction.equals(MyAction.classifyActivityAction)) {
            keywordList = ClassifyActivity.singleKey;
        } else {
            keywordList = singleKey;
        }
        String low, hig;
        StringBuilder sbPrice = new StringBuilder();
        if (keywordList != null && keywordList.size() != 0) {
            for (SearchValue hisKey : keywordList) {
                if (!StringUtils.isEmpty(hisKey.getValue())) {
                    sbPrice.append("&" + hisKey.getName() + "=" + hisKey.getValue());
                } else {
                    low = hisKey.getLow();
                    hig = hisKey.getHig();
                    if (!StringUtils.isEmpty(low) && !StringUtils.isEmpty(hig)) {
                        sbPrice.append("&" + hisKey.getName() + "=" + low + "|" + hig);
                    } else if (!StringUtils.isEmpty(low) && StringUtils.isEmpty(hig)) {
                        sbPrice.append("&" + hisKey.getName() + "=" + low + "|");
                    } else if (StringUtils.isEmpty(low) && !StringUtils.isEmpty(hig)) {
                        sbPrice.append("&" + hisKey.getName() + "=" + "|" + hig);
                    }
                }
            }
            L.e(sbPrice.toString());
        }
        L.e("typeFiler  radio" + sbPrice.toString());
        return sbPrice.toString();
    }

    /**
     * 关键字
     * @return
     */
    public String getkeyWordUrl() {
        String url = "";
        String keyWord = idEtSeach.getText().toString();
        if (!StringUtils.isEmpty(keyWord)) {
            keyWord = StringUtils.removeSpacesUrl(keyWord);
            idTvFilter.setVisibility(View.VISIBLE);
            idTvFilter.setText(keyWord);
        }
        url = "&keyword=" + keyWord;
        return url;
    }


    private void initListener() {
        /*分类界面的确认搜索*/
        ClassifyActivity.setOnClassifySeachListener(new ClassifyOnSeachListener() {
            @Override
            public void onSeach(String action) {
                myAction = action;
                curpage = 1;
                String url = getInitUrl();
                url += getCheckBoxUrl();
                url += getRadioGroupUrl();
                loadNetData(url);
            }
        });

        /*弹出筛选页面dialog*/
        layFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterDialog != null) {
                    backgroundAlpha(0.7f);
                    filterDialog.showAsDropDown(root_view, getStatusBarHeight());
                }

            }
        });


        /*弹出下拉别表搜索事件*/
        layAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listMenuDialog != null) {
                    backgroundAlpha(0.7f);
                    listMenuDialog.showAsDropDown(layout1);
                    igNor.setImageResource(R.drawable.icon_list);
                }
            }
        });


        mCustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data.get(position).getId();
                Intent intent = new Intent(OrderActivity.this, StyleInfromationActivity.class);
                Bundle pBundle = new Bundle();
                L.e("itemId" + data.get(position).getId());
                pBundle.putString("itemId", data.get(position).getId());
                pBundle.putInt("type", 0);
                pBundle.putInt("waitOrderCount", waitOrderCount);
                intent.putExtras(pBundle);
                // openActivity(StyleInfromationActivity.class, pBundle);
                startActivityForResult(intent, 10);
            }
        });


        tvCurentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // openActivity(CustommadeInformationActivity.class, null);
            }
        });

        /*弹出 已被选中的标签dialog  GridView*/
        layGvFileter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridMenuDialog = new GridMenuDialog(OrderActivity.this);
                gridMenuDialog.setOnListMenuSelectCloseClick(new GridMenuDialog.OnListMenuSelectCloseClick() {
                    @Override
                    public void onClose() {
                        L.e("onClose");
                        backgroundAlpha(1f);
                        igNor1.setImageResource(R.drawable.icon_list_nor);
                    }

                    @Override
                    public void onSelect(final String select) {
                        L.e("当前选择" + select);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                id_tv_select.setText(select);
                            }
                        });
                    }
                });
                gridMenuDialog.setOnSeachListener(new OnSeachListener() {
                    @Override
                    public void onSeach(String seachUrl) {
                        String url = getInitUrl();
                        url += seachUrl;
                        loadNetData(url);
                    }
                });

                backgroundAlpha(0.7f);
                gridMenuDialog.showAsDropDown(layout2);
                igNor1.setImageResource(R.drawable.icon_list);
            }
        });

        /*开始搜索事件*/
        idIgSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idEtSeach.getText().toString().equals("")){
                    ToastManager.showToastReal("搜索内容为空！");
                    return;
                }
                curpage = 1;
                String url = getInitUrl();
                url += getkeyWordUrl();
                loadNetData(url);
            }
        });

        /*关键字标签清空*/
        idTvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idTvFilter.setVisibility(View.GONE);
                idTvFilter.setText("");
                idEtSeach.setText("");
                loadNetData(getInitUrl());
            }
        });



        /*历史订单*/
        idTvHisOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent = new Intent(getActivity(), CustomMadeActivity.class);
                // startActivity(intent);
                openActivity(CustomMadeActivity.class, null);
            }
        });


        idTvCurOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, ConfirmOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("waitOrderCount", waitOrderCount);
                intent.putExtras(bundle);
                startActivityForResult(intent, 10);
                //openActivity(ConfirmOrderActivity.class,null);
            }
        });

        idTvClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ClassifyActivity.class, null);
            }
        });

    }


    private BaseAdapter mGvAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(OrderActivity.this).inflate(R.layout.adapter_goods_list, parent, false);
                holder.lay = (LinearLayout) convertView.findViewById(R.id.img_container);
                holder.tv = (TextView) convertView.findViewById(R.id.name);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.ig = (SquareImageView) convertView.findViewById(R.id.product_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // holder.ig.setImageResource(R.drawable.no_image);
            holder.tv.setText(data.get(position).getTitle());
            holder.tvPrice.setText(data.get(position).getPrice());
            if (!data.get(position).getPic().equals(holder.ig.getTag())) {
                // 如果不相同，就加载。改变闪烁的情况
                ImageLoader.getInstance().displayImage(data.get(position).getPic(), holder.ig, ImageLoadOptions.getOptions());
                holder.ig.setTag(data.get(position).getPic());
            }
            return convertView;
        }

        class ViewHolder {
            LinearLayout lay;
            SquareImageView ig;
            TextView tv;
            TextView tvPrice;
        }
    };


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        if (data.size() < list_count) {
            tempCurpage = curpage;
            curpage++;
            pullState = PULL_LOAD;
            loadNetData(getInitUrl());
        } else {
            hint_txt.setText("已加载全部数据喲");
            ToastManager.showToastReal("没有更多数据");
            view.onFooterRefreshComplete();
        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        tempCurpage = curpage;
        curpage = 1;
        pullState = PULL_REFRESH;
        hint_txt.setText("上拉加载更多");
        loadNetData(getInitUrl());
    }


    /*扫描二维码页面*/
    public void scan(View view) {
        Intent inten = new Intent(OrderActivity.this, CaptureActivity.class);
        startActivityForResult(inten, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(requestCode + "");
        if (requestCode == 0 && data != null) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            idEtSeach.setText(result);
            String url = getInitUrl();
            url += getkeyWordUrl();
            loadNetData(url);
            L.e("onActivityResult result" + result);
        }

        if (requestCode == 10 && data != null) {
            Bundle bundle = data.getExtras();
            waitOrderCount = bundle.getInt("waitOrderCount");
            remind(waitOrderCount);
            L.e("waitOrderCount");
        }
    }

}
