package com.qx.mstarstoreapp.base;

import android.app.Activity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2018/4/25 0025.
 */
@Entity
public class MenuItemBean {
    @Id(autoincrement = true)
    private Long id;
    String name;
    int ishow;
    @Generated(hash = 1409838109)
    public MenuItemBean(Long id, String name, int ishow) {
        this.id = id;
        this.name = name;
        this.ishow = ishow;
    }
    @Generated(hash = 1019886793)
    public MenuItemBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIshow() {
        return this.ishow;
    }
    public void setIshow(int ishow) {
        this.ishow = ishow;
    }

   


    
}
