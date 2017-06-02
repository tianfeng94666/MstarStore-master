package com.qx.mstarstoreapp.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.ConfirmOrderActivity;
import com.qx.mstarstoreapp.activity.ConfirmStoneOrderActivity;
import com.qx.mstarstoreapp.activity.CustomMadeActivity;
import com.qx.mstarstoreapp.activity.CustomersListActivity;
import com.qx.mstarstoreapp.activity.ModeOfPaymentActivity;
import com.qx.mstarstoreapp.activity.OrderActivity;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.adapter.SendingListAdater;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.BaseFragment;
import com.qx.mstarstoreapp.json.CancleStoneOrderResult;
import com.qx.mstarstoreapp.json.IsHaveCustomerResult;
import com.qx.mstarstoreapp.json.OrderWaitResult;
import com.qx.mstarstoreapp.json.SendingResult;
import com.qx.mstarstoreapp.json.StoneOrderStateResult;
import com.qx.mstarstoreapp.net.ImageLoadOptions;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.CustomGridView;
import com.qx.mstarstoreapp.viewutils.LoadingWaitDialog;
import com.qx.mstarstoreapp.viewutils.MyLinearLayout;
import com.qx.mstarstoreapp.viewutils.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class StoneOrderFragemnt extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private List<StoneOrderStateResult.DataBean.ListBean> listData;
    private BaseAdapter adapter;
    private PullToRefreshView oullRefreshView;
    private static final int PULL_REFRESH = 1;
    private static final int PULL_LOAD = 2;
    private int pullStatus;
    private int cpage = 1;
    private int tempCurpage;
    private int fragType;
    private int listCount = 0;
    private boolean isflag;
    LinearLayout spContent;
    private int imgContainerWidth;
    private final int WAITINGPAY_CODE = 1;
    private final int PAYED_CODE = 2;
    private final int SENDING_CODE = 3;
    private final int FINISHED_CODE = 4;
    private List<SendingResult.DataBean.OrderListBean> sendinglist = new ArrayList<>();
    private String tokenKey;
    private ListView listView;
    private LoadingWaitDialog dialog;
    JsonObject jsonResult;

    public StoneOrderFragemnt(int fragType) {
        this.fragType = fragType;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_layout, null);
        initView(view);
        cpage = 1;
//        loadNetData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            if (jsonResult == null) {
                loadNetData();
            }
        } else {
            //相当于Fragment的onPause
        }
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listview);
        oullRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_refresh_view);
        spContent = (LinearLayout) view.findViewById(R.id.specifiction_content);

        oullRefreshView.setOnHeaderRefreshListener(this);
        oullRefreshView.setOnFooterRefreshListener(this);
        listData = new ArrayList<>();
        switch (fragType) {
            case WAITINGPAY_CODE:
                adapter = new ListViewAdapter();
                listView.setAdapter(adapter);
                break;
            case PAYED_CODE:
                adapter = new ListViewAdapter();
                listView.setAdapter(adapter);
                break;
            case SENDING_CODE:
                if (sendinglist != null) {
                    adapter = new SendingListAdater(getActivity(), sendinglist);
                    listView.setAdapter(adapter);
                }
                break;
            case FINISHED_CODE:
                adapter = new ListViewAdapter();
                listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.e("listView.setOnItemClickListener");
                Intent intent = null;
                Bundle bundle = null;
                switch (fragType) {
                    case WAITINGPAY_CODE:
                        intent = new Intent(getActivity(), ConfirmStoneOrderActivity.class);
                        bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putString("itemId", listData.get(i).getId());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 11);
                        break;
                    case PAYED_CODE:
                        intent = new Intent(getActivity(), ConfirmStoneOrderActivity.class);
                        bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putString("itemId", listData.get(i).getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case SENDING_CODE:
                        intent = new Intent(getActivity(), ConfirmStoneOrderActivity.class);
                        bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putString("itemId", listData.get(i).getId());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 11);
                        break;
                    case FINISHED_CODE:
                        intent = new Intent(getActivity(), ConfirmStoneOrderActivity.class);
                        bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putString("itemId", listData.get(i).getId());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 11);
                        break;
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            loadNetData();
        }
    }


    public void showWatiNetDialog() {
        dialog = new LoadingWaitDialog(getActivity());
        dialog.show();
    }

    public void dismissWatiNetDialog() {
        if (dialog != null) {
            dialog.cancel();
            dialog.dismiss();
            dialog = null;
        }
    }

    private void loadNetData() {
        showWatiNetDialog();
        String url = "";
        tokenKey = BaseApplication.getToken();
        switch (fragType) {
            case WAITINGPAY_CODE:
                url = AppURL.URL_STONE_ORDER_WAIT_PAY + "tokenKey=" + tokenKey + "&cpage=" + cpage;
                break;
            case PAYED_CODE:
                url = AppURL.URL_STONE_ORDER_PAYED + "tokenKey=" + tokenKey + "&cpage=" + cpage;
                break;
            case SENDING_CODE:
                url = AppURL.URL_STONE_ORDER_SENDING + "tokenKey=" + tokenKey + "&cpage=" + cpage;
                break;
            case FINISHED_CODE:
                url = AppURL.URL_STONE_ORDER_FINISH + "tokenKey=" + tokenKey + "&cpage=" + cpage;
                break;
        }

        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(getActivity(), url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                dismissWatiNetDialog();
                L.e("loadNetData  " + result);
                jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                //待审核及生产中 返回结果
                StoneOrderStateResult orderStateResult;
                List<StoneOrderStateResult.DataBean.ListBean> list = null;

                if (error.equals("0")) {
                    switch (fragType) {
                        case WAITINGPAY_CODE:
                            orderStateResult = new Gson().fromJson(result, StoneOrderStateResult.class);
                            if (orderStateResult.getData() == null) {
                                break;
                            }
//                            setBadge(statusCountBean);

                            list = orderStateResult.getData().getList();
                            if (list != null) {
                                listCount = Integer.valueOf(orderStateResult.getData().getList_count());
                            }
                            break;
                        case PAYED_CODE:
                            orderStateResult = new Gson().fromJson(result, StoneOrderStateResult.class);
                            if (orderStateResult.getData() == null) {
                                break;
                            }
//                            setBadge(statusCountBean);

                            list = orderStateResult.getData().getList();
                            if (list != null) {
                                listCount = Integer.valueOf(orderStateResult.getData().getList_count());
                            }
                            break;
                        case SENDING_CODE:
                            orderStateResult = new Gson().fromJson(result, StoneOrderStateResult.class);
                            if (orderStateResult.getData() == null) {
                                break;
                            }
//                            setBadge(statusCountBean);

                            list = orderStateResult.getData().getList();
                            if (list != null) {
                                listCount = Integer.valueOf(orderStateResult.getData().getList_count());
                            }
                            break;
                        case FINISHED_CODE:
                            orderStateResult = new Gson().fromJson(result, StoneOrderStateResult.class);
                            if (orderStateResult.getData() == null) {
                                break;
                            }
//                            setBadge(statusCountBean);

                            list = orderStateResult.getData().getList();
                            if (list != null) {
                                listCount = Integer.valueOf(orderStateResult.getData().getList_count());
                            }
                            break;
                    }

                    if (pullStatus != PULL_LOAD) {
                        listData.clear();
                    }
                    if (list != null) {
                        listData.addAll(list);
                    }
                    endNetRequest();

                } else if (error.equals("2")) {
                    loginToServer(CustomMadeActivity.class);
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastWhendebug(message);
                    L.e(message);
                }
            }

            @Override
            public void onFail(String fail) {
                dismissWatiNetDialog();
            }

        });
    }

    private void setBadge(OrderWaitResult.DataBean.StatusCountBean statusCountBean) {
        if (statusCountBean != null) {
            ((CustomMadeActivity) getActivity()).onFragOrderCount(statusCountBean);
        }
    }

    FragOrderListFragment.OnOderNumberChange onOderNumberChange;

    public interface OnOderNumberChange {
        void onFragOrderCount(OrderWaitResult.DataBean.StatusCountBean statusCountBean);

        void onFragProduCount(int deliverNum);
    }

    public void setOnOderNumberChange(FragOrderListFragment.OnOderNumberChange onOderNumberChange) {
        this.onOderNumberChange = onOderNumberChange;
    }


    public void endNetRequest() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        tempCurpage = cpage;
        if (pullStatus == PULL_LOAD) {
            oullRefreshView.onFooterRefreshComplete();
        }
        if (pullStatus == PULL_REFRESH) {
            oullRefreshView.onHeaderRefreshComplete();
        }
        pullStatus = 0;
    }

    @Override
    public void onFooterRefresh(PullToRefreshView pullToFootRefreshView) {
        L.e("listCount" + listCount + "adapter.getCount " + adapter.getCount());
        if (listCount > adapter.getCount()) {
            tempCurpage = cpage;
            cpage++;
            pullStatus = PULL_LOAD;
            loadNetData();
        } else {
            ToastManager.showToastReal("没有更多数据");
            pullToFootRefreshView.onFooterRefreshComplete();
        }

    }

    @Override
    public void onHeaderRefresh(PullToRefreshView pullToFootRefreshView) {
        tempCurpage = cpage;
        cpage = 1;
        pullStatus = PULL_REFRESH;
        listData.clear();
        sendinglist.clear();
        loadNetData();

    }

    private void cancleOrder(final String orderId) {
        String url = AppURL.URL_STONE_CANCLE_ORDER + "tokenKey=" + BaseApplication.getToken() + "&orderId=" + orderId;
        //keyword=广西|平果&tokenKey=944df2f27ffce557042887589986c193
        L.e("cancleOrdre:" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(getActivity(), url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    CancleStoneOrderResult cancleStoneOrderResult= new Gson().fromJson(result,CancleStoneOrderResult.class);
                    ToastManager.showToastReal(cancleStoneOrderResult.getMessage());
                    for(int i =0 ;i<listData.size();i++){
                        if(listData.get(i).getId().equals(orderId)){
                            listData.remove(i);
                            break;

                        }
                    }
                    adapter.notifyDataSetChanged();
                } else if (error == 2) {
                    loginToServer(OrderActivity.class);
                } else {
                    ToastManager.showToastReal(OKHttpRequestUtils.getmInstance().getErrorMsg(result));
                }
            }

            @Override
            public void onFail(String fail) {

            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listData == null ? 0 : listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.stone_order_state_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvAction.setVisibility(View.GONE);
            switch (fragType) {
                case WAITINGPAY_CODE:
                    viewHolder.tvOrderState.setText("代付款");
                    viewHolder.tvAction.setVisibility(View.VISIBLE);
                    viewHolder.tvCancleOrder.setVisibility(View.VISIBLE);
                    break;
                case PAYED_CODE:
                    viewHolder.tvOrderState.setText("已付款");
                    break;
                case SENDING_CODE:
                    viewHolder.tvOrderState.setText("已发货");
                    break;
                case FINISHED_CODE:
                    viewHolder.tvOrderState.setText("已完成");
                    break;
            }
            final StoneOrderStateResult.DataBean.ListBean listEntity = listData.get(position);
            viewHolder.tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ModeOfPaymentActivity.class);
                    if (!listEntity.getId().equals("")) {
                        intent.putExtra("id", listEntity.getId());
                        intent.putExtra("type", 2);
                        intent.putExtra("isFinish", 1);
                    }
                    startActivity(intent);
                }
            });
            viewHolder.tvCancleOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancleOrder(listEntity.getId());
                }
            });

            viewHolder.tvOrderNumber.setText(listEntity.getOrderNum());
            viewHolder.idCusName.setText(listEntity.getCustomerName());
            viewHolder.idStartDate.setText(listEntity.getOrderDate());
            viewHolder.tvRemark.setText(listEntity.getRemark());
            viewHolder.tvAmount.setText("共" + listEntity.getNumber() + "件 合计 ");
            viewHolder.tvMoney.setText("¥" + listEntity.getTotalPrice());

            return convertView;
        }


        private void addMenuLayout(final List<String> images, LinearLayout layout) {
            for (int i = 0; i < images.size(); i++) {
                final LinearLayout ll = new LinearLayout(getActivity());
                ll.setPadding(20, 18, 20, 18);
                ll.setGravity(Gravity.CENTER_HORIZONTAL);
                ll.setOrientation(LinearLayout.VERTICAL);
                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                final ImageView img = new ImageView(getActivity()) {
                    @Override
                    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                        int widthSize = (UIUtils.getWindowWidth() - 40 * 3 + 20) / 4;
                        setMeasuredDimension(widthSize, widthSize);
                    }
                };
                ImageLoader.getInstance().displayImage(images.get(i), img, ImageLoadOptions.getOptions());
                img.setBackgroundResource(R.drawable.gv_selector);
                if (StringUtils.isEmpty(images.get(i))) {
                    L.e(images.get(i));
                    break;
                }
                ll.addView(img, lp);
                layout.addView(ll);
            }
        }


        public ImageView getImageView(String pic) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundColor(Color.WHITE);
            int padding = UIUtils.dip2px(15);
            imageView.setPadding(padding, padding, padding, padding);
            // txt.setImageResource(getItem(position));
            ImageLoader.getInstance().displayImage(pic, imageView, ImageLoadOptions.getOptions());
            imageView.setBackgroundResource(R.drawable.gv_selector);
            imageView.setClickable(false);
            imageView.setFocusable(false);
            return imageView;
        }


        public class CustomTypeListViewAdapter extends BaseAdapter {

            private List<String> imgFiles;
            private CustomGridView mCustomGridView;

            public CustomTypeListViewAdapter(List<String> pic, CustomGridView customGridView) {
                this.imgFiles = pic;
                this.mCustomGridView = customGridView;
            }


            @Override
            public int getCount() {
                return imgFiles.size();
            }

            @Override
            public String getItem(int position) {
                return imgFiles.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            int width;

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    int horizontalSpace = mCustomGridView.getHorizontalSpacing();
                    int colums = mCustomGridView.getNumColumns();
                    width = (imgContainerWidth - colums * horizontalSpace) / colums;
                    ImageView img = new ImageView(getActivity()) {
                        @Override
                        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                            setMeasuredDimension(width, width);
                        }
                    };
                    convertView = img;
                }
                if (imgFiles.size() != 0)
                    mCustomGridView.setPadding(0, 15, 0, 36);
                else
                    mCustomGridView.setPadding(0, 15, 0, 0);
                ImageLoader.getInstance().displayImage(imgFiles.get(position), (ImageView) convertView, ImageLoadOptions.getOptions());
                //convertView.setDrawingCacheEnabled(false);
                return convertView;
            }

        }

        public class CustomTypeListViewAdapter1 extends CommonAdapter {

            public CustomTypeListViewAdapter1(List<String> mDatas, int itemLayoutId) {
                super(mDatas, itemLayoutId);
            }

            @Override
            public void convert(int position, BaseViewHolder helper, Object item) {
                //TextView textView = helper.getView(R.id.tv_total_amount);
                //view.setText(item);
                helper.setText(R.id.id_tv_title, "");
            }

        }


        class ViewHolder {
            @Bind(R.id.specifiction_content)
            MyLinearLayout specifictionContent;
            @Bind(R.id.tv_situation)
            TextView tvSituation;
            @Bind(R.id.tv_order_number)
            TextView tvOrderNumber;
            @Bind(R.id.tv_order_state)
            TextView tvOrderState;
            @Bind(R.id.tv1)
            TextView tv1;
            @Bind(R.id.id_cus_name)
            TextView idCusName;
            @Bind(R.id.tv2)
            TextView tv2;
            @Bind(R.id.id_start_date)
            TextView idStartDate;
            @Bind(R.id.tv_action)
            TextView tvAction;
            @Bind(R.id.tv3)
            TextView tv3;
            @Bind(R.id.tv_remark)
            TextView tvRemark;
            @Bind(R.id.inner_lny_container)
            LinearLayout innerLnyContainer;
            @Bind(R.id.tv_cancle_order)
            TextView tvCancleOrder;
            @Bind(R.id.tv_amount)
            TextView tvAmount;
            @Bind(R.id.tv_money)
            TextView tvMoney;
            @Bind(R.id.img_container)
            CustomGridView imgContainer;
            @Bind(R.id.id_lay_images)
            LinearLayout idLayImages;
            @Bind(R.id.linear_container_view)
            LinearLayout linearContainerView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
