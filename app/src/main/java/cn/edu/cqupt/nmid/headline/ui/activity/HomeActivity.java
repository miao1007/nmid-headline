package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.fragment.FeedFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.FeedsFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.NavigationDrawerFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.StreamFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.jpush.android.api.JPushInterface;

/**
 *
 */
public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.home_drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.home_content_layout)
    LinearLayout mLinearLayout;

    NavigationDrawerFragment mNavigationDrawerFragment;
    private String TAG = LogUtils.makeLogTag(HomeActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        Log.d(TAG, "onCreate");
        mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
        mLinearLayout.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
        trySetupToolbar();
    }

    private void trySetupToolbar() {

        setSupportActionBar(mToolbar);
        mNavigationDrawerFragment =
                (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
                        R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(mDrawerLayout, mToolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FeedsFragment();
                break;
            case 1:
                fragment = new StreamFragment();
                break;
            case 2:
                fragment = FeedFragment.newFavInstance();
                break;
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        if (ThemePref.isNightMode(this)) {
            menu.getItem(0).setTitle(R.string.settings_night_mode_day);
        } else {
            menu.getItem(0).setTitle(R.string.settings_night_mode_night);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
