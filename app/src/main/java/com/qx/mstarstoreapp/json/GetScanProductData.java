package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class GetScanProductData {

    /**
     * data : {"model":{"id":"15","pics":[{"id":"36740","pic":"http://zb.mstar.cn:9888/220X220/Module/Image1/96/A13426A.jpg","picb":"http://zb.mstar.cn:9888/800X800/Module/Image2/94/A13426A.jpg","picm":"http://zb.mstar.cn:9888/480X480/Module/Image1/96/A13426A.jpg"},{"id":"36741","pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/1/A13426A__9.jpg","picb":"http://zb.mstar.cn:9888/800X800/Module/Image3/1/A13426A__9.jpg","picm":"http://zb.mstar.cn:9888/480X480/Module/Image3/1/A13426A__9.jpg"}],"price":0,"title":"A13426A","weight":"PT: 4.50g 18K: 4.00g"}}
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
         * model : {"id":"15","pics":[{"id":"36740","pic":"http://zb.mstar.cn:9888/220X220/Module/Image1/96/A13426A.jpg","picb":"http://zb.mstar.cn:9888/800X800/Module/Image2/94/A13426A.jpg","picm":"http://zb.mstar.cn:9888/480X480/Module/Image1/96/A13426A.jpg"},{"id":"36741","pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/1/A13426A__9.jpg","picb":"http://zb.mstar.cn:9888/800X800/Module/Image3/1/A13426A__9.jpg","picm":"http://zb.mstar.cn:9888/480X480/Module/Image3/1/A13426A__9.jpg"}],"price":0,"title":"A13426A","weight":"PT: 4.50g 18K: 4.00g"}
         */

        private ModelBean model;

        public ModelBean getModel() {
            return model;
        }

        public void setModel(ModelBean model) {
            this.model = model;
        }

        public static class ModelBean {
            /**
             * id : 15
             * pics : [{"id":"36740","pic":"http://zb.mstar.cn:9888/220X220/Module/Image1/96/A13426A.jpg","picb":"http://zb.mstar.cn:9888/800X800/Module/Image2/94/A13426A.jpg","picm":"http://zb.mstar.cn:9888/480X480/Module/Image1/96/A13426A.jpg"},{"id":"36741","pic":"http://zb.mstar.cn:9888/220X220/Module/Image3/1/A13426A__9.jpg","picb":"http://zb.mstar.cn:9888/800X800/Module/Image3/1/A13426A__9.jpg","picm":"http://zb.mstar.cn:9888/480X480/Module/Image3/1/A13426A__9.jpg"}]
             * price : 0
             * title : A13426A
             * weight : PT: 4.50g 18K: 4.00g
             */

            private String id;
            private int price;
            private String title;
            private String weight;
            private List<PicsBean> pics;
            private String remarks;

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public List<PicsBean> getPics() {
                return pics;
            }

            public void setPics(List<PicsBean> pics) {
                this.pics = pics;
            }

            public static class PicsBean {
                /**
                 * id : 36740
                 * pic : http://zb.mstar.cn:9888/220X220/Module/Image1/96/A13426A.jpg
                 * picb : http://zb.mstar.cn:9888/800X800/Module/Image2/94/A13426A.jpg
                 * picm : http://zb.mstar.cn:9888/480X480/Module/Image1/96/A13426A.jpg
                 */

                private String id;
                private String pic;
                private String picb;
                private String picm;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getPicb() {
                    return picb;
                }

                public void setPicb(String picb) {
                    this.picb = picb;
                }

                public String getPicm() {
                    return picm;
                }

                public void setPicm(String picm) {
                    this.picm = picm;
                }
            }
        }
    }
}
