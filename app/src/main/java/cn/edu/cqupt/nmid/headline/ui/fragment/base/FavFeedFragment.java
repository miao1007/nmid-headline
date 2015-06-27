package cn.edu.cqupt.nmid.headline.ui.fragment.base;

import android.view.View;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Feed;
import com.activeandroid.query.Select;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 15/5/20.
 * TODO: 解藕
 */
public class FavFeedFragment extends NewsFeedFragment {

  @Override void loadNewFeeds() {
    List<Feed> feeds = new ArrayList<>();
    feeds = new Select().from(Feed.class)
        //.where("isLike=?", true)
        .orderBy("idMember desc")
        .limit(this.feed_limit)
        .execute();
    if (feeds == null || feeds.isEmpty()) {
      showErrorView(View.VISIBLE);
    } else {
      newsBeans.addAll(feeds);
      adapter.notifyDataSetChanged();
    }
    mSwipeRefreshLayout.setRefreshing(false);
  }
}
