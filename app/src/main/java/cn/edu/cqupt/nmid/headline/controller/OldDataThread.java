package cn.edu.cqupt.nmid.headline.controller;

import android.os.Handler;

import java.util.LinkedList;

import cn.edu.cqupt.nmid.headline.controller.bean.NewsBean;

public class OldDataThread extends MyThread{
	public OldDataThread(Handler handler, LinkedList<NewsBean> tempList,
			int category, int limit, Controller controller) {
		super(handler, tempList, category, limit, controller);
	}
	@Override
	public void run() {
				controller.getOldData(tempList, category, limit);
				handler.sendEmptyMessage(2);
	}
}
