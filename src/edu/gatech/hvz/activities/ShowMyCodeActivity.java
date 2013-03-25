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

	private ResourceManager resources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_my_code);
		
		myCodeTextView = (TextView) findViewById(R.id.showmycode_textview_mycode);
		qrCodeWebView = (WebView) findViewById(R.id.showmycode_webview_qrcode);
		
		resources = ResourceManager.getResourceManager();
		System.out.println(resources.getPlayer().getPlayerCode());
		myCodeTextView.setText(resources.getPlayer().getPlayerCode());
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
		String code = resources.getPlayer().getPlayerCode();
		int x = Math.min((int)(qrCodeWebView.getWidth()*.6), (int)(qrCodeWebView.getHeight()*.6));
		String webpage = String.format(html, resources.getDataManager().getQRCode(code, x, x));
		qrCodeWebView.loadData(webpage, "text/html", null);
	}

}
