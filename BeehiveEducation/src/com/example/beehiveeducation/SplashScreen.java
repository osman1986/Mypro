package com.example.beehiveeducation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity
{

	private static int SPLASH_TIME_OUT=3000;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_splash);
	    
	    new Handler().postDelayed(new Runnable()
		{
			
			@Override
			public void run()
			{
				Intent in=new Intent(SplashScreen.this,MainActivity.class);
				startActivity(in);
				finish();
				
			}
		}, SPLASH_TIME_OUT);
	    
	
	   
	}

}
