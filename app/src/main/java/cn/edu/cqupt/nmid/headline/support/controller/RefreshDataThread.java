package cn.edu.cqupt.nmid.headline.support.controller;

import android.os.Handler;

import java.util.LinkedList;

import cn.edu.cqupt.nmid.headline.support.api.headline.bean.NewsBean;


public class RefreshDataThread extends MyThread{

	public RefreshDataThread(Handler handler, LinkedList<NewsBean> tempList,
			int category, int limit, Controller controller) {
		super(handler, tempList, category, limit, controller);
	}
	@Override
	public void run() {
		controller.getRefreshData(tempList, category, limit);
		handler.sendEmptyMessage(2);
	}

}
