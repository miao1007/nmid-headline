package cn.edu.cqupt.nmid.headline.support.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.ui.activity.DetailedActivity;
import cn.jpush.android.api.JPushInterface;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
  private static final String TAG = "JPush";

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle bundle = intent.getExtras();
    if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
      Log.d(TAG, "用户点击打开了通知");
      processClick(context, bundle);
    }
  }

  private void processClick(Context context, Bundle bundle) {
    //打开自定义的Activity
    Log.d(TAG, printBundle(bundle));
    Intent i = new Intent(context, DetailedActivity.class);
    i.putExtras(bundle);
    System.out.println("bundle.getString(JPushInterface.EXTRA_EXTRA) = " + bundle.getString(
        JPushInterface.EXTRA_EXTRA).length());
    System.out.println(
        "bundle.getString(JPushInterface.EXTRA_EXTRA).trim().length() = " + bundle.getString(
            JPushInterface.EXTRA_EXTRA).trim().length());
    if (bundle.getString(JPushInterface.EXTRA_EXTRA).trim().length() > 3) {
      JsonObject newObj =
          new JsonParser().parse(bundle.getString(JPushInterface.EXTRA_EXTRA)).getAsJsonObject();

      i.putExtra(HeadlineService.ID, newObj.get(HeadlineService.ID).getAsInt());
      i.putExtra(HeadlineService.CATEGORY, newObj.get(HeadlineService.CATEGORY).getAsInt());

      //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      context.startActivity(i);
    } else {
      Log.e(TAG, "cn.jpush.android.EXTRA IllegalFormatException");
    }
  }

  // 打印所有的 intent extra 数据
  private static String printBundle(Bundle bundle) {
    StringBuilder sb = new StringBuilder();
    for (String key : bundle.keySet()) {
      if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
      } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
      } else {
        sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
      }
    }
    return sb.toString();
  }
}
