package cn.edu.cqupt.nmid.headline.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.widget.swipebacklayout.SwipeBackActivity;

/**
 * Created by leon on 1/28/15.
 */
public abstract class BaseFragmentActivity extends SwipeBackActivity {

  @InjectView(R.id.toolbar) Toolbar mToolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base_fragment);
    ButterKnife.inject(this);
    mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    trySetupToolbar(mToolbar);
    getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, getFragment()).commit();
  }

  abstract public Fragment getFragment();
}
