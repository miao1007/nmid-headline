package cn.edu.cqupt.nmid.headline.ui.fragment.base;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.event.NightModeEvent;
import cn.edu.cqupt.nmid.headline.support.pref.HttpPref;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.ui.adapter.NewsFeedAdapter;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.Response;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.LOGD;
import static cn.edu.cqupt.nmid.headline.utils.LogUtils.makeLogTag;

/**
 * Created by leon on 14/9/19.
 */

public class NewsFeedFragment extends Fragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_TITLE = "title";
  private static final String ARG_CATEGORY = "slug";
  private static final String ARG_FAV = "favorite";

  String TAG = makeLogTag(NewsFeedFragment.class);
  /**
   * Injected Vies
   */
  @InjectView(R.id.feed_recyclerview) RecyclerView mRecyclerview;
  @InjectView(R.id.feed_swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
  @InjectView(R.id.feed_floating_actionButton) FloatingActionButton mFloatingActionButton;

  @InjectView(R.id.loading_tips) TextView mTvloading_tips;
  @InjectView(R.id.loading_refresh) Button mBtnloading_refresh;
  /**
   * Data
   */
  LinearLayoutManager mLayoutManager;
  ArrayList<Feed> newsBeans = new ArrayList<>();
  NewsFeedAdapter adapter;
  int feed_id;
  private String title;
  protected int feed_limit = 15;
  private int feed_category = HeadlineService.CATE_ALUMNUS;
  private boolean isFavorite = false;
  private boolean isLoadingMore = false;

  public static NewsFeedFragment newInstance(String title, int type) {
    NewsFeedFragment fragment = new NewsFeedFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TITLE, title);
    args.putInt(ARG_CATEGORY, type);
    fragment.setArguments(args);
    return fragment;
  }

  public static NewsFeedFragment newFavInstance() {
    NewsFeedFragment fragment = new NewsFeedFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_FAV, true);
    fragment.setArguments(args);
    return fragment;
  }

  @OnClick(R.id.feed_floating_actionButton) void feed_multiple_actions() {
    loadNewFeeds();
  }

  @OnClick(R.id.loading_refresh) void loading_refresh() {
    loadNewFeeds();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
    getArgsAndPrefs();
  }

  private void getArgsAndPrefs() {
    if (getArguments() != null) {
      title = getArguments().getString(ARG_TITLE);
      feed_category = getArguments().getInt(ARG_CATEGORY);
      isFavorite = getArguments().getBoolean(ARG_FAV);
    } else {
      Log.e(TAG, "getArguments == null!");
    }
    feed_limit = HttpPref.getQueryFeedsLimit(getActivity());
  }

  @Override public View onCreateView(LayoutInflater inflater, final ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");
    View view = inflater.inflate(R.layout.fragment_feed, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    Log.d(TAG, "onViewCreated");
    super.onViewCreated(view, savedInstanceState);
    //set for night mode
    mRecyclerview.setBackgroundResource(ThemePref.getBackgroundResColor(getActivity()));
    mFloatingActionButton.setColorNormalResId(
        ThemePref.getToolbarBackgroundResColor(getActivity()));
    mFloatingActionButton.setColorPressedResId(ThemePref.getItemBackgroundResColor(getActivity()));

    mFloatingActionButton.setIcon(R.drawable.ic_reload_48dp);

    mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
    adapter = new NewsFeedAdapter(newsBeans);
    mRecyclerview.setAdapter(adapter);
    mRecyclerview.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerview.setLayoutManager(mLayoutManager);
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {

        loadNewFeeds();
      }
    });
    //Endless RecyclerView
    mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount 表示剩下2个item自动加载
        // dy>0 表示向下滑动
        if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
          if (!isLoadingMore) {
            loadOldNews();
          }
        }
      }
    });

    loadDbFeeds();
  }

  void loadNewFeeds() {
    mRecyclerview.smoothScrollToPosition(0);
    RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
        .create(HeadlineService.class)
        .getFreshFeeds(feed_category, 0, feed_limit).enqueue(new Callback<HeadJson>() {
      @Override public void onResponse(Response<HeadJson> response) {
        dispatchSuccess(response.body(), true);
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }

  //private void cacheToDb(List<Feed> feeds) {
  //  //如果你同时只操作一个数据库的话，就用被注释掉的方法，反之用下面的
  //  //try {
  //  //  for (Feed feed : feeds) {
  //  //    feed.save();
  //  //  }
  //  //  ActiveAndroid.setTransactionSuccessful();
  //  //} finally {
  //  //  ActiveAndroid.endTransaction();
  //  //}
  //  for (Feed feed : feeds) {
  //    feed.save();
  //  }
  //}

  void loadOldNews() {
    isLoadingMore = true;
    feed_id = newsBeans.get(newsBeans.size() - 1).getIdmember();
    RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
        .create(HeadlineService.class)
        .getOldFeeds(feed_category, feed_id, feed_limit).enqueue(new Callback<HeadJson>() {
      @Override public void onResponse(Response<HeadJson> response) {
        dispatchSuccess(response.body(), false);
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }

  private void dispatchSuccess(HeadJson headJson, boolean isClear) {

    if (mSwipeRefreshLayout != null) {
      mSwipeRefreshLayout.setRefreshing(false);
    }
    showErrorView(View.GONE);

    if (headJson.getStatus() == HeadlineService.STATUS_ERR) {
      Log.e(TAG, "STATUS_ERR , use LogLevel.FULL for more!");
      return;
    }
    if (headJson.getStatus() == HeadlineService.STATUS_OK) {
      if (newsBeans.isEmpty()) {
        Log.d(TAG, "newsBeans.isEmpty()");
        newsBeans.addAll(headJson.getData());
        adapter.notifyDataSetChanged();
        //cacheToDb(newsBeans);
        return;
      }
      if (newsBeans.get(0).getIdmember() == headJson.getData().get(0).getIdmember()) {
        Log.d(TAG, "Same data, Ignore cacheToDb");
        return;
      }
      if (isClear) {
        newsBeans.clear();
      }
      newsBeans.addAll(headJson.getData());
      adapter.notifyDataSetChanged();
    }
  }

  private void dispatchError() {
    if (mSwipeRefreshLayout != null) {
      mSwipeRefreshLayout.setRefreshing(false);
    }
    newsBeans.clear();
    adapter.notifyDataSetChanged();
    isLoadingMore = false;
    showErrorView(View.VISIBLE);
  }

  void loadDbFeeds() {
    //List<Feed> feeds;
    //feeds = new Select().from(Feed.class)
    //    .where("category = ?", Integer.valueOf(this.feed_category))
    //    .orderBy("idMember desc")
    //    .limit(this.feed_limit)
    //    .execute();
    //if (feeds.isEmpty()) {
    //  //TODO
    //  loadNewFeeds();
    //} else {
    //  newsBeans.addAll(feeds);
    //  adapter.notifyDataSetChanged();
    //  mSwipeRefreshLayout.setRefreshing(false);
    //}
    loadNewFeeds();
  }

  void showErrorView(int isVisible) {
    if (mBtnloading_refresh != null && mTvloading_tips != null) {
      mBtnloading_refresh.setVisibility(isVisible);
      mTvloading_tips.setVisibility(isVisible);
    }
  }

  @Subscribe public void onNightmode(NightModeEvent event) {
    Log.d(TAG, "isNightMode" + event.isNightMode);
    //if (mRecyclerview == null) {
    //  Log.d(TAG, "onNightmode mRecyclerview is null!");
    //}
    //mFloatingActionButton.setColorNormalResId(ThemePref.getBackgroundResColor(event.isNightMode));
    //mFloatingActionButton.setColorPressedResId(
    //    ThemePref.getItemBackgroundResColor(event.isNightMode));
    onDetach();
    onAttach(getActivity());
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);

    GlobalContext.getBus().register(this);
  }

  @Override public void onDetach() {
    super.onDetach();
    GlobalContext.getBus().unregister(this);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    LOGD(TAG, "onDestroyView");
    ButterKnife.reset(this);
  }
}
