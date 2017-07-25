package com.qx.mstarstoreapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.activity.StoneChooseMainActivity;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.BaseFragment;
import com.qx.mstarstoreapp.json.ModelDetailResult;
import com.qx.mstarstoreapp.json.StoneDetail;
import com.qx.mstarstoreapp.json.StoneEntity;
import com.qx.mstarstoreapp.viewutils.CustomGridView;

import static com.qx.mstarstoreapp.fragment.StoneFragment.setListViewHeightBasedOnChildren;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class StoneChooseFromSettingFragment extends BaseFragment {
    StoneDetail stoneDetail;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.ll_type)
    LinearLayout llType;
    @Bind(R.id.gv_type)
    GridView gvType;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.iv_weight_reduce)
    ImageView ivWeightReduce;
    @Bind(R.id.et_weight)
    EditText etWeight;
    @Bind(R.id.iv_weight_add)
    ImageView ivWeightAdd;
    @Bind(R.id.tv_amount)
    TextView tvAmount;
    @Bind(R.id.iv_amount_reduce)
    ImageView ivAmountReduce;
    @Bind(R.id.et_amount)
    EditText etAmount;
    @Bind(R.id.iv_amount_add)
    ImageView ivAmountAdd;
    @Bind(R.id.ll_shape)
    LinearLayout llShape;
    @Bind(R.id.gv_shape)
    CustomGridView gvShape;
    @Bind(R.id.tv_color)
    TextView tvColor;
    @Bind(R.id.ll_color)
    LinearLayout llColor;
    @Bind(R.id.gv_color)
    GridView gvColor;
    @Bind(R.id.tv_quality)
    TextView tvQuality;
    @Bind(R.id.ll_quality)
    LinearLayout llQuality;
    @Bind(R.id.gv_quality)
    GridView gvQuality;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.tv_reset)
    TextView tvReset;
    @Bind(R.id.tv_isshow_more)
    TextView tvIsshowMore;
    private boolean[] colorChecks;
    private boolean[] shapeChecks;
    private boolean[] purityChecks;
    private boolean[] typeChecks;
    StoneEntity stoneEntity;
    boolean isShowMore = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_stone_choose_setting, null);
        ButterKnife.bind(this, view);
        stoneDetail = ((StoneChooseMainActivity) getActivity()).getStoneDetail();
        initView();
        return view;
    }

    private void initView() {
        stoneEntity = new StoneEntity();
        initType();
        initPurity();
        initColor();
        initshape();
    }

    private void initshape() {
        shapeChecks = new boolean[stoneDetail.getStoneShapeItem().size()];
        final CommonAdapter commonAdapter = new CommonAdapter<ModelDetailResult.DataEntity.StoneShapeEntity>(stoneDetail.getStoneShapeItem(), R.layout.item_gv_shape) {
            @Override
            public void convert(final int position, final BaseViewHolder helper, final ModelDetailResult.DataEntity.StoneShapeEntity item) {
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
        gvShape.setAdapter(commonAdapter);
        gvShape.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!shapeChecks[position]) {
                    clearCheck(shapeChecks);
                }
                shapeChecks[position] = !shapeChecks[position];

                commonAdapter.notifyDataSetChanged();

            }
        });
        setListViewHeightBasedOnChildren(gvType, 5);
    }

    private void initType() {
        List<ModelDetailResult.DataEntity.StoneTypeEntity> listmore = stoneDetail.getStoneTypeItme();
        List<ModelDetailResult.DataEntity.StoneTypeEntity> listless = listmore.subList(0,9);
        typeChecks = new boolean[listmore.size()];
        final CommonAdapter commonAdapter = new CommonAdapter<ModelDetailResult.DataEntity.StoneTypeEntity>(listmore, R.layout.item_gv_text) {
            @Override
            public void convert(int position, BaseViewHolder helper, ModelDetailResult.DataEntity.StoneTypeEntity item) {
                if (typeChecks[position]) {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_red_bg, getResources().getColor(R.color.white));
                } else {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_white_bg, getResources().getColor(R.color.text_color));
                }
            }
        };
        final CommonAdapter commonAdapter2 = new CommonAdapter<ModelDetailResult.DataEntity.StoneTypeEntity>(listless, R.layout.item_gv_text) {
            @Override
            public void convert(int position, BaseViewHolder helper, ModelDetailResult.DataEntity.StoneTypeEntity item) {
                if (typeChecks[position]) {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_red_bg, getResources().getColor(R.color.white));
                } else {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_white_bg, getResources().getColor(R.color.text_color));
                }
            }
        };
        gvType.setAdapter(commonAdapter2);
        gvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!typeChecks[position]) {
                    clearCheck(typeChecks);
                    tvType.setText(stoneDetail.getStoneTypeItme().get(position).getTitle());
                } else {
                    tvType.setText("");
                }
                typeChecks[position] = !typeChecks[position];
                commonAdapter.notifyDataSetChanged();

            }
        });
        setListViewHeightBasedOnChildren(gvType, 5);
        tvIsshowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowMore=!isShowMore;
                if(isShowMore){
                    gvType.setAdapter(commonAdapter);
                    tvIsshowMore.setText("收起来");
                }else {
                    gvType.setAdapter(commonAdapter2);
                    tvIsshowMore.setText("显示更多");
                }
                setListViewHeightBasedOnChildren(gvType, 5);
            }
        });
    }

    private void clearCheck(boolean[] Checks) {
        for (int i = 0; i < Checks.length; i++) {
            Checks[i] = false;
        }
    }

    private void initPurity() {
        purityChecks = new boolean[stoneDetail.getStonePurityItme().size()];
        final CommonAdapter commonAdapter = new CommonAdapter<ModelDetailResult.DataEntity.StonePurityEntity>(stoneDetail.getStonePurityItme(), R.layout.item_gv_text) {
            @Override
            public void convert(int position, BaseViewHolder helper, ModelDetailResult.DataEntity.StonePurityEntity item) {
                if (purityChecks[position]) {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_red_bg, getResources().getColor(R.color.white));
                } else {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_white_bg, getResources().getColor(R.color.text_color));
                }
            }
        };
        gvQuality.setAdapter(commonAdapter);
        gvQuality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!purityChecks[position]) {
                    clearCheck(purityChecks);
                    tvQuality.setText(stoneDetail.getStonePurityItme().get(position).getTitle());
                } else {
                    tvQuality.setText("");
                }
                purityChecks[position] = !purityChecks[position];
                commonAdapter.notifyDataSetChanged();

            }
        });
        setListViewHeightBasedOnChildren(gvQuality, 5);
    }


    private void initColor() {
        List<ModelDetailResult.DataEntity.StoneColorEntity> colorList = stoneDetail.getStoneColorItme();
        colorChecks = new boolean[colorList.size()];
        final CommonAdapter colorAdapter = new CommonAdapter<ModelDetailResult.DataEntity.StoneColorEntity>(colorList, R.layout.item_gv_text) {
            @Override
            public void convert(int position, BaseViewHolder helper, ModelDetailResult.DataEntity.StoneColorEntity item) {
                if (colorChecks[position]) {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_red_bg, getResources().getColor(R.color.white));
                } else {
                    helper.setText(R.id.tv_item_text, item.getTitle(), R.drawable.corners_white_bg, getResources().getColor(R.color.text_color));
                }

            }

        };
        gvColor.setAdapter(colorAdapter);
        gvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!colorChecks[position]) {
                    clearCheck(colorChecks);
                    tvColor.setText(stoneDetail.getStoneColorItme().get(position).getTitle());
                } else {
                    tvColor.setText("");
                }
                colorChecks[position] = !colorChecks[position];
                colorAdapter.notifyDataSetChanged();

            }
        });
        setListViewHeightBasedOnChildren(gvColor, 7);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
