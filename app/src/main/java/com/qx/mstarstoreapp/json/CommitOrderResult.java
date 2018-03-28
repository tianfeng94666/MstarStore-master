package com.qx.mstarstoreapp.json;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class CommitOrderResult {

    /**
     * response :
     * error : 0
     * message : 添加到当前订单成功
     * data : {"waitOrderCount":"112"}
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
         * waitOrderCount : 112
         */

        private String waitOrderCount;

        public String getWaitOrderCount() {
            return waitOrderCount;
        }

        public void setWaitOrderCount(String waitOrderCount) {
            this.waitOrderCount = waitOrderCount;
        }
    }
}
