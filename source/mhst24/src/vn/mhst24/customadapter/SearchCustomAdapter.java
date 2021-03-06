package vn.mhst24.customadapter;

import java.util.List;

import tungnv.haui.mhst24.R;
import vn.mhst24.entity.CarPool;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * SearchCustomAdapter
 * 
 * Version 1.0
 * 
 * 25-SEP-2014
 * Nodification Logs:
 * DATE        AUTHOR        DESCRIPTION
 * ------------------------------------------------------
 * 25-SEP-2014  TungNV		 
 * */
public class SearchCustomAdapter extends ArrayAdapter<CarPool> {
	List<CarPool> carpool = null;
	Context context;
	int layoutResourceId;
	Typeface typeface;
	private LayoutInflater inflater;

	public SearchCustomAdapter (Context context, int resource, List<CarPool> carpool) {
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
			//holder.imgicon = (ImageView) row.findViewById(R.id.img_avata);
			holder.txtidcarpools = (TextView) row.findViewById(R.id.txtidcarpool);
			holder.txtuser = (TextView) row.findViewById(R.id.txtuser);
			holder.txtiduser = (TextView) row.findViewById(R.id.txtiduser);
			holder.txtidlc_start = (TextView) row.findViewById(R.id.txtlc_start);
			holder.txtidlc_end = (TextView) row.findViewById(R.id.txtlc_end);
			holder.txtidtime_start = (TextView) row.findViewById(R.id.txttime_start);
			holder.txtidtime_back = (TextView) row.findViewById(R.id.txttime_back);
			holder.edit_start = (EditText) row.findViewById(R.id.autoeditstart);
			holder.edit_end = (EditText) row.findViewById(R.id.autoeditend);
			row.setTag(holder);
		} else {
			holder = (CarPoolHolder)row.getTag();
		}
		CarPool listcarpool = carpool.get(position);
		String tmp = listcarpool.getPid() + "";
		holder.txtidcarpools.setText(tmp);
		holder.txtiduser.setText(listcarpool.getUid() + "");
		holder.txtuser.setText(listcarpool.getUser());
		holder.txtidlc_start.setText(listcarpool.getLcstart());
		holder.txtidlc_end.setText(listcarpool.getLcfinish());
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
		EditText edit_start;
		EditText edit_end;
	}
}
