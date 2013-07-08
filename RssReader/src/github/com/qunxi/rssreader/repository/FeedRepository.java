package github.com.qunxi.rssreader.repository;

import github.com.qunxi.rssreader.db.CategoryMapper;
import github.com.qunxi.rssreader.db.EntryMapper;
import github.com.qunxi.rssreader.model.Feed;

public class FeedRepository {
	
	private CategoryMapper categoryMapper = null;
	private EntryMapper entryMapper = null;
	
	public Feed loadFeed(){
		//categoryMapper.loadCategory(id)
		return null;
	}
	
	public boolean SaveFeed(Feed feed){
		long id = categoryMapper.insert(feed.getCategory());
		if(id != -1){
			return entryMapper.batchInsert(feed.getEntries(), id) != -1 ? true : false;
		}
		return false;
	}
}
