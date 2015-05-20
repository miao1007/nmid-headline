package cn.edu.cqupt.nmid.headline.utils.thirdparty;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import cn.edu.cqupt.nmid.headline.BuildConfig;
import cn.edu.cqupt.nmid.headline.utils.DownloadUtils;
import im.fir.sdk.FIR;
import im.fir.sdk.callback.VersionCheckCallback;
import im.fir.sdk.version.AppVersion;

/**
 * Created by leon on 15/5/16.
 * Check for update via Fir.im or wandoujia.You need to use a Theme.AppCompat theme (or descendant)
 * with this activity
 */
public class FIRUtils {

  public final static void checkForUpdate(Context context) {
    if (BuildConfig.DEBUG) {
      FIR.checkForUpdateInFIR(context, FIR.GENERAL_KEY, callback(context));
    } else {
      FIR.checkForUpdateInAppStore(context.getApplicationContext(), callback(context));
    }
  }

  static VersionCheckCallback callback(final Context context) {
    return new VersionCheckCallback() {
      @Override public void onSuccess(final AppVersion appVersion, boolean b) {

        if (appVersion.getVersionCode() == BuildConfig.VERSION_CODE) {
          Toast.makeText(context, "你已经是最新版本", Toast.LENGTH_SHORT).show();
          return;
        }
        new AlertDialog.Builder(context).setTitle("最新版本 " + appVersion.getVersionName())
            .setMessage(appVersion.getChangeLog())
            .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialogInterface, int i) {
                //when download complete, broadcast will be sent to receiver
                DownloadUtils.DownloadApkWithProgress(context.getApplicationContext(),
                    appVersion.getUpdateUrl());
              }
            })
            .create()
            .show();
      }

      @Override public void onFail(String s, int i) {
        Toast.makeText(context, "检查更新失败", Toast.LENGTH_SHORT).show();
      }

      @Override public void onError(Exception e) {
        Toast.makeText(context, "检查更新失败", Toast.LENGTH_SHORT).show();
      }

      @Override public void onStart() {
        Toast.makeText(context, "开始检查更新", Toast.LENGTH_SHORT).show();
      }

      @Override public void onFinish() {

      }
    };
  }
}
