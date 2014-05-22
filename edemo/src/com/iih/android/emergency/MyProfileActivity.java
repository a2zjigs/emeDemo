package com.iih.android.emergency;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
import com.iih.location.commonUtil.XmlData;

public class MyProfileActivity extends Activity {

	private TextView mTitle;
	private Button mUpdatebutton;
	private Button HomeButton;

	private Boolean isEdit = false;

	private EditText mFirstName;
	private EditText mLastName;
	private EditText mEmailId;
	private EditText mPhoneno;
	private Spinner mBloodgroup;
	private EditText mAllergies;
	private EditText mPoliceHepline;
	private EditText mHospitalHelpline;
	private EditText mFirestation;
	private EditText mFriendno;

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
	private String strstatus = "";

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

	private CheckBox mDiabeticChkbx;
	private CheckBox mBldPressureChkbx;
	private CheckBox mCancerChkbx;

	private TextView allergieslable;

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

	private int bldgrpposition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myprofile);

		initialization();
		GetSharedPreValue();
		Settext();

		HomeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MyProfileActivity.this,HomeActivity.class));
				finish();
			}
		});

		mUpdatebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUpdatebutton.setBackgroundResource(R.drawable.update_selector);
				setClickableEditText();
				setcolorBlack();
				if(isEdit == true)
				{
				EditData();
				}
				isEdit = true;
			}
		});

		mBloodgroup.setEnabled(false);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, bloodgroup);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mBloodgroup.setAdapter(adapter);
		/**
		 * set selected value.
		 */
		for (int i = 0; i < bloodgroup.length; i++) {
			if(strbloodgrp.equalsIgnoreCase(bloodgroup[i])){
				bldgrpposition = i;
				break;
			}
		}
		mBloodgroup.setSelection(bldgrpposition);

		/**
		 * updated value on click of Spinner
		 */
		mBloodgroup.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
				int item = mBloodgroup.getSelectedItemPosition();
				strbloodgrp = bloodgroup[item];
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		mBloodgroup.invalidate();

		mDiabeticChkbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					strdiabetic = "Is diabetic";
				}else{
					strdiabetic = "";
				}
			}
		});

		mBldPressureChkbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
				strbldpressure = "Blood Pressure";
				}else{
					strbldpressure = "";
				}
			}
		});

		mCancerChkbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
				strcancer = "Cancer";
				}else{
					strcancer = "";
				}
			}
		});
	}

	private void initialization() {
		// TODO Auto-generated method stub
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("My Profile");

		mUpdatebutton = (Button)findViewById(R.id.actionbar_button);
		mUpdatebutton.setBackgroundResource(R.drawable.edit_selector);

		HomeButton = (Button)findViewById(R.id.Home_Button);
		allergieslable = (TextView)findViewById(R.id.allergiesLable);

		mFirstName = (EditText)findViewById(R.id.Firstname);
		mLastName = (EditText)findViewById(R.id.LastName);
		mEmailId = (EditText)findViewById(R.id.Email);
		mPhoneno = (EditText)findViewById(R.id.Phone);
		mBloodgroup = (Spinner) findViewById(R.id.bloodgrp);
		mAllergies = (EditText) findViewById(R.id.allergies);
		mPoliceHepline = (EditText)findViewById(R.id.policehelpno);
		mHospitalHelpline = (EditText)findViewById(R.id.hospitalhelpno);
		mFirestation = (EditText)findViewById(R.id.firestation);
		mFriendno = (EditText)findViewById(R.id.friendno);

		mDiabeticChkbx = (CheckBox) findViewById(R.id.diabetic);
		mBldPressureChkbx = (CheckBox) findViewById(R.id.Bloodpressure);
		mCancerChkbx = (CheckBox) findViewById(R.id.cancer);
	}

	/**
	 * Get All Data From Shared Preferences Editor
	 */

	public void GetSharedPreValue() {

		strstatus = AppTypeDetails.getInstance(MyProfileActivity.this).Getstatus();
		strFirstname = AppTypeDetails.getInstance(MyProfileActivity.this).getFirstName();
		strLastname = AppTypeDetails.getInstance(MyProfileActivity.this).getLastName();
		strEmailid = AppTypeDetails.getInstance(MyProfileActivity.this).getEmail();
		strPassword = AppTypeDetails.getInstance(MyProfileActivity.this).getpassowrd();
		strUphoneno = AppTypeDetails.getInstance(MyProfileActivity.this).getPhone();
		strbloodgrp = AppTypeDetails.getInstance(MyProfileActivity.this).getBloodgroup();
		strallergies = AppTypeDetails.getInstance(MyProfileActivity.this).getAllergies();

		if(strallergies.equalsIgnoreCase("")){
			allergieslable.setVisibility(View.GONE);
			mAllergies.setVisibility(View.GONE);
		}

		strdiabetic = AppTypeDetails.getInstance(MyProfileActivity.this).getDiabetic();
		strbldpressure = AppTypeDetails.getInstance(MyProfileActivity.this).getBloodPressure();
		strcancer = AppTypeDetails.getInstance(MyProfileActivity.this).getCancer();
		strpolice = AppTypeDetails.getInstance(MyProfileActivity.this).getpoliceno();
		strhospital = AppTypeDetails.getInstance(MyProfileActivity.this).getHospitalno();
		strfirestation = AppTypeDetails.getInstance(MyProfileActivity.this).getfireno();
		strfriendno = AppTypeDetails.getInstance(MyProfileActivity.this).getfriendno(); 

		strEmail1 = AppTypeDetails.getInstance(MyProfileActivity.this).getEmailAddress1();
		strEmail2 = AppTypeDetails.getInstance(MyProfileActivity.this).getEmailAddress2();
		strEmail3 = AppTypeDetails.getInstance(MyProfileActivity.this).getEmailAddress3();

		strPhoneno1 = AppTypeDetails.getInstance(MyProfileActivity.this).getPhoneNumber1();
		strPhoneno2 = AppTypeDetails.getInstance(MyProfileActivity.this).getPhoneNumber2();
		strPhoneno3 = AppTypeDetails.getInstance(MyProfileActivity.this).getPhoneNumber3();

		strLandline1 = AppTypeDetails.getInstance(MyProfileActivity.this).getLandline1();
		strLandline2 = AppTypeDetails.getInstance(MyProfileActivity.this).getLandline2();
		strLandline3 = AppTypeDetails.getInstance(MyProfileActivity.this).getLandline3();

		latitude = AppTypeDetails.getInstance(MyProfileActivity.this).GetRegLatitude();
		longitude = AppTypeDetails.getInstance(MyProfileActivity.this).GetRegLongitude();
	}

	public void Settext() {
		mFirstName.setText(strFirstname);
		mLastName.setText(strLastname);
		mEmailId.setText(strEmailid);
		mPhoneno.setText(strUphoneno);
		mAllergies.setText(strallergies);
		mPoliceHepline.setText(strpolice);
		mHospitalHelpline.setText(strhospital);
		mFirestation.setText(strfirestation);
		mFriendno.setText(strfriendno);


		if(strdiabetic.equalsIgnoreCase("Is diabetic")){
			mDiabeticChkbx.setChecked(true);
		}
		if(strbldpressure.equalsIgnoreCase("Blood Pressure")){
			mBldPressureChkbx.setChecked(true);
		}
		if(strcancer.equalsIgnoreCase("Cancer")){
			mCancerChkbx.setChecked(true);
		}
	}

	/**
	 * Edit Data and Update into XMl file.
	 */
	public void EditData() {
		// TODO Auto-generated method stub

		String Firstname = mFirstName.getText().toString().trim();
		String LastName = mLastName.getText().toString().trim();
		String EmailId = mEmailId.getText().toString().trim();
		String Phoneno = mPhoneno.getText().toString().trim();
		String Allergies = mAllergies.getText().toString().trim();
		String PoliceHepline = mPoliceHepline.getText().toString().trim();
		String HospitalHelpline = mHospitalHelpline.getText().toString().trim();
		String Firestation = mFirestation.getText().toString().trim();
		String Friendno = mFriendno.getText().toString().trim();

		AppTypeDetails.getInstance(MyProfileActivity.this).setFirstName(Firstname);
		AppTypeDetails.getInstance(MyProfileActivity.this).setLastName(LastName);
		AppTypeDetails.getInstance(MyProfileActivity.this).setEmail(EmailId);
		AppTypeDetails.getInstance(MyProfileActivity.this).setPhone(Phoneno);
		AppTypeDetails.getInstance(MyProfileActivity.this).setBloodgroup(strbloodgrp);
		AppTypeDetails.getInstance(MyProfileActivity.this).setAllergies(Allergies);
		AppTypeDetails.getInstance(MyProfileActivity.this).setDiabetic(strdiabetic);
		AppTypeDetails.getInstance(MyProfileActivity.this).setBloodPressure(strbldpressure);
		AppTypeDetails.getInstance(MyProfileActivity.this).setcancer(strcancer);
		AppTypeDetails.getInstance(MyProfileActivity.this).setpoliceno(PoliceHepline);
		AppTypeDetails.getInstance(MyProfileActivity.this).setHospitalno(HospitalHelpline);
		AppTypeDetails.getInstance(MyProfileActivity.this).setfireno(Firestation);
		AppTypeDetails.getInstance(MyProfileActivity.this).setfriendno(Friendno);

		/**
		 * Updated Data in XML File
		 */
		XmlData.CreateXml(strstatus, Firstname, LastName, EmailId,
				strPassword, Phoneno, strbloodgrp, Allergies,
				strdiabetic, strbldpressure, strcancer, PoliceHepline, HospitalHelpline,
				Firestation, Friendno, strEmail1, strEmail2, strEmail3,
				strPhoneno1, strPhoneno2, strPhoneno3, strLandline1,
				strLandline2, strLandline3, latitude, longitude);
		finish();
	}

	public void setClickableEditText() {
		// TODO Auto-generated method stub
		mFirstName.setEnabled(true);
		mLastName.setEnabled(true);
		mEmailId.setEnabled(true);
		mPhoneno.setEnabled(true);
		mBloodgroup.setEnabled(true);
		mAllergies.setEnabled(true);
		mAllergies.setVisibility(View.VISIBLE);
		allergieslable.setVisibility(View.GONE);
		mDiabeticChkbx.setEnabled(true);
		mBldPressureChkbx.setEnabled(true);
		mCancerChkbx.setEnabled(true);
		mPoliceHepline.setEnabled(true);
		mHospitalHelpline.setEnabled(true);
		mFirestation.setEnabled(true);
		mFriendno.setEnabled(true);
	}

	public void setcolorBlack(){

		mFirstName.setTextColor(Color.BLACK);
		mLastName.setTextColor(Color.BLACK);
		mEmailId.setTextColor(Color.BLACK);
		mPhoneno.setTextColor(Color.BLACK);
		mAllergies.setTextColor(Color.BLACK);
		mPoliceHepline.setTextColor(Color.BLACK);
		mHospitalHelpline.setTextColor(Color.BLACK);
		mFirestation.setTextColor(Color.BLACK);
		mFriendno.setTextColor(Color.BLACK);
	}
}
