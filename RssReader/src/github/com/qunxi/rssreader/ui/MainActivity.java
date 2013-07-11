package github.com.qunxi.rssreader.ui;

import java.util.List;

import github.com.qunxi.rssreader.db.CategoryMapper;
import github.com.qunxi.rssreader.model.Category;

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
		List<Category> categories = getCategories(0);
		FeedsAdapter feedAdapter = new FeedsAdapter(this, categories);
		setListAdapter(feedAdapter);
	}

	@Override
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		Category selectItem = (Category)getListAdapter().getItem(position);
		long categoryId = selectItem.getId();
		Intent intent = new Intent(this, EntriesActivity.class);
		intent.putExtra("categoryId", categoryId);
		intent.putExtra("categoryTitle", selectItem.getTitle());
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
	
	private List<Category> getCategories(int curPos)
	{
		CategoryMapper categoryMapper = new CategoryMapper(this);
		return categoryMapper.getCategories(curPos);
	}
	///
	
	
	private class FeedsAdapter extends ArrayAdapter<Category>{
		private final Activity context;
		private final List<Category> categories;
		private ViewContainer container = new ViewContainer();
		
		public FeedsAdapter(Activity context, List<Category> objects) 
		{
			super(context, R.layout.category_item, objects);
			this.context = context;
			this.categories = objects;
		}

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
			container.titleText.setText(categories.get(position).getTitle());
			container.updatedText.setText(categories.get(position).getUpdateTime());
			container.countText.setText(Long.toString(categories.get(position).getCounts()));
			
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
