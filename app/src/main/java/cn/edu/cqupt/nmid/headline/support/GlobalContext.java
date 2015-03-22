package cn.edu.cqupt.nmid.headline.support;

import android.app.Application;
import android.util.Log;
import cn.edu.cqupt.nmid.headline.support.pref.DebugPref;
import cn.edu.cqupt.nmid.headline.support.pref.PushPref;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import com.activeandroid.ActiveAndroid;
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

    isDebug = DebugPref.isDebug(this);

    //start JPush service
    if (PushPref.isPushFeeds(this)) {

      JPushInterface.init(this);
      JPushInterface.setDebugMode(isDebug);
    }
    //bugly.qq.com
    String appId = "1104137422";


    CrashReport.initCrashReport(this, appId, isDebug);
    //sharesdk
    ShareSDK.initSDK(this);
    // Here you start using the ActiveAndroid library.
    ActiveAndroid.initialize(this);


  }

  @Override public void onTerminate() {
    super.onTerminate();
    Log.d("GlobalContext","onTerminate");
    ActiveAndroid.dispose();
  }

  public static GlobalContext getInstance() {
    return globalContext;
  }
}
