package vn.mhst24.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.customadapter.CarPoolCustomAdapter;
import vn.mhst24.entity.CarPool;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListCarPool extends ListFragment {
	// url to get all carpool from database
	private static String url = "http://viettungitpt.byethost7.com/dichung/get_list_carpool.php";
	// JSON Node names
	private static final String TAG_NUM_TRIP = "tripsinfos";

	private static final String TAG_PID = "pid";
	private static final String TAG_UID = "uid";
	private static final String TAG_USER = "name";
	private static final String TAG_AVATA = "avata";
	private static final String TAG_LC_START = "lc_start";
	private static final String TAG_LC_FINISH = "lc_finish";
	private static final String TAG_TIME_START = "time_start";
	private static final String TAG_TIME_END = "time_back";
	
	/*private static final String SPF_LISTCARPOOL = "ListCarPool";
	private static final String SPF_ID = "pid";
	private static final String SPF_USER = "name";
	private static final String SPF_AVATA = "avata";
	private static final String SPF_LC_START = "lc_start";
	private static final String SPF_LC_FINISH = "lc_finish";
	private static final String SPF_TIME_START = "time_start";
	private static final String SPF_TIME_END = "time_back";*/
	List<CarPool> list;
	// products JSONArray
	static final Logger log = Logger.getLogger(ListCarPool.class);
	JSONArray trips = null;
	ListView lv;
	CarPoolCustomAdapter adapter;
	TextView txtpid, txtuid;
	
	/**
	 * @return the list
	 */
	public List<CarPool> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<CarPool> list) {
		this.list = list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (isOnline()) {
			GetList getlist = new GetList();
			getlist.execute();
		} else {
			Toast.makeText(getActivity(), "Không có kết nối!", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		txtpid = (TextView) v.findViewById(R.id.txtidcarpool);
		txtuid = (TextView) v.findViewById(R.id.txtiduser);
		Intent i = new Intent(getActivity().getApplicationContext(), DetailCarPool.class);
		i.putExtra("pid", txtpid.getText().toString());
		i.putExtra("uid", txtuid.getText().toString());
		startActivity(i);
	}
	private class GetList extends AsyncTask<String, Void, List<CarPool>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<CarPool> doInBackground(String... str) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			list = new ArrayList<CarPool>();
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);

					trips = jobject.getJSONArray(TAG_NUM_TRIP);
					for (int i = 0; i < trips.length(); i++) {
						JSONObject c = trips.getJSONObject(i);

						// Storing each json item in variable
						String pid = c.getString(TAG_PID);
						String uid = c.getString(TAG_UID);
						String user = c.getString(TAG_USER);
						String avata = c.getString(TAG_AVATA);
						String start = c.getString(TAG_LC_START);
						String end = c.getString(TAG_LC_FINISH);
						String timestart = c.getString(TAG_TIME_START);
						String timestend = c.getString(TAG_TIME_END);

						CarPool car = new CarPool();
						
						car.setPid(Integer.parseInt(pid));
						car.setUid(Integer.parseInt(uid));
						car.setUser(user);
						car.setAvata(avata);
						car.setLcstart(start);
						car.setLcfinish(end);
						car.setTimestart(timestart);
						car.setTimeback(timestend);
						list.add(car);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return list;
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<CarPool> list) {
			adapter = new CarPoolCustomAdapter(getActivity(), R.layout.item_carpool, list);
			setListAdapter(adapter);
		}
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
