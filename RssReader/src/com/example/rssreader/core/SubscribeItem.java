package com.example.rssreader.core;

public class SubscribeItem {
	private String title;
	private String updateTime;
	private long counts;
	private String addrPic;
	
	public SubscribeItem(String title, String updateTime, long counts, String addrPic)
	{
		this.title = title;
		this.updateTime = updateTime;
		this.counts = counts;
		this.addrPic = addrPic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public long getCounts() {
		return counts;
	}

	public void setCounts(long counts) {
		this.counts = counts;
	}

	public String getAddrPic() {
		return addrPic;
	}

	public void setAddrPic(String addrPic) {
		this.addrPic = addrPic;
	}
}
