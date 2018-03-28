package com.qx.mstarstoreapp.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class PurityEntity implements Serializable {
    /**
     * id : 1
     * title : SI
     */
    private String id;
    private String title;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
