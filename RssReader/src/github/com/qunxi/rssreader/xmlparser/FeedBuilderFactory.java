package github.com.qunxi.rssreader.xmlparser;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
	
	public IFeedBuilder createFeedBuilder(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		String tagName = parser.getName();
		if(tagName.equals(IFeedBuilder.FeedTag)){ //atom
			return new AtomFeedBuilder();
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
