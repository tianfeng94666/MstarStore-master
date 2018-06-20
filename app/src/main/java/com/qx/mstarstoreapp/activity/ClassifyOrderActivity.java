package com.qx.mstarstoreapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.ClassifyOrderAdapter;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.bean.StoneRangeBean;
import com.qx.mstarstoreapp.greendao.gen.StoneRangeBeanDao;
import com.qx.mstarstoreapp.utils.StringUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class ClassifyOrderActivity extends BaseActivity {
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.rv_classify)
    RecyclerView rvClassify;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_comfirm)
    TextView tvComfirm;
    private StoneRangeBeanDao stoneRangBeanDao;
    List<StoneRangeBean> stoneRangeBeanList;
    private ClassifyOrderAdapter classifyOrderAdapter;

    @Override
    public void loadNetData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleText.setText("设置分单条件");
        stoneRangBeanDao = BaseApplication.getApplication().getDaoSession().getStoneRangeBeanDao();
        queryData();
        classifyOrderAdapter = new ClassifyOrderAdapter(this, stoneRangeBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvClassify.setLayoutManager(linearLayoutManager);
        rvClassify.setAdapter(classifyOrderAdapter);
        classifyOrderAdapter.setOnDelListener(new ClassifyOrderAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
                deleteData(stoneRangeBeanList.get(pos));
                stoneRangeBeanList.remove(pos);
                classifyOrderAdapter.notifyItemRemoved(pos);
            }

            @Override
            public void edit(int pos) {
                initDialog(stoneRangeBeanList.get(pos), pos);
            }

            @Override
            public void ischoose(int pos, int i) {
              StoneRangeBean stoneRangeBean =  stoneRangeBeanList.get(pos);
              stoneRangeBean.setIsChoose(i);
              updateData(stoneRangeBean);
            }
        });

    }


    @OnClick({R.id.tv_add, R.id.tv_comfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                initDialog(null, 0);
                break;
            case R.id.tv_comfirm:
                finish();
                break;
        }
    }

    private void initDialog(final StoneRangeBean stoneRangeBean, final int pos) {
        final EditText etWeightMin, etWeightMax, etAmount;
        long id = -5;
        View view = View.inflate(this, R.layout.stone_range_bean_add, null);
        etWeightMin = (EditText) view.findViewById(R.id.et_weight_min);
        etWeightMax = (EditText) view.findViewById(R.id.et_weight_max);
        etAmount = (EditText) view.findViewById(R.id.et_amount);
        if (stoneRangeBean != null) {
            etWeightMin.setText(stoneRangeBean.getStoneMin());
            etWeightMax.setText(stoneRangeBean.getStoneMax());
            etAmount.setText(stoneRangeBean.getAmount());
            id = stoneRangeBean.getId();
        }

        final long finalId = id;
        Object dialog = new AlertDialog.Builder(this)
                .setTitle("添加分类")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (StringUtils.isEmpty(etWeightMax.getText().toString()) && StringUtils.isEmpty(etWeightMin.getText().toString())) {
                            showToastReal("最大值和最小值不能都为空");
                            return;
                        }

                        if (stoneRangeBean == null) {
                            StoneRangeBean stoneRangeBean = new StoneRangeBean(null, etWeightMin.getText().toString(), etWeightMax.getText().toString(), etAmount.getText().toString(), 1);
                            insertData(stoneRangeBean);
                            stoneRangeBeanList.add(stoneRangeBean);
                        } else {
                            StoneRangeBean stoneRangeBean = new StoneRangeBean(finalId, etWeightMin.getText().toString(), etWeightMax.getText().toString(), etAmount.getText().toString(), 1);
                            stoneRangeBeanList.set(pos, stoneRangeBean);
                            updateData(stoneRangeBean);
                        }

                        classifyOrderAdapter.notifyDataSetChanged();
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

    private void queryData() {
        stoneRangeBeanList = stoneRangBeanDao.loadAll();
        if (classifyOrderAdapter != null) {
            classifyOrderAdapter.notifyDataSetChanged();
        }
    }

    private void insertData(StoneRangeBean stoneRangeBean) {
        stoneRangBeanDao.insert(stoneRangeBean);
    }

    private void deleteData(StoneRangeBean stoneRangeBean) {
        stoneRangBeanDao.delete(stoneRangeBean);
    }

    private void updateData(StoneRangeBean stoneRangeBean) {
        stoneRangBeanDao.update(stoneRangeBean);
    }

    private void deleteAll() {
        stoneRangBeanDao.deleteAll();
    }
}
