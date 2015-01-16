package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cn.edu.cqupt.nmid.headline.R;

/**
 * Created by leon on 12/24/14.
 */
public class ThemeUtils {

    public static String THEME_TYPE = "THEME_TYPE";
    public static String DBkey = "settings_general_is_night_mode";

    public static int THEME_DARK = 1;
    public static int THEME_LIGHT = 0;

    public static void switchTheme(Context context, boolean isNight) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DBkey, isNight);
        editor.commit();

    }

    public static void setThemeFromDb(Context context) {

        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(DBkey, false)) {
            context.setTheme(R.style.AppTheme_Dark);
        } else {
            context.setTheme(R.style.AppTheme_Light);
        }
    }

    public static boolean isNightMode(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(DBkey,false);
    }

    public static int getBackgroundColor(Context context){
        if (isNightMode(context)){
            return R.color.background_floating_material_dark;
        } else {
            return R.color.background_floating_material_light;
        }
    }

}
