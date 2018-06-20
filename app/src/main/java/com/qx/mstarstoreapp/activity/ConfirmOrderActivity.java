package com.qx.mstarstoreapp.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.Scrollable.CanScrollVerticallyDelegate;
import com.qx.mstarstoreapp.Scrollable.OnFlingOverListener;
import com.qx.mstarstoreapp.Scrollable.OnScrollChangedListener;
import com.qx.mstarstoreapp.Scrollable.ScrollableLayout;
import com.qx.mstarstoreapp.Scrollable.TabsLayout;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.bean.Type;
import com.qx.mstarstoreapp.inter.AdapterCallBack;
import com.qx.mstarstoreapp.inter.ConfirmOrderOnUpdate;
import com.qx.mstarstoreapp.json.AddressEntity;
import com.qx.mstarstoreapp.json.ConfirmOrderResult;
import com.qx.mstarstoreapp.json.CustomerEntity;
import com.qx.mstarstoreapp.json.DefaultValue;
import com.qx.mstarstoreapp.json.IsHaveCustomerResult;
import com.qx.mstarstoreapp.json.OrderListResult;
import com.qx.mstarstoreapp.json.PriceResult;
import com.qx.mstarstoreapp.net.ImageLoadOptions;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.ExcelUtil;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.SpUtils;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.CustomSelectButton;
import com.qx.mstarstoreapp.viewutils.GridViewWithHeaderAndFooter;
import com.qx.mstarstoreapp.viewutils.PullToRefreshView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 * 创建人：Yangshao
 * 创建时间：2016/9/23 15:35
 * @version     确认订单页面
 *
 */
public class ConfirmOrderActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,
        PullToRefreshView.OnHeaderRefreshListener, CanScrollVerticallyDelegate,
        OnFlingOverListener, AdapterCallBack, ConfirmOrderOnUpdate {

    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.ck_checkall)
    CheckBox ckCheckall;
    @Bind(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @Bind(R.id.tv_go_pay)
    TextView tvGoPay;
    @Bind(R.id.layout_rl_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.id_tv1)
    TextView idTv1;
    @Bind(R.id.id_tv_name)
    TextView idTvName;
    @Bind(R.id.id_tv_address)
    TextView idTvAddress;
    @Bind(R.id.id_cs_color)
    CustomSelectButton idPurity;
    @Bind(R.id.id_receipt)
    Button idReceipt;
    @Bind(R.id.bt_qulity)
    CustomSelectButton btQuality;
    @Bind(R.id.pull_refresh_view)
    PullToRefreshView pullRefreshView;
    @Bind(R.id.rel_shopping_car_bottom_action)
    RelativeLayout relShoppingCarBottomAction;
    @Bind(R.id.id_lay_order_detail)
    LinearLayout idLayOrderDetail;
    @Bind(R.id.id_et_seach)
    EditText idEtSeach;
    @Bind(R.id.id_view_line)
    View idViewLine;
    @Bind(R.id.iv_seach_customer)
    ImageView ivSeachCustomer;
    @Bind(R.id.id_rl1)
    RelativeLayout idRl1;
    @Bind(R.id.phone_tv)
    TextView phoneTv;
    @Bind(R.id.lny_loading_layout)
    LinearLayout lnyLoadingLayout;
    @Bind(R.id.id_ed_word)
    EditText idEdWord;
    @Bind(R.id.id_ed_remarks)
    EditText idEdRemarks;
    @Bind(R.id.id_lay_price1)
    LinearLayout idLayPrice1;
    @Bind(R.id.id_tv_need_price)
    TextView idTvNeedPrice;
    @Bind(R.id.id_tv_total_price)
    TextView idTvTotalPrice;
    @Bind(R.id.id_lay_price2)
    LinearLayout idLayPrice2;
    @Bind(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;
    @Bind(R.id.id_cancle_order)
    Button idCancleOrder;
    @Bind(R.id.root_view)
    RelativeLayout rootView;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.tv_pay)
    Button tvPay;
    @Bind(R.id.tv_totalPrice_title)
    TextView tvTotalPriceTitle;
    @Bind(R.id.gv_order)
    GridViewWithHeaderAndFooter lvOrder;

    /*选择成色的ItME*/
    List<OrderListResult.DataEntity.ModelColorEntity> modelColorItme;
    /*选择质量等级的Item*/
    List<OrderListResult.DataEntity.ModelQualityEntity> modelQualityItem;
    List<OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> listData;
    AddressEntity isDefaultAddress;
    CustomerEntity isDefaultCustomer;
    @Bind(R.id.tv_customer)
    TextView tvCustomer;
    @Bind(R.id.tv_quality)
    TextView tvQuality;
    @Bind(R.id.tv_color)
    TextView tvColor;

    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv_delete_batch)
    TextView tvDeleteBatch;
    @Bind(R.id.tv_clear)
    TextView tvClear;
    @Bind(R.id.tv_statistics)
    TextView tvStatistics;
    @Bind(R.id.iv_tab)
    ImageView ivTab;
    @Bind(R.id.iv_scale)
    ImageView ivScale;
    private int tempCurpage = 1;
    private int pullState = 1;
    private int curpage = 1;
    private ConfirOrderAdapter confirOrderAdapter;
    private int type = 0;   //type=2表示审核订单中过来的页面
    private String orderId;
    private boolean isFirst; //记录 是否第一次进入页面
    private int list_count = -1;
    private static final int PULL_REFRESH = 1;
    private static final int PULL_LOAD = 2;
    private ConfirmOrderResult confirmOrderResult;
    private boolean isShowPrice;
    private boolean ischooseEmpty;//是否选择了产品
    private boolean isCustomized;//是否是用户定制
    private DefaultValue defaultValue;
    String purityId, qualityId;//选中的成色id,质量等级id
    List<String> mcheckIds;//选中的产品id集合
    String invTitle, invType;
    private int GET_EXCEL_FILE = 10;
    private int GET_CUSTOMER = 11;
    private int GET_ADDRESS = 12;
    private boolean isEdit = false;//是否是编辑状态
    Map<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> mchecked = new HashMap<>();
    private int SCALETYPE = 0;//0为一行一个1为3个

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
        getActivityType(getIntent());
        initView();
        initScroll();
        loadNetData();

        isFirst = true;

    }

    public int getActivityType(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return 0;
        }
        type = bundle.getInt("type");
        orderId = bundle.getString("itemId");
        dealExcel(intent);
        return type;
    }

    public void setListHeadView(OrderListResult.DataEntity.OrderInfoEntity orderInfo) {
        listHeadView = LayoutInflater.from(this).inflate(R.layout.order_header_view, null);
        TextView orderNum = (TextView) listHeadView.findViewById(R.id.id_order_num);
        TextView orderDate = (TextView) listHeadView.findViewById(R.id.id_order_date);
        TextView orderStatus = (TextView) listHeadView.findViewById(R.id.id_order_status);
        TextView goodprice = (TextView) listHeadView.findViewById(R.id.id_order_goodprice);
        orderNum.setText("订单编号：" + orderInfo.getOrderNum());
        orderDate.setText("下单日期：" + orderInfo.getOrderDate());
        orderStatus.setText("状态：" + orderInfo.getOrderStatus());
        goodprice.setText("金价：" + orderInfo.getGoldPrice());
        lvOrder.setAdapter(null);
        lvOrder.addHeaderView(listHeadView);
        lvOrder.setAdapter(confirOrderAdapter);
    }


    @Override
    public void loadNetData() {
baseShowWatLoading();
        String url = "";
        if (type == 2) {
            url = AppURL.URL_ORDER_DETAIL + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + curpage + "&orderId=" + orderId+"&pageNum=24";
        } else if (type == 0 || type == 1) {
            url = AppURL.URL_ORDER_LIST + "tokenKey=" + BaseApplication.getToken() + "&purityId=" + purityId +
                    "&qualityId=" + qualityId + "&cpage=" + curpage+"&pageNum=24";
        } else if (type == 5) {
            url = AppURL.URL_ORDER_DETAIL + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + curpage + "&orderId=" + orderId+"&pageNum=24";
        } else if (type == 3 || type == 4) {
            url = AppURL.URL_PERSON_GET_ORDER_LIST + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + curpage+"&pageNum=24";
        }

        L.e("获取订单信息" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
//                lnyLoadingLayout.setVisibility(View.GONE);
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    OrderListResult orderListResult = new Gson().fromJson(result, OrderListResult.class);
                    OrderListResult.DataEntity dataEntity = orderListResult.getData();
                    if (dataEntity != null) {
                        OrderListResult.DataEntity.CurrentOrderlListEntity currentOrderlList = dataEntity.getCurrentOrderlList();
                        List<OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> list = currentOrderlList.getList();
                        isDefaultAddress = dataEntity.getAddress();


                        if (pullState != PULL_LOAD) {
                            confirOrderAdapter.cancelAll();
                            listData.clear();
                        }
                        list_count = Integer.valueOf(currentOrderlList.getList_count());
                        if (curpage == 1) {
                            modelColorItme = dataEntity.getModelColor();
                            modelQualityItem = dataEntity.getModelQuality();
                        }
                        if (list_count == 0) {
                            showToastReal("没有数据");
                        } else {
                            listData.addAll(list);
                        }
                        if (isDefaultAddress != null) {
                            idTvName.setText(isDefaultAddress.getName());
                            idTvAddress.setText(isDefaultAddress.getAddr());
                            phoneTv.setText(isDefaultAddress.getPhone());
                        }
                        if (isFirst) {
                            if (type == 2 || type == 5) {
                                OrderListResult.DataEntity.OrderInfoEntity orderInfo = dataEntity.getOrderInfo();
                                Double totalPrice = dataEntity.getTotalPrice();
                                Double totalNeedPayPrice = dataEntity.getTotalNeedPayPrice();
                                L.e("orderInfo" + orderInfo.toString() + "totalPrice:" + totalPrice + "totalNeedPayPrice:" + totalNeedPayPrice);
                                idPurity.setTextName(orderInfo.getPurityName());
                                btQuality.setTextName(orderInfo.getQualityName());
                                idEdWord.setText(orderInfo.getWord());
                                idEdRemarks.setText(orderInfo.getOrderNote());
                                idEtSeach.setText(orderInfo.getCustomerName());
                                tvTotalPrice.setText(totalNeedPayPrice + "--定金   " + totalPrice + "(合计)");
                                idTvNeedPrice.setText("定金 :" + totalNeedPayPrice);
                                idTvTotalPrice.setText("合计 :" + totalPrice);
                                setListHeadView(orderInfo);
                                invTitle = orderInfo.getInvoiceTitle();
                                invType = orderInfo.getInvoiceType();
                                if (!StringUtils.isEmpty(orderInfo.getInvoiceTitle()) && !StringUtils.isEmpty(invType)) {
                                    idReceipt.setText("类型：" + invType + "     抬头：" + invTitle);
                                    L.e("类型：" + invTitle + "     抬头：" + invTitle);
                                }
                                isDefaultCustomer = dataEntity.getCustomer();
                            } else {
                                defaultValue = dataEntity.getDefaultValue();
                                if (defaultValue.getModelColor() != null) {
                                    if (!defaultValue.getModelColor().getId().isEmpty()) {
                                        idPurity.setTextName(defaultValue.getModelColor().getTitle());
                                        purityId = defaultValue.getModelColor().getId();
                                    }
                                }
                                if (defaultValue.getModelQuality() != null) {
                                    if (!defaultValue.getModelQuality().getId().isEmpty()) {
                                        btQuality.setTextName(defaultValue.getModelQuality().getTitle());
                                        qualityId = defaultValue.getModelQuality().getId() + "";
                                    }
                                }
                                isDefaultCustomer = dataEntity.getCustomer();
                                if (isDefaultCustomer != null) {
                                    idEtSeach.setText(isDefaultCustomer.getCustomerName());
                                }
                            }
                            isFirst = false;
                        }
                        if (priceList != null && priceList.size() != 0) {
                            for (int i = 0; i < priceList.size(); i++) {
                                for (int j = 0; j < listData.size(); j++) {
                                    if (listData.get(j).getId().equals(priceList.get(i).getId())) {
                                        listData.get(j).setPrice(priceList.get(i).getPrice() + "");
                                    }
                                }
                            }
                        }
                        if (type == 2 || type == 5) {
                            tvGoPay.setVisibility(View.GONE);
                            tvStatistics.setVisibility(View.GONE);
                            idLayOrderDetail.setVisibility(View.VISIBLE);
                            idLayPrice1.setVisibility(View.GONE);
                            idLayPrice2.setVisibility(View.VISIBLE);
                        } else {
                            tvGoPay.setVisibility(View.VISIBLE);
                            tvStatistics.setVisibility(View.VISIBLE);
                            idLayOrderDetail.setVisibility(View.GONE);
                            idLayPrice1.setVisibility(View.VISIBLE);
                            idLayPrice2.setVisibility(View.GONE);
                        }
                        editOrder();
                        /**
                         * 是否显示价格处理
                         */
                        isShowPrice = SpUtils.getInstace(ConfirmOrderActivity.this).getBoolean("isShowPrice", true);
                        if (isShowPrice) {
                            tvTotalPriceTitle.setVisibility(View.VISIBLE);
                            tvTotalPrice.setVisibility(View.VISIBLE);
                        } else {
                            tvTotalPriceTitle.setVisibility(View.GONE);
                            tvTotalPrice.setVisibility(View.GONE);
                        }
                        L.e("解析成功");
                        //清空是否选中
                        if (type != 2 || type != 5) {
                            confirOrderAdapter.clearCheckedState();
                            mchecked.clear();
                        }
                        endNetRequest();
                        initListener();
                        L.e("mchecked" + mchecked.size());
                        Set<Map.Entry<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity>> entries = mchecked.entrySet();
//                    for (Map.Entry<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> entry : entries) {
//                        OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity entity = entry.getValue();
//                        L.e(entity.toString());
//                    }
                        changeState(mchecked);
                    }

                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }

            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }

        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getActivityType(intent);
        initView();
        initScroll();
        loadNetData();
    }

    private void initListener() {
        if (idPurity != null)
            idPurity.setOnSelectData(new CustomSelectButton.OnselectData() {
                @Override
                public List<Type> getData() {
                    List<Type> list = new ArrayList<>();
                    for (int i = 0; i < modelColorItme.size(); i++) {
                        Type type = new Type();
                        type.setId(modelColorItme.get(i).getId());
                        type.setTypeName(modelColorItme.get(i).getTitle() + "  " + modelColorItme.get(i).getPrice());
                        list.add(type);
                    }
                    return list;
                }

                @Override
                public void getSelectId(Type type) {
                    idPurity.setText(type.getTypeName());
                    purityId = type.getId();
                    queryPricefoServer(qualityId, purityId);
                    showPurityDialog(type.getTypeName());
                }

                @Override
                public String getTitle() {
                    return "选择成色";
                }

                @Override
                public View getRootView() {
                    return View.inflate(context, R.layout.activity_confirmorder, null);
                }
            });


        if (btQuality != null)
            btQuality.setOnSelectData(new CustomSelectButton.OnselectData() {
                @Override
                public List<Type> getData() {
                    List<Type> list = new ArrayList<>();
                    for (int i = 0; i < modelQualityItem.size(); i++) {
                        Type type = new Type();
                        type.setId(modelQualityItem.get(i).getId());
                        type.setTypeName(modelQualityItem.get(i).getTitle());
                        list.add(type);
                    }
                    return list;
                }

                @Override
                public View getRootView() {
                    return View.inflate(context, R.layout.activity_confirmorder, null);
                }

                @Override
                public void getSelectId(Type type) {
                    btQuality.setText(type.getTypeName());
                    qualityId = type.getId();
                    queryPricefoServer(qualityId, purityId);
                }

                @Override
                public String getTitle() {
                    return "选择质量等级";
                }
            });






        /*搜索用户时间  失去焦点*/
        idEtSeach.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!StringUtils.isEmpty(idEtSeach.getText().toString()))
                        seachCustom(idEtSeach.getText().toString());
                }
            }
        });


        idEdWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    updateCustomorWord(idEtSeach.getText().toString(), idEdWord.getText().toString());
                }
            }
        });
         /*确定支付*/
        if (isShowPrice) {
            tvGoPay.setEnabled(true);
        } else {
            tvGoPay.setTextColor(getResources().getColor(R.color.text_color3));
            tvGoPay.setEnabled(false);
        }


    }

    private void GetReceipt() {
        Intent intent = new Intent(ConfirmOrderActivity.this, ReceiptActivity.class);
        if (!StringUtils.isEmpty(invTitle)) {
            intent.putExtra("invTitle", invTitle);
        }
        intent.putExtra("type", type);
        intent.putExtra("invTitle", invType);
        startActivityForResult(intent, 13);
    }

    private void isChooseAll() {
        if (ckCheckall.isChecked()) {
            confirOrderAdapter.selectAll();
        } else {
            confirOrderAdapter.cancelAll();
        }
        confirOrderAdapter.notifyDataSetChanged();
    }

    private void searchCustomers() {
        boolean isFast = UIUtils.isFastDoubleClick();
        if (!isFast) {
            String st = idEtSeach.getText().toString();
            if (st.isEmpty()) {
                Intent intent = new Intent(ConfirmOrderActivity.this, CustomersListActivity.class);
                intent.putExtra("keyWord", st);
                startActivityForResult(intent, GET_CUSTOMER);
            } else {
                seachCustom(idEtSeach.getText().toString());
            }
        }
    }

    private void commitOrder() {
        tvGoPay.setTextColor(getResources().getColor(R.color.white));
        if (ischooseEmpty) {
            submitOrder();
        } else {
            showToastReal("请选择商品！");
        }
    }

    private void goToPay() {
        Intent intent;
        intent = new Intent(ConfirmOrderActivity.this, ModeOfPaymentActivity.class);
        if (!orderId.equals("")) {
            intent.putExtra("id", orderId);
        }
        startActivity(intent);
    }

    private void showPurityDialog(String purity) {
        if (mcheckIds == null || mcheckIds.size() == 0) {
            showDialog("对当前显示的所有订单成色改为" + purity, 1);
        } else {
            showDialog("对当前选中的订单成色改为" + purity, 0);
        }
    }

    private void showDialog(String st, final int j) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请做出选择")
                .setMessage(st)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        changePurity(j);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {// 消极

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        builder.create().show();
    }

    private void changePurity(int j) {
        List<String> changPurityList = new ArrayList<>();
        if (j == 1) {
            for (int i = 0; i < listData.size(); i++) {
                changPurityList.add(listData.get(i).getId());
            }
        } else {
            changPurityList = new ArrayList<>(mcheckIds);
        }
        String ids = StringUtils.purUrlCut("itemIds", changPurityList).toString();
        String url = AppURL.URL_GET_MODELDETAIL_FORSCAN_MODIFY_PURITY + "tokenKey=" + BaseApplication.getToken() + ids + "&purityId=" + purityId;
        L.e(url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    showToastReal(new Gson().fromJson(result, JsonObject.class).get("message").getAsString());
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    /*
    * @version  取消订单
    */
    public void cancalOrder() {
        // ModelOrderWaitCheckCancelDo?orderId=10&tokenKey=10b588002228fa805231a59bb7976bf4
        String url = AppURL.URL_ORDER_WAIT_CANCEL + "tokenKey=" + BaseApplication.getToken() + "&orderId=" + orderId;
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    showToastReal(" 取消成功");
                    setResult(GET_CUSTOMER);
                    finish();
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }


    /*价格查询  等级和 成色的变化都会影响价格*/
    List<PriceResult.DataEntity.PriceListEntity> priceList;

    public void queryPricefoServer(String qualityId, String purityId) {
        String url;
        if (type == 2) {
            //ModelOrderWaitCheckGetOrderPricePageListDo?purityId=1&qualityId=1&orderId=13&tokenKey=10b588002228fa805231a59bb7976bf4
            url = AppURL.URL_WATI_ORDER_PRICE + "tokenKey=" + BaseApplication.getToken() + "&purityId=" + purityId +
                    "&qualityId=" + qualityId + "&orderId=" + orderId;
        } else {
            url = AppURL.URL_ORDER_PRICE + "tokenKey=" + BaseApplication.getToken() + "&purityId=" + purityId +
                    "&qualityId=" + qualityId + "&cpage=" + curpage;
        }
        L.e("path");
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    PriceResult priceResult = new Gson().fromJson(result, PriceResult.class);
                    priceList = priceResult.getData().getPriceList();
                    if (priceList != null && priceList.size() != 0) {
                        for (int i = 0; i < priceList.size(); i++) {
                            for (int j = 0; j < listData.size(); j++) {
                                if (listData.get(j).getId().equals(priceList.get(i).getId())) {
                                    listData.get(j).setPrice(priceList.get(i).getPrice() + "");
                                    listData.get(j).setNeedPayPrice(priceList.get(i).getNeedPayPrice() + "");
                                }
                            }
                        }
                    }
                    endNetRequest();
                    if (type == 2) {
                        Double total = .0;
                        Double needPrice = .0;
                        for (OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity cartItem : listData) {
                            total += Double.valueOf(cartItem.getPrice()) * Double.valueOf(cartItem.getNumber());
                            needPrice += Double.valueOf(cartItem.getNeedPayPrice()) * Double.valueOf(cartItem.getNumber());
                            L.e("needPrice" + needPrice);
                        }
                        //价格
                        tvTotalPrice.setText(StringUtils.formatedPrice(total));
                        idLayPrice1.setVisibility(View.INVISIBLE);
                        idLayPrice2.setVisibility(View.VISIBLE);
                        idTvNeedPrice.setText("定金 :" + needPrice);
                        idTvTotalPrice.setText("合计 :" + total);
                    } else {
                        confirOrderAdapter.updatePrice();
                    }
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }


    public void onBack(View view) {

        Intent intent = new Intent();
        if (type == 2) {
            intent.setClass(this, CustomMadeActivity.class);
            startActivity(intent);
        } else {
            setResult(GET_ADDRESS, intent);
        }

        finish();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(GET_ADDRESS, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    View listHeadView;

    protected void initView() {
        isCustomized = SpUtils.getInstace(this).getBoolean("isCustomized", true);
//        lnyLoadingLayout.setVisibility(View.VISIBLE);
        if (listData == null) {
            listData = new ArrayList<>();
        }
        confirOrderAdapter = new ConfirOrderAdapter();
        confirOrderAdapter.setOnAdapterCallBack(this);
        lvOrder.setEmptyView(findViewById(R.id.lny_no_result));
        lvOrder.setAdapter(confirOrderAdapter);
        pullRefreshView.setOnFooterRefreshListener(this);
        pullRefreshView.setOnHeaderRefreshListener(this);
          /*发票*/
        idReceipt.setText(R.string.write_a_receip);
        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //订单详情有一个头部
                if (lvOrder.getChildCount() > listData.size()) {
                    gotoEdit(position - 1);
                } else {
                    gotoEdit(position);
                }

            }
        });
        if (type == 0) {
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }

        if (type == 2 || type == 5) {
            ckCheckall.setVisibility(View.GONE);
            titleText.setText(R.string.order_detail);
            idCancleOrder.setText(R.string.cancle_order);
        } else {
            String st = " *";
            setMustFill("客       户", st, tvCustomer);
            setMustFill("质量等级", st, tvQuality);
//            setMustFill(st, tvColor);
            titleText.setText(R.string.confirm_order);
        }


        if (isCustomized) {
            SimpleStyleInfromationActivity.setConfirmOrderOnUpdate(this);
        } else {
            StyleInfromationActivity.setConfirmOrderOnUpdate(this);
        }

        ShopingAddressActivity.setOnRefreshOrderListener(new ShopingAddressActivity.OnRefreshOrderListener() {
            @Override
            public void onrefresh() {
                loadNetData();
            }
        });

    }

    private void editOrder() {
        if (isEdit) {
            tvRight.setText("完成");
            tvGoPay.setVisibility(View.GONE);
            tvStatistics.setVisibility(View.GONE);
            tvClear.setVisibility(View.VISIBLE);
            tvDeleteBatch.setVisibility(View.VISIBLE);
        } else {
            tvRight.setText("编辑");
            tvStatistics.setVisibility(View.VISIBLE);
            tvGoPay.setVisibility(View.VISIBLE);
            tvClear.setVisibility(View.GONE);
            tvDeleteBatch.setVisibility(View.GONE);
        }
    }

    private void setMustFill(String first, String st, TextView tv) {

        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString() + st);
        builder.setSpan(new ForegroundColorSpan(Color.RED),
                tv.getText().toString().length(), (st + tv.getText().toString()).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(builder);
    }

    //OrderCurrentSubmitDo?itemId=1|2|3&addressId=1&purityId=3&qualityId=2&tokenKey=944df2f27ffce557042887589986c193

    /*
     * @version  提交订单
     */
    public void submitOrder() {
        baseShowWatLoading();
        String url;
        if (type > 2) {
            url = AppURL.URL_PERSON_ORDER + "&tokenKey=" + BaseApplication.getToken();
        } else {
            url = AppURL.URL_GET_MODELDETAIL_FORSCAN_COMMIT_ORDER + "&tokenKey=" + BaseApplication.getToken();
        }
        String word = idEdWord.getText().toString();
        String remarks = idEdRemarks.getText().toString();
        if (isDefaultAddress == null) {
            ToastManager.showToastReal(getString(R.string.please_write_address));
            return;
        }
        if (isDefaultCustomer == null) {
            ToastManager.showToastReal(getString(R.string.please_write_customer));
            return;
        }

        String addressId = isDefaultAddress.getId();
        String customerID = isDefaultCustomer.getCustomerID() + "";
        String itemurl = "";
        if (mcheckIds == null || mcheckIds.size() == 0) {
            showToastReal(getString(R.string.please_select_order));
        } else {
            itemurl = StringUtils.purUrlCut("itemId", mcheckIds).toString();
        }
        L.e("word" + word + "   addressId" + addressId + "");
        if (customerID.equals("-1")) {
            showToastReal("请填写客户资料");
            return;
        }
        if (StringUtils.isEmpty(word) && StringUtils.isEmpty(addressId) && StringUtils.isEmpty(customerID) && StringUtils.isEmpty(itemurl)) {
            showToastReal("请填写完整资料");
            return;
        }
        url += itemurl + "&word=" + word + "&purityId=" + purityId + "&addressId=" + addressId + "&qualityId=" + qualityId + "&customerID=" + customerID +
                "&invTitle=" + invTitle + "&invType=" + invType + "&orderNote=" + remarks;
        L.e("submitOrder" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                L.e("确定订单" + result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    ToastManager.showToastReal("提交成功");
//                    onUpdate();
                    JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class).get("data").getAsJsonObject();
                    confirmOrderResult = new Gson().fromJson(result, ConfirmOrderResult.class);
                    if (confirmOrderResult.getData() == null) {
                        return;
                    }
                    String id = jsonObject.get("id").getAsString();
                    Global.waitOrderCount = Integer.valueOf(jsonObject.get("waitOrderCount").getAsString());
                    int isCheckErpOrder = confirmOrderResult.getData().getIsCheckErpOrder();
                    //是否需要付款  0不需要   1需要
                    int isNeedPay = jsonObject.get("isNeetPay").getAsInt();
                    //是都已经上传到ERP订单  //1 跳转到生产详情页    0跳转到审核页面
                    int isErpOrder = jsonObject.get("isErpOrder").getAsInt();
                    /**
                     * 后台分单，直接跳转到历史订单
                     */
                    Intent intent = new Intent(ConfirmOrderActivity.this, CustomMadeActivity.class);
                    intent.putExtra("pageNumber", isCheckErpOrder);
                    startActivity(intent);
/**由于分单
 if (isErpOrder == 1) {
 String orderNum = jsonObject.get("orderNum").getAsString();
 ProgressDialog progressDialog = new ProgressDialog(ConfirmOrderActivity.this, orderNum, 1);
 progressDialog.showAsDropDown(rootView);
 // intent.putExtra("pageNumber",1);
 } else {
 // intent.putExtra("pageNumber",0);  //待审核
 Bundle bundle = new Bundle();
 bundle.putInt("type", 2);
 bundle.putString("itemId", id);
 openActivity(ConfirmOrderActivity.class, bundle);
 }
 **/
                } else if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                } else {
                    ToastManager.showToastReal(OKHttpRequestUtils.getmInstance().getErrorMsg(result));
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }
        });
    }


    /*
     * @version  搜索时候有客户
     */
    private void seachCustom(final String keyWord) {
        String url = AppURL.URL_HAVE_CUSTOMER + "tokenKey=" + BaseApplication.getToken() + "&keyword=" + keyWord;
        //keyword=广西|平果&tokenKey=944df2f27ffce557042887589986c193
        L.e("seachCustom" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                    IsHaveCustomerResult isHaveCustomerResult = new Gson().fromJson(result, IsHaveCustomerResult.class);
                    JsonObject jsonObject = jsonResult.get("data").getAsJsonObject();
                    int state = jsonObject.get("state").getAsInt();
                    if (state == 0) {
                        showToastReal("没有此客户");
                        isDefaultCustomer.setCustomerID(-1);
                    }
                    if (state == 1) {
                        showToastReal("有此客户");
                        isDefaultCustomer = isHaveCustomerResult.getData().getCustomer();
                        if (type == 2 || type == 5) {
                            updateCustomorWord(isDefaultCustomer.getCustomerID() + "", idEdWord.getText().toString());
                        } else {
                            idEtSeach.setText(isDefaultCustomer.getCustomerName());
                        }

                    }
                    if (state == 2) {
                        Intent intent = new Intent(ConfirmOrderActivity.this, CustomersListActivity.class);
                        intent.putExtra("keyWord", keyWord);
                        startActivityForResult(intent, GET_CUSTOMER);
                    }
                } else if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                } else {
                    ToastManager.showToastReal(OKHttpRequestUtils.getmInstance().getErrorMsg(result));
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    /*
     * @version  修改客户和自印
     */
    public void updateCustomorWord(String cus, String word) {
        // ModelOrderWaitCheckDetailModifyInfoDo?orderId=10&customerId=2990&tokenKey=10b588002228fa805231a59bb7976bf4
        String url = "";
        if (type != 2) {
            return;
        }
        url = AppURL.URL_WATI_ORDER_MODIINFO + "tokenKey=" + BaseApplication.getToken() + "&orderId=" + orderId +
                "&customerId=" + cus + "&word=" + word;
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                L.e(result);
                if (error == 0) {
                    showToastReal("更新成功");
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }


    @Override
    public void changeState(Map<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> checked) {
        int totalAmount = 0;
        List<OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> checkedGoods = new ArrayList<>();
        if (confirOrderAdapter.isAllSelected()) {
            ckCheckall.setChecked(true);
        } else {
            ckCheckall.setChecked(false);
        }
        mchecked.putAll(checked);
        Set<Map.Entry<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity>> entries = checked.entrySet();
        for (Map.Entry<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> entry : entries) {
            OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity entity = entry.getValue();
            checkedGoods.add(entity);
        }
        Double total = .0;
        for (OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity cartItem : checkedGoods) {
            total += Double.valueOf(cartItem.getPrice()) * Double.valueOf(cartItem.getNumber());
            totalAmount = Integer.parseInt(cartItem.getNumber()) + totalAmount;
        }
        //价格
        tvTotalPrice.setText(StringUtils.formatedPrice(total));
        tvGoPay.setText("确定(" + totalAmount + ")");
        if (checkedGoods.size() == 0) {
            ischooseEmpty = false;
        } else {
            ischooseEmpty = true;
        }
        if (checkedGoods.size() != 0) {
            mcheckIds = new ArrayList<>();
            for (int i = 0; i < checkedGoods.size(); i++) {
                mcheckIds.add(checkedGoods.get(i).getId());
            }
        }
    }


    private void endNetRequest() {
        ckCheckall.setChecked(confirOrderAdapter.isAllSelected());
        //changeState(mchecked);
        confirOrderAdapter.notifyDataSetChanged();
        tempCurpage = curpage;
        if (pullState == PULL_LOAD) {
            pullRefreshView.onFooterRefreshComplete();
        } else if (pullState == PULL_REFRESH) {
            pullRefreshView.onHeaderRefreshComplete();
        }
        pullState = 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e("onActivityResult" + requestCode);
        if (data == null) {
            return;
        }
        if (requestCode == GET_EXCEL_FILE) {
            dealExcel(data);

        }
        if (requestCode == GET_CUSTOMER) {
            String customerName = data.getStringExtra("CustomerName");
            int customerID = data.getIntExtra("CustomerID", -1);
            idEtSeach.setText(customerName);
            if (isDefaultCustomer == null) {
                isDefaultCustomer = new CustomerEntity();
            }
            isDefaultCustomer.setCustomerID(customerID);
            updateCustomorWord(customerID + "", idEdWord.getText().toString());
        }
        if (requestCode == GET_ADDRESS) {
            String phoneNumber = data.getStringExtra("phoneNumber");
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String addressId = data.getStringExtra("addressId");

            /*设置默认地址ID*/
            if (isDefaultAddress == null) {
                isDefaultAddress = new AddressEntity();
            }
            isDefaultAddress.setId(addressId);
            if (type == 2) {
                updatAddress();
            }
            idTvName.setText(name);
            idTvAddress.setText(address);
            phoneTv.setText(phoneNumber);
        }

        if (requestCode == 13) {
            invTitle = data.getStringExtra("invTitle");
            invType = data.getStringExtra("invTypeId");
            int type = data.getIntExtra("type", 1);
            String invTypeTitle = data.getStringExtra("invTypeTitle");
            if (!StringUtils.isEmpty(invTitle) && !StringUtils.isEmpty(invTypeTitle)) {
                idReceipt.setText("类型：" + invTypeTitle + "     抬头：" + invTitle);
            } else {
                invTitle = "";
                invType = "";
                idReceipt.setText("暂不开票");
            }
            if (type == 2) {
                updateInv();
            }
        }
    }

    private void dealExcel(Intent data) {
        Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
        if (uri == null) {
            return;
        }
        String img_path = getPath(uri);
        File file = new File(img_path);
        ArrayList arrayList = ExcelUtil.readExcel(file);
        L.e(arrayList.toString());
        showToastReal(arrayList.toString());

    }


    public String getPath(Uri uri) {
        String path;
        if ("file".equalsIgnoreCase(uri.getScheme() == null ? "" : uri.getScheme())) {//使用第三方应用打开
            path = uri.getPath();
            return path;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            path = getPath(this, uri);
        } else {//4.4以下下系统调用方法
            path = getRealPathFromURI(uri);
        }
        return path;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    private void updatAddress() {
        String url = AppURL.URL_ORDER_ADRESS_CHANGE + "tokenKey=" + BaseApplication.getToken() + "&addressId=" + isDefaultAddress.getId() +
                "&orderId=" + orderId;
        L.e("updateInv" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                L.e(result);
                if (error == 0) {
                    ToastManager.showToastReal("更新成功");
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    public void updateInv() {
        String url = AppURL.URL_WATI_ORDER_MODIINFO + "tokenKey=" + BaseApplication.getToken() + "&invTitle=" + invTitle +
                "&invType=" + invType +
                "&orderId=" + orderId;
        L.e("updateInv" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                L.e(result);
                if (error == 0) {
                    ToastManager.showToastReal("更新成功");
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";
    private ScrollableLayout mScrollableLayout;
    private TabsLayout tabs;

    public void initScroll() {
        final View header = findViewById(R.id.header);
        tabs = (TabsLayout) findViewById(R.id.tabs);
        tabs.setViewPager();

        mScrollableLayout = (ScrollableLayout) findViewById(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);
        mScrollableLayout.setCanScrollVerticallyDelegate(this);
        mScrollableLayout.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {

                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }
                tabs.setTranslationY(tabsTranslationY);
                header.setTranslationY(y / 2);
            }
        });

    }

    @Override
    public boolean canScrollVertically(int direction) {
        boolean isSroll = (lvOrder != null && lvOrder.canScrollVertically(direction));
        if (isSroll) {
            tabs.setImageView(R.drawable.icon_down);
            ivTab.setImageResource(R.drawable.icon_down);
        } else {
            tabs.setImageView(R.drawable.icon_up);
            ivTab.setImageResource(R.drawable.icon_up);
        }
        return isSroll;
    }

    @Override
    public void onFlingOver(int y, long duration) {
        if (lvOrder != null) {
            lvOrder.smoothScrollBy(y, (int) duration);
        }
    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        if (listData.size() < list_count) {
            tempCurpage = curpage;
            curpage++;
            pullState = PULL_LOAD;
            loadNetData();
        } else {
            showToastReal("没有更多数据");
            view.onFooterRefreshComplete();
        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        tempCurpage = curpage;
        curpage = 1;
        pullState = PULL_REFRESH;
        loadNetData();
    }

    @Override
    public void onUpdate() {
        loadNetData();
    }


    public void gotoEdit(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("itemId", listData.get(position).getId());
        if (type == 2) {
            // ModelOrderWaitCheckDetailModifyInfoDo?orderId=10&customerId=2990&tokenKey=10b588002228fa805231a59bb7976bf4
            bundle.putInt("type", type);
            bundle.putString("orderId", orderId);

            if (listData.get(position).getShowPageType().equals("1")) {
                bundle.putInt("type", 5);
                bundle.putString("orderId", orderId);
                openActivity(MakingActivity.class, bundle);
            } else {
                if (isCustomized) {
                    openActivity(SimpleStyleInfromationActivity.class, bundle);
                } else {
                    openActivity(StyleInfromationActivity.class, bundle);
                }
            }
        } else if (type == 0 || type == 1) {
            bundle.putInt("type", 1);
            if (isCustomized) {
                openActivity(SimpleStyleInfromationActivity.class, bundle);
            } else {
                openActivity(StyleInfromationActivity.class, bundle);
            }
        } else if (type == 3 || type == 4) {
            bundle.putInt("type", 4);
            openActivity(MakingActivity.class, bundle);
        }
    }

    @OnClick({R.id.tv_right, R.id.tv_delete_batch, R.id.tv_clear, R.id.iv_delete, R.id.tv_pay, R.id.id_cancle_order, R.id.rl_address,
            R.id.tv_go_pay, R.id.iv_seach_customer, R.id.ck_checkall, R.id.id_receipt, R.id.tv_statistics, R.id.iv_scale, R.id.iv_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                isEdit = !isEdit;
                editOrder();
                break;
            case R.id.tv_delete_batch:
                //批量删除
                deleteBatch();
                break;
            case R.id.tv_clear:
                //清空
                clearOrder();
                break;
            case R.id.iv_delete:
                idEtSeach.setText("");
                break;
            case R.id.tv_pay:
                //去支付
                goToPay();
                break;
            case R.id.id_cancle_order:
                 /*取消订单*/
                cancalOrder();
                break;
            case R.id.rl_address:
                 /*选择地址*/
                chooseAddress();
                break;
            case R.id.tv_go_pay:
                 /*提交订单*/
                commitOrder();
                break;
            case R.id.iv_seach_customer:
                 /*搜索客户*/
                searchCustomers();
                break;
            case R.id.ck_checkall:
                isChooseAll();
                break;
            case R.id.id_receipt:
                //获得发票
                GetReceipt();
                break;
            case R.id.tv_statistics:
                openActivity(StatisticsActivity.class, null);
                break;
            case R.id.iv_scale:
                changeScale();
                break;
            case R.id.iv_tab:

                break;
        }
    }

    private void changeScale() {
        if (SCALETYPE == 1) {
            SCALETYPE=0;
            lvOrder.setNumColumns(1);
            lvOrder.setAdapter(confirOrderAdapter);
            ivScale.setImageResource(R.mipmap.list_detail);

        } else {
            SCALETYPE=1;
            lvOrder.setNumColumns(3);
            lvOrder.setAdapter(confirOrderAdapter);
            ivScale.setImageResource(R.mipmap.list_image);
        }
    }

    private void clearOrder() {
        baseShowWatLoading();
        String url;

        url = AppURL.URL_CLEAR_ORDER + "&tokenKey=" + BaseApplication.getToken();

        L.e("清空订单" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(ConfirmOrderActivity.this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    ToastManager.showToastReal("清空成功");
                    String strwaitOrderCount = new Gson().fromJson(result, JsonObject.class).get("data").getAsJsonObject().get("waitOrderCount").getAsString();
                    Global.waitOrderCount = Integer.valueOf(strwaitOrderCount);
                    listData.clear();
                    confirOrderAdapter.notifyDataSetChanged();
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
                if (error == 1) {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }
        });

    }

    private void deleteBatch() {
        baseShowWatLoading();
        String url;
        String itemurl = "";
        if (mcheckIds == null || mcheckIds.size() == 0) {
            showToastReal(getString(R.string.please_select_order));
        } else {
            itemurl = StringUtils.purUrlCutByComma("itemIds", mcheckIds).toString();
        }
        url = AppURL.URL_DELETE_BATCH + "&tokenKey=" + BaseApplication.getToken() + itemurl;

        L.e("删除成功" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(ConfirmOrderActivity.this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    ToastManager.showToastReal("删除成功");
                    String strwaitOrderCount = new Gson().fromJson(result, JsonObject.class).get("data").getAsJsonObject().get("waitOrderCount").getAsString();
                    Global.waitOrderCount = Integer.valueOf(strwaitOrderCount);
                    for (int j = 0; j < mcheckIds.size(); j++) {
                        for (int i = 0; i < listData.size(); i++) {
                            if (listData.get(i).getId().equals(mcheckIds.get(j))) {
                                listData.remove(listData.get(i));
                                continue;
                            }
                        }
                    }

                    confirOrderAdapter.notifyDataSetChanged();
                }
                if (error == 2) {
                    loginToServer(ConfirmOrderActivity.class);
                }
                if (error == 1) {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
            }
        });

    }

    private void chooseAddress() {
        Intent intent = new Intent(ConfirmOrderActivity.this, AddressListActivity.class);
        intent.putExtra("type", 2);
        startActivityForResult(intent, GET_ADDRESS);
    }

    public class ConfirOrderAdapter extends BaseAdapter {
        public Map<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> checkedState;

        public ConfirOrderAdapter() {
            checkedState = new HashMap<>();
            isShowPrice = SpUtils.getInstace(ConfirmOrderActivity.this).getBoolean("isShowPrice", true);
            // L.e("ConfirOrderAdapter");
        }

        public void clearCheckedState() {
            checkedState.clear();
        }

        public void selectAll() {
            checkedState.clear();
            for (OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity t : listData) {
                checkedState.put(t.getId(), t);
            }
            if (callBack != null)
                callBack.changeState(checkedState);
        }

        public void cancelAll() {
            checkedState.clear();
            if (callBack != null)
                callBack.changeState(checkedState);
        }

        public void updatePrice() {
            if (callBack != null)
                callBack.changeState(checkedState);
        }


        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity getItem(int i) {
            return listData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            final ViewHolder vh;
            if (convertView == null) {
                if (SCALETYPE == 0) {
                    convertView = LayoutInflater.from(ConfirmOrderActivity.this).inflate(R.layout.layout_order, null);
                } else {
                    convertView = LayoutInflater.from(ConfirmOrderActivity.this).inflate(R.layout.layout_order2, null);
                }

                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            //个人定制不能删除
            if (listData.get(position).getShowPageType().equals("1")) {
                vh.btnDelete.setVisibility(View.GONE);
                vh.btnEdit.setVisibility(View.GONE);
                vh.ckCheckone.setVisibility(View.GONE);
            } else {
                vh.btnDelete.setVisibility(View.VISIBLE);
                vh.btnEdit.setVisibility(View.VISIBLE);
                vh.ckCheckone.setVisibility(View.VISIBLE);
            }
            if (type == 2) {
                vh.ckCheckone.setVisibility(View.GONE);
            }

            OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity listEntity = listData.get(position);
            if (checkedState.get(getItem(position).getId()) != null) {
                vh.ckCheckone.setChecked(true);
            } else {
                vh.ckCheckone.setChecked(false);
            }
            vh.ckCheckone.setTag(position);
            vh.ckCheckone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkedState.get(getItem(position).getId()) != null) {
                        checkedState.remove(getItem(position).getId());
                    } else {
                        checkedState.put(getItem(position).getId(), getItem(position));
                    }
                    if (callBack != null)
                        callBack.changeState(checkedState);
                }
            });
            vh.productName.setText(listEntity.getTitle());
            if (isShowPrice) {
                vh.productPrice.setText("¥"+UIUtils.stringChangeToTwoBitDouble(listEntity.getPrice()));
            } else {
                vh.productPrice.setText("");
            }
            vh.idTvInformation.setText(listEntity.getInfo());
            StringBuilder stringBuilder = new StringBuilder();
            if (StringUtils.isEmpty(listEntity.getPurityName())) {
                stringBuilder.append(listEntity.getBaseInfo());
            } else {
                stringBuilder.append(listEntity.getBaseInfo() + " 成色：" + listEntity.getPurityName());
            }
            vh.productNorms.setText(stringBuilder.toString());
            if (SCALETYPE == 0) {
                vh.productNorms.setText(stringBuilder.toString());
            } else {
                ViewGroup.LayoutParams layoutParams =   vh.productImg.getLayoutParams();
                layoutParams.width = UIUtils.getWindowWidth()/3;
                layoutParams.height = UIUtils.getWindowWidth()/3;
                vh.productImg.setLayoutParams(layoutParams);
                vh.productNorms.setText("成色：" + listEntity.getPurityName());
            }
            vh.productNumber.setText(listEntity.getNumber());
            vh.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoEdit(position);
                }
            });
            ImageLoader.getInstance().displayImage(listData.get(position).getPic(), vh.productImg, ImageLoadOptions.getOptions());
            vh.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteOrder(listData.get(position).getId());
                }
            });
            return convertView;
        }


        private AdapterCallBack callBack;

        public void setOnAdapterCallBack(AdapterCallBack callBack) {
            this.callBack = callBack;
        }

        public boolean isAllSelected() {
            return checkedState.size() == listData.size() && listData.size() != 0;
        }

        class ViewHolder {
            @Bind(R.id.ck_checkone)
            CheckBox ckCheckone;
            @Bind(R.id.btn_edit)
            TextView btnEdit;
            @Bind(R.id.btn_delete)
            TextView btnDelete;
            @Bind(R.id.item_button_layout)
            RelativeLayout itemButtonLayout;
            @Bind(R.id.line)
            View line;
            @Bind(R.id.product_img)
            ImageView productImg;
            @Bind(R.id.product_name)
            TextView productName;
            @Bind(R.id.product_norms)
            TextView productNorms;
            @Bind(R.id.product_price)
            TextView productPrice;
            @Bind(R.id.product_number)
            TextView productNumber;
            @Bind(R.id.id_oder_type)
            TextView idOderType;
            @Bind(R.id.id_lay1)
            LinearLayout idLay1;
            @Bind(R.id.id_tv_information)
            TextView idTvInformation;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


        /*
         * @version    删除订单
         *
         */
        public void deleteOrder(final String id) {
            baseShowWatLoading();
            String url;
            if (type == 2) {
                if (listData.size() <= 1) {
                    showToastReal("请点击取消订单");
                    return;
                }
                url = AppURL.URL_ORDER_WAIT_DELETE + "&tokenKey=" + BaseApplication.getToken() + "&itemId=" + id;
            } else {
                url = AppURL.URL_ORDER_DELETE + "&tokenKey=" + BaseApplication.getToken() + "&itemId=" + id;
            }
            L.e("删除订单" + url);
            VolleyRequestUtils.getInstance().getCookieRequest(ConfirmOrderActivity.this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
                @Override
                public void onSuccess(String result) {
                    baseHideWatLoading();
                    L.e(result);
                    int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                    if (error == 0) {
                        ToastManager.showToastReal("删除成功");
                        String strwaitOrderCount = new Gson().fromJson(result, JsonObject.class).get("data").getAsJsonObject().get("waitOrderCount").getAsString();
                        Global.waitOrderCount = Integer.valueOf(strwaitOrderCount);
                        deleteItem(id);
                    }
                    if (error == 2) {
                        loginToServer(ConfirmOrderActivity.class);
                    }
                    if (error == 1) {
                        String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                        L.e(message);
                        ToastManager.showToastReal(message);
                    }
                }

                @Override
                public void onFail(String fail) {
                    baseHideWatLoading();
                }
            });
        }
    }

    private void deleteItem(String id) {
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getId().equals(id)) {
                listData.remove(listData.get(i));
            }
        }
        confirOrderAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.requestQueue.cancelAll(this);
    }
}
