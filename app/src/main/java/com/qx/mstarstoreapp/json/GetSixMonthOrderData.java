package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public class GetSixMonthOrderData {

    /**
     * data : {"dateTableCount":[{"appcount":"570","appmycount":"192","count":"753","mycount":"192"},{"appcount":"120","appmycount":"32","count":"136","mycount":"32"},{"appcount":"920","appmycount":"283","count":"1275","mycount":"283"},{"appcount":"563","appmycount":"170","count":"707","mycount":"170"},{"appcount":"626","appmycount":"217","count":"740","mycount":"217"},{"appcount":"273","appmycount":"86","count":"336","mycount":"86"}]}
     * error : 0
     * message :
     * response :
     */

    private DataBean data;
    private int error;
    private String message;
    private String response;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
             * appcount : 570
             * appmycount : 192
             * count : 753
             * mycount : 192
             */

            private String appcount;
            private String appmycount;
            private String count;
            private String mycount;

            public String getAppcount() {
                return appcount;
            }

            public void setAppcount(String appcount) {
                this.appcount = appcount;
            }

            public String getAppmycount() {
                return appmycount;
            }

            public void setAppmycount(String appmycount) {
                this.appmycount = appmycount;
            }

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
