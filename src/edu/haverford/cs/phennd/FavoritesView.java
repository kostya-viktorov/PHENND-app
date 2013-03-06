package edu.haverford.cs.phennd;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FavoritesView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_view);
		
	       
		List<String> favoriteArticles = DataManager.getFavorites(); // REPLACE THIS WITH SINGLETON ACCESS TO DATAMANAGER
		ListView listView = (ListView) findViewById(R.id.listView3);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favoriteArticles);
		listView.setAdapter(adapter);
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
        
        final Button settingsViewButton3 = (Button) findViewById(R.id.buttonSettings3);
        settingsViewButton3.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), SettingsView.class);
                            startActivityForResult(intent, 0);
                        }
                });
        
        final ListView listOfStories = (ListView)findViewById(R.id.listView3);
        
        listOfStories.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                    long id) {
                Intent intent = new Intent(v.getContext(), ArticleView.class);
            	String storyName = (String) listOfStories.getItemAtPosition(position);
            	intent.putExtra("Name", storyName);
            	startActivityForResult(intent, 0);
            }

        });
  
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_favorites_view, menu);
		return true;
	}

}
