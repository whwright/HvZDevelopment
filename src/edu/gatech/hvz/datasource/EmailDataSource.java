package edu.gatech.hvz.datasource;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Email;

public class EmailDataSource {
	
	private final String emailURL = "https://hvz.gatech.edu/api/email";

	public void postEmail(Email email)
	{
		String json = new Gson().toJson(email);
		String[] params = { "json", json };
		ResourceManager.getResourceManager().getNetworkManager().makeRequest(emailURL, params);
	}
}
