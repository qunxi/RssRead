package github.com.qunxi.rssreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	
	private static final String[] tableClassName = new String[] {
		"github.com.qunxi.rssreader.db.EntryTable",
		"github.com.qunxi.rssreader.db.FeedTable"//add new table name to be here
	};
	
	private static DatabaseHelper instance = null;
	
	public static DatabaseHelper instance(Context context, String database, int version){
		if(instance == null){
			instance = new DatabaseHelper(context, database, version);
		}
		return instance;
	}
	
	private DatabaseHelper(Context context, String database, int version) {
		super(context, database, null, version);
	}
	
	@Override  
    public void onCreate(SQLiteDatabase db) {
		try{
			for(int i = 0; i < tableClassName.length; ++i){
				Table table = (Table) Class.forName(tableClassName[i]).newInstance();
				String sql = table.createTableSQL();
				db.execSQL(sql);
			}
		}
		catch(Exception e){
			
		}
	}
	
	@Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try{
			for(int i = 0; i < tableClassName.length; ++i){
				Table table = (Table) Class.forName(tableClassName[i]).newInstance();
				String sql = table.dropTableSQL();
				db.execSQL(sql);
			}
		}
		catch(Exception e){
			
		}
        onCreate(db);
	} 
}
