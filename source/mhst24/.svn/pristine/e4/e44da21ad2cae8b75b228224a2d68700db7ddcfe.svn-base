package vn.mhst24.view;

import tungnv.haui.mhst24.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConditionUse extends Activity{
	Button btn_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_condition);
		btn_next = (Button) findViewById(R.id.btnnext);
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), Register.class);
				startActivity(i);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), Login.class);
		startActivity(i);
		finish();
	}
}
