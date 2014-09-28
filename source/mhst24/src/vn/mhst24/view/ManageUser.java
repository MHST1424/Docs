package vn.mhst24.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tungnv.haui.mhst24.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ManageUser extends ListFragment {
	String[] nameSetting = new String[] {"Thông tin tài khoản", "Thay đổi mật khẩu" , "Cài đặt", "Đăng xuất"};
	
	String[] nameDesc = new String[] {"Xem chi tiết thông tin tài khoản", "Thay đổi mật khẩu hiện tại bằng mật khẩu mới", "Cài đặt các thông tin ứng dụng", "Đăng xuất tài khoản khỏi ứng dụng"};
	
	private static final String SPF_NAMEACC = "account";
	private static final String SPF_USER = "user_name";
	private static final String SPF_PASS = "password";
	private static final String SPF_REMEMBER = "remember";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		// Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
 
        for(int i=0;i<4;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name", nameSetting[i]);
            hm.put("desc", nameDesc[i]);
            aList.add(hm);
        }
 
        // Keys used in Hashmap
        String[] from = {"name","desc"};
 
        // Ids of views in listview_layout
        int[] to = {R.id.txtidName, R.id.txtidDescription};
 
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.item_function, from, to);
        setListAdapter(adapter);
        
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (position == 0) {
			Toast.makeText(getActivity().getBaseContext(), "Click thông tin tài khoản", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getActivity().getApplicationContext(), DetailUser.class);
			startActivity(i);
		}
		if (position == 1) {
			Toast.makeText(getActivity().getBaseContext(), "Click thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getActivity().getApplicationContext(), ChangePassword.class);
			startActivity(i);
		}
		if (position == 2) {
			Toast.makeText(getActivity().getBaseContext(), "Click cài đặt", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getActivity().getApplicationContext(), Setting.class);
			startActivity(i);
		}
		if (position == 3) {
			Toast.makeText(getActivity().getBaseContext(), "Click đăng xuất", Toast.LENGTH_SHORT).show();
			SharedPreferences logoutPreferences = getActivity().getSharedPreferences(SPF_NAMEACC, Context.MODE_PRIVATE);
			logoutPreferences.edit().putString(SPF_USER, "").putString(SPF_PASS, "").putBoolean(SPF_REMEMBER, false).commit();
			Intent i = new Intent(getActivity().getApplicationContext(), Login.class);
			startActivity(i);
		}
	}
}
