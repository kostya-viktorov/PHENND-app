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
        
		
		listViewSettings = (ListView) findViewById(R.id.listViewSettingsTags);
		

		
		
		// Filling the ListView with Strings
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, all_categories);
		listViewSettings.setAdapter(adapter); 
		
		listViewSettings.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> a, View view, int position, long id) {

				      CheckedTextView check = (CheckedTextView)view;
				      check.toggle();
				      update_preferences((String)check.getText(), check.isChecked());

				}
		});
		


        final CheckBox enableNotifications = (CheckBox) findViewById(R.id.checkbox_enable_notifications);
        enableNotifications.setChecked(true);
        
        TextView testing = (TextView) findViewById(R.id.testing);
        testing.setText("" + listViewSettings.getChildCount());
        setSomeBoxes(listViewSettings);
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	public void setSomeBoxes(ListView list) {
		int some_index = list.getFirstVisiblePosition();
		CheckedTextView some_item = (CheckedTextView)list.getChildAt(some_index);
		if (some_item != null)
			some_item.toggle();
	}
	
	public void onEnableNotificationsClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		
		((CheckBox) view).setChecked(checked);
	}
	
	public void update_preferences(String category, Boolean value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putBoolean(category, value);
		
		editor.apply();
	}

}
