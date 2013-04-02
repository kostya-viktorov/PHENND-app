package edu.haverford.cs.phennd;

import java.util.ArrayList;
import java.util.List;

import edu.haverford.cs.phennd.MainActivity.TabListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FavoritesView extends Activity {

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
    			if (this.activity.getClass() != MainActivity.class) {
    				startActivity(new Intent(this.activity, MainActivity.class));				
    			}
    		} else if (this.LaunchCode == 1) {
    			if (this.activity.getClass() != FavoritesView.class) {
    				startActivity(new Intent(this.activity, FavoritesView.class));				
    			}
    		} else if (this.LaunchCode == 2) {
    			if (this.activity.getClass() != TagView.class) {
    				startActivity(new Intent(this.activity, TagView.class));				
    			}
    		} else if (this.LaunchCode == 3) {
    			if (this.activity.getClass() != SettingsView.class) {
    				startActivity(new Intent(this.activity, SettingsView.class));				
    			}
    		}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// Do nothing for now
		}
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_view);
		
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Favorites");
        actionBar.setSubtitle("PHENND Update");
	     
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Tab tabCategory = actionBar.newTab();
        tabCategory.setText("Categories").setIcon(R.drawable.ic_launcher).setTabListener(new TabListener(this,0));
        Tab tabFavorites = actionBar.newTab();
        tabFavorites.setText("Favorites").setIcon(R.drawable.ic_launcher).setTabListener(new TabListener(this, 1));

        Tab tabTags = actionBar.newTab();
        tabTags.setText("Tags").setIcon(R.drawable.ic_launcher).setTabListener(new TabListener(this, 2));
        Tab tabSettings = actionBar.newTab();
        tabSettings.setText("Settings").setIcon(R.drawable.ic_launcher).setTabListener(new TabListener(this, 3));

        actionBar.addTab(tabFavorites, true);
        actionBar.addTab(tabCategory, 0, false);
        actionBar.addTab(tabTags, 2, false);
        actionBar.addTab(tabSettings, 3, false);
        actionBar.selectTab(tabFavorites);
        
		List<String> favoriteArticles = DataManager.getFavorites(); // REPLACE THIS WITH SINGLETON ACCESS TO DATAMANAGER
		ListView listView = (ListView) findViewById(R.id.listView3);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favoriteArticles);
		listView.setAdapter(adapter);
        
        final ListView listOfStories = (ListView)findViewById(R.id.listView3);
        
        listOfStories.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                    long id) {
                Intent intent = new Intent(v.getContext(), ArticleView.class);
            	String storyName = (String) listOfStories.getItemAtPosition(position);
            	intent.putExtra("Name", storyName);
            	startActivityForResult(intent, 0);
            }

        });
  
        
	}

}
