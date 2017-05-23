package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class StoneOrderResult {

    /**
     * response :
     * error : 0
     * message :
     * data : {"address":{"id":"119","name":"Gigi ","phone":"13456543456","addr":"江苏省 淮安市 盱眙县 34567788887"},"list":[{"id":"11","price":"2376.00","info":"证书:GIA证书号:2131040153 重量:0.75 颜色:G 净度:SI2"},{"id":"12","price":"1800.00","info":"证书:GIA证书号:1136126085 重量:0.32 颜色:D 净度:VS1"},{"id":"13","price":"2388.00","info":"证书:GIA证书号:1136461182 重量:0.52 颜色:H 净度:VS2"}]}
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
         * address : {"id":"119","name":"Gigi ","phone":"13456543456","addr":"江苏省 淮安市 盱眙县 34567788887"}
         * list : [{"id":"11","price":"2376.00","info":"证书:GIA证书号:2131040153 重量:0.75 颜色:G 净度:SI2"},{"id":"12","price":"1800.00","info":"证书:GIA证书号:1136126085 重量:0.32 颜色:D 净度:VS1"},{"id":"13","price":"2388.00","info":"证书:GIA证书号:1136461182 重量:0.52 颜色:H 净度:VS2"}]
         */

        private AddressEntity address;
        private List<StoneBean> list;

        public AddressEntity getAddress() {
            return address;
        }

        public void setAddress(AddressEntity address) {
            this.address = address;
        }

        public List<StoneBean> getList() {
            return list;
        }

        public void setList(List<StoneBean> list) {
            this.list = list;
        }




    }
}
