package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;

public class ShowMyCodeActivity extends Activity {

	private final static String html = "<html><body style=\"background-color: " +
			"black;\"><div style=\"text-align: center;\"><img src=\"%s\" />" +
			"</div></body></html>";
	
	private TextView myCodeTextView;
	private WebView qrCodeWebView;
	private String testCode = "Sup Guys";

	private ResourceManager resources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_my_code);
		
		myCodeTextView = (TextView) findViewById(R.id.showmycode_textview_mycode);
		qrCodeWebView = (WebView) findViewById(R.id.showmycode_webview_qrcode);
		
		myCodeTextView.setText(testCode);
		
		resources = ResourceManager.getResourceManager();
	}
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		fetchQRCode();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_my_code, menu);
		return true;
	}
	
	private void fetchQRCode() {
		int x = Math.min((int)(qrCodeWebView.getWidth()*.6), (int)(qrCodeWebView.getHeight()*.6));
		String webpage = String.format(html, resources.getDataManager().getQRCode(x, x));
		qrCodeWebView.loadData(webpage, "text/html", null);
	}

}
