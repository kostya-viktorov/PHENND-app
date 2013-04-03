package edu.haverford.cs.phennd;

import edu.haverford.cs.phennd.FavoritesView.TabListener;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsView extends Activity {

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
        tabSettings.setText("Settings").setIcon(R.drawable.ic_launcher).setTabListener(new TabListener(this, 3));

        actionBar.addTab(tabSettings, true);
        actionBar.addTab(tabCategory, 0, false);
        actionBar.addTab(tabFavorites, 1, false);
        actionBar.addTab(tabTags, 2, false);
        actionBar.selectTab(tabSettings);
        
		// List of Tags, copied from MainActivity
		String[] categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
		ListView listViewSettings = (ListView) findViewById(R.id.listViewSettingsTags);
		
		CheckBox[] categoriesCheckBoxList = new CheckBox[categories.length];
		for (int i = 0; i < categories.length; i++) {
			CheckBox tmpBox = new CheckBox(this);
			tmpBox.setText(categories[i]);
			categoriesCheckBoxList[i] = tmpBox;
		}
		
		
		// Filling the ListView with Strings
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
		//listViewSettings.setAdapter(adapter); 
		
		// Filling the ListView with CheckBoxes
		// Currently broked, each element is shown as a reference to the checkbox...
		ArrayAdapter<CheckBox> checkBoxAdapter = new ArrayAdapter<CheckBox>(this, android.R.layout.simple_list_item_1, categoriesCheckBoxList);
		listViewSettings.setAdapter(checkBoxAdapter); 
		

        final CheckBox enableNotifications = (CheckBox) findViewById(R.id.checkbox_enable_notifications);
        enableNotifications.setChecked(true);
        
	}

	public void onEnableNotificationsClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		/*if (checked)
			((CheckBox) view).setChecked(false);
		else
			((CheckBox) view).setChecked(true);
		*/
		// because apparently fuck logic? (KV)
		((CheckBox) view).setChecked(checked);
	}
	


}
