package cn.edu.cqupt.nmid.headline.support.repository.image.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageComment {

  //{"status":1,"data":[{"comment_id":"2","nickname":"nickname","avatar":"avatarurl","comment":"comment","time":"2015-03-15 13:05:25"}]}

  @SerializedName("comment_id") int commentId;
  @SerializedName("nickname") String nickname;
  @SerializedName("comment") String comment;
  @SerializedName("time") String time;
  private String avatar;

  @Expose(deserialize = false) private boolean isCancelable;

  public int getCommentId() {
    return commentId;
  }

  public void setCommentId(int commentId) {
    this.commentId = commentId;
  }

  public boolean isCancelable() {
    return isCancelable;
  }

  public void setIsCancelable(boolean isCancelable) {
    this.isCancelable = isCancelable;
  }

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
