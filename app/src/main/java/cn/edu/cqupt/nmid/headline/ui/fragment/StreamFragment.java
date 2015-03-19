package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.image.ImageService;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageInfo;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageStream;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.UploadResult;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.adapter.StreamAdapter;
import cn.edu.cqupt.nmid.headline.ui.widget.ProgressBarCircular;
import cn.edu.cqupt.nmid.headline.utils.BitmapUtils;
import cn.edu.cqupt.nmid.headline.utils.FileUtils;
import cn.edu.cqupt.nmid.headline.utils.animation.SlideInOutBottomItemAnimator;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.makeLogTag;

public class StreamFragment extends Fragment {

  String TAG = makeLogTag(StreamFragment.class);
  public final static int REQUEST_IMAGE_CAPTURE = 1;
  Uri outputFileUri;
  MaterialDialog dialog;

  /**
   * Injected Vies
   */
  @InjectView(R.id.feed_recyclerview) ObservableRecyclerView mRecyclerview;
  @InjectView(R.id.feed_swiperefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
  @InjectView(R.id.include_progressbar) ProgressBarCircular mIncludeProgressbar;
  @InjectView(R.id.stream_multiple_actions) FloatingActionButton mStreamMultipleActions;

  @OnClick(R.id.stream_multiple_actions) void stream_action_take_picture() {
    if (!ShareSDK.getPlatform(getActivity().getApplicationContext(), QZone.NAME).isValid()) {
      dialog = new MaterialDialog(getActivity()).setTitle("Login Reqired")
          .setMessage("请先登录账号，这样才可以上传哦！")
          .setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override public void onClick(View v) {
              dialog.dismiss();
            }
          })
          .setPositiveButton("OK", new View.OnClickListener() {
            @Override public void onClick(View v) {
              dialog.dismiss();
            }
          });
      dialog.show();
    } else {
      tryUploadImage();
    }
  }

  private void tryUploadImage() {
    File file = FileUtils.createImageFile();
    outputFileUri = Uri.fromFile(file);
    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
    startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE);
  }

  StreamAdapter adapter;
  LinearLayoutManager mLayoutManager;
  ArrayList<ImageInfo> images = new ArrayList<>();
  private int lastid = 0;

  private boolean isLoadingMore = false;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_stream, container, false);
    ButterKnife.inject(this, view);

    mIncludeProgressbar.setVisibility(View.GONE);
    adapter = new StreamAdapter(images);
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
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItems = mLayoutManager.findLastVisibleItemPosition();
        if (dy > 0 && visibleItems == adapter.getItemCount() - 3 && !isLoadingMore) {
          mSwipeRefreshLayout.setRefreshing(true);
          loadOldImage();
          Log.d(TAG, "loadNewFeeds");
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
    } else {
      new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
          .setLogLevel(RestAdapter.LogLevel.BASIC)
          .build()
          .create(ImageService.class)
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
          .create(ImageService.class)
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
    if (resultCode != Activity.RESULT_OK) {
      Log.d(TAG, "canceled or other exception!");
      return;
    }

    if (requestCode == REQUEST_IMAGE_CAPTURE) {
      Log.d(TAG, "onActivityResult: REQUEST_IMAGE_CAPTURE");
      mRecyclerview.smoothScrollToPosition(0);
      tryUploadFrimUri(getActivity(), outputFileUri);
    }
  }

  private void tryUploadFrimUri(Context context, Uri mImageUri) {

    final Bitmap bmp;
    try {

      bmp = BitmapUtils.getThumbnail(context, mImageUri, 300);


        File file = saveBitmapToFile(bmp, mImageUri);
        if (file == null) new File(mImageUri.getPath());

      if (mImageUri != null) {
        String nickname;
        if (ShareSDK.getPlatform(QZone.NAME).isValid()) {
          nickname = ShareSDK.getPlatform(QZone.NAME).getDb().getUserName();
        } else {
          nickname = "手机用户";
        }
        String avatar = ShareSDK.getPlatform(context, QZone.NAME).getDb().getUserIcon();
        new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build()
            .create(ImageService.class)
            .updateImage(new TypedFile("image/*",file),
                new TypedString(nickname), new TypedString(Build.MODEL), new TypedString(avatar),
                new Callback<UploadResult>() {
                  @Override public void success(UploadResult uploadResult, Response response) {
                    if (uploadResult.getStatus() == 1) {
                      Log.d(TAG, "upload successfully!");
                        Toast.makeText(getActivity().getApplication(), "上传成功！",
                                Toast.LENGTH_SHORT).show();
                    } else {
                      Log.e(TAG, "upload failed!");
                        Toast.makeText(getActivity().getApplication(),
                                "上传失败！", Toast.LENGTH_SHORT).show();
                    }
                    bmp.recycle();
                  }

                  @Override public void failure(RetrofitError error) {
                    bmp.recycle();
                  }
                });
      } else {
        bmp.recycle();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
    private File saveBitmapToFile(Bitmap bmp, Uri mImageUri) {


        Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
        int quality = 100;
        OutputStream stream = null;
        File bitmapFile = null;
        try {
            // 判断SDcard状态
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            }

            // 检查SDcard空间
            File SDCardRoot = Environment.getExternalStorageDirectory();
            if (SDCardRoot.getFreeSpace() < 10000) {
                // 弹出对话框提示用户空间不够
                Log.e("Utils", "存储空间不够");

            }

            // 在SDcard创建文件夹及文件
            bitmapFile = new File(SDCardRoot.getPath() + mImageUri.getPath());
            if (!bitmapFile.exists()) bitmapFile.getParentFile().mkdirs();// 创建文件夹
            stream = new FileOutputStream(SDCardRoot.getPath() + mImageUri.getPath());// "/sdcard/"
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(format, quality, stream);
        return bitmapFile;
    }
}
