package cn.edu.cqupt.nmid.headline.support.db.table;

import android.provider.BaseColumns;

/**
 * Created by leon on 1/20/15.
 */
public class TxttNewsBaseTable implements BaseColumns {

  public static final String TABLE_NAME = "txtt_news";

  //int(11)
  public static final String COLUMU_NAME_ID = "_id";
  //varchar(255)
  public static final String COLUMU_NAME_NEWS_ID = "news_id";
  //tinyint(1)
  public static final String COLUMN_NAME_CID = "cid";
  //varchar(255)
  public static final String COLUMN_NAME_TITLE = "title";
  //varchar(255)
  public static final String COLUMN_NAME_SIMPLE_CONTENT = "simple_content";
  //text
  public static final String COLUMN_NAME_CONTENT = "content";
  //varchar(255)
  public static final String COLUMN_NAME_IMAGE_1 = "image1";
  //varchar(255)
  public static final String COLUMN_NAME_IMAGE_2 = "image2";
  //varchar(255)
  public static final String COLUMN_NAME_IMAGE_3 = "image3";
  //varchar(255)
  public static final String COLUMN_NAME_TIME_RELEASE = "time_release";
  //int(11)
  public static final String COLUMN_NAME_TIME_CATCH = "time_catch";
  //varchar(255)
  public static final String COLUMN_NAME_URL = "url";
  //int(11)
  public static final String COLUMN_NAME_COUNT_PRAISE = "count_praise";
  //int(11)
  public static final String COLUMN_NAME_COUNT_READ = "count_read";

  public static final String[] COLUMNS = {
      COLUMU_NAME_ID, COLUMU_NAME_NEWS_ID, COLUMN_NAME_CID, COLUMN_NAME_TITLE,
      COLUMN_NAME_SIMPLE_CONTENT, COLUMN_NAME_CONTENT, COLUMN_NAME_IMAGE_1, COLUMN_NAME_IMAGE_2,
      COLUMN_NAME_IMAGE_3, COLUMN_NAME_TIME_RELEASE, COLUMN_NAME_TIME_CATCH, COLUMN_NAME_URL,
      COLUMN_NAME_COUNT_PRAISE, COLUMN_NAME_COUNT_READ
  };
}
