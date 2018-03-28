package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class GetScanData {

    /**
     * response :
     * error : 0
     * message :
     * data : {"modelPuritys":[{"id":49,"title":"14K黄白分色"},{"id":8,"title":"Au750白"},{"id":5,"title":"G18K白"},{"id":4,"title":"G18K黄"},{"id":6,"title":"G18K玫瑰金"},{"id":7,"title":"G18K玫瑰金分色"},{"id":9,"title":"G750白"},{"id":3,"title":"Pd950"},{"id":1,"title":"PT900"},{"id":2,"title":"PT950"},{"id":47,"title":"千足黄金"}],"waitOrderCount":"112"}
     */

    private String response;
    private int error;
    private String message;
    private DataBean data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * modelPuritys : [{"id":49,"title":"14K黄白分色"},{"id":8,"title":"Au750白"},{"id":5,"title":"G18K白"},{"id":4,"title":"G18K黄"},{"id":6,"title":"G18K玫瑰金"},{"id":7,"title":"G18K玫瑰金分色"},{"id":9,"title":"G750白"},{"id":3,"title":"Pd950"},{"id":1,"title":"PT900"},{"id":2,"title":"PT950"},{"id":47,"title":"千足黄金"}]
         * waitOrderCount : 112
         */

        private String waitOrderCount;
        private List<PurityEntity> modelPuritys;

        public String getWaitOrderCount() {
            return waitOrderCount;
        }

        public void setWaitOrderCount(String waitOrderCount) {
            this.waitOrderCount = waitOrderCount;
        }

        public List<PurityEntity> getModelPuritys() {
            return modelPuritys;
        }

        public void setModelPuritys(List<PurityEntity> modelPuritys) {
            this.modelPuritys = modelPuritys;
        }


    }
}
