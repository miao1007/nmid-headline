package cn.edu.cqupt.nmid.headline.support.pref;

import android.content.Context;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.PreferenceUtils;

/**
 * Created by leon on 1/21/15.
 */
public class ThemePref {

  public static boolean isNightMode(Context context) {

    String DBkey = context.getString(R.string.settings_night_mode_night);
    return PreferenceUtils.getPrefBoolean(context, DBkey, false);
  }

  public static void setNightMode(Context context, boolean isNightMode) {

    String DBkey = context.getString(R.string.settings_night_mode_night);
    PreferenceUtils.setPrefBoolean(context, DBkey, isNightMode);
  }

  public static int getBackgroundResColor(Context context) {
    if (isNightMode(context)) {
      return R.color.background_floating_material_dark;
    } else {
      return R.color.background_floating_material_light;
    }
  }

  public static int getToolbarBackgroundResColor(Context context) {
    if (isNightMode(context)) {
      return R.color.background_material_dark;
    } else {
      return R.color.primary;
    }
  }

  public static int getItemBackgroundResColor(Context context) {
    if (isNightMode(context)) {
      return R.color.background_item_material_dark;
    } else {
      return R.color.background_item_material_light;
    }
  }
}
