package vn.mhst24.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import tungnv.haui.mhst24.R;
import vn.mhst24.json.GetLatLng;
import vn.mhst24.json.HandleXML;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;
import app.akexorcist.gdaplibrary.GoogleDirection;
import app.akexorcist.gdaplibrary.GoogleDirection.OnDirectionResponseListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailMap extends FragmentActivity {
	// URL to get contacts JSON
	private static String xml_url = "https://maps.googleapis.com/maps/api/geocode/xml?address=";
	private static String api_key = "AIzaSyB36gqXnG07xGhiE6LaI4GEikfLYVatQfk";
	// JSON Node names
	private static final String TAG_RESULTS = "results";
	private static final String TAG_GEO = "geometry";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	JSONArray contacts = null;
	String lc_start, lc_end;

	/**
	 * @return the lc_start
	 */
	public String getLc_start() {
		return lc_start;
	}

	/**
	 * @param lc_start
	 *            the lc_start to set
	 */
	public void setLc_start(String lc_start) {
		this.lc_start = lc_start;
	}

	/**
	 * @return the lc_end
	 */
	public String getLc_end() {
		return lc_end;
	}

	/**
	 * @param lc_end
	 *            the lc_end to set
	 */
	public void setLc_end(String lc_end) {
		this.lc_end = lc_end;
	}

	TextView startPoint;
	TextView destinationPoint;
	TextView txtTime, emptyChair, txtPrice, userName;
	LatLng start;
	LatLng end;
	String lat, lng;
	Button buttonRequest;
	String urls, urld;
	GoogleMap mMap;
	GoogleDirection gd;
	Document mDoc;
	private ProgressDialog pDialog;
	private HandleXML objs, objd;
	static double longitute;
	static double latitude;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_map);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			lc_start = extras.getString("start");
			lc_end = extras.getString("end");
		}
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		/*
		 * System.out.println("getLatLong" +
		 * getLatLong(getLocationInfo(spoint)));
		 * 
		 * String adSpoin = null, adDpoin = null; try { adSpoin =
		 * addressEncode(spoint); adDpoin = addressEncode(dpoint); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } // get lat,lng using xpacht
		 * 
		 * // get lat,lng spoint urls = xml_url + adSpoin + "&key=" + api_key;
		 * 
		 * objs = new HandleXML(urls); objs.fetchXML(); while
		 * (objs.parsingComplete) ; // get lat,lng dpoint urld = xml_url +
		 * adDpoin + "&key=" + api_key; objd = new HandleXML(urld);
		 * objd.fetchXML();
		 * 
		 * while (objd.parsingComplete) ; GetDistance gd1 = new GetDistance();
		 * System.out.println("GetDistance" + gd1.getDistance(spoint, dpoint));
		 * 
		 * GetLatLng2 gll = new GetLatLng2();
		 * 
		 * System.out.println("lat1" + gll.GetLat(spoint));
		 * System.out.println("lng1" + gll.GetLng(dpoint));
		 * 
		 * // new GetContacts().execute(); // map
		 */

		GetLatLng gll = new GetLatLng();
		@SuppressWarnings("static-access")
		Double sLat = gll.getLat(gll.getLocationInfo(lc_start));
		Double sLng = gll.getLng(gll.getLocationInfo(lc_start));
		Double dLat = gll.getLat(gll.getLocationInfo(lc_end));
		Double dLng = gll.getLng(gll.getLocationInfo(lc_end));
		start = new LatLng(sLat, sLng);
		end = new LatLng(dLat, dLng);
		//
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15));

		gd = new GoogleDirection(DetailMap.this);
		gd.setOnDirectionResponseListener(new OnDirectionResponseListener() {
			public void onResponse(String status, Document doc,
					GoogleDirection gd) {
				mDoc = doc;
				mMap.addPolyline(gd.getPolyline(doc, 3, Color.RED));
				mMap.addMarker(new MarkerOptions()
						.position(start)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

				mMap.addMarker(new MarkerOptions()
						.position(end)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			}
		});
		gd.setLogging(true);
		gd.request(start, end, GoogleDirection.MODE_DRIVING);

	}

	@SuppressWarnings("unused")
	private String addressEncode(String address)
			throws UnsupportedEncodingException {

		return address = URLEncoder.encode(address, "utf-8");

	}

	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(DetailMap.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance

			try {

				JSONObject jsonObj = new JSONObject(urls);

				// Getting JSON Array node
				contacts = jsonObj.getJSONArray(TAG_RESULTS);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					// Phone node is JSON Object
					JSONObject geometry = c.getJSONObject(TAG_GEO);
					/*
					 * lat = geometry.getString(TAG_LAT);
					 * System.out.println("lat" + lat); lng =
					 * geometry.getString(TAG_LNG); System.out.println("lng" +
					 * lng);
					 */

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			// start = new LatLng(Double.parseDouble(lat),
			// Double.parseDouble(lng));
			// end = new LatLng(13.751279688694071, 100.54316081106663);
			//
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15));

			gd = new GoogleDirection(DetailMap.this);
			gd.setOnDirectionResponseListener(new OnDirectionResponseListener() {
				public void onResponse(String status, Document doc,
						GoogleDirection gd) {
					mDoc = doc;
					mMap.addPolyline(gd.getPolyline(doc, 3, Color.RED));
					mMap.addMarker(new MarkerOptions()
							.position(start)
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

					mMap.addMarker(new MarkerOptions()
							.position(end)
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
				}
			});
			gd.setLogging(true);
			gd.request(start, end, GoogleDirection.MODE_DRIVING);
		}

	}

}
