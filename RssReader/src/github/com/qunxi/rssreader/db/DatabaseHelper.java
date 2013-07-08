package github.com.qunxi.rssreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private final static String DATABASE_NAME = "rss.db";
	private final static int DATABASE_VERSION = 1;
	
	private final static String CATEGORY_TABLE_SQL= "CREATE TABLE category (id INTEGER PRIMARY KEY, title TEXT, count INTEGER, updated TEXT)";
	private final static String ENTRY_TABLE_SQL = "CREATE TABLE entry (id INTEGER PRIMARY KEY, categoryId INTEGER, title TEXT, link TEXT, description TEXT, content TEXT)";
	
	
	private static DatabaseHelper instance = null;
	
	public static DatabaseHelper instance(Context context){
		if(instance == null){
			instance = new DatabaseHelper(context);
		}
		return instance;
	}
	
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override  
    public void onCreate(SQLiteDatabase db) {  
		db.execSQL(CATEGORY_TABLE_SQL);
		db.execSQL(ENTRY_TABLE_SQL);
	}
	
	@Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
		db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS entry");
        onCreate(db);
	}
	
	//private String  
}
