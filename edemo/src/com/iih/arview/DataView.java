package com.iih.arview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iih.android.emergency.R;
import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.locationwithgoogleapi.Place;
import com.iih.locationwithgoogleapi.PlacesService;
import com.iih.utils.Camera;
import com.iih.utils.PaintUtils;
import com.iih.utils.RadarLines;

/**
 * 
 * Currently the markers are plotted with reference to line parallel to the
 * earth surface. We are working to include the elevation and height factors.
 * 
 * */

public class DataView {

	RelativeLayout[] locationMarkerView;
	// ImageView[] subjectImageView;
	RelativeLayout.LayoutParams[] layoutParams;
	RelativeLayout.LayoutParams[] subjectImageViewParams;
	RelativeLayout.LayoutParams[] subjectTextViewParams;
	TextView[] locationTextView;
	TextView[] detailAddTextView;

	int[] nextXofText;
	ArrayList<Integer> nextYofText = new ArrayList<Integer>();

	double[] bearings;
	float angleToShift;
	float yPosition;
	Location currentLocation = new Location("provider");
	Location destinedLocation = new Location("provider");

	/** is the view Initiated? */
	boolean isInit = false;
	boolean isDrawing = true;
	boolean isFirstEntry;
	Context _context;
	/** width and height of the view */
	int width, height;
	android.hardware.Camera camera;

	float yawPrevious;
	float yaw = 0;
	float pitch = 0;
	float roll = 0;

	DisplayMetrics displayMetrics;
	RadarView radarPoints;

	RadarLines lrl = new RadarLines();
	RadarLines rrl = new RadarLines();
	float rx = 10, ry = 20;
	public float addX = 0, addY = 0;
	public float degreetopixelWidth;
	public float degreetopixelHeight;
	public float pixelstodp;
	public float bearing;

	public int[][] coordinateArray = new int[20][2];
	public int locationBlockWidth;
	public int locationBlockHeight;

	public float deltaX;
	public float deltaY;
	Bitmap bmp;

	Dialog dialog;

	/**
	 * added by Ashish
	 * 
	 */
	String[] placeName;
	private String[] imageUrl;
	private String[] latlongs;
	String latitude, longitude;
	private Double[] latitudes;
	private Double[] longitudes;
	Activity activity;

	String[] detailAddress;

	// String[] phoneno;
	// String[] locality;
	// String[] countryName;

	/**
	 * 
	 * ************************
	 */
	public DataView(Context ctx, Activity act) {
		this._context = ctx;

		latlongs = CommonUtil.getLatAndLong(act);

		latitude = latlongs[0];
		longitude = latlongs[1];
		activity = act;

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}

	}

	public boolean isInited() {
		return isInit;
	}

	public class GetPlaces extends AsyncTask<Void, Void, Void> {
		Context context;
		private ListView listView;
		private ProgressDialog bar;

		public GetPlaces() {
			// TODO Auto-generated constructor stub
		}

		public GetPlaces(Context context, ListView listView) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.listView = listView;
		}

		public GetPlaces(Context context) {
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
				bar.setTitle("Loading");
				bar.show();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			// findNearLocation();
			return null;
		}

	}

	public void findNearLocation() {

		/*
		 * hear you should be pass the you current location latitude and
		 * langitude,
		 */

		PlacesService service = new PlacesService("AIzaSyAzRjKN6tPExnBv6dG7f3r7iy7PhAhX3QA");
		// List<Place> findPlaces = service.findPlaces(latitude, longitude,
		// "police|fire_station|hospital");
		String PlaceKeyword = AppTypeDetails.getInstance(activity).getArType();
		List<Place> findPlaces = service.findPlaces(latitude, longitude, PlaceKeyword);

		placeName = new String[findPlaces.size()];
		imageUrl = new String[findPlaces.size()];

		latitudes = new Double[findPlaces.size()];
		longitudes = new Double[findPlaces.size()];

		detailAddress = new String[findPlaces.size()];

		for (int i = 0; i < findPlaces.size(); i++) {

			Place placeDetail = findPlaces.get(i);
			placeDetail.getIcon();

			System.out.println(placeDetail.getName());
			placeName[i] = placeDetail.getName();

			imageUrl[i] = placeDetail.getIcon();

			latitudes[i] = placeDetail.getLatitude();
			longitudes[i] = placeDetail.getLongitude();

			detailAddress[i] = getAddress(latitudes[i], longitudes[i]);
		}

	}

	public void showAlert(String message) {
		Builder builder = new AlertDialog.Builder(activity);
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

	public String getAddress(double latitude, double longitude) {
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


	public void init(int widthInit, int heightInit,android.hardware.Camera camera, DisplayMetrics displayMetrics,RelativeLayout rel) {

		try {
			findNearLocation();
			locationMarkerView = new RelativeLayout[latitudes.length];
			layoutParams = new RelativeLayout.LayoutParams[latitudes.length];
			subjectImageViewParams = new RelativeLayout.LayoutParams[latitudes.length];
			subjectTextViewParams = new RelativeLayout.LayoutParams[latitudes.length];
			locationTextView = new TextView[latitudes.length];
			// detailAddTextView = new TextView[latitudes.length];
			nextXofText = new int[latitudes.length];

			for (int i = 0; i < latitudes.length; i++) {
				final int tabPlace = i;
				layoutParams[i] = new RelativeLayout.LayoutParams(500, 100);
				subjectTextViewParams[i] = new RelativeLayout.LayoutParams(1000, 500);

				locationMarkerView[i] = new RelativeLayout(_context);
				locationTextView[i] = new TextView(_context);
				// locationTextView[i].setText(checkTextToDisplay(placeName[i]));
				locationTextView[i].setText(placeName[i] + "\n"+ detailAddress[i]);
				locationTextView[i].setTextColor(Color.WHITE);
				locationTextView[i].setTextSize(16);
				locationMarkerView[i].setId(i);
				locationTextView[i].setId(i);

				/**
				 * For : Detail Address Text view
				 */
				// detailAddTextView[i] = new TextView(_context);
				// detailAddTextView[i].setText(detailAddress[i]);
				// detailAddTextView[i].setTextColor(Color.WHITE);
				// detailAddTextView[i].setTextSize(12);
				// detailAddTextView[i].setId(i);

				subjectImageViewParams[i] = new RelativeLayout.LayoutParams(400, 100);
				subjectImageViewParams[i].topMargin = 15;

				subjectImageViewParams[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
				layoutParams[i].setMargins(displayMetrics.widthPixels / 2,displayMetrics.heightPixels / 2, 0, 0);
				locationMarkerView[i] = new RelativeLayout(_context);
				locationMarkerView[i].setBackgroundResource(R.drawable.arbox);
				subjectTextViewParams[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				subjectTextViewParams[i].topMargin = 3;
				subjectTextViewParams[i].leftMargin = 7;
				locationMarkerView[i].setLayoutParams(layoutParams[i]);

				locationTextView[i].setLayoutParams(subjectTextViewParams[i]);

				locationMarkerView[i].addView(locationTextView[i]);
				rel.addView(locationMarkerView[i]);

				locationTextView[i].setClickable(false);

				/**
				 * For Detail address
				 */
				// detailAddTextView[i].setLayoutParams(subjectTextViewParams[i]);
				// locationMarkerView[i].addView(detailAddTextView[i]);
				// rel.addView(locationMarkerView[i]);
				// detailAddTextView[i].setClickable(false);

				locationTextView[i].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if ((v.getId() != -1)) {

							// Toast.makeText(
							// _context,
							// "Number of places here = "
							// +
							// checkTextToDisplay(placeName[tabPlace]+"lat"+latitudes[tabPlace]+"long"+longitudes[tabPlace]),
							// Toast.LENGTH_SHORT).show();

//							Toast.makeText(
//									_context,
//									"add= "
//											+ checkTextToDisplay(placeName[i]
//													+ "lat"
//													+ latitudes[tabPlace]
//													+ "long"
//													+ longitudes[tabPlace]),
//									Toast.LENGTH_SHORT).show();
							//showAlert(getAddress(latitudes[tabPlace],longitudes[tabPlace]));
							showPictureialog(placeName[tabPlace],getAddress(latitudes[tabPlace],longitudes[tabPlace]));
							RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) locationMarkerView[v.getId()].getLayoutParams();
							Rect rect = new Rect(params.leftMargin,
									params.topMargin, params.leftMargin+ params.width, params.topMargin+ params.height);
							ArrayList<Integer> matchIDs = new ArrayList<Integer>();
							Rect compRect = new Rect();
							int index = 0;
							for (RelativeLayout.LayoutParams layoutparams : layoutParams) {
								compRect.set(layoutparams.leftMargin,layoutparams.topMargin,layoutparams.leftMargin+ layoutparams.width,
								layoutparams.topMargin+ layoutparams.height);

								if (compRect.intersect(rect)) {
									matchIDs.add(index);
								}
								index++;
							}

							if (matchIDs.size() > 1) {

							}
//							Toast.makeText(
//									_context,
//									"Number of places here = "
//											+ matchIDs.size(),
//									Toast.LENGTH_SHORT).show();

							locationMarkerView[v.getId()].bringToFront();

						}

					}
				});

				locationMarkerView[i].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (v.getId() != -1) {

							RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) locationMarkerView[v.getId()].getLayoutParams();
							Rect rect = new Rect(params.leftMargin,
									params.topMargin, params.leftMargin+ params.width, params.topMargin+ params.height);
							ArrayList<Integer> matchIDs = new ArrayList<Integer>();
							Rect compRect = new Rect();
							int count = 0;
							int index = 0;
							for (RelativeLayout.LayoutParams layoutparams : layoutParams) {
								compRect.set(layoutparams.leftMargin,layoutparams.topMargin,
										layoutparams.leftMargin+ layoutparams.width,
										layoutparams.topMargin+ layoutparams.height);

								if (compRect.intersect(rect)) {
									matchIDs.add(index);
									count += 1;
								}
								index++;
							}

							if (count > 1) {

							}
//							Toast.makeText(_context,
//									"Number of places here = " + count,
//									Toast.LENGTH_SHORT).show();

							locationMarkerView[v.getId()].bringToFront();
						}

					}
				});
			}

			this.displayMetrics = displayMetrics;
			this.degreetopixelWidth = this.displayMetrics.widthPixels / camera.getParameters().getHorizontalViewAngle();
			this.degreetopixelHeight = this.displayMetrics.heightPixels/ camera.getParameters().getVerticalViewAngle();
			System.out.println("camera.getParameters().getHorizontalViewAngle()==" + camera.getParameters().getHorizontalViewAngle());

			bearings = new double[latitudes.length];

			currentLocation.setLatitude(Double.parseDouble(latitude));
			currentLocation.setLongitude(Double.parseDouble(longitude));

			if (bearing < 0)
				bearing = 360 + bearing;

			for (int i = 0; i < latitudes.length; i++) {
				destinedLocation.setLatitude(latitudes[i]);
				destinedLocation.setLongitude(longitudes[i]);
				bearing = currentLocation.bearingTo(destinedLocation);

				if (bearing < 0) {
					bearing = 360 + bearing;
				}
				bearings[i] = bearing;

			}
			radarPoints = new RadarView(this, bearings, activity);
			this.camera = camera;
			width = widthInit;
			height = heightInit;

			lrl.set(0, -RadarView.RADIUS);
			lrl.rotate(Camera.DEFAULT_VIEW_ANGLE / 2);
			lrl.add(rx + RadarView.RADIUS, ry + RadarView.RADIUS);
			rrl.set(0, -RadarView.RADIUS);
			rrl.rotate(-Camera.DEFAULT_VIEW_ANGLE / 2);
			rrl.add(rx + RadarView.RADIUS, ry + RadarView.RADIUS);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		/*
		 * initialization is done, so dont call init() again.
		 */
		isInit = true;
	}

	public void draw(PaintUtils dw, float yaw, float pitch, float roll) {

		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;

		// Draw Radar
		String dirTxt = "";
		int bearing = (int) this.yaw;
		int range = (int) (this.yaw / (360f / 16f));
		if (range == 15 || range == 0)
			dirTxt = "N";
		else if (range == 1 || range == 2)
			dirTxt = "NE";
		else if (range == 3 || range == 4)
			dirTxt = "E";
		else if (range == 5 || range == 6)
			dirTxt = "SE";
		else if (range == 7 || range == 8)
			dirTxt = "S";
		else if (range == 9 || range == 10)
			dirTxt = "SW";
		else if (range == 11 || range == 12)
			dirTxt = "W";
		else if (range == 13 || range == 14)
			dirTxt = "NW";

		radarPoints.view = this;

		dw.paintObj(radarPoints, rx + PaintUtils.XPADDING, ry+ PaintUtils.YPADDING, -this.yaw, 1, this.yaw);
		dw.setFill(false);
		dw.setColor(Color.argb(100, 220, 0, 0));
		dw.paintLine(lrl.x, lrl.y, rx + RadarView.RADIUS, ry + RadarView.RADIUS);
		dw.paintLine(rrl.x, rrl.y, rx + RadarView.RADIUS, ry + RadarView.RADIUS);
		dw.setColor(Color.rgb(255, 255, 255));
		dw.setFontSize(12);
		radarText(dw, "" + bearing + ((char) 176) + " " + dirTxt, rx+ RadarView.RADIUS, ry - 5, true, false, -1);
		drawTextBlock(dw);
	}

	void drawPOI(PaintUtils dw, float yaw) {
		if (isDrawing) {
			dw.paintObj(radarPoints, rx + PaintUtils.XPADDING, ry+ PaintUtils.YPADDING, -this.yaw, 1, this.yaw);
			isDrawing = false;
		}
	}

	void radarText(PaintUtils dw, String txt, float x, float y, boolean bg,boolean isLocationBlock, int count) {

		float padw = 4, padh = 2;
		float w = dw.getTextWidth(txt) + padw * 2;
		float h;
		if (isLocationBlock) {
			h = dw.getTextAsc() + dw.getTextDesc() + padh * 2 + 10;
		} else {
			h = dw.getTextAsc() + dw.getTextDesc() + padh * 2;
		}
		if (bg) {
			if (isLocationBlock) {
				layoutParams[count].setMargins((int) (x - w / 2 - 10), (int) (y - h / 2 - 10), 0, 0);
				layoutParams[count].height = 190;
				layoutParams[count].width = 200;
				locationMarkerView[count].setLayoutParams(layoutParams[count]);

			} else {
				dw.setColor(Color.rgb(0, 0, 0));
				dw.setFill(true);
				dw.paintRect((x - w / 2) + PaintUtils.XPADDING, (y - h / 2)+ PaintUtils.YPADDING, w, h);
				pixelstodp = (padw + x - w / 2)/ ((displayMetrics.density) / 160);
				dw.setColor(Color.rgb(255, 255, 255));
				dw.setFill(false);
				dw.paintText((padw + x - w / 2) + PaintUtils.XPADDING, ((padh+ dw.getTextAsc() + y - h / 2))+ PaintUtils.YPADDING, txt);
			}
		}

	}

	String checkTextToDisplay(String str) {

		if (str.length() > 30) {
			str = str.substring(0, 30) + "...";
		}
		return str;

	}

	void drawTextBlock(PaintUtils dw) {

		for (int i = 0; i < bearings.length; i++) {
			if (bearings[i] < 0) {

				if (this.pitch != 90) {
					yPosition = (this.pitch - 90) * this.degreetopixelHeight + 200;
				} else {
					yPosition = (float) this.height / 2;
				}
				bearings[i] = 360 - bearings[i];
				angleToShift = (float) bearings[i] - this.yaw;
				nextXofText[i] = (int) (angleToShift * degreetopixelWidth);
				yawPrevious = this.yaw;
				isDrawing = true;
				radarText(dw, placeName[i], nextXofText[i], yPosition, true,true, i);
				coordinateArray[i][0] = nextXofText[i];
				coordinateArray[i][1] = (int) yPosition;

			} else {
				angleToShift = (float) bearings[i] - this.yaw;

				if (this.pitch != 90) {
					yPosition = (this.pitch - 90) * this.degreetopixelHeight+ 200;
				} else {
					yPosition = (float) this.height / 2;
				}

				nextXofText[i] = (int) ((displayMetrics.widthPixels / 2) + (angleToShift * degreetopixelWidth));
				if (Math.abs(coordinateArray[i][0] - nextXofText[i]) > 50) {
					radarText(dw, placeName[i], (nextXofText[i]), yPosition,true, true, i);
					coordinateArray[i][0] = (int) ((displayMetrics.widthPixels / 2) + (angleToShift * degreetopixelWidth));
					coordinateArray[i][1] = (int) yPosition;
					isDrawing = true;
				} else {
					radarText(dw, placeName[i], coordinateArray[i][0],yPosition, true, true, i);
					isDrawing = false;
				}
			}
		}
	}

	private void showPictureialog(String mTitle, String mMessage) {

		dialog = new Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar);

		/**
		 * Setting dialogview
		 */

		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);

		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		dialog.setTitle(null);

		dialog.setContentView(R.layout.dialog_common);
		dialog.setCancelable(true);
		TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
		TextView message = (TextView) dialog.findViewById(R.id.dialog_message);
		title.setText(mTitle);
		message.setText(mMessage);
		ImageButton ok = (ImageButton) dialog.findViewById(R.id.btn_ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public class NearbyPlacesList extends BaseAdapter {

		ArrayList<Integer> matchIDs = new ArrayList<Integer>();

		public NearbyPlacesList(ArrayList<Integer> matchID) {
			matchIDs = matchID;
		}

		@Override
		public int getCount() {
			return matchIDs.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

	}
}