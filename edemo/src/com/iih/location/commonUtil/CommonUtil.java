package com.iih.location.commonUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.iih.android.emergency.R;

public class CommonUtil {

	public static boolean isBackPress = false;
	
	public static void showAlert(String message, Activity act) {
		Builder builder = new AlertDialog.Builder(act);
		builder.setTitle("Message");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	// Checking Phone Validations
		public static final boolean isValidPhoneNumber(String target) {
			String regexStr = "^[+]?[0-9]{10,13}$";
			if (target.length() < 10 || target.length() > 13 || target.matches(regexStr) == false) {
				return true;
			}
			return false;
		}
	/**
	 * Checking text is Empty Or not.
	 * @param Srt
	 * @return
	 */
	public static boolean isEmpty(String str) {

		try {
			if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim())) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * @param email
	 *            Email Address to which to be verified
	 * 
	 * @return <code>true</code> if Email Address is valid<br/>
	 *         <code> false</code> if Email Address is invalid
	 */
	public static boolean checkingEmail(String email) {
		String emailAddress = email.toString().trim();

		if (TextUtils.isEmpty(emailAddress))
			return false;
		else if (emailAddress.length() <= 6)
			return false;
		else {

			CharSequence inputStr = emailAddress;
			String globalEmailAddressPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
					+ "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}"
					+ "("
					+ "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

			Pattern pattern = Pattern.compile(globalEmailAddressPattern);
			Matcher matcher = pattern.matcher(inputStr);
			if (matcher.matches())
				return true;
			else
				return false;
		}
	}

	/**
	 * getting address from lat and long
	 * @param activity
	 * @param latitude
	 * @param longitude
	 * @return
	 */

	public static String getAddress(Activity activity,double latitude, double longitude) {
		StringBuilder result = new StringBuilder();
		try {
			Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(latitude,longitude, 1);
			Log.d("address", "" + addresses);

			if (addresses.size() > 0) {
				Address address = addresses.get(0);
				if (address.getAddressLine(0) != null) {
					result.append(address.getAddressLine(0)).append("\n");
				}
				if (address.getAddressLine(1) != null) {
					result.append(address.getAddressLine(1)).append("\n");
				}
				if (address.getAddressLine(2) != null) {
					result.append(address.getAddressLine(2)).append("\n");
				}
//				if (address.getThoroughfare() != null) {
//					result.append(address.getThoroughfare()).append("\n");
//				}
//				if (address.getFeatureName() != null) {
//					result.append(address.getFeatureName()).append("\n");
//				}
				// result.append(address.getAddressLine(2)).append("\n");
				// result.append(address.getSubAdminArea()).append("\n");
				// result.append(address.getSubLocality()).append("\n");
				if (address.getPhone() != null) {
					result.append(address.getPhone()).append("\n");
				}
				if (address.getLocality() != null) {
					result.append(address.getLocality()).append("\n");
				}
				if (address.getCountryName() != null) {
					result.append(address.getCountryName());
				}

//				Log.d("getSubAdminArea ", "" + address.getSubAdminArea());
//				Log.d("getSubLocality ", "" + (address.getSubLocality()));
//				Log.d("getPhone ", "" + address.getPhone());
//				Log.d("getLocality ", "" + address.getLocality());
//				Log.d("getCountryName ", "" + address.getCountryName());
//				Log.d("getFeatureName ", "" + address.getFeatureName());
//				Log.d("getUrl ", "" + address.getUrl());
//
//				Log.d("getAddressLine 0 ", "" + address.getAddressLine(0));
//				Log.d("getAddressLine 1 ", "" + address.getAddressLine(1));
//				Log.d("getAddressLine 2 ", "" + address.getAddressLine(2));
			}
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}

		return result.toString();
	}
	/**
	 * 
	 * @return
	 */
	public static boolean isSdcardPresent() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static String[] getLatAndLong(Activity mContext) {
		LocationUtility mLocation = new LocationUtility(mContext);
		return new String[] { String.valueOf(mLocation.getCurrentLatitude()),String.valueOf(mLocation.getCurrentLongitude()),
				String.valueOf(mLocation.isGPSON()) };
	}

	// Checking Internet Connection
	public static boolean checkConn(Context ctx) {
		ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (conMgr != null) {
			NetworkInfo i = conMgr.getActiveNetworkInfo();
			if (i != null) {
				if (!i.isConnected())
					return false;
				if (!i.isAvailable())
					return false;
			}

			if (i == null)
				return false;

		} else
			return false;

		return true;
	}
}
