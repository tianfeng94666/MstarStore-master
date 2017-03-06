package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */
public class ProductListResult  {


    /**
     * data : {"modelList":[{"baseInfo":"类型:皮带头;手寸:1","needPayPrice":"0","number":2,"stonePrice":"0","modelId":"21","price":"0","id":"124","pic":"http://appapi.fanerweb.com/Uploads/Pics/2016-09-21/577dd3b0N5baaf195.jpg","title":"18K金气质锁骨链群镶钻石链牌 弯弯的月亮女款K金项链吊坠 18K玫瑰金","info":"主石(类型:方钻,数量:1粒,规格:0~30,形状:马鞍,颜色:H+,纯度:SI)"}],"orderInfo":{"confirmDate":"2016-12-05 11:46:46","otherInfo":"成色:; 质量等级:; 字印:2222wa; 金价:0/g; 件数:1","needPayPrice":"0","address":"辽宁省 丹东市 振兴区 哄哄","totalPrice":"0","orderStatusTitle":"待生产","orderNum":"AP2016112310378","orderDate":"2016-11-23 10:32:48","customerName":"旺道-0112"}}
     * response :
     * error : 0
     * message :
     */
    private DataEntity data;
    private String response;
    private int error;
    private String message;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public String getResponse() {
        return response;
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public class DataEntity {
        /**
         * modelList : [{"baseInfo":"类型:皮带头;手寸:1","needPayPrice":"0","number":2,"stonePrice":"0","modelId":"21","price":"0","id":"124","pic":"http://appapi.fanerweb.com/Uploads/Pics/2016-09-21/577dd3b0N5baaf195.jpg","title":"18K金气质锁骨链群镶钻石链牌 弯弯的月亮女款K金项链吊坠 18K玫瑰金","info":"主石(类型:方钻,数量:1粒,规格:0~30,形状:马鞍,颜色:H+,纯度:SI)"}]
         * orderInfo : {"confirmDate":"2016-12-05 11:46:46","otherInfo":"成色:; 质量等级:; 字印:2222wa; 金价:0/g; 件数:1","needPayPrice":"0","address":"辽宁省 丹东市 振兴区 哄哄","totalPrice":"0","orderStatusTitle":"待生产","orderNum":"AP2016112310378","orderDate":"2016-11-23 10:32:48","customerName":"旺道-0112"}
         */
        private List<ModelListEntity> modelList;
        private OrderInfoEntity orderInfo;

        public void setModelList(List<ModelListEntity> modelList) {
            this.modelList = modelList;
        }

        public void setOrderInfo(OrderInfoEntity orderInfo) {
            this.orderInfo = orderInfo;
        }

        public List<ModelListEntity> getModelList() {
            return modelList;
        }

        public OrderInfoEntity getOrderInfo() {
            return orderInfo;
        }

        public class ModelListEntity {
            /**
             * baseInfo : 类型:皮带头;手寸:1
             * needPayPrice : 0
             * number : 2
             * stonePrice : 0
             * modelId : 21
             * price : 0
             * id : 124
             * pic : http://appapi.fanerweb.com/Uploads/Pics/2016-09-21/577dd3b0N5baaf195.jpg
             * title : 18K金气质锁骨链群镶钻石链牌 弯弯的月亮女款K金项链吊坠 18K玫瑰金
             * info : 主石(类型:方钻,数量:1粒,规格:0~30,形状:马鞍,颜色:H+,纯度:SI)
             */
            private String baseInfo;
            private String needPayPrice;
            private String number;
            private String stonePrice;
            private String modelId;
            private String price;
            private String id;
            private String pic;
            private String title;
            private String info;
            private String invoiceTitle;
            private String invoiceType;

            public String getInvoiceType() {
                return invoiceType;
            }

            public void setInvoiceType(String invoiceType) {
                this.invoiceType = invoiceType;
            }

            public String getInvoiceTitle() {
                return invoiceTitle;
            }

            public void setInvoiceTitle(String invoiceTitle) {
                this.invoiceTitle = invoiceTitle;
            }

            public void setBaseInfo(String baseInfo) {
                this.baseInfo = baseInfo;
            }

            public void setNeedPayPrice(String needPayPrice) {
                this.needPayPrice = needPayPrice;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public void setStonePrice(String stonePrice) {
                this.stonePrice = stonePrice;
            }

            public void setModelId(String modelId) {
                this.modelId = modelId;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getBaseInfo() {
                return baseInfo;
            }

            public String getNeedPayPrice() {
                return needPayPrice;
            }

            public String getNumber() {
                return number;
            }

            public String getStonePrice() {
                return stonePrice;
            }

            public String getModelId() {
                return modelId;
            }

            public String getPrice() {
                return price;
            }

            public String getId() {
                return id;
            }

            public String getPic() {
                return pic;
            }

            public String getTitle() {
                return title;
            }

            public String getInfo() {
                return info;
            }
        }

        public class OrderInfoEntity {
            /**
             * confirmDate : 2016-12-05 11:46:46
             * otherInfo : 成色:; 质量等级:; 字印:2222wa; 金价:0/g; 件数:1
             * needPayPrice : 0
             * address : 辽宁省 丹东市 振兴区 哄哄
             * totalPrice : 0
             * orderStatusTitle : 待生产
             * orderNum : AP2016112310378
             * orderDate : 2016-11-23 10:32:48
             * customerName : 旺道-0112
             */
            private String confirmDate;
            private String otherInfo;
            private String needPayPrice;
            private String address;
            private String totalPrice;
            private String orderStatusTitle;
            private String orderNum;
            private String orderDate;
            private String customerName;
            private String invoiceType;
            private String invoiceTitle;

            public String getInvoiceType() {
                return invoiceType;
            }

            public void setInvoiceType(String invoiceType) {
                this.invoiceType = invoiceType;
            }

            public String getInvoiceTitle() {
                return invoiceTitle;
            }

            public void setInvoiceTitle(String invoiceTitle) {
                this.invoiceTitle = invoiceTitle;
            }

            public void setConfirmDate(String confirmDate) {
                this.confirmDate = confirmDate;
            }

            public void setOtherInfo(String otherInfo) {
                this.otherInfo = otherInfo;
            }

            public void setNeedPayPrice(String needPayPrice) {
                this.needPayPrice = needPayPrice;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public void setOrderStatusTitle(String orderStatusTitle) {
                this.orderStatusTitle = orderStatusTitle;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getConfirmDate() {
                return confirmDate;
            }

            public String getOtherInfo() {
                return otherInfo;
            }

            public String getNeedPayPrice() {
                return needPayPrice;
            }

            public String getAddress() {
                return address;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public String getOrderStatusTitle() {
                return orderStatusTitle;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public String getOrderDate() {
                return orderDate;
            }

            public String getCustomerName() {
                return customerName;
            }
        }
    }
}
