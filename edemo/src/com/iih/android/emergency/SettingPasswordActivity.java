package com.iih.android.emergency;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;

public class SettingPasswordActivity extends Dialog implements android.view.View.OnClickListener,Runnable{

	private Activity activity;
	private Button mOkbutton;
	private EditText mPassword;
	private Button mCancelbtn;

	public SettingPasswordActivity(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		activity = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setCanceledOnTouchOutside(false);
		setContentView(R.layout.setting_password_validation);

		mOkbutton = (Button)findViewById(R.id.ok);
		mPassword = (EditText)findViewById(R.id.passvalidation);
		mCancelbtn = (Button)findViewById(R.id.cancel);

		mOkbutton.setOnClickListener(this);
		mCancelbtn.setOnClickListener(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.ok:
			passwordvalidation();
			break;

		case R.id.cancel:
			this.dismiss();
			break;
		default:
			break;
		}
		}

	public void passwordvalidation() {
		// TODO Auto-generated method stub
		String strPassword = mPassword.getText().toString().trim();

		String getpass = AppTypeDetails.getInstance(activity).getpassowrd();

		if (CommonUtil.isEmpty(strPassword)) {
			CommonUtil.showAlert("Please enter password",activity);
		} else if(!strPassword.equalsIgnoreCase(getpass)){
			CommonUtil.showAlert("Wrong password.Please Try Again",activity);
		}else{
			this.dismiss();
			Intent intent = new Intent(activity,SettingActivity.class);
			activity.startActivity(intent);
	//		activity.finish();
		}
	}
}
