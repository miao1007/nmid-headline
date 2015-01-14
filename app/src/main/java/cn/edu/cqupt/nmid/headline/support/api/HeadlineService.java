package cn.edu.cqupt.nmid.headline.support.api;

import cn.edu.cqupt.nmid.headline.support.controller.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.support.Constant;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by leon on 12/27/14.
 */
public interface HeadlineService {
    public String END_POINT = Constant.ENDPOINT;
    public String API = "/txtt/public/api/android";
    public String TYPE = "type";
    public String ID = "id";
    public String CATEGORY = "category";
    public String LIMIT = "limit";


    @GET("/txtt/public/api/android/freshnews")
    void getFreshFeeds(@Query("category") int type,
                       @Query("id") int id,
                       @Query("limit") int limit,
                       Callback<HeadJson> callback);


    @GET("/txtt/public/api/android/oldnews")
    void getOldFeeds(@Query("category") int type,
                     @Query("id") int id,
                     @Query("limit") int limit,
                     Callback<HeadJson> callback);

}
