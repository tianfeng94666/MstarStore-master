package com.qx.mstarstoreapp.json;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class ExcelOrderBean implements Serializable{
    private String id;
    private String modelNum;
    private String number;
    private String purity;
    private String iserror;
    private String handSize;

    public String getHandSize() {
        return handSize;
    }

    public void setHandSize(String handSize) {
        this.handSize = handSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelNum() {
        return modelNum;
    }

    public void setModelNum(String modelNum) {
        this.modelNum = modelNum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public String getIserror() {
        return iserror;
    }

    public void setIserror(String iserror) {
        this.iserror = iserror;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
