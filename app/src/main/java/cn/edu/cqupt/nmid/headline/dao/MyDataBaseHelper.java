package cn.edu.cqupt.nmid.headline.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.edu.cqupt.nmid.headline.controller.bean.NewsBean;


public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static MyDataBaseHelper baseHelper;
    final String createCollege = "create table college(_id integer primary key ,category integer not null,title text not null,simple_content text not null,content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null)";
    final String createScientific = "create table scientific(_id integer primary key ,category integer not null,title text not null,simple_content text not null,content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null)";
    final String createYouth = "create table youth(_id integer primary key ,category integer not null,title text not null,simple_content text not null,content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null)";
    final String createClassmate = "create table classmate(_id integer primary key ,category integer not null,title text not null,simple_content text not null,content text not null,image1 text not null,image2 text not null,image3 text not null,timeRelease text not null)";

    private MyDataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);

    }

    public synchronized static MyDataBaseHelper getInstance(Context context, String name, int version) {
        if (baseHelper == null) {
            baseHelper = new MyDataBaseHelper(context, name, version);
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

    /**
     * 插入数据
     *
     * @param news
     * @param type 分类
     */
    public synchronized void insertData(NewsBean news, String type) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("insert into " + type + " values(?,?,?,?,?,?,?,?,?)", new String[]{
                String.valueOf(news.get_id()), String.valueOf(news.getCategory()), news.getTitle(), news.getSimpleContent(), news.getContent(), news.getImage1(), news.getImage2(), news.getImage3(), news.getTimeRelease()});
        db.close();
    }

    /**
     * 查询数据
     *
     * @param sql
     * @return 结果集
     */
    public synchronized Cursor getData(String sql) {
        return getReadableDatabase().rawQuery(sql, null);
    }

    /**
     * 根据type删除表数据
     *
     * @param type
     */
    public synchronized void deleteData(String type) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(type, null, null);
        db.close();
    }
}
