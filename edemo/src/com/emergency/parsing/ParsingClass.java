package com.emergency.parsing;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParsingClass extends DefaultHandler {

//	ArrayList<String> Uemail = new ArrayList<String>();
//	ArrayList<String> Upass = new ArrayList<String>();
//	ArrayList<String> PoliceNo = new ArrayList<String>();

	public String strUemail = "";
	public String strUpass = "";
	public String strUphoneNo = "";
	public String strbloodgrp = "";
	public String strallergies = "";
	public String strdiabetic = "";
	public String strbldpressure = "";
	public String strcancer = "";
	public String strPoliceNo = "";
	public String strHospitalNo = "";
	public String strFireStNo = "";
	public String strFname="";
	public String strLname="";
	public String strIceno = "";

	public String strEmail1 = "";
	public String strEmail2 = "";
	public String strEmail3 = "";

	public String strPhoneNo1 = "";
	public String strPhoneNo2 = "";
	public String strPhoneNo3 = "";

	public String strLandline1 = "";
	public String strLandline2 = "";
	public String strLandline3 = "";

	public String status = "";

	public String latitude = "";
	public String longitude = "";

//	 EditText edtEmail;
//	 EditText edtNumber;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase("fname")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("lname")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("uemail")) {
			tempStore = "";
		} else if (localName.equalsIgnoreCase("upassword")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("uphoneno")) {
			tempStore = "";
		} else if (localName.equalsIgnoreCase("bloodgroup")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("allergies")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("diabetic")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("bldpressure")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("cancer")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("policeno")) {
			tempStore = "";
		}
		else if (localName.equalsIgnoreCase("hospitalno")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("fireno")) {
			tempStore = "";
		} else if (localName.equalsIgnoreCase("iceno")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("email1")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("email2")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("email3")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("phone1")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("phone2")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("phone3")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("landline1")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("landline2")) {
			tempStore = "";
		}else if (localName.equalsIgnoreCase("landline3")) {
			tempStore = "";
		}else if(localName.equalsIgnoreCase("status")){
			tempStore = "";
		}else if(localName.equalsIgnoreCase("latitude")){
			tempStore = "";
		}else if(localName.equalsIgnoreCase("longtitude")){
			tempStore = "";
		}else{
			tempStore = "";
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (localName.equalsIgnoreCase("fname")) {
			strFname=tempStore;
		}else if (localName.equalsIgnoreCase("lname")) {
//			Uemail.add(tempStore);
			strLname=tempStore;
		}else if (localName.equalsIgnoreCase("uemail")) {
			strUemail=tempStore;
		} else if (localName.equalsIgnoreCase("upassword")) {
			strUpass=tempStore;
		}else if (localName.equalsIgnoreCase("uphoneno")) {
			strUphoneNo=tempStore;
		}else if (localName.equalsIgnoreCase("bloodgroup")) {
			strbloodgrp=tempStore;
		}else if (localName.equalsIgnoreCase("allergies")) {
			strallergies=tempStore;
		}else if (localName.equalsIgnoreCase("diabetic")) {
			strdiabetic=tempStore;
		}else if (localName.equalsIgnoreCase("bldpressure")) {
			strbldpressure=tempStore;
		}else if (localName.equalsIgnoreCase("cancer")) {
			strcancer=tempStore;
		}else if (localName.equalsIgnoreCase("policeno")) {
			strPoliceNo=tempStore;
		} else if (localName.equalsIgnoreCase("hospitalno")) {
			strHospitalNo=tempStore;
		}else if (localName.equalsIgnoreCase("fireno")) {
			strFireStNo=tempStore;
		}else if (localName.equalsIgnoreCase("iceno")) {
			strIceno=tempStore;
		}else if (localName.equalsIgnoreCase("email1")) {
			strEmail1=tempStore;
		}else if (localName.equalsIgnoreCase("email2")) {
			strEmail2=tempStore;
		}else if (localName.equalsIgnoreCase("email3")) {
			strEmail3=tempStore;
		}else if (localName.equalsIgnoreCase("phone1")) {
			strPhoneNo1=tempStore;
		}else if (localName.equalsIgnoreCase("phone2")) {
			strPhoneNo2=tempStore;
		}else if (localName.equalsIgnoreCase("phone3")) {
			strPhoneNo3=tempStore;
		}else if (localName.equalsIgnoreCase("landline1")) {
			strLandline1=tempStore;
		}else if (localName.equalsIgnoreCase("landline2")) {
			strLandline2=tempStore;
		}else if (localName.equalsIgnoreCase("landline3")) {
			strLandline3=tempStore;
		}else if(localName.equalsIgnoreCase("status")){
			status = tempStore;
		}else if(localName.equalsIgnoreCase("latitude")){
			latitude = tempStore;
		}else if(localName.equalsIgnoreCase("longtitude")){
			longitude = tempStore;
		}
		tempStore = "";
	}

	private String tempStore = "";

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		tempStore += new String(ch, start, length);
	}
}
