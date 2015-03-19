package cn.edu.cqupt.nmid.headline.support.receiver;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.edu.cqupt.nmid.headline.utils.DownloadUtils;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;

/**
 * Created by leon on 2/17/15.
 */
public class DownloadCompleteReceiver extends BroadcastReceiver {

  public static String TAG = LogUtils.makeLogTag(DownloadManager.class);

  @Override
  public void onReceive(Context context, Intent intent) {

    if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
      long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
      Log.d(TAG, "onReceive" + completeDownloadId);
      DownloadManager downloadManager =
          (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

      Intent installIntent = new Intent(Intent.ACTION_VIEW);
      installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      installIntent.setDataAndType(downloadManager.getUriForDownloadedFile(completeDownloadId),
          DownloadUtils.MINETYPE_APPLCATION);
      try {
        context.startActivity(installIntent);
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
