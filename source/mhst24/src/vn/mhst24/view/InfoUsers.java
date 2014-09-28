package vn.mhst24.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.entity.InfoUser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoUsers extends Activity {
	TextView name, phone, gender;
	Button btnback;
	// url to get all products list
	private static String url = "http://viettungitpt.byethost7.com/dichung/list_account_filter.php";
	// JSON Node names
	private static final String TAG_ACCOUNT = "accounts";

	private static final String TAG_ID = "uid";
	private static final String TAG_NAME = "name";
	private static final String TAG_PHONE = "birthday";
	private static final String TAG_GENDER = "gender";
	// products JSONArray
	JSONArray account = null;
	RelativeLayout ll;
	ListView list;
	List<InfoUser> listuser;
	InfoUser f;
	JSONArray tripsinfo = null;
	String uid;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_user);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    uid = extras.getString("uid");
		}
		name = (TextView) findViewById(R.id.textName);
		phone = (TextView) findViewById(R.id.txtSodienThoai);
		gender = (TextView) findViewById(R.id.textGioitinh);
		btnback = (Button) findViewById(R.id.btnBack);
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), main.class);
				startActivity(i);
				finish();
			}
		});
		Details d = new Details();
		d.execute();

	}

	private class Details extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(InfoUsers.this);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair(TAG_ID, uid));
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh
					.makeServiceCall(url, ServiceHandler.POST, param);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					account = jobject.getJSONArray(TAG_ACCOUNT);
					for (int i = 0; i < account.length(); i++) {
						JSONObject c = account.getJSONObject(i);
						// Storing each json item in variable
						String name = c.getString(TAG_NAME);
						String phone = c.getString(TAG_PHONE);
						String gender = "Nam";
						if (c.getString(TAG_GENDER).equals("1")) {
							gender = "Nữ";
						}
						// creating new list user
						listuser = new ArrayList<>();
						f = new InfoUser();

						// adding each child node to HashMap key => value
						f.setGender(gender);
						f.setName(name);
						f.setPhone(phone);
						// adding HashList to ArrayList
						listuser.add(f);
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
			 * Updating parsed JSON data into details
			 * */
			name.setText("Họ tên: " + listuser.get(0).getName());
			phone.setText("Số điện thoại: " + listuser.get(0).getPhone());
			gender.setText("Giới tính: " + listuser.get(0).getGender());
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), main.class);
		startActivity(i);
		finish();
	}
}
