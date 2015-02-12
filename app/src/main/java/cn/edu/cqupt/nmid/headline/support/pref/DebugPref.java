package cn.edu.cqupt.nmid.headline.support.pref;

import android.content.Context;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.PreferenceUtils;

/**
 * Created by leon on 2/12/15.
 */
public class DebugPref {
  public static boolean isPushFeeds(Context context) {
    String DBkey = context.getString(R.string.setting_debug);
    return PreferenceUtils.getPrefBoolean(context, DBkey, false);
  }
}
