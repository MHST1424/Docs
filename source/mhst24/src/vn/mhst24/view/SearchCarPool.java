package vn.mhst24.view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.PlaceHolder;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.json.GetLatLng;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class SearchCarPool extends ListActivity {
	
	AutoCompleteTextView editstart, editend;
	Button btnsearch;
	// url to get all products list
	private static String url = "http://viettungitpt.byethost7.com/dichung/list_carpool_filter.php";
	// JSON Node names
	private static final String TAG_NUM_TRIP = "tripsinfos";

	private static final String TAG_PID = "pid";
	private static final String TAG_USER = "name";
	//private static final String TAG_LATLONG = "lat_long";
	private static final String TAG_LC_START = "lc_start";
	private static final String TAG_LC_FINISH = "lc_finish";
	private static final String TAG_TIME_START = "time_start";
	private static final String TAG_TIME_FINISH = "time_back";
	
	private static final String TAG_LAT1 = "slat";
	private static final String TAG_LONG1 = "slong";
	private static final String TAG_LAT2 = "flat";
	private static final String TAG_LONG2 = "flong";
	private static final String TAG_VALUE = "value";

	// products JSONArray
	JSONArray trips = null;
	RelativeLayout ll;
	ListView list;
	String lc_start, lc_finish;
	GetLatLng gll;
	TextView txtid;
	
	ArrayList<HashMap<String, String>> oslist;
	static final Logger log = Logger.getLogger(SearchCarPool.class);
	JSONArray tripsinfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fm_search);
		LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setFileName(Environment.getExternalStorageDirectory()
				+ File.separator + "mhst24" + File.separator + "logs"
				+ File.separator + "log4j.txt");
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
		logConfigurator.setMaxFileSize(1024 * 1024 * 5);
		logConfigurator.setImmediateFlush(true);
		logConfigurator.configure();
		Logger log = Logger.getLogger(SearchCarPool.class);
		log.info("My Application Created");
		
		editstart = (AutoCompleteTextView) findViewById(R.id.autoeditstart);
		editend = (AutoCompleteTextView) findViewById(R.id.autoeditend);
		btnsearch = (Button) findViewById(R.id.btnsearch);

		editstart.setAdapter(new PlaceHolder(SearchCarPool.this,
				R.layout.list_item_autocomplete));
		editend.setAdapter(new PlaceHolder(SearchCarPool.this,
				R.layout.list_item_autocomplete));

		btnsearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lc_start = editstart.getText().toString();
				lc_finish = editend.getText().toString();
				ll = (RelativeLayout) findViewById(R.id.ll);
				oslist = new ArrayList<HashMap<String, String>>();
				if (isOnline()) {
					GetListFilter getfilter = new GetListFilter();
					getfilter.execute();
				} else {
					Toast.makeText(getApplicationContext(), "Không có kết nối!", Toast.LENGTH_LONG).show();
				}
			}
		});	
	}
	private class GetListFilter extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchCarPool.this);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@SuppressWarnings("static-access")
		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			gll = new GetLatLng();
			Double sLat = gll.getLat(gll.getLocationInfo(lc_start));
			Double sLng = gll.getLng(gll.getLocationInfo(lc_start));
			Double dLat = gll.getLat(gll.getLocationInfo(lc_finish));
			Double dLng = gll.getLng(gll.getLocationInfo(lc_finish));
			log.info("latlong" + sLat + sLng + dLat + dLng);
			param.add(new BasicNameValuePair(TAG_LAT1, sLat + ""));
			param.add(new BasicNameValuePair(TAG_LONG1, sLng + ""));
			param.add(new BasicNameValuePair(TAG_LAT2, dLat + ""));
			param.add(new BasicNameValuePair(TAG_LONG2, dLng + ""));
			param.add(new BasicNameValuePair(TAG_VALUE, "1000"));
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, param);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);

					trips = jobject.getJSONArray(TAG_NUM_TRIP);
					for (int i = 0; i < trips.length(); i++) {
						JSONObject c = trips.getJSONObject(i);

						// Storing each json item in variable
						String pid = c.getString(TAG_PID);
						String user = c.getString(TAG_USER);
						String start = c.getString(TAG_LC_START);
						String end = c.getString(TAG_LC_FINISH);
						String time_start = c.getString(TAG_TIME_START);
						String time_end = c.getString(TAG_TIME_FINISH);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_PID, pid);
						map.put(TAG_USER, user);
						map.put(TAG_LC_START, start);
						map.put(TAG_LC_FINISH, end);
						map.put(TAG_TIME_START, time_start);
						map.put(TAG_TIME_FINISH, time_end);

						// adding HashList to ArrayList
						oslist.add(map);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
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
			ListAdapter adapter = new SimpleAdapter(SearchCarPool.this, oslist,
					R.layout.item_carpool, new String[] { TAG_PID, TAG_USER, TAG_LC_START,
							TAG_LC_FINISH, TAG_TIME_START, TAG_TIME_FINISH }, new int[] { R.id.txtidcarpool, R.id.txtuser,
							R.id.txtlc_start, R.id.txtlc_end,
							R.id.txttime_start, R.id.txttime_back });
			// updating listview
			setListAdapter(adapter);
		}
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		txtid = (TextView) v.findViewById(R.id.txtidcarpool);
		Intent i = new Intent(getApplicationContext(), DetailCarPool.class);
		i.putExtra("value", txtid.getText().toString());
		startActivity(i);
	}
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
