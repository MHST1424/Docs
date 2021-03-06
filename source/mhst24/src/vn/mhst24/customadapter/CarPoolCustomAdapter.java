package vn.mhst24.customadapter;

import java.io.IOException;
import java.util.List;

import tungnv.haui.mhst24.R;
import vn.mhst24.control.Base64;
import vn.mhst24.entity.CarPool;
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
 * CarPoolCustomAdapter
 * 
 * Version 1.0
 * 
 * 12-Aug-2014
 * Nodification Logs:
 * DATE        AUTHOR        DESCRIPTION
 * ------------------------------------------------------
 * 12-Aug-2014  TungNV		 
 * */
public class CarPoolCustomAdapter extends ArrayAdapter<CarPool> {

	List<CarPool> carpool = null;
	Context context;
	int layoutResourceId;
	Typeface typeface;
	private LayoutInflater inflater;

	public CarPoolCustomAdapter(Context context, int resource, List<CarPool> carpool) {
		super(context, resource, carpool);
		this.layoutResourceId = resource;
		this.context = context;
		this.carpool = carpool;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CarPoolHolder holder = null;
		if (null == row) {
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new CarPoolHolder();
			holder.imgicon = (ImageView) row.findViewById(R.id.imgavata);
			holder.txtidcarpools = (TextView) row.findViewById(R.id.txtidcarpool);
			holder.txtiduser = (TextView) row.findViewById(R.id.txtiduser);
			holder.txtuser = (TextView) row.findViewById(R.id.txtuser);
			holder.txtidlc_start = (TextView) row.findViewById(R.id.txtlc_start);
			holder.txtidlc_end = (TextView) row.findViewById(R.id.txtlc_end);
			holder.txtidtime_start = (TextView) row.findViewById(R.id.txttime_start);
			holder.txtidtime_back = (TextView) row.findViewById(R.id.txttime_back);
			row.setTag(holder);
		} else {
			holder = (CarPoolHolder)row.getTag();
		}
		CarPool listcarpool = carpool.get(position);
		byte[] decodedString = null;
		try {
			decodedString = Base64.decode(listcarpool.getAvata());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		String tmp = listcarpool.getPid() + "";
		holder.imgicon.setImageBitmap(decodedByte);
		holder.txtidcarpools.setText(tmp);
		holder.txtuser.setText(listcarpool.getUser());
		holder.txtidlc_start.setText(listcarpool.getLcstart());
		holder.txtidlc_end.setText(listcarpool.getLcfinish());
		holder.txtiduser.setText(listcarpool.getUid() + "");
		holder.txtidtime_start.setText("Thời gian đi: " + listcarpool.getTimestart());
		if (listcarpool.getTimeback().isEmpty()) {
			holder.txtidtime_back.setVisibility(View.GONE);
		} else {
			holder.txtidtime_back.setText("Thời gian về: " + listcarpool.getTimeback());
		}
		return row;
	}

	static class CarPoolHolder {
		ImageView imgicon;
		TextView txtuser;
		TextView txtiduser;
		TextView txtidcarpools;
		TextView txtidlc_start;
		TextView txtidlc_end;
		TextView txtidtime_start;
		TextView txtidtime_back;
	}
	
}
