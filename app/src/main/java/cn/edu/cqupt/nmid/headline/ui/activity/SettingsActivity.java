package cn.edu.cqupt.nmid.headline.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.SupportPreferenceFragment;

public class SettingsActivity extends BaseFragmentActivity {

  public int getPreferencesResId() {
    return R.xml.settings_general;
  }

  @Override public Fragment getFragment() {
    return new SupportPreferenceFragment() {
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
        //findPreference(getResources().getString(
        //    R.string.settings_night_mode_night)).setOnPreferenceChangeListener(
        //    new Preference.OnPreferenceChangeListener() {
        //      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
        //        boolean currentNightMode = ((boolean) newValue);
        //        GlobalContext.getBus().post(currentNightMode);
        //        return false;
        //      }
        //    });
      }

      @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        GlobalContext.getBus().register(this);
      }

      @Override public void onDetach() {
        super.onDetach();
        GlobalContext.getBus().unregister(this);
      }
    };
  }
}
