package github.com.qunxi.rssreader.db;

import android.content.Context;

public class MapperRegister {
	
	private static String DatabaseName = "rss.db";
	private static int DatabaseVersion = 1;
	
	/*private static EntryMapper entryMapper = null;
	private static FeedMapper feedMapper = null;
	
	public MapperRegister(Context context){
		entryMapper = new EntryMapper(context, DatabaseName, DatabaseVersion);
		feedMapper = new FeedMapper(context, DatabaseName, DatabaseVersion);
	}
	
	public MapperRegister(Context context, String database, int version){
		DatabaseName = database;
		DatabaseVersion = version;
	}*/
	
	public static EntryMapper entry(Context context){
		return new EntryMapper(context, DatabaseName, DatabaseVersion);
	}
	
	public static FeedMapper feed(Context context){
		return new FeedMapper(context, DatabaseName, DatabaseVersion);
	}
}
