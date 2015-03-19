package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by leon on 1/31/15.
 */
public class JPushUtils {

  public static final String KEY_APP_KEY = "JPUSH_APPKEY";

  // 取得AppKey
  public static String getAppKey(Context context) {
    Bundle metaData = null;
    String appKey = null;
    try {
      ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
          context.getPackageName(), PackageManager.GET_META_DATA);
      if (null != ai)
        metaData = ai.metaData;
      if (null != metaData) {
        appKey = metaData.getString(KEY_APP_KEY);
        if ((null == appKey) || appKey.length() != 24) {
          appKey = null;
        }
      }
    } catch (PackageManager.NameNotFoundException e) {

    }
    return appKey;
  }
}
