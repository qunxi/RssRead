package github.com.qunxi.rssreader.db;

public class EntryTable extends Table{
	
	public static final String TABLE_NAME = "entry";
	
	public static final String TITLE = "title";
	public static final String LINK = "link";
	public static final String UNREAD = "unread";
	public static final String CONTENT = "content";
	public static final String UPDATED = "updated";
	public static final String SUMMARY = "summary";	// this field just for display summary, not standard tag of rss or atom.
	public static final String FEED_ID = "feed_id";
	
	
	public EntryTable(){
		columns.put(_ID, "INTEGER PRIMARY KEY");
		columns.put(TITLE, "TEXT");
		columns.put(LINK, "TEXT");
		columns.put(UNREAD, "INTEGER");	//unread is 1, read is 0
		columns.put(UPDATED, "TEXT");	//format(yyyy-MM-dd-HH:mm:ss)
		columns.put(CONTENT, "TEXT");
		columns.put(SUMMARY, "TEXT");
		columns.put(FEED_ID, "INTEGER");
	};
	
	@Override
	public String getTableName(){
		return TABLE_NAME;
	}
	
}
