package github.com.qunxi.rssreader.test.db;

import java.util.ArrayList;
import java.util.List;

import com.example.rssreader.test.MainActivityTest;

import junit.framework.Assert;
import github.com.qunxi.rssreader.db.DatabaseHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;


public class DatabaseHelperTest extends AndroidTestCase {

	

	private static final String DatabaseName = "test.db";
	private static final int DatabaseVersion = 1;
	private static final int NewDatabaseVersion = 2;
	private SQLiteDatabase wdb = null;
	private DatabaseHelper helper = null;
	private static final String sql = "SELECT name FROM sqlite_master WHERE type='table'";
	
	/*@Override
	protected void setUp() throws Exception{
		
		Context context = getInstrumentation().getContext();
		helper = DatabaseHelper.instance(context, DatabaseName, DatabaseVersion);
		wdb = helper.getWritableDatabase();
		super.setUp();
	}*/
	
	public void test_onCreate_Success(){
		//Context context = getInstrumentation().getContext();
		/*IsolatedContext context 
	        = new IsolatedContext(null, getContext());*/
		helper = DatabaseHelper.instance(getContext(), DatabaseName, DatabaseVersion);
		wdb = helper.getWritableDatabase();
		Cursor c = wdb.rawQuery(sql, null);
		List<String> actualTable = new ArrayList<String>();
		while(c.moveToNext()){
			int colid = c.getColumnIndex("name");
			actualTable.add(c.getString(colid));
		}
		String[] expectedTable = {"entry", "feed"};
		Assert.assertTrue(actualTable.contains(expectedTable));
	}
	
	public void test_onUpdate_Success(){
		
		/*helper.onUpgrade(wdb, DatabaseVersion, NewDatabaseVersion);
		Cursor c = wdb.rawQuery(sql, null);
		Assert.assertTrue(c.getCount() == 0);*/
	}
}



