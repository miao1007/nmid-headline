package cn.edu.cqupt.nmid.headline.support.repository.headline;

import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.HeadJson;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by leon on 12/27/14.
 */
public interface HeadlineService {

  String END_POINT = "http://202.202.43.205:8086/";

  //1	int	学院新闻
  //2	int	科研动态
  //3	int	青春通信
  //4	int	校友新闻
  //required
  int CATE_SCHOOL = 1;
  int CATE_TECHNOLOGY = 2;
  int CATE_TELECOMMUNICATION = 3;
  int CATE_ALUMNUS = 4;

  int STATUS_OK = 1;
  int STATUS_ERR = 0;

  String CATEGORY = "category";

  //关键字	类型	含义	说明
  //自然数	int	设置刷新新闻起始id
  //返回大于该id的新闻，按id升序排列"
  //返回小于该id的新闻，按id逆序排列
  
  
  String ID = "id";

  //若需更新的新闻条数大于请求数，返回最新的limit条新闻，若需更新的新闻条数小于请求数，只返回需更新的新闻。
  String LIMIT = "limit";

  @GET("/api/android/freshnews") Call<HeadJson> getFreshFeeds(@Query(CATEGORY) int category,
      @Query(ID) int id, @Query(LIMIT) int limit);

  @GET("/api/android/oldnews") Call<HeadJson> getOldFeeds(@Query(CATEGORY) int category, @Query(ID) int id,
      @Query(LIMIT) int limit);

  @GET("/api/android/newscontent") Call<HeadJson> getNewsContent(@Query(CATEGORY) int category,
      @Query(ID) int id, Callback<Void> callback);
}
