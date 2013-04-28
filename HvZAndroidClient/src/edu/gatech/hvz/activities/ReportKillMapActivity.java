package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import android.os.Bundle;
import android.content.Intent;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ReportKillMapActivity extends SherlockActivity implements OnTouchListener {
	
	int MAP_HEIGHT = 294;
	int MAP_WIDTH = 401;
	
	Button confirm;
	ImageView map, cross;
	int mapX, mapY;
	int scaledX, scaledY;
	private ResourceManager resources;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_kill_map);
		resources = ResourceManager.getResourceManager();
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Kill Location");
		
		map = (ImageView) findViewById(R.id.reportkillmapactivity_map_imageview);
		cross = (ImageView) findViewById(R.id.reportkillmapactivity_cross_imageview);
		confirm = (Button) findViewById(R.id.reportkillmapactivity_confirm_button);
		
		confirm.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("mapX", mapX);
				i.putExtra("mapY", mapY);
				setResult(RESULT_OK, i);
				finish();
			}
		});
		
		map.setOnTouchListener(this);
		
		if (savedInstanceState != null) {
			mapX = savedInstanceState.getInt("mapX");
			mapY = savedInstanceState.getInt("mapY");
			scaledX = savedInstanceState.getInt("scaledX");
			scaledY = savedInstanceState.getInt("scaledY");
			drawCross();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.menu_contact:
			Intent lineIntent = new Intent(this, ContactAdminsActivity.class);
			startActivity(lineIntent);
			return true;
		case R.id.menu_about:
			Intent aboutintent = new Intent(this, AboutActivity.class);
			startActivity(aboutintent);
			return true;
		case R.id.menu_logout:
			resources.resetData();
			Intent login = new Intent(this, LoginActivity.class);
			startActivity(login);
			finish();
			return true;
		}
		return false;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("scaledX", scaledX);
		outState.putInt("scaledY", scaledY);
		outState.putInt("mapX", mapX);
		outState.putInt("mapY", mapY);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x, y;
		int width, height;

		switch (v.getId()) {
			case R.id.reportkillmapactivity_map_imageview:
				//Get the X and Y of the click within the image
				x = (int) Math.floor(event.getX());
				y = (int) Math.floor(event.getY());
				//Get the width and height of the image
				width = map.getWidth();
				height = map.getHeight();
				//Make sure bounds are correct
				if (x < 0) x = 0;
				if (x > width) x = width;
				if (y < 0) y = 0;
				if (y > height) y = height;
				//Convert from image clicked coords to original image size coords
				scaledX = x;
				scaledY = y;
				mapX = (int) Math.floor(1.0 * x * MAP_WIDTH / width);
				mapY = (int) Math.floor(1.0 * y * MAP_HEIGHT / height);
				//Scale to DP and set margin so the cross is displayed at the correct touched point
				drawCross();
				return true;
			default:
				return false;
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		i.putExtra("mapX", mapX);
		i.putExtra("mapY", mapY);
		setResult(RESULT_OK, i);
	    super.onBackPressed();
	}
	
	private void drawCross() {
		int crossSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
		ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(crossSize, crossSize);
		mlp.setMargins(scaledX-crossSize/2, scaledY-crossSize/2, 0, 0);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(mlp);
		cross.setLayoutParams(lp);
	}

}
