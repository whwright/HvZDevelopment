package edu.gatech.hvz.networking;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class NetworkManager {
	
	private Map<String, String> casCookies;
	
	public NetworkManager()
	{
		
	}

	public Object makeRequest(String urlString) {
		
		try {
			Response r = Jsoup.connect(urlString).cookies(casCookies).method(Method.GET).execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

}
