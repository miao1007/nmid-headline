package cn.edu.cqupt.nmid.headline.support.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "headline.db";
  private static volatile DatabaseHelper singleton = null;

  private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
      int version) {
    super(context, name, factory, version);
  }

  //double-checked locking
  //useful links at http://devbean.blog.51cto.com/448512/203501/
  public static DatabaseHelper getInstance() {
    if (singleton == null) {
      synchronized (DatabaseHelper.class) {
        if (singleton == null) {
          singleton = new DatabaseHelper(GlobalContext.getInstance(), DATABASE_NAME, null,
              DATABASE_VERSION);
        }
      }
    }
    return singleton;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL.CREATE_TABLE_BASE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL.DELETE_TABLE_BASE);
    onCreate(db);
  }
}
