package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leon on 1/10/15.
 */
public class WebViewUtils {
    public static String DBkey = WebViewUtils.class.getSimpleName();
    public static String THEME_TYPE = "isAotuoLoadImages";


    public static void setAotuoLoadImages(Context context, boolean isAotuoLoadImages) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DBkey, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isAotuoLoadImages) {
            editor.putBoolean(THEME_TYPE, true);
        } else {
            editor.putBoolean(THEME_TYPE, false);
        }
        editor.commit();
    }



    public static boolean isAotuoLoadImages(Context context){
        return context.getSharedPreferences(DBkey, 0).getBoolean(THEME_TYPE, true);
    }

}
