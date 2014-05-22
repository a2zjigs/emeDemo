package com.iih.android.emergency;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.location.commonUtil.XmlData;

public class EmailListActivity extends Activity {

	private TextView mTitle; 
	private Button mSaveButton;
	private Button HomeButton;

	private EditText mEmail1;
	private EditText mEmail2;
	private EditText mEmail3;
	private Boolean IsfinishAct =false;

	private String strstatus = "";
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

	private Boolean IsDataSave =false;

	private String latitude = "";
	private String longitude = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergencyemails);

		initialization();

		mSaveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SaveEmails();
			}
		});
	}
	public void initialization() {
		// TODO Auto-generated method stub
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("Email Address");

		mSaveButton = (Button)findViewById(R.id.actionbar_button);
		mSaveButton.setBackgroundResource(R.drawable.save_selector);

		HomeButton = (Button)findViewById(R.id.Home_Button);
		HomeButton.setVisibility(View.INVISIBLE);

		mEmail1 = (EditText)findViewById(R.id.Email1);
		mEmail2 = (EditText)findViewById(R.id.Email2);
		mEmail3 = (EditText)findViewById(R.id.Email3);
	}

	public void SaveEmails() {
		// TODO Auto-generated method stub
		strEmail1 = mEmail1.getText().toString().trim();
		strEmail2 = mEmail2.getText().toString().trim();
		strEmail3 = mEmail3.getText().toString().trim();

		if (CommonUtil.isEmpty(strEmail1) && CommonUtil.isEmpty(strEmail2) && CommonUtil.isEmpty(strEmail3)) {
			finish();
		} else {
			if (!CommonUtil.isEmpty(strEmail1)) {
				IsfinishAct = false;
				checkEmailValidation(strEmail1);
			}
			if (!CommonUtil.isEmpty(strEmail2)) {
				IsfinishAct = false;
				checkEmailValidation(strEmail2);
			}
			if (!CommonUtil.isEmpty(strEmail3)) {
				IsfinishAct = false;
				checkEmailValidation(strEmail3);
			}

			if (IsfinishAct) {
				IsDataSave = true;
				setsharedPrefernces();
				GetSharedPrefrenceData();
				XmlData.CreateXml(strstatus, strFirstname, strLastname,
						strEmailid, strPassword, strUphoneno, strbloodgrp,
						strallergies, strdiabetic, strbldpressure, strcancer,
						strpolice, strhospital, strfirestation, strfriendno,
						strEmail1, strEmail2, strEmail3, strPhoneno1,
						strPhoneno2, strPhoneno3, strLandline1, strLandline2,
						strLandline3, latitude, longitude);
				finish();
		}
		}
	}
	public void setsharedPrefernces() {
		// TODO Auto-generated method stub
		AppTypeDetails.getInstance(EmailListActivity.this).setEmailAddress1(strEmail1);
		AppTypeDetails.getInstance(EmailListActivity.this).setEmailAddress2(strEmail2);
		AppTypeDetails.getInstance(EmailListActivity.this).setEmailAddress3(strEmail3);
	}

	/**
	 * Get All the Data from Shared Preferences editor
	 */

	public void GetSharedPrefrenceData() {

		strstatus = AppTypeDetails.getInstance(EmailListActivity.this).Getstatus();
		strFirstname = AppTypeDetails.getInstance(EmailListActivity.this).getFirstName();
		strLastname = AppTypeDetails.getInstance(EmailListActivity.this).getLastName();
		strEmailid = AppTypeDetails.getInstance(EmailListActivity.this).getEmail();
		strPassword = AppTypeDetails.getInstance(EmailListActivity.this).getpassowrd();
		strUphoneno = AppTypeDetails.getInstance(EmailListActivity.this).getPhone();
		strbloodgrp = AppTypeDetails.getInstance(EmailListActivity.this).getBloodgroup();
		strallergies = AppTypeDetails.getInstance(EmailListActivity.this).getAllergies();
		strdiabetic = AppTypeDetails.getInstance(EmailListActivity.this).getDiabetic();
		strbldpressure = AppTypeDetails.getInstance(EmailListActivity.this).getBloodPressure();
		strcancer = AppTypeDetails.getInstance(EmailListActivity.this).getCancer();
		strpolice = AppTypeDetails.getInstance(EmailListActivity.this).getpoliceno();
		strhospital = AppTypeDetails.getInstance(EmailListActivity.this).getHospitalno();
		strfirestation = AppTypeDetails.getInstance(EmailListActivity.this).getfireno();
		strfriendno = AppTypeDetails.getInstance(EmailListActivity.this).getfriendno(); 

		strPhoneno1 = AppTypeDetails.getInstance(EmailListActivity.this).getPhoneNumber1();
		strPhoneno2 = AppTypeDetails.getInstance(EmailListActivity.this).getPhoneNumber2();
		strPhoneno3 = AppTypeDetails.getInstance(EmailListActivity.this).getPhoneNumber3();

		strLandline1 = AppTypeDetails.getInstance(EmailListActivity.this).getLandline1();
		strLandline2 = AppTypeDetails.getInstance(EmailListActivity.this).getLandline2();
		strLandline3 = AppTypeDetails.getInstance(EmailListActivity.this).getLandline3();

		latitude = AppTypeDetails.getInstance(EmailListActivity.this).GetRegLatitude();
		longitude = AppTypeDetails.getInstance(EmailListActivity.this).GetRegLongitude();
	}

	public void checkEmailValidation(String email) {
		// TODO Auto-generated method stub
		if(!CommonUtil.checkingEmail(email)){
			CommonUtil.showAlert("Enter Proper Email Address", EmailListActivity.this);
		}else{
			IsfinishAct=true;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		strEmail1 = mEmail1.getText().toString().trim();
		strEmail2 = mEmail2.getText().toString().trim();
		strEmail3 = mEmail3.getText().toString().trim();

		if (CommonUtil.isEmpty(strEmail1) && CommonUtil.isEmpty(strEmail1) && CommonUtil.isEmpty(strEmail1)) {
			finish();
		}else{
			if (!IsDataSave) {
				Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Message");
				builder.setIcon(R.drawable.ic_launcher);
				builder.setMessage("You haven't save the data\n Would you like to save");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
				builder.show();
			}else{
			super.onBackPressed();
			}
	}
}
}
