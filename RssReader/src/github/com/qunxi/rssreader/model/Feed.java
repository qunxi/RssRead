package github.com.qunxi.rssreader.model;

import java.util.List;

public class Feed {
	private List<Entry> entries;
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
}
