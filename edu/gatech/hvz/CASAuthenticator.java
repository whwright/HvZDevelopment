package edu.gatech.hvz;

import org.jsoup.nodes.Document;
import org.jsoup.Connection.Response;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class CASAuthenticator {

	private static final String casURI = "https://login.gatech.edu/cas/login";
	private static final String redirectURI = "";
	private String user, pass;
		
	public CASAuthenticator(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	/**
	 * Connected to CAS authentication given a user name and password.
	 *
	 * @return A map of cookie name/value pairs needed to access CAS protected pages.
	 * 		   Returns null if authentication fails.
	 */
	public Map<String, String> connect() {
		Map<String, String> cookies = null;
		
		try {
			Response loginPage = Jsoup.connect(getURI()).method(Method.GET).execute();
			String randomKey = getRandomKey(loginPage.parse());
			Response submitPage = Jsoup
	                .connect(getURI())
	                .data("username", user, "password", pass)
	                .data("lt", randomKey) 
	                .data("_eventId", "submit")
	                .cookies(loginPage.cookies())
	                .method(Method.POST)
	                .execute();
			cookies = submitPage.cookies();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		//Verify if a valid CAS cookie was received
		if ( !cookies.containsKey("CASTGC") ) {
			cookies = null;
		}
		
		return cookies;
	}

	
	/**
	 * Find the value of the hidden input "lt".
	 * This value is a random key and must be forwarded to POST
	 * when entering a user name and password.
	 * 
	 * @param doc The login page document
	 * @return The randomized key
	 */
	private String getRandomKey(Document doc) {
		return doc.select("input[name=lt]").first().attr("value");
	}
	
	/**
	 * @return The full URI that will be accessed for authentication
	 */
	private String getURI() {
		String URI = casURI;
		
		if (redirectURI != null && redirectURI.length() > 0) {
			URI += "?service=" + redirectURI;
		}
		
		return URI;
	}
	
}
