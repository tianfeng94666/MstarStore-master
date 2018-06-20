package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public class GetOrderDivideByCustomerResult {
    /**
     * response :
     * error : 0
     * message :
     * data : {"customerOrderlist":{"list_count":405,"orderList":[{"customerID":20852,"count":1,"customerName":"项友富-林国生订做","orderDate":"2018-05-04 16:11:35"},{"customerID":25801,"count":1,"customerName":"六六福深圳展厅-灵动","orderDate":"2018-05-04 16:38:32"},{"customerID":25846,"count":2,"customerName":"卡仑帝深圳展厅补单","orderDate":"2018-05-07 09:24:47"},{"customerID":4704,"count":1,"customerName":"山东东营百大千禧加盟","orderDate":"2018-05-07 09:27:40"},{"customerID":24574,"count":5,"customerName":"正天阁-DZ","orderDate":"2018-05-07 10:40:20"},{"customerID":21607,"count":21,"customerName":"山西太原裴晓彤周大金加盟","orderDate":"2018-05-07 14:10:57"},{"customerID":24253,"count":29,"customerName":"正天阁A","orderDate":"2018-05-08 13:39:15"},{"customerID":24880,"count":3,"customerName":"柜台批发2-客订","orderDate":"2018-05-08 15:15:59"},{"customerID":25828,"count":1,"customerName":"爱恋珠宝（深圳）18050201D","orderDate":"2018-05-08 16:29:32"},{"customerID":25829,"count":1,"customerName":"爱恋珠宝（深圳）18032431D","orderDate":"2018-05-08 16:31:11"},{"customerID":25821,"count":1,"customerName":"爱恋珠宝（深圳）18042803D","orderDate":"2018-05-08 16:33:41"},{"customerID":25843,"count":1,"customerName":"爱恋珠宝（深圳）18041136D","orderDate":"2018-05-08 16:43:40"},{"customerID":25850,"count":1,"customerName":"爱恋珠宝（深圳）18040816D","orderDate":"2018-05-08 16:44:15"},{"customerID":25844,"count":1,"customerName":"爱恋珠宝（深圳）18040814D","orderDate":"2018-05-08 16:44:37"},{"customerID":25773,"count":1,"customerName":"爱恋珠宝（深圳）18020625D","orderDate":"2018-05-08 16:45:02"},{"customerID":24030,"count":2,"customerName":"柜台批发2","orderDate":"2018-05-09 11:00:33"},{"customerID":22378,"count":16,"customerName":"南京张彩萍-客定（周大金加盟）","orderDate":"2018-05-09 11:37:23"},{"customerID":24045,"count":1,"customerName":"福建周六福-陈金友","orderDate":"2018-05-09 12:12:15"},{"customerID":24858,"count":1,"customerName":"福建周六福-吴金坤","orderDate":"2018-05-09 12:27:05"},{"customerID":2692,"count":6,"customerName":"新疆恒久","orderDate":"2018-05-09 14:23:19"},{"customerID":24185,"count":6,"customerName":"福建周六福-陈振忠","orderDate":"2018-05-09 14:25:34"},{"customerID":29,"count":1,"customerName":"真优美          ","orderDate":"2018-05-09 14:35:37"},{"customerID":15943,"count":4,"customerName":"汇福-配对","orderDate":"2018-05-09 17:23:12"},{"customerID":25880,"count":1,"customerName":"爱恋珠宝（深圳）18050904A","orderDate":"2018-05-09 17:46:19"},{"customerID":24461,"count":16,"customerName":"付周A","orderDate":"2018-05-09 20:07:02"},{"customerID":22266,"count":1,"customerName":"钰宝光珠宝","orderDate":"2018-05-10 11:43:11"},{"customerID":25882,"count":1,"customerName":"柜台批发2-宝格丽大师设计款","orderDate":"2018-05-10 15:18:56"},{"customerID":21204,"count":2,"customerName":"廊坊黄爱鑫","orderDate":"2018-05-11 13:25:03"},{"customerID":11592,"count":8,"customerName":"山西长治千禧加盟","orderDate":"2018-05-11 15:03:39"},{"customerID":10711,"count":30,"customerName":"太平洋珠宝订做","orderDate":"2018-05-11 16:00:55"}]}}
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
         * customerOrderlist : {"list_count":405,"orderList":[{"customerID":20852,"count":1,"customerName":"项友富-林国生订做","orderDate":"2018-05-04 16:11:35"},{"customerID":25801,"count":1,"customerName":"六六福深圳展厅-灵动","orderDate":"2018-05-04 16:38:32"},{"customerID":25846,"count":2,"customerName":"卡仑帝深圳展厅补单","orderDate":"2018-05-07 09:24:47"},{"customerID":4704,"count":1,"customerName":"山东东营百大千禧加盟","orderDate":"2018-05-07 09:27:40"},{"customerID":24574,"count":5,"customerName":"正天阁-DZ","orderDate":"2018-05-07 10:40:20"},{"customerID":21607,"count":21,"customerName":"山西太原裴晓彤周大金加盟","orderDate":"2018-05-07 14:10:57"},{"customerID":24253,"count":29,"customerName":"正天阁A","orderDate":"2018-05-08 13:39:15"},{"customerID":24880,"count":3,"customerName":"柜台批发2-客订","orderDate":"2018-05-08 15:15:59"},{"customerID":25828,"count":1,"customerName":"爱恋珠宝（深圳）18050201D","orderDate":"2018-05-08 16:29:32"},{"customerID":25829,"count":1,"customerName":"爱恋珠宝（深圳）18032431D","orderDate":"2018-05-08 16:31:11"},{"customerID":25821,"count":1,"customerName":"爱恋珠宝（深圳）18042803D","orderDate":"2018-05-08 16:33:41"},{"customerID":25843,"count":1,"customerName":"爱恋珠宝（深圳）18041136D","orderDate":"2018-05-08 16:43:40"},{"customerID":25850,"count":1,"customerName":"爱恋珠宝（深圳）18040816D","orderDate":"2018-05-08 16:44:15"},{"customerID":25844,"count":1,"customerName":"爱恋珠宝（深圳）18040814D","orderDate":"2018-05-08 16:44:37"},{"customerID":25773,"count":1,"customerName":"爱恋珠宝（深圳）18020625D","orderDate":"2018-05-08 16:45:02"},{"customerID":24030,"count":2,"customerName":"柜台批发2","orderDate":"2018-05-09 11:00:33"},{"customerID":22378,"count":16,"customerName":"南京张彩萍-客定（周大金加盟）","orderDate":"2018-05-09 11:37:23"},{"customerID":24045,"count":1,"customerName":"福建周六福-陈金友","orderDate":"2018-05-09 12:12:15"},{"customerID":24858,"count":1,"customerName":"福建周六福-吴金坤","orderDate":"2018-05-09 12:27:05"},{"customerID":2692,"count":6,"customerName":"新疆恒久","orderDate":"2018-05-09 14:23:19"},{"customerID":24185,"count":6,"customerName":"福建周六福-陈振忠","orderDate":"2018-05-09 14:25:34"},{"customerID":29,"count":1,"customerName":"真优美          ","orderDate":"2018-05-09 14:35:37"},{"customerID":15943,"count":4,"customerName":"汇福-配对","orderDate":"2018-05-09 17:23:12"},{"customerID":25880,"count":1,"customerName":"爱恋珠宝（深圳）18050904A","orderDate":"2018-05-09 17:46:19"},{"customerID":24461,"count":16,"customerName":"付周A","orderDate":"2018-05-09 20:07:02"},{"customerID":22266,"count":1,"customerName":"钰宝光珠宝","orderDate":"2018-05-10 11:43:11"},{"customerID":25882,"count":1,"customerName":"柜台批发2-宝格丽大师设计款","orderDate":"2018-05-10 15:18:56"},{"customerID":21204,"count":2,"customerName":"廊坊黄爱鑫","orderDate":"2018-05-11 13:25:03"},{"customerID":11592,"count":8,"customerName":"山西长治千禧加盟","orderDate":"2018-05-11 15:03:39"},{"customerID":10711,"count":30,"customerName":"太平洋珠宝订做","orderDate":"2018-05-11 16:00:55"}]}
         */

        private CustomerOrderlistBean customerOrderlist;

        public CustomerOrderlistBean getCustomerOrderlist() {
            return customerOrderlist;
        }

        public void setCustomerOrderlist(CustomerOrderlistBean customerOrderlist) {
            this.customerOrderlist = customerOrderlist;
        }

        public static class CustomerOrderlistBean {
            /**
             * list_count : 405
             * orderList : [{"customerID":20852,"count":1,"customerName":"项友富-林国生订做","orderDate":"2018-05-04 16:11:35"},{"customerID":25801,"count":1,"customerName":"六六福深圳展厅-灵动","orderDate":"2018-05-04 16:38:32"},{"customerID":25846,"count":2,"customerName":"卡仑帝深圳展厅补单","orderDate":"2018-05-07 09:24:47"},{"customerID":4704,"count":1,"customerName":"山东东营百大千禧加盟","orderDate":"2018-05-07 09:27:40"},{"customerID":24574,"count":5,"customerName":"正天阁-DZ","orderDate":"2018-05-07 10:40:20"},{"customerID":21607,"count":21,"customerName":"山西太原裴晓彤周大金加盟","orderDate":"2018-05-07 14:10:57"},{"customerID":24253,"count":29,"customerName":"正天阁A","orderDate":"2018-05-08 13:39:15"},{"customerID":24880,"count":3,"customerName":"柜台批发2-客订","orderDate":"2018-05-08 15:15:59"},{"customerID":25828,"count":1,"customerName":"爱恋珠宝（深圳）18050201D","orderDate":"2018-05-08 16:29:32"},{"customerID":25829,"count":1,"customerName":"爱恋珠宝（深圳）18032431D","orderDate":"2018-05-08 16:31:11"},{"customerID":25821,"count":1,"customerName":"爱恋珠宝（深圳）18042803D","orderDate":"2018-05-08 16:33:41"},{"customerID":25843,"count":1,"customerName":"爱恋珠宝（深圳）18041136D","orderDate":"2018-05-08 16:43:40"},{"customerID":25850,"count":1,"customerName":"爱恋珠宝（深圳）18040816D","orderDate":"2018-05-08 16:44:15"},{"customerID":25844,"count":1,"customerName":"爱恋珠宝（深圳）18040814D","orderDate":"2018-05-08 16:44:37"},{"customerID":25773,"count":1,"customerName":"爱恋珠宝（深圳）18020625D","orderDate":"2018-05-08 16:45:02"},{"customerID":24030,"count":2,"customerName":"柜台批发2","orderDate":"2018-05-09 11:00:33"},{"customerID":22378,"count":16,"customerName":"南京张彩萍-客定（周大金加盟）","orderDate":"2018-05-09 11:37:23"},{"customerID":24045,"count":1,"customerName":"福建周六福-陈金友","orderDate":"2018-05-09 12:12:15"},{"customerID":24858,"count":1,"customerName":"福建周六福-吴金坤","orderDate":"2018-05-09 12:27:05"},{"customerID":2692,"count":6,"customerName":"新疆恒久","orderDate":"2018-05-09 14:23:19"},{"customerID":24185,"count":6,"customerName":"福建周六福-陈振忠","orderDate":"2018-05-09 14:25:34"},{"customerID":29,"count":1,"customerName":"真优美          ","orderDate":"2018-05-09 14:35:37"},{"customerID":15943,"count":4,"customerName":"汇福-配对","orderDate":"2018-05-09 17:23:12"},{"customerID":25880,"count":1,"customerName":"爱恋珠宝（深圳）18050904A","orderDate":"2018-05-09 17:46:19"},{"customerID":24461,"count":16,"customerName":"付周A","orderDate":"2018-05-09 20:07:02"},{"customerID":22266,"count":1,"customerName":"钰宝光珠宝","orderDate":"2018-05-10 11:43:11"},{"customerID":25882,"count":1,"customerName":"柜台批发2-宝格丽大师设计款","orderDate":"2018-05-10 15:18:56"},{"customerID":21204,"count":2,"customerName":"廊坊黄爱鑫","orderDate":"2018-05-11 13:25:03"},{"customerID":11592,"count":8,"customerName":"山西长治千禧加盟","orderDate":"2018-05-11 15:03:39"},{"customerID":10711,"count":30,"customerName":"太平洋珠宝订做","orderDate":"2018-05-11 16:00:55"}]
             */

            private int list_count;
            private List<OrderListBean> orderList;

            public int getList_count() {
                return list_count;
            }

            public void setList_count(int list_count) {
                this.list_count = list_count;
            }

            public List<OrderListBean> getOrderList() {
                return orderList;
            }

            public void setOrderList(List<OrderListBean> orderList) {
                this.orderList = orderList;
            }

            public static class OrderListBean {
                /**
                 * customerID : 20852
                 * count : 1
                 * customerName : 项友富-林国生订做
                 * orderDate : 2018-05-04 16:11:35
                 */

                private int customerID;
                private int count;
                private String customerName;
                private String orderDate;

                public int getCustomerID() {
                    return customerID;
                }

                public void setCustomerID(int customerID) {
                    this.customerID = customerID;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getCustomerName() {
                    return customerName;
                }

                public void setCustomerName(String customerName) {
                    this.customerName = customerName;
                }

                public String getOrderDate() {
                    return orderDate;
                }

                public void setOrderDate(String orderDate) {
                    this.orderDate = orderDate;
                }
            }
        }
    }
}
