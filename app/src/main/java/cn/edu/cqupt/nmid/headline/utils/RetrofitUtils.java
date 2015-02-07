package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import cn.edu.cqupt.nmid.headline.R;
import com.gc.materialdesign.widgets.SnackBar;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by leon on 14/11/2.
 */
public class RetrofitUtils {
  public static void disErr(Context context, RetrofitError e) {
    switch (e.getKind()) {
      case NETWORK:
        Toast.makeText(context, context.getString(R.string.NETWORK), Toast.LENGTH_SHORT).show();
        break;
      case CONVERSION:
        Toast.makeText(context, context.getString(R.string.CONVERSION), Toast.LENGTH_SHORT).show();
        break;
      case HTTP:
        switch (e.getResponse().getStatus()) {
          case 500:
            Toast.makeText(context,
                e.getResponse().getReason() + " ：" + context.getString(R.string.HTTP_500),
                Toast.LENGTH_SHORT).show();
            break;
          case 404:
            Toast.makeText(context, context.getString(R.string.HTTP_404), Toast.LENGTH_SHORT)
                .show();
            break;
          default:
            Toast.makeText(context, context.getString(R.string.HTTP)
                + String.valueOf(e.getResponse().getStatus())
                + e.getResponse().getReason(), Toast.LENGTH_SHORT).show();
            break;
        }

        break;
      case UNEXPECTED:
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        //TODO:写入日志,发送给作者
        break;
    }
  }

  public static void disSucc(Context context, Response response) {
    if (context != null) {
      Toast.makeText(context, response.getReason(), Toast.LENGTH_SHORT).show();
    }
  }

  public static void disMsg(Context context, String msg) {
    if (context != null) {
      Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
  }

  public static void disSnackMsg(ActionBarActivity context, String msg) {
    if (context != null) {
      new SnackBar(context, msg).show();
    }
  }
}
