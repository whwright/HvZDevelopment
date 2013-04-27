package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class KillMapActivity extends Activity implements OnTouchListener {
	
	TextView coords;
	ImageView map, cross;
	int MAP_HEIGHT = 294;
	int MAP_WIDTH = 401;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kill_map);
		
		coords = (TextView) findViewById(R.id.killmapactivity_coords_textview);
		map = (ImageView) findViewById(R.id.killmapactivity_map_imageview);
		cross = (ImageView) findViewById(R.id.killmapactivity_cross_imageview);
		
		map.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x, y;
		int width, height;
		int realX, realY;
		switch (v.getId()) {
			case R.id.killmapactivity_map_imageview:
				x = (int) Math.floor(event.getX());
				y = (int) Math.floor(event.getY());
				width = map.getWidth();
				height = map.getHeight();
				if (x < 0) x = 0;
				if (x > width) x = width;
				if (y < 0) y = 0;
				if (y > height) y = height;
				realX = (int) Math.floor(1.0 * x * MAP_WIDTH / width);
				realY = (int) Math.floor(1.0 * y * MAP_HEIGHT / height);
				String click = "X: " + realX + " Y: " + realY;
				//Would be nice if this scaled to dip
				int crossSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
				ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(crossSize, crossSize);
				mlp.setMargins(x-crossSize/2, y-crossSize/2, 0, 0);
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(mlp);
				cross.setLayoutParams(lp);
				coords.setText(click + " " + crossSize);
				return true;
			default:
				return false;
		}
	}

}