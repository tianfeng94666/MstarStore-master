package com.qx.mstarstoreapp.json;

/**
 * Created by Administrator on 2018/5/11 0011.
 */

public class GetDefaultCustomerResult {
    /**
     * data : {"customer":{"customerID":23651,"keycode":"97108","customerName":"冼小俊测试","customerFullName":null},"state":1}
     * response :
     * error : 0
     * message :
     */

    private DataBean data;
    private String response;
    private String error;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * customer : {"customerID":23651,"keycode":"97108","customerName":"冼小俊测试","customerFullName":null}
         * state : 1
         */

        private CustomerBean customer;
        private int state;

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public static class CustomerBean {
            /**
             * customerID : 23651
             * keycode : 97108
             * customerName : 冼小俊测试
             * customerFullName : null
             */

            private int customerID;
            private String keycode;
            private String customerName;
            private Object customerFullName;

            public int getCustomerID() {
                return customerID;
            }

            public void setCustomerID(int customerID) {
                this.customerID = customerID;
            }

            public String getKeycode() {
                return keycode;
            }

            public void setKeycode(String keycode) {
                this.keycode = keycode;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public Object getCustomerFullName() {
                return customerFullName;
            }

            public void setCustomerFullName(Object customerFullName) {
                this.customerFullName = customerFullName;
            }
        }
    }
}
