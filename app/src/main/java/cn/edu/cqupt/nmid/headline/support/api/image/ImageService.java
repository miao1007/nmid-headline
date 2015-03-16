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
  String AVATAR = "avatar";
  String LIMIT = "limit";

  //upload image file
  @Multipart @POST("/api/android/upload") void updateImage(
      @Part(IMAGE) TypedFile photo,
      @Part(NICKNAME) TypedString description,
      @Part(DEVICE_INFO) TypedString deviceinfo,
      @Part(AVATAR) TypedString avatar, Callback<UploadResult> callback);

  //get image list
  @GET("/api/android/freshimage") void getRefreshImage(@Query("id") int id, @Query(LIMIT) int limit,
      Callback<ImageStream> imageStreamCallback);

  //get image list
  @GET("/api/android/oldimage") void getROldImage(@Query("id") int id, @Query("limit") int limit,
      Callback<ImageStream> imageStreamCallback);

  //get image Comment list
  @GET("/api/android/getimagecomment") void getImageComments(@Query("id") int id,
      Callback<ImageCommnetReslut> commnetReslutCallback);

  //Like a image
  //http://202.202.43.205:8086/api/android/imagelike
  @GET("/api/android/imagelike") void likeImage(@Query("id") int id, @Query("command") int like,
      Callback<ImageLikeResult> callback);


  @GET("/api/android/imagecomment") void commentImage(@Query("id") int id,
      @Query("nickname") String namename,@Query("avatar") String avatar, @Query("comment") String comment,@Query("command")int cmd,
      Callback<ImageLikeResult> resultCallback);
}
