package com.example.app_name.db;

import java.util.List;

import com.example.app_name.model.ResultRow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractDBWorker extends SQLiteOpenHelper{

	public AbstractDBWorker(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	public abstract void insertUsersInfo(String name, Integer time);
	public abstract List<ResultRow> selectUsersStatistics();
	public abstract void dropUsersStatistics();
}
