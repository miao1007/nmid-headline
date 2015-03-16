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
import cn.edu.cqupt.nmid.headline.ui.adapter.FeedAdapter;
import cn.edu.cqupt.nmid.headline.utils.animation.SlideInOutBottomItemAnimator;
import com.getbase.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
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
  ArrayList<Feed> newsBeans = new ArrayList<>();
  FeedAdapter adapter;
  int feed_id;
  private String title;
  private int feed_limit = 15;
  private int feed_category = HeadlineService.CATE_ALUMNUS;
  private boolean isFavorite = false;
  private boolean isLoadingMore = false;

  public static FeedFragment newInstance(String title, int type) {
    FeedFragment fragment = new FeedFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TITLE, title);
    args.putInt(ARG_CATEGORY, type);
    fragment.setArguments(args);
    return fragment;
  }

  public static FeedFragment newFavInstance() {
    FeedFragment fragment = new FeedFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_FAV, true);
    fragment.setArguments(args);
    return fragment;
  }

  @OnClick(R.id.feed_floating_actionButton) void feed_multiple_actions() {
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
    feed_limit = HttpPref.getQueryFeedsLimit();
  }

  @Override public View onCreateView(LayoutInflater inflater, final ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");
    View view = inflater.inflate(R.layout.fragment_feed, container, false);
    ButterKnife.inject(this, view);
    //set for night mode
    mRecyclerview.setBackgroundResource(ThemePref.getBackgroundResColor(getActivity()));
    mFloatingActionButton.setColorNormalResId(
        ThemePref.getToolbarBackgroundResColor(getActivity()));
    mFloatingActionButton.setColorPressedResId(ThemePref.getItemBackgroundResColor(getActivity()));

    mFloatingActionButton.setIcon(R.drawable.ic_reload_48dp);

    mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
    adapter = new FeedAdapter(newsBeans);
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
    //Endless RecyclerView
    mRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

    //loadDbFeeds();
    //loadNewFeeds();
    return view;
  }

  void loadNewFeeds() {
    mRecyclerview.smoothScrollToPosition(0);
    if (isFavorite) {
      mSwipeRefreshLayout.setRefreshing(false);
    } else {
      new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
          .setLogLevel(RestAdapter.LogLevel.FULL)
          .build()
          .create(HeadlineService.class)
          .getFreshFeeds(feed_category, 0, feed_limit, new Callback<HeadJson>() {
            @Override public void success(final HeadJson headJson, Response response) {
              //fix NullPointException at samsung SM-G3812
              if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
              }
              if (headJson.getStatus() == HeadlineService.STATUS_ERR) {
                Log.e(TAG, "STATUS_ERR , use LogLevel.FULL for more!");
                return;
              }
              if (headJson.getStatus() == HeadlineService.STATUS_OK) {
                if (newsBeans.isEmpty()) {
                  Log.d(TAG, "newsBeans.isEmpty()");
                  newsBeans.addAll(headJson.getData());
                  adapter.notifyDataSetChanged();
                  cacheToDb(newsBeans);
                  return;
                }
                if (newsBeans.get(0).getIdmember() == headJson.getData().get(0).getIdmember()) {
                  Log.d(TAG, "Same data, Ignore cacheToDb");
                  return;
                }
                newsBeans.clear();
                newsBeans.addAll(headJson.getData());
                adapter.notifyDataSetChanged();
                cacheToDb(newsBeans);
              }
            }

            @Override public void failure(RetrofitError error) {
              if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
              }
            }
          });
    }
  }

  private void cacheToDb(List<Feed> feeds) {
    //try {
    //  for (Feed feed : feeds) {
    //    feed.save();
    //  }
    //  ActiveAndroid.setTransactionSuccessful();
    //} finally {
    //  ActiveAndroid.endTransaction();
    //}
    //for (Feed feed : feeds) {
    //  feed.save();
    //}
  }

  void loadOldNews() {
    isLoadingMore = true;
    feed_id = newsBeans.get(newsBeans.size() - 1).getIdmember();
    new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
        .build()
        .create(HeadlineService.class)
        .getOldFeeds(feed_category, feed_id, feed_limit, new Callback<HeadJson>() {
          @Override public void success(HeadJson headJson, Response response) {
            Log.d(TAG, "loadOldNews id = " + feed_id);
            mSwipeRefreshLayout.setRefreshing(false);
            isLoadingMore = false;
            if (headJson.getStatus() == 1) {
              newsBeans.addAll(headJson.getData());
              adapter.notifyDataSetChanged();
            } else if (headJson.getStatus() == 0) {

            }
          }

          @Override public void failure(RetrofitError error) {

            mSwipeRefreshLayout.setRefreshing(false);
            isLoadingMore = false;
          }
        });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    LOGD(TAG, "onDestroyView");
    ButterKnife.reset(this);
  }
}
