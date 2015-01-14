package cn.edu.cqupt.nmid.headline.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.ui.fragment.FeedFragment;

public class FavListActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    FeedFragment feedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        ButterKnife.inject(this);
        trySetupToolbar(mToolbar);
        feedFragment = FeedFragment.newFavInstance(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fav_list_container, feedFragment).commit();
    }

}
