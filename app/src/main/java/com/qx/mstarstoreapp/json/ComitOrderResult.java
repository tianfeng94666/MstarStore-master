package com.qx.mstarstoreapp.json;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class ComitOrderResult {

    /**
     * data : {"Ailpay":{"orderId":"AP170209532","out_trade_no":"350547653655960010","proName":"千禧之星订单支付:AP170209532","probody":"Y06155-60","total_fee":0.01},"needPayPrice":0.01}
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
        /**
         * Ailpay : {"orderId":"AP170209532","out_trade_no":"350547653655960010","proName":"千禧之星订单支付:AP170209532","probody":"Y06155-60","total_fee":0.01}
         * needPayPrice : 0.01
         */

        private AilpayBean Ailpay;
        private double needPayPrice;

        public AilpayBean getAilpay() {
            return Ailpay;
        }

        public void setAilpay(AilpayBean Ailpay) {
            this.Ailpay = Ailpay;
        }

        public double getNeedPayPrice() {
            return needPayPrice;
        }

        public void setNeedPayPrice(double needPayPrice) {
            this.needPayPrice = needPayPrice;
        }

        public static class AilpayBean {
            /**
             * orderId : AP170209532
             * out_trade_no : 350547653655960010
             * proName : 千禧之星订单支付:AP170209532
             * probody : Y06155-60
             * total_fee : 0.01
             */

            private String orderId;
            private String out_trade_no;
            private String proName;
            private String probody;
            private String total_fee;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getProName() {
                return proName;
            }

            public void setProName(String proName) {
                this.proName = proName;
            }

            public String getProbody() {
                return probody;
            }

            public void setProbody(String probody) {
                this.probody = probody;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }
        }
    }
}
