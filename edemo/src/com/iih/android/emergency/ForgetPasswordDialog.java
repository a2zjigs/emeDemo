package com.iih.android.emergency;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.emergency.email.EasyMail;
import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.location.commonUtil.XmlData;

public class ForgetPasswordDialog extends Dialog implements android.view.View.OnClickListener,Runnable{

	private Activity activity;
	private Button mOkButton;
	private Button mCancelButton;

	private static String RandomPassword;

	private String password = "intelithub@123";
	private String user = "test.intelithub@gmail.com";

	private String strFirstname = "";
	private String strLastname = "";
	private String strEmailid = "";
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

	private static final String ALLOWED_CHARACTERS = "qwertyuiopasd0123456789@#$&*fghjklzxcvbnm";

	public ForgetPasswordDialog(Activity context) {
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
		setContentView(R.layout.forgetpassword);

		mOkButton = (Button)findViewById(R.id.ok);
		mCancelButton =(Button)findViewById(R.id.cancelbtn);

		mOkButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
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
			if(CommonUtil.checkConn(activity)){
			new fogetpassAsync(activity).execute();
			}else{
				CommonUtil.showAlert("Internet connection failed.", activity);
				ForgetPasswordDialog.this.dismiss();
			}
			break;

		case R.id.cancelbtn:
			this.dismiss();
			break;

		default:
			break;
		}
	}

	public class fogetpassAsync extends AsyncTask<Void, Void, Void> {

		Context context;
		private ProgressDialog bar;

		public fogetpassAsync() {
			// TODO Auto-generated constructor stub
		}

		public fogetpassAsync(Context context, ListView listView) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		public fogetpassAsync(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				bar.dismiss();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				bar = new ProgressDialog(context);
				bar.setIndeterminate(true);
				bar.setTitle("Loading...");
				bar.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			SendRandomPassword();
			ForgetPasswordDialog.this.dismiss();
			return null;
		}

	}
	public void SendRandomPassword() {
		// TODO Auto-generated method stub
		final Random random = new Random();
		final StringBuilder sb = new StringBuilder();

		int sizeOfRandomString = 8;

		for (int i = 0; i < sizeOfRandomString; ++i){
			sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
		}
			RandomPassword = sb.toString().trim();

			AppTypeDetails.getInstance(activity).setpassword(RandomPassword);//Update Change Password
			GetSharedPrefrenceData();

			/**
			 * Random Password Stored in XML File
			 */
		XmlData.CreateXml("", strFirstname, strLastname, strEmailid,
				RandomPassword, strUphoneno, strbloodgrp, strallergies,
				strdiabetic, strbldpressure, strcancer, strpolice, strhospital,
				strfirestation, strfriendno, strEmail1, strEmail2, strEmail3,
				strPhoneno1, strPhoneno2, strPhoneno3, strLandline1,
				strLandline2, strLandline3, latitude, longitude);

			String Subject = "Reset Your Password";
		String EmailBody = "Hi \n We have received a request to generate a New password.\n Your new password is : "
				+ RandomPassword
				+ "\nPlease enter this password to login Safety application and start using the services from today itself.\nThank you for using Safety application.";

			EasyMail mail = new EasyMail(user, password); //Checking Authentication

			String UserEmail = AppTypeDetails.getInstance(activity).getEmail();

			String[] directionsToSend = {UserEmail};
			mail.setTo(directionsToSend);
			mail.setFrom(user);
			mail.setSubject(Subject);
			mail.setBody(EmailBody);

			try {
				if (mail.send()) {
					activity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(activity,"Thank You,An Email with your Password has been sent",Toast.LENGTH_LONG).show();
						}
					});
				} else {
					activity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(activity,"Thank You,An Email with your Password has been sent",Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Exception e) {
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(activity,"There was a problem sending the email.",Toast.LENGTH_LONG).show();
					}
				});
			}
	}
/**
 * Get All The Data from Shared Preferences editor
 */
	public void GetSharedPrefrenceData() {

		strFirstname = AppTypeDetails.getInstance(activity).getFirstName();
		strLastname = AppTypeDetails.getInstance(activity).getLastName();
		strEmailid = AppTypeDetails.getInstance(activity).getEmail();
		strUphoneno = AppTypeDetails.getInstance(activity).getPhone();
		strbloodgrp = AppTypeDetails.getInstance(activity).getBloodgroup();
		strallergies = AppTypeDetails.getInstance(activity).getAllergies();
		strdiabetic = AppTypeDetails.getInstance(activity).getDiabetic();
		strbldpressure = AppTypeDetails.getInstance(activity).getBloodPressure();
		strcancer = AppTypeDetails.getInstance(activity).getCancer();
		strpolice = AppTypeDetails.getInstance(activity).getpoliceno();
		strhospital = AppTypeDetails.getInstance(activity).getHospitalno();
		strfirestation = AppTypeDetails.getInstance(activity).getfireno();
		strfriendno = AppTypeDetails.getInstance(activity).getfriendno(); 

		strPhoneno1 = AppTypeDetails.getInstance(activity).getPhoneNumber1();
		strPhoneno2 = AppTypeDetails.getInstance(activity).getPhoneNumber2();
		strPhoneno3 = AppTypeDetails.getInstance(activity).getPhoneNumber3();

		strLandline1 = AppTypeDetails.getInstance(activity).getLandline1();
		strLandline2 = AppTypeDetails.getInstance(activity).getLandline2();
		strLandline3 = AppTypeDetails.getInstance(activity).getLandline3();

		latitude = AppTypeDetails.getInstance(activity).GetRegLatitude();
		longitude = AppTypeDetails.getInstance(activity).GetRegLongitude();
	}
}

