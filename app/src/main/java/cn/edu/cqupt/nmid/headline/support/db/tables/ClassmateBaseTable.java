package cn.edu.cqupt.nmid.headline.support.db.tables;

public class ClassmateBaseTable extends BaseTable {//校友新闻表
	public static final String TABLE_NAME="classmate";
	
	public static final String[] COLUMN={
		COLUMN_NAME_ID,
		COLUMN_NAME_CATEGORY,
		COLUMN_NAME_TITLE,
		COLUMN_NAME_SIMPLE_CONTENT,
		COLUMN_NAME_IMAGE1,
		COLUMN_NAME_IMAGE2,
		COLUMN_NAME_IMAGE3,
		COLUMN_NAME_TIME_RELEASE,
		COLUMN_NAME_ISCOLLECT
	};
}
