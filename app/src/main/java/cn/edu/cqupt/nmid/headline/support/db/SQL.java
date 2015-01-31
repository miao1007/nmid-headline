package cn.edu.cqupt.nmid.headline.support.db;

import cn.edu.cqupt.nmid.headline.support.db.tables.BaseTable;

public class SQL {
  public static final String TEXT_TYPE = " TEXT";
  public static final String INTEGER_TYPE = " INTEGER";
  public static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT =
      " INTEGER PRIMARY KEY AUTOINCREMENT";
  public static final String COMMA_SEP = ", ";
  //TODO

  //base
  public static final String CREATE_TABLE_BASE = "CREATE TABLE "
      + BaseTable.TABLE_NAME
      + " ("
      + BaseTable.COLUMN_NAME_ID
      + INTEGER_PRIMARY_KEY_AUTOINCREMENT
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_CATEGORY
      + INTEGER_TYPE
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_TITLE
      + TEXT_TYPE
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_SIMPLE_CONTENT
      + TEXT_TYPE
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_IMAGE1
      + TEXT_TYPE
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_IMAGE2
      + TEXT_TYPE
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_IMAGE3
      + TEXT_TYPE
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_TIME_RELEASE
      + TEXT_TYPE
      + COMMA_SEP
      + BaseTable.COLUMN_NAME_ISCOLLECT
      + INTEGER_TYPE
      + ")";
  public static final String DELETE_TABLE_BASE = "DROP TABLE IF EXISTS " + BaseTable.TABLE_NAME;
}
