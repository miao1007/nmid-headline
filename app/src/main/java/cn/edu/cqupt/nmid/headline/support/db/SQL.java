package cn.edu.cqupt.nmid.headline.support.db;

import cn.edu.cqupt.nmid.headline.support.db.table.TxttNewsBaseTable;
import cn.edu.cqupt.nmid.headline.support.db.table.TxttNewsInstituteTable;
import cn.edu.cqupt.nmid.headline.support.db.table.TxttNewsSchoolFellowTable;
import cn.edu.cqupt.nmid.headline.support.db.table.TxttNewsScienceTable;
import cn.edu.cqupt.nmid.headline.support.db.table.TxttNewsYoungTable;

/**
 * Created by leon on 1/20/15.
 */
public class SQL {

  public static final String DATA_TYPE_DATE = "DATA";
  public static final String DATA_TYPE_TEXT = " TEXT";
  public static final String DATA_TYPE_INT = " INTEGER";
  public static final String DATA_TYPE_VCHAR_255 = " varchar(255)";
  public static final String DATA_TYPE_TINY_INT_1 = " tinyint(1)";
  public static final String DATA_TYPE_INT_11 = " int(11)";

  public static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT =
      " INTEGER PRIMARY KEY AUTOINCREMENT";
  public static final String COMMA_SEP = ", ";

  public static final String CREATE_TABLE_TXTT_NEWS_BASE =
      "CREATE TABLE " + TxttNewsBaseTable.TABLE_NAME + " ("

          + TxttNewsBaseTable.COLUMNS[0] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[1] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[2] + DATA_TYPE_TINY_INT_1 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[3] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[4] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[5] + DATA_TYPE_TEXT + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[6] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[7] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[8] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[9] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[10] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[11] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[12] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[13] + DATA_TYPE_INT_11 + COMMA_SEP

          + ")";

  public static final String CREATE_TABLE_TXTT_NEWS_INSTITUTE =
      "CREATE TABLE " + TxttNewsInstituteTable.TABLE_NAME + " ("

          + TxttNewsBaseTable.COLUMNS[0] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[1] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[2] + DATA_TYPE_TINY_INT_1 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[3] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[4] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[5] + DATA_TYPE_TEXT + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[6] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[7] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[8] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[9] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[10] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[11] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[12] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[13] + DATA_TYPE_INT_11 + COMMA_SEP

          + ")";

  public static final String CREATE_TABLE_TXTT_NEWS_SCHOOL_FELLOW =
      "CREATE TABLE " + TxttNewsSchoolFellowTable.TABLE_NAME + " ("

          + TxttNewsBaseTable.COLUMNS[0] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[1] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[2] + DATA_TYPE_TINY_INT_1 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[3] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[4] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[5] + DATA_TYPE_TEXT + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[6] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[7] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[8] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[9] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[10] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[11] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[12] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[13] + DATA_TYPE_INT_11 + COMMA_SEP

          + ")";

  public static final String CREATE_TABLE_TXTT_NEWS_YOUNG =
      "CREATE TABLE " + TxttNewsYoungTable.TABLE_NAME + " ("

          + TxttNewsBaseTable.COLUMNS[0] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[1] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[2] + DATA_TYPE_TINY_INT_1 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[3] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[4] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[5] + DATA_TYPE_TEXT + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[6] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[7] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[8] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[9] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[10] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[11] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[12] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[13] + DATA_TYPE_INT_11 + COMMA_SEP

          + ")";

  public static final String CREATE_TABLE_TXTT_NEWS_SCIENCE =
      "CREATE TABLE " + TxttNewsScienceTable.TABLE_NAME + " ("

          + TxttNewsBaseTable.COLUMNS[0] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[1] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[2] + DATA_TYPE_TINY_INT_1 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[3] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[4] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[5] + DATA_TYPE_TEXT + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[6] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[7] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[8] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[9] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[10] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[11] + DATA_TYPE_VCHAR_255 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[12] + DATA_TYPE_INT_11 + COMMA_SEP

          + TxttNewsBaseTable.COLUMNS[13] + DATA_TYPE_INT_11 + COMMA_SEP

          + ")";

  public static final String DELETE_TABLE_TXTT_NEWS_BASE =
      "DROP TABLE IF EXISTS " + TxttNewsBaseTable.TABLE_NAME;

  public static final String DELETE_TABLE_TXTT_NEWS_INSTITUTE =
      "DROP TABLE IF EXISTS " + TxttNewsInstituteTable.TABLE_NAME;

  public static final String DELETE_TABLE_TXTT_NEWS_SCHOOL_FELLOW =
      "DROP TABLE IF EXISTS " + TxttNewsSchoolFellowTable.TABLE_NAME;

  public static final String DELETE_TABLE_TXTT_NEWS_YOUNG =
      "DROP TABLE IF EXISTS " + TxttNewsYoungTable.TABLE_NAME;

  public static final String DELETE_TABLE_TXTT_NEWS_SCIENCE =
      "DROP TABLE IF EXISTS " + TxttNewsScienceTable.TABLE_NAME;
}
