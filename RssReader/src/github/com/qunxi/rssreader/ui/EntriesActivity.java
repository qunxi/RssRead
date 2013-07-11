package github.com.qunxi.rssreader.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import java.util.List;

import github.com.qunxi.rssreader.db.EntryMapper;
import github.com.qunxi.rssreader.model.Entry;
import com.example.rssreader.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EntriesActivity extends ListActivity {

	private String categoryTitle = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EntryMapper entryMapper = new EntryMapper(this);
		long categoryId = getIntent().getLongExtra("categoryId", -1);
		List<Entry> entries = entryMapper.loadEntries(0, categoryId);
		categoryTitle = getIntent().getStringExtra("categoryTitle");
		EntryAdapter entryAdapter = new EntryAdapter(this, entries);
		setListAdapter(entryAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entries, menu);
		return true;
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
		intent.putExtra("categoryTitle", categoryTitle);
		startActivity(intent);
	}
	
	//@Override
	
	
	private class EntryAdapter extends ArrayAdapter<Entry>{

		private final List<Entry> entries;
		private final Activity context;
		private ViewContainer container = new ViewContainer();
		//private static final int SUMMARY_BEGIN = 0;
		//private static final int SUMMARY_END = 150;
		
		public EntryAdapter(Activity context, List<Entry> entries) 
		{
			super(context, R.layout.entry_item, entries);
			this.entries = entries;
			this.context = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View view = null;
			convertView = null;
			if(convertView == null){
				LayoutInflater inflater = context.getLayoutInflater();
				view = inflater.inflate(R.layout.entry_item, null);
				
				container.summaryText = (TextView)view.findViewById(R.id.entry_item_summary);
				container.updatedText = (TextView)view.findViewById(R.id.entry_item_updated);
				container.iconImage = (ImageView)view.findViewById(R.id.entry_item_icon);
				
				view.setTag(container);
			}
			else{
				view = convertView;
				container = (ViewContainer)view.getTag();
			}
			
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(new URL(entries.get(position).getThumbImage()).openConnection().getInputStream());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			container.iconImage.setImageBitmap(bitmap);

		    container.updatedText.setText(entries.get(position).getUpdated());
		    StringBuilder summary = new StringBuilder();
		    summary.append("<font font-weight = \"bold\" color=\"#000000\"><strong>")
		    	   .append(entries.get(position).getTitle())
		    	   .append("-</strong></font>")
		    	   .append(entries.get(position).getSummary());
		    
		    container.summaryText.setText(Html.fromHtml(summary.toString()));
			
			return view;
		}
		
		/*private String getSummary(int position)
		{
			String description = entries.get(position).getDescription();
			if(description == null){
				
				String contents = Html.fromHtml(entries.get(position).getContent().replaceAll("<img.+?>", "")).toString();
				int length = contents.length();
				int end = length < SUMMARY_END ? length : SUMMARY_END; 
				String summary = contents.substring(SUMMARY_BEGIN,  end);
				//return summary.replaceFirst("<img.+?>", "");
				return summary;
			}
			return description;
		}*/
		
		private final class ViewContainer{
			protected ImageView iconImage;
			protected TextView updatedText;
			protected TextView summaryText;
		}
		
	}
}
