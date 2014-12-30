package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.edu.cqupt.nmid.headline.R;

/**
 * Created by leon on 12/24/14.
 */
public class ThemeUtils {

    public static String THEME_TYPE = "THEME_TYPE";
    public static String DBkey = ThemeUtils.class.getSimpleName();

    public static int THEME_DARK = 1;
    public static int THEME_LIGHT = 0;

    public static void switchTheme(Context context, int themeType) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DBkey, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        if (themeType == THEME_DARK) {
            editor.putInt(THEME_TYPE, THEME_DARK);
        } else {
            editor.putInt(THEME_TYPE, THEME_LIGHT);
        }
        editor.commit();


    }

    public static void setThemeFromDb(Context context) {

        int currentTheme = context.getSharedPreferences(DBkey, 0).getInt(THEME_TYPE, THEME_LIGHT);
        if (currentTheme == THEME_DARK){
            context.setTheme(R.style.AppTheme_Dark);
        } else {
            context.setTheme(R.style.AppTheme_Light);
        }
    }

    public static boolean isNightMode(Context context){
        int currentTheme = context.getSharedPreferences(DBkey, 0).getInt(THEME_TYPE, THEME_LIGHT);
        if (currentTheme == THEME_DARK){
            return true;
        } else {
            return false;
        }
    }

}
