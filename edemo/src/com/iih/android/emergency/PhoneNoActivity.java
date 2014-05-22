package com.iih.android.emergency;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.location.commonUtil.XmlData;

public class PhoneNoActivity extends Activity  implements OnClickListener{

	private TextView mTitle;
	private Button mSaveButton;
	private Button HomeButton;

	private EditText mPhone1;
	private EditText mPhone2;
	private EditText mPhone3;

	private EditText mlandline1;
	private EditText mlandline2;
	private EditText mlandline3;

	private Boolean IsfinishAct =false;
	private Boolean IsDataSave = false;

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

	private Button mPlus1;
	private Button mPlus2;
	private Button mPlus3;
	private Button mPlus4;
	private Button mPlus5;
	private Button mPlus6;

	private static final int PICK_CONTACT1 = 1;
	private static final int PICK_CONTACT2 = 2;
	private static final int PICK_CONTACT3 = 3;
	private static final int PICK_CONTACT4 = 4;
	private static final int PICK_CONTACT5 = 5;
	private static final int PICK_CONTACT6 = 6;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergencyphoneno);

		initialization();

		mSaveButton.setOnClickListener(this);
		mPlus1.setOnClickListener(this);
		mPlus2.setOnClickListener(this);
		mPlus3.setOnClickListener(this);
		mPlus4.setOnClickListener(this);
		mPlus5.setOnClickListener(this);
		mPlus6.setOnClickListener(this);
	}

	public void initialization() {
		// TODO Auto-generated method stub
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("Phone Numbers");

		mSaveButton = (Button)findViewById(R.id.actionbar_button);
		mSaveButton.setBackgroundResource(R.drawable.save_selector);

		HomeButton = (Button)findViewById(R.id.Home_Button);
		HomeButton.setVisibility(View.INVISIBLE);

		mPhone1 = (EditText)findViewById(R.id.Phoneno1);
		mPhone2 = (EditText)findViewById(R.id.Phoneno2);
		mPhone3 = (EditText)findViewById(R.id.Phoneno3);

		mlandline1 = (EditText)findViewById(R.id.Landline1);
		mlandline2 = (EditText)findViewById(R.id.Landline2);
		mlandline3 = (EditText)findViewById(R.id.Landline3);

		mPlus1 = (Button)findViewById(R.id.PlusIcon1);
		mPlus2 = (Button)findViewById(R.id.PlusIcon2);
		mPlus3 = (Button)findViewById(R.id.PlusIcon3);
		mPlus4 = (Button)findViewById(R.id.PlusIcon4);
		mPlus5 = (Button)findViewById(R.id.PlusIcon5);
		mPlus6 = (Button)findViewById(R.id.PlusIcon6);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.actionbar_button:
			SavePhoneNumbers();
			break;

		case R.id.PlusIcon1:
			intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT1);
			break;

		case R.id.PlusIcon2:
			intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT2);
			break;

		case R.id.PlusIcon3:
			intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT3);
			break;

		case R.id.PlusIcon4:
			intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT4);
			break;

		case R.id.PlusIcon5:
			intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT5);
			break;

		case R.id.PlusIcon6:
			intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT6);
			break;

		default:
			break;
		}
	}

	public void SavePhoneNumbers() {
		// TODO Auto-generated method stub

		strPhoneno1 = mPhone1.getText().toString().trim();
		strPhoneno2 = mPhone2.getText().toString().trim();
		strPhoneno3 = mPhone3.getText().toString().trim();

		strLandline1 = mlandline1.getText().toString().trim();
		strLandline2 = mlandline2.getText().toString().trim();
		strLandline3 = mlandline3.getText().toString().trim();

		if (CommonUtil.isEmpty(strPhoneno1) && CommonUtil.isEmpty(strPhoneno2) && CommonUtil.isEmpty(strPhoneno3)
				&& CommonUtil.isEmpty(strLandline1) && CommonUtil.isEmpty(strLandline2) && CommonUtil.isEmpty(strLandline3)) {
			finish();
		} else {
			if(!CommonUtil.isEmpty(strPhoneno1)){
				IsfinishAct=false;
				checkPhoneno(strPhoneno1);
			}
			if(!CommonUtil.isEmpty(strPhoneno2)){
				IsfinishAct=false;
				checkPhoneno(strPhoneno2);
			}
			if(!CommonUtil.isEmpty(strPhoneno3)){
				IsfinishAct=false;
				checkPhoneno(strPhoneno3);
			}
			if (!CommonUtil.isEmpty(strLandline1)) {
				IsfinishAct = true;
			}
			if (!CommonUtil.isEmpty(strLandline2)) {
				IsfinishAct = true;
			}
			if (!CommonUtil.isEmpty(strLandline3)) {
				IsfinishAct = true;
			}

			if(IsfinishAct){
				IsDataSave = true;
				setSharedPrefrences();
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

	public void setSharedPrefrences() {
		// TODO Auto-generated method stub
		AppTypeDetails.getInstance(PhoneNoActivity.this).setPhoneNumber1(strPhoneno1);
		AppTypeDetails.getInstance(PhoneNoActivity.this).setPhoneNumber2(strPhoneno2);
		AppTypeDetails.getInstance(PhoneNoActivity.this).setPhoneNumber3(strPhoneno3);

		AppTypeDetails.getInstance(PhoneNoActivity.this).setLandline1(strLandline1);
		AppTypeDetails.getInstance(PhoneNoActivity.this).setLandline2(strLandline2);
		AppTypeDetails.getInstance(PhoneNoActivity.this).setLandline3(strLandline3);
	}

	/**
	 * Get All The Data from Shared Preferences editor
	 */
	public void GetSharedPrefrenceData() {

		strstatus = AppTypeDetails.getInstance(PhoneNoActivity.this).Getstatus();
		strFirstname = AppTypeDetails.getInstance(PhoneNoActivity.this).getFirstName();
		strLastname = AppTypeDetails.getInstance(PhoneNoActivity.this).getLastName();
		strEmailid = AppTypeDetails.getInstance(PhoneNoActivity.this).getEmail();
		strPassword = AppTypeDetails.getInstance(PhoneNoActivity.this).getpassowrd();
		strUphoneno = AppTypeDetails.getInstance(PhoneNoActivity.this).getPhone();
		strbloodgrp = AppTypeDetails.getInstance(PhoneNoActivity.this).getBloodgroup();
		strallergies = AppTypeDetails.getInstance(PhoneNoActivity.this).getAllergies();
		strdiabetic = AppTypeDetails.getInstance(PhoneNoActivity.this).getDiabetic();
		strbldpressure = AppTypeDetails.getInstance(PhoneNoActivity.this).getBloodPressure();
		strcancer = AppTypeDetails.getInstance(PhoneNoActivity.this).getCancer();
		strpolice = AppTypeDetails.getInstance(PhoneNoActivity.this).getpoliceno();
		strhospital = AppTypeDetails.getInstance(PhoneNoActivity.this).getHospitalno();
		strfirestation = AppTypeDetails.getInstance(PhoneNoActivity.this).getfireno();
		strfriendno = AppTypeDetails.getInstance(PhoneNoActivity.this).getfriendno(); 

		strEmail1 = AppTypeDetails.getInstance(PhoneNoActivity.this).getEmailAddress1();
		strEmail2 = AppTypeDetails.getInstance(PhoneNoActivity.this).getEmailAddress2();
		strEmail3 = AppTypeDetails.getInstance(PhoneNoActivity.this).getEmailAddress3();

		latitude = AppTypeDetails.getInstance(PhoneNoActivity.this).GetRegLatitude();
		longitude = AppTypeDetails.getInstance(PhoneNoActivity.this).GetRegLongitude();

	}
	private void checkPhoneno(String PhoneNumber) {
		// TODO Auto-generated method stub
		if(CommonUtil.isValidPhoneNumber(PhoneNumber)){
			CommonUtil.showAlert("Enter valid phone number", PhoneNoActivity.this);
		}else{
			IsfinishAct=true;
		}
	}

	/**
	 * Open a contact List and make a call 
	 */
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT1):
			if (resultCode == Activity.RESULT_OK) {

				ContentResolver cr = getContentResolver();

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					// other data is available for the Contact. I have decided
					// to only get the name of the Contact.
					String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

					if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = ?", new String[] { id },null);

						while (pCur.moveToNext()) {
							String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							mPhone1.setText(phone); //Setting phone no from Contact List to Edittext
						}
					}
				}
			}
		break;

		case (PICK_CONTACT2):
			if (resultCode == Activity.RESULT_OK) {

				ContentResolver cr = getContentResolver();

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					// other data is available for the Contact. I have decided
					// to only get the name of the Contact.
					String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

					if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = ?", new String[] { id },null);

						while (pCur.moveToNext()) {
							String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							mPhone2.setText(phone);//Setting phone no from Contact List to Edittext
						}
					}
				}
			}
		break;

		case (PICK_CONTACT3):
			if (resultCode == Activity.RESULT_OK) {

				ContentResolver cr = getContentResolver();

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					// other data is available for the Contact. I have decided
					// to only get the name of the Contact.
					String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

					if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = ?", new String[] { id },null);

						while (pCur.moveToNext()) {
							String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							mPhone3.setText(phone);//Setting phone no from Contact List to Edittext
						}
					}
				}
			}
		break;

		case (PICK_CONTACT4):
			if (resultCode == Activity.RESULT_OK) {

				ContentResolver cr = getContentResolver();

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					// other data is available for the Contact. I have decided
					// to only get the name of the Contact.
					String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

					if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = ?", new String[] { id },null);

						while (pCur.moveToNext()) {
							String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							mlandline2.setText(phone); //Setting phone no from Contact List to Edittext
						}
					}
				}
			}
		break;

		case (PICK_CONTACT5):
			if (resultCode == Activity.RESULT_OK) {

				ContentResolver cr = getContentResolver();

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					// other data is available for the Contact. I have decided
					// to only get the name of the Contact.
					String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

					if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = ?", new String[] { id },null);

						while (pCur.moveToNext()) {
							String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							mlandline2.setText(phone); //Setting Landline phone no from Contact List to Edittext
							
						}
					}
				}
			}
		break;

		case (PICK_CONTACT6):
			if (resultCode == Activity.RESULT_OK) {

				ContentResolver cr = getContentResolver();

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					// other data is available for the Contact. I have decided
					// to only get the name of the Contact.
					String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

					if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = ?", new String[] { id },null);

						while (pCur.moveToNext()) {
							String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							mlandline3.setText(phone); //Setting landline no from Contact List to Edittext
						} 
					}
				}
			}
		break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		strPhoneno1 = mPhone1.getText().toString().trim();
		strPhoneno2 = mPhone2.getText().toString().trim();
		strPhoneno3 = mPhone3.getText().toString().trim();

		strLandline1 = mlandline1.getText().toString().trim();
		strLandline2 = mlandline2.getText().toString().trim();
		strLandline3 = mlandline3.getText().toString().trim();

		if (CommonUtil.isEmpty(strPhoneno1) && CommonUtil.isEmpty(strPhoneno2) && CommonUtil.isEmpty(strPhoneno3)
				&& CommonUtil.isEmpty(strLandline1) && CommonUtil.isEmpty(strLandline2) && CommonUtil.isEmpty(strLandline3)) {
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