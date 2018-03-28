package com.qx.mstarstoreapp.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qx.mstarstoreapp.activity.LoginActivity;
import com.qx.mstarstoreapp.activity.MainActivity;
import com.qx.mstarstoreapp.activity.StoneHistoryOrder;
import com.qx.mstarstoreapp.base.BaseApplication;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.utils.SpUtils;
import com.qx.mstarstoreapp.utils.StringUtils;

import org.json.JSONObject;

import java.util.logging.Logger;

import cn.jpush.android.api.JPushInterface;

import static com.qx.mstarstoreapp.base.BaseActivity.JUMP_TO_ACTIVITY;
import static com.qx.mstarstoreapp.base.BaseApplication.getApplication;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class JipushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();


        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            "JPush用户注册成功"
           Global.REGISTRATION =((BaseApplication)getApplication()).getRegistrationID();

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            Logger.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Logger.d(TAG, "接受到推送下来的通知");

            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Logger.d(TAG, "用户点击打开了通知");

            openNotification(context,intent);

        } else {
//            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){

    }

    private void openNotification(Context context, Intent intent){
       String token = BaseApplication.spUtils.getString(SpUtils.key_tokenKey);
       if(StringUtils.isEmpty(token)){
           Intent intent2 = new Intent(context, LoginActivity.class);
           context.startActivity(intent2);
       }else {
           intent.setClass(context, StoneHistoryOrder.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);
       }
    }



}
