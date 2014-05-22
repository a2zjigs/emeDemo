package com.iih.android.emergency;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iih.arview.DataView;
import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;

public class MapViewActivity extends FragmentActivity {

	private TextView mTitle;
	private Button mCapturebtn;
	private Button HomeButton;

	final int RQS_GooglePlayServices = 1;
	private GoogleMap myMap;

	double src_lat;
	double src_long;
	double dest_lat;
	double dest_long;

	private String[] latlongs;
	private String latitude;
	private String longitude;

	private MarkerOptions markerOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);

		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("Location");

		mCapturebtn = (Button)findViewById(R.id.actionbar_button);
		mCapturebtn.setBackgroundResource(R.drawable.camera_selector);

		HomeButton = (Button)findViewById(R.id.Home_Button);
		HomeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MapViewActivity.this,HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});

		mCapturebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CaptureMapScreen();
			}
		});

		/**
		 * Getting User Latitude and Longitude of Registration Time.
		 */
		String strlat =AppTypeDetails.getInstance(MapViewActivity.this).GetRegLatitude();
		String StrLong = AppTypeDetails.getInstance(MapViewActivity.this).GetRegLongitude();

		src_lat = Double.valueOf(strlat);
		src_long =Double.valueOf(StrLong);

		/**
		 * getting Current Latitude and Longitude
		 */
		latlongs = CommonUtil.getLatAndLong(MapViewActivity.this);
		latitude = latlongs[0];
		longitude = latlongs[1];

		dest_lat = Double.valueOf(latitude);
		dest_long = Double.valueOf(longitude);

		myMap = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map))).getMap();

		if (myMap==null) {
			Toast.makeText(MapViewActivity.this,"incompatible version / not available of Google Play Services",Toast.LENGTH_LONG).show();
			return;
		}

		LatLng srcLatLng = new LatLng(src_lat, src_long);
		LatLng destLatLng = new LatLng(dest_lat, dest_long);

		// LatLng srcLatLng = new LatLng(23.0,72.0);
		// LatLng destLatLng = new LatLng(23.0,72.0);

		/**
		 * Getting Source and Destination Address.
		 */
		DataView dataview = new DataView(getApplicationContext(), MapViewActivity.this);
		String SrcAddress = dataview.getAddress(src_lat, src_long);
		String DesAddress = dataview.getAddress(dest_lat, dest_long);

		myMap.addMarker(new MarkerOptions().position(srcLatLng).title(SrcAddress));
		myMap.animateCamera(CameraUpdateFactory.newLatLng(srcLatLng));
		myMap.addMarker(new MarkerOptions().position(destLatLng).title(DesAddress));

		// Enabling MyLocation in Google Map
		myMap.setMyLocationEnabled(true);
		myMap.getUiSettings().setZoomControlsEnabled(true);
		myMap.getUiSettings().setCompassEnabled(true);
		myMap.getUiSettings().setMyLocationButtonEnabled(true);
		myMap.getUiSettings().setAllGesturesEnabled(true);
		myMap.setTrafficEnabled(true);
		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(srcLatLng, 12));

		// myMap.buildDrawingCache();
		// Bitmap bitmap = myMap.getDrawingCache();
		// myMap.destroyDrawingCache();

		markerOptions = new MarkerOptions();

		// Polyline line = myMap.addPolyline(new
		// PolylineOptions().add(srcLatLng,
		// destLatLng).width(5).color(Color.RED));
			connectAsyncTask _connectAsyncTask = new connectAsyncTask();
			_connectAsyncTask.execute();

	}

	public void CaptureMapScreen() {

		SnapshotReadyCallback callback = new SnapshotReadyCallback() {
			Bitmap bitmap;

			@Override
			public void onSnapshotReady(Bitmap snapshot) {
				// TODO Auto-generated method stub
				bitmap = snapshot;
				try {

					String path = Environment.getExternalStorageDirectory().toString();

					new File(path + "/safty").mkdirs();
//					File myFile = new File(path + "/safty/" + "safty" + ".jpg");
					String mapImagename = "MyMapScreen" + System.currentTimeMillis();

					FileOutputStream out = new FileOutputStream(path + "/safty/" + mapImagename + ".png");

					AppTypeDetails.getInstance(MapViewActivity.this).SetMapImage(mapImagename);

					// above "/mnt ..... png" => is a storage path (where image
					// will be stored) + name of image you can customize as per
					// your Requirement

					bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		myMap.snapshot(callback);

		// myMap is object of GoogleMap +> GoogleMap myMap;
		// which is initialized in onCreate() =>
		// myMap = ((SupportMapFragment)
		// getSupportFragmentManager().findFragmentById(R.id.map_pass_home_call)).getMap();
	}
	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS) {
			//Toast.makeText(getApplicationContext(),"isGooglePlayServicesAvailable SUCCESS", Toast.LENGTH_LONG).show();
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,RQS_GooglePlayServices);
		}
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(MapViewActivity.this);
			progressDialog.setMessage("Fetching route, Please wait...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			fetchData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (doc != null) {

				NodeList _nodelist = doc.getElementsByTagName("status");
				Node node1 = _nodelist.item(0);
				String _status1 = node1.getChildNodes().item(0).getNodeValue();

				if (_status1.equalsIgnoreCase("OK")) {
					NodeList _nodelist_path = doc.getElementsByTagName("overview_polyline");
					Node node_path = _nodelist_path.item(0);
					Element _status_path = (Element) node_path;
					NodeList _nodelist_destination_path = _status_path.getElementsByTagName("points");
					Node _nodelist_dest = _nodelist_destination_path.item(0);
					String _path = _nodelist_dest.getChildNodes().item(0).getNodeValue();
					List<LatLng> directionPoint = decodePoly(_path);

					PolylineOptions rectLine = new PolylineOptions().width(10).color(Color.RED);
					for (int i = 0; i < directionPoint.size(); i++) {
						rectLine.add(directionPoint.get(i));
					}
					// Adding route on the map
					myMap.addPolyline(rectLine);
					markerOptions.position(new LatLng(dest_lat, dest_long));
					markerOptions.draggable(true);
					myMap.addMarker(markerOptions);
				} else {
					showAlert("Unable to find the route");
				}

			} else {
				showAlert("Unable to find the route");
			}

			progressDialog.dismiss();

		}

	}

	Document doc = null;

	private void fetchData() {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps/api/directions/xml?origin=");
		urlString.append(src_lat);
		urlString.append(",");
		urlString.append(src_long);
		urlString.append("&destination=");// to
		urlString.append(dest_lat);
		urlString.append(",");
		urlString.append(dest_long);
		urlString.append("&sensor=true&mode=driving");
		Log.d("url", "::" + urlString.toString());
		HttpURLConnection urlConnection = null;
		URL url = null;
		try {
			url = new URL(urlString.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.connect();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = (Document) db.parse(urlConnection.getInputStream());// Util.XMLfromString(response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showAlert(String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(MapViewActivity.this);
		alert.setTitle("Error");
		alert.setCancelable(false);
		alert.setMessage(message);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		alert.show();
	}

	private ArrayList<LatLng> decodePoly(String encoded) {
		ArrayList<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;
		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;
			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
			poly.add(position);
		}
		return poly;
	}
}