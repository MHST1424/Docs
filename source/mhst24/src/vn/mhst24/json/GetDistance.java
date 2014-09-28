package vn.mhst24.json;

import java.io.IOException;
import java.io.InputStream;
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

import tungnv.haui.mhst24.R;
import vn.mhst24.json.HandleXML;

public class GetDistance {
	private static String xml_url = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=";
	private static String api_key = "AIzaSyB36gqXnG07xGhiE6LaI4GEikfLYVatQfk";

	String urls, urld;

	private HandleXML objs, objd;
	String adSpoin = null, adDpoin = null;

	public GetDistance() {

	}

	public String getDistance(String sPoint, String dPoint) {
		try {
			adSpoin = addressEncode(sPoint);
			adDpoin = addressEncode(dPoint);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urld = xml_url + adSpoin + "&destinations=" + adDpoin
				+ "&mode=driving&language=vi-VN&key=" + api_key;

		objd = new HandleXML(urld);
		objd.fetchXML();

		while (objd.parsingComplete)
			;
		return objd.getDistance();

	}

	@SuppressWarnings("unused")
	private String addressEncode(String address)
			throws UnsupportedEncodingException {

		return address = URLEncoder.encode(address, "utf-8");

	}

}
