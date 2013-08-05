package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Feed;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;


public interface IFeedBuilder 
{
	public static final String NameSpace = null;
	//atom tag
	public static final String FeedTag = "feed";
	public static final String RssTag = "rss";
	
	public Feed getFeed(String fromDate) 
					throws XmlPullParserException, IOException;
	//public Entry generateEntry() throws XmlPullParserException, IOException;
}
