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
import com.qx.mstarstoreapp.activity.CustomMadeActivity;
import com.qx.mstarstoreapp.activity.DeliveryTableActivity;
import com.qx.mstarstoreapp.activity.ProductionListActivity;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.BaseFragment;
import com.qx.mstarstoreapp.json.OrderWaitResult;
import com.qx.mstarstoreapp.net.ImageLoadOptions;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.CustomGridView;
import com.qx.mstarstoreapp.viewutils.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragOrderListFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private List<OrderWaitResult.DataEntity.OrderListEntity.ListEntity> listData;
    private ListViewAdapter adapter;
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
    private final int CHECKING_CODE = 1;
    private final int PRODUCTING_CODE = 2;
    private final int SENDING_CODE = 3;
    private final int FINISHED_CODE = 4;

    public FragOrderListFragment(int fragType) {
        this.fragType = fragType;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_layout, null);
        initView(view);
        cpage = 1;
        loadNetData();
        return view;
    }

    private void initView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.listview);
        oullRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_refresh_view);
        spContent = (LinearLayout) view.findViewById(R.id.specifiction_content);

        oullRefreshView.setOnHeaderRefreshListener(this);
        oullRefreshView.setOnFooterRefreshListener(this);
        listData = new ArrayList<>();
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.e("listView.setOnItemClickListener");
                Intent intent = null;
                Bundle bundle = null;
                switch (fragType) {
                    case CHECKING_CODE:
                        intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                        bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putString("itemId", listData.get(i).getId());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 11);
                        break;
                    case PRODUCTING_CODE:
                        intent = new Intent(getActivity(), ProductionListActivity.class);
                        bundle = new Bundle();
                        bundle.putInt("type", fragType);
                        bundle.putString("orderNum", listData.get(i).getOrderNum());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case SENDING_CODE:
//                        intent = new Intent(getActivity(), DeliveryTableActivity.class);
//                        startActivity(intent);
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


    //    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (getUserVisibleHint() && !isflag) {
//            //LogUtils.e(TAG + "调用onActivityCreated" + indentState);
//           // curpage = 1;
//            showWatiNetDialog();
//            loadNetData();
//        }
//        isflag = true; //仅第一次调用
//    }
//
//    /**
//     * 当前用户看到一个fragment时可以执行一下代码
//     *
//     * @param isVisibleToUser
//     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if (isVisibleToUser && isflag) {
//            // LogUtils.e(TAG+"setUserVisibleHint是否在显示" + indentState);
////            curpage = 1;
//            showWatiNetDialog();
//            loadNetData();
//        }
//        super.setUserVisibleHint(isVisibleToUser);
//    }

    @Override
    public void onResume() {
        super.onResume();
        //cpage=1;
        //loadNetData();
    }


    private void loadNetData() {
        String url = "";
        switch (fragType){
            case CHECKING_CODE:
                url = AppURL.URL_ORDER_WAITCHECK + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + cpage;
                break;
            case PRODUCTING_CODE:
                url = AppURL.URL_ORDER_MODEL_LIST + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + cpage;
                // ModelOrderProduceListPage?tokenKey=10b588002228fa805231a59bb7976bf4&cpage=2
                break;
            case SENDING_CODE:
//                url = AppURL.URL_ORDER_WAITCHECK + "tokenKey=" + BaseApplication.getToken() + "&cpage=" + cpage;
                break;
        }

        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(getActivity(), url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e("loadNetData  " + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    OrderWaitResult orderWaitResult = new Gson().fromJson(result, OrderWaitResult.class);
                    OrderWaitResult.DataEntity.OrderListEntity orderList = orderWaitResult.getData().getOrderList();
                    List<OrderWaitResult.DataEntity.OrderListEntity.ListEntity> list = orderList.getList();
                    if (cpage == 1) {
                        listCount = Integer.valueOf(orderList.getList_count());
                        L.e("fragType " + fragType + "listCount " + listCount);
                        switch (fragType){
                            case CHECKING_CODE:
                                onOderNumberChange.onFragOrderCount(listCount);
                                break;
                            case PRODUCTING_CODE:
                                onOderNumberChange.onFragProduCount(listCount);
                                break;
                            case SENDING_CODE:
//                                onOderNumberChange.onFragProduCount(listCount);
                                break;
                        }

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
            }

        });
    }

    OnOderNumberChange onOderNumberChange;

    public interface OnOderNumberChange {
        void onFragOrderCount(int payNum);

        void onFragProduCount(int deliverNum);
    }

    public void setOnOderNumberChange(OnOderNumberChange onOderNumberChange) {
        this.onOderNumberChange = onOderNumberChange;
    }


    public void endNetRequest() {
        adapter.notifyDataSetChanged();
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
        loadNetData();
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
                convertView = View.inflate(getActivity(), R.layout.adapter_order_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            OrderWaitResult.DataEntity.OrderListEntity.ListEntity listEntity = listData.get(position);
            viewHolder.tvOrderNumber.setText(listEntity.getOrderNum());
            viewHolder.idCusName.setText(listEntity.getCustomerName());
            viewHolder.idStartDate.setText(listEntity.getOrderDate());


            if (fragType == 1) {
                //修改日期
                viewHolder.idEndDate.setText(listEntity.getModifyDate());
            } else if (fragType == 2) {
                //审核日期
                viewHolder.idEndDate.setText(listEntity.getConfirmDate());
            }
            viewHolder.idRemarks.setText(listEntity.getOtherInfo());
            viewHolder.tvTotalAmount.setText("参考总价 " + listEntity.getTotalPrice());
            viewHolder.idTvNeed.setText("定金 " + listEntity.getNeedPayPrice());


            L.e(listEntity.getPicsEntity().size() + "size()");

            viewHolder.layImages.removeAllViews();
            if (listEntity.getPicsEntity() != null && listEntity.getPicsEntity().size() != 0) {
                addMenuLayout(listEntity.getPicsEntity(), viewHolder.layImages);
            }

//
//
//          if ( viewHolder.customListViewAdapter==null){
//              viewHolder.customListViewAdapter = new CustomTypeListViewAdapter(listEntity.getPicsEntity(), viewHolder.customListView);
//          }
//            viewHolder.customListView.setAdapter(viewHolder.customListViewAdapter);
//            viewHolder.customListView.setClickable(false);
//            viewHolder.customListView.setPressed(false);
//            viewHolder.customListView.setEnabled(false);
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

        class ViewHolder {
            @Bind(R.id.tv_order_number)
            TextView tvOrderNumber;
            @Bind(R.id.tv_situation)
            TextView tvSituation;
            @Bind(R.id.id_cus_name)
            TextView idCusName;
            @Bind(R.id.id_start_date)
            TextView idStartDate;
            @Bind(R.id.id_end_date)
            TextView idEndDate;
            @Bind(R.id.id_remarks)
            TextView idRemarks;
            @Bind(R.id.inner_lny_container)
            LinearLayout innerLnyContainer;
            @Bind(R.id.tv_total_amount)
            TextView tvTotalAmount;
            @Bind(R.id.img_container)
            CustomGridView customListView;
            @Bind(R.id.linear_container_view)
            LinearLayout linearContainerView;
            @Bind(R.id.tv_need_amount)
            TextView idTvNeed;
            @Bind(R.id.id_lay_images)
            LinearLayout layImages;
            CustomTypeListViewAdapter customListViewAdapter;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
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
    }
}
