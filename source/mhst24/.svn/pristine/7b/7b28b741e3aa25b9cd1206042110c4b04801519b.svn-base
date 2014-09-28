package vn.mhst24.json;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetLatLng {

	static Double longitute;
	static Double latitude;

	public GetLatLng() {
	}

	public static JSONObject getLocationInfo(String address) {
		StringBuilder stringBuilder = new StringBuilder();
		try {

			address = address.replaceAll(" ", "%20");

			HttpPost httppost = new HttpPost(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ address + "&sensor=false");
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();

			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}

	public static double getLng(JSONObject jsonObject) {

		try {

			longitute = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lng");

		} catch (JSONException e) {
			return 0;

		}

		return longitute;
	}

	public static double getLat(JSONObject jsonObject) {

		try {

			latitude = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lat");

		} catch (JSONException e) {
			return 0;

		}

		return latitude;
	}
}
