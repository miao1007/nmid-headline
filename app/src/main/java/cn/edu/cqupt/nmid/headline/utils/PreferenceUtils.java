package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import cn.edu.cqupt.nmid.headline.R;

/**
 * Created by leon on 1/14/15.
 */
public class PreferenceUtils {

    public static int getWebViewTextZoom(Context context) {
        try {
            return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.settings_font_size), "100"));
        } catch (ClassCastException e) {
            return 100;
        }
    }

    public static int getQueryLimit(Context context) {
        try {
            return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.settings_push_feeds), "15"));
        } catch (ClassCastException e) {
            return 15;
        }
    }

    public static boolean isPushFeeds(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.settings_push_feeds), false);
    }


    public static int isAutoLoadImages(Context context) {
        try {
            return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.settings_data_usage_control), "1"));
        } catch (ClassCastException e) {
            return 1;
        }
    }

}
