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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private ProgressDialog pDialog;
	private static String url = "http://viettungitpt.byethost7.com/dichung/check_account.php";

	private EditText edituser, editpass;
	private CheckBox chkremember;
	private Button btnLogin, btnRegister;
	boolean check;
	String user;
	String avataapp;
	String distance = "1000";
	JSONArray account = null;

	// Json nodes name
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ACCOUNT = "accounts";
	private static final String TAG_ID = "uid";
	//private static final String TAG_USER = "user_name";
	private static final String TAG_NAME = "name";
	private static final String TAG_VISIABLE= "visiable";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_BIRTHDAY = "birthday";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_NUMBERCMND = "number_cmnd";
	private static final String TAG_CMND = "cmnd";
	private static final String TAG_AVATA = "avata";
	private static final String TAG_VALID = "validate";
	//sharedpreferences account
	private static final String SPF_NAMEACC = "account";
	private static final String SPF_USER = "user_name";
	private static final String SPF_PASS = "password";
	private static final String SPF_UID = "uid";
	private static final String SPF_AVATA = "avata";
	private static final String SPF_NAME = "name";
	private static final String SPF_BIRTHDAY = "birthday";
	private static final String SPF_GENDER = "gender";
	private static final String SPF_EMAIL = "email";
	private static final String SPF_NUMBERCMND = "numbercmnd";
	private static final String SPF_VALID = "validate";
	private static final String SPF_VISIABLE= "visiable";
	private static final String SPF_CMND = "cmnd";
	private static final String SPF_REMEMBER = "remember";
	//sharedpreferences setting
	private static final String SPF_NAMESETTING = "setting";
	private static final String SPF_DISTANCE = "distance";
	private static final String SPF_AVATAAPP = "avata";
	private static final String SPF_MOTO = "moto";
	private static final String SPF_CAR = "car";
	private static final String SPF_TAXI = "taxi";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		edituser = (EditText) findViewById(R.id.editName);
		editpass = (EditText) findViewById(R.id.editPass);
		chkremember = (CheckBox) findViewById(R.id.chkBox);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
		SharedPreferences settingPreferences = getSharedPreferences(SPF_NAMESETTING, Context.MODE_PRIVATE);
		settingPreferences.edit().putString(SPF_AVATAAPP, avataapp).putString(SPF_DISTANCE, distance).putString(SPF_CAR, "10000").putString(SPF_MOTO, "4000").putString(SPF_TAXI, "1000").commit();
		check = loginPreferences.getBoolean(SPF_REMEMBER, false);
		user = loginPreferences.getString(SPF_USER, "");
		if (check) {
			Intent i = new Intent(getApplicationContext(), main.class);
			startActivity(i);
			finish();
		} else {
			edituser.setText(loginPreferences.getString(SPF_USER, ""));
			editpass.setText(loginPreferences.getString(SPF_PASS, ""));
		}
		// button login click
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (isOnline()) {
					Check_Account check = new Check_Account();
					check.execute();
				} else {
					Toast.makeText(getApplicationContext(), "Không có kết nối!", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		// button register click
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						Register.class);
				startActivity(intent);
			}
		});
	}

	private class Check_Account extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Đang kiểm tra \nVui lòng đợi...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... str) {
			ServiceHandler sh = new ServiceHandler();
			String user_name = edituser.getText().toString();
			String pass = editpass.getText().toString();
			String message = null;
			// Building parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name", user_name));
			params.add(new BasicNameValuePair("pass", pass));

			// get Json string from url
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, params);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					int success = jobject.getInt(TAG_SUCCESS);
					if (success == 1) {
						if (chkremember.isChecked()) {
							account = jobject.getJSONArray(TAG_ACCOUNT);
							JSONObject c = account.getJSONObject(0);
							// Storing each json item in variable
							String id = c.getString(TAG_ID);
							//String users = c.getString(TAG_USER);
							String avata = c.getString(TAG_AVATA);
							String name = c.getString(TAG_NAME);
							String email = c.getString(TAG_EMAIL);
							String birth = c.getString(TAG_BIRTHDAY);
							String cmnd = c.getString(TAG_CMND);
							String number_cmnd = c.getString(TAG_NUMBERCMND);
							String gender = c.getString(TAG_GENDER);
							String valid = c.getString(TAG_VALID);
							String visiable = c.getString(TAG_VISIABLE);
							SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
							loginPreferences.edit().putString(SPF_UID, id).putString(SPF_AVATA, avata).putString(SPF_NAME, name).putString(SPF_EMAIL, email).putString(SPF_BIRTHDAY, birth).putString(SPF_CMND, cmnd).putString(SPF_NUMBERCMND, number_cmnd).putString(SPF_GENDER, gender).putString(SPF_VALID, valid).putString(SPF_VISIABLE, visiable).putString(SPF_USER, user_name).putString(SPF_PASS, pass).putBoolean(SPF_REMEMBER, true).commit();
						} else {
							SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
							loginPreferences.edit().putString(SPF_USER, user_name).putString(SPF_PASS, pass).putBoolean(SPF_REMEMBER, false).commit();
						}
						message = "Login success!";
						Intent intent = new Intent(getApplicationContext(),
								main.class);
						intent.putExtra("user", user_name);
						startActivity(intent);
						finish();
					} else {
						message = "Sorry, Try Again!";
						SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
						loginPreferences.edit().putString(SPF_USER, user_name).putString(SPF_PASS, pass).putBoolean(SPF_REMEMBER, false).commit();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return message;
		}

		@Override
		protected void onPostExecute(String str) {
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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
