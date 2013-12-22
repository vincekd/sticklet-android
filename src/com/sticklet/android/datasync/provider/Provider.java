package com.sticklet.android.datasync.provider;

import java.util.logging.Logger;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.sticklet.android.constants.StickletAndroidConstants;
import com.sticklet.android.database.StickletContract;
import com.sticklet.android.database.StickletDbHelper;

public class Provider extends ContentProvider {
	private static final Logger logger = Logger.getLogger(Provider.class.getName());
	private static final UriMatcher uriMatcher;

	private static final int ALL_NOTEBOOKS = 1;
	private static final int ONE_NOTEBOOK = 2;
	private static final int ALL_NOTES = 3;
	private static final int ONE_NOTE = 4;

	private StickletDbHelper dbHelper;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(StickletAndroidConstants.AUTHORITY, "notes", ALL_NOTES);
		uriMatcher.addURI(StickletAndroidConstants.AUTHORITY, "notes/#", ONE_NOTE);
		uriMatcher.addURI(StickletAndroidConstants.AUTHORITY, "notebooks", ALL_NOTEBOOKS);
		uriMatcher.addURI(StickletAndroidConstants.AUTHORITY, "notebooks/#", ONE_NOTEBOOK);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new StickletDbHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ALL_NOTEBOOKS:
			return "vnd.android.cursor.dir/vnd." + StickletAndroidConstants.AUTHORITY + "." + "notebooks";
		case ONE_NOTEBOOK:
			return "vnd.android.cursor.item/vnd." + StickletAndroidConstants.AUTHORITY + "." + "notebooks";
		case ALL_NOTES:
			return "vnd.android.cursor.dir/vnd." + StickletAndroidConstants.AUTHORITY + "." + "notes";
		case ONE_NOTE:
			return "vnd.android.cursor.item/vnd." + StickletAndroidConstants.AUTHORITY + "." + "notes";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		//queryBuilder.setTables(CountriesDb.SQLITE_TABLE);
		String id;
		int match = uriMatcher.match(uri);
		logger.info("" + match);
		switch (match) {
		case ALL_NOTEBOOKS:
			queryBuilder.setTables(StickletContract.Notebook.TABLE_NAME);
			break;
		case ONE_NOTEBOOK:
			queryBuilder.setTables(StickletContract.Notebook.TABLE_NAME);
			id = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(StickletContract.Notebook.COLUMN_NAME_NOTEBOOK_ID + "=" + id);
			break;
		case ALL_NOTES:
			queryBuilder.setTables(StickletContract.Note.TABLE_NAME);
			break;
		case ONE_NOTE:
			queryBuilder.setTables(StickletContract.Note.TABLE_NAME);
			id = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(StickletContract.Note.COLUMN_NAME_NOTE_ID + "=" + id);
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
 	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String table;
		Uri outUri;
		switch (uriMatcher.match(uri)) {
		case ALL_NOTEBOOKS:
			//do nothing
			table = StickletContract.Notebook.TABLE_NAME;
			outUri = StickletContract.Notebook.URI;
			break;
		case ALL_NOTES:
			table = StickletContract.Note.TABLE_NAME;
			outUri = StickletContract.Note.URI;
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		long id = db.insert(table, null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(outUri + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String table, id;
		switch (uriMatcher.match(uri)) {
		case ALL_NOTEBOOKS:
			//do nothing
			table = StickletContract.Notebook.TABLE_NAME;
			break;
		case ONE_NOTEBOOK:
			table = StickletContract.Notebook.TABLE_NAME;
			id = uri.getPathSegments().get(1);
			selection = StickletContract.Notebook.COLUMN_NAME_NOTEBOOK_ID + "=" + id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
			break;
		case ALL_NOTES:
			table = StickletContract.Note.TABLE_NAME;
			break;
		case ONE_NOTE:
			table = StickletContract.Note.TABLE_NAME;
			id = uri.getPathSegments().get(1);
			selection = StickletContract.Note.COLUMN_NAME_NOTE_ID + "=" + id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int deleteCount = db.delete(table, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String table, id;
		switch (uriMatcher.match(uri)) {
		case ALL_NOTEBOOKS:
			//do nothing
			table = StickletContract.Notebook.TABLE_NAME;
			break;
		case ONE_NOTEBOOK:
			table = StickletContract.Notebook.TABLE_NAME;
			id = uri.getPathSegments().get(1);
			selection = StickletContract.Notebook.COLUMN_NAME_NOTEBOOK_ID + "=" + id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
			break;
		case ALL_NOTES:
			table = StickletContract.Note.TABLE_NAME;
			break;
		case ONE_NOTE:
			table = StickletContract.Note.TABLE_NAME;
			id = uri.getPathSegments().get(1);
			selection = StickletContract.Note.COLUMN_NAME_NOTE_ID + "=" + id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		int updateCount = db.update(table, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}
}