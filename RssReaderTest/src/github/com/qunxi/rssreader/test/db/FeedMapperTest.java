package github.com.qunxi.rssreader.test.db;

import github.com.qunxi.rssreader.db.FeedMapper;
import github.com.qunxi.rssreader.db.MapperRegister;
import android.content.Context;
import android.test.InstrumentationTestCase;

public class FeedMapperTest extends InstrumentationTestCase {
	private String DatabaseName = "test.db";
	private int DatabaseVersion = 1;
	FeedMapper feedMapper = null;
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		Context context = getInstrumentation().getContext();
		//new MapperRegister(context, DatabaseName, DatabaseVersion);
		//feedMapper = MapperRegister.feed();
	}
	
	public void test_add_Success(){
		//feedMapper.add(entity)
	}
	
	public void test_getBySpec_Success(){
		
	}
	
	
}
