package cn.edu.cqupt.nmid.headline.ui.activity.base;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
public abstract class BasePrefActivity extends ActionBarActivity
    implements Preference.OnPreferenceChangeListener {

  final static String TAG = LogUtils.makeLogTag(BasePrefActivity.class);

  @InjectView(R.id.include_settings_toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Init the swipe back
    SwipeBack.attach(this, Position.LEFT)
        .setContentView(R.layout.activity_base_settings)
        .setSwipeBackView(R.layout.swipeback_default);
    setContentView(R.layout.activity_base_settings);
    ButterKnife.inject(this);
    Log.d(TAG, ThemePref.isNightMode(this) + "");
    mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    trySetupToolbar(mToolbar);
    getFragmentManager().beginTransaction()
        .replace(R.id.include_settings_container, new PreferenceFragment() {
          @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
              Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            //Set night-mode for background
            view.setBackgroundResource(ThemePref.getBackgroundResColor(getActivity()));
            return view;
          }

          @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(getPreferencesResId());
          }
        })
        .commit();
  }

  abstract public int getPreferencesResId();

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
    if (item.getItemId() == android.R.id.home){
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.swipeback_stack_to_front, R.anim.swipeback_stack_right_out);
  }
}
