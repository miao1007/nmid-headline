package cn.edu.cqupt.nmid.headline.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by leon on 14/10/11.
 */
public class NetworkUtils {
    private Context context;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        return (con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting() || con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting());
    }
}
