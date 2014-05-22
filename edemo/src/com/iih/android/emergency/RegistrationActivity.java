package com.iih.android.emergency;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.location.commonUtil.XmlData;

public class RegistrationActivity extends Activity implements OnClickListener,OnCheckedChangeListener {

	private Button mRegisterButton;
	private TextView mTitle;
	private Button HomeButton;

	private Button mEmailAddress;
	private Button mPhoneNumbers;

	private EditText mFirstName;
	private EditText mLastName;
	private EditText mEmailid;
	private EditText mPassword;
	private EditText mConfirmPwd;
	private EditText mPhoneno;
	private Spinner mBloodgroup;
	private EditText mAllergies;
	private EditText mPoliceno;
	private EditText mHospitalno;
	private EditText mFirestationo;
	private EditText mFriendno;

	private CheckBox mDiabeticChkbx;
	private CheckBox mBldPressureChkbx;
	private CheckBox mCancerChkbx;

	private String strFirstname = "";
	private String strLastname = "";
	private String strEmailid = "";
	private String strPassword = "";
	private String strconfirmpass = "";
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
	private String strstatus = "";

	private String[] latlongs;
	private String latitude;
	private String longitude;

	private String[] bloodgroup = 
		{
			"O+",
			"A+",
			"B+",
			"O-",
			"A-",
			"AB+",
			"B-",
			"AB-"
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registeration);

		initialization();

		mRegisterButton.setOnClickListener(this);
		mEmailAddress.setOnClickListener(this);
		mPhoneNumbers.setOnClickListener(this);
		mDiabeticChkbx.setOnCheckedChangeListener(this);
		mBldPressureChkbx.setOnCheckedChangeListener(this);
		mCancerChkbx.setOnCheckedChangeListener(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, bloodgroup);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mBloodgroup.setAdapter(adapter);
		mBloodgroup.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				int item = mBloodgroup.getSelectedItemPosition();
				strbloodgrp = bloodgroup[item];
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	public void initialization() {
		// TODO Auto-generated method stub
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("Registration");

		mRegisterButton = (Button) findViewById(R.id.actionbar_button);
		mRegisterButton.setBackgroundResource(R.drawable.register_selector);

		HomeButton = (Button) findViewById(R.id.Home_Button);
		HomeButton.setVisibility(View.INVISIBLE);

		mEmailAddress = (Button) findViewById(R.id.EmergncyEmail);
		mPhoneNumbers = (Button) findViewById(R.id.EmergencyPhone);

		mFirstName = (EditText) findViewById(R.id.Firstname);
		mLastName = (EditText) findViewById(R.id.Lastname);
		mEmailid = (EditText) findViewById(R.id.Email);
		mPassword = (EditText) findViewById(R.id.password);
		mConfirmPwd = (EditText) findViewById(R.id.Confirmpassword);
		mPhoneno = (EditText) findViewById(R.id.Phone);
		mPoliceno = (EditText) findViewById(R.id.Policehelpline);
		mHospitalno = (EditText) findViewById(R.id.HospitalHelpLine);
		mFirestationo = (EditText) findViewById(R.id.FireHelpLine);
		mFriendno = (EditText) findViewById(R.id.FriendHelp);
		mBloodgroup = (Spinner) findViewById(R.id.bloodgrp);
		mAllergies = (EditText) findViewById(R.id.Allergies);

		mDiabeticChkbx = (CheckBox) findViewById(R.id.diabetic);
		mBldPressureChkbx = (CheckBox) findViewById(R.id.Bloodpressure);
		mCancerChkbx = (CheckBox) findViewById(R.id.cancer);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.actionbar_button:
			GetData(); // Get All the Registration Data
			turnGPSOff();
			break;
			
		case R.id.EmergncyEmail:
			startActivity(new Intent(RegistrationActivity.this,EmailListActivity.class));
			break;

		case R.id.EmergencyPhone:
			startActivity(new Intent(RegistrationActivity.this,PhoneNoActivity.class));
			break;

		default:
			break;
		}
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int id = buttonView.getId();
		switch (id) {
		case R.id.diabetic:
			if (isChecked) {
				strdiabetic = "Is diabetic";
			}
			break;

		case R.id.Bloodpressure:
			if(isChecked){
				strbldpressure = "Blood Pressure";
				}
			break;

		case R.id.cancer:
			if(isChecked){
				strcancer = "Cancer";
				}
			break;

		default:
			break;
		}
	}
	public void GetData() {
		// TODO Auto-generated method stub
		strstatus = "login";

		/**
		 * getting Current Latitude and Longitude
		 */
		latlongs = CommonUtil.getLatAndLong(RegistrationActivity.this);
		latitude = latlongs[0];
		longitude = latlongs[1];

		strFirstname = mFirstName.getText().toString().trim();
		strLastname = mLastName.getText().toString().trim();
		strEmailid = mEmailid.getText().toString().trim();
		strPassword = mPassword.getText().toString().trim();
		strconfirmpass = mConfirmPwd.getText().toString().trim();
		strUphoneno = mPhoneno.getText().toString().trim();
		strpolice = mPoliceno.getText().toString().trim();
		strhospital = mHospitalno.getText().toString().trim();
		strfirestation = mFirestationo.getText().toString().trim();
		strfriendno = mFriendno.getText().toString().trim();
		strallergies = mAllergies.getText().toString().trim();

		if (CommonUtil.isEmpty(strFirstname)) {
			//CommonUtil.showAlert("Please enter firstname.",RegistrationActivity.this);
			mFirstName.setBackgroundResource(R.drawable.gradiant);
		} else if (CommonUtil.isEmpty(strLastname)) {
			//CommonUtil.showAlert("Please enter lastname.",RegistrationActivity.this);
			mLastName.setBackgroundResource(R.drawable.gradiant);
		} else if (CommonUtil.isEmpty(strEmailid)) {
			//CommonUtil.showAlert("Please enter email.",RegistrationActivity.this);
			mEmailid.setBackgroundResource(R.drawable.gradiant);
		} else if (!CommonUtil.checkingEmail(strEmailid)) {
			CommonUtil.showAlert("Please enter proper email.",RegistrationActivity.this);
		} else if (CommonUtil.isEmpty(strPassword)) {
			//CommonUtil.showAlert("Please enter password.",RegistrationActivity.this);
			mPassword.setBackgroundResource(R.drawable.gradiant);
		} else if (CommonUtil.isEmpty(strconfirmpass)) {
			//CommonUtil.showAlert("Please confirm password.",RegistrationActivity.this);
			mConfirmPwd.setBackgroundResource(R.drawable.gradiant);
		} else if (!strPassword.equalsIgnoreCase(strconfirmpass)) {
			CommonUtil.showAlert("Password & Confirm password does not match.",RegistrationActivity.this);
		} else if (CommonUtil.isEmpty(strpolice)) {
			//CommonUtil.showAlert("Please enter police station number.",RegistrationActivity.this);
			mPoliceno.setBackgroundResource(R.drawable.gradiant);
		} else if (CommonUtil.isEmpty(strhospital)) {
			//CommonUtil.showAlert("Please enter hospital number.",RegistrationActivity.this);
			mHospitalno.setBackgroundResource(R.drawable.gradiant);
		} else if (CommonUtil.isEmpty(strfirestation)) {
			//CommonUtil.showAlert("Please enter fire station number.",RegistrationActivity.this);
			mFirestationo.setBackgroundResource(R.drawable.gradiant);
		} else if (CommonUtil.isEmpty(strfriendno)) {
			//CommonUtil.showAlert("Please enter Ics Number.",RegistrationActivity.this);
			mFriendno.setBackgroundResource(R.drawable.gradiant);
		} else {

			/**
			 * Store All the Data in Shared Preferences Editor
			 */
			AppTypeDetails.getInstance(RegistrationActivity.this).setFirstName(strFirstname);
			AppTypeDetails.getInstance(RegistrationActivity.this).setLastName(strLastname);
			AppTypeDetails.getInstance(RegistrationActivity.this).setEmail(strEmailid);
			AppTypeDetails.getInstance(RegistrationActivity.this).setpassword(strPassword);
			AppTypeDetails.getInstance(RegistrationActivity.this).setPhone(strUphoneno);
			AppTypeDetails.getInstance(RegistrationActivity.this).setBloodgroup(strbloodgrp);
			AppTypeDetails.getInstance(RegistrationActivity.this).setAllergies(strallergies);
			AppTypeDetails.getInstance(RegistrationActivity.this).setDiabetic(strdiabetic);
			AppTypeDetails.getInstance(RegistrationActivity.this).setBloodPressure(strbldpressure);
			AppTypeDetails.getInstance(RegistrationActivity.this).setcancer(strcancer);
			AppTypeDetails.getInstance(RegistrationActivity.this).setpoliceno(strpolice);
			AppTypeDetails.getInstance(RegistrationActivity.this).setHospitalno(strhospital);
			AppTypeDetails.getInstance(RegistrationActivity.this).setfireno(strfirestation);
			AppTypeDetails.getInstance(RegistrationActivity.this).setfriendno(strfriendno);
			AppTypeDetails.getInstance(RegistrationActivity.this).Setstatus(strstatus);
			AppTypeDetails.getInstance(RegistrationActivity.this).SetRegLatitude(latitude);
			AppTypeDetails.getInstance(RegistrationActivity.this).SetRegLongitude(longitude);

			String strEmail1 = AppTypeDetails.getInstance(RegistrationActivity.this).getEmailAddress1();
			String strEmail2 = AppTypeDetails.getInstance(RegistrationActivity.this).getEmailAddress2();
			String strEmail3 = AppTypeDetails.getInstance(RegistrationActivity.this).getEmailAddress3();

			String strPhoneno1 = AppTypeDetails.getInstance(RegistrationActivity.this).getPhoneNumber1();
			String strPhoneno2 = AppTypeDetails.getInstance(RegistrationActivity.this).getPhoneNumber2();
			String strPhoneno3 = AppTypeDetails.getInstance(RegistrationActivity.this).getPhoneNumber3();

			String strLandline1 = AppTypeDetails.getInstance(RegistrationActivity.this).getLandline1();
			String strLandline2 = AppTypeDetails.getInstance(RegistrationActivity.this).getLandline2();
			String strLandline3 = AppTypeDetails.getInstance(RegistrationActivity.this).getLandline3();

			/**
			 * Inserting Data into XML file
			 */
			XmlData.CreateXml(strstatus, strFirstname, strLastname, strEmailid,
					strPassword, strUphoneno, strbloodgrp, strallergies,
					strdiabetic, strbldpressure, strcancer, strpolice,
					strhospital, strfirestation, strfriendno, strEmail1,
					strEmail2, strEmail3, strPhoneno1, strPhoneno2,
					strPhoneno3, strLandline1, strLandline2, strLandline3,
					latitude, longitude);

			startActivity(new Intent(RegistrationActivity.this,HomeActivity.class));
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
		startActivity(i);
		finish();
		super.onBackPressed();
	}
@Override
public void finish() {
	// TODO Auto-generated method stub
	turnGPSOff();
	super.finish();
}
	
	// automatic turn off the gps
	public void turnGPSOff() {
		String provider = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (provider.contains("gps")) { // if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings","com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			this.sendBroadcast(poke);
		}else{
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", false);
			this.sendBroadcast(intent);
		}
	}
}
