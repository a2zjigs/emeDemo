package com.iih.android.emergency;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.location.commonUtil.XmlData;

public class ResetPasswordActivity extends Activity {

	private TextView mTitle;
	private Button mResetButton;
	private Button HomeButton;

	private EditText mOldpassword;
	private EditText mNewPassword;
	private EditText mConfirmNewpass;

	private String strOldpass;
	private String strNewpass;
	private String StrConfirmNewpass;

	private String strstatus ="";
	private String strFirstname = "";
	private String strLastname = "";
	private String strEmailid = "";
	private String strPassword = "";
	private String strUphoneno = "";
	private String strbloodgrp = "";
	private String strallergies = "";
	private String strdiabetic = "";
	private String strbldpressure = "";
	private String strcancer = "";
	private String strpolice = "";
	private String strhospital = "";
	private String strfirestation = "";
	private String strfriendno = "";

	private String strEmail1 = "";
	private String strEmail2 = "";
	private String strEmail3 = "";

	private String strPhoneno1 = "";
	private String strPhoneno2 = "";
	private String strPhoneno3 = "";

	private String strLandline1 = "";
	private String strLandline2 = "";
	private String strLandline3 = "";

	private String latitude = "";
	private String longitude = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resetpassword);

		initialization();

		mResetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resetpassword();
			}
		});
	}

	public void initialization() {
		// TODO Auto-generated method stub
		mTitle = (TextView)findViewById(R.id.title);
		mTitle.setText("Reset password");

		mResetButton = (Button)findViewById(R.id.actionbar_button);
		mResetButton.setBackgroundResource(R.drawable.reset_selector);

		HomeButton = (Button)findViewById(R.id.Home_Button);
		HomeButton.setVisibility(View.INVISIBLE);

		mOldpassword = (EditText)findViewById(R.id.oldpassword);
		mNewPassword = (EditText)findViewById(R.id.newpassword);
		mConfirmNewpass = (EditText)findViewById(R.id.newconfirmpassword);
	}

	public void resetpassword() {
		// TODO Auto-generated method stub
		strOldpass = mOldpassword.getText().toString().trim();
		strNewpass = mNewPassword.getText().toString().trim();
		StrConfirmNewpass = mConfirmNewpass.getText().toString().trim();

		strPassword = AppTypeDetails.getInstance(ResetPasswordActivity.this).getpassowrd();

		if(CommonUtil.isEmpty(strOldpass) && CommonUtil.isEmpty(strNewpass) && CommonUtil.isEmpty(StrConfirmNewpass)){
			CommonUtil.showAlert("Please complete the information.", ResetPasswordActivity.this);
		}else if(CommonUtil.isEmpty(strOldpass)){
			CommonUtil.showAlert("Please enter old password", ResetPasswordActivity.this);
		}else if(CommonUtil.isEmpty(strNewpass)){
			CommonUtil.showAlert("Please enter new password", ResetPasswordActivity.this);
		}else if(CommonUtil.isEmpty(StrConfirmNewpass)){
			CommonUtil.showAlert("Please confirm new password", ResetPasswordActivity.this);
		}else if(!strNewpass.equalsIgnoreCase(StrConfirmNewpass)){
			CommonUtil.showAlert("New password & Confirm new password does not match.", ResetPasswordActivity.this);
		}else if(!strOldpass.equalsIgnoreCase(strPassword)){
			CommonUtil.showAlert("Old passowrd does not match.", ResetPasswordActivity.this);
		}else{

			GetSharedPrefrenceData();

			/**
			 * Updated New password in XML file
			 */
			XmlData.CreateXml(strstatus, strFirstname, strLastname, strEmailid,
					strNewpass, strUphoneno, strbloodgrp, strallergies,
					strdiabetic, strbldpressure, strcancer, strpolice,
					strhospital, strfirestation, strfriendno, strEmail1,
					strEmail2, strEmail3, strPhoneno1, strPhoneno2,
					strPhoneno3, strLandline1, strLandline2, strLandline3,
					latitude, longitude);

			CommonUtil.showAlert("Password successfully change.", ResetPasswordActivity.this);
			finish();
		}
	}
	/**
	 * Get All The Data from Shared Preferences editor
	 */
	public void GetSharedPrefrenceData() {

		strstatus = AppTypeDetails.getInstance(ResetPasswordActivity.this).Getstatus();
		strFirstname = AppTypeDetails.getInstance(ResetPasswordActivity.this).getFirstName();
		strLastname = AppTypeDetails.getInstance(ResetPasswordActivity.this).getLastName();
		strEmailid = AppTypeDetails.getInstance(ResetPasswordActivity.this).getEmail();
		strPassword = AppTypeDetails.getInstance(ResetPasswordActivity.this).getpassowrd();
		strUphoneno = AppTypeDetails.getInstance(ResetPasswordActivity.this).getPhone();
		strbloodgrp = AppTypeDetails.getInstance(ResetPasswordActivity.this).getBloodgroup();
		strallergies = AppTypeDetails.getInstance(ResetPasswordActivity.this).getAllergies();
		strdiabetic = AppTypeDetails.getInstance(ResetPasswordActivity.this).getDiabetic();
		strbldpressure = AppTypeDetails.getInstance(ResetPasswordActivity.this).getBloodPressure();
		strcancer = AppTypeDetails.getInstance(ResetPasswordActivity.this).getCancer();
		strpolice = AppTypeDetails.getInstance(ResetPasswordActivity.this).getpoliceno();
		strhospital = AppTypeDetails.getInstance(ResetPasswordActivity.this).getHospitalno();
		strfirestation = AppTypeDetails.getInstance(ResetPasswordActivity.this).getfireno();
		strfriendno = AppTypeDetails.getInstance(ResetPasswordActivity.this).getfriendno();

		strEmail1 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getEmailAddress1();
		strEmail2 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getEmailAddress2();
		strEmail3 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getEmailAddress3();

		strPhoneno1 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getPhoneNumber1();
		strPhoneno2 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getPhoneNumber2();
		strPhoneno3 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getPhoneNumber3();

		strLandline1 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getLandline1();
		strLandline2 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getLandline2();
		strLandline3 = AppTypeDetails.getInstance(ResetPasswordActivity.this).getLandline3();

		latitude = AppTypeDetails.getInstance(ResetPasswordActivity.this).GetRegLatitude();
		longitude = AppTypeDetails.getInstance(ResetPasswordActivity.this).GetRegLongitude();
	}
}
