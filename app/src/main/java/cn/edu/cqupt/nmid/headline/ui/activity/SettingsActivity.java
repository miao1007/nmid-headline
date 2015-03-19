package cn.edu.cqupt.nmid.headline.ui.activity;

import android.preference.Preference;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BasePrefActivity;

public class SettingsActivity extends BasePrefActivity {

  @Override public int getPreferencesResId() {
    return R.xml.settings_general;
  }

  @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
    
    return false;
  }
}
