package vn.mhst24.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.customadapter.FriendCustomAdapter;
import vn.mhst24.entity.Friend;
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

public class ListFriend extends ListFragment {
	
	// url to get all Friend from database
		private static String url = "http://viettungitpt.byethost7.com/dichung/list_friend.php";
		// JSON Node names
		private static final String TAG_FRIEND = "friends";

		private static final String TAG_ID = "uid";
		private static final String TAG_USER = "user_name";
		private static final String TAG_AVATA = "avata";
		List<Friend> list;
		// products JSONArray
		JSONArray trips = null;
		ListView lv;
		FriendCustomAdapter adapter;
		TextView txtid;

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
			txtid = (TextView) v.findViewById(R.id.iduser);
			Intent i = new Intent(getActivity().getApplicationContext(), DetailUser.class);
			i.putExtra("value", txtid.getText().toString());
			startActivity(i);
		}
		private class GetList extends AsyncTask<String, Void, List<Friend>> {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected List<Friend> doInBackground(String... str) {
				// Creating service handler class instance
				ServiceHandler sh = new ServiceHandler();
				list = new ArrayList<Friend>();
				// Making a request to url and getting response
				String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
				// log.info("action:" + jsonStr);
				if (jsonStr != null) {
					try {
						JSONObject jobject = new JSONObject(jsonStr);

						trips = jobject.getJSONArray(TAG_FRIEND);
						for (int i = 0; i < trips.length(); i++) {
							JSONObject c = trips.getJSONObject(i);

							// Storing each json item in variable
							String user = c.getString(TAG_USER);
							String id = c.getString(TAG_ID);
							String avata = c.getString(TAG_AVATA);

							Friend f = new Friend();
							f.setId(id);
							f.setAvata(avata);
							f.setUser_name(user);
							list.add(f);
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
			protected void onPostExecute(List<Friend> list) {
				adapter = new FriendCustomAdapter(getActivity(), R.layout.item_friend, list);
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
