package github.com.qunxi.rssreader.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import github.com.qunxi.rssreader.db.MapperRegister;
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
		String url = editText.getText().toString();
		Pattern p=Pattern.compile("^http(s)*://*");
		Matcher m=p.matcher(url);
		if(!m.find())
			url = "http://" + url;
		if(!MapperRegister.feed(this).isExist(url)){
			new SubcribeAsyncTask(this).execute(url);
		}
	}
	
	
	private class SubcribeAsyncTask extends DownloadXmlAsyncTask{

		public SubcribeAsyncTask(Context context) {
			super(context);	
		}
		
	}
}
