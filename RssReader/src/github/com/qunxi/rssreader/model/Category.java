package github.com.qunxi.rssreader.model;

public class Category {
	private String title;
	private String updateTime;
	private long counts;
	private String logo;
	private long id;
	
	public Category(){	
	}
	
	public Category(String title, String updateTime, long counts, String addrPic)
	{
		this.title = title;
		this.updateTime = updateTime;
		this.counts = counts;
		this.logo = addrPic;
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
		return logo;
	}

	public void setAddrPic(String addrPic) {
		this.logo = addrPic;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
