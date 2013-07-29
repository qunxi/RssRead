package github.com.qunxi.rssreader.model;

import java.util.List;

public class Feed extends EntityObject {
	private long id = -1;
	private String title = null;
	private String updated = null;
	private String url = null;
	private List<Entry> entries = null;
	
	//not database fields
	private long counts = 0;
	private long unread = 0;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<Entry> getEntries() {
		return entries;
	}
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		for(Entry entry: entries){
			entry.setFeedId(id);
		}
	}
	public long getCounts() {
		return counts;
	}
	public void setCounts(long counts) {
		this.counts = counts;
	}
	public long getUnread() {
		return unread;
	}
	public void setUnread(long unread) {
		this.unread = unread;
	}

	
	/*private List<Entry> entries;
	private Category category;
	
	public Feed( Category category, List<Entry> entries){
		this.entries = entries;
		this.category = category;
	}
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setCategoryId(long id){
		if(category != null){
			category.setId(id);
		}
		
		for(Entry entry : entries){
			entry.setCategoryId(id);
		}
	}*/
}
