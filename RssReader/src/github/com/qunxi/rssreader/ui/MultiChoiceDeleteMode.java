package github.com.qunxi.rssreader.ui;


import com.example.rssreader.R;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.AbsListView.MultiChoiceModeListener;

public abstract class MultiChoiceDeleteMode implements MultiChoiceModeListener{

	protected ListActivity context;
	
	public MultiChoiceDeleteMode(ListActivity activity){
		this.context = activity;
	}
	
	@Override
	public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.action_recycle:
				Dialog dlg = new Builder(context)
				.setIcon(R.drawable.ic_alerts_warning)
				.setTitle(R.string.remove_dialog_title)
				.setPositiveButton(R.string.yes_button_text, new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//MapperRegister.feed(context).removeFeed(getSelectedFeedIds());
						doPositiveButton();
						((BaseAdapter)context.getListAdapter()).notifyDataSetChanged();
						mode.finish();
					}
				})
				.setNegativeButton(R.string.no_button_text,  new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
				dlg.show();
				
			default:
		}
	
		return true;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = context.getMenuInflater();
        inflater.inflate(R.menu.details, menu);
		
		((BaseAdapter)context.getListAdapter()).notifyDataSetChanged();
		return true;
	}

	

	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
		
		return true;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		 final int checkedCount = context.getListView().getCheckedItemCount();
		 //itemsState.put(position, checked);
		 if(checkedCount > 0){
			 mode.setTitle("" + checkedCount + context.getText(R.string.multimode_action_title));
		 }
	}
	
	public abstract void doPositiveButton();
	/*private List<Long> getSelectedFeedIds(){
		List<Long> feedIds = new ArrayList<Long>();
		for(int i = 0; i < itemsState.size(); ++i){
			int position = itemsState.keyAt(i);
			Feed feed = ((Feed)context.getListAdapter().getItem(position));
			long id = feed.getId();
			FeedsAdapter adapter = (FeedsAdapter)getListAdapter();
			adapter.remove(feed);
			
			feedIds.add(id);
		}
		return feedIds;
	}*/
}
