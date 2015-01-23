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
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.HeadJson;
import cn.edu.cqupt.nmid.headline.support.pref.HttpPref;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.adapter.SwipeAdapter;
import cn.edu.cqupt.nmid.headline.utils.animation.SlideInOutBottomItemAnimator;
import com.getbase.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.LinkedList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.LOGD;
import static cn.edu.cqupt.nmid.headline.utils.LogUtils.makeLogTag;

/**
 * Created by leon on 14/9/19.
 */

public class FeedFragment extends Fragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_TITLE = "title";
  private static final String ARG_CATEGORY = "slug";
  private static final String ARG_FAV = "favorite";

  String TAG = makeLogTag(FeedFragment.class);
  /**
   * Injected Vies
   */
  @InjectView(R.id.feed_recyclerview) RecyclerView mRecyclerview;
  @InjectView(R.id.feed_swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
  @InjectView(R.id.feed_floating_actionButton) FloatingActionButton mFloatingActionButton;
  /**
   * Data
   */
  LinearLayoutManager mLayoutManager;
  ArrayList<Feed> feeds = new ArrayList<>();
  SwipeAdapter adapter;
  int feed_id;
  private String title;
  private int feed_limit = 15;
  private int feed_cate = HeadlineService.CATE_ALUMNUS;
  private boolean favorite = false;
  private boolean isLoadingMore = false;

  public static FeedFragment newInstance(String title, int type) {
    FeedFragment fragment = new FeedFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TITLE, title);
    args.putInt(ARG_CATEGORY, type);
    fragment.setArguments(args);
    return fragment;
  }

  public static FeedFragment newFavInstance(Boolean isFav) {
    FeedFragment fragment = new FeedFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_FAV, isFav);
    fragment.setArguments(args);
    return fragment;
  }

  @OnClick(R.id.feed_floating_actionButton) void feed_multiple_actions() {
    loadNewFeeds();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
    if (getArguments() != null) {
      title = getArguments().getString(ARG_TITLE);
      feed_cate = getArguments().getInt(ARG_CATEGORY);
      favorite = getArguments().getBoolean(ARG_FAV);

      Log.d(TAG, "getArguments " + feed_cate);
    } else {
      Log.d(TAG, "getArguments == null!");
    }
    feed_limit = HttpPref.getQueryFeedsLimit(getActivity());
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
    mFloatingActionButton.setIcon(R.drawable.ic_share_grey600_36dp);

    mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
    adapter = new SwipeAdapter(getActivity(), feeds);
    mRecyclerview.setAdapter(adapter);
    mRecyclerview.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerview.setLayoutManager(mLayoutManager);
    mRecyclerview.setItemAnimator(new SlideInOutBottomItemAnimator(mRecyclerview));
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {

        loadNewFeeds();
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
            loadOldNews();
          }
        }
      }
    });
    return view;
  }

  LinkedList<Feed> getMockData() {
    LinkedList<Feed> feedsToAdd = new LinkedList<>();
    Feed feed = new Feed();
    feed.setId(11);
    feed.setCategory(1);
    feed.setImage1("http://www.baidu.com/img/bdlogo.png");
    feed.setImage2("http://www.baidu.com/img/bdlogo.png");
    feed.setImage3("http://www.baidu.com/img/bdlogo.png");
    feed.setSimple_content("simple content");
    feed.setTitle(adapter.getItemCount() + "");
    feed.setTime_release("2015");
    for (int i = 0; i < 10; i++) {
      feedsToAdd.add(feed);
    }
    return feedsToAdd;
  }

  void loadNewFeeds() {
    mRecyclerview.smoothScrollToPosition(0);
    new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.BASIC)
        .setEndpoint(HeadlineService.END_POINT)
        .build()
        .create(HeadlineService.class)
        .getFreshFeeds(feed_cate, 0, feed_limit, new Callback<HeadJson>() {
          @Override public void success(HeadJson headJson, Response response) {
            mSwipeRefreshLayout.setRefreshing(false);
            feeds.clear();

            //TODO Tell the server !!!
            for (Feed feed : headJson.getData()) {
              feeds.add(0, feed);
            }

            adapter.notifyDataSetChanged();

            Log.d(TAG, "Last id is" + feeds.get(0).getId());
          }

          @Override public void failure(RetrofitError error) {
            mSwipeRefreshLayout.setRefreshing(false);
          }
        });
  }

  void loadOldNews() {
    isLoadingMore = true;
    feed_id = feeds.get(feeds.size() - 1).getId();

    new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
        .setEndpoint(HeadlineService.END_POINT)
        .build()
        .create(HeadlineService.class)
        .getOldFeeds(feed_cate, feed_id, feed_limit, new Callback<HeadJson>() {
          @Override public void success(HeadJson headJson, Response response) {
            Log.d(TAG, "loadOldNews id = " + feed_id);
            mSwipeRefreshLayout.setRefreshing(false);
            isLoadingMore = false;
            if (headJson.getStatus() == 1) {
              feeds.addAll(headJson.getData());
              adapter.notifyDataSetChanged();
            } else {
              //TODO remove footer

            }
          }

          @Override public void failure(RetrofitError error) {

            mSwipeRefreshLayout.setRefreshing(false);
            isLoadingMore = false;
          }
        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    LOGD(TAG, "onDestroyView");
    ButterKnife.reset(this);
  }
}
