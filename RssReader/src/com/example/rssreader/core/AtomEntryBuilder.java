package com.example.rssreader.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AtomEntryBuilder// extends EntryBuilder 
{
	private static final String NameSpace = null;
	private static final String FeedTag = "feed";
	private static final String EntryTag = "entry";
	private static final String TitleTag = "title";
	private static final String DescriptionTag = "summary";
	private static final String LinkTag = "link";
	private static final String ContentTag = "content";
	private static final String RelAttribute = "rel";
	private static final String HrefAttribute = "href";
	private static final String ValueOfRelAttr = "alternate";
	
	public AtomEntryBuilder(){
	}
	
	/*
	 *  generate entries by xml file
	 * 
	 * */
	public List<Entry> getEntries(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		List<Entry> entries = new ArrayList<Entry>();
		parser.require(XmlPullParser.START_TAG, NameSpace, FeedTag);
		while(parser.next() != XmlPullParser.END_DOCUMENT){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals(EntryTag)){
				entries.add(generateEntry(parser));
			}
			else{
				ignoreNotInterestTag(parser);
			}
		}
		return entries;
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
			else if(name.equals(DescriptionTag)){
				description = getDescription(parser);
			}
			else if(name.equals(ContentTag)){
				content = getContents(parser);
			}
			else{
				ignoreNotInterestTag(parser);
			}
		}
		return new Entry(title, description, link, content);
	}

	private String getTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
		return readText(parser, TitleTag);
	}

	private String getDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		return readText(parser, DescriptionTag);
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
			if(rel == null || rel.equals(ValueOfRelAttr))
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
		String text = null;
		if(parser.next() == XmlPullParser.TEXT){
			text = parser.getText();
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, NameSpace, tagName);
		return text;
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
