package github.com.qunxi.rssreader.db;

import java.util.List;

import github.com.qunxi.rssreader.model.EntityObject;
import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class FeedMapper extends AbstractMapper
{
	private static long OFFSET = 0;
	private static final int LIMIT = 10;
	
	public FeedMapper(Context context, String database, int version) {
		super(context, database, version);
	}
	
	//this method just query the feed table, not include associate entries.
	@SuppressWarnings("unchecked")
	public List<Feed> getLimitFeeds(int limit, int offset){
		//"Select count(entry.unread) As totleItems, sum(entry.unread) As nureadItems, * from feed join entry on feed.id = entry.feedId group by feed.id"
		String totalExpr = "SUM(" + EntryTable.UNREAD + ") AS " + FeedTable.UNREAD;
		String unReadExpr = "COUNT(" + EntryTable.UNREAD + ") AS " + FeedTable.TOTAL;
		String sql = "SELECT " + totalExpr + ", " + unReadExpr + ", " + FeedTable.TABLE_NAME + ".* " + "FROM " + FeedTable.TABLE_NAME + " JOIN " + EntryTable.TABLE_NAME + " ON " + FeedTable.TABLE_NAME + "." + FeedTable._ID
					 + " = " + EntryTable.TABLE_NAME + "." + EntryTable.FEED_ID + " GROUP BY " + FeedTable.TABLE_NAME + "." + FeedTable._ID;//+ " LIMIT " + limit + " OFFSET " + offset;
		List<Feed> feeds = (List<Feed>) load(wdb.rawQuery(sql, null));
		wdb.close();
		return feeds;
	}
	
	public Feed getFeedById(long feedId, long offset)
	{
		String sql = "SELECT * FROM " + FeedTable.TABLE_NAME + " WHERE " + FeedTable._ID + " = " + feedId;
		List<? extends EntityObject> feeds = load(wdb.rawQuery(sql, null));
		if(feeds.size() == 1)
		{
			Feed feed = (Feed)feeds.get(0);
			feed.setEntries(associateEntries(feedId, offset));
			wdb.close();
			return feed;
		}
		wdb.close();
		return null;
	}
	
	public boolean updateReadState(Entry entry){
		return MapperRegister.entry(context).updateReadState(entry);
	}
	
	@SuppressWarnings("unchecked")
	public List<Entry> associateEntries(long feedId, long offset){
		if(offset != -1){
			OFFSET = offset;
		}
   		String sql = "SELECT * FROM " + EntryTable.TABLE_NAME + " WHERE " + EntryTable.FEED_ID + "=" + feedId
   				+ " ORDER BY " + EntryTable.UPDATED + " DESC " + " LIMIT " + LIMIT + " OFFSET " + OFFSET;
   		List<? extends EntityObject> entries = MapperRegister.entry(context).load(wdb.rawQuery(sql, null));
   		OFFSET += LIMIT;
   		wdb.close();
		return (List<Entry>) entries;
	}
	
	public boolean saveFeed(Feed feed) throws Exception{
		//(isExist(feed))
			//return false;
		if(add(feed) == -1){
			throw new Exception();
		}
		return true;
	}

	public boolean updateFeed(Feed feed) throws Exception{
		ContentValues values =  new ContentValues();
		values.put(FeedTable.UPDATE_DATE, feed.getUpdated());
		String[] whereArgs={Long.toString(feed.getId())};
		wdb.beginTransaction();
		if(wdb.update(FeedTable.TABLE_NAME, values, FeedTable._ID + "=?", whereArgs) == 1){
			if(MapperRegister.entry(context).addAll(feed.getEntries())){
				wdb.setTransactionSuccessful();
				wdb.endTransaction();
				wdb.close();
				return true;
			}
			else{
				wdb.close();
				throw new Exception();
			}
		}
		else{
			wdb.close();
			throw new Exception();
		}
	}
	
	public boolean isExist(String url){
		String sql = "SELECT COUNT(*) FROM " + FeedTable.TABLE_NAME + " WHERE " + FeedTable.URL + " = '" + url + "'";
		Cursor c = wdb.rawQuery(sql, null);
		c.moveToNext();
		if(c.getInt(0) != 0){
			wdb.close();
			return true;
		}
		wdb.close();
		return false;
	}
	
	protected boolean isExist(Feed feed){
		return isExist(feed.getUrl());
	}
	
	@Override
	protected EntityObject doLoad(Cursor c) {
		
			Feed feed = new Feed();
					
			int colid = c.getColumnIndex(FeedTable._ID);
			feed.setId(c.getLong(colid));
			
			colid = c.getColumnIndex(FeedTable.TITLE);
			feed.setTitle(c.getString(colid));
			
			colid = c.getColumnIndex(FeedTable.UPDATE_DATE);
		    feed.setUpdated(c.getString(colid));
			
			colid = c.getColumnIndex(FeedTable.URL);
			feed.setUrl(c.getString(colid));
			
			//
			colid = c.getColumnIndex(FeedTable.TOTAL);
			if(colid != -1)
				feed.setCounts(c.getLong(colid));
			
			colid = c.getColumnIndex(FeedTable.UNREAD);
			if(colid != -1)
				feed.setUnread(c.getLong(colid));
			
			return feed;
	}

	@Override
	protected long doInsert(EntityObject entity) {
		ContentValues values = new ContentValues();
		Feed feed = (Feed)entity;
		values.put(FeedTable.TITLE, feed.getTitle());
		values.put(FeedTable.UPDATE_DATE, feed.getUpdated());
		values.put(FeedTable.URL, feed.getUrl());
		wdb.beginTransaction();
		long feedId = wdb.insert(FeedTable.TABLE_NAME, FeedTable._ID, values);
		if(feedId != -1){
			feed.setId(feedId);
			if(MapperRegister.entry(context).addAll(feed.getEntries())){
				wdb.setTransactionSuccessful();
				wdb.endTransaction();
				return feedId;
			}
		}
		return -1;
	}


}

