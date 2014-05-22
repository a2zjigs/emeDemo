package com.iih.android.emergency;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.emergency.parsing.ParsingClass;
import com.iih.location.commonUtil.AppTypeDetails;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		new ParseData(this).execute();
	}

	/**
	 * parse All the data in XML File
	 */
	public void bindDataToListing() {
		try {
			SAXParserFactory saxparser = SAXParserFactory.newInstance();
			SAXParser parser = saxparser.newSAXParser();
			XMLReader xmlReader = parser.getXMLReader();
			ParsingClass pc = new ParsingClass();
			xmlReader.setContentHandler(pc);

			InputStream is = new FileInputStream(Environment.getExternalStorageDirectory() + "/db.xml");
			xmlReader.parse(new InputSource(is));

			AppTypeDetails.getInstance(SplashScreen.this).Setstatus(pc.status);
			AppTypeDetails.getInstance(SplashScreen.this).setFirstName(pc.strFname);
			AppTypeDetails.getInstance(SplashScreen.this).setLastName(pc.strLname);
			AppTypeDetails.getInstance(SplashScreen.this).setEmail(pc.strUemail);
			AppTypeDetails.getInstance(SplashScreen.this).setpassword(pc.strUpass);
			AppTypeDetails.getInstance(SplashScreen.this).setPhone(pc.strUphoneNo);
			AppTypeDetails.getInstance(SplashScreen.this).setBloodgroup(pc.strbloodgrp);
			AppTypeDetails.getInstance(SplashScreen.this).setAllergies(pc.strallergies);
			AppTypeDetails.getInstance(SplashScreen.this).setDiabetic(pc.strdiabetic);
			AppTypeDetails.getInstance(SplashScreen.this).setBloodPressure(pc.strbldpressure);
			AppTypeDetails.getInstance(SplashScreen.this).setcancer(pc.strcancer);
			AppTypeDetails.getInstance(SplashScreen.this).setpoliceno(pc.strPoliceNo);
			AppTypeDetails.getInstance(SplashScreen.this).setHospitalno(pc.strHospitalNo);
			AppTypeDetails.getInstance(SplashScreen.this).setfireno(pc.strFireStNo);
			AppTypeDetails.getInstance(SplashScreen.this).setfriendno(pc.strIceno);

			AppTypeDetails.getInstance(SplashScreen.this).setEmailAddress1(pc.strEmail1);
			AppTypeDetails.getInstance(SplashScreen.this).setEmailAddress2(pc.strEmail2);
			AppTypeDetails.getInstance(SplashScreen.this).setEmailAddress3(pc.strEmail3);

			AppTypeDetails.getInstance(SplashScreen.this).setPhoneNumber1(pc.strPhoneNo1);
			AppTypeDetails.getInstance(SplashScreen.this).setPhoneNumber2(pc.strPhoneNo2);
			AppTypeDetails.getInstance(SplashScreen.this).setPhoneNumber3(pc.strPhoneNo3);

			AppTypeDetails.getInstance(SplashScreen.this).setPhoneNumber1(pc.strLandline1);
			AppTypeDetails.getInstance(SplashScreen.this).setPhoneNumber2(pc.strLandline2);
			AppTypeDetails.getInstance(SplashScreen.this).setPhoneNumber3(pc.strLandline3);

			AppTypeDetails.getInstance(SplashScreen.this).SetRegLatitude(pc.latitude);
			AppTypeDetails.getInstance(SplashScreen.this).SetRegLongitude(pc.longitude);

		} catch (Exception e) {
			e.getMessage();

		}
	}

	public class ParseData extends AsyncTask<Void, Void, Void> {
		Context context;


		public ParseData(Context context) {
			this.context = context;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			try {
				mHandler.sendEmptyMessageDelayed(0,3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			bindDataToListing();
			return null;
		}

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String strstatus = AppTypeDetails.getInstance(SplashScreen.this).Getstatus();

			if(strstatus.equalsIgnoreCase("")){
			startActivity(new Intent(SplashScreen.this, LoginActivity.class));
			finish();
			}else{
				Intent intent =new Intent(SplashScreen.this,HomeActivity.class);
				startActivity(intent);
				finish();
			}
		};
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
	}
}
