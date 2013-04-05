package edu.haverford.cs.phennd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PHENNDDbOpenHelper extends SQLiteOpenHelper {
//String _eventDate, String _eventLocation
	public static final String DATABASE_NAME = "phennd.db";
	public static final String DATABASE_TABLE = "Articles";
	public static final int DATABASE_VERSION = 1;
	public static final String KEY_ID = "primary_key";
	public static final String COL_PUBDATE = "pubDate";
	public static final String COL_CONTENTS = "contents";
	public static final String COL_URL = "url";
	public static final String COL_TITLE = "title";
	public static final String COL_CREATOR = "creator";
	public static final String COL_CATEGORY = "category";
	public static final String COL_EVENTDATE = "eventDate";
	public static final String COL_EVENTLOCATION = "eventLocation";
	public static final String COL_FAVORITED = "favorited";
	public static final String COL_TAGS = "tags";
	public static final String COL_INSERT_DATE = "insertDate";
	
	private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + 
			" (" + KEY_ID + " integer primary key autoincrement, " +
			COL_PUBDATE + " text, " + COL_CONTENTS + " text, " + COL_URL + " text, " + 
			COL_TITLE + " text, " +  COL_CREATOR + " text, " + COL_CATEGORY + " text, " + 
			COL_EVENTDATE + " text, " + COL_EVENTLOCATION + " text, " + COL_TAGS + " text, " + COL_FAVORITED + " int, " + COL_INSERT_DATE  + " int);";
	
	PHENNDDbOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w("PHENNDDb", "Creating DB?");
		db.execSQL(DATABASE_CREATE);
		Log.w("PHENNDDb", "Created DB.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("PHENNDDb", "Upgrading from version " + oldVersion + " to " + newVersion + ", destroying all data.");
		db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		onCreate(db);

	}

}
