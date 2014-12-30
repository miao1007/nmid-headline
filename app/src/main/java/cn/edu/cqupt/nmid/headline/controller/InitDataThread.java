package cn.edu.cqupt.nmid.headline.controller;

import android.os.Handler;

import java.util.LinkedList;

import cn.edu.cqupt.nmid.headline.controller.bean.NewsBean;

public class InitDataThread extends MyThread {

    public InitDataThread(Handler handler, LinkedList<NewsBean> tempList,
                          int category, int limit, Controller controller) {
        super(handler, tempList, category, limit, controller);
    }

    @Override
    public void run() {
        controller.getInitData(tempList, category, limit);
        handler.sendEmptyMessage(1);
    }
}
