package com.qx.mstarstoreapp.inter;

import com.qx.mstarstoreapp.json.OrderListResult;
import com.qx.mstarstoreapp.json.StoneOrderResult;

import java.util.Map;

public interface AdapterCallBack {

    void changeState(Map<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> checked);

}
