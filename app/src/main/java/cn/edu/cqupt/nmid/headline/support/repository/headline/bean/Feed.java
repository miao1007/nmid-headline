package cn.edu.cqupt.nmid.headline.support.repository.headline.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name = "Feed") public class Feed extends Model implements Parcelable {

  @SerializedName("idmember") @Column(name = "idmember") int idmember;

  @SerializedName("category") @Column(name = "category") int category;

  @SerializedName("title") @Column(name = "title") String title;

  @SerializedName("simple_content") @Column(name = "simple_content") String simple_content;

  @SerializedName("image1") @Column(name = "image1") String image1;

  @SerializedName("image2") @Column(name = "image2") String image2;

  @SerializedName("image3") @Column(name = "image3") String image3;

  @SerializedName("time_release") @Column(name = "time_release") String timeRelease;

  @SerializedName("isLike") @Column(name = "isLike") boolean isLike;

  public Feed(int idmember, int category) {
    this.idmember = idmember;
    this.category = category;
  }

  public boolean isLike() {
    return isLike;
  }

  public void setLike(boolean isLike) {
    this.isLike = isLike;
  }

  public int getIdmember() {
    return idmember;
  }

  public void setIdmember(int idmember) {
    this.idmember = idmember;
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
   * @return The simple_content
   */
  public String getSimple_content() {
    return simple_content;
  }

  /**
   * @param simple_content The simple_content
   */
  public void setSimple_content(String simple_content) {
    this.simple_content = simple_content;
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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.idmember);
    dest.writeInt(this.category);
    dest.writeString(this.title);
    dest.writeString(this.simple_content);
    dest.writeString(this.image1);
    dest.writeString(this.image2);
    dest.writeString(this.image3);
    dest.writeString(this.timeRelease);
    dest.writeByte(isLike ? (byte) 1 : (byte) 0);
  }

  public Feed() {
  }

  public Feed(int idmember, int category, String title, String simple_content, String image1,
      String image2, String image3, String timeRelease, boolean isLike) {
    this.idmember = idmember;
    this.category = category;
    this.title = title;
    this.simple_content = simple_content;
    this.image1 = image1;
    this.image2 = image2;
    this.image3 = image3;
    this.timeRelease = timeRelease;
    this.isLike = isLike;
  }

  private Feed(Parcel in) {
    this.idmember = in.readInt();
    this.category = in.readInt();
    this.title = in.readString();
    this.simple_content = in.readString();
    this.image1 = in.readString();
    this.image2 = in.readString();
    this.image3 = in.readString();
    this.timeRelease = in.readString();
    this.isLike = in.readByte() != 0;
  }

  public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
    public Feed createFromParcel(Parcel source) {
      return new Feed(source);
    }

    public Feed[] newArray(int size) {
      return new Feed[size];
    }
  };
}
