package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.Constant;
import cn.edu.cqupt.nmid.headline.support.api.weather.WeatherService;
import cn.edu.cqupt.nmid.headline.support.api.weather.bean.Weather;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.task.UserInfoGetTask;
import cn.edu.cqupt.nmid.headline.ui.activity.SettingsActivity;
import cn.edu.cqupt.nmid.headline.ui.adapter.NavigationItemsAdapter;
import cn.edu.cqupt.nmid.headline.ui.adapter.NavigationSecondaryItemsAdapter;
import cn.edu.cqupt.nmid.headline.utils.PreferenceUtils;
import cn.edu.cqupt.nmid.headline.utils.animation.BlurTransformation;
import cn.edu.cqupt.nmid.headline.utils.animation.CircleTransformation;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NavigationDrawerFragment extends Fragment {

  private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
  private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

  View rootView;
  Context context;

  @InjectView(R.id.navigation_drawer_holder) LinearLayout mLinearLayout;
  @InjectView(R.id.navigation_drawer_avatar) ImageView mAvatar;
  @InjectView(R.id.navigation_drawer_avatar_bg) ImageView mAvatarBg;
  @InjectView(R.id.navigation_drawer_username) TextView mUsername;
  @InjectView(R.id.navigation_drawer_profile) FrameLayout mProfile;
  @InjectView(R.id.navigation_drawer_list_main) ListView mMainListView;
  @InjectView(R.id.navigation_drawer_list_secondary) ListView mSecondaryListView;
  NavigationItemsAdapter mNavigationItemsAdapter;
  private UserInfoGetTask mUserInfoFetchTask;
  private NavigationDrawerCallbacks mCallbacks;
  private ActionBarDrawerToggle mDrawerToggle;
  private DrawerLayout mDrawerLayout;
  private View mFragmentContainerView;
  private int mCurrentSelectedPosition = 0;
  private boolean mFromSavedInstanceState;
  private boolean mUserLearnedDrawer;

  @OnClick(R.id.navigation_drawer_avatar) void navigation_drawer_avatar() {
    Platform qzone = ShareSDK.getPlatform(getActivity(), QZone.NAME);
    qzone.setPlatformActionListener(new PlatformActionListener() {
      @Override public void onComplete(Platform platform, int i, HashMap<String, Object> map) {
        fetchUserInfo();
      }

      @Override public void onError(Platform platform, int i, Throwable throwable) {

      }

      @Override public void onCancel(Platform platform, int i) {

      }
    });
    qzone.authorize();
    if (mDrawerLayout != null) {
      mDrawerLayout.closeDrawer(mFragmentContainerView);
    }
  }

  @OnItemClick(R.id.navigation_drawer_list_main) void navigation_drawer_list_main(int position) {
    selectItem(position);
  }

  @OnItemClick(R.id.navigation_drawer_list_secondary) void navigation_drawer_list_secondary(
      int position) {
    switch (position) {
      case 0:
        startActivity(new Intent(getActivity(), SettingsActivity.class));
        break;
      case 1:
        getActivity().finish();
        break;
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    context = getActivity();
    mUserLearnedDrawer =
        PreferenceUtils.getPrefBoolean(getActivity(), PREF_USER_LEARNED_DRAWER, false);

    if (savedInstanceState != null) {
      mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
      mFromSavedInstanceState = true;
    }
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ShareSDK.initSDK(getActivity());
    rootView = inflater.inflate(R.layout.fragment_navigation_drawer, null);
    ButterKnife.inject(this, rootView);

    //add night mode
    mLinearLayout.setBackgroundResource(ThemePref.getItemBackgroundResColor(getActivity()));
    mProfile.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(getActivity()));

    mNavigationItemsAdapter = new NavigationItemsAdapter(getActivity());
    mMainListView.setAdapter(mNavigationItemsAdapter);
    mMainListView.getLayoutParams().height = mNavigationItemsAdapter.getListViewHeight();

    mMainListView.setItemChecked(mCurrentSelectedPosition, true);

    NavigationSecondaryItemsAdapter mNavSecondaryItemsAdapter =
        new NavigationSecondaryItemsAdapter(getActivity());
    mSecondaryListView.setAdapter(mNavSecondaryItemsAdapter);
    mSecondaryListView.getLayoutParams().height = mNavSecondaryItemsAdapter.getListViewHeight();

    fetchUserInfo();

    selectItem(mCurrentSelectedPosition);

    return rootView;
  }

  public void fetchUserInfo() {
    PlatformDb db = ShareSDK.getPlatform(getActivity(), QZone.NAME).getDb();
    if (db.isValid()) {
      Picasso.with(context)
          .load(db.getUserIcon())
          .fit()
          .transform(new CircleTransformation())
          .into(mAvatar);
      Picasso.with(context)
          .load(db.getUserIcon())
          .transform(new BlurTransformation(context))
          .into(mAvatarBg);
      mUsername.setText(db.getUserName());
    }

    new RestAdapter.Builder().setEndpoint(Constant.ENDPOINT)
        .build()
        .create(WeatherService.class)
        .getWeatherService(new Callback<Weather>() {
          @Override public void success(Weather weather, Response response) {

          }

          @Override public void failure(RetrofitError error) {

          }
        });
  }

  public boolean isDrawerOpen() {
    return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
  }

  public void toggleDrawer() {
    if (mDrawerLayout != null) {
      if (isDrawerOpen()) {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
      } else {
        mDrawerLayout.openDrawer(mFragmentContainerView);
      }
    }
  }

  public void setUp(int drawerHolderId, DrawerLayout drawerLayout, Toolbar toolbar) {
    mFragmentContainerView = getActivity().findViewById(drawerHolderId);
    mDrawerLayout = drawerLayout;
    mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

    mDrawerLayout.setStatusBarBackground(R.color.primarg_bg);

    mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.open,
        R.string.close) {

      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (!isAdded()) {
          return;
        }

        getActivity().invalidateOptionsMenu();
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (!isAdded()) {
          return;
        }

        if (!mUserLearnedDrawer) {
          mUserLearnedDrawer = true;
          SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
          sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
        }

        getActivity().invalidateOptionsMenu();
      }
    };

    if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
      mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    mDrawerLayout.post(new Runnable() {
      @Override
      public void run() {
        mDrawerToggle.syncState();
      }
    });

    mDrawerLayout.setDrawerListener(mDrawerToggle);
  }

  private void selectItem(int position) {
    mCurrentSelectedPosition = position;

    if (mMainListView != null) mMainListView.setItemChecked(position, true);
    if (mDrawerLayout != null) mDrawerLayout.closeDrawer(mFragmentContainerView);
    if (mCallbacks != null) mCallbacks.onNavigationDrawerItemSelected(position);

    if (position == 100) {
      // Profile was selected
      //HomeActivity.m = getString(R.string.title_profile);

      if (mMainListView != null && mMainListView.getAdapter() != null) {
        ((NavigationItemsAdapter) mMainListView.getAdapter()).selectItem(100);
      }
    } else if (mMainListView != null && mMainListView.getAdapter() != null) {
      ((NavigationItemsAdapter) mMainListView.getAdapter()).selectItem(position);
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mCallbacks = (NavigationDrawerCallbacks) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mCallbacks = null;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    if (mDrawerLayout != null && isDrawerOpen()) {
      //inflater.inflate(R.menu.global, menu);
      showGlobalContextActionBar();
    }
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showGlobalContextActionBar() {
    ActionBarActivity activity = (ActionBarActivity) getActivity();
    if (activity != null) activity.getSupportActionBar().setTitle(getString(R.string.app_name));
  }

  public static interface NavigationDrawerCallbacks {
    void onNavigationDrawerItemSelected(int position);
  }
}
