package cn.edu.cqupt.nmid.headline.ui.activity.base;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import cn.edu.cqupt.nmid.headline.utils.LolipopUtils;

/**
 * Created by leon on 15/5/17.
 */
public class BaseToolbarActivity extends AppCompatActivity {

  protected void setStatusbarColor(View view) {

    //对于Lollipop的设备，只需要在style.xml中设置colorPrimaryDark即可

    //对于4.4的设备，如下即可
    Window w = getWindow();

    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
      w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      int statusBarHeight = LolipopUtils.getStatusBarHeight(this);
      view.setPadding(0, statusBarHeight, 0, 0);
      return;
    }
  }
}
