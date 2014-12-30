package cn.edu.cqupt.nmid.headline.api;

import cn.edu.cqupt.nmid.headline.controller.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.support.Constant;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by leon on 12/27/14.
 */
public interface HeadlineService {
    public String END_POINT = Constant.API_URL;
    public String API = "/txtt/public/api/android";
    public String TYPE = "type";
    public String ID = "id";
    public String CATEGORY = "category";
    public String LIMIT = "limit";

//
//    url = new URL(Constant.API_URL + "/txtt/public/api/android/" + type
//    + "?id=" + _id + "&category=" + category + "&limit="
//            + limit);
    @GET("/txtt/public/api/android/freshnews")
    void getFeeds(@Query("category") int type,
                  @Query("id") int id,
                  @Query("limit") int limit,
                  Callback<HeadJson> callback);

}
