package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public class GetOrderDivideByCustomerResult {

    /**
     * data : {"customerOrderlist":{"list_count":8,"orderCount":12,"orderList":[{"count":1,"customerID":15987,"customerName":"深圳金叶珠宝","orderDate":"2018-06-20 11:15:35","quantity":1},{"count":1,"customerID":26047,"customerName":"山伊文化珠宝-陈文","orderDate":"2018-06-20 10:31:54","quantity":1},{"count":1,"customerID":26030,"customerName":"爱恋珠宝（深圳）18061302W","orderDate":"2018-06-19 16:04:41","quantity":1},{"count":1,"customerID":24880,"customerName":"柜台批发2-客订","orderDate":"2018-06-19 15:18:17","quantity":1},{"count":1,"customerID":17773,"customerName":"徐和平","orderDate":"2018-06-19 14:28:34","quantity":1},{"count":2,"customerID":26040,"customerName":"金珊-周（改款）","orderDate":"2018-06-19 11:41:11","quantity":85},{"count":2,"customerID":10711,"customerName":"太平洋珠宝订做","orderDate":"2018-06-19 11:40:53","quantity":2},{"count":3,"customerID":26033,"customerName":"金嘉利嘉福S066 GW8061604","orderDate":"2018-06-19 11:40:35","quantity":7}],"orderdDetailQuantityCount":99}}
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
         * customerOrderlist : {"list_count":8,"orderCount":12,"orderList":[{"count":1,"customerID":15987,"customerName":"深圳金叶珠宝","orderDate":"2018-06-20 11:15:35","quantity":1},{"count":1,"customerID":26047,"customerName":"山伊文化珠宝-陈文","orderDate":"2018-06-20 10:31:54","quantity":1},{"count":1,"customerID":26030,"customerName":"爱恋珠宝（深圳）18061302W","orderDate":"2018-06-19 16:04:41","quantity":1},{"count":1,"customerID":24880,"customerName":"柜台批发2-客订","orderDate":"2018-06-19 15:18:17","quantity":1},{"count":1,"customerID":17773,"customerName":"徐和平","orderDate":"2018-06-19 14:28:34","quantity":1},{"count":2,"customerID":26040,"customerName":"金珊-周（改款）","orderDate":"2018-06-19 11:41:11","quantity":85},{"count":2,"customerID":10711,"customerName":"太平洋珠宝订做","orderDate":"2018-06-19 11:40:53","quantity":2},{"count":3,"customerID":26033,"customerName":"金嘉利嘉福S066 GW8061604","orderDate":"2018-06-19 11:40:35","quantity":7}],"orderdDetailQuantityCount":99}
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
             * list_count : 8
             * orderCount : 12
             * orderList : [{"count":1,"customerID":15987,"customerName":"深圳金叶珠宝","orderDate":"2018-06-20 11:15:35","quantity":1},{"count":1,"customerID":26047,"customerName":"山伊文化珠宝-陈文","orderDate":"2018-06-20 10:31:54","quantity":1},{"count":1,"customerID":26030,"customerName":"爱恋珠宝（深圳）18061302W","orderDate":"2018-06-19 16:04:41","quantity":1},{"count":1,"customerID":24880,"customerName":"柜台批发2-客订","orderDate":"2018-06-19 15:18:17","quantity":1},{"count":1,"customerID":17773,"customerName":"徐和平","orderDate":"2018-06-19 14:28:34","quantity":1},{"count":2,"customerID":26040,"customerName":"金珊-周（改款）","orderDate":"2018-06-19 11:41:11","quantity":85},{"count":2,"customerID":10711,"customerName":"太平洋珠宝订做","orderDate":"2018-06-19 11:40:53","quantity":2},{"count":3,"customerID":26033,"customerName":"金嘉利嘉福S066 GW8061604","orderDate":"2018-06-19 11:40:35","quantity":7}]
             * orderdDetailQuantityCount : 99
             */

            private int list_count;
            private int orderCount;
            private int orderdDetailQuantityCount;
            private List<OrderListBean> orderList;

            public int getList_count() {
                return list_count;
            }

            public void setList_count(int list_count) {
                this.list_count = list_count;
            }

            public int getOrderCount() {
                return orderCount;
            }

            public void setOrderCount(int orderCount) {
                this.orderCount = orderCount;
            }

            public int getOrderdDetailQuantityCount() {
                return orderdDetailQuantityCount;
            }

            public void setOrderdDetailQuantityCount(int orderdDetailQuantityCount) {
                this.orderdDetailQuantityCount = orderdDetailQuantityCount;
            }

            public List<OrderListBean> getOrderList() {
                return orderList;
            }

            public void setOrderList(List<OrderListBean> orderList) {
                this.orderList = orderList;
            }

            public static class OrderListBean {
                /**
                 * count : 1
                 * customerID : 15987
                 * customerName : 深圳金叶珠宝
                 * orderDate : 2018-06-20 11:15:35
                 * quantity : 1
                 */

                private int count;
                private int customerID;
                private String customerName;
                private String orderDate;
                private int quantity;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public int getCustomerID() {
                    return customerID;
                }

                public void setCustomerID(int customerID) {
                    this.customerID = customerID;
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

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }
            }
        }
    }
}
