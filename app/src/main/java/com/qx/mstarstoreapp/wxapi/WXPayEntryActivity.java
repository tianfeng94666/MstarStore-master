package com.qx.mstarstoreapp.wxapi;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.qx.mstarstoreapp.activity.ModeOfPaymentActivity;
import com.qx.mstarstoreapp.activity.PaySuccessActivity;
import com.qx.mstarstoreapp.base.AppURL;
import com.qx.mstarstoreapp.base.Global;
import com.qx.mstarstoreapp.pay.WXPayInfo;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信支付显示结果的页面
 * @author asus1
 *
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("支付结果页面");
        setContentView(tv);

    	api = WXAPIFactory.createWXAPI(this, Global.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			Intent intent;
			if(resp.errCode==0){
				intent = new Intent(this, PaySuccessActivity.class);
				if (!Global.id.equals("")) {
					intent.putExtra("id", Global.id);
					intent.putExtra("type", Global.type + "");
				}
				startActivity(intent);
				Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
				finish();
			}else {
				Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
				finish();
			}

		}
	}
}