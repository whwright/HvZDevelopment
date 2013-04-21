package edu.gatech.hvz.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class NetworkManager {
	
	private Map<String, String> casCookies;
	private BasicCookieStore cookieStore;
	
	public NetworkManager() {
	}

	public void setCookies(Map<String, String> cookies) {
		this.casCookies = cookies;
		//Convert cookies into a CookieStore
		this.cookieStore = new BasicCookieStore();
		if (casCookies != null) {
			for (String key : casCookies.keySet()) {
				BasicClientCookie cookie = new BasicClientCookie(key, casCookies.get(key));
				cookie.setVersion(0);
				cookie.setPath("/cas");
				cookie.setDomain(".gatech.edu");
				cookie.setSecure(true);
				cookieStore.addCookie(cookie);
			}
		}
		
	}
	
	public String makeRequest(String urlString, String ... data ) {
		String json = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(urlString + paramsToURL(data));
			
			//Set the params up
			BasicHttpParams params = new BasicHttpParams();
			params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			get.setParams(params);
		
			//Set cookies
			client.setCookieStore(cookieStore);
			
			//Run the GET and get the response body
			json = getBody(client.execute(get));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public String makePost(String urlString, String ... data ) {
		String json = null;
		
		try {
			Document doc = Jsoup.connect(urlString).data(data).cookies(casCookies).post();
			Elements elem = doc.getElementsByTag("body");
			json = elem.first().ownText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * Builds a URL encoded string for GET parameters
	 * @param paramArray An array of [key, value, key, value...]
	 * @return the URL encoded string with a ? prefixed
	 */
	private String paramsToURL(String ... paramArray) {
		
		if (paramArray == null || paramArray.length == 0) {
			return "";
		}
		
		List<NameValuePair> params = new LinkedList<NameValuePair>();

		for (int i = 0; i < paramArray.length; i += 2) {
			params.add(new BasicNameValuePair(paramArray[i], paramArray[i+1]));
		}

		return "?" + URLEncodedUtils.format(params, "utf-8");
	}
	
	private String getBody(HttpResponse response) throws IOException {
		InputStream inputStream = response.getEntity().getContent();
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		
		// Read response into a buffered stream
		int readBytes = 0;
		byte[] sBuffer = new byte[512];
		while ((readBytes = inputStream.read(sBuffer)) != -1) {
			content.write(sBuffer, 0, readBytes);
		}
		// Return result from buffered stream
		return new String(content.toByteArray());
	}

}
