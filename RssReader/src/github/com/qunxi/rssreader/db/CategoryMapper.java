package github.com.qunxi.rssreader.db;

import java.util.ArrayList;
import java.util.List;

import github.com.qunxi.rssreader.model.Category;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CategoryMapper {
	private DatabaseHelper dbHelper = null;
	private SQLiteDatabase wdb = null;
	private SQLiteDatabase rdb = null;

	private static final int LIMIT = 10;
	
	public CategoryMapper(Context context){
		dbHelper = DatabaseHelper.instance(context);
		
		wdb = dbHelper.getWritableDatabase();
		//dbHelper.onUpgrade(wdb, 1, 2);
		rdb = dbHelper.getReadableDatabase();
	}
	
	public Category loadCategory(long id){
		
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
	
	public List<Category> getCategories(int offset)
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
			
			categories.add(category);
	     }
	     
		c.close();
		
		return categories;
	}
	
	public long insert(Category category){
		ContentValues values = new ContentValues();
		values.put(CategoryTable.TITLE, category.getTitle());
		values.put(CategoryTable.COUNT, -1);
		values.put(CategoryTable.UPDATE_DATE, category.getUpdateTime());
		return wdb.insert(CategoryTable.TABLE_NAME, CategoryTable.ID, values);
	}
	
}
