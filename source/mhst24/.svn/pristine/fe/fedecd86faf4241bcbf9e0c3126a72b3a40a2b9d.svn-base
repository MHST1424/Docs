package vn.mhst24.view;

import tungnv.haui.mhst24.R;
import vn.mhst24.TabPage.TabsPageAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class main extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPageAdapter mAdapter;
	private ActionBar actionBar;
	private static final String SPF_NAMEACC = "account";
	private static final String SPF_UID = "uid";
	String uid;
	// Tab titles
	int[] myIcon = new int[]{R.drawable.newspaper_rss, R.drawable.sign_free, R.drawable.user_group, R.drawable.system_preferences};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences loginPreferences = getSharedPreferences(SPF_NAMEACC,
				Context.MODE_PRIVATE);
		uid = loginPreferences.getString(SPF_UID, "");
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPageAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (int tab : myIcon) {
			actionBar.addTab(actionBar.newTab().setIcon(tab)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_carpool:
			Intent i = new Intent(getApplicationContext(), AddCarPool.class);
			startActivity(i);
			return true;
		case R.id.action_search:
			Intent intent = new Intent(getApplicationContext(), SearchCarPool.class);
			startActivity(intent);
			return true;
		}
		return false;
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {

	}
}
