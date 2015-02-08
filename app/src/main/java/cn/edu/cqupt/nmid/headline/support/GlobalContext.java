
package cn.edu.cqupt.nmid.headline.support;

import android.app.Application;
import cn.edu.cqupt.nmid.headline.support.pref.PushPref;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import com.testin.agent.TestinAgent;

/**
 * Created by leon on 1/27/15.
 */
public class GlobalContext extends Application {

  //singleton
  private static GlobalContext globalContext = null;


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

    TestinAgent.init(this);


  }

  public static GlobalContext getInstance() {
    return globalContext;
  }

}
