package edu.haverford.cs.phennd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	private static final int SPLASH_LENGTH = 1000;
	 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
 
        Handler handler = new Handler(); 
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
 
 
                finish();              
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
               }       
          
        }, SPLASH_LENGTH); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called
 
    }
 
}
