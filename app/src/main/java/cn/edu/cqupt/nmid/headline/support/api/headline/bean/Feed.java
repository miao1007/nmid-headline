package cn.edu.cqupt.nmid.headline.support.api.headline.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;
@Table(name="basetable")
public class Feed extends Model{
  //do not use `id` because it has been defined in Model
  @Column(name = "idMember")
  @SerializedName(value="id")
  private int idMember;

  @Column(name = "category")
  private int category;

  @Column(name = "title")
  private String title;

  @Column(name = "simpleContent")
  @SerializedName("simple_content")
  private String simpleContent;

  @Column(name = "image1")
  private String image1;

  @Column(name = "image2")
  private String image2;

  @Column(name = "image3")
  private String image3;

  @Column(name = "timeRelease")
  @SerializedName("time_release")
  private String timeRelease;

  @Column(name = "isCollect")
  private boolean isCollect;


  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public boolean isCollect() {
    return isCollect;
  }

  public void setCollect(boolean isCollect) {
    this.isCollect = isCollect;
  }

  public int getIdMember() {
    return idMember;
  }

  public void setIdMember(int idMember) {
    this.idMember = idMember;
  }

  /**
   * @return The category
   */
  public int getCategory() {
    return category;
  }

  /**
   * @param category The category
   */
  public void setCategory(int category) {
    this.category = category;
  }

  /**
   * @return The title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title The title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return The simpleContent
   */
  public String getSimpleContent() {
    return simpleContent;
  }

  /**
   * @param simpleContent The simpleContent
   */
  public void setSimpleContent(String simpleContent) {
    this.simpleContent = simpleContent;
  }

  /**
   * @return The image1
   */
  public String getImage1() {
    return image1;
  }

  /**
   * @param image1 The image1
   */
  public void setImage1(String image1) {
    this.image1 = image1;
  }

  /**
   * @return The image2
   */
  public String getImage2() {
    return image2;
  }

  /**
   * @param image2 The image2
   */
  public void setImage2(String image2) {
    this.image2 = image2;
  }

  /**
   * @return The image3
   */
  public String getImage3() {
    return image3;
  }

  /**
   * @param image3 The image3
   */
  public void setImage3(String image3) {
    this.image3 = image3;
  }

  /**
   * @return The timeRelease
   */
  public String getTimeRelease() {
    return timeRelease;
  }

  /**
   * @param timeRelease The timeRelease
   */
  public void setTimeRelease(String timeRelease) {
    this.timeRelease = timeRelease;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
