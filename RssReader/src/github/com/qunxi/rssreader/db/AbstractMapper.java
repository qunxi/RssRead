package github.com.qunxi.rssreader.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractMapper<T> {
	
	protected DatabaseHelper dbHelper = null;
	protected SQLiteDatabase wdb = null;
	protected SQLiteDatabase rdb = null;
	
	protected static final int LIMIT = 10;
	
	public AbstractMapper(Context context)
	{	
		dbHelper = DatabaseHelper.instance(context);
		
		wdb = dbHelper.getWritableDatabase();
		//dbHelper.onUpgrade(wdb, 1, 2);
		rdb = dbHelper.getReadableDatabase();
	}
	
	public abstract List<T> loadAll(long id, int offset);
	
	public abstract T load(long id);
	
	public abstract long insert(T object);
	
	public abstract boolean insertAll(List<T> objects);
	
	public abstract boolean exist(long id);
}
