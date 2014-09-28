package vn.mhst24.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.Base64;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.entity.User;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class EditUser extends Activity {
	//private ProgressDialog pDialog;
	// url to edit one user
	private static String url = "http://viettungitpt.byethost7.com/dichung/edit_account.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	private static final String TAG_ID = "uid";
	private static final String TAG_USER = "user_name";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_BIRTHDAY = "birthday";
	private static final String TAG_AVATA = "avata";
	private static final String TAG_VISIABLE = "visiable";

	private static final String SPF_NAMEACC = "account";
	private static final String SPF_USER = "user_name";
	private static final String SPF_UID = "uid";
	private static final String SPF_AVATA = "avata";
	private static final String SPF_NAME = "name";
	private static final String SPF_BIRTHDAY = "birthday";
	private static final String SPF_EMAIL = "email";
	private static final String SPF_VISIABLE = "visiable";
	
	String uid;
	JSONArray account = null;
	RelativeLayout ll;
	ListView list;
	User user;
	List<User> listuser;
	private EditText edit_user, edit_name, edit_birth, edit_email;
	private Button btn_cancel, btn_edit, btn_changeavata, btn_visiable;
	private ImageView imgavata;
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;
	String encodedImage;
	String value_Btnvisiable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edituser);

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// create and put edittext, checkbox, button in xml values
		edit_birth = (EditText) findViewById(R.id.editBirthday);
		edit_email = (EditText) findViewById(R.id.editEmail);
		edit_name = (EditText) findViewById(R.id.editName);
		edit_user = (EditText) findViewById(R.id.editUser);
		btn_cancel = (Button) findViewById(R.id.btnCancel);
		btn_edit = (Button) findViewById(R.id.btnEdit);
		btn_changeavata = (Button) findViewById(R.id.btnChangeAvata);
		btn_visiable = (Button) findViewById(R.id.btnchangevisiavle);
		imgavata = (ImageView) findViewById(R.id.imgavata);
		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
		uid = loginPreferences.getString(SPF_UID, "");
		byte[] decodedString = null;
		try {
			decodedString = Base64.decode(loginPreferences.getString(SPF_AVATA, ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		imgavata.setImageBitmap(decodedByte);
		imgavata.setImageBitmap(decodedByte);
		edit_user.setText(loginPreferences.getString(SPF_USER, ""));
		edit_name.setText(loginPreferences.getString(SPF_NAME, ""));
		edit_birth.setText(loginPreferences.getString(SPF_BIRTHDAY, ""));
		edit_email.setText(loginPreferences.getString(SPF_EMAIL, ""));		
		if (loginPreferences.getString(SPF_VISIABLE, "").equals("0")) {
			btn_visiable.setText("Show");
		} else {
			btn_visiable.setText("Hide");
		}
		
		edit_birth.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (isOnline()) {
					EditAccount edit = new EditAccount();
					edit.execute();
				} else {
					Toast.makeText(getApplicationContext(), "No connection!", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		btn_changeavata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				open();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), main.class);
				startActivity(i);
				finish();
			}
		});
		btn_visiable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (btn_visiable.getText().toString().equals("Show")) {
					value_Btnvisiable = "1";
				} else {
					value_Btnvisiable = "0";
				}
			}
		});
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), DetailUser.class);
		startActivity(i);
		finish();
	}
	public void open() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bp = (Bitmap) data.getExtras().get("data");
		imgavata.setImageBitmap(bp);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bp.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		encodedImage = Base64.encodeBytes(b);
	}

	private class EditAccount extends AsyncTask<String, Void, String> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditUser.this);
			pDialog.setMessage("Đang lấy dữ liệu ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... param) {
			ServiceHandler sh = new ServiceHandler();
			//String uid = edit_user.getText().toString();
			String user_name = edit_user.getText().toString();
			String name = edit_name.getText().toString();
			String birthday = edit_birth.getText().toString();
			String email = edit_email.getText().toString();
			String avata = encodedImage;
			String visiables = value_Btnvisiable;
			// Buildings parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_ID, uid));
			params.add(new BasicNameValuePair(TAG_USER, user_name));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_BIRTHDAY, birthday));
			params.add(new BasicNameValuePair(TAG_EMAIL, email));
			params.add(new BasicNameValuePair(TAG_AVATA, avata));
			params.add(new BasicNameValuePair(TAG_VISIABLE, visiables));
			String message = null;
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,
					params);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					int success = jobject.getInt(TAG_SUCCESS);
					if (success == 1) {
						message = "Cập nhật tài khoản thành công!";
						SharedPreferences editinfoPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
						editinfoPreferences.edit().putString(SPF_AVATA, avata).putString(SPF_NAME, name).putString(SPF_EMAIL, email).putString(SPF_BIRTHDAY, birthday).putString(SPF_USER, user_name).commit();
						Intent intent = new Intent(getApplicationContext(), main.class);
						startActivity(intent);
						finish();
					} else {
						message = "Cập nhật lỗi!";
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
	private DatePickerDialog.OnDateSetListener mDateSetListener =
		    new DatePickerDialog.OnDateSetListener() {

		        public void onDateSet(DatePicker view, int year, 
		                              int monthOfYear, int dayOfMonth) {
		            mYear = year;
		            mMonth = monthOfYear;
		            mDay = dayOfMonth;
		            updateDate();
		        }
		    };
		    @Override
		    protected Dialog onCreateDialog(int id) {
		        switch (id) {
		        case DATE_DIALOG_ID:
		            return new DatePickerDialog(this,
		                        mDateSetListener,
		                        mYear, mMonth, mDay);
		        }
		        return null;
		    }
	@SuppressWarnings("deprecation")
	private void updateDate() {
		edit_birth.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mDay).append("/").append(mMonth + 1).append("/")
				.append(mYear).append(" "));
		showDialog(DATE_DIALOG_ID);
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
