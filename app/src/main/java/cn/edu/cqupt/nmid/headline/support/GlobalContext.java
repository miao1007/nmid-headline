package cn.edu.cqupt.nmid.headline.support;

import android.app.Application;
import cn.edu.cqupt.nmid.headline.support.pref.DebugPref;
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
  private boolean isDebug = true;

  @Override public void onCreate() {
    super.onCreate();
    globalContext = this;

    isDebug = DebugPref.isPushFeeds(this);

    //start JPush service
    if (PushPref.isPushFeeds(this)) {

      JPushInterface.init(this);
      JPushInterface.setDebugMode(isDebug);
    }

    String appId = "1104137422";   //上Bugly(bugly.qq.com)注册产品获取的AppId

    CrashReport.initCrashReport(this, appId, isDebug);  //初始化SDK
    //sharesdk
    ShareSDK.initSDK(this);
  }

  public static GlobalContext getInstance() {
    return globalContext;
  }
}
