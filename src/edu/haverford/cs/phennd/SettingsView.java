package edu.haverford.cs.phennd;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

public class SettingsView extends Activity {
	private ListView listViewCats;
	private ListView listViewTags;
	private String[] all_categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
	private String[] all_tags = {"Education","Health","Environment","Service-learning","Higher Education","Arts","Nonprofit","Nutrition","Poverty","Civic Engagement","Community Service/Volunteer","Technology","AmeriCorps","Community Development","West","North","Northeast","Northwest","South","Center City","New Jersey","Older adult","Youth","Women","LGBT","Immigrant"};
	SharedPreferences prefs;
	
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
		setContentView(R.layout.activity_settings_view);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("PHENND Update");
        actionBar.setSubtitle("Settings");
        
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
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        listViewCats = (ListView) findViewById(R.id.listViewCats);
        listViewTags = (ListView) findViewById(R.id.listViewTags);
		

		
		
		// Filling the ListView with Strings
		ArrayAdapter<String> adapterCats = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, all_categories);
		listViewCats.setAdapter(adapterCats);
		listViewCats.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// Checking the appropriate boxes
		Boolean thisBoolean;
		for (int i=0;i<all_categories.length;i++) {
			thisBoolean = prefs.getBoolean(all_categories[i], true);
			listViewCats.setItemChecked(i, thisBoolean);
	    }
		
		// Filling the ListView with Strings
		ArrayAdapter<String> adapterTags = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, all_tags);
		listViewTags.setAdapter(adapterTags);
		listViewTags.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		for (int i=0;i<all_tags.length;i++) {
			thisBoolean = prefs.getBoolean(all_tags[i], true);
			listViewTags.setItemChecked(i, thisBoolean);
	    }
		
	
        final CheckBox enableNotifications = (CheckBox) findViewById(R.id.checkbox_enable_notifications);
        enableNotifications.setChecked(true);

	}
	
	public void onPause() {
		super.onPause();
		SharedPreferences.Editor editor = prefs.edit();

		// updates categories info
		for(int i = 0; i < all_categories.length; i ++) {
			editor.putBoolean(all_categories[i], false);
		}
		SparseBooleanArray checkedCats = listViewCats.getCheckedItemPositions();
		if (checkedCats != null) {
			for (int i = 0; i < checkedCats.size(); i++) {
				if(checkedCats.valueAt(i)) {
					editor.putBoolean(all_categories[i], true);
				}
			}
		}
		
		// updates flags info
		ArrayList<String> flagsToAdd = new ArrayList<String>();
		for(int i = 0; i < all_tags.length; i ++) {
			editor.putBoolean(all_tags[i], false);
		}
		SparseBooleanArray checkedTags = listViewTags.getCheckedItemPositions();
		if (checkedTags != null) {
			for (int i = 0; i < checkedTags.size(); i++) {
				if(checkedTags.valueAt(i)) {
					editor.putBoolean(all_tags[i], true);
					flagsToAdd.add(all_tags[i]);
				}
			}
		}
		
		editor.apply();
		DataManager.setFlaggedTags(flagsToAdd);
		
	}

		
	public void onEnableNotificationsClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		
		((CheckBox) view).setChecked(checked);
	}
	
	public void setCats(View view) {
		return;
	}
	
	public void setTags(View view) {
		return;
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
