package edu.haverford.cs.phennd;

import edu.haverford.cs.phennd.SettingsView.TabListener;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleView extends Activity {

	DataManager dm;
	
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
		setContentView(R.layout.activity_article_view);
		dm = DataManager.getDataManager(getBaseContext());
       
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

        actionBar.addTab(tabCategory, 0, false);
        actionBar.addTab(tabFavorites, 1, false);
        actionBar.addTab(tabTags, 2, false);

		Bundle extras = getIntent().getExtras();
		final TextView articleTitle = (TextView) findViewById(R.id.textViewArticleTitle);
		final TextView articleText = (TextView)findViewById(R.id.textViewArticleText);
		final String articleName = extras.getString("Name");
		articleTitle.setText(articleName);	
		final ArticleData article = dm.getArticle(articleName);

		articleText.setText(article.getContent());
		
		
		CheckBox favoritesBox = (CheckBox) findViewById(R.id.checkBox1);
		favoritesBox.setChecked(article.isFavorited());
		favoritesBox.setOnClickListener(new OnClickListener() {
			
			  @Override
			  public void onClick(View v) {
				    boolean checked = ((CheckBox) v).isChecked();
				    // Get an ArticleData object from DataManager; call setFavorited(boolean f) on the ArticleData with the value of checked
				    
				    
				    if (checked) {
				    	dm.addFavorite(article.getTitle());
				    }
 				    if (article.isFavorited() == true) {
				    	System.out.println("Article is favorited, do stuff here.");
				    } else {
				    	System.out.println("Article is unfavorited, do stuff here.");
				    }
		 
			  }
			});
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_article_view, menu);
		return true;
	}

	public void onFavoriteCheckboxClicked(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    if (checked == true){
	    	System.out.println("Box is now checked!");
	    } else {
	    	System.out.println("Box is now unchecked!");
	    }
	    // Get an ArticleData object from DataManager; call setFavorited(boolean f) on the ArticleData with the value of checked
	    // ArticleData article = DataManager.getArticle(articleTitle);
	    // article.setFavorited(checked);
	    // if (article.isFavorited == true) {
	    // System.out.println("Article is favorited!")
	    // } else {
	    // System.out.println("Article is unfavorited!")
	    // }
	}
	    
}
