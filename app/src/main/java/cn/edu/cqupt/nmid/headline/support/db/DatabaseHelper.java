package cn.edu.cqupt.nmid.headline.support.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leon on 1/20/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "database.db";

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL.CREATE_TABLE_TXTT_NEWS_BASE);
    db.execSQL(SQL.CREATE_TABLE_TXTT_NEWS_INSTITUTE);
    db.execSQL(SQL.CREATE_TABLE_TXTT_NEWS_SCHOOL_FELLOW);
    db.execSQL(SQL.CREATE_TABLE_TXTT_NEWS_SCIENCE);
    db.execSQL(SQL.CREATE_TABLE_TXTT_NEWS_YOUNG);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL.DELETE_TABLE_TXTT_NEWS_BASE);
    db.execSQL(SQL.DELETE_TABLE_TXTT_NEWS_INSTITUTE);
    db.execSQL(SQL.DELETE_TABLE_TXTT_NEWS_SCHOOL_FELLOW);
    db.execSQL(SQL.DELETE_TABLE_TXTT_NEWS_SCIENCE);
    db.execSQL(SQL.DELETE_TABLE_TXTT_NEWS_YOUNG);
    onCreate(db);
  }
}