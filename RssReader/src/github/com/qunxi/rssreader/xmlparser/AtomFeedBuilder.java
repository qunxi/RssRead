package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.utils.DateNormalize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AtomFeedBuilder extends AbstractFeedParser 
{
	public AtomFeedBuilder(XmlPullParser parser){
		super(parser);
	}
	

	public Feed getFeed(String fromDate) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, NameSpace, FeedTag);
		List<Entry> entries = new ArrayList<Entry>();

		Feed feed = new Feed();
		while(parser.next() != XmlPullParser.END_DOCUMENT){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(EntryTag)){	// get all entries of feed
				Entry entry = generateEntry();
				if(fromDate !=null && entry.getUpdated().equals(fromDate)){
					break;
				}
				else{
					entries.add(entry);
				}
			}
			else if(name.equals(TitleTag)){ //feed title
				feed.setTitle(getTitle());
			}
			else if(name.equals(UpdatedTag)){ //feed update date
				String date = DateNormalize.AtomDateConvert(getUpdated());
				feed.setUpdated(date);
				if(fromDate != null && date.equals(fromDate)){
					return null;
				}
			}
			else{
				ignoreNotInterestTag();
			}
		}
		
		feed.setEntries(entries);
		return feed;
	}
	
	@Override
	protected Entry generateEntry() throws XmlPullParserException, IOException{
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
			else if(name.equals(SummaryTag)){
				entry.setSummary(readText(SummaryTag));
			}
			else if(name.equals(ContentTag)){
				entry.setContent(getContents());
			}
			else if(name.equals(UpdatedTag)){
				entry.setUpdated(DateNormalize.AtomDateConvert(getUpdated()));
			}
			else{
				ignoreNotInterestTag();
			}
		}
		if(entry.getContent() == null){
			throw new IOException();
		}
		return entry;
	}

	protected String getUpdated() throws XmlPullParserException, IOException{
		return readText(UpdatedTag);
	}

	/*private String getDescription() throws XmlPullParserException, IOException {
		return readText(SummaryTag);
	}*/
	
	protected String getContents() throws XmlPullParserException, IOException {
		return readText(ContentTag);
	}
	
	private String getLink() throws XmlPullParserException, IOException 
	{
		String link = null;
		parser.require(XmlPullParser.START_TAG, NameSpace, LinkTag);
		if(parser.getName().equals(LinkTag))
		{
			String rel = parser.getAttributeValue(NameSpace, RelAttribute);
			if(rel == null || rel.equals(ValueOfRelAttr))//sometimes 
			{
				link = parser.getAttributeValue(NameSpace, HrefAttribute);
				parser.nextTag();
			}
		}
		parser.require(XmlPullParser.END_TAG, NameSpace, LinkTag);
		return link;
	}
	
	

}
