package vn.mhst24.customadapter;

import java.io.IOException;
import java.util.List;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.Base64;
import vn.mhst24.entity.Friend;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * FriendCustomAdapter
 * 
 * Version 1.0
 * 
 * 25-SEP-2014
 * Nodification Logs:
 * DATE        AUTHOR        DESCRIPTION
 * ------------------------------------------------------
 * 25-SEP-2014  TungNV		 
 * */
public class FriendCustomAdapter extends ArrayAdapter<Friend>{

	List<Friend> friend = null;
	Context context;
	int layoutResourceId;
	Typeface typeface;
	private LayoutInflater inflater;
	public FriendCustomAdapter(Context context, int resource, List<Friend> friend) {
		super(context, resource, friend);
		this.layoutResourceId = resource;
		this.context = context;
		this.friend = friend;
		//log.info("contructor:" + carpool);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//log.info("jasdf");
		View row = convertView;
		CarPoolHolder holder = null;
		if (null == row) {
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new CarPoolHolder();
			holder.imgicon = (ImageView) row.findViewById(R.id.avata);
			holder.txtuser = (TextView) row.findViewById(R.id.txtUserName);
			row.setTag(holder);
		} else {
			holder = (CarPoolHolder)row.getTag();
		}
		Friend listfriend = friend.get(position);
		byte[] decodedString = null;
		try {
			decodedString = Base64.decode(listfriend.getAvata());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		holder.imgicon.setImageBitmap(decodedByte);
		holder.txtuser.setText(listfriend.getUser_name());
		/*holder.txtidcarpools.setText(tmp);
		holder.txtuser.setText(listcarpool.getUser());
		holder.txtidlc_start.setText(listcarpool.getLcstart());
		holder.txtidlc_end.setText(listcarpool.getLcfinish());
		holder.txtidtime_start.setText("Thời gian đi: " + listcarpool.getTimestart());
		if (listcarpool.getTimeback().isEmpty()) {
			holder.txtidtime_back.setVisibility(View.GONE);
		} else {
			holder.txtidtime_back.setText("Thời gian về: " + listcarpool.getTimeback());
		}*/
		return row;
	}

	static class CarPoolHolder {
		ImageView imgicon;
		TextView txtuser;
		/*TextView txtidcarpools;
		TextView txtidlc_start;
		TextView txtidlc_end;
		TextView txtidtime_start;
		TextView txtidtime_back;*/
	}
	
}
