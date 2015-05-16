package cn.edu.cqupt.nmid.headline.support.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.image.ImageService;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.UploadResult;
import cn.edu.cqupt.nmid.headline.utils.BitmapUtils;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class UploadService extends Service {

  public static final String TAG = LogUtils.makeLogTag(UploadService.class);
  public static final String Key = "urikey";

  Uri outputFileUri;

  private Uri mImageUri;

  public UploadService() {
  }

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public void onCreate() {
    super.onCreate();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {

    mImageUri = intent.getParcelableExtra(Key);

    final Bitmap bmp;
    try {

      bmp = BitmapUtils.getThumbnail(this, mImageUri, 300);

      File file = saveBitmapToFile(bmp, mImageUri);
      if (file == null) new File(mImageUri.getPath());

      if (mImageUri != null) {
        String nickname;
        if (ShareSDK.getPlatform(QZone.NAME).isValid()) {
          nickname = ShareSDK.getPlatform(QZone.NAME).getDb().getUserName();
        } else {
          nickname = "手机用户";
        }
        Toast.makeText(this, "上传中！", Toast.LENGTH_SHORT).show();
        String avatar =
            ShareSDK.getPlatform(this.getApplicationContext(), QZone.NAME).getDb().getUserIcon();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();
        adapter.create(ImageService.class)
            .updateImage(new TypedFile("image/*", file), new TypedString(nickname),
                new TypedString(Build.MODEL), new TypedString(avatar),
                new Callback<UploadResult>() {
                  @Override public void success(UploadResult uploadResult, Response response) {
                    if (uploadResult.getStatus() == 1) {
                      Log.d(TAG, "upload successfully!");
                      Toast.makeText(UploadService.this, "上传成功！", Toast.LENGTH_SHORT).show();
                    } else {
                      Log.e(TAG, "upload failed!");
                      Toast.makeText(UploadService.this, "上传失败！", Toast.LENGTH_SHORT).show();
                    }
                    bmp.recycle();
                    onDestroy();
                  }

                  @Override public void failure(RetrofitError error) {
                    bmp.recycle();
                    onDestroy();
                  }
                });
      } else {
        bmp.recycle();
        onDestroy();
      }
    } catch (IOException e) {
      Toast.makeText(UploadService.this, e.getMessage(), Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    }

    return super.onStartCommand(intent, flags, startId);
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

  @Override public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy");
  }
}
