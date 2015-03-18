package cn.edu.cqupt.nmid.headline.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

/**
 * Created by leon on 1/28/15.
 */
public abstract class BaseFragmentActivity extends ActionBarActivity {


  @InjectView(R.id.base_frag_toolbar) Toolbar mToolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Init the swipe back
    SwipeBack.attach(this, Position.LEFT)
        .setContentView(R.layout.activity_base_fragment)
        .setSwipeBackView(R.layout.swipeback_default);
    setContentView(R.layout.activity_base_fragment);
    ButterKnife.inject(this);
    trySetupToolbar(mToolbar);
    getSupportFragmentManager().beginTransaction().replace(R.id.base_fragment_container, getFragment()).commit();
  }

  abstract public Fragment getFragment();


  public void trySetupToolbar(Toolbar mToolbar) {
    try {
      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    } catch (NullPointerException e) {
      Log.e(getClass().getSimpleName(), "toolbar is null!");
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home){
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.swipeback_stack_to_front, R.anim.swipeback_stack_right_out);
  }

}
