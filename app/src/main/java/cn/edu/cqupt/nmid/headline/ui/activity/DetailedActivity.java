package cn.edu.cqupt.nmid.headline.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.WebViewFragment;

/**
 * Useful @Link:http://developer.android.com/training/animation/crossfade.html
 */
public class DetailedActivity extends BaseFragmentActivity {

  Feed feed;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override public Fragment getFragment() {
    return WebViewFragment.newInstance(getIntent().getExtras());
  }
}
