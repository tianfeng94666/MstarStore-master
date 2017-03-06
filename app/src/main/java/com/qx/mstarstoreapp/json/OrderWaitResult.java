package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class OrderWaitResult {

    /**
     * data : {"orderList":{"list_count":"1","list":[{"otherInfo":"成色:G18K黄; 质量等级:普通; 字印:11; 金价:209.7/g; 件数:1","needPayPrice":473.04,"modifyDate":"2016-11-14 09:02:46","totalPrice":4730.4,"orderStatusTitle":"待付定金","orderNum":"201611140902467200","id":"23","orderDate":"2016-11-14 09:02:46","pics":["http://appapi.fanerweb.com/Uploads/Pics/2016-09-21/57b55369N2495bed4.jpg"],"customerName":"旺道BS020"}]}}
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
         * orderList : {"list_count":"1","list":[{"otherInfo":"成色:G18K黄; 质量等级:普通; 字印:11; 金价:209.7/g; 件数:1","needPayPrice":473.04,"modifyDate":"2016-11-14 09:02:46","totalPrice":4730.4,"orderStatusTitle":"待付定金","orderNum":"201611140902467200","id":"23","orderDate":"2016-11-14 09:02:46","pics":["http://appapi.fanerweb.com/Uploads/Pics/2016-09-21/57b55369N2495bed4.jpg"],"customerName":"旺道BS020"}]}
         */
        private OrderListEntity orderList;

        public void setOrderList(OrderListEntity orderList) {
            this.orderList = orderList;
        }

        public OrderListEntity getOrderList() {
            return orderList;
        }

        public class OrderListEntity {
            /**
             * list_count : 1
             * list : [{"otherInfo":"成色:G18K黄; 质量等级:普通; 字印:11; 金价:209.7/g; 件数:1","needPayPrice":473.04,"modifyDate":"2016-11-14 09:02:46","totalPrice":4730.4,"orderStatusTitle":"待付定金","orderNum":"201611140902467200","id":"23","orderDate":"2016-11-14 09:02:46","pics":["http://appapi.fanerweb.com/Uploads/Pics/2016-09-21/57b55369N2495bed4.jpg"],"customerName":"旺道BS020"}]
             */
            private String list_count;
            private List<ListEntity> list;

            public void setList_count(String list_count) {
                this.list_count = list_count;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public String getList_count() {
                return list_count;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public class ListEntity {
                /**
                 * otherInfo : 成色:G18K黄; 质量等级:普通; 字印:11; 金价:209.7/g; 件数:1
                 * needPayPrice : 473.04
                 * modifyDate : 2016-11-14 09:02:46
                 * totalPrice : 4730.4
                 * orderStatusTitle : 待付定金
                 * orderNum : 201611140902467200
                 * id : 23
                 * orderDate : 2016-11-14 09:02:46
                 * pics : ["http://appapi.fanerweb.com/Uploads/Pics/2016-09-21/57b55369N2495bed4.jpg"]
                 * customerName : 旺道BS020
                 */
                private String otherInfo;
                private double needPayPrice;
                private String modifyDate;
                private double totalPrice;
                private String orderStatusTitle;
                private String orderNum;
                private String id;
                private String orderDate;
                private String customerName;
                private List<String> pics;

                private String confirmDate;

                public String getConfirmDate() {
                    return confirmDate;
                }

                public void setConfirmDate(String confirmDate) {
                    this.confirmDate = confirmDate;
                }

                public List<String> getPicsEntity() {
                    return pics;
                }

                public void setPicsEntity(List<String> picsEntity) {
                    this.pics = picsEntity;
                }

                @Override
                public String toString() {
                    return "ListEntity{" +
                            "otherInfo='" + otherInfo + '\'' +
                            ", needPayPrice=" + needPayPrice +
                            ", modifyDate='" + modifyDate + '\'' +
                            ", totalPrice=" + totalPrice +
                            ", orderStatusTitle='" + orderStatusTitle + '\'' +
                            ", orderNum='" + orderNum + '\'' +
                            ", id='" + id + '\'' +
                            ", orderDate='" + orderDate + '\'' +
                            ", customerName='" + customerName + '\'' +
                            '}';
                }

                public void setOtherInfo(String otherInfo) {
                    this.otherInfo = otherInfo;
                }

                public void setNeedPayPrice(double needPayPrice) {
                    this.needPayPrice = needPayPrice;
                }

                public void setModifyDate(String modifyDate) {
                    this.modifyDate = modifyDate;
                }

                public void setTotalPrice(double totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public void setOrderStatusTitle(String orderStatusTitle) {
                    this.orderStatusTitle = orderStatusTitle;
                }

                public void setOrderNum(String orderNum) {
                    this.orderNum = orderNum;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setOrderDate(String orderDate) {
                    this.orderDate = orderDate;
                }


                public void setCustomerName(String customerName) {
                    this.customerName = customerName;
                }

                public String getOtherInfo() {
                    return otherInfo;
                }

                public double getNeedPayPrice() {
                    return needPayPrice;
                }

                public String getModifyDate() {
                    return modifyDate;
                }

                public double getTotalPrice() {
                    return totalPrice;
                }

                public String getOrderStatusTitle() {
                    return orderStatusTitle;
                }

                public String getOrderNum() {
                    return orderNum;
                }

                public String getId() {
                    return id;
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


}
