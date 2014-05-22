package com.iih.location.commonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class XmlData {
	
	public static void CreateXml(String status,String firstname,String lastname,String email,String password,String Uphone,
			String bloodgroup,String allergies,String diabetic,String bldpressure,String cancer,String Police,String hospital,String firestation,String icsno,String email1,String email2,String email3,
			String phone1,String phone2,String phone3,String landline1,String landline2,String landline3,String latitude,
			String longtitude) {

		File newxmlfile = new File(Environment.getExternalStorageDirectory()+ "/db.xml");
		// File newxmlfile = new File(XmlFileCreator.this.getFilesDir(),
		// "db.xml");
		try {
			newxmlfile.createNewFile();
		} catch (IOException e) {
			Log.e("IOException", "exception in createNewFile() method");
		}
		// we have to bind the new file with a FileOutputStream
		FileOutputStream fileos = null;
		try {
			fileos = new FileOutputStream(newxmlfile);
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", "can't create FileOutputStream");
		}
		// we create a XmlSerializer in order to write xml data
		XmlSerializer serializer = Xml.newSerializer();
		try {
			// we set the FileOutputStream as output for the serializer, using
			// UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			// Write <?xml declaration with encoding (if encoding not null) and
			// standalone flag (if standalone not null)
			serializer.startDocument(null, Boolean.valueOf(true));
			// set indentation option
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output",true);
			// start a tag called "root"
			serializer.startTag(null, "root");
			// i indent code just to have a view similar to xml-tree

			serializer.startTag(null, "status");
			serializer.text(status);
			serializer.endTag(null, "status");

			serializer.startTag(null, "fname");
			serializer.text(firstname);
			serializer.endTag(null, "fname");
			
			serializer.startTag(null, "lname");
			serializer.text(lastname);
			serializer.endTag(null, "lname");

			serializer.startTag(null, "uemail");
			serializer.text(email);
			serializer.endTag(null, "uemail");

			serializer.startTag(null, "upassword");
			serializer.text(password);
			serializer.endTag(null, "upassword");
			
			serializer.startTag(null, "uphoneno");
			serializer.text(Uphone);
			serializer.endTag(null, "uphoneno");

			serializer.startTag(null, "bloodgroup");
			serializer.text(bloodgroup);
			serializer.endTag(null, "bloodgroup");

			serializer.startTag(null, "allergies");
			serializer.text(allergies);
			serializer.endTag(null, "allergies");

			serializer.startTag(null, "diabetic");
			serializer.text(diabetic);
			serializer.endTag(null, "diabetic");

			serializer.startTag(null, "bldpressure");
			serializer.text(bldpressure);
			serializer.endTag(null, "bldpressure");

			serializer.startTag(null, "cancer");
			serializer.text(cancer);
			serializer.endTag(null, "cancer");

			serializer.startTag(null, "policeno");
			serializer.text(Police);
			serializer.endTag(null, "policeno");

			serializer.startTag(null, "hospitalno");
			serializer.text(hospital);
			serializer.endTag(null, "hospitalno");

			serializer.startTag(null, "fireno");
			// write some text inside <child3>
			// serializer.text("8866381425");
			serializer.text(firestation);
			serializer.endTag(null, "fireno");
			
			serializer.startTag(null, "iceno");
			// write some text inside <child3>
			// serializer.text("8866381425");
			serializer.text(icsno);
			serializer.endTag(null, "iceno");

			serializer.startTag(null, "email1");
			serializer.text(email1);
			serializer.endTag(null, "email1");

			serializer.startTag(null, "email2");
			serializer.text(email2);
			serializer.endTag(null, "email2");

			serializer.startTag(null, "email3");
			serializer.text(email3);
			serializer.endTag(null, "email3");

			serializer.startTag(null, "phone1");
			serializer.text(phone1);
			serializer.endTag(null, "phone1");

			serializer.startTag(null, "phone2");
			serializer.text(phone2);
			serializer.endTag(null, "phone2");

			serializer.startTag(null, "phone3");
			serializer.text(phone3);
			serializer.endTag(null, "phone3");

			serializer.startTag(null, "landline1");
			serializer.text(landline1);
			serializer.endTag(null, "landline1");

			serializer.startTag(null, "landline2");
			serializer.text(landline2);
			serializer.endTag(null, "landline2");

			serializer.startTag(null, "landline3");
			serializer.text(landline3);
			serializer.endTag(null, "landline3");

			serializer.startTag(null, "latitude");
			serializer.text(latitude);
			serializer.endTag(null, "latitude");

			serializer.startTag(null, "longtitude");
			serializer.text(longtitude);
			serializer.endTag(null, "longtitude");

			serializer.endTag(null, "root");
			serializer.endDocument();
			// write xml data into the FileOutputStream
			serializer.flush();
			// finally we close the file stream
			fileos.close();

		} catch (Exception e) {
			Log.e("Exception", "error occurred while creating xml file");
		}
	}
}
