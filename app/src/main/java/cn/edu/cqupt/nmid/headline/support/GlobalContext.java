package cn.edu.cqupt.nmid.headline.support;

import android.app.Application;

/**
 * Created by leon on 1/27/15.
 */
public class GlobalContext extends Application {

  //singleton
  private static GlobalContext globalContext = null;


  @Override public void onCreate() {
    super.onCreate();
    globalContext = this;
  }

  public static GlobalContext getInstance() {
    return globalContext;
  }

}
