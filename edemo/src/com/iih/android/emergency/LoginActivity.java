package com.iih.android.emergency;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.location.commonUtil.XmlData;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;

public class LoginActivity extends Activity {

	protected static final String TAG = LoginActivity.class.getName();

	private SimpleFacebook mSimpleFacebook;

	private Button mLoginbtn;
	private Button mRegistrationbtn;
	private Button mActionBarButton;
	private TextView mTitle;
	private Button HomeButton;
	private Button mLoginwithfb;
	private TextView forgetpssword;
	private EditText mUsername;
	private EditText mPassword;

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

	ForgetPasswordDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		initialization(); //Initialize all the layout

		mLoginbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginData();
			}
		});
		mLoginwithfb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSimpleFacebook.login(mOnLoginListener);
			}
		});

		mRegistrationbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
				finish();
				turnGPSOn();
			}
		});

		forgetpssword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new ForgetPasswordDialog(LoginActivity.this);
				dialog.show();
			}
		});
	}

	private void initialization() {
		// TODO Auto-generated method stub
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("Login");

		mActionBarButton = (Button)findViewById(R.id.actionbar_button);
		mActionBarButton.setVisibility(View.INVISIBLE);

		HomeButton = (Button)findViewById(R.id.Home_Button);
		HomeButton.setVisibility(View.INVISIBLE);

		mLoginwithfb = (Button)findViewById(R.id.loginwithfb);
		mRegistrationbtn = (Button)findViewById(R.id.Register);
		mLoginbtn = (Button)findViewById(R.id.Login);
		forgetpssword = (TextView)findViewById(R.id.Forgetpassword);
		mUsername = (EditText)findViewById(R.id.Username);
		mPassword = (EditText)findViewById(R.id.Password);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		mUsername.setText("");
		mPassword.setText("");
		super.onRestart();
	}
	/**
	 * Login Method
	 */
	public void loginData() {
		// TODO Auto-generated method stub
		

		String mStrUsername = mUsername.getText().toString().trim();
		String mStrpassword = mPassword.getText().toString().trim();

		String StrUsername = AppTypeDetails.getInstance(LoginActivity.this).getEmail();
		String StrPassword = AppTypeDetails.getInstance(LoginActivity.this).getpassowrd();

		if(CommonUtil.isEmpty(mStrUsername)){
			CommonUtil.showAlert("Please enter user name.", LoginActivity.this);
		} else if(CommonUtil.isEmpty(mStrpassword)){
			CommonUtil.showAlert("Please enter password", LoginActivity.this);
		} else if(!CommonUtil.checkingEmail(mStrUsername)){
			CommonUtil.showAlert("Enter proper email.", LoginActivity.this);
		}else {
			if (mStrUsername.equalsIgnoreCase(StrUsername) && mStrpassword.equalsIgnoreCase(StrPassword)) {

				strstatus = "login";
				AppTypeDetails.getInstance(LoginActivity.this).Setstatus(strstatus);

				GetSharedPrefrenceData();

				/**
				 * Updated Status in XML File
				 */
				XmlData.CreateXml(strstatus, strFirstname, strLastname, strEmailid,
						strPassword, strUphoneno, strbloodgrp, strallergies,
						strdiabetic, strbldpressure, strcancer, strpolice, strhospital,
						strfirestation, strfriendno, strEmail1, strEmail2, strEmail3,
						strPhoneno1, strPhoneno2, strPhoneno3, strLandline1,
						strLandline2, strLandline3, latitude, longitude);

				startActivity(new Intent(LoginActivity.this, HomeActivity.class));
				finish();
			}else{
				CommonUtil.showAlert("Invalid username and password.", LoginActivity.this);
			}
		}
	}

	/**
	 * Get All The Data from Shared Preferences editor
	 */
	public void GetSharedPrefrenceData() {

		strFirstname = AppTypeDetails.getInstance(LoginActivity.this).getFirstName();
		strLastname = AppTypeDetails.getInstance(LoginActivity.this).getLastName();
		strEmailid = AppTypeDetails.getInstance(LoginActivity.this).getEmail();
		strPassword = AppTypeDetails.getInstance(LoginActivity.this).getpassowrd();
		strUphoneno = AppTypeDetails.getInstance(LoginActivity.this).getPhone();
		strbloodgrp = AppTypeDetails.getInstance(LoginActivity.this).getBloodgroup();
		strallergies = AppTypeDetails.getInstance(LoginActivity.this).getAllergies();
		strdiabetic = AppTypeDetails.getInstance(LoginActivity.this).getDiabetic();
		strbldpressure = AppTypeDetails.getInstance(LoginActivity.this).getBloodPressure();
		strcancer = AppTypeDetails.getInstance(LoginActivity.this).getCancer();
		strpolice = AppTypeDetails.getInstance(LoginActivity.this).getpoliceno();
		strhospital = AppTypeDetails.getInstance(LoginActivity.this).getHospitalno();
		strfirestation = AppTypeDetails.getInstance(LoginActivity.this).getfireno();
		strfriendno = AppTypeDetails.getInstance(LoginActivity.this).getfriendno(); 

		strEmail1 = AppTypeDetails.getInstance(LoginActivity.this).getEmailAddress1();
		strEmail2 = AppTypeDetails.getInstance(LoginActivity.this).getEmailAddress2();
		strEmail3 = AppTypeDetails.getInstance(LoginActivity.this).getEmailAddress3();

		strPhoneno1 = AppTypeDetails.getInstance(LoginActivity.this).getPhoneNumber1();
		strPhoneno2 = AppTypeDetails.getInstance(LoginActivity.this).getPhoneNumber2();
		strPhoneno3 = AppTypeDetails.getInstance(LoginActivity.this).getPhoneNumber3();

		strLandline1 = AppTypeDetails.getInstance(LoginActivity.this).getLandline1();
		strLandline2 = AppTypeDetails.getInstance(LoginActivity.this).getLandline2();
		strLandline3 = AppTypeDetails.getInstance(LoginActivity.this).getLandline3();

		latitude = AppTypeDetails.getInstance(LoginActivity.this).GetRegLatitude();
		longitude = AppTypeDetails.getInstance(LoginActivity.this).GetRegLongitude();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mSimpleFacebook = SimpleFacebook.getInstance(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		CommonUtil.isBackPress = true;
//		Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//		intent.putExtra("onback", "yes");
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		startActivity(intent);
		finish();

		super.onBackPressed();
	}

	public void loginwithfacebook() {

		strstatus = "fblogin";

		AppTypeDetails.getInstance(LoginActivity.this).Setstatus(strstatus);

		GetSharedPrefrenceData();

		/**
		 * Updated Status in XML File
		 */
		XmlData.CreateXml(strstatus, strFirstname, strLastname, strEmailid,
				strPassword, strUphoneno, strbloodgrp, strallergies,
				strdiabetic, strbldpressure, strcancer, strpolice, strhospital,
				strfirestation, strfriendno, strEmail1, strEmail2, strEmail3,
				strPhoneno1, strPhoneno2, strPhoneno3, strLandline1,
				strLandline2, strLandline3, latitude, longitude);
	

		Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
		startActivity(intent);
		finish();
	}

	// automatic turn on the gps
	public void turnGPSOn() {

		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		this.sendBroadcast(intent);

		String provider = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")) { // if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings","com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			this.sendBroadcast(poke);
		}
	}

	// Login listener
				public OnLoginListener mOnLoginListener = new OnLoginListener()
				{

					@Override
					public void onFail(String reason)
					{
						Log.w(TAG, "Failed to login");
					}

					@Override
					public void onException(Throwable throwable)
					{
						Log.e(TAG, "Bad thing happened", throwable);
					}

					@Override
					public void onThinking()
					{
						// show progress bar or something to the user while login is happening
					}

					@Override
					public void onLogin()
					{
						// change the state of the button or do whatever you want
						if (CommonUtil.checkConn(LoginActivity.this) == true) {
							loginwithfacebook();
							Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_LONG).show();
							}else{
								CommonUtil.showAlert("Internet Connection Failed",LoginActivity.this);
							}
					}

					@Override
					public void onNotAcceptingPermissions()
					{
						Toast.makeText(LoginActivity.this, "You didn't accept read permissions", Toast.LENGTH_LONG).show();
					}
				};
}
