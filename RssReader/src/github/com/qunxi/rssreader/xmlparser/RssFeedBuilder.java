package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.utils.DateUtils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class RssFeedBuilder extends AbstractFeedParser 
{
	public RssFeedBuilder(XmlPullParser parser){
		super(parser);
		
	}
	
	@Override
	protected void initializeTag(){
		this.LatestUpdateDateTag = "lastBuildDate";
		this.EntryTag = "item";
		this.DescriptionTag = "description";
		this.EntryUpdateDateTag = "pubDate";
		this.ContentTag="content:encoded";
	}
	
	@Override
	protected String getLink() throws XmlPullParserException, IOException 
	{
		return readText(LinkTag);
	}
	
	@Override
	protected String normalizeDate(String srcDate){
		return DateUtils.RssDateConvert(srcDate);
	}
}
