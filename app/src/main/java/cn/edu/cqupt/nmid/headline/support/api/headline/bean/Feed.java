package cn.edu.cqupt.nmid.headline.support.api.headline.bean;

public class Feed {
	private int id;
	private int category;
	private String title;
	private String simple_content;
	private String image1;
	private String image2;
	private String image3;
	private String time_release;
	private int isCollect;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSimple_content() {
		return simple_content;
	}
	public void setSimple_content(String simple_content) {
		this.simple_content = simple_content;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public String getTime_release() {
		return time_release;
	}
	public void setTime_release(String time_release) {
		this.time_release = time_release;
	}
	public int getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
	
}
