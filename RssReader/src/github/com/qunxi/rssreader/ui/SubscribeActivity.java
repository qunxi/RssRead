package github.com.qunxi.rssreader.ui;

import github.com.qunxi.rssreader.db.CategoryMapper;
import github.com.qunxi.rssreader.db.EntryMapper;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.xmlparser.FeedBuilderFactory;
import github.com.qunxi.rssreader.xmlparser.IFeedBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.rssreader.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SubscribeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscribe, menu);
		return true;
	}
	
	public void searchRssFeedAddr(View view)
	{
		EditText editText = (EditText)findViewById(R.id.edit_subscribe);
		String rssFeedAddr = editText.getText().toString();
		new DownloadFeedXmlAsyncTask().execute(rssFeedAddr);
	}
	
	private class DownloadFeedXmlAsyncTask extends AsyncTask<String, Void, Feed>{
		@Override
		protected Feed doInBackground(String... urls) {
			InputStream stream = null;
			try{
				stream = DownloadFeedFile(urls[0]);
				Feed feed = getFeedFromXml(stream);
				return feed;
				
			}
			catch(IOException e){
				return null;
			}
			finally{
				if(stream != null){
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		@Override
        protected void onPostExecute(Feed result) {
			if(persistFeedToDb(result)){
				//warning message show success;
			}
        }
		
		private boolean persistFeedToDb(Feed feed){
			CategoryMapper categoryMapper = new CategoryMapper(SubscribeActivity.this);
			long id = categoryMapper.insert(feed.getCategory());
			if(id != -1){
				EntryMapper entryMapper = new EntryMapper(SubscribeActivity.this);
				return entryMapper.batchInsert(feed.getEntries(), id) != -1 ? true : false;
			}
			return false;
		}
		
		private InputStream DownloadFeedFile(String feedUrl) throws IOException{
		
			URL url = new URL(feedUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(1000);
			conn.setConnectTimeout(1500);
			conn.connect();
			int response = conn.getResponseCode();
			Log.d("HttpExample", "the response is:" + response);
			return conn.getInputStream();
		}
		
		private Feed getFeedFromXml(InputStream stream) throws IOException
		{
			try {
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(stream,  null);
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		        parser.nextTag();
		        IFeedBuilder builder = FeedBuilderFactory.instance().createFeedBuilder(parser);
		        return builder.getFeed(parser);
			}catch (XmlPullParserException e) {
				Log.d("getFeedFromXml", "getFeed has exception");
				e.printStackTrace();
				return null;
			}
		}
	}
}
