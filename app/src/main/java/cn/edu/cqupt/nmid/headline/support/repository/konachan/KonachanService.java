package cn.edu.cqupt.nmid.headline.support.repository.konachan;

import cn.edu.cqupt.nmid.headline.support.repository.konachan.bean.KnoImage;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by leon on 2/2/15.
 */
public interface KonachanService {

  final static String ENDPONIT = "http://konachan.net";
  final static String TAGS = "tags";
  final static String PAGE = "page";
  final static String LIMIT = "limit";

  @GET("/post.json") void searchByTagsAndPage(@Query(TAGS) String tags,
      @Query(PAGE) int page, @Query(LIMIT) int limit, Callback<List<KnoImage>> callback);
}
