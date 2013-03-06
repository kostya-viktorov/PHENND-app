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
		final TextView articleText = (TextView) findViewById(R.id.textView1);
		String articleName = extras.getString("Name");
		articleText.setText(articleName);		
		// Basically, now you can use articleName to get article info from DataManager and build the page accordingly
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_article_view, menu);
		return true;
	}

}
