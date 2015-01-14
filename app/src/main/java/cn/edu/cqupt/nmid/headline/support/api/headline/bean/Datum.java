
package cn.edu.cqupt.nmid.headline.support.api.headline.bean;


public class Datum {

    private int id;
    private int category;
    private String title;
    private String simple_content;
    private String image1;
    private String image2;
    private String image3;
    private String time_release;

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The category
     */
    public int getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The image1
     */
    public String getImage1() {
        return image1;
    }

    /**
     * 
     * @param image1
     *     The image1
     */
    public void setImage1(String image1) {
        this.image1 = image1;
    }

    /**
     * 
     * @return
     *     The image2
     */
    public String getImage2() {
        return image2;
    }

    /**
     * 
     * @param image2
     *     The image2
     */
    public void setImage2(String image2) {
        this.image2 = image2;
    }

    /**
     * 
     * @return
     *     The image3
     */
    public String getImage3() {
        return image3;
    }

    /**
     * 
     * @param image3
     *     The image3
     */
    public void setImage3(String image3) {
        this.image3 = image3;
    }

	public String getSimple_content() {
		return simple_content;
	}

	public void setSimple_content(String simple_content) {
		this.simple_content = simple_content;
	}

	public String getTime_release() {
		return time_release;
	}

	public void setTime_release(String time_release) {
		this.time_release = time_release;
	}
    

}
