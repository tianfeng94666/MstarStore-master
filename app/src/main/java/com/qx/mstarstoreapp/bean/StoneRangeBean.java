package com.qx.mstarstoreapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/9 0009.
 */
@Entity
public class StoneRangeBean {
    @Id (autoincrement = true)
    private Long id;
    private String stoneMin;
    private String stoneMax;
    private String amount;
    private int isChoose;
    @Generated(hash = 570815100)
    public StoneRangeBean(Long id, String stoneMin, String stoneMax, String amount,
            int isChoose) {
        this.id = id;
        this.stoneMin = stoneMin;
        this.stoneMax = stoneMax;
        this.amount = amount;
        this.isChoose = isChoose;
    }
    @Generated(hash = 655988030)
    public StoneRangeBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStoneMin() {
        return this.stoneMin;
    }
    public void setStoneMin(String stoneMin) {
        this.stoneMin = stoneMin;
    }
    public String getStoneMax() {
        return this.stoneMax;
    }
    public void setStoneMax(String stoneMax) {
        this.stoneMax = stoneMax;
    }
    public String getAmount() {
        return this.amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public int getIsChoose() {
        return this.isChoose;
    }
    public void setIsChoose(int isChoose) {
        this.isChoose = isChoose;
    }
 
}
