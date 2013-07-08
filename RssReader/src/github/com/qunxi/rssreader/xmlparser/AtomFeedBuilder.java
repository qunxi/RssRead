package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.model.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AtomFeedBuilder implements IFeedBuilder
{
	
	
	public AtomFeedBuilder(){
	}
	

	public Feed getFeed(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, NameSpace, FeedTag);
		List<Entry> entries = new ArrayList<Entry>();
		String headerTitle = null;
		String feedUpdated = null;
		String logo = null;
		while(parser.next() != XmlPullParser.END_DOCUMENT){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(EntryTag)){	// get all entries of feed
				entries.add(generateEntry(parser));
			}
			else if(name.equals(TitleTag)){ //feed title
				headerTitle = getTitle(parser);
			}
			else if(name.equals(UpdatedTag)){ //feed update date
				feedUpdated = getUpdated(parser);
			}
			else{
				ignoreNotInterestTag(parser);
			}
		}
		Category feedHeader = new Category(headerTitle, feedUpdated, entries.size(), logo);
		return new Feed(feedHeader, entries);
	}
	
	private Entry generateEntry(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		String title = null;
		String link = null;
		String description = null;
		String content = null;
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(TitleTag)){
				title = getTitle(parser);
			}
			else if(name.equals(LinkTag)){
				link = getLink(parser);
			}
			else if(name.equals(SummaryTag)){
				description = getDescription(parser);
			}
			else if(name.equals(ContentTag)){
				content = getContents(parser);
			}
			else{
				ignoreNotInterestTag(parser);
			}
		}
		return new Entry(title, link, description, content);
	}

	private String getUpdated(XmlPullParser parser) throws XmlPullParserException, IOException{
		return readText(parser, UpdatedTag);
	}
	
	private String getTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
		return readText(parser, TitleTag);
	}

	private String getDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		return readText(parser, SummaryTag);
	}

	private String getContents(XmlPullParser parser) throws XmlPullParserException, IOException {
		return readText(parser, ContentTag);
	}
	
	private String getLink(XmlPullParser parser) throws XmlPullParserException, IOException 
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
	
	private String readText(XmlPullParser parser, String tagName) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, NameSpace, tagName);
		String text = getInnerAllValuesOfTag(parser, tagName);
		parser.require(XmlPullParser.END_TAG, NameSpace, tagName);
		return text;
	}

	private String getInnerAllValuesOfTag(XmlPullParser parser, String tagName) 
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
	
	private void ignoreNotInterestTag(XmlPullParser parser)
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

}
