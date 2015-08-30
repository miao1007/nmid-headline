package cn.edu.cqupt.nmid.headline.support.event;

/**
 * Created by leon on 15/5/20.
 */
public class ImageUploadEvent {
  boolean isSuccess;

  public ImageUploadEvent(boolean isSuccess) {
    this.isSuccess = isSuccess;
  }
}
