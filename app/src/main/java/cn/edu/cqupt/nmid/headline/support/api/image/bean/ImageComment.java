package cn.edu.cqupt.nmid.headline.support.api.image.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageComment {

  //{"status":1,"data":[{"comment_id":"2","nickname":"nickname","avatar":"avatarurl","comment":"comment","time":"2015-03-15 13:05:25"}]}

  @SerializedName("comment_id")
  private String commentId;
  @Expose private String nickname;
  @Expose private String comment;
  @Expose private String time;
  private String avatar;

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
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
   * @return The comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * @param comment The comment
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * @return The time
   */
  public String getTime() {
    return time;
  }

  /**
   * @param time The time
   */
  public void setTime(String time) {
    this.time = time;
  }
}
