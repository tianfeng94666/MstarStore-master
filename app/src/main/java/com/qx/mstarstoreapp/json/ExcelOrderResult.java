package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class ExcelOrderResult {

    /**
     * data : {"ErrData":[{"id":1,"isErr":1,"modelNum":"找不到款号:N04","number":"","purity":""},{"id":5,"isErr":1,"modelNum":"找不到款号:Y0","number":"","purity":""}]}
     * error : 1
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
        String waitOrderCount;

        public String getWaitOrderCount() {
            return waitOrderCount;
        }

        public void setWaitOrderCount(String waitOrderCount) {
            this.waitOrderCount = waitOrderCount;
        }

        private List<ErrDataBean> ErrData;

        public List<ErrDataBean> getErrData() {
            return ErrData;
        }

        public void setErrData(List<ErrDataBean> ErrData) {
            this.ErrData = ErrData;
        }

        public static class ErrDataBean {
            /**
             * id : 1
             * isErr : 1
             * modelNum : 找不到款号:N04
             * number :
             * purity :
             */

            private String id;
            private int isErr;
            private String modelNum;
            private String number;
            private String purity;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIsErr() {
                return isErr;
            }

            public void setIsErr(int isErr) {
                this.isErr = isErr;
            }

            public String getModelNum() {
                return modelNum;
            }

            public void setModelNum(String modelNum) {
                this.modelNum = modelNum;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getPurity() {
                return purity;
            }

            public void setPurity(String purity) {
                this.purity = purity;
            }
        }
    }
}
