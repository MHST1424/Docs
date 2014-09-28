package vn.mhst24.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.entity.CarPool;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailCarPool extends Activity{
	
	
	TextView lc_start, lc_finish, time_start, time_back, owner, type_car, num_seat, cost, note;
	Button btnchoose, btnMap;
	// url to get all products list
	private static String url = "http://viettungitpt.byethost7.com/dichung/list_onecarpool_filter.php";
	// JSON Node names
	private static final String TAG_NUM_TRIP = "tripsinfos";
	
	private static final String TAG_PID = "pid";
	private static final String TAG_USER = "name";
	private static final String TAG_LC_START = "lc_start";
	private static final String TAG_LC_FINISH = "lc_finish";
	private static final String TAG_TIME_START = "time_start";
	private static final String TAG_TIME_END = "time_back";
	private static final String TAG_OWNER = "owner";
	private static final String TAG_TYPE_CAR = "type_car";
	private static final String TAG_COST = "cost";
	private static final String TAG_NUM_SEAT = "num_seat";
	private static final String TAG_NOTES = "notes";
	private static final String SPF_NAMEACC = "account";
	private static final String SPF_UID = "uid";
	String uid, start, end;
	// products JSONArray
	JSONArray arrcarpool = null;
	RelativeLayout ll;
	ListView list;
	List<CarPool> listcarpool;
	String id;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	CarPool carpool;
	static final Logger log = Logger.getLogger(ListCarPool.class);
	JSONArray tripsinfo = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_carpool);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    id = extras.getString("value");
		}
		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
		uid = loginPreferences.getString(SPF_UID, "");
		lc_start = (TextView) findViewById(R.id.textlc_start);
		lc_finish = (TextView) findViewById(R.id.textlc_finish);
		time_start = (TextView) findViewById(R.id.texttime_start);
		time_back = (TextView) findViewById(R.id.texttime_back);
		owner = (TextView) findViewById(R.id.textowner);
		type_car = (TextView) findViewById(R.id.texttype_car);
		num_seat = (TextView) findViewById(R.id.textnumber_seat);
		cost = (TextView) findViewById(R.id.textcost);
		note = (TextView) findViewById(R.id.textnotes);
		btnchoose = (Button) findViewById(R.id.btnchoose);
		btnMap = (Button) findViewById(R.id.btnmap);
		if (isOnline()) {
			Details detail = new Details();
			detail.execute();
		} else {
			Toast.makeText(getApplicationContext(), "Không có kết nối!", Toast.LENGTH_LONG).show();
		}
		
		btnchoose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		btnMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				start = listcarpool.get(0).getLcstart();
				end = listcarpool.get(0).getLcfinish();
				Log.e("start", start + end);
				Intent i = new Intent(getApplicationContext(), DetailMap.class);
				i.putExtra("start", start);
				i.putExtra("end", end);
				startActivity(i);
			}
		});
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), main.class);
		startActivity(i);
		finish();
	}
	private class Details extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailCarPool.this);
			pDialog.setMessage("Đang lấy dữ liệu ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("pid", id));
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, param);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					arrcarpool = jobject.getJSONArray(TAG_NUM_TRIP);
						JSONObject c = arrcarpool.getJSONObject(0);
						// Storing each json item in variable
						String pids = c.getString(TAG_PID);
						String name = c.getString(TAG_USER);
						String lc_starts = c.getString(TAG_LC_START);
						String lc_finishs = c.getString(TAG_LC_FINISH);
						String time_starts = c.getString(TAG_TIME_START);
						String time_backs = c.getString(TAG_TIME_END);
						//String owners = c.getString(TAG_OWNER);
						String type_cars = c.getString(TAG_TYPE_CAR);
						String num_seats = c.getString(TAG_NUM_SEAT);
						String costs = c.getString(TAG_COST);
						String notes = c.getString(TAG_NOTES);
						
						boolean owners = false;
						if(c.getString(TAG_OWNER).equals("0")) {
							owners = true;;
						} else {
							owners = false;
						}
						// creating new list carpool
						listcarpool = new ArrayList<>();
						carpool = new CarPool();
						
						// adding each child node to HashMap key => value
						carpool.setId(Integer.parseInt(pids));
						carpool.setUser(name);
						carpool.setLcstart(lc_starts);
						carpool.setLcfinish(lc_finishs);
						carpool.setTimestart(time_starts);
						carpool.setTimeback(time_backs);
						carpool.setOwner(owners);
						carpool.setTypecar(type_cars);
						carpool.setNumseat(Integer.parseInt(num_seats));
						carpool.setCost(Float.parseFloat(costs));
						carpool.setNote(notes);
						
						// adding HashList to ArrayList
						listcarpool.add(carpool);
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
			 * Updating parsed JSON data into details
			 * */
			lc_start.setText("Điểm đầu: " + listcarpool.get(0).getLcstart());
			lc_finish.setText("Điểm cuối: " + listcarpool.get(0).getLcfinish());
			time_start.setText("Thời gian đi: " + listcarpool.get(0).getTimestart());
			time_back.setText("Thời gian về: " + listcarpool.get(0).getTimeback());
			type_car.setText("Loại xe: " + listcarpool.get(0).getTypecar());
			num_seat.setText("Số ghế: " + listcarpool.get(0).getNumseat());
			cost.setText("Giá tiền: " + listcarpool.get(0).getCost());
			if (listcarpool.get(0).isOwner()) {
				owner.setText("Chủ xe: Yes");
			} else {
				owner.setText("Chủ xe: No");
			}
			note.setText("Ghi chú: " + listcarpool.get(0).getNote());
		}
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
