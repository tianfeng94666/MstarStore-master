package com.qx.mstarstoreapp.inter;

import com.qx.mstarstoreapp.json.OrderListResult;

import java.util.Map;

public interface AdapterCallBack {

    void changeState(Map<String, OrderListResult.DataEntity.CurrentOrderlListEntity.ListEntity> checked);
}
