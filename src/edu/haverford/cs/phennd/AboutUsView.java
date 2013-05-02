package edu.haverford.cs.phennd;

import edu.haverford.cs.phennd.SettingsView.TabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class AboutUsView extends Activity {
	
	class TabListener implements ActionBar.TabListener {
    	private Activity activity;
    	private int LaunchCode;
    	
    	public TabListener(Activity activity, int launchCode) {
    		this.activity = activity;
    		this.LaunchCode = launchCode;
    	}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// Do nothing for now
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
    		if (this.LaunchCode == 0) {
	                Intent intent = new Intent(this.activity, MainActivity.class);
		           	intent.putExtra("TabToLaunch", "Main");
    				startActivity(intent);
    		} else if (this.LaunchCode == 1) {
                Intent intent = new Intent(this.activity, MainActivity.class);
	           	intent.putExtra("TabToLaunch", "Favorites");
				startActivity(intent);
    		} else if (this.LaunchCode == 2) {
                Intent intent = new Intent(this.activity, MainActivity.class);
	           	intent.putExtra("TabToLaunch", "Tags");
				startActivity(intent);
    		} else if (this.LaunchCode == 3) {
    		}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// Do nothing for now
		}
	}

	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("PHENND Update");
        actionBar.setSubtitle("Articles");
        
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tabCategory = actionBar.newTab();
        tabCategory.setText("Categories").setIcon(R.drawable.shelficon).setTabListener(new TabListener(this,0));
        Tab tabFavorites = actionBar.newTab();
        tabFavorites.setText("Favorites").setIcon(R.drawable.staricon).setTabListener(new TabListener(this, 1));
        Tab tabTags = actionBar.newTab();
        tabTags.setText("Tags").setIcon(R.drawable.tagicon).setTabListener(new TabListener(this, 2));

        actionBar.addTab(tabCategory, 0, false);
        actionBar.addTab(tabFavorites, 1, false);
        actionBar.addTab(tabTags, 2, false);

    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_settings:
				startActivity(new Intent(this, SettingsView.class));		
	            return true;
	        case R.id.about_us:
				startActivity(new Intent(this, AboutUsView.class));		
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
