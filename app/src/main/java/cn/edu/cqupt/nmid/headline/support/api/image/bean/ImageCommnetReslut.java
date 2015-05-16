package cn.edu.cqupt.nmid.headline.support.api.image.bean;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ImageCommnetReslut {

  @SerializedName("status") private int status;
  @SerializedName("data") private List<ImageComment> data = new ArrayList<ImageComment>();

  /**
   * @return The status
   */
  public int getStatus() {
    return status;
  }

  /**
   * @param status The status
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * @return The data
   */
  public List<ImageComment> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<ImageComment> data) {
    this.data = data;
  }
}
