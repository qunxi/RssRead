package com.example.rssreader.core;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public abstract class EntryBuilder 
{
	protected XmlPullParser xmlParser = Xml.newPullParser();

	public abstract List<Entry> getEntries() 
			throws XmlPullParserException, IOException;
		
	public abstract String generateTitle();
	public abstract String generateDescription();
	public abstract String generateLink();
	public abstract String generateContent();
}
