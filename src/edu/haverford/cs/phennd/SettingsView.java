package edu.haverford.cs.phennd;

import edu.haverford.cs.phennd.FavoritesView.TabListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsView extends Activity {
	private ListView listViewSettings;
	private String[] all_categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
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
		setContentView(R.layout.activity_settings_view);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Settings");
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

        actionBar.addTab(tabSettings, true);
        actionBar.addTab(tabCategory, 0, false);
        actionBar.addTab(tabFavorites, 1, false);
        actionBar.addTab(tabTags, 2, false);
        actionBar.selectTab(tabSettings);
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
		listViewSettings = (ListView) findViewById(R.id.listViewSettingsTags);
		

		
		
		// Filling the ListView with Strings
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, all_categories);
		listViewSettings.setAdapter(adapter);
		listViewSettings.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// Checking the appropriate boxes
		Boolean thisBoolean;
		for (int i=0;i<all_categories.length;i++) {
			thisBoolean = prefs.getBoolean(all_categories[i], true);
			listViewSettings.setItemChecked(i, thisBoolean);
	    }
		
	
        final CheckBox enableNotifications = (CheckBox) findViewById(R.id.checkbox_enable_notifications);
        enableNotifications.setChecked(true);
        
	}
	
	public void onPause() {
		super.onPause();
		SharedPreferences.Editor editor = prefs.edit();
		for(int i = 0; i < all_categories.length; i ++) {
			editor.putBoolean(all_categories[i], false);
		}
		SparseBooleanArray checkedItems = listViewSettings.getCheckedItemPositions();
		if (checkedItems != null) {
			for (int i = 0; i < checkedItems.size(); i++) {
				if(checkedItems.valueAt(i)) {
					editor.putBoolean(all_categories[i], true);
				}
			}
		}
		editor.apply();
		
	}

		
	public void onEnableNotificationsClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		
		((CheckBox) view).setChecked(checked);
	}
	
	public void update_preferences(String category, Boolean value) {
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putBoolean(category, value);
		
		editor.apply();
	}

}
