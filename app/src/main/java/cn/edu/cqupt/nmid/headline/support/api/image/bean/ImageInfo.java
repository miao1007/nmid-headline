package cn.edu.cqupt.nmid.headline.support.api.image.bean;

import java.util.HashMap;
import java.util.Map;

public class ImageInfo {

  private int idmember;
  private String imageurl;
  private String previrousurl;
  private String nickname;
  private String deviceinfo;
  private String uploadtime;
  private int count_like;
  private String avatar;

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public int getCount_like() {
    return count_like;
  }

  public void setCount_like(int count_like) {
    this.count_like = count_like;
  }

  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public int getIdmember() {
    return idmember;
  }

  public void setIdmember(int idmember) {
    this.idmember = idmember;
  }

  public String getImageurl() {
    return imageurl;
  }

  /**
   * @param imageurl The imageurl
   */
  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }

  /**
   * @return The previrousurl
   */
  public String getPrevirousurl() {
    return previrousurl;
  }

  /**
   * @param previrousurl The previrousurl
   */
  public void setPrevirousurl(String previrousurl) {
    this.previrousurl = previrousurl;
  }

  /**
   * @return The nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @param nickname The nickname
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * @return The deviceinfo
   */
  public String getDeviceinfo() {
    return deviceinfo;
  }

  /**
   * @param deviceinfo The deviceinfo
   */
  public void setDeviceinfo(String deviceinfo) {
    this.deviceinfo = deviceinfo;
  }

  /**
   * @return The uploadtime
   */
  public String getUploadtime() {
    return uploadtime;
  }

  /**
   * @param uploadtime The uploadtime
   */
  public void setUploadtime(String uploadtime) {
    this.uploadtime = uploadtime;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
