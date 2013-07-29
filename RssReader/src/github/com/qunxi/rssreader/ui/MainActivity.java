package github.com.qunxi.rssreader.ui;

import java.util.List;

import github.com.qunxi.rssreader.db.MapperRegister;
import github.com.qunxi.rssreader.model.Feed;

import com.example.rssreader.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		Feed selectItem = (Feed)getListAdapter().getItem(position);
		//long categoryId = selectItem.getId();
		Intent intent = new Intent(this, EntriesActivity.class);
		intent.putExtra("feedId", selectItem.getId());
		//intent.putExtra("url", selectItem.getUrl());
		//intent.putExtra("fromDate", selectItem.getUpdated());
		//intent.putExtra("categoryId", categoryId);
		//intent.putExtra("categoryTitle", selectItem.getTitle());
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId() == R.id.action_new){
			Intent intent = new Intent(this, SubscribeActivity.class);
			startActivity(intent);
		}
		return true;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		List<Feed> categories = getFeeds(0);
		FeedsAdapter feedAdapter = new FeedsAdapter(this, categories);
		setListAdapter(feedAdapter);
		
		/*FeedsAdapter adapter = (FeedsAdapter)getListAdapter();
		List<Category> categories  = getCategories(0);
		if(categories != null && categories.size() > 0)
			adapter.refresh(categories);*/
	}
	
	
	private List<Feed> getFeeds(int curPos)
	{
		return MapperRegister.feed().getLimitFeeds(10, curPos);
	}
	///
	
	
	private class FeedsAdapter extends ArrayAdapter<Feed>{
		private final Activity context;
		private List<Feed> feeds;
		private ViewContainer container = new ViewContainer();
		
		public FeedsAdapter(Activity context, List<Feed> objects) 
		{
			super(context, R.layout.category_item, objects);
			this.context = context;
			this.feeds = objects;
		}

		/*public void refresh(List<Category> categories){
			this.categories = categories;
			notifyDataSetChanged();
		}*/
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			
			if(convertView == null){
				LayoutInflater inflater = context.getLayoutInflater();
				view = inflater.inflate(R.layout.category_item, null);
				
				container.iconImage = (ImageView)view.findViewById(R.id.category_item_icon);
				container.titleText = (TextView)view.findViewById(R.id.category_item_title);
				container.updatedText = (TextView)view.findViewById(R.id.category_item_updated);
				container.countText = (TextView)view.findViewById(R.id.category_item_count);
				view.setTag(container);
			}
			else{
				view = convertView;
				container = (ViewContainer)view.getTag();
			}
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_rss);
			container.iconImage.setImageBitmap(bitmap);
			container.titleText.setText(feeds.get(position).getTitle());
			container.updatedText.setText(feeds.get(position).getUpdated());
			container.countText.setText(Long.toString(feeds.get(position).getUnread()) + "/" + Long.toString(feeds.get(position).getCounts()));
			
			return view;
		}
		
		private final class ViewContainer{
			protected ImageView iconImage;
			protected TextView titleText;
			protected TextView updatedText;
			protected TextView countText;
		}
	}
}
