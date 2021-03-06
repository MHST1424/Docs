package vn.mhst24.view;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.PlaceHolder;
import vn.mhst24.control.ServiceHandler;
import vn.mhst24.json.GetLatLng;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddCarPool extends Activity {

	private TextView txttime, txtdate, txttime1, txtdate1;
	private Calendar myCalendar = Calendar.getInstance();
	private AutoCompleteTextView autotextviewdi;
	private AutoCompleteTextView autotextviewden;
	private Button btnadd;
	private EditText soghe, giatien;
	private RadioButton chkowner, chkgues, chkcar, chkmoto, chktaxi;
	private ProgressDialog pDialog;
	private ImageView img_owner, img_user, img_motor, img_car, img_taxi;
	String name;
	boolean chkClickdate1 = false;
	String id;
	InputStream is = null;
	String result = null;
	String line = null;
	String uid = null;
	String moneycar, moneytaxi, moneymoto;
	int code;
	GetLatLng gll;
	// url create account
	private static String url = "http://viettungitpt.byethost7.com/dichung/create_tripsinfo.php";
	private static String urllatlong = "http://viettungitpt.byethost7.com/dichung/create_latlong.php";
	// Json node name
	private static final String TAG_SUCCESS = "success";
	private static final String SPF_NAMEACC = "account";
	private static final String SPF_UID = "uid";
	private static final String SPF_NAMESETTING = "setting";
	private static final String SPF_MOTO = "moto";
	private static final String SPF_CAR = "car";
	private static final String SPF_TAXI = "taxi";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_carpool);
		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC,
				Context.MODE_PRIVATE);
		uid = loginPreferences.getString(SPF_UID, "");
		SharedPreferences settingPreferences = getSharedPreferences(
				SPF_NAMESETTING, Context.MODE_PRIVATE);
		moneycar = settingPreferences.getString(SPF_CAR, "");
		moneytaxi = settingPreferences.getString(SPF_TAXI, "");
		moneymoto = settingPreferences.getString(SPF_MOTO, "");
		// get Time current
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		final String currentDateandTime = sdf.format(new Date());
		//
		txtdate = (TextView) findViewById(R.id.txtNgayDi);
		txttime = (TextView) findViewById(R.id.txtGioDi);
		txtdate1 = (TextView) findViewById(R.id.txtNgayVe);
		txttime1 = (TextView) findViewById(R.id.txtGioVe);
		chkowner = (RadioButton) findViewById(R.id.chkOwner);
		chkgues = (RadioButton) findViewById(R.id.chkGuest);
		chkmoto = (RadioButton) findViewById(R.id.chkMoto);
		chktaxi = (RadioButton) findViewById(R.id.chkTaxi);
		chkcar = (RadioButton) findViewById(R.id.chkCar);
		soghe = (EditText) findViewById(R.id.editSoGhe);
		giatien = (EditText) findViewById(R.id.editTien);
		txttime.setText(getTime());
		txtdate.setText(getDate());
		txttime1.setText("--:--");
		txtdate1.setText("--/--/----");
		autotextviewdi = (AutoCompleteTextView) findViewById(R.id.editD1);
		autotextviewden = (AutoCompleteTextView) findViewById(R.id.editD2);
		btnadd = (Button) findViewById(R.id.btnDangTin);

		//
		img_owner = (ImageView) findViewById(R.id.img_owner);
		img_user = (ImageView) findViewById(R.id.img_user);
		img_motor = (ImageView) findViewById(R.id.img_motor);
		img_car = (ImageView) findViewById(R.id.img_car);
		img_taxi = (ImageView) findViewById(R.id.img_taxi);
		img_owner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chkowner.setChecked(true);

			}
		});
		img_user.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chkgues.setChecked(true);

			}
		});
		img_motor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chkmoto.setChecked(true);

			}
		});
		img_car.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chkcar.setChecked(true);

			}
		});
		img_taxi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chktaxi.setChecked(true);

			}
		});

		chkowner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (chkowner.isChecked()) {
					chkgues.setChecked(false);
					chkmoto.setEnabled(true);
					chkcar.setEnabled(true);
					chktaxi.setEnabled(true);
					giatien.setEnabled(true);
				}
			}
		});
		chkgues.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (chkgues.isChecked()) {
					chkowner.setChecked(false);
					chkmoto.setEnabled(false);
					chkcar.setEnabled(false);
					chktaxi.setEnabled(false);
					giatien.setEnabled(false);
				}
			}
		});
		chkcar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (chkcar.isChecked()) {
					chkmoto.setChecked(false);
					chktaxi.setChecked(false);
				}
			}
		});
		chktaxi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (chktaxi.isChecked()) {
					chkmoto.setChecked(false);
					chkcar.setChecked(false);
				}
			}
		});
		chkmoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (chkmoto.isChecked()) {
					chkcar.setChecked(false);
					chktaxi.setChecked(false);
				}
			}
		});

		autotextviewdi.setAdapter(new PlaceHolder(AddCarPool.this,
				R.layout.list_item_autocomplete));
		autotextviewden.setAdapter(new PlaceHolder(AddCarPool.this,
				R.layout.list_item_autocomplete));
		btnadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy", Locale.getDefault());
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				Calendar calendar3 = Calendar.getInstance();

				try {
					calendar1.setTime(dateFormat.parse(currentDateandTime));

					calendar2.setTime(dateFormat.parse(txtdate.getText()
							.toString()));
					calendar3.setTime(dateFormat.parse(txtdate1.getText()
							.toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (autotextviewdi.getText().toString().length() < 10) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập điểm đi", Toast.LENGTH_SHORT).show();
				} else if (autotextviewden.getText().toString().length() < 10) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập điểm đến", Toast.LENGTH_SHORT)
							.show();

				} else if (calendar1.compareTo(calendar2) > 0) {

					Toast.makeText(getApplicationContext(),
							"Thời gian đi không hợp lệ", Toast.LENGTH_SHORT)
							.show();
				} else if (calendar2.compareTo(calendar3) > 0
						& chkClickdate1 == true) {
					Toast.makeText(getApplicationContext(),
							"Thời gian về không hợp lệ", Toast.LENGTH_SHORT)
							.show();

				}

				else if (chkowner.isChecked() == false
						& chkgues.isChecked() == false) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng chọn phương tiện", Toast.LENGTH_SHORT)
							.show();

				} else if (chkowner.isChecked()) {
					if (chkcar.isChecked() == false
							& chkmoto.isChecked() == false
							& chktaxi.isChecked() == false)
						Toast.makeText(getApplicationContext(),
								"Vui lòng chọn phương tiện", Toast.LENGTH_SHORT)
								.show();
					else if (soghe.getText().toString().length() < 1)
						Toast.makeText(getApplicationContext(),
								"Vui lòng chọn số ghế", Toast.LENGTH_SHORT)
								.show();
					else if (Integer.parseInt(soghe.getText().toString()) > 220
							|| Integer.parseInt(soghe.getText().toString()) < 1)
						Toast.makeText(getApplicationContext(),
								"Số ghế không đúng", Toast.LENGTH_SHORT).show();
					else if (giatien.getText().toString().length() < 1)
						Toast.makeText(getApplicationContext(),
								"Vui lòng nhập giá tiền", Toast.LENGTH_SHORT)
								.show();
					else {
						Insert_Trips insert = new Insert_Trips();
						insert.execute();
					}

				} else if (chkgues.isChecked()) {
					if (soghe.getText().toString().length() < 1)
						Toast.makeText(getApplicationContext(),
								"Vui lòng số ghế", Toast.LENGTH_SHORT).show();
					else if (Integer.parseInt(soghe.getText().toString()) > 220
							|| Integer.parseInt(soghe.getText().toString()) < 1)
						Toast.makeText(getApplicationContext(),
								"Số ghế không đúng", Toast.LENGTH_SHORT).show();
					else {
						Insert_Trips insert = new Insert_Trips();
						insert.execute();
					}
				}

			}
		});

		txtdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(AddCarPool.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		txttime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new TimePickerDialog(AddCarPool.this, time, myCalendar
						.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),
						true).show();
			}
		});
		txtdate1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(AddCarPool.this, date1, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				chkClickdate1 = true;
			}
		});
		txttime1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new TimePickerDialog(AddCarPool.this, time1, myCalendar
						.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),
						true).show();
			}
		});
	}

	public String getDate() {
		SimpleDateFormat datefm = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = new java.util.Date();
		return datefm.format(date);
	}

	public String getTime() {
		SimpleDateFormat timefm = new SimpleDateFormat("HH:mm");
		java.util.Date time = new java.util.Date();
		return timefm.format(time);
	}

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			updateDate();
		}
	};
	DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateDate1();
		}
	};

	private void updateDate() {
		String myFormat = "dd/MM/yyyy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		txtdate.setText(sdf.format(myCalendar.getTime()));
	}

	private void updateDate1() {
		String myFormat = "dd/MM/yyyy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		txtdate1.setText(sdf.format(myCalendar.getTime()));
	}

	TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			myCalendar.set(Calendar.HOUR, hourOfDay);
			myCalendar.set(Calendar.MINUTE, minute);
			updateTime();
		}
	};
	TimePickerDialog.OnTimeSetListener time1 = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			myCalendar.set(Calendar.HOUR, hourOfDay);
			myCalendar.set(Calendar.MINUTE, minute);
			updateTime1();
		}
	};

	private void updateTime() {
		String myFormat = "HH:mm";
		SimpleDateFormat timefm = new SimpleDateFormat(myFormat, Locale.US);
		txttime.setText(timefm.format(myCalendar.getTime()));
	}

	private void updateTime1() {
		String myFormat = "HH:mm";
		SimpleDateFormat timefm = new SimpleDateFormat(myFormat, Locale.US);
		txttime1.setText(timefm.format(myCalendar.getTime()));
	}

	private class Insert_Trips extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AddCarPool.this);
			pDialog.setMessage("Đang kiểm tra \nVui lòng đợi...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@SuppressWarnings("static-access")
		@Override
		protected String doInBackground(String... str) {
			String message = null;
			ServiceHandler sh = new ServiceHandler();
			String lc_start = autotextviewdi.getText().toString();
			String lc_finish = autotextviewden.getText().toString();
			String time_start = txttime.getText().toString() + "-"
					+ txtdate.getText().toString();
			String owner = "true";
			String type = "moto";
			if (chkcar.isChecked()) {
				type = "car";
			} else {
				if (chktaxi.isChecked()) {
					type = "taxi";
				} else {
					type = "moto";
				}
			}
			if (chkowner.isChecked()) {
				owner = "owner";
			} else {
				owner = "gues";
			}
			String num_seat = soghe.getText().toString();
			String cost = giatien.getText().toString();
			// get lat,lng
			gll = new GetLatLng();
			Double sLat = gll.getLat(gll.getLocationInfo(lc_start));
			Double sLng = gll.getLng(gll.getLocationInfo(lc_start));
			Double dLat = gll.getLat(gll.getLocationInfo(lc_finish));
			Double dLng = gll.getLng(gll.getLocationInfo(lc_finish));
			String latlng = sLat + "," + sLng + "-" + dLat + "," + dLng;
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("lat_long", latlng));
			param.add(new BasicNameValuePair("lat_start", sLat + ""));
			param.add(new BasicNameValuePair("long_start", sLng + ""));
			param.add(new BasicNameValuePair("lat_end", dLat + ""));
			param.add(new BasicNameValuePair("long_end", dLng + ""));
			// Buildings parameters
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid", uid));
			params.add(new BasicNameValuePair("owner", owner));
			params.add(new BasicNameValuePair("type_car", type));
			params.add(new BasicNameValuePair("lat_long", latlng));
			params.add(new BasicNameValuePair("lc_start", lc_start));
			params.add(new BasicNameValuePair("lc_finish", lc_finish));
			params.add(new BasicNameValuePair("time_start", time_start));
			params.add(new BasicNameValuePair("owner", owner));
			params.add(new BasicNameValuePair("num_seat", num_seat));
			params.add(new BasicNameValuePair("cost", cost));
			// params.add(new BasicNameValuePair("notes:, notes));
			// get Json string from url
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,
					params);
			String jsonStr1 = sh.makeServiceCall(urllatlong,
					ServiceHandler.POST, param);
			if ((jsonStr != null) && (jsonStr1 != null)) {
				try {
					JSONObject jobject = new JSONObject(jsonStr);
					int success = jobject.getInt(TAG_SUCCESS);
					JSONObject jobject1 = new JSONObject(jsonStr);
					int success1 = jobject1.getInt(TAG_SUCCESS);
					if ((success == 1) && (success1 == 1)) {
						message = "Thêm chuyến đi thành công!";
						Intent intent = new Intent(getApplicationContext(),
								main.class);
						startActivity(intent);
						finish();
					} else {
						message = "Thêm chuyến đi thất bại!";
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

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}
