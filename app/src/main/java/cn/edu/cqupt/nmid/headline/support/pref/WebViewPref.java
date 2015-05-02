package cn.edu.cqupt.nmid.headline.support.pref;

import android.content.Context;
import android.preference.PreferenceManager;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.utils.PreferenceUtils;

/**
 * Created by leon on 1/20/15.
 */
public class WebViewPref {

  /*
  * See R.arrray.settings_entry_values_font_sizes
  * */
  public static int getWebViewTextZoom(Context context) {

    String key = GlobalContext.getInstance().getString(R.string.settings_font_size);
    return Integer.valueOf(PreferenceUtils.getPrefString(context, key, "115"));
  }

  /*
  * See {@link R.array.settings_entry_values_load_images}
  * */
  public static int isAutoLoadImages(Context context) {
    try {
      return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context)
          .getString(context.getString(R.string.settings_data_usage_control), "1"));
    } catch (ClassCastException e) {
      return 1;
    }
  }
}
