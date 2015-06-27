package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.ui.activity.base.BaseFragmentActivity;
import cn.edu.cqupt.nmid.headline.ui.fragment.WebViewFragment;

/**
 * Useful @Link:http://developer.android.com/training/animation/crossfade.html
 */
public class DetailedActivity extends BaseFragmentActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public Fragment getFragment() {
    return WebViewFragment.newInstance(getIntent().getExtras());
  }

  public static void startActivity(Context context, Feed feed) {
    Intent intent = new Intent(context, DetailedActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(WebViewFragment.PARCELABLE_KEY, feed);
    context.startActivity(intent);
  }
}
