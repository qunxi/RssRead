package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Category;
import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;

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
		String headerTitle = null;
		String feedUpdated = null;
		String logo = null;
		while(parser.next() != XmlPullParser.END_DOCUMENT){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(ItemTag)){	// get all entries of feed
				Entry entry = generateEntry();
				if(fromDate !=null && entry.getUpdated().equals(fromDate)){
					break;
				}
				else{
					entries.add(entry);
				}
			}
			else if(name.equals(TitleTag)){ //feed title
				headerTitle = getTitle();
			}
			else if(name.equals(LastBuildDate)){ //feed update date
				feedUpdated = getLastBuilderDate();
				if(feedUpdated.equals(fromDate)){
					return null;
				}
			}
			else{
				ignoreNotInterestTag();
			}
		}
		Category feedHeader = new Category(headerTitle, feedUpdated, entries.size(), logo);
		return new Feed(feedHeader, entries);
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
				entry.setDescription(getDescription());
			}
			else if(name.equals(Content_Encode)){
				entry.setContent(getContents());
			}
			else if(name.equals(Publish_Date)){
				entry.setUpdated(getPublishDate());
			}
			else{
				ignoreNotInterestTag();
			}
		}
		return entry;
	}
	
	private String getPublishDate() throws XmlPullParserException, IOException{
		return readText(Publish_Date);
	}
	private String getDescription() throws XmlPullParserException, IOException{
		return readText(DescriptionTag);
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
		/*String link = null;
		parser.require(XmlPullParser.START_TAG, NameSpace, LinkTag);
		if(parser.getName().equals(LinkTag))
		{
			link = parser.getAttributeValue(NameSpace, HrefAttribute);
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, NameSpace, LinkTag);
		return link;*/
	}
}
