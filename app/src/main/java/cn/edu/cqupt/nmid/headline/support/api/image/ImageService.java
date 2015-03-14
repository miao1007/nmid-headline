package cn.edu.cqupt.nmid.headline.support.api.image;

import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageCommnetReslut;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageLikeResult;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageStream;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.UploadResult;
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
public interface ImageService {

  String IMAGE = "image";
  String NICKNAME = "nickname";
  String DEVICE_INFO = "deviceinfo";

  //upload file
  @Multipart @POST("/api/android/upload") void updateImage(@Part(IMAGE) TypedFile photo,
      @Part(NICKNAME) TypedString description, @Part(DEVICE_INFO) TypedString deviceinfo,
      Callback<UploadResult> callback);

  //get image list
  @GET("/api/android/freshimage") void getRefreshImage(@Query("id") int id,
      @Query("limit") int limit, Callback<ImageStream> imageStreamCallback);

  //get image list
  @GET("/api/android/oldimage") void getROldImage(@Query("id") int id, @Query("limit") int limit,
      Callback<ImageStream> imageStreamCallback);

  //get Comment list
  //http://115.29.139.53:8080/api/android/getimagecomment?id=1
  @GET("/api/android/getimagecomment")
  void getImageComments(@Query("id")int id,Callback<ImageCommnetReslut> commnetReslutCallback);

  //Like a image
  @GET("/api/android/imagepraise")
  //http://115.29.139.53:8080/api/android/imagepraise?id=2
  void likeImage(@Query("id")int id, Callback<ImageLikeResult> callback);

  //http://115.29.139.53:8080/api/android/imagecomment?id=1&nickname=xiaowenwen&comment=%E6%B5%8B%E8%AF%951
  @GET("/api/android/imagecomment")
  void commentImage(@Query("id")int id,@Query("nickname")String namename,@Query("comment")String comment,Callback<ImageLikeResult> resultCallback);

}
