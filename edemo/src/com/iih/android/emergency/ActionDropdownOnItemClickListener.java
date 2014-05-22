package com.iih.android.emergency;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.XmlData;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnInviteListener;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;
import com.sromku.simple.fb.SimpleFacebook.OnLogoutListener;

public class ActionDropdownOnItemClickListener implements OnItemClickListener {

	protected static final String TAG = HomeActivity.class.getName();

	/*private SimpleFacebook mSimpleFacebook;*/

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

	private String latitude = "";
	private String longitude = "";

	HomeActivity mainActivity;

	private static final int PICK_CONTACT = 1;

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

		// get the context and main activity to access variables
		Context mContext = v.getContext();
		mainActivity = ((HomeActivity) mContext);

		// add some animation when a list item was clicked
		Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
		fadeInAnimation.setDuration(10);
		v.startAnimation(fadeInAnimation);

		// dismiss the pop up
		mainActivity.popupWindowDogs.dismiss();

		// get the text and set it as the button text
		//String selectedItemText = ((TextView) v).getText().toString();
		//mainActivity.buttonShowDropDown.setText(selectedItemText);

	/*	// get the id
		String selectedItemTag = ((TextView) v).getTag().toString();
		Toast.makeText(mContext, "Dog ID is: " + selectedItemTag,Toast.LENGTH_SHORT).show();*/

		switch (position) {
		case 0:
			mainActivity.startActivity(new Intent(mainActivity,MapViewActivity.class));
			break;

		case 1:
			Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			mainActivity.startActivityForResult(intent, PICK_CONTACT);
			break;

		case 2:
			//mainActivity.startActivity(new Intent(mainActivity,InviteFriends.class));
			HomeActivity.mSimpleFacebook.login(mOnLoginListener);
			HomeActivity.mSimpleFacebook.invite("I invite you to use this app", onInviteListener);
			break;

		case 3:
			SettingPasswordActivity Dialog = new SettingPasswordActivity(mainActivity);
			Dialog.show();
			break;

		case 4:
			HomeActivity.mSimpleFacebook.logout(mOnLogoutListener);
			break;
		default:
			break;
		}
	}

	private void Logout() {

		strstatus = AppTypeDetails.getInstance(mainActivity).Getstatus();

		/**
		 * Signout from Facebook
		 */
		if(strstatus.equalsIgnoreCase("fblogin")){

				GetSharedPrefrenceData();

				AppTypeDetails.getInstance(mainActivity).Setstatus("");

				XmlData.CreateXml("", strFirstname, strLastname, strEmailid,
						strPassword, strUphoneno, strbloodgrp, strallergies,
						strdiabetic, strbldpressure, strcancer, strpolice,
						strhospital, strfirestation, strfriendno, strEmail1,
						strEmail2, strEmail3, strPhoneno1, strPhoneno2,
						strPhoneno3, strLandline1, strLandline2, strLandline3,
						latitude, longitude);

				Intent intent1 = new Intent(mainActivity,LoginActivity.class);
				mainActivity.startActivity(intent1);
				mainActivity.finish();
		}


		/**
		 * Sign out from Application
		 */
		if(strstatus.equalsIgnoreCase("login")){

		GetSharedPrefrenceData();

		AppTypeDetails.getInstance(mainActivity).Setstatus("");
		/**
		 * Updated Status Value Null in XML file.
		 */

		XmlData.CreateXml("", strFirstname, strLastname, strEmailid,
				strPassword, strUphoneno, strbloodgrp, strallergies,
				strdiabetic, strbldpressure, strcancer, strpolice,
				strhospital, strfirestation, strfriendno, strEmail1,
				strEmail2, strEmail3, strPhoneno1, strPhoneno2,
				strPhoneno3, strLandline1, strLandline2, strLandline3,
				latitude, longitude);

		Intent intent2 = new Intent(mainActivity,LoginActivity.class);
		intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mainActivity.startActivity(intent2);
		mainActivity.finish();
		}
	}
	/**
	 * Get All The Data from Shared Preferences editor
	 */
	public void GetSharedPrefrenceData() {

		strFirstname = AppTypeDetails.getInstance(mainActivity).getFirstName();
		strLastname = AppTypeDetails.getInstance(mainActivity).getLastName();
		strEmailid = AppTypeDetails.getInstance(mainActivity).getEmail();
		strPassword = AppTypeDetails.getInstance(mainActivity).getpassowrd();
		strUphoneno = AppTypeDetails.getInstance(mainActivity).getPhone();
		strbloodgrp = AppTypeDetails.getInstance(mainActivity).getBloodgroup();
		strallergies = AppTypeDetails.getInstance(mainActivity).getAllergies();
		strdiabetic = AppTypeDetails.getInstance(mainActivity).getDiabetic();
		strbldpressure = AppTypeDetails.getInstance(mainActivity).getBloodPressure();
		strcancer = AppTypeDetails.getInstance(mainActivity).getCancer();
		strpolice = AppTypeDetails.getInstance(mainActivity).getpoliceno();
		strhospital = AppTypeDetails.getInstance(mainActivity).getHospitalno();
		strfirestation = AppTypeDetails.getInstance(mainActivity).getfireno();
		strfriendno = AppTypeDetails.getInstance(mainActivity).getfriendno(); 

		strEmail1 = AppTypeDetails.getInstance(mainActivity).getEmailAddress1();
		strEmail2 = AppTypeDetails.getInstance(mainActivity).getEmailAddress2();
		strEmail3 = AppTypeDetails.getInstance(mainActivity).getEmailAddress3();

		strPhoneno1 = AppTypeDetails.getInstance(mainActivity).getPhoneNumber1();
		strPhoneno2 = AppTypeDetails.getInstance(mainActivity).getPhoneNumber2();
		strPhoneno3 = AppTypeDetails.getInstance(mainActivity).getPhoneNumber3();

		strLandline1 = AppTypeDetails.getInstance(mainActivity).getLandline1();
		strLandline2 = AppTypeDetails.getInstance(mainActivity).getLandline2();
		strLandline3 = AppTypeDetails.getInstance(mainActivity).getLandline3();

		latitude = AppTypeDetails.getInstance(mainActivity).GetRegLatitude();
		longitude = AppTypeDetails.getInstance(mainActivity).GetRegLongitude();
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
			HomeActivity.mSimpleFacebook.invite("I invite you to use this app", onInviteListener);
		}

		@Override
		public void onNotAcceptingPermissions()
		{
			Toast.makeText( mainActivity, "You didn't accept read permissions", Toast.LENGTH_LONG).show();
		}
	};
	// Logout listener
				public OnLogoutListener mOnLogoutListener = new OnLogoutListener()
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
					public void onLogout()
					{
						// change the state of the button or do whatever you want
						Logout();
						Toast.makeText(mainActivity, "You are logged out", Toast.LENGTH_LONG).show();
					}
				};

				//Invite Friends listener
				public OnInviteListener onInviteListener = new SimpleFacebook.OnInviteListener()
				{

					@Override
					public void onFail(String reason)
					{
						// insure that you are logged in before inviting
						Log.w(TAG, reason);
					}

					@Override
					public void onException(Throwable throwable)
					{
						Log.e(TAG, "Bad thing happened", throwable);
					}

					@Override
					public void onComplete(List<String> invitedFriends)
					{
						Toast.makeText(mainActivity, "Invitation was sent to " + invitedFriends.size() + " Friends", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onCancel()
					{
						Toast.makeText(mainActivity,"Cancelled the dialog", Toast.LENGTH_LONG).show();
					}
				};
}
