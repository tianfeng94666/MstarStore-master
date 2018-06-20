package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class StatisticsResult {

    /**
     * data : {"ranges":[{"count":"0","productInfos":[],"rangeTitle":"2~3"},{"count":"11","productInfos":[{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":88,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/Y07447-5.jpg","pid":"105382"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"},{"number":1,"pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/98/QSM1276.jpg","pid":"68858"}],"rangeTitle":"0~50"},{"count":"0","productInfos":[],"rangeTitle":""}]}
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
        private List<RangesBean> ranges;

        public List<RangesBean> getRanges() {
            return ranges;
        }

        public void setRanges(List<RangesBean> ranges) {
            this.ranges = ranges;
        }

        public static class RangesBean {
            /**
             * count : 0
             * productInfos : []
             * rangeTitle : 2~3
             */

            private String count;
            private String rangeTitle;
            private List<?> productInfos;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getRangeTitle() {
                return rangeTitle;
            }

            public void setRangeTitle(String rangeTitle) {
                this.rangeTitle = rangeTitle;
            }

            public List<?> getProductInfos() {
                return productInfos;
            }

            public void setProductInfos(List<?> productInfos) {
                this.productInfos = productInfos;
            }
        }
    }
}
