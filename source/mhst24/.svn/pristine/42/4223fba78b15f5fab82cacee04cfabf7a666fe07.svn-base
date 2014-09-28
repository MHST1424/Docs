package vn.mhst24.control;

/*
 * ServiceHandler
 * 
 * Version 1.0
 * 
 * 12-Aug-2014
 * Nodification Logs:
 * DATE        AUTHOR        DESCRIPTION
 * ------------------------------------------------------
 * 12-Aug-2014  TungNV		 
 * */
public class Util {
	/*public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}*/
	public boolean validEmail(String str) {
		Boolean chk = false;
		while (chk == false) {
			String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
			java.util.regex.Pattern p = java.util.regex.Pattern
					.compile(ePattern);
			java.util.regex.Matcher m = p.matcher(str);
			if (m.matches() == true) {
				chk = false;
				return true;
			}
		}
		return false;
	}
	public boolean validUser(String str) {
		Boolean chk = false;
		while (chk == false) {
			String ePattern = "[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9]*";
			java.util.regex.Pattern p = java.util.regex.Pattern
					.compile(ePattern);
			java.util.regex.Matcher m = p.matcher(str);
			if (m.matches() == true) {
				chk = false;
				return true;
			}
		}
		return false;
	}
	public boolean validPass(String str) {
		Boolean chk = false;
		while (chk == false) {
			String ePattern = "[a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_][a-zA-Z0-9@_]*";
			java.util.regex.Pattern p = java.util.regex.Pattern
					.compile(ePattern);
			java.util.regex.Matcher m = p.matcher(str);
			if (m.matches() == true) {
				chk = false;
				return true;
			}
		}
		return false;
	}
}
