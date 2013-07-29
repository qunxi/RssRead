package github.com.qunxi.rssreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import github.com.qunxi.rssreader.model.EntityObject;
import github.com.qunxi.rssreader.model.Entry;

public class EntryMapper extends AbstractMapper{

	public EntryMapper(Context context, String database, int version) {
		super(context, database, version);
	}

	@Override
	protected EntityObject doLoad(Cursor c) {
		
		Entry entry = new Entry();
		
		int colid = c.getColumnIndex(EntryTable.LINK);
		entry.setLink(c.getString(colid));
		
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
		long id= wdb.insert(EntryTable.TABLE_NAME, EntryTable._ID, values);
		entry.setId(id);
		return id;
	}

	/*protected boolean addAll(List<Entry> entries){
		return false;
	}*/
}

