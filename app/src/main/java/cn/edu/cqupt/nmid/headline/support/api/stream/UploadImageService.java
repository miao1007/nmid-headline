package cn.edu.cqupt.nmid.headline.support.api.stream;

import cn.edu.cqupt.nmid.headline.support.api.stream.bean.ImageStream;
import cn.edu.cqupt.nmid.headline.support.api.stream.bean.UploadResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by leon on 2/2/15.
 */
public interface UploadImageService {

  public static String IMAGE = "image";
  public static String NICKNAME = "nickname";
  public static String DEVICE_INFO = "deviceinfo";

  //upload file
  @Multipart @POST("/api/android/upload") void updateImage(@Part(IMAGE) TypedFile photo,
      @Part(NICKNAME) TypedString description, @Part(DEVICE_INFO) TypedString deviceinfo,
      Callback<UploadResult> callback);

  //get image list
  @GET("/api/android/freshimage")
  void getRefreshImage(@Query("id") int id, @Query("limit") int limit,
      Callback<ImageStream> imageStreamCallback);

  //get image list
  @GET("/api/android/oldimage")
  void getROldImage(@Query("id") int id, @Query("limit") int limit,
      Callback<ImageStream> imageStreamCallback);

}
