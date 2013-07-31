package github.com.qunxi.rssreader.test.db;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;
import github.com.qunxi.rssreader.db.Table;


public class TableTest extends InstrumentationTestCase {

	/*@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public TableTest(){
		
	}*/
	
	public void test_CreateTableSQL_Success(){
		try {
			TestTable table = new TestTable();
			String actualSQL = table.createTableSQL();
			String expectedSQL = "CREATE TABLE test(_id INTEGER PRIMARY KEY,TestCaseID INTEGER,TestName TEXT)";
			
			Assert.assertEquals(expectedSQL, actualSQL);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void test_CreateTableSQL_NoneTableName(){
		try {
			NoneTable table = new NoneTable();
			table.createTableSQL();
			Assert.fail("throw a exception of table and columns is null");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void test_DropTableSQL_Success(){
		try {
			TestTable table = new TestTable();
			
			String actualSQL = table.dropTableSQL();
			String expectedSQL = "DROP TABLE IF EXISTS test";
			assertEquals(expectedSQL, actualSQL);
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	
	public void test_DropTableSQ_NoneTableName(){
		try {
			NoneTable table = new NoneTable();
			table.dropTableSQL();
			Assert.fail("throw a exception of table is null");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}

class NoneTable extends Table{
	public static final String TABLE_NAME = null;
	public static final Map<String, String> COLUMNS = null;
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}
}

class TestTable extends Table{
	public static final String TABLE_NAME = "test";
	
	public static final String TEST_NAME = "TestName";
	public static final String TESTCASE_ID = "TestCaseID";
	
	private static final Map<String, String> COLUMNS = new HashMap<String,String>();
	public TestTable(){
			COLUMNS.put(_ID, "INTEGER PRIMARY KEY");
			COLUMNS.put(TEST_NAME, "TEXT");
			COLUMNS.put(TESTCASE_ID, "INTEGER");
	};
	@Override
	public String getTableName(){
		return TABLE_NAME;
	}
}