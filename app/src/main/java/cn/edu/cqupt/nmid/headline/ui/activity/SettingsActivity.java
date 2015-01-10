package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;

public class SettingsActivity extends BaseActivity {

    String TAG = LogUtils.makeLogTag(SettingsActivity.class);

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * accout
     */
    @InjectView(R.id.settings_account)
    TextView mTextView_account;

    @OnClick(R.id.settings_account)
    void onClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }


    /**
     * about
     */
    @InjectView(R.id.settings_about)
    TextView mTextView_about;

    @OnClick(R.id.settings_about)
    void settings_about() {
        startActivity(new Intent(this, AboutActivity.class));
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    @InjectView(R.id.settings_clen_cache)
    TextView mTextView_clen_cache;

    @InjectView(R.id.settings_font_size)
    TextView mTextView_font_size;

    @InjectView(R.id.settings_data_saving)
    TextView mTextView_data_saving;

    @InjectView(R.id.settings_push_news)
    SwitchCompat mSwitchCompat_push_news;

    @InjectView(R.id.settings_night_mode)
    SwitchCompat mSwitchCompat_night_mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.setThemeFromDb(this);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView_about.setText(getString(R.string.settings_about_nexus));
        mTextView_account.setText(getString(R.string.settings_account));
        mTextView_clen_cache.setText(getString(R.string.settings_clean_cache));
        mTextView_data_saving.setText(getString(R.string.settings_save_data));
        mTextView_font_size.setText(getString(R.string.settings_font_size));

        mSwitchCompat_push_news.setText(getString(R.string.settings_push_news));
        mSwitchCompat_night_mode.setText(getString(R.string.settings_night_mode));

    }

}
