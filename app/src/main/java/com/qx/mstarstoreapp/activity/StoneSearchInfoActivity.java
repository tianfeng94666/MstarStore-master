package com.qx.mstarstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.adapter.StoneOthersAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.json.KeyTitle;
import com.qx.mstarstoreapp.json.StoneSearchInfo;
import com.qx.mstarstoreapp.json.StoneSearchResult;
import com.qx.mstarstoreapp.json.ValueRange;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.SpUtils;
import com.qx.mstarstoreapp.utils.StringUtils;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.utils.UIUtils;
import com.qx.mstarstoreapp.viewutils.CustomGridView;
import com.qx.mstarstoreapp.viewutils.LeftPopupWindow;
import com.qx.mstarstoreapp.viewutils.RangeSeekBar;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class StoneSearchInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.tv_right)
    TextView ivRight;
    @Bind(R.id.id_rel_title)
    RelativeLayout idRelTitle;
    @Bind(R.id.ll_certificate_orgnization)
    LinearLayout llCertificateOrgnization;
    @Bind(R.id.tv_add_spot)
    TextView tvAddSpot;
    @Bind(R.id.iv_reduce)
    ImageView ivReduce;
    @Bind(R.id.et_ring_amount)
    EditText etSpot;
    @Bind(R.id.iv_add)
    ImageView ivAdd;

    RangeSeekBar sbPrice;
    @Bind(R.id.ll_shape)
    LinearLayout llShape;
    @Bind(R.id.gv_shape)
    CustomGridView gvShape;
    @Bind(R.id.ll_color)
    LinearLayout llColor;
    @Bind(R.id.gv_color)
    GridView gvColor;
    @Bind(R.id.ll_quality)
    LinearLayout llQuality;
    @Bind(R.id.gv_quality)
    GridView gvQuality;
    @Bind(R.id.tv_certificate_1)
    TextView tvCertificate1;
    @Bind(R.id.tv_certificate_2)
    TextView tvCertificate2;
    @Bind(R.id.lv_others)
    ListView lvOthers;
    @Bind(R.id.tv_color)
    TextView tvColor;
    @Bind(R.id.tv_quality)
    TextView tvQuality;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.tv_reset)
    TextView idTvCurorder;
    @Bind(R.id.gv_weight)
    GridView gvWeight;
    @Bind(R.id.et_weight_min)
    EditText etWeightMin;
    @Bind(R.id.et_weight_max)
    EditText etWeightMax;
    @Bind(R.id.gv_price)
    GridView gvPrice;
    @Bind(R.id.et_price_min)
    EditText etPriceMin;
    @Bind(R.id.et_price_max)
    EditText etPriceMax;
    @Bind(R.id.ll_show_less)
    LinearLayout llShowLess;
    @Bind(R.id.rl_stone_info)
    RelativeLayout rlStoneInfo;
    private StoneSearchResult stoneSearchResult;
    private StoneSearchResult.DataBean.CertAuthBean certAuthBean;
    private StoneSearchResult.DataBean.ShapeBean shapeBean;
    private boolean[] certAuthBeanIsChooselist;
    private DecimalFormat df = new DecimalFormat("0.0");
    private DecimalFormat df1 = new DecimalFormat("0");
    private StoneSearchResult.DataBean.ColorBean colorBean;
    private StoneSearchResult.DataBean.PurityBean purityBean;
    private boolean[] colorChecks;
    private boolean[] shapeChecks;
    private boolean[] purityChecks;
    private StoneSearchResult.DataBean.WeightBean weightBean;
    private StoneSearchResult.DataBean.PriceBean priceBean;
    private String weightkey = "";
    private String pricekey = "";
    private boolean[] weightChecks;
    private boolean[] priceChecks;
    private StoneOthersAdapter stoneOthersAdapter;
    private StoneSearchInfo stoneSearchInfo;
    private int openType;//0 是正常进入，1是主石进入
    private String itemId;//产品的id
    private int type;//是否是修改订单
    private LeftPopupWindow leftPopupWindow;
    private boolean isShowPrice;
    private ValueRange stoneValueRang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stone_storehouse1);
        ButterKnife.bind(this);
        getDate();
        loadNetData();
    }

    private void getDate() {
        openType = getIntent().getIntExtra("openType", 0);
        itemId = getIntent().getStringExtra("itemId");
        type = getIntent().getIntExtra("type", 0);
        isShowPrice = SpUtils.getInstace(this).getBoolean("isShowStonePrice", true);
        stoneValueRang = (ValueRange) getIntent().getSerializableExtra("stoneValueRange");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Global.isShowPopup != 0) {
            llShowLess.setVisibility(View.VISIBLE);
            initPopwindow();
        } else {
            llShowLess.setVisibility(View.GONE);
        }
    }

    private void initView() {
        titleText.setText("裸石库");
        idIgBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        idTvCurorder.setOnClickListener(this);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(this);
        initCertificate();
        initSpot();
        initRandSeekBar();
        initShape();
        initColor();
        initPurity();
        initOthers();
        if (Global.isShowPopup != 0) {
            llShowLess.setVisibility(View.VISIBLE);
            initPopwindow();
        } else {
            llShowLess.setVisibility(View.GONE);
        }
        if (Global.isShowPopup != 0 && Global.ring != null && Global.ring.getRingWeightRange() != null) {
            String st = Global.ring.getRingWeightRange().getValue();
            String[] sts = st.split(",");
            if (sts.length == 2) {
                etWeightMin.setText(sts[0]);
                etWeightMax.setText(sts[1]);
            }

        }
        //来自个人定制
        if(type>2){
            if(stoneValueRang!=null){
                String st = stoneValueRang.getValue();
                String[] sts = st.split(",");
                if (sts.length == 2) {
                    etWeightMin.setText(sts[0]);
                    etWeightMax.setText(sts[1]);
                }
            }
        }
    }


    private void initPopwindow() {
        leftPopupWindow = new LeftPopupWindow(this);
        llShowLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftPopupWindow.showPop(rlStoneInfo);
            }
        });
    }

    private void initOthers() {
        stoneOthersAdapter = new StoneOthersAdapter(this, stoneSearchResult.getData());
        lvOthers.setAdapter(stoneOthersAdapter);
    }

    private void initPurity() {
        purityChecks = new boolean[purityBean.getValues().size()];
        final CommonAdapter commonAdapter = new CommonAdapter<String>(purityBean.getValues(), R.layout.item_gv_text) {
            @Override
            public void convert(int position, BaseViewHolder helper, String item) {
                if (purityChecks[position]) {
                    helper.setText(R.id.tv_item_text, item, R.drawable.corners_red_bg, getResources().getColor(R.color.white));
                } else {
                    helper.setText(R.id.tv_item_text, item, R.drawable.corners_white_bg, getResources().getColor(R.color.text_color));
                }
            }
        };
        gvQuality.setAdapter(commonAdapter);
        gvQuality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                purityChecks[position] = !purityChecks[position];
                commonAdapter.notifyDataSetChanged();
                tvQuality.setText(getselectPurity());
            }
        });
        setGvNum(gvQuality);
    }

    private void initColor() {
        List<String> colorList = colorBean.getValues();
        colorChecks = new boolean[colorList.size()];
        final CommonAdapter colorAdapter = new CommonAdapter<String>(colorList, R.layout.item_gv_text) {
            @Override
            public void convert(int position, BaseViewHolder helper, String item) {
                if (colorChecks[position]) {
                    helper.setText(R.id.tv_item_text, item, R.drawable.corners_red_bg, getResources().getColor(R.color.white));
                } else {
                    helper.setText(R.id.tv_item_text, item, R.drawable.corners_white_bg, getResources().getColor(R.color.text_color));
                }

            }
        };
        gvColor.setAdapter(colorAdapter);
        gvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                colorChecks[position] = !colorChecks[position];
                colorAdapter.notifyDataSetChanged();
                tvColor.setText(getselectColor());
            }
        });
        setGvNum(gvColor);
    }

    public void setGvNum(GridView gv) {
        if (UIUtils.isPad(this)) {
            if (UIUtils.isScreenChange(this)) {
                gv.setNumColumns(10);
                setListViewHeightBasedOnChildren(gv, 10);
            } else {
                gv.setNumColumns(8);
                setListViewHeightBasedOnChildren(gv, 10);
            }

        } else {
            if (UIUtils.isScreenChange(this)) {
                gv.setNumColumns(8);
                setListViewHeightBasedOnChildren(gv, 8);
            } else {
                gv.setNumColumns(5);
                setListViewHeightBasedOnChildren(gv, 5);
            }
        }
    }

    private void initShape() {
        List<StoneSearchResult.DataBean.ShapeBean.ValuesBean> shapeList = shapeBean.getValues();
        shapeChecks = new boolean[shapeList.size()];
        final CommonAdapter shapeAdapter = new CommonAdapter<StoneSearchResult.DataBean.ShapeBean.ValuesBean>(shapeList, R.layout.item_gv_shape) {
            @Override
            public void convert(final int position, final BaseViewHolder helper, final StoneSearchResult.DataBean.ShapeBean.ValuesBean item) {
                helper.setImageBitmapHeight(R.id.iv_item_shape, 1.2);
                if (shapeChecks[position]) {
                    helper.setImageBitmap(R.id.iv_item_shape, item.getPic1());

                } else {
                    helper.setImageBitmap(R.id.iv_item_shape, item.getPic());
                }
                helper.setViewOnclick(R.id.iv_item_shape, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shapeChecks[position] = !shapeChecks[position];
                        if (shapeChecks[position]) {
                            helper.setImageBitmap(R.id.iv_item_shape, item.getPic1());
                        } else {
                            helper.setImageBitmap(R.id.iv_item_shape, item.getPic());
                        }
                    }
                });

            }
        };
        gvShape.setAdapter(shapeAdapter);
//        gvShape.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                shapeChecks[position] = !shapeChecks[position];
//                shapeAdapter.notifyDataSetChanged();
//            }
//        });
        if (UIUtils.isPad(this)) {
            gvShape.setNumColumns(10);
            setListViewHeightBasedOnChildren(gvShape, 10);
        } else {
            if (UIUtils.isScreenChange(this)) {
                gvShape.setNumColumns(8);
                setListViewHeightBasedOnChildren(gvShape, 8);
            } else {
                gvShape.setNumColumns(5);
                setListViewHeightBasedOnChildren(gvShape, 5);
            }
        }


    }

    private String getselectColor() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < colorChecks.length; i++) {
            if (colorChecks[i]) {
                sb.append(colorBean.getValues().get(i) + "  ");
            }
        }
        return sb.toString();
    }

    private String getselectPurity() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < purityChecks.length; i++) {
            if (purityChecks[i]) {
                sb.append(purityBean.getValues().get(i) + "  ");
            }
        }
        return sb.toString();
    }

    private void initRandSeekBar() {

        final List<KeyTitle> weightList = weightBean.getList();
        final List<KeyTitle> priceList = priceBean.getList();
        if (weightList == null) {
            return;
        }
        if (priceList == null) {
            return;
        }
        weightChecks = new boolean[weightList.size()];
        priceChecks = new boolean[priceList.size()];
        gvWeight.setNumColumns(weightList.size());
        final CommonAdapter weightAdapter = new CommonAdapter<KeyTitle>(weightList, R.layout.item_gv_text2) {
            @Override
            public void convert(int position, BaseViewHolder helper, KeyTitle item) {
                if (weightChecks[position]) {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.board_red, getResources().getColor(R.color.theme_red));
                } else {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.board_gray, getResources().getColor(R.color.text_color));
                }
            }
        };
        gvWeight.setAdapter(weightAdapter);
        gvWeight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = weightList.get(position).getKey();
                String[] values = value.split(",");
                etWeightMin.setText(values[0]);
                if (values[1].equals("0")) {
                    etWeightMax.setText("");
                } else {
                    etWeightMax.setText(values[1]);
                }

                if (weightChecks[position]) {
                    weightChecks[position] = !weightChecks[position];
                    weightkey = "";
                    etWeightMax.setText("");
                    etWeightMin.setText("");
                } else {
                    clearCheck(weightChecks);
                    weightChecks[position] = !weightChecks[position];
                    weightkey = weightList.get(position).getKey();
                }
                weightAdapter.notifyDataSetChanged();
            }
        });
        etWeightMin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    clearCheck(weightChecks);
                    weightAdapter.notifyDataSetChanged();
                    weightkey = "";
                }
                return false;
            }
        });
        etWeightMax.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    clearCheck(weightChecks);
                    weightAdapter.notifyDataSetChanged();
                    weightkey = "";
                }
                return false;
            }
        });


        gvPrice.setNumColumns(priceList.size());
        final CommonAdapter priceAdapter = new CommonAdapter<KeyTitle>(priceList, R.layout.item_gv_text2) {
            @Override
            public void convert(int position, BaseViewHolder helper, KeyTitle item) {
                if (priceChecks[position]) {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.board_red, getResources().getColor(R.color.theme_red));
                } else {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.board_gray, getResources().getColor(R.color.text_color));
                }
            }

        };
        gvPrice.setAdapter(priceAdapter);
        gvPrice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String st = priceList.get(position).getKey();
                String[] values = st.split(",");
                etPriceMin.setText(values[0]);
                if (values[1].equals("0")) {
                    etPriceMax.setText("");
                } else {
                    etPriceMax.setText(values[1]);
                }

                if (priceChecks[position]) {
                    priceChecks[position] = !priceChecks[position];
                    pricekey = "";
                    etPriceMax.setText("");
                    etPriceMin.setText("");
                } else {
                    clearCheck(priceChecks);
                    priceChecks[position] = !priceChecks[position];
                    pricekey = priceList.get(position).getKey();
                }
                priceAdapter.notifyDataSetChanged();
            }
        });
        etPriceMax.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    clearCheck(priceChecks);
                    priceAdapter.notifyDataSetChanged();
                    pricekey = "";
                }
                return false;
            }
        });
        etPriceMin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    clearCheck(priceChecks);
                    priceAdapter.notifyDataSetChanged();
                    pricekey = "";
                }
                return false;
            }
        });


    }

    private void clearCheck(boolean[] Checks) {
        for (int i = 0; i < Checks.length; i++) {
            Checks[i] = false;
        }
    }

    private void initSpot() {
        ivAdd.setOnClickListener(this);
        ivReduce.setOnClickListener(this);
    }

    private void initCertificate() {
        tvCertificate1.setText(certAuthBean.getValues().get(0));
        tvCertificate2.setText(certAuthBean.getValues().get(1));
        tvCertificate1.setOnClickListener(this);
        tvCertificate2.setOnClickListener(this);
        certAuthBeanIsChooselist = new boolean[2];

    }

    @Override
    public void loadNetData() {
        baseShowWatLoading();
        String url = "";
        url = AppURL.URL_STONE_SEARCHINFO + "tokenKey=" + BaseApplication.getToken();
        if (StringUtils.isEmpty(url)) {
            return;
        }
        L.e("获取地址" + url);
        VolleyRequestUtils.getInstance().getCookieRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                L.e("result" + result);
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    stoneSearchResult = new Gson().fromJson(result, StoneSearchResult.class);
                    if (stoneSearchResult.getData() == null) {
                        return;
                    }
                    certAuthBean = stoneSearchResult.getData().getCertAuth();
                    shapeBean = stoneSearchResult.getData().getShape();
                    colorBean = stoneSearchResult.getData().getColor();
                    purityBean = stoneSearchResult.getData().getPurity();
                    weightBean = stoneSearchResult.getData().getWeight();
                    priceBean = stoneSearchResult.getData().getPrice();
                    initView();
                } else if (error.equals("2")) {
                    loginToServer(StoneSearchInfoActivity.class);
                } else {
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    L.e(message);
                    showToastReal("数据加载错误:+" + message);
                }
            }

            @Override
            public void onFail(String fail) {
                showToastReal("数据获取失败");
                baseHideWatLoading();
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_certificate_1:
                isChoose(0);
                break;
            case R.id.tv_certificate_2:
                isChoose(1);
                break;
            case R.id.iv_add:
                add();
                break;
            case R.id.iv_reduce:
                reduce();
                break;
            case R.id.id_ig_back:
                finish();
                break;
            case R.id.tv_search:
                if (searchStone()) {
                    gotoResult();
                }
                break;
            case R.id.tv_reset:
                reset();
                loadNetData();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(this, StoneHistoryOrder.class);
                startActivity(intent);
                //设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
        }
    }

    private void reset() {
        tvColor.setText("");
        tvQuality.setText("");
        pricekey = "";
        weightkey = "";
        etPriceMax.setText("");
        etPriceMin.setText("");
        etWeightMax.setText("");
        etWeightMin.setText("");
        certAuthBeanIsChooselist[0] = false;
        tvCertificate1.setTextColor(getResources().getColor(R.color.text_color));
        tvCertificate1.setBackgroundResource(R.drawable.corners_white_bg);
        certAuthBeanIsChooselist[1] = false;
        tvCertificate2.setTextColor(getResources().getColor(R.color.text_color));
        tvCertificate2.setBackgroundResource(R.drawable.corners_white_bg);
    }

    private boolean searchStone() {
        stoneSearchInfo = new StoneSearchInfo();
        stoneSearchInfo.setCerAuth(getCerAuth());

        String weightMin, weightMax;
        weightMin = etWeightMin.getText().toString();
        weightMax = etWeightMax.getText().toString();
        if (!weightMin.isEmpty() && !weightMax.isEmpty() && Double.parseDouble(weightMin) > Double.parseDouble(weightMax)) {
            ToastManager.showToastReal("克拉搜索输入有误！");
            return false;
        }
        if (weightMax.isEmpty()) {
            weightMax = "0";
        }
        if (weightMin.isEmpty()) {
            weightMin = "0";
        }

        stoneSearchInfo.setWeight(weightMin + "," + weightMax);


        String priceMin, priceMax;
        priceMin = etPriceMin.getText().toString();
        priceMax = etPriceMax.getText().toString();
        if ((!priceMax.isEmpty() && !priceMin.isEmpty()) && Double.parseDouble(priceMin) > Double.parseDouble(priceMax)) {
            ToastManager.showToastReal("价格搜索输入有误！");
            return false;
        }
        if (priceMax.isEmpty()) {
            priceMax = "0";
        }
        if (priceMin.isEmpty()) {
            priceMin = "0";
        }
        stoneSearchInfo.setPrice(priceMin + "," + priceMax);

        stoneSearchInfo.setShape(getShape());
        stoneSearchInfo.setColor(getStoneColor());
        stoneSearchInfo.setPurity(getPurity());
        stoneSearchInfo.setCut(stoneOthersAdapter.getChooseResult(0));
        stoneSearchInfo.setPolishing(stoneOthersAdapter.getChooseResult(1));
        stoneSearchInfo.setSymmetric(stoneOthersAdapter.getChooseResult(2));
        stoneSearchInfo.setFluorescence(stoneOthersAdapter.getChooseResult(3));
        stoneSearchInfo.setPercent(etSpot.getText().toString());
        return true;
    }

    private String getPurity() {
        List<String> puritys = purityBean.getValues();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < puritys.size(); i++) {
            if (purityChecks[i]) {
                if (sb.toString().equals("")) {
                    sb.append(puritys.get(i));
                } else {
                    sb.append("," + puritys.get(i));
                }
            }
        }
        return sb.toString();
    }

    private String getShape() {
        List<StoneSearchResult.DataBean.ShapeBean.ValuesBean> shapeList = shapeBean.getValues();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < shapeList.size(); i++) {
            if (shapeChecks[i]) {
                if (sb.toString().equals("")) {
                    sb.append(shapeList.get(i).getName());
                } else {
                    sb.append("," + shapeList.get(i).getName());
                }
            }
        }
        return sb.toString();

    }

    private String getStoneColor() {
        List<String> colorList = colorBean.getValues();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < colorList.size(); i++) {
            if (colorChecks[i]) {
                if (sb.toString().equals("")) {
                    sb.append(colorList.get(i));
                } else {
                    sb.append("," + colorList.get(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获得选中的证书
     *
     * @return
     */
    private String getCerAuth() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < certAuthBeanIsChooselist.length; i++) {
            if (certAuthBeanIsChooselist[i]) {
                sb.append(certAuthBean.getValues().get(0));
                if (i != certAuthBeanIsChooselist.length - 1) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    private void gotoResult() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("searchStoneInfo", stoneSearchInfo);
        Intent intent = new Intent(this, StoneSearchResultActivity.class);
        intent.putExtra("openType", openType);
        intent.putExtra("type", type);
        intent.putExtra("itemId", itemId);
        intent.putExtra("stoneInfo", bundle);
        startActivity(intent);
        //设置切换动画，从右边进入，左边退出
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    private void reduce() {
        int i = Integer.parseInt(etSpot.getText().toString());
//        if (i > 1) {
        --i;
        etSpot.setText(i + "");
//        }
    }

    private void add() {
        int i = Integer.parseInt(etSpot.getText().toString());
//        if (i >= 1) {
        ++i;
        etSpot.setText(i + "");
//        }
    }

    private void isChoose(int i) {
        if (i == 0) {
            if (!certAuthBeanIsChooselist[0]) {
                tvCertificate1.setTextColor(getResources().getColor(R.color.white));
                tvCertificate1.setBackgroundResource(R.drawable.corners_red_bg);
                certAuthBeanIsChooselist[0] = true;
            } else {
                tvCertificate1.setTextColor(getResources().getColor(R.color.text_color));
                tvCertificate1.setBackgroundResource(R.drawable.corners_white_bg);
                certAuthBeanIsChooselist[0] = false;
            }
        } else {
            if (!certAuthBeanIsChooselist[1]) {
                tvCertificate2.setTextColor(getResources().getColor(R.color.white));
                tvCertificate2.setBackgroundResource(R.drawable.corners_red_bg);
                certAuthBeanIsChooselist[1] = true;
            } else {
                tvCertificate2.setTextColor(getResources().getColor(R.color.text_color));
                tvCertificate2.setBackgroundResource(R.drawable.corners_white_bg);
                certAuthBeanIsChooselist[1] = false;
            }
        }
    }

    // 动态加载GridView 高度
    public static void setListViewHeightBasedOnChildren(GridView myGridView, int num) {
        ListAdapter listAdapter = myGridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int col = num;
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            View listItem = listAdapter.getView(i, null, myGridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = myGridView.getLayoutParams();
        params.height = totalHeight + 20;
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        myGridView.setLayoutParams(params);
    }

}
