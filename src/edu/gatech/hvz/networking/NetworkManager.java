package edu.gatech.hvz.networking;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkManager {

	public Object makeRequest(String urlString) {
		
		try {
			/* IDK HOW TO MAKE HTTP CALLS AND GET JSON BACK OR WHATEVER STILL LOOKING AROUND */
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
