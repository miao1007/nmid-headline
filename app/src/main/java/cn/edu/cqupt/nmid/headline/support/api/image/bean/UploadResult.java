package cn.edu.cqupt.nmid.headline.support.api.image.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leon on 2/2/15.
 */
public class UploadResult {

  @SerializedName("status")  int status;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
