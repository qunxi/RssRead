package github.com.qunxi.rssreader.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import github.com.qunxi.rssreader.model.Entry;

public class EntryMapper extends AbstractMapper<Entry>{

	public EntryMapper(Context context) {
		super(context);
	}

	@Override
	public List<Entry> loadAll(long id, int offset) {
		List<Entry> entries = new ArrayList<Entry>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ")
		   .append(EntryTable.TABLE_NAME)
		   .append(" WHERE ")
		   .append(EntryTable.CATEGORY_ID)
		   .append(" == ")
		   .append(id)
		   .append(" order by updated desc ")
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
			entry.setTitle(c.getString(colid));
			
			colid = c.getColumnIndex(EntryTable.DESCRIPTION);
			entry.setDescription(c.getString(colid));
			
			colid = c.getColumnIndex(EntryTable.CONTENT);
			entry.setContent(c.getString(colid));

			colid = c.getColumnIndex(EntryTable.UPDATED);
			entry.setUpdated(c.getString(colid));
			
			colid = c.getColumnIndex(EntryTable.CATEGORY_ID);
			entry.setCategoryId(c.getLong(colid));
			
			entries.add(entry);
	     }
	     
		c.close();
		
		return entries;
	}

	@Override
	public Entry load(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insert(Entry entry) {
		ContentValues values = new ContentValues();
		values.put(EntryTable.CATEGORY_ID, entry.getCategoryId());
		values.put(EntryTable.TITLE, entry.getTitle());
		values.put(EntryTable.LINK, entry.getLink());
		values.put(EntryTable.DESCRIPTION, entry.getDescription());
		values.put(EntryTable.CONTENT, entry.getContent());
		values.put(EntryTable.UPDATED, entry.getUpdated());
		return wdb.insert(EntryTable.TABLE_NAME, EntryTable.ID, values);
	}

	@Override
	public boolean insertAll(List<Entry> entries) {
		
		wdb.beginTransaction();
		for(Entry entry : entries){
			insert(entry);
		}
		wdb.setTransactionSuccessful();
		wdb.endTransaction();
		return true;
	}

	@Override
	public boolean exist(long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

