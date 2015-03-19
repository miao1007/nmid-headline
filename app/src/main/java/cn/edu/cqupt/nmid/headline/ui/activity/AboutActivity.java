package cn.edu.cqupt.nmid.headline.ui.activity;

import android.support.v4.app.Fragment;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.AboutFragment;

public class AboutActivity extends BaseFragmentActivity {

  @Override public Fragment getFragment() {
    return new AboutFragment();
  }
}
