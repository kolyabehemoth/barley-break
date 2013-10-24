package com.app_name;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app_name.R;
import com.app_name.db.AbstractDBWorker;
import com.app_name.db.DBWorker;
import com.app_name.model.LikeModel;
import com.app_name.view.AbstarctOwnView;
import com.app_name.view.OwnView;

public class GameActivity extends AbstractGameActivity {
	static final int[] ids = { R.id.ownView1, R.id.ownView2, R.id.ownView3,
			R.id.ownView4, R.id.ownView5, R.id.ownView6, R.id.ownView7,
			R.id.ownView8, R.id.ownView9, R.id.ownView10, R.id.ownView11,
			R.id.ownView12, R.id.ownView13, R.id.ownView14, R.id.ownView15 };
	LikeModel lk;
	TextView tx;
	View selected_item;
	long time = 0;
	long beginTime;
	Object obj = new Object();
	List<OwnView> l;
	AbstractDBWorker db;
	boolean flag = false;
	// runs without a timer by reposting this handler at the end of the runnable
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {

		@Override
		public void run() {
			long temp = System.currentTimeMillis() - beginTime + time;
			int seconds = (int) (temp / 1000);
			int minutes = seconds / 60;
			int milisec = (int) temp % 1000;
			seconds = seconds % 60;
			tx.setText(String.format("%d:%02d:%03d", minutes, seconds, milisec));
			timerHandler.postDelayed(this, 20);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		displayF();
		tx = (TextView) findViewById(R.id.textView);
		db = new DBWorker(this);
		beginTime = System.currentTimeMillis();
		timerHandler.postDelayed(timerRunnable, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		beginTime = System.currentTimeMillis();
	}

	@Override
	protected void onPause() {
		super.onPause();
		long temp = System.currentTimeMillis();
		time += (temp - beginTime);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!flag) {
			timerHandler.removeCallbacks(timerRunnable);
		}
	}

	private void dispayDialogWindow() {
		time += System.currentTimeMillis() - beginTime;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter your name:");
		final EditText input = new EditText(this);
		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(10);
		input.setFilters(filters);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);
		builder.setMessage("Your result is: " + time + " ms");
		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (input.getText().toString().length() > 0) {
					db.insertUsersInfo(input.getText().toString(), (int) time);
					finish();
				}
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

	private void displayF() {
		int width = getBaseContext().getResources().getDisplayMetrics().widthPixels;
		int height = getBaseContext().getResources().getDisplayMetrics().heightPixels;
		l = new ArrayList<OwnView>();
		float parametr = 0;
		float margeLeft = 0;
		float margeTop = 0;
		parametr = (float) (width / 4 - width / 10);
		margeLeft = width / 2 - 2 * parametr - parametr / 2;
		margeTop = height / 2 - 2.5f * parametr;

		for (int i = 0, j = 0; i < ids.length; i++) {
			OwnView v = (OwnView) findViewById(ids[i]);
			v.setSecondActivity(this);
			l.add(v);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v
					.getLayoutParams();
			params.width = (int) parametr;
			params.height = params.width;
			if (i % 4 == 0 && i != 0) {
				j++;
			}
			float temp = margeTop + j * parametr + j * 10;
			params.setMargins((int) margeLeft + i % 4 * (int) parametr + i % 4
					* 10, (int) ((int) temp > 0 ? temp : (temp + 50)), 0, 0);
			v.setLayoutParams(params);
			v.setSize(params.width);
			v.invalidate();
		}

		lk = new LikeModel();
		lk.fill();
		int[][] arr = lk.getArr();
		for (int i = 0; i < LikeModel.size; i++) {
			for (int j = 0; j < LikeModel.size
					&& (j != LikeModel.size - 1 || i != LikeModel.size - 1); j++) {
				int t = i * LikeModel.size + j;
				l.get(t).setText(arr[i][j] + "");
				l.get(t).setLocationRel(new Point(i, j));
			}
		}
		refresh();
		tx = (TextView) findViewById(R.id.textview_game_name);
	}

	private void refresh() {
		Point p0 = lk.getPoint();
		for (OwnView o : l) {
			Point p = o.getLocationRel();
			if (p.x - p0.x == 1 && p.y == p0.y) {
				o.setDirectionToMove(2);
				continue;
			} else if (p0.x - p.x == 1 && p.y == p0.y) {
				o.setDirectionToMove(0);
				continue;
			}
			if (p.y - p0.y == 1 && p.x == p0.x) {
				o.setDirectionToMove(1);
				continue;
			} else if (p0.y - p.y == 1 && p.x == p0.x) {
				o.setDirectionToMove(3);
				continue;
			}
			o.setDirectionToMove(-1);
		}
	}

	@Override
	public void recalculate(AbstarctOwnView aov) {
		synchronized (obj) {
			if (!lk.isResolved()) {
				if (lk.move(aov.getDirectionToMove())) {
					aov.draw();
					refresh();
				}
				if (lk.isResolved()) {
					timerHandler.removeCallbacks(timerRunnable);
					flag = true;
					dispayDialogWindow();
				}
			}
		}
	}
}
