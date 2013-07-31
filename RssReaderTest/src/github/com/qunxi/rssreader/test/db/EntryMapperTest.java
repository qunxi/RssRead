package github.com.qunxi.rssreader.test.db;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import github.com.qunxi.rssreader.db.EntryMapper;
import github.com.qunxi.rssreader.db.EntryTable;
import github.com.qunxi.rssreader.db.MapperRegister;
import github.com.qunxi.rssreader.model.Entry;
import android.content.Context;
import android.test.InstrumentationTestCase;

public class EntryMapperTest extends InstrumentationTestCase  {
	private String DatabaseName = "test.db";
	private int DatabaseVersion = 1;
	EntryMapper entryMapper = null;
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		Context context = getInstrumentation().getContext();
		//new MapperRegister(context, DatabaseName, DatabaseVersion);
		//entryMapper = MapperRegister.entry();
	}
	
	public void test_add_getBySpec_Success(){
		Entry expectedEntry = new Entry();
		expectedEntry.setContent("content");
		expectedEntry.setFeedId(1);
		expectedEntry.setLink("link");
		expectedEntry.setTitle("title");
		expectedEntry.setUpdated("2013-1-1");
		expectedEntry.setSummary("summary");
		/*long id = entryMapper.(expectedEntry);
		Specification spec = new Specification();
		Map<String,String> andWhere = new HashMap<String,String>();
		andWhere.put(EntryTable._ID, Long.toString(id));
		spec.setAndWhere(andWhere);
		Entry actualEntry = (Entry) (entryMapper.getBySpec(spec).get(0));
		Assert.assertTrue(actualEntry.equals(expectedEntry));*/
	}
}
