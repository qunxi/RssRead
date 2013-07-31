package github.com.qunxi.rssreader.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;

public class Entry extends EntityObject {
	
	private static final int SUMMARY_END = 50;
	private static final int SUMMARY_BEGIN = 0;
	
	private long id;
	private String title;
	private String link;
	private boolean unread = true;
	private String summary;
	private String content;
	private String updated;
	private long feedId = -1;
	
	public Entry(){
		
	}
	
	/*public Entry(String title, String link, String description, String content, String updated)
	{
		this.title = title;
		this.link = link;
		//this.description = description;
		this.content = content;
		this.updated = updated;
	}*/
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	/*public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}*/
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
		String contents = Html.fromHtml(content.replaceAll("<img.+?>", "")).toString();
		int length = contents.length();
		int end = length < SUMMARY_END ? length : SUMMARY_END; 
		String summary = contents.substring(SUMMARY_BEGIN,  end);
		setSummary(summary);
	}
	
	/*public String getSummary(){
		/*if(description != null){
			return description;
		}else{
			String contents = Html.fromHtml(content.replaceAll("<img.+?>", "")).toString();
			int length = contents.length();
			int end = length < SUMMARY_END ? length : SUMMARY_END; 
			String summary = contents.substring(SUMMARY_BEGIN,  end);
			return summary;
		}
	}*/
	
	public String getThumbImage(){
		Pattern p=Pattern.compile("<img\\s+src\\s*=\\s*\"(\\w+)\">");
		Matcher m=p.matcher(content);    
		if(m.find()){
			return m.group(1);
		}
		return null;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long categoryId) {
		this.feedId = categoryId;
	}

	public boolean isUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public boolean equals(Entry entry){
		if(this.content == entry.getContent() 
		   && this.feedId == entry.getFeedId()
		   && this.link == entry.getLink()
		   && this.unread == entry.isUnread()
		   && this.summary == entry.getSummary()
		   && this.title == entry.getTitle()
		   && this.updated == entry.getUpdated()){
			return true;
		}
		else
			return false;
	}
}
