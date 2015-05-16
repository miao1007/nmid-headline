package cn.edu.cqupt.nmid.headline.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

/**
 * Created by leon on 1/21/15.
 */
public abstract class BasePrefActivity extends BaseToolbarActivity {

  final static String TAG = LogUtils.makeLogTag(BasePrefActivity.class);

  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @InjectView(R.id.toolbar_holder) RelativeLayout mToolbarHolder;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStatusbarColor(mToolbarHolder);
    // Init the swipe back
    SwipeBack.attach(this, Position.LEFT)
        .setContentView(R.layout.activity_base_settings)
        .setSwipeBackView(R.layout.swipeback_default);
    setContentView(R.layout.activity_base_settings);
    ButterKnife.inject(this);
    Log.d(TAG, ThemePref.isNightMode(this) + "");
    mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    trySetupToolbar(mToolbar);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.include_settings_container, getFragment())
        .commit();
  }

  abstract protected Fragment getFragment();

  public void trySetupToolbar(Toolbar mToolbar) {
    try {
      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
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
}
