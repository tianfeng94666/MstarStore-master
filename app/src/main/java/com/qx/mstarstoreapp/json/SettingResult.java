package com.qx.mstarstoreapp.json;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class SettingResult {

    /**
     * data : {"address":"江苏省 淮安市 盱眙县 34567788887","headPic":"http://appapi.fanerweb.com/Uploads/Pics/2017-03-01/s_58b66ac491a61.jpg","isShowPrice":0,"phone":"15994767200","userName":"xianxianjun"}
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
         * address : 江苏省 淮安市 盱眙县 34567788887
         * headPic : http://appapi.fanerweb.com/Uploads/Pics/2017-03-01/s_58b66ac491a61.jpg
         * isShowPrice : 0
         * phone : 15994767200
         * userName : xianxianjun
         */

        private String address;
        private String headPic;
        private int isShowPrice;
        private String phone;
        private String userName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getIsShowPrice() {
            return isShowPrice;
        }

        public void setIsShowPrice(int isShowPrice) {
            this.isShowPrice = isShowPrice;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
