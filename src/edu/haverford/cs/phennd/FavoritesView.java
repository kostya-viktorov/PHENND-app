package edu.haverford.cs.phennd;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FavoritesView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_favorites_view, menu);
		return true;
	}

}
