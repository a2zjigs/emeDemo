package com.iih.arview;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;

import com.iih.location.commonUtil.AppTypeDetails;
import com.iih.location.commonUtil.CommonUtil;
import com.iih.locationwithgoogleapi.Place;
import com.iih.locationwithgoogleapi.PlacesService;
import com.iih.utils.PaintUtils;


public class RadarView{
	/** The screen */
	public DataView view;
	/** The radar's range */
	float range;
	/** Radius in pixel on screen */
	public static float RADIUS = 40;
	/** Position on screen */
	static float originX = 0 , originY = 0;
	
	/**
	 * You can change the radar color from here.
	 *   */
	static int radarColor = Color.argb(100, 220, 0, 0);
	Location currentLocation = new Location("provider");
	Location destinedLocation = new Location("provider");

	public float[][] coordinateArray ;
	
	float angleToShift;
	public float degreetopixel;
	public float bearing;
	public float circleOriginX;
	public float circleOriginY;
	private float mscale;
	
	
	public float x = 0;
	public float y = 0;
	public float z = 0;

	float  yaw = 0;
	double[] bearings;
	ARView arView = new ARView();
	Activity mActivity;
	
	/**
	 * added by ashish
	 * 
	 */
	String[] placeName;
	private String[] imageUrl;
	private String[] latlongs;
	String latitude, longitude;
	private Double[] latitudes;
	private Double[] longitudes;

	/**
	 * 
	 * ************************
	 */
	
	public RadarView(DataView dataView, double[] bearings,Activity act){
		this.bearings = bearings;
		latlongs = CommonUtil.getLatAndLong(act);

		mActivity=act;
		latitude = latlongs[0];
		longitude = latlongs[1];
		findNearLocation();

		coordinateArray = new float[latitudes.length][2];
		calculateMetrics();
	}

	public void findNearLocation() {

		/*
		 * hear you should be pass the you current location latitude and
		 * langitude,
		 */

		PlacesService service = new PlacesService("AIzaSyAzRjKN6tPExnBv6dG7f3r7iy7PhAhX3QA");
		List<Place> findPlaces = service.findPlaces(latitude, longitude, AppTypeDetails.getInstance(mActivity).getArType());

		placeName = new String[findPlaces.size()];
		imageUrl = new String[findPlaces.size()];

		latitudes = new Double[findPlaces.size()];
		longitudes = new Double[findPlaces.size()];

		for (int i = 0; i < findPlaces.size(); i++) {

			Place placeDetail = findPlaces.get(i);
			placeDetail.getIcon();

			System.out.println(placeDetail.getName());
			placeName[i] = placeDetail.getName();

			imageUrl[i] = placeDetail.getIcon();

			latitudes[i] = placeDetail.getLatitude();
			longitudes[i] = placeDetail.getLongitude();
		}

	}

	
	public void calculateMetrics(){
		circleOriginX = originX + RADIUS;
		circleOriginY = originY + RADIUS;
		range = (float)arView.convertToPix(10) * 50;
		mscale = range / arView.convertToPix((int)RADIUS);
	}
	
	public void paint(PaintUtils dw, float yaw) {
		
//		circleOriginX = originX + RADIUS;
//		circleOriginY = originY + RADIUS;
		this.yaw = yaw;
//		range = arView.convertToPix(10) * 1000;		/** Draw the radar */
		dw.setFill(true);
		dw.setColor(radarColor);
		dw.paintCircle(originX + RADIUS, originY + RADIUS, RADIUS);

		/** put the markers in it */
//		float scale = range / arView.convertToPix((int)RADIUS);
		/**
		 *  Your current location coordinate here.
		 * */
		
//		latlongs = CommonUtil.getLatAndLong(act);
//
//		latitude = latlongs[0];
//		longitude = latlongs[1];
//		currentLocation.setLatitude(37.774968);
//		currentLocation.setLongitude(-122.41941);
		
		currentLocation.setLatitude(Double.parseDouble(latitude));
		currentLocation.setLongitude(Double.parseDouble(longitude));

		
		for(int i = 0; i <latitudes.length;i++){
			destinedLocation.setLatitude(latitudes[i]);
			destinedLocation.setLongitude(longitudes[i]);
			convLocToVec(currentLocation, destinedLocation);
			float x = this.x / mscale;
			float y = this.z / mscale;

			
			if (x * x + y * y < RADIUS * RADIUS) {
				dw.setFill(true);
				dw.setColor(Color.rgb(255, 255, 255));
				dw.paintRect(x + RADIUS, y + RADIUS, 2, 2);
			}
		}
	}

	public void calculateDistances(PaintUtils dw, float yaw){
		currentLocation.setLatitude(Double.parseDouble(latitude));
		currentLocation.setLongitude(Double.parseDouble(longitude));
		for(int i = 0; i <latitudes.length;i++){
			if(bearings[i]<0){
				bearings[i] = 360 - bearings[i];
			}
			if(Math.abs(coordinateArray[i][0] - yaw) > 3){
				angleToShift = (float)bearings[i] - this.yaw;
				coordinateArray[i][0] = this.yaw;
			}else{
				angleToShift = (float)bearings[i] - coordinateArray[i][0] ;
				
			}
			destinedLocation.setLatitude(latitudes[i]);
			destinedLocation.setLongitude(longitudes[i]);
			float[] z = new float[1];
			z[0] = 0;
			Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), destinedLocation.getLatitude(), destinedLocation.getLongitude(), z);
			bearing = currentLocation.bearingTo(destinedLocation);

			this.x = (float) (circleOriginX + 40 * (Math.cos(angleToShift)));
			this.y = (float) (circleOriginY + 40 * (Math.sin(angleToShift)));


			if (x * x + y * y < RADIUS * RADIUS) {
				dw.setFill(true);
				dw.setColor(Color.rgb(255, 255, 255));
				dw.paintRect(x + RADIUS - 1, y + RADIUS - 1, 2, 2);
			}
		}
	}
	
	/** Width on screen */
	public float getWidth() {
		return RADIUS * 2;
	}

	/** Height on screen */
	public float getHeight() {
		return RADIUS * 2;
	}
	
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void convLocToVec(Location source, Location destination) {
		float[] z = new float[1];
		z[0] = 0;
		Location.distanceBetween(source.getLatitude(), source.getLongitude(), destination
				.getLatitude(), source.getLongitude(), z);
		float[] x = new float[1];
		Location.distanceBetween(source.getLatitude(), source.getLongitude(), source
				.getLatitude(), destination.getLongitude(), x);
		if (source.getLatitude() < destination.getLatitude())
			z[0] *= -1;
		if (source.getLongitude() > destination.getLongitude())
			x[0] *= -1;

		set(x[0], (float) 0, z[0]);
	}
}