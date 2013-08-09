package github.com.qunxi.rssreader.ui;

import java.util.ArrayList;
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
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ListActivity {

	private boolean isChoiceMode = false;
	
	private SparseBooleanArray itemsState = new SparseBooleanArray();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new ModeCallBack(this));
	}

	@Override
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		Feed selectItem = (Feed)getListAdapter().getItem(position);
		Intent intent = new Intent(this, EntriesActivity.class);
		intent.putExtra("feedId", selectItem.getId());
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
	}
	
	private List<Feed> getFeeds(int curPos)
	{
		return MapperRegister.feed(this).getLimitFeeds(10, curPos);
	}
	///
	
	private class ModeCallBack extends MultiChoiceDeleteMode{

		public ModeCallBack(ListActivity activity) {
			super(activity);
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			isChoiceMode = true;
			return super.onCreateActionMode(mode, menu);
		}
		
		@Override
		public void onDestroyActionMode(ActionMode arg0) {
			isChoiceMode = false;
			itemsState.clear();
		}
		
		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			 itemsState.put(position, checked);
			 super.onItemCheckedStateChanged(mode, position, id, checked);
		}

		@Override
		public void doPositiveButton() {
			List<Long> feedIds = new ArrayList<Long>();
			for(int i = 0; i < itemsState.size(); ++i){
				int position = itemsState.keyAt(i);
				Feed feed = ((Feed)getListAdapter().getItem(position));
				long id = feed.getId();
				FeedsAdapter adapter = (FeedsAdapter)getListAdapter();
				adapter.remove(feed);
				
				feedIds.add(id);
			}
			MapperRegister.feed(MainActivity.this).removeFeed(feedIds);	
		}
	}
		
	private class FeedsAdapter extends ArrayAdapter<Feed>{
		private final Activity context;
		private List<Feed> feeds;
		
		public FeedsAdapter(Activity context, List<Feed> objects) 
		{
			super(context, R.layout.feed_item, objects);
			this.context = context;
			this.feeds = objects;
		}
		
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			View view = null;
			ViewContainer container = null;
			
			if(convertView == null){
				LayoutInflater inflater = context.getLayoutInflater();
				view = inflater.inflate(R.layout.feed_item, null);
				container = new ViewContainer();
				container.iconImage = (ImageView)view.findViewById(R.id.category_item_icon);
				container.titleText = (TextView)view.findViewById(R.id.category_item_title);
				container.updatedText = (TextView)view.findViewById(R.id.category_item_updated);
				container.countText = (TextView)view.findViewById(R.id.category_item_count);
				container.selCheckbox = (CheckBox)view.findViewById(R.id.category_item_selected);
				view.setTag(container);
			}
			else{
				view = convertView;
				container = (ViewContainer)view.getTag();
			}
			
			container.selCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			    ListView listView = (ListView)parent;
				@Override
				public void onCheckedChanged(CompoundButton compound, boolean arg1) {
					(listView).setItemChecked(position, compound.isChecked());
				}
				
			});
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_rss);
			container.iconImage.setImageBitmap(bitmap);
			container.titleText.setText(feeds.get(position).getTitle());
			container.updatedText.setText(feeds.get(position).getUpdated());
			container.countText.setText(Long.toString(feeds.get(position).getUnread()) + "/" + Long.toString(feeds.get(position).getCounts()));
			if(isChoiceMode){
				container.selCheckbox.setVisibility(View.VISIBLE);
				container.countText.setVisibility(View.INVISIBLE);
				container.selCheckbox.setChecked(itemsState.get(position));
			}else{
				container.selCheckbox.setVisibility(View.INVISIBLE);
				container.countText.setVisibility(View.VISIBLE);
			}

			return view;
		}
		
		private final class ViewContainer{
			protected ImageView iconImage;
			protected TextView titleText;
			protected TextView updatedText;
			protected TextView countText;
			protected CheckBox selCheckbox;
		}
	}


	
}
