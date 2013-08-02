package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Feed;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;


public interface IFeedBuilder 
{
	public static final String NameSpace = null;
	//atom tag
	public static final String FeedTag = "feed";
	public static final String UpdatedTag = "updated";
	public static final String EntryTag = "entry";
	public static final String SummaryTag = "summary";
	public static final String ContentTag = "content";
	public static final String RelAttribute = "rel";
	public static final String ValueOfRelAttr = "alternate";
	//Rss tag
	public static final String RssTag = "rss";
	public static final String CHANNEL = "channel";
	public static final String LastBuildDate = "lastBuildDate";
	public static final String VersionAttr = "version";
	public static final String ItemTag = "item";
	public static final String DescriptionTag = "description";
	public static final String Publish_Date = "pubDate";
	public static final String Content_Encode="content:encoded";
	//common tag
	public static final String TitleTag = "title";
	public static final String LinkTag = "link";
	public static final String HrefAttribute = "href";
	
	public abstract Feed getFeed(String fromDate) 
					throws XmlPullParserException, IOException;
			
}
