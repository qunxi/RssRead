package com.example.rssreader.core;

public class Entry {
	private String title;
	private String link;
	private String description;
	private String content;
	
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
	
	public String getLink(){
		return link;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getContent(){
		return content;
	}
}
