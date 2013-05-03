package edu.haverford.cs.phennd;


import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;


public class MainActivity<T> extends Activity {

	private String[] all_categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
	private String[] all_tags = {"Education","Health","Environment","Service-learning","Higher Education","Arts","Nonprofit","Nutrition","Poverty","Civic Engagement","Community Service/Volunteer","Technology","AmeriCorps","Community Development","West","North","Northeast","Northwest","South","Center City","New Jersey","Older adult","Youth","Women","LGBT","Immigrant"};
	private DataManager dataManager;
	ListView displayList;
	String tagsOrCategories = "Categories";
	static String focus = "Categories";
	
	class MyTabListener implements TabListener {
    	private Activity activity;
    	private int LaunchCode = 0;
    	private DataManager dm;
  	
    	public MyTabListener(Activity activity, int launchCode) {
    		this.activity = activity;
    		this.LaunchCode = launchCode;
    		dm = DataManager.getDataManager(getBaseContext());
    	}
    	
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			if (this.LaunchCode == 0) {
				
			}
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
	
		if (this.LaunchCode == 0) {
			focus = "Categories";
			Log.w("PHENND","cats");
			getIntent().putExtra("TabToLaunch", "Categories");
			swapListContent(activity,0);
			} else if (this.LaunchCode == 1) {
				focus = "Favorites";
				getIntent().putExtra("TabToLaunch", "Favorites");
				Log.w("PHENND","favs");
				swapListContent(activity,1);
			} else if (this.LaunchCode == 2) {
				focus = "Tags";
				getIntent().putExtra("TabToLaunch","Tags");
				Log.w("PHENND","tags");
				swapListContent(activity,2);				
			}
		}


		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}
	}

	
	
    protected void swapListContent(Activity activity, Integer listContentCode){
    	if (listContentCode.equals(0)){ // categories
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
	        updateCategories(prefs, all_categories);
	        updateTags(prefs, all_tags);
	        
	        String[] wantedCategories = getWantedCategories(prefs, all_categories);
	        
	        displayList = (ListView)findViewById(R.id.listView1);
	        
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, wantedCategories);
			displayList.setAdapter(adapter); 
	       
	        Thread t = new Thread() { 
	    	   @Override
	    	   public void run() {
	    		   	DataManager dm = DataManager.getDataManager(getBaseContext());
	    	        dm.updateArticles();
	    	   }
	        };
	        t.start();
		       displayList.setOnItemClickListener(new OnItemClickListener() {

		           @Override
		           public void onItemClick(AdapterView<?> parent, View v, int position,
		                   long id) {
		               Intent intent = new Intent(v.getContext(), ArticleListView.class);
		           	String Name = (String) displayList.getItemAtPosition(position);
		           	
		           	intent.putExtra("TagsOrCategories", "Categories");
		           	intent.putExtra("MetaInfo", Name);
		           	startActivityForResult(intent, 0);
		           }

		       });
    	} else if (listContentCode.equals(1)) { // favorites
    		List<String> favoriteArticles = dataManager.getFavorites(); // REPLACE THIS WITH SINGLETON ACCESS TO DATAMANAGER
        	favoriteArticles.remove("You do not currently have any articles favorited. You can find articles in" +
        			"\"Categories\" or \"Tags\", and favorite them by clicking the checkbox in the top left corner.");
	        if(favoriteArticles.isEmpty())
	        	favoriteArticles.add("You do not currently have any articles favorited. You can find articles in" +
	        			"\"Categories\" or \"Tags\", and favorite them by clicking the checkbox in the top left corner.");
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, favoriteArticles);
    		displayList.setAdapter(adapter);
           
    		displayList.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> parent, View v, int position,
                    long id) {
    				Intent intent = new Intent(v.getContext(), ArticleView.class);
    				String storyName = (String) displayList.getItemAtPosition(position);
    				

		           	// Really hacky way of making our "No Favorites" message not break the app when clicked
		           	if (storyName.equals("You do not currently have any articles favorited. You can find articles in" +
		        			"\"Categories\" or \"Tags\", and favorite them by clicking the checkbox in the top left corner."))
	        			return;
    				
    				intent.putExtra("Name", storyName);
    				startActivityForResult(intent, 0);
    			}
        });
    	} else if (listContentCode.equals(2)){ // tags
	        List<String> flaggedTags = dataManager.getFlaggedTags();
	        flaggedTags.remove("You do not currently have any tags selected. You can select them in \"Settings\", located in the top right corner.");
	        if(flaggedTags.isEmpty())
	        	flaggedTags.add("You do not currently have any tags selected. You can select them in \"Settings\", located in the top right corner.");
	        	
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, flaggedTags);
			displayList.setAdapter(adapter); 
			
			displayList.setOnItemClickListener(new OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View v, int position,
	                    long id) {
	                Intent intent = new Intent(v.getContext(), ArticleListView.class);
	            	String tagName = (String) displayList.getItemAtPosition(position);
	            	
	            	// Really hacky way of making our "No Tags" message not break the app when clicked
	            	if (tagName.equals("You do not currently have any tags selected. You can select them in \"Settings\", located in the top right corner."))
            			return;
	            	intent.putExtra("TagsOrCategories", "Tags");
	            	intent.putExtra("MetaInfo", tagName);
	            	startActivityForResult(intent, 0);
	            }

	        }); 	
    	}
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = DataManager.getDataManager(getBaseContext());
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        String tabToPick = "";
        if (extras != null) {
        	tabToPick= extras.getString("TabToLaunch");
        	focus = tabToPick;
        } else {
        	tabToPick = focus;
        	Log.w("PHENND", "Focus is: " + focus);
        }
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("PHENND Update");
        actionBar.setSubtitle("Articles");
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tabCategory = actionBar.newTab();
        tabCategory.setText("Categories").setIcon(R.drawable.shelficon).setTabListener(new MyTabListener(this, 0));
        Tab tabFavorites = actionBar.newTab();
        tabFavorites.setText("Favorites").setIcon(R.drawable.staricon).setTabListener(new MyTabListener(this, 1));
        Tab tabTags = actionBar.newTab();
        tabTags.setText("Tags").setIcon(R.drawable.tagicon).setTabListener(new MyTabListener(this,2));

        actionBar.addTab(tabCategory, true);
        actionBar.addTab(tabFavorites, 1, false);
        actionBar.addTab(tabTags, 2, false);    

        	

        
		if (tabToPick.equals("Favorites")){
			actionBar.setSelectedNavigationItem(1);			
		} else if (tabToPick.equals("Tags")){
			actionBar.setSelectedNavigationItem(2);			
		} else {
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	        updateCategories(prefs, all_categories);
	        updateTags(prefs, all_tags);
	        
	        String[] wantedCategories = getWantedCategories(prefs, all_categories);
	        
	        displayList = (ListView)findViewById(R.id.listView1);
	        
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wantedCategories);
			displayList.setAdapter(adapter); 
	       
	        Thread t = new Thread() { 
	    	   @Override
	    	   public void run() {
	    	        dataManager.updateArticles();
	    	   }
	       };
	       t.start();       
	       
	       displayList.setOnItemClickListener(new OnItemClickListener() {

	           @Override
	           public void onItemClick(AdapterView<?> parent, View v, int position,
	                   long id) {
	               Intent intent = new Intent(v.getContext(), ArticleListView.class);
	           	String Name = (String) displayList.getItemAtPosition(position);
	           	intent.putExtra("TagsOrCategories", tagsOrCategories);
	           	intent.putExtra("MetaInfo", Name);
	           	startActivityForResult(intent, 0);
	           }

	       });
		}
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
        		editor.putBoolean(tags[i], false); // sets them to false by default
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
