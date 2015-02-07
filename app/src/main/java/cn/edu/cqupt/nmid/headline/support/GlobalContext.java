package cn.edu.cqupt.nmid.headline.support;

import android.app.Application;
import cn.edu.cqupt.nmid.headline.support.pref.PushPref;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by leon on 1/27/15.
 */
public class GlobalContext extends Application {

  //singleton
  private static GlobalContext globalContext = null;

  //do not use the key anywhere else without my written permission
  private static String appId = "1104137422";

  @Override public void onCreate() {
    super.onCreate();
    globalContext = this;
    //start JPush service

    //JPushInterface.setDebugMode(true);
    //JPushInterface.init(this);
    if (PushPref.isPushFeeds(this)){
      JPushInterface.init(this);
    }

    //sharesdk
    ShareSDK.initSDK(this);

    //tencent bugly
    CrashReport. initCrashReport(this, appId, true);  //初始化SDK

  }

  public static GlobalContext getInstance() {
    return globalContext;
  }

}
