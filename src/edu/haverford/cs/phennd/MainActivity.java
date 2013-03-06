package edu.haverford.cs.phennd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	
	DataManager dataManager = new DataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
		listView.setAdapter(adapter); 

        final Button tagViewButton = (Button) findViewById(R.id.buttonTags1);
        tagViewButton.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), TagView.class);
                            startActivityForResult(intent, 0);
                        }
                });

        final Button favoritesViewButton = (Button) findViewById(R.id.buttonFavorites1);
        favoritesViewButton.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), FavoritesView.class);
                            startActivityForResult(intent, 0);
                        }
                });
        
        final Button settingsViewButton = (Button) findViewById(R.id.buttonSettings1);
        settingsViewButton.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), SettingsView.class);
                            startActivityForResult(intent, 0);
                        }
                });
        
       
       // I think that this is why sometimes articles don't load - I might be crazy (KV)
       Thread t = new Thread() { 
    	   @Override
    	   public void run() {
    	        DataManager.updateArticles();
    	   }
       };
       t.start();


        final ListView listOfCategories = (ListView)findViewById(R.id.listView1);
        
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
