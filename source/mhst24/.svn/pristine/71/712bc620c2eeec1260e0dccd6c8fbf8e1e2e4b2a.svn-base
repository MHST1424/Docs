package vn.mhst24.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.entity.CarPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListMessage extends ListFragment{
	
	// url to get all products list
			private static String url = "http://viettungitpt.byethost7.com/dichung/get_list_CarPool.php";
			// JSON Node names
			private static final String TAG_NUM_TRIP = "tripsinfos";
			
			private static final String TAG_LC_USER = "user_name";
			private static final String TAG_LC_START = "lc_start";
			private static final String TAG_LC_FINISH = "lc_finish";
			private static final String TAG_TIME_START = "time_start";
			private static final String TAG_TIME_END = "time_back";

			// products JSONArray
			JSONArray trips = null;
			//CarPoolCustomAdapter adapter;
			Button detail, vote;

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				GetList getlist = new GetList();
				getlist.execute();
				
				return super.onCreateView(inflater, container, savedInstanceState);
			}
			
			private class GetList extends AsyncTask<String, Void, SimpleAdapter> {
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
				}

				@Override
				protected SimpleAdapter doInBackground(String... str) {
					// Creating service handler class instance
					ServiceHandler sh = new ServiceHandler();
					List<CarPool> list = new ArrayList<CarPool>();
					// Making a request to url and getting response
					String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
					//log.info("action:" + jsonStr);
					if (jsonStr != null) {
						try {
							JSONObject jobject = new JSONObject(jsonStr);

							trips = jobject.getJSONArray(TAG_NUM_TRIP);
							for (int i = 0; i < trips.length(); i++) {
								JSONObject c = trips.getJSONObject(i);

								// Storing each json item in variable
								String start = c.getString(TAG_LC_START);
								String end = c.getString(TAG_LC_FINISH);
								String timestart = c.getString(TAG_TIME_START);
								String timestend = c.getString(TAG_TIME_END);

								CarPool car = new CarPool();

								car.setLcstart(start);
								car.setLcfinish(end);
								car.setTimestart(timestart);
								car.setTimeback(timestend);
								list.add(car);

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
						for (CarPool c : list) {
							//log.info("listcarpool1:" + list);
							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("lc_start", c.getLcstart());
							hm.put("lc_finish", c.getLcfinish());
							hm.put("time_Start", c.getTimestart());
							hm.put("time_back", c.getTimeback());
							aList.add(hm);
						}
						
						// Keys used in Hashmap
						String[] from = { "lc_start", "lc_finish", "time_Start", "time_back" };

						// Ids of views in listview_layout
						int[] to = { R.id.txtlc_start, R.id.txtlc_end, R.id.txttime_start,
								R.id.txttime_back };

						// Instantiating an adapter to store each items
						// R.layout.listview_layout defines the layout of each item
						SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.item_carpool, from, to);
						//adapter = new CarPoolCustomAdapter(getActivity(), R.layout.item_carpool, list);
						return adapter;
					} else {
						Log.e("ServiceHandler", "Couldn't get any data from the url");
					}
					return null;
				}
				@Override
		        protected void onPostExecute(SimpleAdapter adapter) {
					setListAdapter(adapter);
		        }
			}
			
			@Override
			public void onListItemClick(ListView l, View v, int position, long id) {
				super.onListItemClick(l, v, position, id);
				Toast.makeText(getActivity().getBaseContext(), "Click Items", Toast.LENGTH_SHORT).show();
			
			}
}
