package cn.edu.cqupt.nmid.headline.ui.activity;

import android.preference.Preference;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BasePrefActivity;

public class SettingsMoreActivity extends BasePrefActivity {


  @Override public int getPreferencesResId() {
    return R.xml.settings_more;
  }

  @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
    return false;
  }
}
