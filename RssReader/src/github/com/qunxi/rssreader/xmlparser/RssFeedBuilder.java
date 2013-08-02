package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class RssFeedBuilder extends AbstractFeedParser 
{
	public RssFeedBuilder(XmlPullParser parser){
		super(parser);
	}
	
	@Override
	public Feed getFeed(String fromDate) throws XmlPullParserException, IOException {
		parser.nextTag();
		parser.require(XmlPullParser.START_TAG, NameSpace, CHANNEL);
		List<Entry> entries = new ArrayList<Entry>();
		Feed feed = new Feed();
		while(parser.next() != XmlPullParser.END_DOCUMENT){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(ItemTag)){	// get all entries of feed
				Entry entry = generateEntry();
				if(feed.getUpdated() == null || DateUtils.isLarge(entry.getUpdated(), feed.getUpdated()))
					feed.setUpdated(entry.getUpdated());
				if(fromDate !=null && DateUtils.isLarge(fromDate, entry.getUpdated())){
					break;
				}
				else{
					entries.add(entry);
				}
			}
			else if(name.equals(TitleTag)){ //feed title
				feed.setTitle(getTitle());
			}
			else if(name.equals(LastBuildDate)){ //feed update date
				String date = DateUtils.RssDateConvert(getLastBuilderDate());
				feed.setUpdated(date);
			}
			else{
				ignoreNotInterestTag();
			}
		}
		if(entries.size() > 0)
			feed.setEntries(entries);
		else
			feed = null;
		return feed;
	}
	
	@Override
	protected  Entry generateEntry() throws XmlPullParserException, IOException{
		Entry entry = new Entry();
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(TitleTag)){
				entry.setTitle(getTitle());
			}
			else if(name.equals(LinkTag)){
				entry.setLink(getLink());
			}
			else if(name.equals(DescriptionTag)){
				entry.setSummary(getDescription());
			}
			else if(name.equals(Content_Encode)){
				entry.setContent(getContents());
			}
			else if(name.equals(Publish_Date)){
				String date = DateUtils.RssDateConvert(getPublishDate());
				entry.setUpdated(date);
			}
			else{
				ignoreNotInterestTag();
			}
		}
		if(entry.getContent() == null){
			entry.setContent(entry.getSummary());
		}
		return entry;
	}
	
	private String getDescription() throws XmlPullParserException, IOException{
		return readText(DescriptionTag);
	}
	
	private String getPublishDate() throws XmlPullParserException, IOException{
		return readText(Publish_Date);
	}

	private String getLastBuilderDate() throws XmlPullParserException, IOException{
		return readText(LastBuildDate);
	}
	
	protected String getContents() throws XmlPullParserException, IOException {
		return readText(Content_Encode);
	}
	
	private String getLink() throws XmlPullParserException, IOException
	{
		return readText(LinkTag);
	}
}
