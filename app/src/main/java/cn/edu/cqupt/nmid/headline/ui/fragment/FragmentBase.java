package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.support.v7.widget.RecyclerView;
import retrofit.Callback;

/**
 * Created by leon on 5/2/15.
 */
public interface FragmentBase<T> {

  void onInitializeStart();

  Callback<T> onLoadingMoreStart();

  Callback<T> onRefreshingStart();

  RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter();
}
