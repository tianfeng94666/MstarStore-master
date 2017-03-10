package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class DeliveryTableResult {
    /**
     * response :
     * error : 0
     * message :
     * data : {"moItem":{"moNum":1702220025,"orderNum":"AP170216110","purityName":"Au750白","goldPrice":208.1,"number":1,"moDate":"2017-02-22 17:21:48","customerName":"世爵百年KD3767-ZZ","totalPrice":612.63},"modelList":[{"modelNum":"QY0134-30","typeName":"女戒","unitPrice":612.63,"pic":"http://124.172.169.117:9888/220X220/Module/Image1/2/QY0134-30.jpg","sInfo":"手寸:11 毛重:2.61 净金重:2.5496 损耗:1.1","dInfo":"基本费用:29 附加费用:0 其他费用:0 起版费:0 单价成本:612.63","remark":"备注:"}]}
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
         * moItem : {"moNum":1702220025,"orderNum":"AP170216110","purityName":"Au750白","goldPrice":208.1,"number":1,"moDate":"2017-02-22 17:21:48","customerName":"世爵百年KD3767-ZZ","totalPrice":612.63}
         * modelList : [{"modelNum":"QY0134-30","typeName":"女戒","unitPrice":612.63,"pic":"http://124.172.169.117:9888/220X220/Module/Image1/2/QY0134-30.jpg","sInfo":"手寸:11 毛重:2.61 净金重:2.5496 损耗:1.1","dInfo":"基本费用:29 附加费用:0 其他费用:0 起版费:0 单价成本:612.63","remark":"备注:"}]
         */

        private MoItemBean moItem;
        private List<ModelListBean> modelList;

        public MoItemBean getMoItem() {
            return moItem;
        }

        public void setMoItem(MoItemBean moItem) {
            this.moItem = moItem;
        }

        public List<ModelListBean> getModelList() {
            return modelList;
        }

        public void setModelList(List<ModelListBean> modelList) {
            this.modelList = modelList;
        }

        public static class MoItemBean {
            /**
             * moNum : 1702220025
             * orderNum : AP170216110
             * purityName : Au750白
             * goldPrice : 208.1
             * number : 1
             * moDate : 2017-02-22 17:21:48
             * customerName : 世爵百年KD3767-ZZ
             * totalPrice : 612.63
             */

            private int moNum;
            private String orderNum;
            private String purityName;
            private double goldPrice;
            private int number;
            private String moDate;
            private String customerName;
            private double totalPrice;

            public int getMoNum() {
                return moNum;
            }

            public void setMoNum(int moNum) {
                this.moNum = moNum;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getPurityName() {
                return purityName;
            }

            public void setPurityName(String purityName) {
                this.purityName = purityName;
            }

            public double getGoldPrice() {
                return goldPrice;
            }

            public void setGoldPrice(double goldPrice) {
                this.goldPrice = goldPrice;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getMoDate() {
                return moDate;
            }

            public void setMoDate(String moDate) {
                this.moDate = moDate;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }
        }

        public static class ModelListBean {
            /**
             * modelNum : QY0134-30
             * typeName : 女戒
             * unitPrice : 612.63
             * pic : http://124.172.169.117:9888/220X220/Module/Image1/2/QY0134-30.jpg
             * sInfo : 手寸:11 毛重:2.61 净金重:2.5496 损耗:1.1
             * dInfo : 基本费用:29 附加费用:0 其他费用:0 起版费:0 单价成本:612.63
             * remark : 备注:
             */

            private String modelNum;
            private String typeName;
            private double unitPrice;
            private String pic;
            private String sInfo;
            private String dInfo;
            private String remark;
            private boolean isChoose;

            public boolean isChoose() {
                return isChoose;
            }

            public void setChoose(boolean choose) {
                isChoose = choose;
            }

            public String getModelNum() {
                return modelNum;
            }

            public void setModelNum(String modelNum) {
                this.modelNum = modelNum;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public double getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(double unitPrice) {
                this.unitPrice = unitPrice;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getSInfo() {
                return sInfo;
            }

            public void setSInfo(String sInfo) {
                this.sInfo = sInfo;
            }

            public String getDInfo() {
                return dInfo;
            }

            public void setDInfo(String dInfo) {
                this.dInfo = dInfo;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
