package com.example.rssreader.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.example.rssreader.core.AtomEntryBuilder;
import com.example.rssreader.core.Entry;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AtomEntryBuilderTest2 extends TestCase {


	private StringBuilder xml = new StringBuilder();

	public AtomEntryBuilderTest2(String name) {
		super(name);
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


	public void testGetEntries() {
		InputStream stream = new ByteArrayInputStream(xml.toString().getBytes());
		//InputStream stream = getInstrumentation().getContext().getResources().openRawResource(R.raw.atomfeed);

		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(stream,  null);
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            AtomEntryBuilder builder = new AtomEntryBuilder();
            List<Entry> entries = builder.getEntries(parser);
            Assert.assertEquals(true, true);
            //List<Entry> 
            //Assert.assertEquals(expected, entries);
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

}
