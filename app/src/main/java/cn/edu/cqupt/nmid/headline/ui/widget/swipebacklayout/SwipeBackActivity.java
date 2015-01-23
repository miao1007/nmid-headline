package cn.edu.cqupt.nmid.headline.ui.widget.swipebacklayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class SwipeBackActivity extends ActionBarActivity implements SwipeBackActivityBase {
  private SwipeBackActivityHelper mHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mHelper = new SwipeBackActivityHelper(this);
    mHelper.onActivityCreate();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mHelper.onPostCreate();
  }

  @Override
  public View findViewById(int id) {
    View v = super.findViewById(id);
    if (v == null && mHelper != null) return mHelper.findViewById(id);
    return v;
  }

  @Override
  public SwipeBackLayout getSwipeBackLayout() {
    return mHelper.getSwipeBackLayout();
  }

  @Override
  public void setSwipeBackEnable(boolean enable) {
    getSwipeBackLayout().setEnableGesture(enable);
  }

  @Override
  public void scrollToFinishActivity() {
    SwipeUtils.convertActivityToTranslucent(this);
    getSwipeBackLayout().scrollToFinishActivity();
  }


  public void trySetupToolbar(Toolbar mToolbar){
    try {
      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } catch (NullPointerException e){
      Log.e(getClass().getSimpleName(), "toolbar is null!");
    }
  }
}
