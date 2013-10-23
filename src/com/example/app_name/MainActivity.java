package com.example.app_name;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	
	Button buttonNew;
	Button buttonHighscore;
	Button buttonExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addListenerOnButton(){
		
		buttonNew = (Button) findViewById(R.id.button1);
		buttonHighscore = (Button) findViewById(R.id.button2);
		buttonExit = (Button) findViewById(R.id.button3);
		
		buttonNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent startNewActivityOpen = new Intent(MainActivity.this, SecondActivity.class);
				startActivity(startNewActivityOpen);
			}
			
		});
		buttonHighscore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent startNewActivityOpen = new Intent(MainActivity.this, HighScoresActivity.class);
				startActivity(startNewActivityOpen);
			}
			
		});
		buttonExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
	            System.exit(0);
			}
			
		});
	}

}
