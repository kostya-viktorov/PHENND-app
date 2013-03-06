package edu.haverford.cs.phennd;

import java.lang.reflect.Array;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TagView extends Activity {

    List<String> flaggedTags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_view);
		
        final Button mainViewButton2 = (Button) findViewById(R.id.buttonMain2);		
        mainViewButton2.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivityForResult(intent, 0);
                        }
                });

        final Button favoritesViewButton2 = (Button) findViewById(R.id.buttonFavorites2);
        favoritesViewButton2.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), FavoritesView.class);
                            startActivityForResult(intent, 0);
                        }
                });
        flaggedTags = DataManager.getFlaggedTags(); // REPLACE THIS WITH SINGLETON ACCESS TO DATAMANAGER
        ListView listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, flaggedTags);
		listView.setAdapter(adapter); 
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tag_view, menu);
		return true;
	}

}
