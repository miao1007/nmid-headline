package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.adapter.FeedAdapter;
import cn.edu.cqupt.nmid.headline.utils.animation.SlideInOutBottomItemAnimator;
import com.getbase.floatingactionbutton.FloatingActionButton;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.makeLogTag;

/**
 * Created by leon on 1/28/15.
 */

@Deprecated
public abstract class BaseFeedFragment extends Fragment implements BaseFragmentCallback {

  String TAG = makeLogTag(BaseFeedFragment.class);

  /**
   * Injected Vies
   */
  @InjectView(R.id.feed_recyclerview) RecyclerView mRecyclerview;
  @InjectView(R.id.feed_swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
  @InjectView(R.id.feed_floating_actionButton) FloatingActionButton mFloatingActionButton;




  @OnClick(R.id.feed_floating_actionButton) void feed_multiple_actions() {
    onRefresh();
  }

  private boolean isLoadingMore = true;
  private LinearLayoutManager mLayoutManager;
  private FeedAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
    onGetArgs();
    onGetPref();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, final ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");
    View view = inflater.inflate(R.layout.fragment_feed, container, false);
    ButterKnife.inject(this, view);
    mRecyclerview.setBackgroundResource(ThemePref.getBackgroundResColor(getActivity()));
    mFloatingActionButton.setColorNormalResId(
        ThemePref.getToolbarBackgroundResColor(getActivity()));
    mFloatingActionButton.setColorPressedResId(ThemePref.getItemBackgroundResColor(getActivity()));
    mFloatingActionButton.setIcon(R.drawable.ic_reload_48dp);

    mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
    adapter = getAdapter();
    mRecyclerview.setAdapter(adapter);
    mRecyclerview.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerview.setLayoutManager(mLayoutManager);
    mRecyclerview.setItemAnimator(new SlideInOutBottomItemAnimator(mRecyclerview));
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {

        onRefresh();
        mRecyclerview.smoothScrollToPosition(0);
      }
    });

    mRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount 表示剩下2个item自动加载
        // dy>0 表示向下滑动
        if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
          if (!isLoadingMore) {
            onLoadMore();
          }
        }
      }
    });
    onLoadFromDb();
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.d(TAG, "onDestroyView");
    ButterKnife.reset(this);
  }
}
