package com.qx.mstarstoreapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
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
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.bean.Type;
import com.qx.mstarstoreapp.inter.ConfirmOrderOnUpdate;
import com.qx.mstarstoreapp.json.ModelDetailResult;
import com.qx.mstarstoreapp.json.StoneEntity;
import com.qx.mstarstoreapp.net.ImageLoadOptions;
import com.qx.mstarstoreapp.net.OKHttpRequestUtils;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.BadgeView;
import com.qx.mstarstoreapp.viewutils.CustomLV;
import com.qx.mstarstoreapp.viewutils.CustomSelectButton;
import com.qx.mstarstoreapp.viewutils.CustomSelectInput;
import com.qx.mstarstoreapp.viewutils.MyGridView;
import com.qx.mstarstoreapp.viewutils.SelectDotView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * 创建人：Yangshao
 * 创建时间：2016/10/19 9:37
 * @version  款式信息
 *
 */
public class StyleInfromationActivity extends BaseActivity implements View.OnClickListener {


    List<StoneEntity> stoneEntities = new ArrayList<>();
    StoneEntity stone;
    ModelDetailResult.DataEntity.ModelEntity.StoneAEntity stoneA;
    ModelDetailResult.DataEntity.ModelEntity.StoneBEntity stoneB;
    ModelDetailResult.DataEntity.ModelEntity.StoneCEntity stoneC;

    List<ModelDetailResult.DataEntity.StoneTypeEntity> stoneTypeItme; //类型
    List<ModelDetailResult.DataEntity.StoneColorEntity> stoneColorItme;  //颜色
    List<ModelDetailResult.DataEntity.StonePurityEntity> stonePurityItme; //净度
    List<ModelDetailResult.DataEntity.StoneSpecEntity> stoneSpecItme;  //规格
    List<ModelDetailResult.DataEntity.StoneShapeEntity> stoneShapeItem;  //形状
    List<ModelDetailResult.DataEntity.ModelEntity.PicsEntity> pics;
    ModelDetailResult.DataEntity.ModelEntity modelEntity;
    List<ModelDetailResult.DataEntity.RemarksEntity> remarksEntity;//备注
    Type remar = new Type();  //提交的

    @Bind(R.id.id_list)
    CustomLV listView;
    ListAdapter adapter;
    LinearLayout lymenus;
    @Bind(R.id.id_store_title)
    TextView idStoreTitle;
    @Bind(R.id.id_store_information)
    TextView idStoreInformation;
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;

    @Bind(R.id.id_menus)
    LinearLayout idMenus;
    @Bind(R.id.id_cus_store_type)
    CustomSelectButton idCusStoreType;
    @Bind(R.id.id_tv_curorder)
    TextView idTvCurorder;
    @Bind(R.id.id_tv_add_order)
    TextView idTvAddOrder;
    @Bind(R.id.id_cus_store_number)
    EditText idCusStoreNumber;
    @Bind(R.id.id_cus_store_size)
    EditText idCusStoreSize;
    String categoryTitle, storeNumber, storeSize, categoryId;
    @Bind(R.id.id_cus_store_remarkid)
    CustomSelectButton idCusStoreRemarkid;
    @Bind(R.id.id_tv_store_remarks)
    EditText idTvStoreRemarks;
    @Bind(R.id.id_gv_image)
    MyGridView idGvImage;
    @Bind(R.id.lny_loading_layout)
    LinearLayout lnyLoadingLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.indicator_tv)
    TextView indicatorTV;
    @Bind(R.id.index_product_images_indicator)
    LinearLayout newsDotsContainer;

    @Bind(R.id.id_vipage_content)
    RelativeLayout id_vipage_content;

    int type = 0;
    String orderId;
    int waitOrderCount;
    String itemId;
    static ConfirmOrderOnUpdate confirmOrderOnUpdate;

    public static void setConfirmOrderOnUpdate(ConfirmOrderOnUpdate confirmOrderOnUpdate) {
        StyleInfromationActivity.confirmOrderOnUpdate = confirmOrderOnUpdate;
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindwoState();
        setContentView(R.layout.activity_style_information);
        ButterKnife.bind(this);
        getIntentData();
        initView();
        loadNetData();
    }



    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     */
    private void initWindwoState() {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        itemId = extras.getString("itemId");
        type = extras.getInt("type");
        orderId = extras.getString("orderId");
        waitOrderCount = extras.getInt("waitOrderCount", 0);

    }


    private void initView() {
        // titleText.setText("款式信息");
        lnyLoadingLayout.setVisibility(View.VISIBLE);
        idTvAddOrder.setText(R.string.add_curent_order);
        idTvCurorder.setText(R.string.look_current_order);

        if (type == 1 || type == 2) {
            idTvAddOrder.setText(R.string.confirm_update);
            idTvCurorder.setText(R.string.cancle_update);
        }
        lymenus = (LinearLayout) findViewById(R.id.id_menus);
        idTvAddOrder.setOnClickListener(this);
        adapter = new ListAdapter();
        listView.setAdapter(adapter);
        badge = new BadgeView(StyleInfromationActivity.this, idTvCurorder);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        remind(waitOrderCount);

        idIgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("waitOrderCount", waitOrderCount);
                setResult(10, intent);
                finish();
            }
        });
    }


    BadgeView badge;
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
        badge.setBadgeMargin(0, 15); // 水平和竖直方向的间距+-
        if (isVisible) {
            badge.show();// 只有显示
        } else {
            badge.hide();//影藏显示
        }
    }

    @Override
    public   void loadNetData() {
        String url;
        if (type == 1) {
            url = AppURL.URL_MODEL_UPDATE + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId;
        } else if (type == 2) {
            url = AppURL.URL_ORDER_MODEL_UPDATE + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId;
        } else {
            url = AppURL.URL_MODEL_DETAIL + "tokenKey=" + BaseApplication.getToken() + "&id=" + itemId;
            //款号页面
        }
        L.e("获取款号" + url + "Type   " + type);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                stoneEntities.clear();
                lnyLoadingLayout.setVisibility(View.GONE);
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    ModelDetailResult modelDetail = new Gson().fromJson(result, ModelDetailResult.class);
                    ModelDetailResult.DataEntity dataEntity = modelDetail.getData();
                    List<ModelDetailResult.DataEntity.GoldenPriceEntity> goldenPrice = dataEntity.getGoldenPrice();
                    remarksEntity = dataEntity.getRemarks();
                    modelEntity = dataEntity.getModel();
                    categoryTitle = modelEntity.getCategoryTitle();
                    categoryId = modelEntity.getCategoryId();
                    /*是否自带主石*/

                    //L.e("服务器返回是否自带主石头"+isSelfStone);
                    /*得到数量*/
                    String number =modelEntity.getNumber();
                    String remark=modelEntity.getRemark();
                    String handSize=modelEntity.getHandSize();

                    pics = modelEntity.getPics();
                    stone = modelEntity.getStone();
                    stoneA = modelEntity.getStoneA();
                    stoneB = modelEntity.getStoneB();
                    stoneC = modelEntity.getStoneC();
                    stone.setIsSelfStone(modelEntity.getIsSelfStone());
                    if(modelEntity.getIsSelfStone()==1){
                        stone.setChecked(true);
                    }
                    if(stoneA.getStoneOut().equals("1")){
                        stoneA.setChecked(true);
                        stoneA.setIsSelfStone(modelEntity.getIsSelfStone());
                    }
                    if(stoneB.getStoneOut().equals("1")){
                        stoneB.setChecked(true);
                        stoneB.setIsSelfStone(modelEntity.getIsSelfStone());
                    }
                    if(stoneC.getStoneOut().equals("1")){
                        stoneC.setChecked(true);
                        stoneC.setIsSelfStone(modelEntity.getIsSelfStone());
                    }
                    stoneEntities.add(stone);
                    stoneEntities.add(stoneA);
                    stoneEntities.add(stoneB);
                    stoneEntities.add(stoneC);
                    stoneTypeItme = dataEntity.getStoneType();
                    stoneColorItme = dataEntity.getStoneColor();
                    stonePurityItme = dataEntity.getStonePurity();
                    stoneSpecItme = dataEntity.getStoneSpec();
                    stoneShapeItem = dataEntity.getStoneShape();

                    setStorePrice(stone);
                    setStorePrice(stoneA);
                    setStorePrice(stoneB);
                    setStorePrice(stoneC);
                    L.e("categoryTitle" + categoryTitle);
                    idStoreTitle.setText(modelEntity.getTitle());
                    idCusStoreType.setTextName(categoryTitle);
                    if (!StringUtils.isEmpty(number)){
                        idCusStoreNumber.setText(""+number);
                    }
                    idCusStoreSize.setText(handSize);
                    // initView();
                    //layoutBtns();
                    //initGvImage();
                    initViewPager();
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < goldenPrice.size(); i++) {
                        sb.append(goldenPrice.get(i).getTitle() + " " + goldenPrice.get(i).getPrice() + "      ");
                    }
                    idStoreInformation.setText(sb.toString());
                    adapter.notifyDataSetChanged();
                } else if (error == 2) {
                    ToastManager.showToastReal(getString(R.string.data_error));
                } else {
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



    String[] getPics;
    int currentTab;
    SelectDotView newsDots;

    public void initViewPager() {
        newsDots = new SelectDotView(this);
        newsDots.setDotNum(pics.size());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
        newsDotsContainer.removeAllViews();
        newsDotsContainer.addView(newsDots, lp);
        final List<View> list = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(this);
        /**
         * 创建多个item （每一条viewPager都是一个item） 从服务器获取完数据（图片url地址） 后，再设置适配器
         */
        getPics = new String[pics.size()];
        for (int i = 0; i < pics.size(); i++) {
            getPics[i] = pics.get(i).getPicb();
            View item = inflater.inflate(R.layout.item_product_viewpager, null);
            list.add(item);
        }
        // 创建适配器， 把组装完的组件传递进去
        MyAdapter adapter = new MyAdapter(list);
        viewPager.setAdapter(adapter);
        //currentTab = pics.size() * SCALE / 2;
        currentTab=0;
        viewPager.setCurrentItem(currentTab, false);
        indicatorTV.setVisibility(View.VISIBLE);
        indicatorTV.setText("1" + "/" + list.size());

//        FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) viewPager.getLayoutParams(); // 取控件mGrid当前的布局参数
//        L.e("getWindowHight"+ UIUtils.getWindowHight()/2);
//        linearParams.height = UIUtils.dip2px(UIUtils.getWindowHight()/2);// 当控件的高强制设成50象素
//        viewPager.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) id_vipage_content.getLayoutParams(); // 取控件mGrid当前的布局参数
        L.e("getWindowHight"+ UIUtils.getWindowHight()/2);
        if (UIUtils.getWindowHight()>1500){
            linearParams.height = UIUtils.dip2px(600);// 当控件的高强制设成50象素
        }else {
            linearParams.height = UIUtils.dip2px(350);// 当控件的高强制设成50象素
        }
        linearParams.height = UIUtils.getWindowWidth();// 当控件的高强制设成50象素
        id_vipage_content.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicatorTV.setText(position + 1 + "/" + list.size());
                currentTab = position;
                newsDots.setSelectedDot(currentTab % list.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * 适配器，负责装配 、销毁 数据 和 组件 。
     */
    private class MyAdapter extends PagerAdapter {
        private List<View> mList;

        public MyAdapter(List<View> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mList.get(position);
            ImageView image = ((ImageView) view.findViewById(R.id.image));
            if (image == null) {
                return mList.get(position);
            }
            if (pics == null) {
                return mList.get(position);
            }
            ImageLoader.getInstance().displayImage(pics.get(position).getPicm(), image, ImageLoadOptions.getOptions());
            image.getHeight();
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pics.size() == 0 && StringUtils.isEmpty(pics.get(0).getPicb())) {
                        return;
                    }
                    //主页图片
                    Intent intent = new Intent(StyleInfromationActivity.this,
                            ImageBrowserActivity.class);
                    intent.putExtra("photos", getPics);
                    L.e("size:" + getPics.length);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            container.removeView(mList.get(position));
            container.addView(mList.get(position));
            return mList.get(position);
        }

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tv_add_order:
                chkeckConfirmOrder();
                break;
        }
    }
    /*提交订单之前检查是否允许提交*/
    private void chkeckConfirmOrder() {
        storeNumber = idCusStoreNumber.getText().toString();
        storeSize = idCusStoreSize.getText().toString();
        L.e("storeNumber" + storeNumber + "storeSize" + storeSize + "categoryTitle" + categoryTitle);
        boolean isConfir = false;
        boolean isConfirA = false;
        boolean isConfirB = false;
        boolean isConfirC = false;
        if (isNumber(storeNumber)) {
            /*自带主石头*/
            if(!stone.isChecked()&&!checkUpPass(stone)||stone.isChecked()){
                isConfir=true;
            }
            if (!stoneA.isChecked()&&!checkUpPass(stoneA)||stoneA.isChecked()){
                isConfirA=true;
            }
            if (!stoneB.isChecked()&&!checkUpPass(stoneB)||stoneB.isChecked()){
                isConfirB=true;
            }
            if (!stoneC.isChecked()&&!checkUpPass(stoneC)||stoneC.isChecked()){
                isConfirC=true;
            }
            /*是否可以提交*/
            if (isConfir&&isConfirA&&isConfirB&&isConfirC) {
                addCurentOrder();
                idTvAddOrder.setEnabled(false);
            } else {
                ToastManager.showToastReal(getString(R.string.please_fill_data));
            }

        } else {
            ToastManager.showToastReal("件数只许整形或者0.5");
        }

    }



    /**提交订单
     *
     * **/
    private void addCurentOrder() {
        // OrderDoCurrentModelItemDo?productId=1&categoryId=8&number=2&handSize=3&stone=1|3|2|3|4|5&stoneA=1|2|2|3|4|5&
        // stoneB=1|2|3|3|4|5&stoneC=1|2|3|3|4|6&tokenKey=10b588002228fa805231a59bb7976bf4
        String url = "";
        String urlStroe = objectisEmptyAndtoJson("stone", stone);
        String urlStroeA = objectisEmptyAndtoJson("stoneA", stoneA);
        String urlStroeB = objectisEmptyAndtoJson("stoneB", stoneB);
        String urlStroeC = objectisEmptyAndtoJson("stoneC", stoneC);
        if (type == 1) {
            url = AppURL.URL_CURRENT_EDIT_ORDER + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId + "&number=" + storeNumber
                    + "&handSize=" + storeSize + urlStroe + urlStroeA + urlStroeB + urlStroeC + "&isSelfStone=" + stone.getIsSelfStone()+"";
        } else if (type == 2) {
            url = AppURL.URL_UPDATE_ORDER_WATET + "tokenKey=" + BaseApplication.getToken() + "&itemId=" + itemId + "&number=" + storeNumber
                    + "&handSize=" + storeSize + urlStroe + urlStroeA + urlStroeB + urlStroeC + "&isSelfStone=" + stone.getIsSelfStone();
        } else {
            url = AppURL.URL_CURRENT_ORDER + "tokenKey=" + BaseApplication.getToken() + "&productId=" + itemId + "&categoryId=" + categoryId +
                    "&number=" + storeNumber + "&handSize=" + storeSize + urlStroe + urlStroeA + urlStroeB + urlStroeC + "&isSelfStone=" + stone.getIsSelfStone();
        }
        L.e("提交订单" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e("提交订单 result" + result);
                if (new Gson().fromJson(result, JsonObject.class).get("error").getAsString().equals("0")) {
                    idTvAddOrder.setEnabled(true);
                    if (type == 1 || type == 2) {
                        ToastManager.showToastReal("修改成功");
                        if (confirmOrderOnUpdate != null) {
                            confirmOrderOnUpdate.onUpdate();
                            finish();
                        }
                    } else {
                        String strwaitOrderCount = new Gson().fromJson(result, JsonObject.class).get("data").getAsJsonObject().get("waitOrderCount").getAsString();
                        waitOrderCount = Integer.valueOf(strwaitOrderCount);
                        L.e("waitOrderCount" + waitOrderCount);
                        ToastManager.showToastReal("添加成功");
                        remind(waitOrderCount);
                        idTvAddOrder.setEnabled(true);
                        return;
                    }
                    //finish();
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    ToastManager.showToastReal(message);
                    idTvAddOrder.setEnabled(true);
                }
            }

            @Override
            public void onFail(String fail) {

            }

        });
    }




    /**
     * 验证输入 不通过 返回true 要么全空要么全不为空
     * */
    public boolean checkUpPass(StoneEntity stoEntity) {
        boolean isUpPass=true;
        //全部为空
        if (StringUtils.isEmpty(stoEntity.getNumber()) &&
                StringUtils.isEmpty(stoEntity.getShapeId()) && StringUtils.isEmpty(stoEntity.getPurityTitle())
                && StringUtils.isEmpty(stoEntity.getColorId()) && StringUtils.isEmpty(stoEntity.getTypeId())
                && StringUtils.isEmpty(stoEntity.getSpecTitle())) {
            isUpPass= false;
        }
        if (!StringUtils.isEmpty(stoEntity.getNumber()) &&
                !StringUtils.isEmpty(stoEntity.getShapeId()) && !StringUtils.isEmpty(stoEntity.getPurityTitle())
                && !StringUtils.isEmpty(stoEntity.getColorId()) && !StringUtils.isEmpty(stoEntity.getTypeId())
                && !StringUtils.isEmpty(stoEntity.getSpecTitle())) {
            isUpPass= false;
        }

        if (!isUpPass){
            L.e( stoEntity.toString()+"通过验证");
        }else {
            L.e( stoEntity.toString()+"不通过验证");
        }

        return isUpPass;
    }


    /*验证数字是否是整形和0.5*/
    public boolean isNumber(String storeNumber){
        if(!StringUtils.isEmpty(storeNumber)){
            if(!storeNumber.equals("0.5")&&!(storeNumber.indexOf(".")==-1)){
                return false;
            }
        }
        return true;
    }

    /**
     * 将选择的数据转化为JSON字符串
     * *
     * */
    public String objectisEmptyAndtoJson(String key, StoneEntity stoEntity) {
        List list = new ArrayList();
        //如果是主石
        if (stoEntity.isChecked()&&!stoEntity.getStroneName().equals("主 石")){
            for (int i=0;i<7;i++){
                list.add("");
                if (i==6){
                    list.add(stoEntity.getIsSelfStone()+"");
                }
            }
        }else if (stoEntity.isChecked()&&stoEntity.getStroneName().equals("主 石")){
            list.add(stoEntity.getTypeId());
            list.add(stoEntity.getSpecTitle());
            list.add(stoEntity.getShapeId());
            list.add(stoEntity.getColorId());
            list.add(stoEntity.getPurityId());
            list.add(stoEntity.getNumber());
        }else {
            if (!StringUtils.isEmpty(stoEntity.getNumber()) &&
                    !StringUtils.isEmpty(stoEntity.getPurityId()) && !StringUtils.isEmpty(stoEntity.getColorId())
                    && !StringUtils.isEmpty(stoEntity.getTypeId()) && !StringUtils.isEmpty(stoEntity.getSpecTitle())
                    && !StringUtils.isEmpty(stoEntity.getShapeId())) {
                list.add(stoEntity.getTypeId());
                list.add(stoEntity.getSpecTitle());
                list.add(stoEntity.getShapeId());
                list.add(stoEntity.getColorId());
                list.add(stoEntity.getPurityId());
                list.add(stoEntity.getNumber());
                if (!stoEntity.getStroneName().equals("主 石")){
                    list.add(stoEntity.getIsSelfStone()+"");
                }

            }
        }
        return StringUtils.purUrlCut(key, list).toString();
    }



    public class ListAdapter extends BaseAdapter {
        public  Map<String,StoneEntity> checkedState;
        public ListAdapter(){
            checkedState = new HashMap<>();
        }

        public void removeChecked(StoneEntity stoneEntity){
            if(checkedState.get(stoneEntity.getStroneName())!=null){
                checkedState.remove(stoneEntity.getStroneName());
                stoneEntity.setChecked(false);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return stoneEntities.size();
        }

        @Override
        public StoneEntity getItem(int i) {
            return stoneEntities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(StyleInfromationActivity.this).inflate(R.layout.item_styleinfromation, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.checkLayoutVisible.setVisibility(View.VISIBLE);
            final StoneEntity stoneEntity = getItem(i);
            switch (i) {
                case 0:
                    stoneEntity.setStroneName("主 石");
                    viewHolder.idTvTitle.setText("主 石");
                    viewHolder.tvHint.setText("自带");
                    break;
                case 1:
                    stoneEntity.setStroneName("副石A");
                    viewHolder.idTvTitle.setText("副石A");
                    viewHolder.tvHint.setText("封石");
                    break;
                case 2:
                    stoneEntity.setStroneName("副石B");
                    viewHolder.idTvTitle.setText("副石B");
                    viewHolder.tvHint.setText("封石");
                    break;
                case 3:
                    stoneEntity.setStroneName("副石C");
                    viewHolder.idTvTitle.setText("副石C");
                    viewHolder.tvHint.setText("封石");

                    break;
            }

           // viewHolder.idStoreNumber.addTextChangedListener(new TextChange(viewHolder,stoneEntity));
            viewHolder.idStoreNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus){
                        L.e("得到焦点 ");
                        viewHolder.idStoreNumber.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                L.e("得到焦点改变 数据"+s.toString());
                                stoneEntity.setNumber(s.toString());
                            }
                        });
                    }
                    if (!StringUtils.isEmpty(stoneEntity.getNumber())){
                        viewHolder.idStoreNumber.setBackgroundResource(R.drawable.btn_bg_while);
                    }else {
                        viewHolder.idStoreNumber.setBackgroundResource(R.drawable.check_red_border);
                    }
                }
            });
            viewHolder.idStoreNumber.setText(stoneEntity.getNumber());
            if (!StringUtils.isEmpty(viewHolder.idStoreNumber.getText().toString())){
                viewHolder.idStoreNumber.setBackgroundResource(R.drawable.btn_bg_while);
            }else {
                viewHolder.idStoreNumber.setBackgroundResource(R.drawable.check_red_border);
            }

            if (stoneEntity.isChecked()){
                redHint(viewHolder,false);
                checkedState.put(stoneEntity.getStroneName(),stoneEntity);
            }else {
                redHint(viewHolder,checkUpPass(stoneEntity));
            }
            if(checkedState.get(stoneEntity.getStroneName())!=null){
                viewHolder.idIsCheck.setChecked(true);
            }else {
                viewHolder.idIsCheck.setChecked(false);
            }
            viewHolder.idIsCheck.setTag(i);
            viewHolder.idIsCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkedState.get(stoneEntity.getStroneName())!=null){
                        checkedState.remove(stoneEntity.getStroneName());
                        stoneEntity.setChecked(false);
                        stoneEntity.setIsSelfStone(0);
                    }else {
                        checkedState.put(stoneEntity.getStroneName(),stoneEntity);
                    }
                    if (checkedState.size()>0){
                        for (String key:checkedState.keySet()){
                            StoneEntity stone = checkedState.get(key);//得到每个key多对用value的值
                            stone.setChecked(true);
                            stoneEntity.setIsSelfStone(1);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            initDataAndListener(viewHolder,stoneEntity);
            return view;
        }



        /*true红色提示  表示不通过验证*/
        public void redHint(ViewHolder viewHolder,boolean isRed){
            if (isRed){
                viewHolder.idStoreCut.getTv().setBackgroundResource(R.drawable.check_red_border);
                viewHolder.idStoreType.getTv().setBackgroundResource(R.drawable.check_red_border);
                viewHolder.idStoreColor.getTv().setBackgroundResource(R.drawable.check_red_border);
                viewHolder.idStoreNorm.getTv().setBackgroundResource(R.drawable.check_red_border);
                viewHolder.idStoreShape.getTv().setBackgroundResource(R.drawable.check_red_border);
            }else {
                viewHolder.idStoreCut.getTv().setBackgroundResource(R.drawable.btn_bg_while);
                viewHolder.idStoreType.getTv().setBackgroundResource(R.drawable.btn_bg_while);
                viewHolder.idStoreColor.getTv().setBackgroundResource(R.drawable.btn_bg_while);
                viewHolder.idStoreNorm.getTv().setBackgroundResource(R.drawable.btn_bg_while);
                viewHolder.idStoreShape.getTv().setBackgroundResource(R.drawable.btn_bg_while);
                viewHolder.idStoreNumber.setBackgroundResource(R.drawable.btn_bg_while);
            }
        }


    }

    /*选中显示*/
    public void isCheckVisable(final ViewHolder viewHolder,final StoneEntity stoneEntity){
        if (!stoneEntity.isChecked()){
            viewHolder.idStoreType.setDefaultText("类型");
            viewHolder.idStoreNorm.setDefaultText("规格");
            viewHolder.idStoreShape.setDefaultText("形状");
            viewHolder.idStoreCut.setDefaultText("净度");
            viewHolder.idStoreColor.setDefaultText("颜色");
        }else {
            viewHolder.idStoreType.setTextName(stoneEntity.getTypeTitle());
            viewHolder.idStoreNorm.setTextName(stoneEntity.getSpecTitle());
            viewHolder.idStoreShape.setTextName(stoneEntity.getShapeTitle());
            viewHolder.idStoreCut.setTextName(stoneEntity.getPurityTitle());
            viewHolder.idStoreColor.setTextName(stoneEntity.getColorTitle());
        }

    }





    private void initDataAndListener(final ViewHolder viewHolder, final StoneEntity stoneEntity) {

        isCheckVisable(viewHolder,stoneEntity);



        idCusStoreRemarkid.setOnSelectData(new CustomSelectButton.OnselectData() {
            @Override
            public List<Type> getData() {
                List<Type> list = new ArrayList<>();
                for (int i = 0; i < remarksEntity.size(); i++) {
                    Type type = new Type();
                    type.setId(remarksEntity.get(i).getId());
                    type.setTypeName(remarksEntity.get(i).getTitle());
                    type.setContent(remarksEntity.get(i).getContent());
                    list.add(type);
                }
                return list;
            }

            @Override
            public void getSelectId(Type type) {
                remar = type;
                String curentRemar = idTvStoreRemarks.getText().toString();
                idTvStoreRemarks.setText(curentRemar + type.getContent());
                isSetNOcheck(stoneEntity);
            }

            @Override
            public String getTitle() {
                return "选择备注";
            }
        });


        viewHolder.idStoreType.setOnSelectData(new CustomSelectButton.OnselectData() {
            @Override
            public List<Type> getData() {
                List<Type> list = new ArrayList<Type>();
                for (int i = 0; i < stoneTypeItme.size(); i++) {
                    Type type = new Type();
                    type.setId(stoneTypeItme.get(i).getId());
                    type.setTypeName(stoneTypeItme.get(i).getTitle());
                    list.add(type);
                }
                return list;
            }

            @Override
            public void getSelectId(Type type) {
                stoneEntity.setTypeId(type.getId());
                stoneEntity.setTypeTitle(type.getTypeName());
                setStorePrice(stoneEntity);
               // adapter.setTag(viewHolder,position);
                isSetNOcheck(stoneEntity);
            }

            @Override
            public String getTitle() {
                return "请选择类型";
            }
        });
        viewHolder.idStoreColor.setOnSelectData(new CustomSelectButton.OnselectData() {
            @Override
            public List<Type> getData() {
                List<Type> list = new ArrayList<Type>();
                for (int i = 0; i < stoneColorItme.size(); i++) {
                    Type type = new Type();
                    type.setId(stoneColorItme.get(i).getId());
                    type.setTypeName(stoneColorItme.get(i).getTitle());
                    list.add(type);
                }
                return list;
            }

            @Override
            public void getSelectId(Type type) {
                stoneEntity.setColorId(type.getId());
                stoneEntity.setColorTitle(type.getTypeName());
               setStorePrice(stoneEntity);
               // adapter.setTag(viewHolder,position);
                isSetNOcheck(stoneEntity);
            }

            @Override
            public String getTitle() {
                return "请选择颜色";
            }
        });
        viewHolder.idStoreCut.setOnSelectData(new CustomSelectButton.OnselectData() {
            @Override
            public List<Type> getData() {
                List<Type> list = new ArrayList<Type>();
                for (int i = 0; i < stonePurityItme.size(); i++) {
                    Type type = new Type();
                    type.setId(stonePurityItme.get(i).getId());
                    type.setTypeName(stonePurityItme.get(i).getTitle());
                    list.add(type);
                }
                return list;
            }

            @Override
            public void getSelectId(Type type) {
                stoneEntity.setPurityId(type.getId());
                stoneEntity.setPurityTitle(type.getTypeName());
                setStorePrice(stoneEntity);
               // adapter.setTag(viewHolder,position);
                isSetNOcheck(stoneEntity);
            }

            @Override
            public String getTitle() {
                return "请选择净度";
            }
        });


        viewHolder.idStoreNorm.setOnSelectData(new CustomSelectInput.OnselectData() {
            @Override
            public void getSelectId(String key) {
                stoneEntity.setSpecTitle(key);
                setStorePrice(stoneEntity);
              //  adapter.setTag(viewHolder,position);
                isSetNOcheck(stoneEntity);
            }

            @Override
            public String getTitle() {
                if (!StringUtils.isEmpty(stoneEntity.getSpecSelectTitle())) {
                    return stoneEntity.getSpecSelectTitle();
                } else {
                    return "请输入合法规格";
                }

            }
        });

        viewHolder.idStoreShape.setOnSelectData(new CustomSelectButton.OnselectData() {
            @Override
            public List<Type> getData() {
                List<Type> list = new ArrayList<Type>();
                for (int i = 0; i < stoneShapeItem.size(); i++) {
                    Type type = new Type();
                    type.setId(stoneShapeItem.get(i).getId());
                    type.setTypeName(stoneShapeItem.get(i).getTitle());
                    list.add(type);
                }
                return list;
            }

            @Override
            public void getSelectId(Type type) {
                stoneEntity.setShapeId(type.getId());
                stoneEntity.setShapeTitle(type.getTypeName());
                isSetNOcheck(stoneEntity);
                //adapter.setTag(viewHolder,position);
            }

            @Override
            public String getTitle() {
                return "请选择形状";
            }
        });


        /*    查看当前订单*/
        idTvCurorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addCurentOrder();
                //  openActivity(ConfirmOrderActivity.class,null);
                /*确定页面的取消修改*/
                if (type == 1 || type == 2) {
                    finish();
                } else {
                    /*查看当前订单*/
                    Intent intent = new Intent(StyleInfromationActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("waitOrderCount", waitOrderCount);
                    startActivityForResult(intent, 12);
                }
            }
        });
    }


    public void isSetNOcheck(StoneEntity  stoneEntity){
        if (stoneEntity.isChecked()&&!stoneEntity.getStroneName().equals("主 石")){
            adapter.removeChecked(stoneEntity);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e("requestCode" + requestCode);
        if (requestCode == 11) {
            if (data != null) {
                String tempid = data.getExtras().getString("itemId");
                type = 1;
                if (!StringUtils.isEmpty(tempid)) {
                    itemId = tempid;
                    loadNetData();
                }
            }
        }
        if (requestCode == 12) {
            if (data==null){
                return;
            }
            waitOrderCount = data.getExtras().getInt("waitOrderCount");
            remind(waitOrderCount);
        }
    }

    public void setStorePrice(StoneEntity stoneEntity) {
        if (!(StringUtils.isEmpty(stoneEntity.getColorId()) && StringUtils.isEmpty(stoneEntity.getPurityId())
                && StringUtils.isEmpty(stoneEntity.getSpecTitle()) && StringUtils.isEmpty(stoneEntity.getTypeId()))) {
            loadStonePrice(stoneEntity);
        }
    }

    /***
     *
     * 获取价格
     *
     * */
    public void loadStonePrice(final StoneEntity stoneEntity) {
        String url = AppURL.URL_STONE_PRICE + "tokenKey=" + BaseApplication.getToken() + "&colorId=" + stoneEntity.getColorId() +
                "&categoryId=" + stoneEntity.getTypeId() + "&specValue=" + stoneEntity.getSpecTitle() + "&purityId=" + stoneEntity.getPurityId();
        L.e("查询价格:    " + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
                int error = OKHttpRequestUtils.getmInstance().getResultCode(result);
                if (error == 0) {
                    String price = new Gson().fromJson(result, JsonObject.class).getAsJsonObject("data").get("price").getAsString();
                    stoneEntity.setPrice(price);
                    adapter.notifyDataSetChanged();
                } else if (error == 2) {
                    loginToServer(StyleInfromationActivity.class);
                } else {
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

    public class ViewHolder {
        @Bind(R.id.id_store_type)
        CustomSelectButton idStoreType;
        @Bind(R.id.id_store_norm)
        CustomSelectInput idStoreNorm;
        @Bind(R.id.id_store_shape)
        CustomSelectButton idStoreShape;
        @Bind(R.id.id_store_color)
        CustomSelectButton idStoreColor;
        @Bind(R.id.id_store_cut)
        CustomSelectButton idStoreCut;
        @Bind(R.id.id_store_price)
        CustomSelectButton idStorePrice;
        @Bind(R.id.id_tv_title)
        TextView idTvTitle;
        @Bind(R.id.id_ed_number)
        EditText idStoreNumber;
        @Bind(R.id.id_check_Layout_visiable)
        LinearLayout checkLayoutVisible;

        @Bind(R.id.id_is_check)
        CheckBox idIsCheck;

        @Bind(R.id.tv_hint)
        TextView tvHint;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("waitOrderCount", waitOrderCount);
            setResult(10, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
