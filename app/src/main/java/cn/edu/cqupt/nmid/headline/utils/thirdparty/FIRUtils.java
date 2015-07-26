package cn.edu.cqupt.nmid.headline.utils.thirdparty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

  public final static void checkForUpdate(Context context, boolean isShowToast) {
    VersionCheckCallback versionCheckCallback = callback(context, isShowToast);
    if (BuildConfig.DEBUG) {
      FIR.checkForUpdateInFIR(context, FIR.GENERAL_KEY, versionCheckCallback);
    } else {
      FIR.checkForUpdateInAppStore(context, versionCheckCallback);
    }
  }

  static VersionCheckCallback callback(final Context context, final boolean isShowToast) {
    return new VersionCheckCallback() {
      @Override public void onSuccess(final AppVersion appVersion, boolean b) {

        if (appVersion.getVersionCode() == BuildConfig.VERSION_CODE) {
          if (isShowToast) Toast.makeText(context, "你已经是最新版本", Toast.LENGTH_SHORT).show();

          return;
        }
        try {
          new AlertDialog.Builder(context).setTitle("有新版本可用")
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
          //IllegalStateException when using appcompAlertDialog or NullException when windows leak
        } catch (Exception e) {
          Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override public void onFail(String s, int i) {
        if (isShowToast) Toast.makeText(context, "检查更新失败", Toast.LENGTH_SHORT).show();
      }

      @Override public void onError(Exception e) {
        if (isShowToast) Toast.makeText(context, "检查更新失败", Toast.LENGTH_SHORT).show();
      }

      @Override public void onStart() {
        if (isShowToast) Toast.makeText(context, "开始检查更新", Toast.LENGTH_SHORT).show();
      }

      @Override public void onFinish() {

      }
    };
  }
}
