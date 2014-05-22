package com.iih.android.emergency;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emergency.email.EasyMail;
import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;

public class SpeechToTextActivity extends Activity implements OnClickListener {

	protected static final int RESULT_SPEECH = 1;

	private Button Homebutton;
	private TextView mTitle;
	private Button SpeakButton;

	private EditText mEmailId;
	private EditText mSubject;
	private EditText TextArea;
	
	private Button mSendBtn;
	private String Subject;

	private String EmergencyText;
	private String EmergencyTextfinal="";

	private String Email1;
	private String Email2;
	private String Email3;

	private String user = "test.intelithub@gmail.com";
	private String password = "intelithub@123";

	private String EmailList;
	private String EmailBody;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergencytap);

		initialization();

		Homebutton.setOnClickListener(this);
		SpeakButton.setOnClickListener(this);
		mSendBtn.setOnClickListener(this);

		//Speech to Text Speak Now on tap Button
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

		try {
			startActivityForResult(intent, RESULT_SPEECH);
//			TextArea.setText("");
		} catch (ActivityNotFoundException a) {
			Toast t = Toast.makeText(getApplicationContext(),"Ops! Your device doesn't support Speech to Text",Toast.LENGTH_SHORT);
			t.show();
		}
	}

	private void initialization() {
		// TODO Auto-generated method stub

		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("Emergency Help");

		Homebutton = (Button)findViewById(R.id.Home_Button);

		SpeakButton = (Button)findViewById(R.id.actionbar_button);
		SpeakButton.setBackgroundResource(R.drawable.speak_selector);

		mEmailId = (EditText)findViewById(R.id.Emailid);
		mSubject = (EditText)findViewById(R.id.subject);
		TextArea = (EditText)findViewById(R.id.textarea);

		mSendBtn = (Button)findViewById(R.id.btnSend);

		Subject = "I am in Emergency!!I need your Help!!";
		mSubject.setText(Subject);

		Email1 = AppTypeDetails.getInstance(SpeechToTextActivity.this).getEmailAddress1();
		Email2 = AppTypeDetails.getInstance(SpeechToTextActivity.this).getEmailAddress2();
		Email3 = AppTypeDetails.getInstance(SpeechToTextActivity.this).getEmailAddress3();

		if(CommonUtil.isEmpty(Email1) && CommonUtil.isEmpty(Email2) && CommonUtil.isEmpty(Email3) ){
			mEmailId.setText("");
		}else {
		EmailList  = Email1 + ","+ Email2 + ","+ Email3 + ",";
		mEmailId.setText(EmailList);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: 
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				EmergencyText =text.get(0);
				EmergencyTextfinal=EmergencyTextfinal+" "+EmergencyText;

				TextArea.setText(EmergencyTextfinal);
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {

		case R.id.Home_Button:
			startActivity(new Intent(SpeechToTextActivity.this,HomeActivity.class));
			finish();
			break;

		case R.id.actionbar_button:

			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

			try {
				startActivityForResult(intent, RESULT_SPEECH);
//				TextArea.setText("");
			} catch (ActivityNotFoundException a) {
				Toast t = Toast.makeText(getApplicationContext(),"Ops! Your device doesn't support Speech to Text",Toast.LENGTH_SHORT);
				t.show();
			}
			break;

		case R.id.btnSend:
			if (CommonUtil.checkConn(SpeechToTextActivity.this)) {
				new SpeechtoTextAsync(SpeechToTextActivity.this).execute();
			} else {
			CommonUtil.showAlert("Internet connection not found.", SpeechToTextActivity.this);
			}
			break;

		default:
			break;
		}
	}

	public void SendMail() {

		final EasyMail mail = new EasyMail(user, password); //Checking Authentication

		EmailBody = TextArea.getText().toString().trim();
	
		if(CommonUtil.isEmpty(EmailBody)){
			EmailBody = "Hello.\n\n I am in Trouble, Need your help as soon as possible please!!";
		}
		String Email = mEmailId.getText().toString().trim();

		if(!CommonUtil.isEmpty(Email)){

			String[] directionsToSend = {Email};
			mail.setTo(directionsToSend);
			mail.setFrom(user);
			mail.setSubject(Subject);
			mail.setBody(EmailBody);

			SpeechToTextActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
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
			});
		}
	}

	/**
	 * Async for sending email
	 * @author Jignesh Jain
	 *
	 */
		public class SpeechtoTextAsync extends AsyncTask<Void, Void, Void> {

			Context context;
			private ProgressDialog bar;

			public SpeechtoTextAsync() {
				// TODO Auto-generated constructor stub
			}

			public SpeechtoTextAsync(Context context, ListView listView) {
				// TODO Auto-generated constructor stub
				this.context = context;
			}

			public SpeechtoTextAsync(Context context) {
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
				return null;
			}
		}
}
