package cn.edu.cqupt.nmid.headline.support.pref;

import android.content.Context;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.PreferenceUtils;

/**
 * Created by leon on 1/21/15.
 */
public class HttpPref {

  public static int getQueryFeedsLimit(Context context) {

    String key = context.getString(R.string.settings_more_query_limit);
    return Integer.valueOf(PreferenceUtils.getPrefString(context, key, "15"));
  }
}
