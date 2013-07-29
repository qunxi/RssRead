package github.com.qunxi.rssreader.ui;

import github.com.qunxi.rssreader.net.DownloadXmlAsyncTask;

import com.example.rssreader.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
		new SubcribeAsyncTask(this).execute(rssFeedAddr);
	}
	
	
	private class SubcribeAsyncTask extends DownloadXmlAsyncTask{

		public SubcribeAsyncTask(Context context) {
			super(context);	
		}
		
	}
}
