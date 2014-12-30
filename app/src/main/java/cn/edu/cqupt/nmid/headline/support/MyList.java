//package cn.edu.cqupt.nmid.headline.support;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Handler;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//
//import java.util.LinkedList;
//
//import cn.edu.cqupt.nmid.headline.R;
//import cn.edu.cqupt.nmid.headline.controller.Controller;
//import cn.edu.cqupt.nmid.headline.controller.bean.NewsBean;
//
//
//public class MyList {
//	final int LIMIT = 10;
//	LinkedList<NewsBean> listBeans = null;
//	LinkedList<NewsBean> tempList = null;
//	Controller controller = null;
//	PullToRefreshListView list = null;
//	BaseAdapter adapter = null;
//	int type;
//	Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what == 1) {
//				listBeans.clear();
//				listBeans.addAll(tempList);
//				adapter.notifyDataSetChanged();
//			}
//			if (msg.what == 2) {
//				listBeans.clear();
//				listBeans.addAll(tempList);
//				adapter.notifyDataSetChanged();
//				list.onRefreshComplete();
//			}
//		}
//	};
//
//	public MyList(Context context, int type) {
//		controller = new Controller(context);
//		listBeans = new LinkedList<NewsBean>();
//		tempList = new LinkedList<NewsBean>();
//		list = (PullToRefreshListView) ((Activity) context)
//				.findViewById(R.id.list);
//		adapter = new MyBaseAdapter(listBeans, context);
//		this.type = type;
//		initList();
//	}
//
//	public void initList() {
//		ILoadingLayout startLabels = list.getLoadingLayoutProxy(true,false);
//		ILoadingLayout endLabels = list.getLoadingLayoutProxy(false,true);
//		startLabels.setPullLabel("下拉刷新");
//		startLabels.setRefreshingLabel("加载中");
//		startLabels.setReleaseLabel("释放立即刷新");
//		endLabels.setPullLabel("加载更多");
//		endLabels.setRefreshingLabel("加载中");
//		endLabels.setReleaseLabel("释放立即加载");
//		list.setAdapter(adapter);
//		list.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				controller.RefreshData(handler, tempList, type, LIMIT);
//			}
//
//			@Override
//			public void onPullUpToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				controller.OldData(handler, tempList, type, LIMIT);
//			}
//		});
//		controller.InitData(handler, tempList, Constant.TYPE_COLLEGE, LIMIT);
//	}
//}
