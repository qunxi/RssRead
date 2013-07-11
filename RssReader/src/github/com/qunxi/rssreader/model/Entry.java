package github.com.qunxi.rssreader.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;

public class Entry {
	
	private static final int SUMMARY_END = 150;
	private static final int SUMMARY_BEGIN = 0;
	
	private String title;
	private String link;
	private String description;
	private String content;
	private String updated;
	
	public Entry(){
		
	}
	
	public Entry(String title, String link, String description, String content, String updated)
	{
		this.title = title;
		this.link = link;
		this.description = description;
		this.content = content;
		this.updated = updated;
	}
	
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
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getSummary(){
		if(description != null){
			return description;
		}else{
			String contents = Html.fromHtml(content.replaceAll("<img.+?>", "")).toString();
			int length = contents.length();
			int end = length < SUMMARY_END ? length : SUMMARY_END; 
			String summary = contents.substring(SUMMARY_BEGIN,  end);
			return summary;
		}
	}
	
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
}
