package edu.haverford.cs.phennd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		List<String> title1ItemsList = new ArrayList<String>();
		title1ItemsList.add("item 1.1");
		title1ItemsList.add("item 1.2");
		title1ItemsList.add("item 1.3");

		map.put("Grant Opportunities", title1ItemsList);

		List<String> title2ItemsList = new ArrayList<String>();
		title2ItemsList.add("item 2.1");
		title2ItemsList.add("item 2.2");
		title2ItemsList.add("item 2.3");

		map.put("Job Opportunities/AmeriCorps Opportunities", title2ItemsList);
		
		List<String> title3ItemsList = new ArrayList<String>();
		title3ItemsList.add("item 2.1");
		title3ItemsList.add("item 2.2");
		title3ItemsList.add("item 2.3");

		map.put("K-16 Partnerships", title3ItemsList);
		
		List<String> title4ItemsList = new ArrayList<String>();
		title4ItemsList.add("item 2.1");
		title4ItemsList.add("item 2.2");
		title4ItemsList.add("item 2.3");

		map.put("For Students", title4ItemsList);
		
		List<String> title5ItemsList = new ArrayList<String>();
		title5ItemsList.add("item 2.1");
		title5ItemsList.add("item 2.2");
		title5ItemsList.add("item 2.3");

		map.put("Miscellaneous", title5ItemsList);
		
		List<String> title6ItemsList = new ArrayList<String>();
		title6ItemsList.add("item 2.1");
		title6ItemsList.add("item 2.2");
		title6ItemsList.add("item 2.3");

		map.put("National Conferences & Calls for Proposal", title6ItemsList);
		
		List<String> title7ItemsList = new ArrayList<String>();
		title7ItemsList.add("item 2.1");
		title7ItemsList.add("item 2.2");
		title7ItemsList.add("item 2.3");

		map.put("New Resources", title7ItemsList);
		
		List<String> title8ItemsList = new ArrayList<String>();
		title8ItemsList.add("item 2.1");
		title8ItemsList.add("item 2.2");
		title8ItemsList.add("item 2.3");

		map.put("Other Local Events and Workshops", title8ItemsList);
		
		List<String> title9ItemsList = new ArrayList<String>();
		title9ItemsList.add("item 2.1");
		title9ItemsList.add("item 2.2");
		title9ItemsList.add("item 2.3");

		map.put("Partnerships Classifieds", title9ItemsList);
		
		List<String> title10ItemsList = new ArrayList<String>();
		title10ItemsList.add("item 2.1");
		title10ItemsList.add("item 2.2");
		title10ItemsList.add("item 2.3");

		map.put("PHENND Events/Activities", title10ItemsList);
		
		//ExpandableListAdapter expandAdapter = expandlistView.getExpandableListAdapter();
		
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
       Thread t = new Thread() { 
    	   @Override
    	   public void run() {
    	        DataManager.updateArticles();
    	   }
       };
       t.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    /*
    public void activities(View view) {
    	Intent intent = new Intent(this, TagView.class);
    	startActivity(intent);
    }
    */
    
}
