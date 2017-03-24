package com.qx.mstarstoreapp.base;

/**
 * Created by Administrator on 2016/9/21.
 */
public class AppURL {
    /*"http://192.168.1.240:9112/api/Aproxy/*/
    private static String baseUrl = "http://appapi.fanerweb.com/api/aproxy/";
    //测试 http://appapi1.fanerweb.com/api/Aproxy/
    private static String baseUrl1 = "http://appapi1.fanerweb.com/api/aproxy/";
    // private static String baseUrl="http://192.168.1.240:9112/api/Aproxy/";
    public static String URL_LOGIN = baseUrl + "userLoginDo?";


    public static String URL_REGISTER = baseUrl + "userRegisterDo?";

    public static String URL_HOME = baseUrl + "userAdminPage?";

    /*修改密码*/
    public static String URL_UPDATE_PWD = baseUrl + "userModifyPasswordDo?";
    //userModifyPasswordDo?tokenKey=841bf597782dced089a6e5167bcf29de&password=123456&phoneCode=32656
    /*添加收货地址*/
    public static String URL_SHOP_ADDRESS = baseUrl + "userAddAddressPage?";
    /*得到*/   // getCity?tokenKey=944df2f27ffce557042887589986c193&id=18
    public static String URL_GET_CITY = baseUrl + "getCity?";
    /**/   //getArea?tokenKey=944df2f27ffce557042887589986c193&id=278
    public static String URL_GET_AREA = baseUrl + "getArea?";

    /*添加地址*/
    // addUserAddressDo?tokenKey=944df2f27ffce557042887589986c193&name=广播站&provinceId=20&cityId=319&areaId=3230&phone=15994767200&addr=神木林百度洞44&isDefault=1
    public static String URL_ADD_ARDRESS = baseUrl + "addUserAddressDo?";

    public static String URL_ADDRESS_MANAGER = baseUrl + "AddressListPage?";

    /*选择地址*/
    public static String URL_ADDRESS_SELECT = baseUrl + "AddressListPageForSelect?";

    /*删除地址*/
    public static String URL_DEL_ADDRESS = baseUrl + "deleteAddressDo?";

    /*修改地址页面获取原地址
    * */
    public static String URL_MODIFY_ADDRESS = baseUrl + "userModifyAddressPage?";

    /*修改提交*/
    public static String URL_MODIFY_ADDRESS_DO = baseUrl + "modifyAddressDo?";
    // modifyAddressDo?tokenKey=944df2f27ffce557042887589986c193&name=广播站&provinceId=20&cityId=319&areaId=3230&phone=15994767200&addr=神木林百度洞44&id=4&isDefault=1

    /*设置默认地址*/
    public static String URL_DEFAULT_ADDRESS = baseUrl + "setDefaultAddressDo?";
    // setDefaultAddressDo?tokenKey=944df2f27ffce557042887589986c193&id=2

    //            http://appapi.fanerweb.com/api/aproxy/modelListPage?tokenKey=4402575f73660a61eabc2b8bcc56fef8

    //modelListPage?tokenKey=944df2f27ffce557042887589986c193&category=1,2,3,4,5,78,lo,34&keyWord=mm|xx|vv|%27gg|莱尔|xx&price=6000|10000&weight=0.9|1.8
    /*款号列表*/
    public static String URL_MODE_LIST = baseUrl + "modelListPage?";

    /*上传头像*/
    public static String URL_UPLOAD_PiC = baseUrl + "userModifyHeadPicDo?";


    /*款号详情   ModelDetailPage?tokenKey=944df2f27ffce557042887589986c193&id=1*/
    public static String URL_MODEL_DETAIL = baseUrl + "ModelDetailPage?";

    /*款号修改   ModelDetailPage?tokenKey=944df2f27ffce557042887589986c193&id=1*/
    public static String URL_MODEL_UPDATE = baseUrl + "ModelDetailPageForCurrentOrderEditPage?";

    //?itemId=16&tokenKey=10b588002228fa805231a59bb7976bf4
    //订单页修改款号
    public static String URL_ORDER_MODEL_UPDATE = baseUrl + "ModelOrderWaitCheckModelDetailPageForCurrentOrderEditPage?";

    /*获得石头价格*/
    public static String URL_STONE_PRICE = baseUrl + "getStonePrice?";
    //getStonePrice?tokenKey=944df2f27ffce557042887589986c193&colorId=4&categoryId=4&specId=1&purityId=2


    /*添加到当前订单*/
    public static String URL_CURRENT_ORDER = baseUrl + "OrderDoCurrentModelItemDo?";
    //OrderDoCurrentModelItemDo?productId=1&categoryId=8&number=2&handSize=3&stone=1|3|2|3|4|5&stoneA=1|2|2|3|4|5&stoneB=1|2|3|3|4|5&stoneC=1|2|3|3|4|6&tokenKey=10b588002228fa805231a59bb7976bf4

    /*修改添加添加到当前订单*/
    public static String URL_CURRENT_EDIT_ORDER = baseUrl + "OrderCurrentEditModelItemDo?";


    //订单页跳转修改页修改
    public static String URL_UPDATE_ORDER_WATET = baseUrl + "ModelOrderWaitCheckOrderCurrentEditModelItemDo?";
    //?itemId=16&number=2&handSize=8&stone=1|3|2|3|3|3&stoneA=1|2|2|3|3|3&stoneB=1|2|2|3|3|5&stoneC=1|2|3|3|3|3&tokenKey=10b588002228fa805231a59bb7976bf4
    /*类型选择*/
    // modelFilerPage?tokenKey=944df2f27ffce557042887589986c193

    public static String URL_MODEL_FILTER = baseUrl + "modelFilerPage?";


   /*当前订单*/
    //OrderDoCurrentModelItemDo?productId=1&categoryId=8&number=2&handSize=3&stone=1|3|2|3|4|5&stoneA=1|2|2|3|4|5&stoneB=1|2|3|3|4|5&stoneC=1|2|3|3|4|6&tokenKey=10b588002228fa805231a59bb7976bf4


    /*生成订单*/
    //OrderListPage?tokenKey=10b588002228fa805231a59bb7976bf4&cpage=1
    public static String URL_ORDER_LIST = baseUrl + "OrderListPage?";


    //ModelOrderWaitCheckDetail?tokenKey=10b588002228fa805231a59bb7976bf4&cpage=1&orderId=13
    public static String URL_ORDER_DETAIL = baseUrl + "ModelOrderWaitCheckDetail?";


    /*搜索客户*/
    //IsHaveCustomer?keyword=广西|平果&tokenKey=944df2f27ffce557042887589986c193
    public static String URL_HAVE_CUSTOMER = baseUrl + "IsHaveCustomer?";

    /*客户列表*/
    //GetCustomerList?keyword=湖南|益阳&cpage=1&tokenKey=944df2f27ffce557042887589986c193
    public static String URL_CUSTOMER_LIST = baseUrl + "GetCustomerList?";



    /*删除订单*/
    //GetCustomerList?keyword=湖南|益阳&cpage=1&tokenKey=944df2f27ffce557042887589986c193
    public static String URL_ORDER_DELETE = baseUrl + "OrderCurrentDeleteModelItemDo?";

    //审核页跳转订单页
   // ?itemId=3&tokenKey=10b588002228fa805231a59bb7976bf4
    public static String URL_ORDER_WAIT_DELETE = baseUrl + "ModelOrderWaitCheckDetailDeleteModelItemDo?";
    // ?itemId=1&tokenKey=10b588002228fa805231a59bb7976bf4


    /*提交订单*/
    //OrderCurrentSubmitDo?itemId=1|2|3&addressId=1&purityId=3&qualityId=2&tokenKey=944df2f27ffce557042887589986c193
    public static String URL_ORDER_SUBMIT = baseUrl + "OrderCurrentSubmitDo?";

    /*获取单个石头价格*/
   // GetOrderPricePageList?tokenKey=944df2f27ffce557042887589986c193&cpage=1&purityId=1&qualityId=1
    public static String URL_ORDER_PRICE = baseUrl + "GetOrderPricePageList?";

    /*定金页价格修改*/
   // ?purityId=1&qualityId=1&orderId=13&tokenKey=10b588002228fa805231a59bb7976bf4
    public static String URL_WATI_ORDER_PRICE = baseUrl + "ModelOrderWaitCheckModifyGetOrderPricePageListDo?";
    /*待审核订单  ModelOrderWaitCheckList?tokenKey=10b588002228fa805231a59bb7976bf4 */
    public static String URL_ORDER_WAITCHECK = baseUrl + "ModelOrderWaitCheckList?";


    /*生产中的订单  ModelOrderProduceListPage?tokenKey=10b588002228fa805231a59bb7976bf4&cpage=2 */
    public static String URL_ORDER_MODEL_LIST = baseUrl + "ModelOrderProduceListPage?";
    //修改自印和客户ID
    public static String URL_WATI_ORDER_MODIINFO = baseUrl + "ModelOrderWaitCheckDetailModifyInfoDo?";

    /*审核页面挑战取消订单*/
   // ?orderId=10&tokenKey=10b588002228fa805231a59bb7976bf4
    public static String URL_ORDER_WAIT_CANCEL = baseUrl + "ModelOrderWaitCheckCancelDo?";


    /*订单 详情  ModelOrderProduceDetailPage?orderNum=IAR201611101415&tokenKey=10b588002228fa805231a59bb7976bf4  */
    public static String URL_PD_ORDER_DETAIL = baseUrl + "ModelOrderProduceDetailPage?";


    /*登录验证码*/
    public static String URL_LOGING_CODE=baseUrl+"GetLoginVerifyCodeDo?";

    /*注册验证码*/
    public static String URL_REGISTER_CODE=baseUrl+"GetRegisterVerifyCodeDo?";


    /*修改密码验证码*/
    public static String URL_UPDATA_PWD_CODE=baseUrl+"GetUserModifyPasswordVerifyCodeDo?";

    /*ModelInvoicePage?tokenKey=944df2f27ffce557042887589986c193 获取发票*/
    public static String URL_MODELINVOICE_PAGE=baseUrl+"ModelInvoicePage?";


    /*1.1.2.4生产进度*/
    public static String URL_MODELPRODUCE_PROGRESS=baseUrl+"ModelOrderProduceDetailShowRateProgressPage?";
   // ?tokenKey=509e15ec5b23808d098ecaf1f533c7de&orderNum=AP2016121617116


    /*1.1.2验证输入的规格*/
    public static String URL_CHECKSPEIC= baseUrl + "CheckSpecificationsForm?";


    /*个人设置页面*/
    public static String URL_USER_MODIFY= baseUrl + "userModifyPage?";
    //http://appapi1.fanerweb.com/api/Aproxy/userModifyPage?tokenKey=509e15ec5b23808d098ecaf1f533c7de

    //忘记密码
    public static String URL_USER_FORGETPWD= baseUrl + "userForgetPasswordDo?";

    //http://appapi.fanerweb.com/api/Aproxy/GetForgetPasswordVerifyCodeDo?phone=xxxxx  忘记密码获取验证码
    public static String URL_CODE_FORGETPWD= baseUrl + "GetForgetPasswordVerifyCodeDo?";

    //结算单
    public static String URL_CODE_FINISH= baseUrl + "ModelFinishBillList?";
    //结算单详情
    public static String URL_CODE_FINISH_DETAIL= baseUrl + "ModelBillFinishDetailRec?";
    //已货单列表 http://appapi1.fanerweb.com/api/Aproxy/ModelBillList?cpage=3&tokenKey=7cdcf3a6a47904dbff1e7da86b8ef225
    public static String URL_CODE_SENDING= baseUrl + "ModelBillList?";
    //已货单详情
    public static String URL_CODE_SENDING_DETAIL= baseUrl + "ModelArriveBillMo?";
    //获得版本信息
    public static String URL_CODE_VERSION = baseUrl + "currentVersion?";

    //订单搜索页面
    public static String URL_CODE_ORDER_SEARCH = baseUrl1 + "ModelUserOrderSearchPage?";
    //搜索订单列表页
    public static String URL_CODE_ORDER_SEARCH_LIST = baseUrl1 + "ModelOrderSearch?";
    //搜索详情页
    public static String URL_CODE_ORDER_SEARCH_DETAIL = baseUrl1 + "ModelOrderSearchDetail?";
    //搜索结算详情页
    public static String URL_CODE_SEARCH_FINSIH_DETAIL = baseUrl1 + "ModelBillFinishDetailRecForSearch?";
    //搜索出库详情页
    public static String URL_CODE_SEARCH_DELIVERY_DETAIL = baseUrl1 + "ModelArriveBillMoForSearch?";
    /*1.1.2.4搜索页面的生产进度*/
    public static String URL_MODELPRODUCE_PROGRESS2=baseUrl1+"ModelOrderProduceDetailShowRateProgressPageForSearch?";
    /*订单 详情  ModelOrderProduceDetailPage?  */
    public static String URL_PD_ORDER_DETAIL2 = baseUrl1 + "ModelOrderProduceDetailHistoryPageForSearch?";

}


