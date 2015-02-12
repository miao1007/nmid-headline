package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import cn.edu.cqupt.nmid.headline.support.api.stream.UploadImageService;
import cn.edu.cqupt.nmid.headline.support.api.stream.bean.Datum;
import cn.edu.cqupt.nmid.headline.support.api.stream.bean.ImageStream;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.activity.UploadActivity;
import cn.edu.cqupt.nmid.headline.ui.adapter.StreamAdapter;
import cn.edu.cqupt.nmid.headline.ui.widget.ProgressBarCircular;
import cn.edu.cqupt.nmid.headline.utils.animation.SlideInOutBottomItemAnimator;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.makeLogTag;

public class StreamFragment extends Fragment {

  String TAG = makeLogTag(StreamFragment.class);
  /**
   * Injected Vies
   */
  @InjectView(R.id.feed_recyclerview) ObservableRecyclerView mRecyclerview;
  @InjectView(R.id.feed_swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
  @InjectView(R.id.include_progressbar) ProgressBarCircular mIncludeProgressbar;
  @InjectView(R.id.stream_multiple_actions) FloatingActionButton mStreamMultipleActions;

  @OnClick(R.id.stream_multiple_actions) void stream_action_take_picture() {
    Intent intent = new Intent(getActivity(), UploadActivity.class);
    //    startActivityForResult(intent,REQUEST_TAKE_PICTURE);
    startActivityForResult(intent, 3);
  }

  final int REQUEST_TAKE_PICTURE = 1;
  final int REQUEST_SELECT_IMAGE = 2;

  private Uri mImageUri;

  StreamAdapter adapter;
  LinearLayoutManager mLayoutManager;
  ArrayList<Datum> images = new ArrayList<>();
  private int lastid = 0;

  private boolean isLoadingMore = false;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_stream, container, false);
    ButterKnife.inject(this, view);

    mIncludeProgressbar.setVisibility(View.GONE);
    adapter = new StreamAdapter(getActivity(), images);
    mLayoutManager = new LinearLayoutManager(getActivity());

    mRecyclerview.setBackgroundResource(ThemePref.getBackgroundResColor(getActivity()));
    mStreamMultipleActions.setColorNormalResId(
        ThemePref.getToolbarBackgroundResColor(getActivity()));
    mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
    mRecyclerview.setAdapter(adapter);
    mRecyclerview.setHasFixedSize(true);
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
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItems = mLayoutManager.findLastVisibleItemPosition();
        if (dy > 0 && visibleItems == adapter.getItemCount() - 1 && !isLoadingMore) {
          mSwipeRefreshLayout.setRefreshing(true);
          loadOldImage();
          Log.d(TAG, "loadNewFeeds");
        }
      }
    });

    loadNewFeeds();
    return view;
  }

  void loadNewFeeds() {
    if (isLoadingMore) {
      Log.d(TAG, "ignore manually update");
    } else {
      new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
          .setLogLevel(RestAdapter.LogLevel.BASIC)
          .build()
          .create(UploadImageService.class)
          .getRefreshImage(0, 15, new Callback<ImageStream>() {
            @Override public void success(ImageStream imageStream, Response response) {
              isLoadingMore = false;
              mSwipeRefreshLayout.setRefreshing(false);
              images.clear();
              //bug fix when server return null
              if (imageStream.getStatus() == 1) {
                images.addAll(imageStream.getData());
                adapter.notifyDataSetChanged();
              }
            }

            @Override public void failure(RetrofitError error) {
              mSwipeRefreshLayout.setRefreshing(false);
              isLoadingMore = false;
            }
          });
    }
  }

  void loadOldImage() {
    if (isLoadingMore) {
      Log.d(TAG, "ignore manually update");
    } else {
      lastid = images.size() - 1;
      new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
          .setLogLevel(RestAdapter.LogLevel.BASIC)
          .build()
          .create(UploadImageService.class)
          .getROldImage(lastid, 15, new Callback<ImageStream>() {
            @Override public void success(ImageStream imageStream, Response response) {
              isLoadingMore = false;
              mSwipeRefreshLayout.setRefreshing(false);
              //bug fix when server return null
              if (imageStream.getStatus() == 1) {
                images.addAll(imageStream.getData());
                adapter.notifyDataSetChanged();
              }
            }

            @Override public void failure(RetrofitError error) {
              mSwipeRefreshLayout.setRefreshing(false);
              isLoadingMore = false;
            }
          });
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == 3) {
      loadNewFeeds();
    }
  }
}
