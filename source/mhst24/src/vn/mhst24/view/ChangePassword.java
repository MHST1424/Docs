package vn.mhst24.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.ServiceHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends Activity {
	private ProgressDialog pDialog;
	// url to get all products list
	private static String url = "http://viettungitpt.byethost7.com/dichung/change_pass_account.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	// JSON Node names
	private static final String TAG_UID = "uid";
	private static final String TAG_OLDPASS = "passold";
	private static final String TAG_NEWPASS = "passnew";
	private static final String SPF_NAMEACC = "account";
	private static final String SPF_UID = "uid";
	String uid;
	TextView txtuser;
	EditText edit_oldpass, edit_newpass, edit_againpass;
	Button btnchangepass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		txtuser = (TextView) findViewById(R.id.txtUser);
		edit_oldpass = (EditText) findViewById(R.id.edit_oldpass);
		edit_newpass = (EditText) findViewById(R.id.edit_newpass);
		edit_againpass = (EditText) findViewById(R.id.edit_againpass);
		btnchangepass = (Button) findViewById(R.id.btnchange_pass);
		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
		uid = loginPreferences.getString(SPF_UID, "");
		
		btnchangepass.setOnClickListener(new OnClickListener() {
			String pass1 = edit_newpass.getText().toString();
			String pass2 = edit_againpass.getText().toString();
			@Override
			public void onClick(View arg0) {
				if (pass1.equals(pass2)) {
					Change_Pass changepass = new Change_Pass();
					changepass.execute();
				} else {
					Toast.makeText(getApplicationContext(), "Please input again password!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private class Change_Pass extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ChangePassword.this);
			pDialog.setMessage("Đang kiểm tra \nVui lòng đợi...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... str) {
			ServiceHandler sh = new ServiceHandler();
			String oldpass = edit_oldpass.getText().toString();
			String newpass = edit_oldpass.getText().toString();
			String message = null;
			// Buildings parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_UID, uid));
			params.add(new BasicNameValuePair(TAG_OLDPASS, oldpass));
			params.add(new BasicNameValuePair(TAG_NEWPASS, newpass));

			// get Json string from url
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,
					params);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					int success = jobject.getInt(TAG_SUCCESS);
					if (success == 1) {
						message = "Change password success!";
						Intent intent = new Intent(getApplicationContext(),
								DetailUser.class);
						startActivity(intent);
					} else {
						message = "Change password fail!";
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
}
