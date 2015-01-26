package cn.edu.cqupt.nmid.headline.support.db;

public interface Constant {
	int TYPE_COLLEGE = 1;//学院风采分类
	int TYPE_SCIENTIFIC=2;//科研动态分类
	int TYPE_YOUTH=3;//青春通信分类
	int TYPE_CLASSMATE=4;//校友风采分类
	String[] TYPE={ "", "college", "scientific", "youth","classmate" };//分类数组
	String API_URL="http://113.251.223.241";//服务器地址
}
