package com.sticklet.android.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.sticklet.android.constants.StickletAndroidConstants;

public final class StickletContract {
	public StickletContract() {}
	
	public static final class Note implements BaseColumns {
		public static final String TABLE_NAME = "notes";
		public static final Uri URI = Uri.parse("content://" + StickletAndroidConstants.AUTHORITY + "/notes");
        public static final String COLUMN_NAME_NOTE_ID = "id";
        public static final String COlUMN_NAME_NOTEBOOK = "notebook";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_CREATED = "created";
        public static final String COLUMN_NAME_UPDATED = "updated";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_INDEX = "`index`";
	}
	
	public static final class Notebook implements BaseColumns {
		public static final String TABLE_NAME = "notebooks";
		public static final Uri URI = Uri.parse("content://" + StickletAndroidConstants.AUTHORITY + "/notebooks");
        public static final String COLUMN_NAME_NOTEBOOK_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CREATED = "created";
        public static final String COLUMN_NAME_UPDATED = "updated";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_INDEX = "`index`";
	}
}