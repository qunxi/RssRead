package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.model.Entry;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class AbstractFeedParser implements IFeedBuilder{
	
	protected XmlPullParser parser;
	
	public AbstractFeedParser(XmlPullParser parser){
		this.parser = parser;
	}
	
	
	protected String getPlainText(String tagName) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, NameSpace, tagName);
		return parser.getText();
	}
	
	protected String readText(String tagName) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, NameSpace, tagName);
		String text = getInnerAllValuesOfTag(tagName);
		parser.require(XmlPullParser.END_TAG, NameSpace, tagName);
		return text;
	}

	protected String getInnerAllValuesOfTag(String tagName) 
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
	
	protected void ignoreNotInterestTag()
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
	
	
	protected String getTitle() throws XmlPullParserException, IOException {
		return readText(TitleTag);
	}
	
	protected abstract Entry generateEntry() throws XmlPullParserException, IOException;
}
