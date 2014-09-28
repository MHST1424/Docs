package vn.mhst24.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.control.Util;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements View.OnClickListener {

	private ProgressDialog pDialog;
	private Calendar myCalendar = Calendar.getInstance();
	// url create account
	private static String url = "http://viettungitpt.byethost7.com/dichung/create_account.php";
	// Json node name
	private static final String TAG_SUCCESS = "success";
	private EditText edit_user, edit_pass, edit_name, edit_email;
	private TextView txtbirthday;
	private CheckBox chk_accept;
	private RadioButton chk_male, chk_female;
	private Button btn_cancel, btn_register;
	static final int DATE_DIALOG_ID = 0;
	Util base;
	String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	String userPattern = "[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9]*";
	String passPattern = "[a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_]*";
	int count = 0;
	int con1 = 0, con2 = 0, con3 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// create and put edittext, checkbox, button in xml values
		txtbirthday = (TextView) findViewById(R.id.txtBirthday);
		edit_email = (EditText) findViewById(R.id.editEmail);
		edit_name = (EditText) findViewById(R.id.editName);
		edit_pass = (EditText) findViewById(R.id.editPass);
		edit_user = (EditText) findViewById(R.id.editUser);
		chk_male = (RadioButton) findViewById(R.id.radioGenderMale);
		chk_female = (RadioButton) findViewById(R.id.radioGenderFemale);
		chk_accept = (CheckBox) findViewById(R.id.chkAgree);
		btn_cancel = (Button) findViewById(R.id.btnCancel);
		btn_register = (Button) findViewById(R.id.btnRegister);

		chk_male.setOnClickListener(this);
		chk_female.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		txtbirthday.setText(getDate());
		txtbirthday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new DatePickerDialog(Register.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		edit_email.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String email = edit_email.getText().toString();
				if (!email.matches(emailPattern) || s.length() < 6 || s.length() > 50) {
					con1 = 0;
					chk_accept.setChecked(false);
					Toast.makeText(getApplicationContext(), "Invalid email!",
							Toast.LENGTH_SHORT).show();
				} else {
					con1 = 1;
				}
			}
		});
		edit_user.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String name = edit_user.getText().toString();
				if (!name.matches(userPattern) || s.length() < 6 || s.length() > 150) {
					con2 = 0;
					chk_accept.setChecked(false);
					Toast.makeText(getApplicationContext(),
							"Invalid user name!", Toast.LENGTH_SHORT).show();
				} else {
					con2 = 1;
				}
			}
		});
		edit_pass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String pass = edit_pass.getText().toString();
				if (!pass.matches(passPattern) || s.length() < 6 || s.length() > 30) {
					con3 = 0;
					chk_accept.setChecked(false);
					Toast.makeText(getApplicationContext(),
							"Invalid password!", Toast.LENGTH_SHORT).show();
				} else {
					con3 = 1;
				}
			}
		});

		chk_accept.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int result = con1 + con2 + con3;
				if (isChecked && result == 3) {
					btn_register.setEnabled(true);
				} else {
					btn_register.setEnabled(false);
				}
				
			}
		});
	}

	public void onClick(View v) {
		if (v == btn_register) {
			if (isOnline()) {
				Insert_Account insert = new Insert_Account();
				insert.execute();
			} else {
				Toast.makeText(getApplicationContext(), "Không có kết nối!", Toast.LENGTH_LONG).show();
			}
			
		}
		if (v == chk_female) {
			if (chk_female.isChecked()) {
				chk_male.setChecked(false);
			}
		}
		if (v == chk_male) {
			if (chk_male.isChecked()) {
				chk_female.setChecked(false);
			}
		}
		if (v == btn_cancel) {
			Intent i = new Intent(getApplicationContext(), Login.class);
			startActivity(i);
			finish();
		}
	}

	private class Insert_Account extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
			pDialog.setMessage("Đang kiểm tra \nVui lòng đợi...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... str) {
			ServiceHandler sh = new ServiceHandler();
			String user_name = edit_user.getText().toString();
			String name = edit_name.getText().toString();
			String pass = edit_pass.getText().toString();
			String birthday = txtbirthday.getText().toString();
			String email = edit_email.getText().toString();
			String gender;
			String message = null;
			if (chk_female.isChecked()) {
				gender = "false";
			} else {
				gender = "true";
			}
			// Buildings parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name", user_name));
			params.add(new BasicNameValuePair("pass", pass));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("birthday", birthday));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("gender", gender));

			// get Json string from url
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,
					params);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					int success = jobject.getInt(TAG_SUCCESS);
					if (success == 1) {
						Intent intent = new Intent(getApplicationContext(),
								Login.class);
						message = "Register success!";
						startActivity(intent);
						finish();
					} else {
						message = "Register error!";
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
			Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT)
					.show();
		}
	}

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String myFormat = "yyyy/MM/dd";
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			int date1 = Integer.parseInt(sdf.format(myCalendar.getTime()).replace("/", ""));
			int date2 = Integer.parseInt(getDate().replace("/", ""));
			if (date1 < date2) {
				updateDate();
			}
		}
	};

	private void updateDate() {
		String myFormat = "yyyy/MM/dd"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		txtbirthday.setText(sdf.format(myCalendar.getTime()));
	}
	public String getDate() {
		SimpleDateFormat datefm = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = new java.util.Date();
		return datefm.format(date);
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
