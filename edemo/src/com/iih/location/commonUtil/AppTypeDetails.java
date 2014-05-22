package com.iih.location.commonUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Added by Ashish: This Singleton class handles to store data corresponding App
 * details which are stored in {@link SharedPreferences}
 */
public class AppTypeDetails {

	private SharedPreferences sh;

	private AppTypeDetails() {

	}

	private AppTypeDetails(Context mContext) {
		sh = PreferenceManager.getDefaultSharedPreferences(mContext);

	}

	private static AppTypeDetails instance = null;

	/**
	 * 
	 * @param mContext
	 * @return {@link AppTypeDetails}
	 */
	public synchronized static AppTypeDetails getInstance(Context mContext) {

		if (instance == null) {
			instance = new AppTypeDetails(mContext);
		}
		return instance;
	}

	public Boolean getIsFree() {
		return sh.getBoolean("isfree", true);
	}

	public void setIsFree(Boolean IsFree) {
		sh.edit().putBoolean("isfree", IsFree).commit();
	}

	public String getStartUrl() {
		return sh.getString("starturl", "");
	}

	/**
	 * @param StartUrl
	 *            the StartUrl to set
	 */
	public void setStartUrl(String starturl) {
		sh.edit().putString("starturl", starturl).commit();
	}

	/**
	 * Set And get Shared preference Method for fbstatus
	 * @return
	 */

	public String Getfblogin() {
		return sh.getString("fblogin", "");
	}

	public void Setfblogin(String fblogin) {
		sh.edit().putString("fblogin", fblogin).commit();
	}

	/**
	 * Set And get Shared preference Method for status
	 * @return
	 */

	public String Getstatus() {
		return sh.getString("status", "");
	}

	public void Setstatus(String status) {
		sh.edit().putString("status", status).commit();
	}

	/**
	 * Set And get Shared Preferences Method for First Name
	 * @return
	 */

	public String getFirstName() {
		return sh.getString("firstname", "");
	}

	public void setFirstName(String firstname) {
		sh.edit().putString("firstname", firstname).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Last Name
	 * @return
	 */

	public String getLastName() {
		return sh.getString("lastname", "");
	}

	public void setLastName(String lastname) {
		sh.edit().putString("lastname", lastname).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Email
	 * @return
	 */

	public String getEmail() {
		return sh.getString("email", "");
	}

	public void setEmail(String email) {
		sh.edit().putString("email", email).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Password
	 * @return
	 */

	public String getpassowrd() {
		return sh.getString("password", "");
	}

	public void setpassword(String password) {
		sh.edit().putString("password", password).commit();
	}

	/**
	 * Set And get Shared preference Method for Phone
	 * @return
	 */

	public String getPhone() {
		return sh.getString("phone", "");
	}

	public void setPhone(String phone) {
		sh.edit().putString("phone", phone).commit();
	}

	/**
	 * Set And get Shared preference Method for Blood group
	 * @return
	 */

	public String getBloodgroup() {
		return sh.getString("bloodgrp", "");
	}

	public void setBloodgroup(String bloodgrp) {
		sh.edit().putString("bloodgrp", bloodgrp).commit();
	}

	/**
	 * Set And get Shared preference Method for Allergies
	 * @return
	 */

	public String getAllergies() {
		return sh.getString("allergies", "");
	}

	public void setAllergies(String allergies) {
		sh.edit().putString("allergies", allergies).commit();
	}

	/**
	 * Set And get Shared Preferences Method for IsDiabetic
	 * @return
	 */

	public String getDiabetic() {
		return sh.getString("diabetic", "");
	}

	public void setDiabetic(String diabetic) {
		sh.edit().putString("diabetic", diabetic).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Blood Pressure
	 * @return
	 */

	public String getBloodPressure() {
		return sh.getString("bldpresure", "");
	}

	public void setBloodPressure(String bldpresure) {
		sh.edit().putString("bldpresure", bldpresure).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Cancer
	 * @return
	 */

	public String getCancer() {
		return sh.getString("cancer", "");
	}

	public void setcancer(String cancer) {
		sh.edit().putString("cancer", cancer).commit();
	}

	/**
	 * Set And get Shared preference Method for Police Helpline
	 * @return
	 */

	public String getpoliceno() {
		return sh.getString("police", "");
	}

	public void setpoliceno(String police) {
		sh.edit().putString("police", police).commit();
	}

	/**
	 * Set And get Shared preference Method for Hospital Helpline
	 * @return
	 */

	public String getHospitalno() {
		return sh.getString("hospital", "");
	}

	public void setHospitalno(String hospital) {
		sh.edit().putString("hospital", hospital).commit();
	}

	/**
	 * Set And get Shared preference Method for Fire Station Helpline
	 * @return
	 */

	public String getfireno() {
		return sh.getString("fire", "");
	}

	public void setfireno(String fire) {
		sh.edit().putString("fire", fire).commit();
	}

	/**
	 * Set And get Shared preference Method for Friend Mobile no
	 * @return
	 */

	public String getfriendno() {
		return sh.getString("friendno", "");
	}

	public void setfriendno(String friendno) {
		sh.edit().putString("friendno", friendno).commit();
	}

	
	/**
	 * Set And get Shared preference Method for AR types
	 * @return
	 */

	public String getArType() {
		return sh.getString("artype", "");
	}

	public void setArType(String arTypes) {
		sh.edit().putString("artype", arTypes).commit();
	}
	
	/**
	 * Set And get Shared preference Method for Email Address1
	 * @return
	 */

	public String getEmailAddress1() {
		return sh.getString("email1", "");
	}

	public void setEmailAddress1(String email1) {
		sh.edit().putString("email1", email1).commit();
	}

	/**
	 * Set And get Shared preference Method for Email Address2
	 * @return
	 */

	public String getEmailAddress2() {
		return sh.getString("email2", "");
	}

	public void setEmailAddress2(String email2) {
		sh.edit().putString("email2", email2).commit();
	}

	/**
	 * Set And get Shared preference Method for Email Address3
	 * @return
	 */

	public String getEmailAddress3() {
		return sh.getString("email3", "");
	}

	public void setEmailAddress3(String email3) {
		sh.edit().putString("email3", email3).commit();
	}

	/**
	 * Set And get Shared preference Method for Phone Number1
	 * @return
	 */

	public String getPhoneNumber1() {
		return sh.getString("phone1", "");
	}

	public void setPhoneNumber1(String phone1) {
		sh.edit().putString("phone1", phone1).commit();
	}

	/**
	 * Set And get Shared preference Method for Phone Number2
	 * @return
	 */

	public String getPhoneNumber2() {
		return sh.getString("phone2", "");
	}

	public void setPhoneNumber2(String phone2) {
		sh.edit().putString("phone2", phone2).commit();
	}

	/**
	 * Set And get Shared preference Method for Phone Number3
	 * @return
	 */

	public String getPhoneNumber3() {
		return sh.getString("phone3", "");
	}

	public void setPhoneNumber3(String phone3) {
		sh.edit().putString("phone3", phone3).commit();
	}

	/**
	 * Set And get Shared preference Method for LandLine 1
	 * @return
	 */

	public String getLandline1() {
		return sh.getString("landline1", "");
	}

	public void setLandline1(String landline1) {
		sh.edit().putString("landline1", landline1).commit();
	}

	/**
	 * Set And get Shared preference Method for LandLine 2
	 * @return
	 */

	public String getLandline2() {
		return sh.getString("landline2", "");
	}

	public void setLandline2(String landline2) {
		sh.edit().putString("landline2", landline2).commit();
	}

	/**
	 * Set And get Shared preference Method for LandLine 3
	 * @return
	 */

	public String getLandline3() {
		return sh.getString("landline3", "");
	}

	public void setLandline3(String landline3) {
		sh.edit().putString("landline3", landline3).commit();
	}

	/**
	 * Set And get Shared preference Method for Saving Image path 
	 * @return
	 */

	public String GetImagePath() {
		return sh.getString("path", "");
	}

	public void SetImagePath(String imagepath) {
		sh.edit().putString("path", imagepath).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Saving Current Latitude
	 * @return
	 */

	public String GetRegLatitude() {
		return sh.getString("latitude", "");
	}

	public void SetRegLatitude(String lat) {
		sh.edit().putString("latitude", lat).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Saving Current Longitude
	 * @return
	 */

	public String GetRegLongitude() {
		return sh.getString("longitude", "");
	}

	public void SetRegLongitude(String longtitude) {
		sh.edit().putString("longitude", longtitude).commit();
	}

	/**
	 * Set And get Shared Preferences Method for Saving Map Screenshot
	 * @return
	 */

	public String GetMapImage() {
		return sh.getString("mapimage", "");
	}

	public void SetMapImage(String mapimage) {
		sh.edit().putString("mapimage", mapimage).commit();
	}


	public void clear() {
		sh.edit().clear().commit();
	}

}
