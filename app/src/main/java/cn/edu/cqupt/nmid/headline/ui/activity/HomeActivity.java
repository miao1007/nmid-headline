package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.fragment.ImagesFeedFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.NavigationDrawerFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.NewsFeedFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.SlidingTabFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.LolipopUtils;
import cn.jpush.android.api.JPushInterface;
import com.squareup.otto.Subscribe;

/**
 *
 */
public class HomeActivity extends AppCompatActivity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {

  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @InjectView(R.id.toolbar_holder) RelativeLayout mToolbarHolder;

  @InjectView(R.id.home_drawer_layout) DrawerLayout mDrawerLayout;
  @InjectView(R.id.home_content_layout) LinearLayout mLinearLayout;

  NavigationDrawerFragment mNavigationDrawerFragment;
  private String TAG = LogUtils.makeLogTag(HomeActivity.class);

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.inject(this);
    setStatusbarColor();
    trySetupToolbar();
    trySetupNavigationDrawer();
  }

  public void trySetupToolbar() {
    try {
      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
      mToolbarHolder.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    } catch (NullPointerException e) {
      Log.e(getClass().getSimpleName(), "toolbar is null!");
    }
  }

  private void trySetupNavigationDrawer() {

    mNavigationDrawerFragment =
        (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
            R.id.navigation_drawer);

    mNavigationDrawerFragment.setUp(mDrawerLayout, mToolbar);
  }

  @Override public void onNavigationDrawerItemSelected(int position) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment = null;
    switch (position) {
      case 0:
        fragment = new SlidingTabFragment();
        break;
      case 1:
        fragment = new ImagesFeedFragment();
        break;
      case 2:
        fragment = NewsFeedFragment.newFavInstance();
        break;
    }

    if (fragment != null) {
      fragmentManager.beginTransaction().replace(R.id.base_fragment_container, fragment).commit();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    if (ThemePref.isNightMode(this)) {
      menu.getItem(0).setTitle(R.string.settings_night_mode_day);
    } else {
      menu.getItem(0).setTitle(R.string.settings_night_mode_night);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_night_mode:
        ThemePref.setNightMode(this, !ThemePref.isNightMode(this));

        Intent intent = new Intent(this, this.getClass());
        startActivity(intent);
        finish();

        break;
      case R.id.action_settings:
        startActivity(new Intent(this, SettingsActivity.class));
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onResume() {
    super.onResume();
    JPushInterface.onResume(this);
    GlobalContext.getBus().register(this);
  }

  @Override protected void onPause() {
    super.onPause();
    JPushInterface.onPause(this);
    GlobalContext.getBus().unregister(this);
  }

  @Subscribe public void onNightmode(boolean currentNightMode) {
    ThemePref.setNightMode(this, !ThemePref.isNightMode(this));
    Intent intent = new Intent(this, this.getClass());
    startActivity(intent);
    finish();
  }

  @Override protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "onRestart");
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
