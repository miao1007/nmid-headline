package cn.edu.cqupt.nmid.headline.support.api.image.bean;

import com.google.gson.annotations.Expose;

public class ImageComment {

  @Expose private String nickname;
  @Expose private String comment;
  @Expose private String time;

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
