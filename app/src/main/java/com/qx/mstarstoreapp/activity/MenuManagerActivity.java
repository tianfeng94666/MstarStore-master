package com.qx.mstarstoreapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.adapter.BaseViewHolder;
import com.qx.mstarstoreapp.adapter.CommonAdapter;
import com.qx.mstarstoreapp.base.BaseActivity;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.MenuItemBean;
import com.qx.mstarstoreapp.greendao.gen.MenuItemBeanDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class MenuManagerActivity extends BaseActivity {


    List menuList;
    @Bind(R.id.id_ig_back)
    ImageView idIgBack;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.layout_rl_title)
    RelativeLayout layoutRlTitle;
    @Bind(R.id.lv_menu)
    ListView lvMenu;


    private MenuItemBeanDao menuItemBeanDao;

    @Override
    public void loadNetData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleText.setText("菜单设置");
        menuItemBeanDao = BaseApplication.getApplication().getDaoSession().getMenuItemBeanDao();
        menuList = menuItemBeanDao.loadAll();
        if (menuList == null || menuList.size() == 0) {
            //进行菜单初始化
            initMenu();
            setLvAdapter();
        } else {
            setLvAdapter();
        }

    }

    private void initMenu() {
        MenuItemBean showGoldenPrice, orderReview, orderSearch;
        showGoldenPrice = new MenuItemBean(null, "显示金价", 0);
        orderReview = new MenuItemBean(null, "订单审核", 0);
        orderSearch = new MenuItemBean(null, "我的订单", 0);

        menuList.add(showGoldenPrice);
        menuList.add(orderReview);
        menuList.add(orderSearch);
        //存入数据库
        menuItemBeanDao.insert(showGoldenPrice);
        menuItemBeanDao.insert(orderReview);
        menuItemBeanDao.insert(orderSearch);
    }

    public void setLvAdapter() {
        lvMenu.setAdapter(new CommonAdapter<MenuItemBean>(menuList, R.layout.item_menu2) {
            @Override
            public void convert(int position, BaseViewHolder helper, final MenuItemBean item) {
                TextView tv = helper.getView(R.id.tv_menu);
                final ImageView iv = helper.getView(R.id.iv_is_show);
                tv.setText(item.getName());
                if (item.getIshow() == 0) {
                    iv.setImageResource(R.drawable.icon_switch_off);
                } else {
                    iv.setImageResource(R.drawable.icon_switch_on);
                }
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getIshow() == 0) {
                            item.setIshow(1);
                        } else {
                            item.setIshow(0);
                        }
                        if (item.getIshow() == 0) {
                            iv.setImageResource(R.drawable.icon_switch_off);
                        } else {
                            iv.setImageResource(R.drawable.icon_switch_on);
                        }
                        menuItemBeanDao.update(item);
                    }
                });
            }
        });
    }


}
