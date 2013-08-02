package github.com.qunxi.rssreader.ui;


import java.util.List;
import github.com.qunxi.rssreader.db.MapperRegister;
import github.com.qunxi.rssreader.model.Entry;
import github.com.qunxi.rssreader.model.Feed;
import github.com.qunxi.rssreader.net.DownloadXmlAsyncTask;
import com.example.rssreader.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EntriesActivity extends ListActivity implements OnScrollListener {

	private static final int LOAD_ITEMS = 10;
	private Feed feed = null;
	boolean updated = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		long feedId = getIntent().getLongExtra("feedId", -1);
		feed = loadFeed(feedId, 0);
		EntryAdapter entryAdapter = new EntryAdapter(this, feed.getEntries());
		setListAdapter(entryAdapter);
		ActionBar bar = getActionBar(); 
		bar.setTitle(feed.getTitle());
		getListView().setOnScrollListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entries, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.action_entries_refresh){
			new UpdateAnsyncTask(this).execute(feed.getUrl(), feed.getUpdated());
		}
		return true;
	}
	
	@Override  
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && updated){
			new ScrollLoadAsnycTask().execute();
		}
	}
	
	
	private class ScrollLoadAsnycTask extends AsyncTask<Void, Void, List<Entry>> {
		
		@Override
		protected List<Entry> doInBackground(Void... params) {
			return loadFeed(feed.getId(), -1).getEntries();
		}
		
		@Override
	    protected void onPostExecute(List<Entry> result) {
			EntryAdapter adapter = (EntryAdapter)getListAdapter();
			adapter.addAll(result);
	    }
		
	}
		
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		if((firstVisibleItem + visibleItemCount) % LOAD_ITEMS == 0){
			updated = true;
		}
	}
	
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		Entry selectItem = (Entry)getListAdapter().getItem(position);
		String content = selectItem.getContent();
		Intent intent = new Intent(this, DetailsActivity.class);
		intent.putExtra("content", content);
		intent.putExtra("title", selectItem.getTitle());
		intent.putExtra("updated", selectItem.getUpdated());
		intent.putExtra("categoryTitle", feed.getTitle());
		startActivity(intent);
		selectItem.setUnread(false);
		MapperRegister.feed(this).updateReadState(selectItem);
	}
	
	private Feed loadFeed(long feedId, long offset){
		return MapperRegister.feed(this).getFeedById(feedId, offset);
	}
	
	
	private class UpdateAnsyncTask extends DownloadXmlAsyncTask{

		private EntryAdapter adapter;
		public UpdateAnsyncTask(Context context) {
			super(context);
			this.adapter = (EntryAdapter)getListAdapter();
		}
		
		@Override
		protected void onPostExecute(Feed result) {
			if(result != null){
				result.setId(feed.getId());
				super.onPostExecute(result);
				List<Entry> entries = loadFeed(feed.getId(), 0).getEntries();
				adapter.refresh(entries);
			}
	    }
	}
	
	private class EntryAdapter extends ArrayAdapter<Entry>{

		private List<Entry> entries;
		private final Activity context;
				
		public EntryAdapter(Activity context, List<Entry> entries) 
		{
			super(context, R.layout.entry_item, entries);
			this.entries = entries;
			this.context = context;
		}
		
		public void refresh(List<Entry> entries){
			this.entries = entries;
			notifyDataSetChanged();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View view = null;
			ViewHolder container = null;
			if(convertView == null){
				
				LayoutInflater inflater = context.getLayoutInflater();
				view = inflater.inflate(R.layout.entry_item, null);
				
				container = new ViewHolder();
				container.summaryText = (TextView)view.findViewById(R.id.entry_item_summary);
				container.updatedText = (TextView)view.findViewById(R.id.entry_item_updated);
				//container.iconImage = (ImageView)view.findViewById(R.id.entry_item_icon);
				
				view.setTag(container);
			}
			else{
				view = convertView;
				container = (ViewHolder)view.getTag();
			}
									
		    container.updatedText.setText(entries.get(position).getUpdated());
		    StringBuilder summary = new StringBuilder();
		    
		    if(entries.get(position).isUnread())
		    	summary.append("<font font-weight = \"bold\" color=\"#000000\"><strong>");
		    else
		    	summary.append("<font font-weight = \"bold\" color=\"#BEBEBE\"><strong>");
		    summary.append(entries.get(position).getTitle())
		    	   .append("-</strong></font>")
		    	   .append(entries.get(position).getSummary());
		   
		    container.summaryText.setText(Html.fromHtml(summary.toString()));
			
			return view;
		}
			
		private final class ViewHolder{
			//protected ImageView iconImage;
			protected TextView updatedText;
			protected TextView summaryText;
		}
		
	}
}
