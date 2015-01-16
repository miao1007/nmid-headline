package cn.edu.cqupt.nmid.headline.ui.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;

public class SettingsActivity extends BaseActivity{

    String TAG = LogUtils.makeLogTag(SettingsActivity.class);

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.setThemeFromDb(this);
        setContentView(R.layout.include_settings);

        ButterKnife.inject(this);
        trySetupToolbar(mToolbar);
        getFragmentManager().beginTransaction().replace(R.id.include_settings_container, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_general);
        }

    }


}
