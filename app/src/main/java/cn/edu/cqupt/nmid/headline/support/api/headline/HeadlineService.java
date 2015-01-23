package cn.edu.cqupt.nmid.headline.support.api.headline;

import cn.edu.cqupt.nmid.headline.support.api.headline.bean.HeadJson;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by leon on 12/27/14.
 */
public interface HeadlineService {
  public String END_POINT = "http://192.168.99.148:8888/";


  //1	int	学院新闻
  //2	int	科研动态
  //3	int	青春通信
  //4	int	校友新闻
  //required
  public int CATE_SCHOOL = 1;
  public int CATE_TECHNOLOGY = 2;
  public int CATE_TELECOMMUNICATION = 3;
  public int CATE_ALUMNUS = 4;

  public String CATEGORY = "category";

  //关键字	类型	含义	说明
  //自然数	int	设置刷新新闻起始id
  //返回大于该id的新闻，按id升序排列"
  //返回小于该id的新闻，按id逆序排列
  public String ID = "id";

  //若需更新的新闻条数大于请求数，返回最新的limit条新闻，若需更新的新闻条数小于请求数，只返回需更新的新闻。
  public String LIMIT = "limit";

  @GET("/api/android/freshnews") void getFreshFeeds(@Query(CATEGORY) int category,
      @Query(ID) int id, @Query(LIMIT) int limit, Callback<HeadJson> callback);

  @GET("/api/android/oldnews") void getOldFeeds(@Query(CATEGORY) int category,
      @Query(ID) int id, @Query(LIMIT) int limit, Callback<HeadJson> callback);

  @GET("/api/android/newscontent") void getNewsContent(@Query(CATEGORY) int category,
      @Query(ID) int id, Callback<Void> callback);
}
