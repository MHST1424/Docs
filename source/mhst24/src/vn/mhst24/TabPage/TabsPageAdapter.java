package vn.mhst24.TabPage;

import vn.mhst24.view.ListCarPool;
import vn.mhst24.view.ListCarPoolFree;
import vn.mhst24.view.ListFriend;
import vn.mhst24.view.ManageUser;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;

public class TabsPageAdapter extends FragmentPagerAdapter{

	@Override
	public Fragment getItem(int index) {
		switch(index) {
		case 0: 
			ListCarPool carpool = new ListCarPool();
			return carpool;
		case 1: 
			ListCarPoolFree carpoolfree = new ListCarPoolFree();
			return carpoolfree;
		case 2: 
			ListFriend friend = new ListFriend();
			return friend;
		case 3: 
			ManageUser user = new ManageUser();
			return user;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	public TabsPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	
}
