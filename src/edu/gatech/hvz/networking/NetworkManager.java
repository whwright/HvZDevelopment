package edu.gatech.hvz.networking;

import java.io.IOException;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.gatech.hvz.entities.Kill;

public class NetworkManager {
	
	private Map<String, String> casCookies;
	
	public NetworkManager() {
	}

	public void setCookies(Map<String, String> cookies) {
		this.casCookies = cookies;
	}
	
	public String makeRequest(String urlString, String ... data ) {
		String json = null;
		
		try {
			Document doc = Jsoup.connect(urlString).data(data).cookies(casCookies).get();
			Elements elem = doc.getElementsByTag("body");
			json = elem.first().ownText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}

}
