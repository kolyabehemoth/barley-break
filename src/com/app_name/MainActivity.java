package com.app_name;

import com.app_name.R;

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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addListenerOnButton() {

		buttonNew = (Button) findViewById(R.id.button_newgame);
		buttonHighscore = (Button) findViewById(R.id.button_highscores);
		buttonExit = (Button) findViewById(R.id.button_exit);

		buttonNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent startNewActivityOpen = new Intent(MainActivity.this,
						GameActivity.class);
				startActivity(startNewActivityOpen);
			}

		});
		buttonHighscore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent startNewActivityOpen = new Intent(MainActivity.this,
						HighScoresActivity.class);
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
