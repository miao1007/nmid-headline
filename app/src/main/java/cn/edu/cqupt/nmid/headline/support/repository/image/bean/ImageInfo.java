package cn.edu.cqupt.nmid.headline.support.repository.image.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

@Table(name = "ImageInfo") public class ImageInfo extends Model {

  @SerializedName("idmember") @Column(name = "idmember") int idmember;
  @SerializedName("imageurl") @Column(name = "imageurl") String imageurl;
  @SerializedName("previrousurl") @Column(name = "previrousurl") String previrousurl;
  @SerializedName("nickname") @Column(name = "nickname") String nickname;
  @SerializedName("deviceinfo") @Column(name = "deviceinfo") String deviceinfo;
  @SerializedName("uploadtime") @Column(name = "uploadtime") long uploadtime;
  @SerializedName("count_like") @Column(name = "count_like") int count_like;
  @SerializedName("avatar") @Column(name = "avatar") String avatar;

  @Expose(serialize = false) private boolean isHaveClickLike = false;

  public boolean isHaveClickLike() {
    return isHaveClickLike;
  }

  public void setIsLike(boolean isLike) {
    this.isHaveClickLike = isLike;
  }

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
  public long getUploadtime() {
    return uploadtime;
  }

  /**
   * @param uploadtime The uploadtime
   */
  public void setUploadtime(long uploadtime) {
    this.uploadtime = uploadtime;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
