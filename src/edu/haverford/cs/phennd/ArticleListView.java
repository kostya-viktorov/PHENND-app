package edu.haverford.cs.phennd;

import java.util.ArrayList;
import java.util.List;

import edu.haverford.cs.phennd.SettingsView.TabListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleListView extends Activity {

	
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
    				startActivity(new Intent(this.activity, MainActivity.class));				
    		} else if (this.LaunchCode == 1) {
    				startActivity(new Intent(this.activity, MainActivity.class));
    		} else if (this.LaunchCode == 2) {
    				startActivity(new Intent(this.activity, MainActivity.class));
    		} else if (this.LaunchCode == 3) {
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
		setContentView(R.layout.activity_article_list_view);

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

        
		Bundle extras = getIntent().getExtras();
		String tagsOrCategories = extras.getString("TagsOrCategories");
		String metaInfo = extras.getString("MetaInfo");
		
	
		final ListView listOfStories = (ListView)findViewById(R.id.listViewList);

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
       
		DataManager dm = DataManager.getDataManager(getBaseContext());
		List<String> articles = new ArrayList<String>();
		if (tagsOrCategories.equals("Tags")) {
			actionBar.setTitle("Articles By Tag");
			articles = dm.getArticleTitlesForTag(metaInfo);
        } else if (tagsOrCategories.equals("Categories")) {
        	actionBar.setTitle("Articles By Category");
			articles = dm.getArticleTitlesForCategory(metaInfo); 
        }
      
        ListView listView = (ListView) findViewById(R.id.listViewList);		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articles);
		listView.setAdapter(adapter); 
        
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
