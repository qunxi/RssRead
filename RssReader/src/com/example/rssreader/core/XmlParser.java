package com.example.rssreader.core;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class XmlParser {
		
	public static String getText(XmlPullParser xmlParser, String nameSpace, String tagName) 
								throws XmlPullParserException, IOException
	{
		xmlParser.require(XmlPullParser.START_TAG, nameSpace, tagName);
		String text = null;
		if(xmlParser.next() == XmlPullParser.TEXT){
			text = xmlParser.getText();
			xmlParser.nextTag();
		}
		xmlParser.require(XmlPullParser.END_TAG, nameSpace, tagName);
		return text;
	}
	
	/*public static String getAttribute(XmlPullParser xmlParser, String nameSpace, String tagName,
									  String attributeName) 
						 throws XmlPullParserException, IOException
	{
		String link = null;
		xmlParser.require(XmlPullParser.START_TAG, nameSpace, tagName);
		if(xmlParser.getName().equals(tagName))
		{
			if(xmlParser.getAttributeValue(nameSpace, RelAttribute).equals(ValueOfRelAttr))
			{
				link = xmlParser.getAttributeValue(NameSpace, HrefAttribute);
				xmlParser.nextTag();
			}
		}
		xmlParser.require(XmlPullParser.END_TAG, nameSpace, tagName);
		return link;
	}*/
}
