/**
 * 
 */
package github.com.qunxi.rssreader.test;

import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.xmlparser.FeedBuilderFactory;
import github.com.qunxi.rssreader.xmlparser.IFeedBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.test.InstrumentationTestCase;



public class AtomEntryBuilderTest extends InstrumentationTestCase {

	private StringBuilder xml = new StringBuilder();

	public AtomEntryBuilderTest() {
		//String xml = "<?xml version"
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		   .append("<feed xmlns=\"http://www.w3.org/2005/Atom\">")
		   .append("<link href=\"http://martinfowler.com/feed.atom\" rel=\"self\"/>")
		   .append("<link href=\"http://martinfowler.com\"/>")
		   .append("<id>http://martinfowler.com/feed.atom</id>")
		   .append("<title>Martin Fowler</title>")
		   .append("<subtitle>Master feed of news and updates from martinfowler.com</subtitle>")
		   .append("<author><name>Martin Fowler</name><email>fowler@acm.org</email><uri>http://martinfowler.com</uri></author>")
		   .append("<updated>2013-06-14T10:55:00-04:00</updated>")
		   .append("<entry><title>photostream 48</title><link href=\"http://martinfowler.com/photos/48.html\"/>")
		   .append("<updated>2013-06-14T10:55:00-04:00</updated><id>tag:martinfowler.com,2013-06-14:photostream-48</id>")
		   .append("<category term=\"\"/><content type=\"html\">")
		   .append("<p><a href = 'http://martinfowler.com/photos/48.html'><img src = 'http://martinfowler.com/photos/48.jpg'></img></a></p> <p></p> <p>Melrose, MA</p>")
		   .append("</content></entry></feed>");
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}


	/*public void testGetEntries() {
		InputStream stream = new ByteArrayInputStream(xml.toString().getBytes());
		
		try {
			IFeedBuilder builder = FeedBuilderFactory.instance().createFeedBuilder(stream);
            Category category = new Category("Martin Fowler", "2013-06-14T10:55:00-04:00", -1, null);
            Entry entry = new Entry("photostream 48", "http://martinfowler.com/photos/48.html", null, 
            			"<p><a href = 'http://martinfowler.com/photos/48.html'><img src = 'http://martinfowler.com/photos/48.jpg'></img></a></p> <p></p> <p>Melrose, MA</p>",
            			"2013-06-14T10:55:00-04:00");
	        List<Entry> entries = new ArrayList<Entry>();
	        entries.add(entry);
            Feed expectFeed = new Feed(category, entries);
            Feed actualFeed = builder.getFeed(null);
            assertTrue(FeedCompare(expectFeed, actualFeed));
            
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean FeedCompare(Feed expectFeed, Feed actualFeed){
		if( expectFeed.getCategory().getTitle() == actualFeed.getCategory().getTitle()
			&& expectFeed.getCategory().getUpdateTime() == actualFeed.getCategory().getUpdateTime()){
			List<Entry> eEntries = expectFeed.getEntries();
			List<Entry> aEntries = actualFeed.getEntries();
			for(int i = 0; i < expectFeed.getEntries().size(); ++i){
				if(!(eEntries.get(i).getContent() == aEntries.get(i).getContent()
				   && eEntries.get(i).getUpdated() == aEntries.get(i).getUpdated()
				   && eEntries.get(i).getSummary() == aEntries.get(i).getSummary()
				   && eEntries.get(i).getTitle() == aEntries.get(i).getTitle())){
					return false;
				}
			}
			return true;
		}
		return false;
	}*/

}
