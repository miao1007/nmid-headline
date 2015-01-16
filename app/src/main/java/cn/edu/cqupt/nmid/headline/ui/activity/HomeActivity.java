package cn.edu.cqupt.nmid.headline.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.Constant;
import cn.edu.cqupt.nmid.headline.support.api.weather.WeatherService;
import cn.edu.cqupt.nmid.headline.support.api.weather.bean.Weather;
import cn.edu.cqupt.nmid.headline.ui.adapter.PagerAdapter;
import cn.edu.cqupt.nmid.headline.ui.fragment.FeedFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.FeedsFragment;
import cn.edu.cqupt.nmid.headline.ui.fragment.NavigationDrawerFragment;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.NetworkUtils;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 *
 */
public class HomeActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private String TAG = LogUtils.makeLogTag(HomeActivity.class);
    private boolean isAnimate = false;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    MenuItem actionView;
    NavigationDrawerFragment mNavigationDrawerFragment;


    PagerAdapter mPagerAdapter;

    ArrayList<FeedFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onRestart");
        ThemeUtils.setThemeFromDb(this);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        trySetupToolbar();
        trySyncWeather();


    }

    /**
     * Setup Toolbar and L-style Toggle
     */
    private void trySetupToolbar() {


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer_holder,
                mDrawerLayout,
                mToolbar);

    }




    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment =  new FeedsFragment();break;
            case 1:

        }

        if (fragment != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        actionView = menu.findItem(R.id.action_weather);
        actionView.setActionView(R.layout.include_weather);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    protected void onStart() {
        super.onStart();
        //recreate();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
        //recreate();
    }



    /**
     * Sync Weather data via rest service
     */
    private void trySyncWeather() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            new RestAdapter.Builder()
                    .setEndpoint(Constant.ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build()
                    .create(WeatherService.class)
                    .getWeatherService(
                            new Callback<Weather>() {
                                @Override
                                public void success(Weather weather, Response response) {


                                    ((TextView) actionView.getActionView().findViewById(R.id.weather_tempeture)).setText(
                                            weather.getData().getTemperature() + " " + weather.getData().getTitle());

                                    Picasso.with(HomeActivity.this)
                                            .load(weather.getData().getDaypictureurl())
                                            .into(((ImageView) actionView.getActionView().findViewById(R.id.weather_img))
                                            );

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.e(TAG, "getWeatherService Failed");
                                }
                            });
        }
    }

}
