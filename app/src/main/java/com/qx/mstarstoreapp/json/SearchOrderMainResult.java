package com.qx.mstarstoreapp.json;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class SearchOrderMainResult {

    /**
     * response :
     * error : 0
     * message :
     * data : {"orderProduce":{"orderInfo":{"OrderID":"AP170310360","CustomerName":"世爵百年KD4117-NJ","OrderDate":"2017-03-10 13:35:16","ConfirmDate":"2017-03-10 15:13:58","PurityName":"Au750白","QualityName":"普通","Sigil":"","GoldPrice":204.5,"OrderStatus":"A6","number":2,"TypeName":null,"OrderMemo":"封副石！！！"},"modelList":[{"OrderID":"AP170310360","ModuleID":"A40261","appDetailID":null,"TypeName":"女戒","Perimeter":12,"QuantityDetail":1,"IsCSP":null,"StoneP":"钻石","StonePSpecs":"6'","StonePQuantity":1,"StonePFigure":"圆形","StoneSA":null,"StoneSASpecs":null,"StoneSAQuantity":null,"StoneSAFigure":null,"StoneSB":null,"StoneSBSpecs":null,"StoneSBQuantity":null,"StoneSBFigure":null,"StoneSC":null,"StoneSCSpecs":null,"StoneSCQuantity":null,"StoneSCFigure":null,"remarks":"改镶6分"},{"OrderID":"AP170310360","ModuleID":"A44183","appDetailID":null,"TypeName":"女戒","Perimeter":13,"QuantityDetail":1,"IsCSP":null,"StoneP":"钻石","StonePSpecs":"7'","StonePQuantity":1,"StonePFigure":"圆形","StoneSA":null,"StoneSASpecs":null,"StoneSAQuantity":null,"StoneSAFigure":null,"StoneSB":null,"StoneSBSpecs":null,"StoneSBQuantity":null,"StoneSBFigure":null,"StoneSC":null,"StoneSCSpecs":null,"StoneSCQuantity":null,"StoneSCFigure":null,"remarks":null}]},"orderSended":{"recList":[{"recNum":1703160050,"customerName":"世爵百年KD4117-NJ","recDate":"2017-03-16 16:29:38","purityName":"Au750白","orderDate":"2017-03-10 13:35:16","number":2,"totalPrice":726.51,"moList":[{"recNum":1703160050,"moNum":1703160039,"moDate":"2017-03-16 16:08:51","number":2,"totalPrice":726.51}]}]}}
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
         * orderProduce : {"orderInfo":{"OrderID":"AP170310360","CustomerName":"世爵百年KD4117-NJ","OrderDate":"2017-03-10 13:35:16","ConfirmDate":"2017-03-10 15:13:58","PurityName":"Au750白","QualityName":"普通","Sigil":"","GoldPrice":204.5,"OrderStatus":"A6","number":2,"TypeName":null,"OrderMemo":"封副石！！！"},"modelList":[{"OrderID":"AP170310360","ModuleID":"A40261","appDetailID":null,"TypeName":"女戒","Perimeter":12,"QuantityDetail":1,"IsCSP":null,"StoneP":"钻石","StonePSpecs":"6'","StonePQuantity":1,"StonePFigure":"圆形","StoneSA":null,"StoneSASpecs":null,"StoneSAQuantity":null,"StoneSAFigure":null,"StoneSB":null,"StoneSBSpecs":null,"StoneSBQuantity":null,"StoneSBFigure":null,"StoneSC":null,"StoneSCSpecs":null,"StoneSCQuantity":null,"StoneSCFigure":null,"remarks":"改镶6分"},{"OrderID":"AP170310360","ModuleID":"A44183","appDetailID":null,"TypeName":"女戒","Perimeter":13,"QuantityDetail":1,"IsCSP":null,"StoneP":"钻石","StonePSpecs":"7'","StonePQuantity":1,"StonePFigure":"圆形","StoneSA":null,"StoneSASpecs":null,"StoneSAQuantity":null,"StoneSAFigure":null,"StoneSB":null,"StoneSBSpecs":null,"StoneSBQuantity":null,"StoneSBFigure":null,"StoneSC":null,"StoneSCSpecs":null,"StoneSCQuantity":null,"StoneSCFigure":null,"remarks":null}]}
         * orderSended : {"recList":[{"recNum":1703160050,"customerName":"世爵百年KD4117-NJ","recDate":"2017-03-16 16:29:38","purityName":"Au750白","orderDate":"2017-03-10 13:35:16","number":2,"totalPrice":726.51,"moList":[{"recNum":1703160050,"moNum":1703160039,"moDate":"2017-03-16 16:08:51","number":2,"totalPrice":726.51}]}]}
         */

        private OrderProduceBean orderProduce;
        private OrderSendedBean orderSended;

        public OrderProduceBean getOrderProduce() {
            return orderProduce;
        }

        public void setOrderProduce(OrderProduceBean orderProduce) {
            this.orderProduce = orderProduce;
        }

        public OrderSendedBean getOrderSended() {
            return orderSended;
        }

        public void setOrderSended(OrderSendedBean orderSended) {
            this.orderSended = orderSended;
        }

        public static class OrderProduceBean {
            /**
             * orderInfo : {"OrderID":"AP170310360","CustomerName":"世爵百年KD4117-NJ","OrderDate":"2017-03-10 13:35:16","ConfirmDate":"2017-03-10 15:13:58","PurityName":"Au750白","QualityName":"普通","Sigil":"","GoldPrice":204.5,"OrderStatus":"A6","number":2,"TypeName":null,"OrderMemo":"封副石！！！"}
             * modelList : [{"OrderID":"AP170310360","ModuleID":"A40261","appDetailID":null,"TypeName":"女戒","Perimeter":12,"QuantityDetail":1,"IsCSP":null,"StoneP":"钻石","StonePSpecs":"6'","StonePQuantity":1,"StonePFigure":"圆形","StoneSA":null,"StoneSASpecs":null,"StoneSAQuantity":null,"StoneSAFigure":null,"StoneSB":null,"StoneSBSpecs":null,"StoneSBQuantity":null,"StoneSBFigure":null,"StoneSC":null,"StoneSCSpecs":null,"StoneSCQuantity":null,"StoneSCFigure":null,"remarks":"改镶6分"},{"OrderID":"AP170310360","ModuleID":"A44183","appDetailID":null,"TypeName":"女戒","Perimeter":13,"QuantityDetail":1,"IsCSP":null,"StoneP":"钻石","StonePSpecs":"7'","StonePQuantity":1,"StonePFigure":"圆形","StoneSA":null,"StoneSASpecs":null,"StoneSAQuantity":null,"StoneSAFigure":null,"StoneSB":null,"StoneSBSpecs":null,"StoneSBQuantity":null,"StoneSBFigure":null,"StoneSC":null,"StoneSCSpecs":null,"StoneSCQuantity":null,"StoneSCFigure":null,"remarks":null}]
             */

            private OrderInfoBean orderInfo;
            private List<ModelListBean> modelList;

            public OrderInfoBean getOrderInfo() {
                return orderInfo;
            }

            public void setOrderInfo(OrderInfoBean orderInfo) {
                this.orderInfo = orderInfo;
            }

            public List<ModelListBean> getModelList() {
                return modelList;
            }

            public void setModelList(List<ModelListBean> modelList) {
                this.modelList = modelList;
            }

            public static class OrderInfoBean {
                /**
                 * OrderID : AP170310360
                 * CustomerName : 世爵百年KD4117-NJ
                 * OrderDate : 2017-03-10 13:35:16
                 * ConfirmDate : 2017-03-10 15:13:58
                 * PurityName : Au750白
                 * QualityName : 普通
                 * Sigil :
                 * GoldPrice : 204.5
                 * OrderStatus : A6
                 * number : 2
                 * TypeName : null
                 * OrderMemo : 封副石！！！
                 */

                private String OrderID;
                private String CustomerName;
                private String OrderDate;
                private String ConfirmDate;
                private String PurityName;
                private String QualityName;
                private String Sigil;
                private double GoldPrice;
                private String OrderStatus;
                private int number;
                private Object TypeName;
                private String OrderMemo;

                public String getOrderID() {
                    return OrderID;
                }

                public void setOrderID(String OrderID) {
                    this.OrderID = OrderID;
                }

                public String getCustomerName() {
                    return CustomerName;
                }

                public void setCustomerName(String CustomerName) {
                    this.CustomerName = CustomerName;
                }

                public String getOrderDate() {
                    return OrderDate;
                }

                public void setOrderDate(String OrderDate) {
                    this.OrderDate = OrderDate;
                }

                public String getConfirmDate() {
                    return ConfirmDate;
                }

                public void setConfirmDate(String ConfirmDate) {
                    this.ConfirmDate = ConfirmDate;
                }

                public String getPurityName() {
                    return PurityName;
                }

                public void setPurityName(String PurityName) {
                    this.PurityName = PurityName;
                }

                public String getQualityName() {
                    return QualityName;
                }

                public void setQualityName(String QualityName) {
                    this.QualityName = QualityName;
                }

                public String getSigil() {
                    return Sigil;
                }

                public void setSigil(String Sigil) {
                    this.Sigil = Sigil;
                }

                public double getGoldPrice() {
                    return GoldPrice;
                }

                public void setGoldPrice(double GoldPrice) {
                    this.GoldPrice = GoldPrice;
                }

                public String getOrderStatus() {
                    return OrderStatus;
                }

                public void setOrderStatus(String OrderStatus) {
                    this.OrderStatus = OrderStatus;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public Object getTypeName() {
                    return TypeName;
                }

                public void setTypeName(Object TypeName) {
                    this.TypeName = TypeName;
                }

                public String getOrderMemo() {
                    return OrderMemo;
                }

                public void setOrderMemo(String OrderMemo) {
                    this.OrderMemo = OrderMemo;
                }
            }

            public static class ModelListBean {
                /**
                 * OrderID : AP170310360
                 * ModuleID : A40261
                 * appDetailID : null
                 * TypeName : 女戒
                 * Perimeter : 12
                 * QuantityDetail : 1
                 * IsCSP : null
                 * StoneP : 钻石
                 * StonePSpecs : 6'
                 * StonePQuantity : 1
                 * StonePFigure : 圆形
                 * StoneSA : null
                 * StoneSASpecs : null
                 * StoneSAQuantity : null
                 * StoneSAFigure : null
                 * StoneSB : null
                 * StoneSBSpecs : null
                 * StoneSBQuantity : null
                 * StoneSBFigure : null
                 * StoneSC : null
                 * StoneSCSpecs : null
                 * StoneSCQuantity : null
                 * StoneSCFigure : null
                 * remarks : 改镶6分
                 */

                private String OrderID;
                private String ModuleID;
                private Object appDetailID;
                private String TypeName;
                private int Perimeter;
                private int QuantityDetail;
                private Object IsCSP;
                private String StoneP;
                private String StonePSpecs;
                private int StonePQuantity;
                private String StonePFigure;
                private Object StoneSA;
                private Object StoneSASpecs;
                private Object StoneSAQuantity;
                private Object StoneSAFigure;
                private Object StoneSB;
                private Object StoneSBSpecs;
                private Object StoneSBQuantity;
                private Object StoneSBFigure;
                private Object StoneSC;
                private Object StoneSCSpecs;
                private Object StoneSCQuantity;
                private Object StoneSCFigure;
                private String remarks;

                public String getOrderID() {
                    return OrderID;
                }

                public void setOrderID(String OrderID) {
                    this.OrderID = OrderID;
                }

                public String getModuleID() {
                    return ModuleID;
                }

                public void setModuleID(String ModuleID) {
                    this.ModuleID = ModuleID;
                }

                public Object getAppDetailID() {
                    return appDetailID;
                }

                public void setAppDetailID(Object appDetailID) {
                    this.appDetailID = appDetailID;
                }

                public String getTypeName() {
                    return TypeName;
                }

                public void setTypeName(String TypeName) {
                    this.TypeName = TypeName;
                }

                public int getPerimeter() {
                    return Perimeter;
                }

                public void setPerimeter(int Perimeter) {
                    this.Perimeter = Perimeter;
                }

                public int getQuantityDetail() {
                    return QuantityDetail;
                }

                public void setQuantityDetail(int QuantityDetail) {
                    this.QuantityDetail = QuantityDetail;
                }

                public Object getIsCSP() {
                    return IsCSP;
                }

                public void setIsCSP(Object IsCSP) {
                    this.IsCSP = IsCSP;
                }

                public String getStoneP() {
                    return StoneP;
                }

                public void setStoneP(String StoneP) {
                    this.StoneP = StoneP;
                }

                public String getStonePSpecs() {
                    return StonePSpecs;
                }

                public void setStonePSpecs(String StonePSpecs) {
                    this.StonePSpecs = StonePSpecs;
                }

                public int getStonePQuantity() {
                    return StonePQuantity;
                }

                public void setStonePQuantity(int StonePQuantity) {
                    this.StonePQuantity = StonePQuantity;
                }

                public String getStonePFigure() {
                    return StonePFigure;
                }

                public void setStonePFigure(String StonePFigure) {
                    this.StonePFigure = StonePFigure;
                }

                public Object getStoneSA() {
                    return StoneSA;
                }

                public void setStoneSA(Object StoneSA) {
                    this.StoneSA = StoneSA;
                }

                public Object getStoneSASpecs() {
                    return StoneSASpecs;
                }

                public void setStoneSASpecs(Object StoneSASpecs) {
                    this.StoneSASpecs = StoneSASpecs;
                }

                public Object getStoneSAQuantity() {
                    return StoneSAQuantity;
                }

                public void setStoneSAQuantity(Object StoneSAQuantity) {
                    this.StoneSAQuantity = StoneSAQuantity;
                }

                public Object getStoneSAFigure() {
                    return StoneSAFigure;
                }

                public void setStoneSAFigure(Object StoneSAFigure) {
                    this.StoneSAFigure = StoneSAFigure;
                }

                public Object getStoneSB() {
                    return StoneSB;
                }

                public void setStoneSB(Object StoneSB) {
                    this.StoneSB = StoneSB;
                }

                public Object getStoneSBSpecs() {
                    return StoneSBSpecs;
                }

                public void setStoneSBSpecs(Object StoneSBSpecs) {
                    this.StoneSBSpecs = StoneSBSpecs;
                }

                public Object getStoneSBQuantity() {
                    return StoneSBQuantity;
                }

                public void setStoneSBQuantity(Object StoneSBQuantity) {
                    this.StoneSBQuantity = StoneSBQuantity;
                }

                public Object getStoneSBFigure() {
                    return StoneSBFigure;
                }

                public void setStoneSBFigure(Object StoneSBFigure) {
                    this.StoneSBFigure = StoneSBFigure;
                }

                public Object getStoneSC() {
                    return StoneSC;
                }

                public void setStoneSC(Object StoneSC) {
                    this.StoneSC = StoneSC;
                }

                public Object getStoneSCSpecs() {
                    return StoneSCSpecs;
                }

                public void setStoneSCSpecs(Object StoneSCSpecs) {
                    this.StoneSCSpecs = StoneSCSpecs;
                }

                public Object getStoneSCQuantity() {
                    return StoneSCQuantity;
                }

                public void setStoneSCQuantity(Object StoneSCQuantity) {
                    this.StoneSCQuantity = StoneSCQuantity;
                }

                public Object getStoneSCFigure() {
                    return StoneSCFigure;
                }

                public void setStoneSCFigure(Object StoneSCFigure) {
                    this.StoneSCFigure = StoneSCFigure;
                }

                public String getRemarks() {
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
                }
            }
        }

        public static class OrderSendedBean {
            private List<RecListBean> recList;

            public List<RecListBean> getRecList() {
                return recList;
            }

            public void setRecList(List<RecListBean> recList) {
                this.recList = recList;
            }

            public static class RecListBean {
                /**
                 * recNum : 1703160050
                 * customerName : 世爵百年KD4117-NJ
                 * recDate : 2017-03-16 16:29:38
                 * purityName : Au750白
                 * orderDate : 2017-03-10 13:35:16
                 * number : 2
                 * totalPrice : 726.51
                 * moList : [{"recNum":1703160050,"moNum":1703160039,"moDate":"2017-03-16 16:08:51","number":2,"totalPrice":726.51}]
                 */

                private int recNum;
                private String customerName;
                private String recDate;
                private String purityName;
                private String orderDate;
                private int number;
                private double totalPrice;
                private List<MoListBean> moList;

                public int getRecNum() {
                    return recNum;
                }

                public void setRecNum(int recNum) {
                    this.recNum = recNum;
                }

                public String getCustomerName() {
                    return customerName;
                }

                public void setCustomerName(String customerName) {
                    this.customerName = customerName;
                }

                public String getRecDate() {
                    return recDate;
                }

                public void setRecDate(String recDate) {
                    this.recDate = recDate;
                }

                public String getPurityName() {
                    return purityName;
                }

                public void setPurityName(String purityName) {
                    this.purityName = purityName;
                }

                public String getOrderDate() {
                    return orderDate;
                }

                public void setOrderDate(String orderDate) {
                    this.orderDate = orderDate;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public double getTotalPrice() {
                    return totalPrice;
                }

                public void setTotalPrice(double totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public List<MoListBean> getMoList() {
                    return moList;
                }

                public void setMoList(List<MoListBean> moList) {
                    this.moList = moList;
                }

                public static class MoListBean {
                    /**
                     * recNum : 1703160050
                     * moNum : 1703160039
                     * moDate : 2017-03-16 16:08:51
                     * number : 2
                     * totalPrice : 726.51
                     */

                    private int recNum;
                    private int moNum;
                    private String moDate;
                    private int number;
                    private double totalPrice;

                    public int getRecNum() {
                        return recNum;
                    }

                    public void setRecNum(int recNum) {
                        this.recNum = recNum;
                    }

                    public int getMoNum() {
                        return moNum;
                    }

                    public void setMoNum(int moNum) {
                        this.moNum = moNum;
                    }

                    public String getMoDate() {
                        return moDate;
                    }

                    public void setMoDate(String moDate) {
                        this.moDate = moDate;
                    }

                    public int getNumber() {
                        return number;
                    }

                    public void setNumber(int number) {
                        this.number = number;
                    }

                    public double getTotalPrice() {
                        return totalPrice;
                    }

                    public void setTotalPrice(double totalPrice) {
                        this.totalPrice = totalPrice;
                    }
                }
            }
        }
    }
}
