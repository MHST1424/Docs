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

public class GetDistance2 {

	static String distance;

	public GetDistance2() {
	}

	public static JSONObject getDistanceInfo(String spoint, String dpoint) {
		StringBuilder stringBuilder = new StringBuilder();
		try {

			spoint = spoint.replaceAll(" ", "%20");
			dpoint = dpoint.replaceAll(" ", "%20");
			HttpPost httppost = new HttpPost(
					"https://maps.googleapis.com/maps/api/distancematrix/xml?origins="
							+ spoint + "&destinations=" + dpoint
							+ "&mode=driving&language=vi-VN&key="
							+ "AIzaSyB36gqXnG07xGhiE6LaI4GEikfLYVatQfk");
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

	public static String getDistance(JSONObject jsonObject) {

		try {

			distance = ((JSONArray) jsonObject.get("rows")).getJSONObject(0)
					.getJSONObject("elements").getJSONObject("distance")
					.getString("text");

		} catch (JSONException e) {
			return "";

		}

		return distance;
	}

}
