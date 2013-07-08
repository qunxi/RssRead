package github.com.qunxi.rssreader.model;

public class Entry {
	private String title;
	private String link;
	private String description;
	private String content;
	
	public Entry(){
		
	}
	
	public Entry(String title, String link, String description, String content)
	{
		this.title = title;
		this.link = link;
		this.description = description;
		this.content = content;
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
}
