package com.qx.mstarstoreapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.inter.OnResultListener;

public class ConfirmPwdDialog extends DialogFragment {

	private Context mContext;
	private OnResultListener<String> listener;
	private String mTitle, mMsg;
	private EditText pwd;
	public ConfirmPwdDialog(Context context, String title, String msg) {
		mContext = context;
		mTitle = title;
		mMsg = msg;
	}

	public void setConfirmListener(OnResultListener<String> listener) {
		this.listener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final View view = View.inflate(mContext, R.layout.pay_dialog, null);
		final Dialog dlg = new Dialog(mContext, R.style.Theme_dialog);

		TextView tvTitle = (TextView) view.findViewById(R.id.title_text);
		pwd = (EditText) view.findViewById(R.id.id_ed_pwd);
		Button btnYes = (Button) view.findViewById(R.id.id_btn_pay);

		ImageView imgNo = (ImageView) view.findViewById(R.id.id_ig_back);


		tvTitle.setText("确认密码");
		btnYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onResult(true, pwd.getText().toString());
				}
				dlg.dismiss();
			}
		});

		imgNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dlg.dismiss();
			}
		});
		dlg.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
		dlg.setContentView(view);
		return dlg;
	}

	@Override
	public void onStart() {
		super.onStart();
	}
}
