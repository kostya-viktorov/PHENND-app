package edu.haverford.cs.phennd;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ArticleList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_view);
		
        final Button mainViewButton3 = (Button) findViewById(R.id.buttonMain3);		
        mainViewButton3.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivityForResult(intent, 0);
                        }
                });

        final Button tagsViewButton3 = (Button) findViewById(R.id.buttonTags3);
        tagsViewButton3.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), TagView.class);
                            startActivityForResult(intent, 0);
                        }
                });
        
		List<String> favoriteArticles = DataManager.getFavorites(); // REPLACE THIS WITH SINGLETON ACCESS TO DATAMANAGER
        		ListView listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favoriteArticles);
		listView.setAdapter(adapter); 
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_favorites_view, menu);
		return true;
	}

}
