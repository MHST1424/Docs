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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailFriend extends Activity {

	TextView userid, nick, password, name, birthday, gender, email, validate;
	// url to get all products list
	private static String url = "http://viettungitpt.byethost7.com/dichung/list_account_filter.php";
	// JSON Node names
	private static final String TAG_ACCOUNT = "accounts";

	private static final String TAG_ID = "uid";
	private static final String TAG_USER = "user_name";
	private static final String TAG_NAME = "name";
	private static final String TAG_AVATA = "avata";
	private static final String TAG_BIRTHDAY = "birthday";
	private static final String TAG_GENDER = "gender";
	// products JSONArray
	JSONArray account = null;
	RelativeLayout ll;
	ListView list;
	List<InfoUser> listfriend;
	InfoUser f;
	JSONArray tripsinfo = null;
	String uid;

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_friend);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    uid = extras.getString("uid");
		}
		userid = (TextView) findViewById(R.id.textuserid);
		nick = (TextView) findViewById(R.id.textNick);
		name = (TextView) findViewById(R.id.textName);
		birthday = (TextView) findViewById(R.id.textNgaysinhdk);
		gender = (TextView) findViewById(R.id.textGioitinh);
		Details d = new Details();
		d.execute();

	}

	private class Details extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailFriend.this);
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
						String id = c.getString(TAG_ID);
						String users = c.getString(TAG_USER);
						String name = c.getString(TAG_NAME);
						String avata = c.getString(TAG_AVATA);
						String birth = c.getString(TAG_BIRTHDAY);
						String gender = "Nam";
						if (c.getString(TAG_GENDER).equals("1")) {
							gender = "Nữ";
						}
						// creating new list user
						listfriend = new ArrayList<>();
						f = new InfoUser();

						// adding each child node to HashMap key => value
						f.setId(id);
						f.setAvata(avata);
						f.setBirthday(birth);
						f.setGender(gender);
						f.setNick(users);
						f.setName(name);
						f.setNick(users);
						// adding HashList to ArrayList
						listfriend.add(f);
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
			nick.setText("Tên nick: " + listfriend.get(0).getNick());
			name.setText("Họ tên: " + listfriend.get(0).getName());
			birthday.setText("Ngày sinh: " + listfriend.get(0).getBirthday());
			gender.setText("Giới tính: " + listfriend.get(0).getGender());
		}
	}
}
