package cn.edu.cqupt.nmid.headline.support.api.image.bean;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ImageStream {

  @SerializedName("status") int status;
  @SerializedName("data") List<ImageInfo> data = new ArrayList<ImageInfo>();

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
  public List<ImageInfo> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<ImageInfo> data) {
    this.data = data;
  }
}
