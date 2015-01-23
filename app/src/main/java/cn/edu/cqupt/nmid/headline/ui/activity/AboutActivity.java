package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.BuildConfig;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.widget.swipebacklayout.SwipeBackActivity;

public class AboutActivity extends SwipeBackActivity {

  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @InjectView(R.id.about_version) TextView mTextView_vision;

  @OnClick(R.id.about_nexus_img) void onClick() {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.addCategory(Intent.CATEGORY_BROWSABLE);
    intent.setData(Uri.parse(getString(R.string.settings_about_url)));
    startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    ButterKnife.inject(this);
    mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mTextView_vision.setText(BuildConfig.VERSION_NAME);
  }
}
