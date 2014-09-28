package vn.mhst24.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ValidateAccount extends Activity {
	private ProgressDialog pDialog;
	// url to validate cmnd
	private static String url = "http://viettungitpt.byethost7.com/dichung/validate_account.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	private static final String TAG_ID = "uid";
	private static final String TAG_CMND = "cmnd";
	private static final String TAG_NUMBERCMND = "number_cmnd";
	private static final String SPF_NAMEACC = "account";
	private static final String SPF_UID = "uid";
	private static final String SPF_NUMBERCMND = "numbercmnd";
	private static final String SPF_CMND = "cmnd";
	String uid;
	// products JSONArray
	JSONArray account = null;
	RelativeLayout ll;
	ListView list;
	User users;
	List<User> listuser;
	ImageView imgFavorite;
	EditText number_cmnd;
	Button btncapture, btnvalidate;
	String encodedImage;

	/**
	 * @return the encodedImage
	 */
	public String getEncodedImage() {
		return encodedImage;
	}

	/**
	 * @param encodedImage
	 *            the encodedImage to set
	 */
	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		imgFavorite = (ImageView) findViewById(R.id.imgcmnd);
		btncapture = (Button) findViewById(R.id.btn_capture);
		btnvalidate = (Button) findViewById(R.id.btn_validate);
		number_cmnd = (EditText) findViewById(R.id.txtcmnd);
		
		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
		uid = loginPreferences.getString(SPF_UID, "");
		
		number_cmnd.setText(loginPreferences.getString(SPF_NUMBERCMND, ""));
		byte[] decodedString = null;
		try {
			decodedString = Base64.decode(loginPreferences.getString(SPF_CMND, ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		imgFavorite.setImageBitmap(decodedByte);
		
		btncapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				open();
			}
		});
		btnvalidate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isOnline()) {
					Validate valid = new Validate();
					valid.execute();
				} else {
					Toast.makeText(getApplicationContext(), "Không có kết nối!", Toast.LENGTH_LONG).show();
				}
				
			}
		});
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
		imgFavorite.setImageBitmap(bp);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bp.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		// String encodedImage = Base64.encode(b, Base64.DEFAULT);
		encodedImage = Base64.encodeBytes(b);
	}

	private class Validate extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ValidateAccount.this);
			pDialog.setMessage("Đang kiểm tra \nVui lòng đợi...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... str) {
			ServiceHandler sh = new ServiceHandler();
			String numbercmnd = number_cmnd.getText().toString();
			// Buildings parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_ID, uid));
			params.add(new BasicNameValuePair(TAG_NUMBERCMND, numbercmnd));
			params.add(new BasicNameValuePair(TAG_CMND, encodedImage));
			String message = null;
			// get Json string from url
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,
					params);
			if (jsonStr != null) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					int success = jobject.getInt(TAG_SUCCESS);
					if (success == 1) {
						message = "Valid success!";
						Intent intent = new Intent(getApplicationContext(),
								DetailUser.class);
						startActivity(intent);
						// finish();
					} else {
						message = "Valid fail!";
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
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
