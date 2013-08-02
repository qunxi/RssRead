package github.com.qunxi.rssreader.ui;

import com.example.rssreader.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		TextView titleView = (TextView) findViewById(R.id.artical_title);
		titleView.setText(getIntent().getStringExtra("title"));
		
		TextView updatedView = (TextView) findViewById(R.id.artical_updated);
		updatedView.setText(getIntent().getStringExtra("updated"));
		
		TextView categoryView = (TextView) findViewById(R.id.categoryTitle);
		categoryView.setText(getIntent().getStringExtra("categoryTitle"));
		
		WebView view = (WebView) findViewById(R.id.webview);
		String content = getIntent().getStringExtra("content");
		WebSettings webSettings= view.getSettings(); 
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		
		view.loadDataWithBaseURL (null, content, "text/html", "utf-8",null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

}
