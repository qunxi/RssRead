package github.com.qunxi.rssreader.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.provider.BaseColumns;

public abstract class Table implements BaseColumns
{	

	public abstract String getTableName();
	
	public Map<String, String> getTableColumns(){
		return columns;
	}
	
	protected Map<String, String> columns =  new HashMap<String, String>();
	
	
	public String createTableSQL() throws Exception{
		String tableName = getTableName();
		
		if((tableName == null || tableName == "") 
		   &&(columns == null ||columns.isEmpty())){
			throw new Exception();
		}
		
		StringBuilder creator = new StringBuilder();
		creator.append("CREATE TABLE ").append(tableName).append("(");
		
		Iterator<String> keys = columns.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			creator.append(key).append(" ").append(columns.get(key)).append(",");
		}
		int lastIndex = creator.length() - 1;
		
		creator.replace(lastIndex, lastIndex+1, ")");
		return creator.toString();
	}
	
	public String dropTableSQL() throws Exception{
		String tableName = getTableName();
		
		if(tableName == null || tableName == ""){
			throw new Exception();
		}
		
		StringBuilder drop = new StringBuilder();
		drop.append("DROP TABLE IF EXISTS ").append(tableName);
		return drop.toString();
	}
}
