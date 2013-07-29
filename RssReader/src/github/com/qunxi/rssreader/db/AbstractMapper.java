package github.com.qunxi.rssreader.db;

import github.com.qunxi.rssreader.model.EntityObject;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractMapper{
	
	
		
	protected DatabaseHelper dbHelper = null; 
	protected SQLiteDatabase wdb = null;
	protected SQLiteDatabase rdb = null;
	
	protected static final int LIMIT = 10;
	
	public AbstractMapper(Context context, String database, int version){
		
		dbHelper = DatabaseHelper.instance(context, database, version);
		
		wdb = dbHelper.getWritableDatabase();
		rdb = dbHelper.getReadableDatabase();
		//dbHelper.onUpgrade(wdb, 1, 2);
	}
		
	protected abstract EntityObject doLoad(Cursor c);
	
	protected abstract long doInsert(EntityObject entity);
	
	//protected abstract String querySQL();
	
	protected List<? extends EntityObject> load(Cursor c/*Specification spec*/){
		//Cursor c = rdb.rawQuery(querySQL(), null);
		return loadEntity(c);
	}
	
	/*protected boolean modify(EntityObject entity){
		return false;
		
	}
	protected boolean remove(EntityObject entity) {
		return false;
	}*/
	
	protected long add(EntityObject entity) {
		return doInsert(entity);
	}
	
	protected boolean addAll(List<? extends EntityObject> entities){
		for(EntityObject entry : entities){
			if(add(entry) == -1)
			{
				return false;
			}
		}
		return true;
	}
	
	private List<EntityObject> loadEntity(Cursor c) 
	{
		List<EntityObject> entities = new ArrayList<EntityObject>();
		while (c.moveToNext()) {
			
			EntityObject entity = doLoad(c);
			
			if(entity != null){
				entities.add(entity);
			}
	     }
		return entities;
	}
	
	/*private String querySQL(Specification spec) {
		StringBuilder sql = new StringBuilder();
		
		String tableName = spec.getTableName();
		if(tableName == null || tableName == ""){
			return null;
		}
		sql.append("SELECT * FROM ")
		   .append(tableName);
		
		Map<String,String> andWhere = spec.getAndWhere();
		if(andWhere != null){
		   StringBuilder where = new StringBuilder();
		   where .append(" WHERE ");
		   Iterator<String> keys = andWhere.keySet().iterator();
			while(keys.hasNext()){
				String key = keys.next();
				where.append(key).append(" == ").append(andWhere.get(key)).append(" AND ");
			}
			int lastIndex = where.lastIndexOf("AND");
			sql.append(where.substring(0, lastIndex));
		}
		
		String[] orders = spec.getDescOrder();
		if(orders != null){
			sql.append(" ORDER BY ");
			for(int i = 0; i < orders.length; ++i){
				sql.append(orders[i]);
				if(i != orders.length-1){
					sql.append(",");
				}
			}
			sql.append(" DESC ");
		}

		if(spec.getLimit() != -1){
			sql.append(" LIMIT ")
			   .append(spec.getLimit());
		}
		
		if(spec.getOffset() != -1){
		   sql.append(" OFFSET ")
		      .append(spec.getOffset());
		}
		return sql.toString();
	}*/
	
}
