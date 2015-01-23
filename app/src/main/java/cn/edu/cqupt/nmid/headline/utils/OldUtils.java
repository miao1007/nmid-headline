package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import java.util.Date;

/**
 * Created by xihsa_000 on 3/15/14.
 */
public class OldUtils {
    Context context;

    public OldUtils(Context context) {
        this.context = context;
    }

    private static double removeMinusFromLong(double time) {
        if (time < 0) {
            String timeString = time + "";
            timeString = timeString.substring(1, timeString.length());
            return Double.parseDouble(timeString);
        } else return time;
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }


}
