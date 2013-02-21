package edu.haverford.cs.phennd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class FavoritesView extends Activity {

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_favorites_view, menu);
		return true;
	}

}
