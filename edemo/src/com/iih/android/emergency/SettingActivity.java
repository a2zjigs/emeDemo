package com.iih.android.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends Activity implements OnClickListener {

	private Button mActionbutton;
	private TextView mTitle;
	private Button HomeButton;

	private Button mProfilebtn;
	private Button mAddmoreContact;
	private Button mAddmoreEmail;
	private Button mResetPassowrd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		initialization();

		mProfilebtn.setOnClickListener(this);
		mAddmoreContact.setOnClickListener(this);
		mAddmoreEmail.setOnClickListener(this);
		mResetPassowrd.setOnClickListener(this);
		HomeButton.setOnClickListener(this);
	}
	private void initialization() {
		// TODO Auto-generated method stub
		mActionbutton = (Button)findViewById(R.id.actionbar_button);
		mActionbutton.setVisibility(View.GONE);

		mTitle = (TextView)findViewById(R.id.title);
		mTitle.setText("Settings");

		HomeButton = (Button)findViewById(R.id.Home_Button);

		mProfilebtn = (Button)findViewById(R.id.myprofile);
		mAddmoreContact = (Button)findViewById(R.id.Addmorecontact);
		mAddmoreEmail = (Button)findViewById(R.id.AddmoreEmail);
		mResetPassowrd = (Button)findViewById(R.id.resetpassord);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.myprofile:
			startActivity(new Intent(SettingActivity.this,MyProfileActivity.class));
			break;

		case R.id.Addmorecontact:
			startActivity(new Intent(SettingActivity.this,PhoneNoActivity.class));
			break;

		case R.id.AddmoreEmail:
			startActivity(new Intent(SettingActivity.this,EmailListActivity.class));
			break;

		case R.id.resetpassord:
			startActivity(new Intent(SettingActivity.this,ResetPasswordActivity.class));
			break;

		case R.id.Home_Button:
			startActivity(new Intent(SettingActivity.this,HomeActivity.class));
			finish();
			break;

		default:
			break;
		}
	}

}
