package github.com.qunxi.rssreader.xmlparser;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class FeedBuilderFactory 
{
	private static FeedBuilderFactory instance;
	
	public static FeedBuilderFactory instance()
	{
		if(instance == null)
			instance = new FeedBuilderFactory();
		return instance;
	}
	
	private FeedBuilderFactory(){
		
	}
	
	public IFeedBuilder createFeedBuilder(InputStream stream) throws XmlPullParserException, IOException{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(stream,  null);
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.nextTag();
		String tagName = parser.getName();
		
		if(tagName.equals(IFeedBuilder.FeedTag)){ //atom
			return new AtomFeedBuilder(parser);
		}
		else if(tagName.equals(IFeedBuilder.RssTag)){
			//String version = parser.getAttributeValue(IFeedBuilder.NameSpace, IFeedBuilder.VersionAttr);
			return new RssFeedBuilder();
		}
		else{
			return null;
		}
	}
}
