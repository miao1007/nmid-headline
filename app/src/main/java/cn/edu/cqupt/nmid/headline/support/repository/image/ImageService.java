package cn.edu.cqupt.nmid.headline.support.repository.image;

import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageCommnetReslut;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageLikeResult;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageStream;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.UploadResult;
import java.io.File;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import rx.Observable;

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
      @Part(IMAGE) File photo,
      @Part(NICKNAME) String description,
      @Part(DEVICE_INFO) String deviceinfo,
      @Part(AVATAR) String avatar, Callback<UploadResult> callback);

  @Multipart @POST("/api/android/upload") Observable<UploadResult> getupdateImage(
      @Part(IMAGE) File photo,
      @Part(NICKNAME) String description,
      @Part(DEVICE_INFO) String deviceinfo,
      @Part(AVATAR) String avatar);

  //get image list
  @GET("/api/android/freshimage") Call<ImageStream> getRefreshImage(@Query("id") int id, @Query(LIMIT) int limit);

  //get image list
  @GET("/api/android/oldimage") Call<ImageStream> getROldImage(@Query("id") int id, @Query("limit") int limit);

  //get image Comment list
  @GET("/api/android/getimagecomment") void getImageComments(@Query("id") int id,
      Callback<ImageCommnetReslut> commnetReslutCallback);

  //Like a image
  //http://202.202.43.205:8086/api/android/imagelike
  @GET("/api/android/imagelike") Call<ImageLikeResult> likeImage(@Query("id") int id, @Query("command") int like);


  @GET("/api/android/imagecomment") void commentImage(@Query("id") int id,
      @Query("nickname") String namename,@Query("avatar") String avatar, @Query("comment") String comment,@Query("command")int cmd,
      Callback<ImageLikeResult> resultCallback);
}
