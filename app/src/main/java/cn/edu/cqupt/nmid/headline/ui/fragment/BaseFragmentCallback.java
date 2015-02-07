package cn.edu.cqupt.nmid.headline.ui.fragment;

import cn.edu.cqupt.nmid.headline.ui.adapter.FeedAdapter;

/**
 * Created by leon on 1/28/15.
 */

@Deprecated
public interface BaseFragmentCallback {

  void onGetArgs();

  void onGetPref();

  void onRefresh();

  void onLoadMore();

  void onLoadFromDb();

  FeedAdapter getAdapter();

}
