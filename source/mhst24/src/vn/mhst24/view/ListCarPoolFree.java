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

public class ListCarPoolFree extends ListFragment{
	// url to get all carpool free from database
		private static String url = "http://viettungitpt.byethost7.com/dichung/list_carpool_free.php";
		// JSON Node names
		private static final String TAG_NUM_TRIP = "tripsinfos";

		private static final String TAG_ID = "pid";
		private static final String TAG_USER = "name";
		private static final String TAG_AVATA = "avata";
		private static final String TAG_LC_START = "lc_start";
		private static final String TAG_LC_FINISH = "lc_finish";
		private static final String TAG_TIME_START = "time_start";
		private static final String TAG_TIME_END = "time_back";
		List<CarPool> list;
		// products JSONArray
		static final Logger log = Logger.getLogger(ListCarPool.class);
		JSONArray trips = null;
		ListView lv;
		CarPoolCustomAdapter adapter;
		TextView txtid;
		@Override
		public void onCreate(Bundle si) {
			super.onCreate(si);
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
			txtid = (TextView) v.findViewById(R.id.txtidcarpool);
			Intent i = new Intent(getActivity().getApplicationContext(), DetailCarPool.class);
			i.putExtra("value", txtid.getText().toString());
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
				// log.info("action:" + jsonStr);
				if (jsonStr != null) {
					try {
						JSONObject jobject = new JSONObject(jsonStr);

						trips = jobject.getJSONArray(TAG_NUM_TRIP);
						for (int i = 0; i < trips.length(); i++) {
							JSONObject c = trips.getJSONObject(i);

							// Storing each json item in variable
							String id = c.getString(TAG_ID);
							String user = c.getString(TAG_USER);
							String avata = c.getString(TAG_AVATA);
							String start = c.getString(TAG_LC_START);
							String end = c.getString(TAG_LC_FINISH);
							String timestart = c.getString(TAG_TIME_START);
							String timestend = c.getString(TAG_TIME_END);

							//list.add(new CarPool(i, start, end, timestart, timestend, false, null, 0, 1, null));
							CarPool car = new CarPool();
							
							car.setId(Integer.parseInt(id));
							car.setUser(user);
							car.setAvata(avata);
							car.setLcstart(start);
							car.setLcfinish(end);
							car.setTimestart(timestart);
							car.setTimeback(timestend);
							list.add(car);
						}
						//adapter = new CarPoolCustomAdapter(getActivity(), R.layout.item_carpool, list);
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
				if (list.isEmpty()) {
					Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_LONG).show();
				}
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
