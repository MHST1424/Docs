package vn.mhst24.view;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import tungnv.haui.mhst24.R;
import vn.mhst24.entity.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailUser extends Activity {

	TextView nick, password, name, birthday, gender, email, cmnd, validate, visiable;
	Button btnvalid, btnedit;

	private static final String SPF_NAMEACC = "account";
	private static final String SPF_USER = "user_name";
	private static final String SPF_UID = "uid";
	//private static final String SPF_AVATA = "avata";
	private static final String SPF_NAME = "name";
	private static final String SPF_BIRTHDAY = "birthday";
	private static final String SPF_GENDER = "gender";
	private static final String SPF_EMAIL = "email";
	private static final String SPF_NUMBERCMND = "numbercmnd";
	private static final String SPF_VALID = "validate";
	private static final String SPF_VISIABLE = "visiable";

	
	String uid, pid;
	// products JSONArray
	JSONArray account = null;
	RelativeLayout ll;
	ListView list;
	List<User> listuser;
	/**
	 * @return the listuser
	 */
	public List<User> getListuser() {
		return listuser;
	}

	/**
	 * @param listuser the listuser to set
	 */
	public void setListuser(List<User> listuser) {
		this.listuser = listuser;
	}

	User c_user;
	static final Logger log = Logger.getLogger(ListCarPool.class);
	JSONArray tripsinfo = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_account);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    pid = extras.getString("value");
		}
		visiable = (TextView) findViewById(R.id.textVisiable);
		nick = (TextView) findViewById(R.id.textNick);
		password = (TextView) findViewById(R.id.textMatkhaudk);
		name = (TextView) findViewById(R.id.textName);
		birthday = (TextView) findViewById(R.id.textNgaysinhdk);
		gender = (TextView) findViewById(R.id.textGioitinh);
		email = (TextView) findViewById(R.id.textEmail);
		cmnd = (TextView) findViewById(R.id.textcmnd);
		validate = (TextView) findViewById(R.id.textValid);
		btnvalid = (Button) findViewById(R.id.btnValidate);
		btnedit = (Button) findViewById(R.id.btnEdit);
		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
		uid = loginPreferences.getString(SPF_UID, "");
		String visiables = "false";
		if (loginPreferences.getString(SPF_VISIABLE, "").equals("1")) {
			visiables = "true";
		}
		visiable.setText("Ẩn nick: " + visiables);
		nick.setText("Nick name: " + loginPreferences.getString(SPF_USER, ""));
		password.setText("Mật khẩu: *************");
		name.setText("Tên: " + loginPreferences.getString(SPF_NAME, ""));
		birthday.setText("Ngày sinh: " + loginPreferences.getString(SPF_BIRTHDAY, ""));
		if(loginPreferences.getString(SPF_GENDER, "0").equals("0")) {
			gender.setText("Giới tính: " + "Name");
		} else {
			gender.setText("Giới tính: " + "Nữ");
		}
		if(loginPreferences.getString(SPF_VALID, "0").equals("1")) {
			validate.setText("Xác nhận: " + "OK");
		} else {
			validate.setText("Xác nhận: " + "False");
		}
		email.setText("Email: " + loginPreferences.getString(SPF_EMAIL, ""));		
		cmnd.setText("Số CMND: " + loginPreferences.getString(SPF_NUMBERCMND, ""));
		
		btnvalid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), ValidateAccount.class);
				startActivity(i);
			}
		});
		btnedit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), EditUser.class);
				startActivity(i);
			}
		});

	}
}
