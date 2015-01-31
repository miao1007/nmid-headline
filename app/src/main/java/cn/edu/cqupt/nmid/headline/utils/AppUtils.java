package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by leon on 1/31/15.
 */
public class AppUtils {

  public static String getImei(Context context, String imei) {
    try {
      TelephonyManager telephonyManager =
          (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      imei = telephonyManager.getDeviceId();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return imei;
  }
}
