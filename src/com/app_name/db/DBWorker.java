package com.app_name.db;

import java.util.ArrayList;
import java.util.List;

import com.app_name.model.ResultRow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBWorker extends AbstractDBWorker {
	private static final String DATABASE_NAME = "barleybreak";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "table_name";
	private static final String TABLE_NAME_ID = "id";
	private static final String TABLE_NAME_NICKNAME = "name";
	private static final String TABLE_NAME_TIME = "time";
	private static final int MAX_RESULT = 10;
	private static final String CREATE_TABLE_NAME = "CREATE  TABLE  IF NOT EXISTS "
			+ TABLE_NAME
			+ "("
			+ TABLE_NAME_ID
			+ " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
			+ TABLE_NAME_NICKNAME
			+ " VARCHAR NOT NULL ,"
			+ TABLE_NAME_TIME
			+ " INTEGER NOT NULL );";
	private static final String DROP_TABLE_NAME = "DROP TABLE IF EXISTS "
			+ TABLE_NAME + ";";

	public DBWorker(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE_NAME);

		onCreate(db);
	}

	@Override
	public void insertUsersInfo(String name, Integer time) {
		SQLiteDatabase db = this.getReadableDatabase();
		Integer count = 0;
		Integer maxTime = Integer.MAX_VALUE;
		Integer id = -1;
		String selectQuery = "SELECT COUNT(*),MAX(time),id FROM " + TABLE_NAME
				+ ";";
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			count = c.getInt(0);
			maxTime = c.getInt(1);
			id = c.getInt(2);
		}
		if (count < MAX_RESULT) {
			maxTime = Integer.MAX_VALUE;
		}
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TABLE_NAME_NICKNAME, name);
		values.put(TABLE_NAME_TIME, time);
		if (time < maxTime) {
			if (count >= MAX_RESULT) {
				db.delete(TABLE_NAME, TABLE_NAME_ID + "=" + id, null);
			}
			db.insert(TABLE_NAME, null, values);
		}
	}

	@Override
	public List<ResultRow> selectUsersStatistics() {
		List<ResultRow> l = new ArrayList<ResultRow>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NAME
				+ " ORDER BY TIME ASC;";
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				ResultRow r = new ResultRow();
				r.setTime(c.getInt(c.getColumnIndex(TABLE_NAME_TIME)));
				r.setName(c.getString(c.getColumnIndex(TABLE_NAME_NICKNAME)));
				l.add(r);
			} while (c.moveToNext());
		}
		return l;
	}

	/* Truncate */
	@Override
	public void dropUsersStatistics() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
	}

}
