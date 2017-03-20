package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class SearchOrderResult {

    /**
     * data : {"endDate":"2017-03-20","searchKeyword":[{"id":1,"title":"订单号"},{"id":2,"title":"款号"}],"searchScope":[{"id":1,"title":"我的订单"},{"id":2,"title":"所有订单"}],"startDate":"2017-02-20"}
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
         * endDate : 2017-03-20
         * searchKeyword : [{"id":1,"title":"订单号"},{"id":2,"title":"款号"}]
         * searchScope : [{"id":1,"title":"我的订单"},{"id":2,"title":"所有订单"}]
         * startDate : 2017-02-20
         */

        private String endDate;
        private String startDate;
        private List<SearchKeywordBean> searchKeyword;
        private List<SearchScopeBean> searchScope;

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public List<SearchKeywordBean> getSearchKeyword() {
            return searchKeyword;
        }

        public void setSearchKeyword(List<SearchKeywordBean> searchKeyword) {
            this.searchKeyword = searchKeyword;
        }

        public List<SearchScopeBean> getSearchScope() {
            return searchScope;
        }

        public void setSearchScope(List<SearchScopeBean> searchScope) {
            this.searchScope = searchScope;
        }

        public static class SearchKeywordBean {
            /**
             * id : 1
             * title : 订单号
             */

            private int id;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class SearchScopeBean {
            /**
             * id : 1
             * title : 我的订单
             */

            private int id;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
