package com.example.rssreader.net;

import java.io.IOException;
import java.net.URL;

import android.os.AsyncTask;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.rssreader.core.SubscribeItem;
import com.example.rssreader.core.XmlParser;

public class SubscribtionAsyncTask extends AsyncTask<URL, Void, Void> {
	
	private final static String NameSpace = null;
	private final static String TitleTag = "title";
	private final static String UpdatedTag = "updated";
	private final static String EntryTag = "entry";
	
	@Override
	protected Void doInBackground(URL... urls) {
		return null;
	}
	
	private SubscribeItem generateSubscribItem(XmlPullParser xmlParser) throws XmlPullParserException, IOException{
		String title = XmlParser.getText(xmlParser, NameSpace, TitleTag);
		String updated = XmlParser.getText(xmlParser, NameSpace, UpdatedTag);
		return null;
		
	}
	
	private long getUnreadItemsCount(XmlPullParser xmlParser) throws XmlPullParserException, IOException
	{
		xmlParser.require(XmlPullParser.START_TAG, NameSpace , EntryTag);
		
		return 1;
	}
}

