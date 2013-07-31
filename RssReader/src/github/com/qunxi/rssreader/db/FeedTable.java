package github.com.qunxi.rssreader.db;

public class FeedTable extends Table{
	
	public static final String TABLE_NAME = "feed";
	
	public static final String TITLE = "title";
	public static final String UPDATE_DATE = "updated";
	public static final String URL = "url";
	
	//below tow fields just for select statement, not real fields of table
	public static final String TOTAL = "total";
	public static final String UNREAD = "unread";
	
	public FeedTable(){
		columns.put(_ID, "INTEGER PRIMARY KEY");
		columns.put(TITLE, "TEXT");
		columns.put(UPDATE_DATE, "TEXT");
		columns.put(URL, "TEXT");
	}
	
	@Override
	public String getTableName(){
		return TABLE_NAME;
	}
	
}
