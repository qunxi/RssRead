package github.com.qunxi.rssreader.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import github.com.qunxi.rssreader.model.Entry;

public class EntryMapper 
{
	private DatabaseHelper dbHelper = null;
	private SQLiteDatabase wdb = null;
	private SQLiteDatabase rdb = null;
	
	private static final int LIMIT = 10;
	
	public EntryMapper(Context context){
		dbHelper = DatabaseHelper.instance(context);
		wdb = dbHelper.getWritableDatabase();
		rdb = dbHelper.getReadableDatabase();
	}
	
	//private Entry load(long id){
	//	return null;
	//}
	
	public List<Entry> loadEntries(int offset)
	{
		List<Entry> entries = new ArrayList<Entry>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ")
		   .append(EntryTable.TABLE_NAME)
		   .append(" LIMIT ")
		   .append(LIMIT)
		   .append(" OFFSET ")
		   .append(offset);
		Cursor c = rdb.rawQuery(sql.toString(), null);
		
		while (c.moveToNext()) {
			Entry entry = new Entry();
			
			int colid = c.getColumnIndex(EntryTable.LINK);
			entry.setLink(c.getString(colid));
			
			colid = c.getColumnIndex(EntryTable.TITLE);
			entry.setDescription(c.getString(colid));
			
			colid = c.getColumnIndex(EntryTable.DESCRIPTION);
			entry.setDescription(c.getString(colid));
			
			colid = c.getColumnIndex(EntryTable.CONTENT);
			entry.setContent(c.getString(colid));

			entries.add(entry);
	     }
	     
		c.close();
		
		return entries;
	}
	
	public long insert(Entry entry, long categoryId)
	{
		ContentValues values = new ContentValues();
		values.put(EntryTable.CATEGORY_ID, categoryId);
		values.put(EntryTable.TITLE, entry.getTitle());
		values.put(EntryTable.LINK, entry.getLink());
		values.put(EntryTable.DESCRIPTION, entry.getDescription());
		values.put(EntryTable.CONTENT, entry.getContent());
		return wdb.insert(EntryTable.TABLE_NAME, EntryTable.ID, values);
	}
	
	public long batchInsert(List<Entry> entries, long categoryId){
		long id = -1;
		wdb.beginTransaction();
		for(Entry entry : entries){
			id = insert(entry, categoryId);
		}
		wdb.setTransactionSuccessful();
		wdb.endTransaction();
		return id;
	}
	//private boolean update(Entry entry){
	//	return false;
	//}
	
	//private boolean remove(Entry entry){
	//	return false;
	//}
}
