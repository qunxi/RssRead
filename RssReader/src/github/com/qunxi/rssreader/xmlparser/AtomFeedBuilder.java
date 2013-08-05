package github.com.qunxi.rssreader.xmlparser;

import github.com.qunxi.rssreader.utils.DateUtils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AtomFeedBuilder extends AbstractFeedParser 
{
	//atom tag
	private String RelAttribute = "rel";
	private String ValueOfRelAttr = "alternate";
	private String HrefAttribute = "href";
	
	public AtomFeedBuilder(XmlPullParser parser){
		super(parser);
		
	}
	
	@Override
	protected void initializeTag(){
		this.LatestUpdateDateTag = "updated";
		this.EntryTag = "entry";
		this.DescriptionTag = "summary";
		this.EntryUpdateDateTag = "updated";
		this.ContentTag = "content";
	}
	
	@Override
	protected String getLink() throws XmlPullParserException, IOException 
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

	@Override
	protected String normalizeDate(String srcDate) {
		return DateUtils.AtomDateConvert(srcDate);
	}
	
}
