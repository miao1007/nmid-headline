package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.event.ImageUploadEvent;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.image.ImageService;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageInfo;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.ImageStream;
import cn.edu.cqupt.nmid.headline.support.service.UploadService;
import cn.edu.cqupt.nmid.headline.ui.adapter.ImagesFeedAdapter;
import cn.edu.cqupt.nmid.headline.utils.ImageUtils;
import cn.edu.cqupt.nmid.headline.utils.animation.SlideInOutBottomItemAnimator;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.squareup.otto.Subscribe;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import retrofit.Callback;
import retrofit.Response;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.makeLogTag;

public class ImagesFeedFragment extends Fragment {

  String TAG = makeLogTag(ImagesFeedFragment.class);

  Uri outputFileUri;
  private String theLarge;

  /**
   * Injected Vies
   */
  @InjectView(R.id.feed_recyclerview) ObservableRecyclerView mRecyclerview;
  @InjectView(R.id.feed_swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
  //@InjectView(R.id.include_progressbar) RelativeLayout mIncludeProgressbar;
  @InjectView(R.id.stream_multiple_actions) FloatingActionButton mStreamMultipleActions;

  @OnClick(R.id.stream_multiple_actions) void stream_action_take_picture() {
    if (!ShareSDK.getPlatform(getActivity().getApplicationContext(), QZone.NAME).isValid()) {

      new AlertDialog.Builder(getActivity()).setTitle("Login Required")
          .setMessage("请先登录账号，这样才可以上传哦！")
          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {

            }
          })
          .create()
          .show();
    } else {
      startTakePhoto();
    }
  }

  private void startTakePhoto() {
    Intent intent;
    // 判断是否挂载了SD卡
    String savePath = "";
    String storageState = Environment.getExternalStorageState();
    if (storageState.equals(Environment.MEDIA_MOUNTED)) {
      savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/headline/Camera/";
      File savedir = new File(savePath);
      if (!savedir.exists()) {
        savedir.mkdirs();
      }
    }

    // 没有挂载SD卡，无法保存文件
    if (savePath.isEmpty()) {
      RetrofitUtils.disMsg(getActivity(), "无法保存照片，请检查SD卡是否挂载");
      return;
    }

    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    String fileName = "osc_" + timeStamp + ".jpg";// 照片命名
    File out = new File(savePath, fileName);
    Uri uri = Uri.fromFile(out);
    outputFileUri = uri;

    theLarge = savePath + fileName;// 该照片的绝对路径

    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
  }

  ImagesFeedAdapter adapter;
  LinearLayoutManager mLayoutManager;
  ArrayList<ImageInfo> images = new ArrayList<>();
  private int lastid = 0;

  private boolean isLoadingMore = false;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_stream, container, false);
    ButterKnife.inject(this, view);

    //mIncludeProgressbar.setVisibility(View.GONE);
    adapter = new ImagesFeedAdapter(images);
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
    mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItems = mLayoutManager.findLastVisibleItemPosition();
        int lastVisibleItems = adapter.getItemCount();
        if (dy > 0 && visibleItems == lastVisibleItems - 1) {
          loadOldImage();
        }
      }
    });

    loadNewFeeds();
    return view;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  void loadNewFeeds() {
    if (isLoadingMore) {
      Log.d(TAG, "ignore manually update");
      return;
    }
    isLoadingMore = true;
    RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
        .create(ImageService.class)
        .getRefreshImage(0, 4).enqueue(new Callback<ImageStream>() {
      @Override public void onResponse(retrofit.Response<ImageStream> response) {
        isLoadingMore = false;
        mSwipeRefreshLayout.setRefreshing(false);
        images.clear();
        //bug fix when server return null
        if (response.body().getStatus() == 1) {
          images.addAll(response.body().getData());
          adapter.notifyDataSetChanged();
        }
      }

      @Override public void onFailure(Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
        isLoadingMore = false;
      }
    });
  }

  void loadOldImage() {

    if (isLoadingMore) {
      Log.d(TAG, "ignore manually update");
      return;
    }
    isLoadingMore = true;
    lastid = images.get(images.size() - 1).getIdmember();
    RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
        .create(ImageService.class)
        .getROldImage(lastid, 4).enqueue(new Callback<ImageStream>() {
      @Override public void onResponse(retrofit.Response<ImageStream> response) {
        isLoadingMore = false;
        //bug fix when server return null
        if (response.body().getStatus() == 1) {
          images.addAll(response.body().getData());
          adapter.notifyDataSetChanged();
        }
      }

      @Override public void onFailure(Throwable t) {
        isLoadingMore = false;
      }
    });
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) {
      Log.d(TAG, "canceled or other exception!");
      return;
    }

    if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA) {
      Log.d(TAG, "onActivityResult: REQUEST_IMAGE_CAPTURE");
      mRecyclerview.smoothScrollToPosition(0);
      tryUploadFromUri(getActivity(), outputFileUri);
    }
  }

  private void tryUploadFromUri(Context context, Uri mImageUri) {
    Intent intent = new Intent(context.getApplicationContext(), UploadService.class);
    intent.putExtra(UploadService.Key, mImageUri);
    getActivity().startService(intent);
  }

  //called from upload service when uploaded successfully
  @Subscribe public void onNightmode(ImageUploadEvent event) {
    loadNewFeeds();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    GlobalContext.getBus().register(this);
  }

  @Override public void onDetach() {
    super.onDetach();
    GlobalContext.getBus().unregister(this);
  }
}
