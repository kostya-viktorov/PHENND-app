package edu.haverford.cs.phennd;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ArticleView extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_view);
		Bundle extras = getIntent().getExtras();
		final TextView articleTitle = (TextView) findViewById(R.id.textViewArticleTitle);

		articleTitle.setText(extras.getString("Name"));	
		//articleText.setText(extras.getString(""));
		// Basically, now you can use articleName to get article info from DataManager and build the page accordingly
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_article_view, menu);
		return true;
	}

}
