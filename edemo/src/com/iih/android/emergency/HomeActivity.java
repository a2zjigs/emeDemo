package com.iih.android.emergency;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.emergency.email.EasyMail;
import com.iih.arview.ARView;
import com.iih.arview.DataView;
import com.iih.broadcastreceiver.DeliverSMSBroadcastReceiver;
import com.iih.broadcastreceiver.OutgoingSMSBroadcastReceiver;
import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.sromku.simple.fb.SimpleFacebook;

public class HomeActivity extends Activity implements OnClickListener {

	public static SimpleFacebook mSimpleFacebook;

	protected static final String TAG = LoginActivity.class.getName();

	private Button mMenubutton;
	private Button HomeButton;
	private TextView mTitle;

	private Button mPoliceAR;
	private Button mHospitalAR;
	private Button mFireAR;

	private Button policeTap;
	private Button HospitalTap;
	private Button FireTap;
	private Button IcsTap;

	private Button mPoliceCall;
	private Button mHospitalCall;
	private Button mFireCall;
	private Button mFriendCall;

	private Intent callIntent;

	private ImageView TapButton;

	private SmsManager manager;
	public static final int  MAX_MESSAGE_SIZE = 160;
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver outgoingSMSBR = new OutgoingSMSBroadcastReceiver();
	private final BroadcastReceiver deliverSMSBR = new DeliverSMSBroadcastReceiver();

	private String user = "test.intelithub@gmail.com";
	private String password = "intelithub@123";

	private String[] latlongs;
	private String latitude;
	private String longitude;

	DataView dataview;

	String popUpContents[];
	PopupWindow popupWindowDogs;
	Button buttonShowDropDown;

	private String Address;
	private String EmailList;

	private String EmgncyPoliceCall;
	private String EmgncyHospitalCall;
	private String EmgncyFireCall;
	private String EmgncyICSCall;

	private static final int PICK_CONTACT = 1;

	private EasyMail mail;
	private String EmailBody = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}

		initialization();

		//start animation on tap button
		final AnimationDrawable myAnimationDrawable = (AnimationDrawable) TapButton.getDrawable();

		TapButton.post(new Runnable() {

			@Override
			public void run() {
				myAnimationDrawable.start();
			}
		});
		/*
		 * initialize pop up window items list
		 */

		// add items on the array dynamically
		List<String> menulist = new ArrayList<String>();
		menulist.add("Location::1");
		menulist.add("Contact::2");
		menulist.add("Invite Friends::3");
		menulist.add("Settings::4");
		menulist.add("Signout::5");

		// convert to simple array
		popUpContents = new String[menulist.size()];
		menulist.toArray(popUpContents);

		/*
		 * initialize pop up window
		 */
		popupWindowDogs = popupWindowDogs();

		mMenubutton.setOnClickListener(this);

		mPoliceAR.setOnClickListener(this);
		mHospitalAR.setOnClickListener(this);
		mFireAR.setOnClickListener(this);

		policeTap.setOnClickListener(this);
		HospitalTap.setOnClickListener(this);
		FireTap.setOnClickListener(this);
		IcsTap.setOnClickListener(this);

		mPoliceCall.setOnClickListener(this);
		mHospitalCall.setOnClickListener(this);
		mFireCall.setOnClickListener(this);
		mFriendCall.setOnClickListener(this);

		TapButton.setOnClickListener(this);
	}

	/**
	 * Initialize all the Layout
	 */
	private void initialization() {

		mTitle = (TextView)findViewById(R.id.title);
		mTitle.setText("Home");

		mMenubutton = (Button) findViewById(R.id.actionbar_button);
		mMenubutton.setBackgroundResource(R.drawable.action_menu_icon_selector);

		HomeButton = (Button)findViewById(R.id.Home_Button);
		HomeButton.setVisibility(View.GONE);

		mPoliceAR = (Button) findViewById(R.id.pNearby);
		mHospitalAR = (Button) findViewById(R.id.HNearby);
		mFireAR = (Button) findViewById(R.id.fNearby);

		policeTap = (Button)findViewById(R.id.police);
		HospitalTap = (Button)findViewById(R.id.hospital);
		FireTap = (Button)findViewById(R.id.Firestation);
		IcsTap = (Button)findViewById(R.id.icsno);

		mPoliceCall = (Button)findViewById(R.id.pCall);
		mHospitalCall = (Button)findViewById(R.id.HCall);
		mFireCall = (Button)findViewById(R.id.fCall);
		mFriendCall = (Button)findViewById(R.id.frCall);

		TapButton = (ImageView)findViewById(R.id.tapbutton);

		String Email1 = AppTypeDetails.getInstance(HomeActivity.this).getEmailAddress1();
		String Email2 = AppTypeDetails.getInstance(HomeActivity.this).getEmailAddress2();
		String Email3 = AppTypeDetails.getInstance(HomeActivity.this).getEmailAddress3();

		EmailList = Email1 +"," +Email2 +","+Email3;
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if(CommonUtil.isBackPress == true){
			try {
				//String str = myintent.getExtras().getString("onback");
				//if(str.equalsIgnoreCase("yes"))
				//{
				CommonUtil.isBackPress=false;
				finish();
			//	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {

		case R.id.actionbar_button:
			//SettingPasswordActivity Dialog = new SettingPasswordActivity(HomeActivity.this);
			//Dialog.show();
			popupWindowDogs.showAsDropDown(v, -5, 0);
			// show the list view as dropdown
			// buttonShowDropDown.setBackgroundResource(R.drawable.ic_launcher);
			// buttonShowDropDown.setText("Select your dog...");
			// popupWindowDogs.showAsDropDown(v);
			break;
		
		case R.id.tapbutton:
			Intent intent = new Intent(HomeActivity.this,SpeechToTextActivity.class);
			startActivity(intent);
			break;

		case R.id.pNearby:
			if (CommonUtil.checkConn(HomeActivity.this)) {
			
			makeToast();
			AppTypeDetails.getInstance(HomeActivity.this).setArType("police");
			startActivity(new Intent(HomeActivity.this, ARView.class));
			}else{
				CommonUtil.showAlert("Internet connection not found.", HomeActivity.this);
			}
			break;

		case R.id.HNearby:
			if (CommonUtil.checkConn(HomeActivity.this)) {
			makeToast();
			AppTypeDetails.getInstance(HomeActivity.this).setArType("hospital");
			startActivity(new Intent(HomeActivity.this, ARView.class));
			}else{
				CommonUtil.showAlert("Internet connection not found.", HomeActivity.this);
			}
			break;

		case R.id.fNearby:
			if (CommonUtil.checkConn(HomeActivity.this)) {
			makeToast();
			AppTypeDetails.getInstance(HomeActivity.this).setArType("fire_station");
			startActivity(new Intent(HomeActivity.this, ARView.class));
			}else{
				CommonUtil.showAlert("Internet connection not found.", HomeActivity.this);
			}
			break;

		case R.id.police:
			manager =  SmsManager.getDefault();

			if (CommonUtil.checkConn(HomeActivity.this)) {
				new homeAsync(HomeActivity.this).execute();  
			} else {
				CommonUtil.showAlert("Internet connection not found.", HomeActivity.this);
			}

			break;

		case R.id.hospital:
			manager =  SmsManager.getDefault();
			if (CommonUtil.checkConn(HomeActivity.this)) {
				new homeAsync(HomeActivity.this).execute();
			} else {
				CommonUtil.showAlert("Internet connection not found.", HomeActivity.this);
			}
			break;

		case R.id.Firestation:
			manager =  SmsManager.getDefault();
			if (CommonUtil.checkConn(HomeActivity.this)) {
				new homeAsync(HomeActivity.this).execute();
			} else {
				CommonUtil.showAlert("Internet connection not found.", HomeActivity.this);
			}
			break;

		case R.id.icsno:
			manager =  SmsManager.getDefault();
			if (CommonUtil.checkConn(HomeActivity.this)) {
				new homeAsync(HomeActivity.this).execute();
			} else {
			CommonUtil.showAlert("Internet connection not found.", HomeActivity.this);
			}
			break;

		case R.id.pCall:

			EmgncyPoliceCall = AppTypeDetails.getInstance(HomeActivity.this).getpoliceno();
			callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+EmgncyPoliceCall));
			startActivity(callIntent);
			break;

		case R.id.HCall:

			EmgncyHospitalCall = AppTypeDetails.getInstance(HomeActivity.this).getHospitalno();
			callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+EmgncyHospitalCall));
			startActivity(callIntent);
			
			break;

		case R.id.fCall:

			EmgncyFireCall = AppTypeDetails.getInstance(HomeActivity.this).getfireno();
			callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+EmgncyFireCall));
			startActivity(callIntent);
			break;

		case R.id.frCall:

			EmgncyICSCall = AppTypeDetails.getInstance(HomeActivity.this).getfriendno();
			callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+EmgncyICSCall));
			startActivity(callIntent);
			break;
		default:
			break;
		}
	}

	/**
	 * Open a contact List and make a call 
	 */
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {

		mSimpleFacebook.onActivityResult(this, reqCode, resultCode, data);
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {

				//ContentResolver cr = getContentResolver();

				Uri contactData = data.getData();

				Cursor c = this.getContentResolver().query(contactData, null, null, null, null);

				if (c.moveToFirst()) {
					// other data is available for the Contact. I have decided
					// to only get the name of the Contact.
					String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

					if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = getContentResolver().query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = ?", new String[] { id },null);

						while (pCur.moveToNext()) {
							String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

							callIntent = new Intent(Intent.ACTION_CALL);
							callIntent.setData(Uri.parse("tel:"+phone));
							startActivity(callIntent);
						}
					}
				}
			}
		break;
		}
	}

	public PopupWindow popupWindowDogs() {

		// initialize a pop up window type
		PopupWindow popupWindow = new PopupWindow(this);

		// the drop down list is a list view
		ListView listViewDogs = new ListView(this);

		// set our adapter and pass our pop up window contents`
		listViewDogs.setAdapter(MenulistAdapter(popUpContents));

		// set the item click listener
		listViewDogs.setOnItemClickListener(new ActionDropdownOnItemClickListener());

		// some other visual settings
		popupWindow.setFocusable(true);
		popupWindow.setWidth(250);
		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

		// set the list view as pop up window content
		popupWindow.setContentView(listViewDogs);

		return popupWindow;
	}
	/*
	 * adapter where the list values will be set
	 */
	private ArrayAdapter<String> MenulistAdapter(String menuArray[]) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuArray) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// setting the ID and text for every items in the list
				String item = getItem(position);
				String[] itemArr = item.split("::");
				String text = itemArr[0];
				String id = itemArr[1];

				// visual settings for the list item
				TextView listItem = new TextView(HomeActivity.this);

				listItem.setText(text);
				listItem.setTag(id);
				listItem.setBackgroundColor(Color.WHITE);
				listItem.setTextSize(22);
				listItem.setPadding(10, 10, 10, 10);
				listItem.setTextColor(Color.BLACK);

				return listItem;
			}
		};
		return adapter;
	}

	/**
	 * Method is used to send emergency Message on Emergency Email.
	 */

	public void SendMail() {

		/**
		 * getting Current Latitude and Longitude
		 */
		
		HomeActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				String path = Environment.getExternalStorageDirectory().toString();

				String ImageName = AppTypeDetails.getInstance(HomeActivity.this).GetImagePath();
				String mapImagename = AppTypeDetails.getInstance(HomeActivity.this).GetMapImage();

				final EasyMail mail = new EasyMail(user, password); //Checking Authentication

				String imagPath = path + "/safty/" + ImageName + ".jpg";

				String mappath = path + "/safty/" + mapImagename + ".png";

				String Subject = "Emergency!! Help Require!!";

				latlongs = CommonUtil.getLatAndLong(HomeActivity.this);

				latitude = latlongs[0];
				longitude = latlongs[1];

				double lat = Double.parseDouble(latitude);
				double longt = Double.parseDouble(longitude);

				//Getting Address from DataView class.
				Address = CommonUtil.getAddress(HomeActivity.this,lat, longt);

				EmailBody = "Hello.\n\n I am in Trouble, Need your help as soon as possible please!!"
						+ "\n Currently, I am at " + Address
						+ "\nI request you to get me out of this problem as quick as you can. I am in Really need of your help.";

				
				/**
				 * Checking Image is captured or not.If it will be Captured then attach in mail. 
				 */
				if(!CommonUtil.isEmpty(ImageName)){
				try {
					mail.addAttachment(imagPath);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				if(!CommonUtil.isEmpty(mapImagename)){
					try {
						mail.addAttachment(mappath);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


			if(!CommonUtil.isEmpty(EmailList)){

				String[] directionsToSend = {EmailList};
				mail.setTo(directionsToSend);
				mail.setFrom(user);
				mail.setSubject(Subject);
				mail.setBody(EmailBody);

						try {
							if (mail.send()) {
								Toast.makeText(getApplicationContext(),"Email was sent successfully.",Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(getApplicationContext(),"Email was not sent.", Toast.LENGTH_LONG).show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),"There was a problem sending the email.",Toast.LENGTH_LONG).show();
						}
					}
			}
		});
	}

/**
 * Method is used to send emergency Message on Emergency Phone number
 */
	public void sendMessage(){

		String phoneNumber1 = AppTypeDetails.getInstance(HomeActivity.this).getPhoneNumber1();
		String phoneNumber2 = AppTypeDetails.getInstance(HomeActivity.this).getPhoneNumber2();
		String phoneNumber3 = AppTypeDetails.getInstance(HomeActivity.this).getPhoneNumber3();

		String message = "Emergency!! Help Require!! I am in Trouble, Need your help as soon as possible please!! ";

		if(!isNullOrEmpty(phoneNumber1) && !isNullOrEmpty(message)){

				PendingIntent piSend1 = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT),0);
				PendingIntent piDelivered1 = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
				manager.sendTextMessage(phoneNumber1, null, message, piSend1, piDelivered1);
			}
		if(!isNullOrEmpty(phoneNumber2) && !isNullOrEmpty(message)){

			PendingIntent piSend2 = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT),0);
			PendingIntent piDelivered2 = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

			manager.sendTextMessage(phoneNumber2, null, message, piSend2, piDelivered2);
		}

		if(!isNullOrEmpty(phoneNumber3) && !isNullOrEmpty(message)){

			PendingIntent piSend3 = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT),0);
			PendingIntent piDelivered3 = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

			manager.sendTextMessage(phoneNumber2, null, message, piSend3, piDelivered3);
		}
	}

	//Custom Toast Message
		private void makeToast() {

			String Toastmessage = "Loading....";
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_layout));
			((TextView) layout.findViewById(R.id.toast_text_1)).setText(Toastmessage);
			Toast toast = new Toast(getBaseContext());
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
			toast.show();
		}

	@Override
	protected void onResume() {
		registerReceiver(outgoingSMSBR, new IntentFilter(SMS_SENT));

		registerReceiver(deliverSMSBR, new IntentFilter(SMS_DELIVERED));
		super.onResume();
	
		mSimpleFacebook = SimpleFacebook.getInstance(this);
	}

	@Override
	protected void onPause() {
		unregisterReceiver(outgoingSMSBR);
		unregisterReceiver(deliverSMSBR);
		super.onPause();
	}

	private boolean isNullOrEmpty(String string){
		return string == null || string.isEmpty();
	}
/**
 * Async for sending email and message
 * @author Jignesh Jain
 *
 */
	public class homeAsync extends AsyncTask<Void, Void, Void> {

		Context context;
		private ProgressDialog bar;

		public homeAsync() {
			// TODO Auto-generated constructor stub
		}

		public homeAsync(Context context, ListView listView) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		public homeAsync(Context context) {
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

			SendMail();
			//sendMessage();
			return null;
		}

	}
}
