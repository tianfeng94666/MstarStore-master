package com.qx.mstarstoreapp.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qx.mstarstoreapp.bean.CustomList;
import com.qx.mstarstoreapp.bean.ModelListEntity;
import com.qx.mstarstoreapp.greendao.gen.DaoMaster;
import com.qx.mstarstoreapp.greendao.gen.DaoSession;
import com.qx.mstarstoreapp.json.ClassTypeFilerEntity;
import com.qx.mstarstoreapp.json.SearchValue;
import com.qx.mstarstoreapp.net.ImageLoderUtils;
import com.qx.mstarstoreapp.net.VolleySingleton;
import com.qx.mstarstoreapp.utils.ACache;
import com.qx.mstarstoreapp.utils.Constants;
import com.qx.mstarstoreapp.utils.L;
import com.qx.mstarstoreapp.utils.SpUtils;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.finalteam.okhttpfinal.Part;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Headers;
import okhttp3.Interceptor;

public class BaseApplication extends Application {
    private static BaseApplication mBaseApplication = null;
    private static Looper mMainThreadLooper = null;
    private static Handler mMainThreadHandler = null;
    private static int mMainThreadId;
    private static Thread mMainThread = null;
    private static String token;
    private static String registrationID;
    public static int mNetWorkState;
    // volley请求队列
    public static RequestQueue requestQueue = null;
    private static String userPic;

    public static ACache mAcache = null;

    public static String getUserPic() {
        return userPic;
    }

    public static void setUserPic(String userPic) {
        BaseApplication.userPic = userPic;
    }

    public static SpUtils spUtils;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public  String getRegistrationID() {
        registrationID = JPushInterface.getRegistrationID(this);
        return registrationID;
    }

    public  void setRegistrationID(String registrationID) {
        BaseApplication.registrationID = registrationID;
    }

    // 共享变量
    public static final int FLUSH_MAIN_ACTIVITY = 1;
    private Handler main;

    public void setMainHandler(Handler handler) {
        main = handler;
    }

    public void flushMain() {
        main.sendEmptyMessage(FLUSH_MAIN_ACTIVITY);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        BaseApplication.mBaseApplication = this;
        BaseApplication.mMainThreadLooper = getMainLooper();
        BaseApplication.mMainThreadHandler = new Handler();
        BaseApplication.mMainThreadId = android.os.Process.myTid();
        BaseApplication.mMainThread = Thread.currentThread();
        spUtils = SpUtils.getInstace(this);
        mAcache = ACache.get(this);
        initVolley();
        initImageLoder();
        initOkHttpFinal();
//        initLeakCanary();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Global.REGISTRATION =((BaseApplication)getApplication()).getRegistrationID();
        setDatabase();
        //初始化分享
//        ShareSDK.initSDK(this);
    }
    private void setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this,"notes-db", null);
        db =mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }


    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        // Normal app init code...

    }

    public static final int REQ_TIMEOUT = 35000;

    private void initOkHttpFinal() {
        List<Part> commomParams = new ArrayList<>();
        Headers commonHeaders = new Headers.Builder().build();
        List<Interceptor> interceptorList = new ArrayList<>();
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder()
                .setCommenParams(commomParams)
                .setCommenHeaders(commonHeaders)
                .setTimeout(REQ_TIMEOUT)
                .setInterceptors(interceptorList)
                .setDebug(true);
        OkHttpFinal.getInstance().init(builder.build());
    }


    public void initImageLoder() {
        ImageLoderUtils.initImageLoader(this);
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseApplication.token = token;
    }

    private Context getContext() {
        return this.getApplicationContext();
    }


    public static BaseApplication getApplication() {
        return mBaseApplication;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    //初始化 volley请求队列
    public void initVolley() {
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
    }


//    public static void loadNetData() {
//      //  JsonObject jsonResult = new Gson().fromJson(Constants.jsonResult, JsonObject.class);
//      //  String error=jsonResult.get("error").getAsString();
////        if (error.equals("0")) {
////
////        }
//        ModeListResult modeListResult = new Gson().fromJson(Constants.getJsonResult2, ModeListResult.class);
//        ModeListResult.DataEntity dataEntity = modeListResult.getData();
//        ModeListResult.DataEntity.ModelEntity modeEntity = dataEntity.getMode();
//        L.e("解析成功"+modeEntity.toString());
//
//    }


    public static void loadNetData1() {
        Gson gson = new Gson();
        String error = new Gson().fromJson(Constants.jsonResult, JsonObject.class).get("error").getAsString();
        if ("0".equals(error)) {
            JsonObject jData = gson.fromJson(Constants.jsonResult, JsonObject.class).get("data").getAsJsonObject();
            JsonArray typeList = gson.fromJson(jData, JsonObject.class).get("typeList").getAsJsonArray();
            for (int i = 0; i < typeList.size(); i++) {
                //L.e("typeList  id;"+  typeList.get(i).getAsJsonObject().get("id").getAsString());
                // L.e("typeList  sort;"+  typeList.get(i).getAsJsonObject().get("sort").getAsString());
                // L.e("typeList  mulSelect;"+  typeList.get(i).getAsJsonObject().get("mulSelect").getAsString());
            }
            JsonObject model = gson.fromJson(jData, JsonObject.class).get("model").getAsJsonObject();
            // JsonArray modelList = gson.fromJson(model, JsonObject.class).get("modelList").getAsJsonArray();
            //  int count=model.get("list_count").getAsInt();
            // L.e("解析成"+count);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void loadNetData2() {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ClassTypeFilerEntity[]>() {
            }.getType();
            JsonObject jData = gson.fromJson(Constants.jsonResult1, JsonObject.class).getAsJsonObject("data");
            JsonArray jTypeList = jData.get("typeList").getAsJsonArray();
            ClassTypeFilerEntity[] ct = gson.fromJson(jTypeList, type);
            List<ClassTypeFilerEntity> classTypeFilerEntityList = new ArrayList<>();
            List<ClassTypeFilerEntity.AttributeListEntity> attributeList = new ArrayList<>();
            for (ClassTypeFilerEntity entity : ct) {
                classTypeFilerEntityList.add(entity);
                List<ClassTypeFilerEntity.AttributeListEntity> attributeList1 = entity.getAttributeList();
                for (int i = 0; i < attributeList1.size(); i++) {
                    ClassTypeFilerEntity.AttributeListEntity attributeListEntity = attributeList1.get(i);
                    L.e("attributeList1：" + attributeListEntity.toString());
                    attributeList.add(attributeListEntity);
                }
            }

            type = new TypeToken<CustomList[]>() {
            }.getType();
            JsonArray jCustomList = jData.get("customList").getAsJsonArray();
            CustomList[] cl = gson.fromJson(jCustomList, type);
            List<CustomList> customList = new ArrayList<>();
            for (CustomList entity : cl) {
                L.e("customList：" + entity.toString());
                customList.add(entity);
            }

            List<ModelListEntity> modelList = new ArrayList<>();
            JsonObject jmodel = gson.fromJson(jData, JsonObject.class).get("model").getAsJsonObject();
            JsonArray jmodelList = gson.fromJson(jmodel, JsonObject.class).get("modelList").getAsJsonArray();
            ModelListEntity[] mds = gson.fromJson(jmodelList, ModelListEntity[].class);
            modelList.clear();
            Collections.addAll(modelList, mds);
            for (int i = 0; i < modelList.size(); i++) {
                L.e("ModelListEntity:" + modelList.get(i).toString());
            }

            type = new TypeToken<SearchValue[]>() {
            }.getType();
            JsonArray jSearchValue = jData.get("searchValue").getAsJsonArray();
            SearchValue[] jsv = gson.fromJson(jSearchValue, type);
            List<SearchValue> searchValueList = new ArrayList<>();
            for (SearchValue entity : jsv) {
                L.e("searchValueList：" + entity.toString());
                searchValueList.add(entity);
            }


        } catch (Exception e) {
        }
    }


}
