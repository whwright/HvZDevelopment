package edu.gatech.hvz.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

/**
 * A class to handle all network activity for the application.
 * Supports generic GET request with variable length parameters.
 */
public class NetworkManager {
	
	private Map<String, String> casCookies;
	private BasicCookieStore cookieStore;
	
	/**
	 * Set cookies that will be used for the remainder of the session.
	 * Needs to be a map of cookies in the format <CookieName, CookieValue>,
	 * both in string format.
	 * @param cookies A map of the cookies to set
	 */
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
	
	/**
	 * Make a HTTP GET request to a URL with option parameters
	 * 
	 * @param urlString The base URL for the request
	 * @param data An array of params, where the 0th item is the name of the first param, the 1st item is the value of the first param, the 2nd item is the name of the second param, etc.
	 * @return
	 */
	public String makeRequest(String urlString, String ... data ) {
		String json = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			//Build the full URL with params
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
	
	/**
	 * Builds a URL encoded string for GET parameters
	 * @param paramArray An array of [key, value, key, value...]
	 * @return the URL encoded string with a ? prefixed, if there are params.  Empty string otherwise.
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
	
	/**
	 * Reads the body of a HTTP response into a string
	 * @param response The response object from a HTTP request
	 * @return A string representation of the body of an HTML response
	 * @throws IOException
	 */
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
