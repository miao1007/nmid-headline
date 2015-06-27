package cn.edu.cqupt.nmid.headline.support;

import android.app.Application;
import android.util.Log;
import cn.edu.cqupt.nmid.headline.support.pref.DebugPref;
import cn.edu.cqupt.nmid.headline.support.pref.PushPref;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import com.activeandroid.ActiveAndroid;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import im.fir.sdk.FIR;

/**
 * Created by leon on 1/27/15.
 */
public class GlobalContext extends Application {

  //singleton
  private static GlobalContext globalContext = null;
  private boolean isDebug = true;
  public static Bus bus;

  @Override public void onCreate() {
    FIR.init(this);
    super.onCreate();
    globalContext = this;

    bus = new Bus(ThreadEnforcer.MAIN);
    isDebug = DebugPref.isDebug(this);

    //start JPush service
    if (PushPref.isPushFeeds(this)) {

      JPushInterface.init(this);
      JPushInterface.setDebugMode(isDebug);
    }

    //sharesdk
    ShareSDK.initSDK(this);
    // Here you start using the ActiveAndroid library.
    ActiveAndroid.initialize(this);
  }

  //However , It will never be called.
  @Override public void onTerminate() {
    super.onTerminate();
    Log.d("GlobalContext", "onTerminate");
    bus.unregister(this);
    //ActiveAndroid.dispose();
  }

  public static GlobalContext getInstance() {
    return globalContext;
  }

  //otto eventbus
  public static Bus getBus() {
    return bus;
  }
}
