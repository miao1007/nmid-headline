package cn.edu.cqupt.nmid.headline.ui.activity;

import android.support.v4.app.Fragment;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;

public class ImageCommentActivity extends BaseFragmentActivity {

  @Override public Fragment getFragment() {
    return new ImageCommentActivityFragment();
  }
}
