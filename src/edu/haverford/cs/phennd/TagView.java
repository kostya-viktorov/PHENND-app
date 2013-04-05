package edu.haverford.cs.phennd;

import java.util.ArrayList;
import java.util.List;

//import edu.haverford.cs.phennd.MainActivity.TabListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TagView extends Activity {

    List<String> flaggedTags;

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
		setContentView(R.layout.activity_tag_view);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Article Tags");
        actionBar.setSubtitle("PHENND Update");
        
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tabCategory = actionBar.newTab();
        tabCategory.setText("Categories").setIcon(R.drawable.shelficon).setTabListener(new TabListener(this,0));
        Tab tabFavorites = actionBar.newTab();
        tabFavorites.setText("Favorites").setIcon(R.drawable.staricon).setTabListener(new TabListener(this, 1));
        Tab tabTags = actionBar.newTab();
        tabTags.setText("Tags").setIcon(R.drawable.tagicon).setTabListener(new TabListener(this, 2));
        Tab tabSettings = actionBar.newTab();
        //tabSettings.setText("Settings").setIcon(R.drawable.ic_launcher).setTabListener(new TabListener(this, 3));
        tabSettings.setText("Settings").setTabListener(new TabListener(this, 3));

        actionBar.addTab(tabTags, true);
        actionBar.addTab(tabCategory, 0, false);
        actionBar.addTab(tabFavorites, 1,false);
        actionBar.addTab(tabSettings, 3, false);
        actionBar.selectTab(tabTags);

        flaggedTags = DataManager.getFlaggedTags(); // REPLACE THIS WITH SINGLETON ACCESS TO DATAMANAGER
        ListView listView = (ListView) findViewById(R.id.listView2);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, flaggedTags);
		listView.setAdapter(adapter); 
        
        
        final ListView listOfTags = (ListView)findViewById(R.id.listView2);
        
        listOfTags.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                    long id) {
                Intent intent = new Intent(v.getContext(), ArticleListView.class);
            	String tagName = (String) listOfTags.getItemAtPosition(position);
            	intent.putExtra("TagsOrCategories", "Tags");
            	intent.putExtra("MetaInfo", tagName);
            	startActivityForResult(intent, 0);
            }

        }); 
	}


}
