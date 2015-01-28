package cn.edu.cqupt.nmid.headline.ui.fragment;

import cn.edu.cqupt.nmid.headline.ui.adapter.SwipeAdapter;

/**
 * Created by leon on 1/28/15.
 */
public interface BaseFragmentCallback {

  void onGetArgs();

  void onGetPref();

  void onRefresh();

  void onLoadMore();

  void onLoadFromDb();

  SwipeAdapter getAdapter();

}
