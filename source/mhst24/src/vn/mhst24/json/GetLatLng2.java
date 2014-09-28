package vn.mhst24.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tungnv.haui.mhst24.R;
import vn.mhst24.json.HandleXML;

import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import app.akexorcist.gdaplibrary.GoogleDirection;
import app.akexorcist.gdaplibrary.GoogleDirection.OnDirectionResponseListener;

public class GetLatLng2 {
	// URL to get contacts JSON
	private static String xml_url = "https://maps.googleapis.com/maps/api/geocode/xml?address=";
	private static String api_key = "AIzaSyB36gqXnG07xGhiE6LaI4GEikfLYVatQfk";
	// JSON Node names
	private static final String TAG_RESULTS = "results";
	private static final String TAG_GEO = "geometry";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	JSONArray contacts = null;

	String urls, urld;

	private ProgressDialog pDialog;
	private HandleXML objs, objd;
	String adSpoin = null, adDpoin = null;

	public GetLatLng2() {

	}

	public String GetLat(String sPoint) {
		try {
			adSpoin = addressEncode(sPoint);
			System.out.println("adSpoin" + adSpoin);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urls = xml_url + adSpoin + "&key=" + api_key;
		System.out.println("urls" + urls);
		objs = new HandleXML(urls);
		objs.fetchXML();
		while (objs.parsingComplete)
			;
		return objs.getLat();

	}

	public String GetLng(String dPoint) {
		try {
			adDpoin = addressEncode(dPoint);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urld = xml_url + adDpoin + "&key=" + api_key;

		objd = new HandleXML(urld);
		objd.fetchXML();

		while (objd.parsingComplete)
			;
		return objd.getLng();

	}

	@SuppressWarnings("unused")
	private String addressEncode(String address)
			throws UnsupportedEncodingException {

		return address = URLEncoder.encode(address, "utf-8");

	}

}
