package com.app_name;

import java.util.ArrayList;
import java.util.List;

import com.app_name.R;
import com.app_name.db.AbstractDBWorker;
import com.app_name.db.DBWorker;
import com.app_name.model.ResultRow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HighScoresActivity extends Activity {
	static final int[] ids = { R.id.textView3, R.id.textView4, R.id.textView5,
			R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9,
			R.id.textView10, R.id.textView11, R.id.textView12, R.id.textView13,
			R.id.textView14, R.id.textView15, R.id.textView16, R.id.textView17,
			R.id.textView18, R.id.textView19, R.id.textView20, R.id.textView21,
			R.id.textView22 };
	AbstractDBWorker adb;
	List<TextView> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores_activity);
		adb = new DBWorker(getApplicationContext());
		init();
	}

	private void init() {
		list = new ArrayList<TextView>();
		for (int i = 0; i < ids.length; i++) {
			TextView v = (TextView) findViewById(ids[i]);
			list.add(v);
		}
		List<ResultRow> l = adb.selectUsersStatistics();
		int i = 0;
		for (ResultRow r : l) {
			list.get(i).setText(r.getName());
			list.get(i + 10).setText(String.valueOf(r.getTime()));
			i++;
		}

		Button clearButton = (Button) findViewById(R.id.button_newgame);
		clearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adb.dropUsersStatistics();
				for (TextView tx : list) {
					tx.setText("...");
				}
			}
		});
	}
}
