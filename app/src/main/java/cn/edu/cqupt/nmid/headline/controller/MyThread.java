package cn.edu.cqupt.nmid.headline.controller;

import android.os.Handler;

import java.util.LinkedList;

import cn.edu.cqupt.nmid.headline.controller.bean.NewsBean;

public abstract class MyThread extends Thread {
	Handler handler;
	LinkedList<NewsBean> tempList;
	int category;
	int limit;
	Controller controller;
	public MyThread(){};
	public MyThread(Handler handler, LinkedList<NewsBean> tempList,
			int category, int limit, Controller controller) {
		this.handler = handler;
		this.tempList = tempList;
		this.category = category;
		this.limit = limit;
		this.controller = controller;
	}
}
