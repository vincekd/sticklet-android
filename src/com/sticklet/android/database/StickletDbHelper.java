package com.sticklet.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sticklet.android.database.StickletContract.Note;
import com.sticklet.android.database.StickletContract.Notebook;

public class StickletDbHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Sticklet.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_NOTES =
			"CREATE TABLE " + Note.TABLE_NAME + " (" +
					Note._ID + " INTEGER PRIMARY KEY," +
					Note.COLUMN_NAME_NOTE_ID + TEXT_TYPE + COMMA_SEP +
					Note.COlUMN_NAME_NOTEBOOK + " INTEGER" + COMMA_SEP +
					Note.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
					Note.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
					Note.COLUMN_NAME_CREATED + " DATETIME" + COMMA_SEP +
					Note.COLUMN_NAME_UPDATED + " DATETIME" + COMMA_SEP +
					Note.COLUMN_NAME_COLOR + " INTEGER" + COMMA_SEP +
					Note.COLUMN_NAME_INDEX + " INTEGER" +
					" )";

	private static final String SQL_CREATE_NOTEBOOKS =
			"CREATE TABLE " + Notebook.TABLE_NAME + " (" +
					Notebook._ID + " INTEGER PRIMARY KEY," +
					Notebook.COLUMN_NAME_NOTEBOOK_ID + TEXT_TYPE + COMMA_SEP +
					Notebook.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
					Notebook.COLUMN_NAME_CREATED + " DATETIME" + COMMA_SEP +
					Notebook.COLUMN_NAME_UPDATED + " DATETIME" + COMMA_SEP +
					Notebook.COLUMN_NAME_COLOR + " INTEGER" + COMMA_SEP +
					Notebook.COLUMN_NAME_INDEX + " INTEGER" +
					" )";

	private static final String SQL_DELETE_NOTES = "DROP TABLE IF EXISTS " + Note.TABLE_NAME;
	private static final String SQL_DELETE_NOTEBOOKS = "DROP TABLE IF EXISTS " + Notebook.TABLE_NAME;

	public StickletDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_NOTES);
		db.execSQL(SQL_CREATE_NOTEBOOKS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_NOTES);
		db.execSQL(SQL_DELETE_NOTEBOOKS);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}