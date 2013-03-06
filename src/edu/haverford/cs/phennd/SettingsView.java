package edu.haverford.cs.phennd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsView extends Activity {
// did I make it?
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_view);
		
		// List of Tags, copied from MainActivity
		String[] categories = {"Grant Opportunities", "Job Opportunities/AmeriCorps Opportunities", "K-16 Partnerships", "For Students","Miscellaneous","National Conferences & Calls for Proposal","New Resources","Other Local Events and workshops","Partnerships Classifieds","PHENND Events/Activities"};
		ListView listViewSettings = (ListView) findViewById(R.id.listViewSettingsTags);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
		listViewSettings.setAdapter(adapter); 
		
		
		// 3/4 buttons (ignores an attempt to go from settings to settings)
        final Button mainViewButtonSettings = (Button) findViewById(R.id.buttonMainSettings);
		mainViewButtonSettings.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivityForResult(intent, 0);
                        }
                });
		
        final Button tagsViewButtonSettings = (Button) findViewById(R.id.buttonTagsSettings);
		tagsViewButtonSettings.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), TagView.class);
                            startActivityForResult(intent, 0);
                        }
                });
		
		final Button favoritesViewButtonSettings = (Button) findViewById(R.id.buttonFavoritesSettings);
        favoritesViewButtonSettings.setOnClickListener(
                new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(v.getContext(), FavoritesView.class);
                            startActivityForResult(intent, 0);
                        }
                });
		

        final CheckBox enableNotifications = (CheckBox) findViewById(R.id.checkbox_enable_notifications);
        enableNotifications.setChecked(true);
        
	}

	public void onEnableNotificationsClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		/*if (checked)
			((CheckBox) view).setChecked(false);
		else
			((CheckBox) view).setChecked(true);
		*/
		// because apparently fuck logic? (KV)
		((CheckBox) view).setChecked(checked);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings_view, menu);
		return true;
	}

}
