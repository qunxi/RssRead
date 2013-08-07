package github.com.qunxi.rssreader.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import github.com.qunxi.rssreader.model.EntityObject;
import github.com.qunxi.rssreader.model.Entry;

public class EntryMapper extends AbstractMapper{

	public EntryMapper(Context context, String database, int version) {
		super(context, database, version);
	}

	public void removeEntries(long feedId, List<Long> entrieIds)
	{
		wdb.beginTransaction();
		for(long id: entrieIds){
			
			String sql = "DELETE FROM " + EntryTable.TABLE_NAME + " WHERE " + EntryTable.FEED_ID + " = " + feedId + " AND " + EntryTable._ID + " = " + id;
			wdb.execSQL(sql);
		}
		wdb.setTransactionSuccessful();
		wdb.endTransaction();
		wdb.close();
	}
	
	protected boolean updateReadState(EntityObject entity){
		Entry entry = (Entry)entity;
		ContentValues values = new ContentValues();
		values.put(EntryTable.UNREAD, entry.isUnread());
		String[] whereArgs ={Long.toString(entry.getId())};
		if(wdb.update(EntryTable.TABLE_NAME, values, EntryTable._ID + " = ?", whereArgs) == 1){
			wdb.close();
			return true;
		}
		wdb.close();
		return false;
	}
	
	@Override
	protected EntityObject doLoad(Cursor c) {
		
		Entry entry = new Entry();
		
		int colid = c.getColumnIndex(EntryTable.LINK);
		entry.setLink(c.getString(colid));
		
		colid = c.getColumnIndex(EntryTable._ID);
		entry.setId(c.getLong(colid));
		
		colid = c.getColumnIndex(EntryTable.UNREAD);
		boolean readState = c.getShort(colid) == 0 ? false : true;
		entry.setUnread(readState);
		
		colid = c.getColumnIndex(EntryTable.TITLE);
		entry.setTitle(c.getString(colid));
		
		colid = c.getColumnIndex(EntryTable.SUMMARY);
		entry.setSummary(c.getString(colid));
		
		colid = c.getColumnIndex(EntryTable.CONTENT);
		entry.setContent(c.getString(colid));

		colid = c.getColumnIndex(EntryTable.UPDATED);
		entry.setUpdated(c.getString(colid));
		
		colid = c.getColumnIndex(EntryTable.FEED_ID);
		entry.setFeedId(c.getLong(colid));
		
		return entry;
	}

	@Override
	protected long doInsert(EntityObject entity) {
		Entry entry = (Entry)entity;
		ContentValues values = new ContentValues();
		values.put(EntryTable.FEED_ID, entry.getFeedId());
		values.put(EntryTable.TITLE, entry.getTitle());
		values.put(EntryTable.LINK, entry.getLink());
		values.put(EntryTable.SUMMARY, entry.getSummary());
		values.put(EntryTable.CONTENT, entry.getContent());
		values.put(EntryTable.UPDATED, entry.getUpdated());
		values.put(EntryTable.UNREAD, entry.isUnread());
		long id= wdb.insert(EntryTable.TABLE_NAME, EntryTable._ID, values);
		entry.setId(id);
		return id;
	}

}

