package com.qx.mstarstoreapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.FileManger;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.json.ExcelOrderBean;
import com.qx.mstarstoreapp.json.ExcelOrderResult;
import com.qx.mstarstoreapp.net.VolleyRequestUtils;
import com.qx.mstarstoreapp.utils.ExcelUtil;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.ToastManager;
import com.qx.mstarstoreapp.viewutils.SwipeMenuLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class ExcelOrderDealActivity extends BaseActivity {

    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView tvRight;
    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.tv_model_number)
    TextView tvModelNumber;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_purity)
    TextView tvPurity;
    @Bind(R.id.gv_order)
    ListView lvOrder;
    @Bind(R.id.bt_confirm)
    Button btConfirm;
    private ArrayList<ArrayList<ArrayList<Object>>> excleList;
    private ArrayList<ExcelOrderBean> arrayList;
    private CommonAdapter commonAdapter;
    private ExcelOrderResult excleOrdeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_order);
        ButterKnife.bind(this);
        dealExcel(getIntent());
    }


    private void dealExcel(Intent data) {
        titleText.setText("excel导入");
        Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
        if (uri == null) {
            return;
        }
        String img_path = new FileManger().getPath(this, uri);
        File file = new File(img_path);
        excleList = ExcelUtil.readExcel(file);
        changeToGson();

    }

    private void changeToGson() {
        arrayList = new ArrayList<>();
        ArrayList<ArrayList<Object>> sheet = excleList.get(0);
        for (int i = 1; i < sheet.size(); i++) {
            if (sheet.get(i).size() < 4) {
                continue;
            }
            ExcelOrderBean excelOrderBean = new ExcelOrderBean();
            excelOrderBean.setId(i + "");
            excelOrderBean.setModelNum(sheet.get(i).get(0).toString());
            excelOrderBean.setNumber(sheet.get(i).get(1).toString());
            excelOrderBean.setPurity(sheet.get(i).get(2).toString());
            excelOrderBean.setHandSize(sheet.get(i).get(3).toString());
            excelOrderBean.setIserror("0");



            arrayList.add(excelOrderBean);
        }

        initView();


    }

    private void initView() {
        if (arrayList == null) {
            return;
        }
        commonAdapter = new CommonAdapter(arrayList, R.layout.excel_item_layout) {
            @Override
            public void convert(final int position, final BaseViewHolder helper, Object item) {
                helper.setText(R.id.et_id, arrayList.get(position).getId());
                helper.setText(R.id.et_model_number, arrayList.get(position).getModelNum());
                helper.setText(R.id.et__number, arrayList.get(position).getNumber());
                helper.setText(R.id.et_purity, arrayList.get(position).getPurity());
                helper.setText(R.id.tv_message, arrayList.get(position).getIserror());
                helper.setText(R.id.tv_hand_size, arrayList.get(position).getHandSize());

                if (arrayList.get(position).getIserror().equals("0")) {
                    helper.getView(R.id.tv_message).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.tv_message).setVisibility(View.VISIBLE);
                }
                helper.setViewOnclick(R.id.bt_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.remove(position);
                        showToastReal("已经删除");
                        ((SwipeMenuLayout) helper.getConvertView()).quickClose();
                        notifyDataSetChanged();
                    }
                });
                helper.setViewOnclick(R.id.bt_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initDialog(position);
                    }
                });

            }
        };
        lvOrder.setAdapter(commonAdapter);

        btConfirm.setText("确定(" + arrayList.size() + ")");
    }

    private void initDialog(final int position) {
        final EditText etModenumber, etNumber, etPurity,etHandsize;
        View view = View.inflate(this, R.layout.excel_item_eidt, null);
        etModenumber = (EditText) view.findViewById(R.id.et_model_number);
        etNumber = (EditText) view.findViewById(R.id.et_number);
        etPurity = (EditText) view.findViewById(R.id.et_purity);
        etHandsize = (EditText) view.findViewById(R.id.et_handsize);

        etModenumber.setText(arrayList.get(position).getModelNum());
        etNumber.setText(arrayList.get(position).getNumber());
        etPurity.setText(arrayList.get(position).getPurity());
        etHandsize.setText(arrayList.get(position).getHandSize());
        Object dialog = new AlertDialog.Builder(this)
                .setTitle("数据修改")
                .setPositiveButton("确定修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayList.get(position).setModelNum(etModenumber.getText().toString());
                        arrayList.get(position).setNumber(etNumber.getText().toString());
                        arrayList.get(position).setPurity(etPurity.getText().toString());
                        arrayList.get(position).setHandSize(etHandsize.getText().toString());
                        arrayList.get(position).setIserror("0");
                        commonAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setView(view)
                .show();


    }


    @Override
    public void loadNetData() {
        baseShowWatLoading();

        Map map = new HashMap();
        L.e(arrayList.toString());
        ArrayList temp = new ArrayList(arrayList);
        Collections.sort(temp, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ExcelOrderBean bean1 = (ExcelOrderBean) o1;
                ExcelOrderBean bean2 = (ExcelOrderBean) o2;
                return bean2.getId().compareTo(bean1.getId());
            }
        });
        map.put("json", temp.toString());

        String url = AppURL.URL_COMMIT_ORDER_BYEXCEL + "&tokenKey=" + BaseApplication.getToken();
        L.e("url", url);
        VolleyRequestUtils.getInstance().getStringPostRequest(this, url, new VolleyRequestUtils.HttpStringRequsetCallBack() {
            @Override
            public void onSuccess(String result) {
                baseHideWatLoading();
                JsonObject jsonResult = new Gson().fromJson(result, JsonObject.class);
                String error = jsonResult.get("error").getAsString();
                if (error.equals("0")) {
                    excleOrdeResult = new Gson().fromJson(result, ExcelOrderResult.class);
                    if (excleOrdeResult.getData() == null) {
                        return;
                    }
                    Global.waitOrderCount = Integer.parseInt(excleOrdeResult.getData().getWaitOrderCount());
                    openActivity(ConfirmOrderActivity.class, null);

                } else if (error.equals("1")) {
                    baseHideWatLoading();
                    L.e(result);
                    excleOrdeResult = new Gson().fromJson(result, ExcelOrderResult.class);
                    if (excleOrdeResult.getData().getErrData() == null) {
                        return;
                    }
                    showToastReal(excleOrdeResult.getMessage());
                    List<ExcelOrderResult.DataBean.ErrDataBean> errorList = excleOrdeResult.getData().getErrData();
                    editIserror(errorList);

                } else {
                    baseHideWatLoading();
                    String message = new Gson().fromJson(result, JsonObject.class).get("message").getAsString();
                    ToastManager.showToastWhendebug(message);
                    L.e(message);
                    loginToServer(MakingActivity.class);
                }
            }

            @Override
            public void onFail(String fail) {
                baseHideWatLoading();
                L.e("fail:", fail);
            }
        }, map);
    }

    private void editIserror(List<ExcelOrderResult.DataBean.ErrDataBean> errorList) {
        if (errorList == null || errorList.size() == 0) {
            return;
        }
        for (int j = 0; j < errorList.size(); j++) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getId().equals(errorList.get(j).getId())) {
                    arrayList.get(i).setIserror(errorList.get(j).getModelNum() + errorList.get(j).getPurity() + errorList.get(j).getNumber());
                }

            }
        }
        commonAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bt_confirm:
                showToastReal("导入");
                loadNetData();
                break;
        }
    }
}
