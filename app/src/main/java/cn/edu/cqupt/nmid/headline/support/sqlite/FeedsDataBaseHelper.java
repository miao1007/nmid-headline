package cn.edu.cqupt.nmid.headline.support.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.edu.cqupt.nmid.headline.support.api.headline.bean.NewsBean;


public class FeedsDataBaseHelper extends SQLiteOpenHelper {
    private static FeedsDataBaseHelper baseHelper;
    final String createCollege = "create table college(_id integer primary key ,category integer not null,title text not null,simple_content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null,iscollect integer not null)";
    final String createScientific = "create table scientific(_id integer primary key ,category integer not null,title text not null,simple_content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null,iscollect integer not null)";
    final String createYouth = "create table youth(_id integer primary key ,category integer not null,title text not null,simple_content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null,iscollect integer not null)";
    final String createClassmate = "create table classmate(_id integer primary key ,category integer not null,title text not null,simple_content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null,iscollect integer not null)";

    private FeedsDataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);

    }

    public synchronized static FeedsDataBaseHelper getInstance(Context context, String name, int version) {
        if (baseHelper == null) {
            baseHelper = new FeedsDataBaseHelper(context, name, version);
        }
        return baseHelper;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCollege);
        db.execSQL(createScientific);
        db.execSQL(createYouth);
        db.execSQL(createClassmate);
    }


    public synchronized void insertData(NewsBean news, String type) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("insert into " + type + " values(?,?,?,?,?,?,?,?,?)", new String[]{
                String.valueOf(news.get_id()), String.valueOf(news.getCategory()), news.getTitle(), news.getSimpleContent(), news.getImage1(), news.getImage2(), news.getImage3(), news.getTimeRelease(), String.valueOf(news.getIsCollect())});
        db.close();
    }

    public synchronized Cursor getData(String sql) {
        return getReadableDatabase().rawQuery(sql, null);
    }

    public synchronized void deleteData(String type) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(type, null, null);
        db.close();
    }

    public synchronized void upData(int id, String category, int iscollect) {
        ContentValues values = new ContentValues();
        values.put("iscollect", iscollect);
        SQLiteDatabase db = getWritableDatabase();
        db.update(category, values, "_id=?", new String[]{String.valueOf(id)});
    }
}
