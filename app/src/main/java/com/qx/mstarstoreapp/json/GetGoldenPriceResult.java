package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class GetGoldenPriceResult {

    /**
     * response :
     * error : 0
     * message :
     * data : {"purityGoldPrice":[{"PurityID":8,"PurityName":"Au750白","UnitPrice":175.2,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":5,"PurityName":"G18K白","UnitPrice":175.2,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":4,"PurityName":"G18K黄","UnitPrice":175.2,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":6,"PurityName":"G18K玫瑰金","UnitPrice":175.2,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":7,"PurityName":"G18K玫瑰金分色","UnitPrice":175.2,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":9,"PurityName":"G750白","UnitPrice":175.2,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":3,"PurityName":"Pd950","UnitPrice":109.7,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":1,"PurityName":"PT900","UnitPrice":176.1,"GoldDate":"2016-01-15 11:47:26"},{"PurityID":2,"PurityName":"PT950","UnitPrice":179.6,"GoldDate":"2016-01-15 11:47:26"}]}
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
        private List<PurityGoldPriceBean> purityGoldPrice;

        public List<PurityGoldPriceBean> getPurityGoldPrice() {
            return purityGoldPrice;
        }

        public void setPurityGoldPrice(List<PurityGoldPriceBean> purityGoldPrice) {
            this.purityGoldPrice = purityGoldPrice;
        }

        public static class PurityGoldPriceBean {
            /**
             * PurityID : 8
             * PurityName : Au750白
             * UnitPrice : 175.2
             * GoldDate : 2016-01-15 11:47:26
             */

            private int PurityID;
            private String PurityName;
            private double UnitPrice;
            private String GoldDate;

            public int getPurityID() {
                return PurityID;
            }

            public void setPurityID(int PurityID) {
                this.PurityID = PurityID;
            }

            public String getPurityName() {
                return PurityName;
            }

            public void setPurityName(String PurityName) {
                this.PurityName = PurityName;
            }

            public double getUnitPrice() {
                return UnitPrice;
            }

            public void setUnitPrice(double UnitPrice) {
                this.UnitPrice = UnitPrice;
            }

            public String getGoldDate() {
                return GoldDate;
            }

            public void setGoldDate(String GoldDate) {
                this.GoldDate = GoldDate;
            }
        }
    }
}
