package cn.edu.cqupt.nmid.headline.ui.fragment.daimajia;

import java.util.List;

/**
 * Created by leon on 5/2/15.
 */
public interface UIRespondent<T> {
  void onEnd();

  void onInitializeDone(Exception exception, List<T> list);

  void onInitializeStart();

  void onLoadMoreDone(Exception exception, List<T> list);

  void onLoadingMoreStart();

  void onRefreshDone(Exception exception, List<T> list);

  void onRefreshingStart();
}