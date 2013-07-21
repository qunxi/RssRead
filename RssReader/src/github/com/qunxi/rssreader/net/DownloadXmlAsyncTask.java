package github.com.qunxi.rssreader.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

import github.com.qunxi.rssreader.db.CategoryMapper;
import github.com.qunxi.rssreader.db.EntryMapper;
import github.com.qunxi.rssreader.model.Category;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.xmlparser.FeedBuilderFactory;
import github.com.qunxi.rssreader.xmlparser.IFeedBuilder;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadXmlAsyncTask extends AsyncTask<String, Void, Feed> {
	
	protected Context context;
	private String url;
	public DownloadXmlAsyncTask(Context context){
		this.context = context;
	}
	
	@Override
	protected Feed doInBackground(String... urls) {
		try{
			InputStream stream = null;
			try{
				this.url = urls[0];
				stream = DownloadFeedFile(url);
				return getFeedFromXml(stream, urls.length == 1 ? null : urls[1]);
			}
			finally{
				if(stream != null){
					stream.close();
				}
			}
		}
		catch(IOException e){
			return null;
		}
	}
	
	@Override
    protected void onPostExecute(Feed result) {

		if(result != null && saveFeedToDatabase(result)){
			//show successful message
		}
		//show failed message
    }
	
	/*protected Context getContext(){
		return context;
	}*/
	
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
	
	private Feed getFeedFromXml(InputStream stream, String fromDate){
		try {
			IFeedBuilder builder = FeedBuilderFactory.instance().createFeedBuilder(stream);
			return builder.getFeed(fromDate);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean saveFeedToDatabase(Feed feed){
		CategoryMapper categoryMapper = new CategoryMapper(context);
		Category category = feed.getCategory();
		category.setUrl(url);
		long id = category.getId();
		
		if(!categoryMapper.exist(id)){
			id = categoryMapper.insert(category);
		}
		else{
			categoryMapper.updateDate(id, category.getUpdateTime());
		}
		
		if(id != -1){
			feed.setCategoryId(id);
			EntryMapper entryMapper = new EntryMapper(context);
			return entryMapper.insertAll(feed.getEntries());
		}

		return false;
	 }

}
