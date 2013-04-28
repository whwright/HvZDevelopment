package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class ShowMyCodeActivity extends SherlockActivity {

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
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Player Code");
		
		myCodeTextView = (TextView) findViewById(R.id.showmycode_textview_mycode);
		qrCodeWebView = (WebView) findViewById(R.id.showmycode_webview_qrcode);
		
		qrCodeWebView.getSettings().setLoadWithOverviewMode(true);
		qrCodeWebView.getSettings().setUseWideViewPort(true);
		//DisplayMetrics displayMetrics = new DisplayMetrics();
		//getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		//int height = displayMetrics.heightPixels;
		//int width = displayMetrics.widthPixels;
		
		resources = ResourceManager.getResourceManager();
		myCodeTextView.setText(resources.getPlayer().getPlayerCode());
	}
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		fetchQRCode();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void fetchQRCode() {
		String code = resources.getPlayer().getPlayerCode();
		int x = Math.min((int)(qrCodeWebView.getWidth()*.6), (int)(qrCodeWebView.getHeight()*.6));
		String webpage = String.format(html, resources.getDataManager().getQRCode(code, x, x));
		qrCodeWebView.loadData(webpage, "text/html", null);
	}

}
