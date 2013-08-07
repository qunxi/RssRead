package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class AbstractFeedParser implements IFeedBuilder{
	
	protected XmlPullParser parser;
	
	protected String LatestUpdateDateTag = null;
	protected String EntryTag = null;
	protected String DescriptionTag = null;
	protected String EntryUpdateDateTag = null;
	protected String ContentTag = null; 
	
	protected String TitleTag = "title";
	protected String LinkTag = "link";
	
	public AbstractFeedParser(XmlPullParser parser){
		this.parser = parser;
		initializeTag();
	}
	
	@Override
	public Feed getFeed(String fromDate) throws XmlPullParserException, IOException {
		//parser.nextTag();
		List<Entry> entries = new ArrayList<Entry>();
		Feed feed = new Feed();
		while(parser.next() != XmlPullParser.END_DOCUMENT){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(EntryTag)){	// get all entries of feed
				Entry entry = generateEntry();
				if(feed.getUpdated() == null || DateUtils.isLargeEqual(entry.getUpdated(), feed.getUpdated()))
					feed.setUpdated(entry.getUpdated());
				if(fromDate !=null 
				   && DateUtils.isLargeEqual(fromDate, entry.getUpdated())){
					break;
				}
				else{
					entries.add(entry);
				}
			}
			else if(name.equals(TitleTag)){ //feed title
				feed.setTitle(readText(TitleTag));
			}
			else if(name.equals(LatestUpdateDateTag)){ //feed update date
				String date = normalizeDate(readText(LatestUpdateDateTag));
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
	
	private  Entry generateEntry() throws XmlPullParserException, IOException{
		Entry entry = new Entry();
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(TitleTag)){
				entry.setTitle(readText(TitleTag));
			}
			else if(name.equals(LinkTag)){
				entry.setLink(getLink());
			}
			else if(name.equals(DescriptionTag)){
				entry.setSummary(readText(DescriptionTag));
			}
			else if(name.equals(ContentTag)){
				entry.setContent(readText(ContentTag));
			}
			else if(name.equals(EntryUpdateDateTag)){
				String date = normalizeDate(readText(EntryUpdateDateTag));
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
		
	protected String readText(String tagName) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, NameSpace, tagName);
		String text = getInnerAllValuesOfTag(tagName);
		parser.require(XmlPullParser.END_TAG, NameSpace, tagName);
		return text;
	}

	private String getInnerAllValuesOfTag(String tagName) 
									throws XmlPullParserException, IOException
	{
		StringBuilder strBuilder = new StringBuilder();

		int depth = 1;
		while(depth !=0 ){
			switch(parser.next())
			{
			case XmlPullParser.END_TAG:
				--depth;
				if(depth > 0){
					strBuilder.append("</" + parser.getName() + ">");
				}
				break;
			case XmlPullParser.START_TAG:
				++depth;
				StringBuilder attrs = new StringBuilder();
				for(int i = 0; i < parser.getAttributeCount(); ++i){
					attrs.append(parser.getAttributeName(i) + "=\"" + parser.getAttributeValue(i) +"\" ");
				}
				strBuilder.append("<" + parser.getName() + " " + attrs + "/>");
				break;
			case XmlPullParser.TEXT:
					strBuilder.append(parser.getText());
				break;
			}
				
		}
		
		return strBuilder.toString();
	}
	
	private void ignoreNotInterestTag()
							   throws XmlPullParserException, IOException
	{
		if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	}
	
	protected abstract String getLink() throws XmlPullParserException, IOException;
	protected abstract void initializeTag();
	protected abstract String normalizeDate(String srcDate);
}
