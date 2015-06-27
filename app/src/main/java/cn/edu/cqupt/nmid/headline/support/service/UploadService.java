package cn.edu.cqupt.nmid.headline.support.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.event.ImageUploadEvent;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.image.ImageService;
import cn.edu.cqupt.nmid.headline.support.repository.image.bean.UploadResult;
import cn.edu.cqupt.nmid.headline.utils.ImageUtils;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import java.io.File;
import java.io.IOException;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class UploadService extends Service {

  public static final String TAG = LogUtils.makeLogTag(UploadService.class);
  public static final String Key = "urikey";

  private Uri mImageUri;

  public UploadService() {
  }

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public void onCreate() {
    super.onCreate();
    GlobalContext.getBus().register(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    GlobalContext.getBus().unregister(this);
    Log.d(TAG, "onDestroy");
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {

    mImageUri = intent.getParcelableExtra(Key);
    if (mImageUri == null && !ShareSDK.getPlatform(QZone.NAME).isValid()) {
      onDestroy();
      return -1;
    }

    String nickname = ShareSDK.getPlatform(QZone.NAME).getDb().getUserName();
    String avatar = ShareSDK.getPlatform(QZone.NAME).getDb().getUserIcon();

    try {
      ImageUtils.createImageThumbnail(getApplicationContext(), mImageUri.getPath(),
          mImageUri.getPath(), 800, 100);
    } catch (IOException e) {
      Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_SHORT).show();
      return super.onStartCommand(intent, flags, startId);
    }

    Toast.makeText(this, "上传中！", Toast.LENGTH_SHORT).show();
    RestAdapter adapter = RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT);
    adapter.create(ImageService.class)
        .updateImage(new TypedFile("image/*", new File(mImageUri.getPath())),
            new TypedString(nickname), new TypedString(Build.MODEL), new TypedString(avatar),
            new Callback<UploadResult>() {
              @Override public void success(UploadResult uploadResult, Response response) {
                if (uploadResult.getStatus() == 1) {
                  Log.d(TAG, "upload successfully!");
                  Toast.makeText(getApplicationContext(), "上传成功！", Toast.LENGTH_SHORT).show();
                  GlobalContext.getBus().post(new ImageUploadEvent(true));
                } else {
                  Log.e(TAG, "upload failed!");
                  Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_SHORT).show();
                }
                UploadService.this.stopSelf();
              }

              @Override public void failure(RetrofitError error) {
                onDestroy();
              }
            });

    return super.onStartCommand(intent, flags, startId);
  }
}
