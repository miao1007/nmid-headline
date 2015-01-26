package cn.edu.cqupt.nmid.headline.support.db;

import cn.edu.cqupt.nmid.headline.support.db.tables.ClassmateBaseTable;
import cn.edu.cqupt.nmid.headline.support.db.tables.CollegeBaseTable;
import cn.edu.cqupt.nmid.headline.support.db.tables.ScientificBaseTable;
import cn.edu.cqupt.nmid.headline.support.db.tables.YouthBaseTable;

public class SQL {
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String COMMA_SEP = ", ";
    //学院新闻表
    public static final String CREATE_TABLE_COLLEGE = "CREATE TABLE "
            + CollegeBaseTable.TABLE_NAME + " ("
            + CollegeBaseTable.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_CATEGORY + INTEGER_TYPE + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_SIMPLE_CONTENT + TEXT_TYPE + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_IMAGE1 + TEXT_TYPE + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_IMAGE2 + TEXT_TYPE + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_IMAGE3 + TEXT_TYPE + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_TIME_RELEASE + TEXT_TYPE + COMMA_SEP
            + CollegeBaseTable.COLUMN_NAME_ISCOLLECT + INTEGER_TYPE + ")";
    public static final String DELETE_TABLE_COLLEGE = "DROP TABLE IF EXISTS "
            + CollegeBaseTable.TABLE_NAME;
    
    //科研动态表
    public static final String CREATE_TABLE_SCIENTIFIC = "CREATE TABLE "
            + ScientificBaseTable.TABLE_NAME + " ("
            + ScientificBaseTable.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_CATEGORY + INTEGER_TYPE + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_SIMPLE_CONTENT + TEXT_TYPE + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_IMAGE1 + TEXT_TYPE + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_IMAGE2 + TEXT_TYPE + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_IMAGE3 + TEXT_TYPE + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_TIME_RELEASE + TEXT_TYPE + COMMA_SEP
            + ScientificBaseTable.COLUMN_NAME_ISCOLLECT + INTEGER_TYPE + ")";
    public static final String DELETE_TABLE_SCIENTIFIC = "DROP TABLE IF EXISTS "
            + ScientificBaseTable.TABLE_NAME;
    
    //青春通信表
    public static final String CREATE_TABLE_YOUTH = "CREATE TABLE "
            + YouthBaseTable.TABLE_NAME + " ("
            + YouthBaseTable.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_CATEGORY + INTEGER_TYPE + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_SIMPLE_CONTENT + TEXT_TYPE + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_IMAGE1 + TEXT_TYPE + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_IMAGE2 + TEXT_TYPE + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_IMAGE3 + TEXT_TYPE + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_TIME_RELEASE + TEXT_TYPE + COMMA_SEP
            + YouthBaseTable.COLUMN_NAME_ISCOLLECT + INTEGER_TYPE + ")";
    public static final String DELETE_TABLE_YOUTH = "DROP TABLE IF EXISTS "
            + YouthBaseTable.TABLE_NAME;
    
    //校友新闻
    public static final String CREATE_TABLE_CLASSMATE = "CREATE TABLE "
            + ClassmateBaseTable.TABLE_NAME + " ("
            + ClassmateBaseTable.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_CATEGORY + INTEGER_TYPE + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_SIMPLE_CONTENT + TEXT_TYPE + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_IMAGE1 + TEXT_TYPE + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_IMAGE2 + TEXT_TYPE + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_IMAGE3 + TEXT_TYPE + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_TIME_RELEASE + TEXT_TYPE + COMMA_SEP
            + ClassmateBaseTable.COLUMN_NAME_ISCOLLECT + INTEGER_TYPE + ")";
    public static final String DELETE_TABLE_CLASSMATE = "DROP TABLE IF EXISTS "
            + ClassmateBaseTable.TABLE_NAME;
}
