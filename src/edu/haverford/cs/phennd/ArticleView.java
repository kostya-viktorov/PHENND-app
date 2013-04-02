package edu.haverford.cs.phennd;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

public class ArticleView extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_view);

		ActionBar actionBar = getActionBar();
        actionBar.setTitle("Article");
        actionBar.setSubtitle("PHENND Update");
        
		Bundle extras = getIntent().getExtras();
		final TextView articleTitle = (TextView) findViewById(R.id.textViewArticleTitle);
		final String articleName = extras.getString("Name");
		articleTitle.setText(articleName);	
		final ArticleData article = DataManager.getArticle(articleName);
		//articleTitle.setText(extras.getString("Name"));	
		// Basically, now you can use articleName to get article info from DataManager and build the page accordingly
		CheckBox favoritesBox = (CheckBox) findViewById(R.id.checkBox1);
		favoritesBox.setChecked(article.isFavorited());
		favoritesBox.setOnClickListener(new OnClickListener() {
			
			  @Override
			  public void onClick(View v) {
				    boolean checked = ((CheckBox) v).isChecked();
				    // Get an ArticleData object from DataManager; call setFavorited(boolean f) on the ArticleData with the value of checked
				    
				    article.setFavorited(checked);
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
