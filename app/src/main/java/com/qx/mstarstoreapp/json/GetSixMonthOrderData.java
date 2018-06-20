package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public class GetSixMonthOrderData {

    /**
     * response :
     * error : 0
     * message :
     * data : {"dateTableCount":[{"count":"743","mycount":"0"},{"count":"570","mycount":"0"},{"count":"2000","mycount":"0"},{"count":"920","mycount":"0"},{"count":"960","mycount":"0"},{"count":"397","mycount":"0"}]}
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
        private List<DateTableCountBean> dateTableCount;

        public List<DateTableCountBean> getDateTableCount() {
            return dateTableCount;
        }

        public void setDateTableCount(List<DateTableCountBean> dateTableCount) {
            this.dateTableCount = dateTableCount;
        }

        public static class DateTableCountBean {
            /**
             * count : 743
             * mycount : 0
             */

            private String count;
            private String mycount;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getMycount() {
                return mycount;
            }

            public void setMycount(String mycount) {
                this.mycount = mycount;
            }
        }
    }
}
