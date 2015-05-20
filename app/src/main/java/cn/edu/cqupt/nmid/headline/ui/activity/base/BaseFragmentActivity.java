package cn.edu.cqupt.nmid.headline.ui.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.utils.LolipopUtils;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

/**
 * Created by leon on 1/28/15.
 * Best for : Webview, Settings , About
 * UI elements included only
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

  @InjectView(R.id.toolbar) public Toolbar mToolbar;
  @InjectView(R.id.toolbar_holder) public RelativeLayout mToolbarHolder;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Init the swipe back
    SwipeBack.attach(this, Position.LEFT)
        .setContentView(R.layout.activity_base_fragment)
        .setSwipeBackView(R.layout.swipeback_default);
    setContentView(R.layout.activity_base_fragment);
    ButterKnife.inject(this);
    setStatusbarColor();
    trySetupToolbar(mToolbar);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.base_fragment_container, getFragment())
        .commit();
  }

  abstract public Fragment getFragment();

  public void trySetupToolbar(Toolbar mToolbar) {
    try {
      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
      mToolbarHolder.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    } catch (NullPointerException e) {
      Log.e(getClass().getSimpleName(), "toolbar is null!");
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  protected void setStatusbarColor() {

    //对于Lollipop的设备，只需要在style.xml中设置colorPrimaryDark即可

    //对于4.4的设备，如下即可
    Window w = getWindow();

    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
      w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      int statusBarHeight = LolipopUtils.getStatusBarHeight(this);
      mToolbarHolder.setPadding(0, statusBarHeight, 0, 0);
      return;
    }
  }
}
