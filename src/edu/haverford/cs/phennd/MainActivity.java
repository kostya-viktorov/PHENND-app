package edu.haverford.cs.phennd;

//import edu.haverford.cs.phennd.NotificationService;
import java.util.Arrays;

import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity<T> extends Activity {
	
	DataManager dataManager;
	private String[] all_categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
	private String[] all_tags = {"Education","Health","Environment","Service-learning","Higher Education","Arts","Nonprofit","Nutrition","Poverty","Civic Engagement","Community Service/Volunteer","Technology","AmeriCorps","Community Development","West","North","Northeast","Northwest","South","Center City","New Jersey","Older adult","Youth","Women","LGBT","Immigrant"};
	

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
        setContentView(R.layout.activity_main);
        dataManager = new DataManager(getBaseContext());
        
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Article Categories");
        actionBar.setSubtitle("PHENND Update");
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tabCategory = actionBar.newTab();
        tabCategory.setText("Categories").setIcon(R.drawable.shelficon).setTabListener(new TabListener(this,0));
        Tab tabFavorites = actionBar.newTab();
        tabFavorites.setText("Favorites").setIcon(R.drawable.staricon).setTabListener(new TabListener(this, 1));
        Tab tabTags = actionBar.newTab();
        tabTags.setText("Tags").setIcon(R.drawable.tagicon).setTabListener(new TabListener(this, 2));
        Tab tabSettings = actionBar.newTab();
        tabSettings.setText("Settings").setTabListener(new TabListener(this, 3));

        actionBar.addTab(tabCategory, true);
        actionBar.addTab(tabFavorites, 1, false);
        actionBar.addTab(tabTags, 2, false);
        actionBar.addTab(tabSettings, 3, false);
        actionBar.selectTab(tabCategory);
        
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        updateCategories(prefs, all_categories);
        updateTags(prefs, all_tags);
        
        String[] wantedCategories = getWantedCategories(prefs, all_categories);
        
        final ListView listOfCategories = (ListView) findViewById(R.id.listView1);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wantedCategories);
		listOfCategories.setAdapter(adapter); 
       
       

       Thread t = new Thread() { 
    	   @Override
    	   public void run() {
    	        DataManager.updateArticles();
    	   }
       };
       t.start();


        
        listOfCategories.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                    long id) {
                Intent intent = new Intent(v.getContext(), ArticleListView.class);
            	String categoryName = (String) listOfCategories.getItemAtPosition(position);
            	intent.putExtra("TagsOrCategories", "Categories");
            	intent.putExtra("MetaInfo", categoryName);
            	startActivityForResult(intent, 0);
            }

        });
        
    }
	public void onResume() {
	    super.onResume();
	 /* TODO: make this match what we want...
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    int minutes = prefs.getInt("interval", 1);
	    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
	    Intent i = new Intent(this, NotificationService.class);
	    PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
	    am.cancel(pi);
	    // by my own convention, minutes <= 0 means notifications are disabled
	    if (minutes > 0) {
	        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
	            SystemClock.elapsedRealtime() + minutes*60*1000,
	            minutes*60*1000, pi);
	    } */
	}

	
	public void updateCategories(SharedPreferences prefs, String[] categories) {
		SharedPreferences.Editor editor = prefs.edit();
		
        //puts categories into SharedPreferences if need be
        if (!prefs.getBoolean("haveRunCats", false)) {
        	for(int i = 0; i < categories.length; i++) {
        		editor.putBoolean(categories[i], true);
        	}
        	editor.putBoolean("haveRunCats", true);
        }
        editor.apply();
	}
	
	public void updateTags(SharedPreferences prefs, String[] tags) {
		SharedPreferences.Editor editor = prefs.edit();
		
        //puts categories into SharedPreferences if need be
        if (!prefs.getBoolean("haveRunTags", false)) {
        	for(int i = 0; i < tags.length; i++) {
        		editor.putBoolean(tags[i], true);
        	}
        	editor.putBoolean("haveRunTags", true);
        }
        editor.apply();
	}
	
	public String[] getWantedCategories(SharedPreferences prefs, String[] categories) {
		int size = categories.length;
		int j = 0;
		String[] wantedCategories = new String[size];
		String[] answer;
		
		for (int i = 0; i < size; i++) {
			if (prefs.getBoolean(categories[i], false)) {
				wantedCategories[j] = categories[i];
				j++;
			}
		}
		
		answer = Arrays.copyOfRange(wantedCategories, 0, j);
		return answer;
	}

}
