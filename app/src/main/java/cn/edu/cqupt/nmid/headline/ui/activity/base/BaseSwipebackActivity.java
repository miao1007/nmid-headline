package cn.edu.cqupt.nmid.headline.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import cn.edu.cqupt.nmid.headline.R;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

/**
 * Created by leon on 3/13/15.
 */
public class BaseSwipebackActivity extends ActionBarActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SwipeBack.attach(this, Position.LEFT)
        .setContentView(R.layout.activity_base_settings)
        .setSwipeBackView(R.layout.swipeback_default);
    setContentView(R.layout.activity_base_settings);
  }



}
