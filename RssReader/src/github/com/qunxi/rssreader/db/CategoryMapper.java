package github.com.qunxi.rssreader.db;

import java.util.ArrayList;
import java.util.List;

import github.com.qunxi.rssreader.model.Category;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class CategoryMapper extends AbstractMapper<Category>
{
	public CategoryMapper(Context context) {
		super(context);
	}

	@Override
	public List<Category> loadAll(long id, int offset) 
	{
		List<Category> categories = new ArrayList<Category>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ")
		   .append(CategoryTable.TABLE_NAME)
		   .append(" LIMIT ")
		   .append(LIMIT)
		   .append(" OFFSET ")
		   .append(offset);
		Cursor c = rdb.rawQuery(sql.toString(), null);
	
		while (c.moveToNext()) {
			Category category = new Category();
			
			int colid = c.getColumnIndex(CategoryTable.COUNT);
			category.setCounts(c.getLong(colid));
			
			colid = c.getColumnIndex(CategoryTable.ID);
			category.setId(c.getLong(colid));
			
			colid = c.getColumnIndex(CategoryTable.TITLE);
			category.setTitle(c.getString(colid));
			
			colid = c.getColumnIndex(CategoryTable.UPDATE_DATE);
			category.setUpdateTime(c.getString(colid));
			
			colid = c.getColumnIndex(CategoryTable.URL);
			category.setUrl(c.getString(colid));
			
			categories.add(category);
	     }
	     
		c.close();
		
		return categories;
	}

	@Override
	public Category load(long id) 
	{
		if(!exist(id)){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ")
		   .append(EntryTable.TABLE_NAME)
		   .append(" WHERE ")
		   .append(CategoryTable.ID)
		   .append(" = ")
		   .append(id);
		Cursor c = rdb.rawQuery(sql.toString(), null);
		
		Category category = new Category();
		
		while (c.moveToNext()) {
						
			int colid = c.getColumnIndex(CategoryTable.UPDATE_DATE);
			category.setUpdateTime(c.getString(colid));
			
			colid = c.getColumnIndex(CategoryTable.TITLE);
			category.setTitle(c.getString(colid));
	     }
	     
		 c.close();
		 return category;
	}

	@Override
	public long insert(Category category) 
	{
		ContentValues values = new ContentValues();
		values.put(CategoryTable.TITLE, category.getTitle());
		values.put(CategoryTable.COUNT, -1);
		values.put(CategoryTable.UPDATE_DATE, category.getUpdateTime());
		values.put(CategoryTable.URL, category.getUrl());
		return wdb.insert(CategoryTable.TABLE_NAME, CategoryTable.ID, values);
	}

	@Override
	public boolean insertAll(List<Category> objects) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean exist(long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ")
		   .append(CategoryTable.TABLE_NAME)
		   .append(" WHERE ")
		   .append(CategoryTable.ID)
		   .append(" = ")
		   .append(id);
		Cursor c = rdb.rawQuery(sql.toString(), null);
		if(c.getCount() == 0){
			return false;
		}
		return true;
	}
	
	public int updateDate(long id, String date)
	{
		ContentValues cv = new ContentValues();
		cv.put(CategoryTable.UPDATE_DATE, date);
		return wdb.update(CategoryTable.TABLE_NAME, cv, CategoryTable.ID + "=" + id, null);
	}

}

