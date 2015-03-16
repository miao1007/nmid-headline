package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebSettings;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.support.pref.WebViewPref;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.NetworkUtils;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Useful @Link:http://developer.android.com/training/animation/crossfade.html
 */
public class DetailedActivity extends ActionBarActivity {

  static final String MIME_TYPE = "text/html";
  static final String ENCODING = "utf-8";
  String url;

  @InjectView(R.id.detailed_toolbar) Toolbar mToolbar;
  @InjectView(R.id.detailed_webview) ObservableWebView mWebView;
  @InjectView(R.id.detailed_multiple_actions) FloatingActionsMenu mFloatingActionsMenu;
  @InjectView(R.id.detailed_progressbar) ViewStub mViewStub;
  @InjectView(R.id.detailed_action_favorite) FloatingActionButton mFloatingActionButton;

  private String TAG = LogUtils.makeLogTag(DetailedActivity.class);
  /**
   * Intent extra uesd for ShareSDK
   */
  private int idMember;
  private int category;
  private String title;
  private String excerpt;

  @OnClick(R.id.detailed_action_share) void detailed_action_share() {
    mFloatingActionsMenu.toggle();

    dispatchOneKeyShare();
  }

  @OnClick(R.id.detailed_action_favorite) void detailed_action_favorite(View v) {
    //Feed feed = Select.from(Feed.class).where(Condition.prop("idMember").eq(idMember)).first();
    //
    //Log.d(TAG, feed.isCollect() + "");
    //feed.setCollect(!feed.isCollect());
    //feed.save();
    //Log.d(TAG, feed.isCollect() + "");
    //mFloatingActionButton.setColorNormalResId(
    //    feed.isCollect() ? R.color.holo_red_dark : R.color.icons);
    //mFloatingActionsMenu.toggle();
  }

  @OnClick(R.id.detailed_action_settings) void detailed_action_settings() {
    startActivity(new Intent(this, SettingsActivity.class));
  }

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    // Init the swipe back
    SwipeBack.attach(this, Position.LEFT)
        .setContentView(R.layout.activity_detailed)
        .setSwipeBackView(R.layout.swipeback_default);

    setContentView(R.layout.activity_detailed);
    ButterKnife.inject(this);
    tryGetIntent();
    trySetupFAB();
    trySetupWebview();
    trySetupToolbar();
  }

  private void trySetupFAB() {
    //Log.d(TAG, feed.isCollect() + "");
    //mFloatingActionButton.setColorNormalResId(
    //    feed.isCollect() ? R.color.holo_red_dark : R.color.icons);
  }

  private void trySetupToolbar() {
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (ThemePref.isNightMode(this)) {
      mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    }
  }

  private void tryGetIntent() {
    Feed feed = getIntent().getExtras().getParcelable("key");

    idMember = feed.getIdMember();
    category = feed.getCategory();
    title = feed.getTitle();
    excerpt = feed.getSimpleContent();
  }

  private void trySetupWebview() {

    mWebView.setPadding(0, mToolbar.getHeight(), 0, 0);

    url = HeadlineService.END_POINT
        + "/api/android/newscontent?id="
        + idMember
        + "&category="
        + category;

    WebSettings settings = mWebView.getSettings();

    settings.setTextZoom(WebViewPref.getWebViewTextZoom(this));
    switch (WebViewPref.isAutoLoadImages(this)) {
      case 0:
        settings.setLoadsImagesAutomatically(false);
        break;
      case 1:

        break;
      case 2:
        if (!NetworkUtils.isWifiAviliable(this)) {
          settings.setLoadsImagesAutomatically(false);
        }
        break;
    }

    mWebView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
      //正在滚动时，包括drag滚动和惯性滚动
      @Override public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

      }

      //当按下touch
      @Override public void onDownMotionEvent() {
        ///Log.i(TAG, "onDownMotionEvent");

      }

      //当松开touch后，计算相对y
      @Override public void onUpOrCancelMotionEvent(ScrollState scrollState) {

      }
    });

    //new WebContentGetTask(new WebContentGetTaskCallback() {
    //  @Override public void onPreExcute() {
    //    mViewStub.inflate();
    //  }
    //
    //  @Override public void onSuccess(Object o) {
    //    mViewStub.setVisibility(View.GONE);
    //    String htmlData;
    //
    //  }
    //}).execute(url);
    RestAdapter adapter = new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT).setLogLevel(
        RestAdapter.LogLevel.FULL).build();
    adapter.create(HeadlineService.class).getNewsContent(category, idMember, new Callback<Void>() {
      @Override public void success(Void aVoid, Response response) {
        String o = response.getBody().toString();
        String htmlData;
        if (ThemePref.isNightMode(DetailedActivity.this)) {
          // Webview will use asserts/style_night.css
          htmlData =
              "<link rel=\"stylesheet\" type=\"text/css\" href=\"style_night.css\" /> <body class= \"gloable\"> "
                  + o
                  + "</body>";
        } else {
          // Webview will use asserts/style.css
          htmlData =
              "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" /> <body class= \"gloable\"> "
                  + o
                  + "</body>";
        }

        mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, MIME_TYPE, ENCODING, null);
      }

      @Override public void failure(RetrofitError error) {

      }
    });
  }

  @Override public void onBackPressed() {
    overridePendingTransition(R.anim.swipeback_stack_to_front, R.anim.swipeback_stack_right_out);

    if (mFloatingActionsMenu.isExpanded()) {
      mFloatingActionsMenu.collapse();
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_MENU) {
      mFloatingActionsMenu.toggle();
    }
    return super.onKeyUp(keyCode, event);
  }

  private void dispatchOneKeyShare() {
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字
    oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(title);
    // text是分享文本，所有平台都需要这个字段
    oks.setText(excerpt);
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    oks.setComment("我在通信头条分享了文章");
    // site是分享此内容的网站名称，仅在QQ空间使用
    oks.setSite(getString(R.string.app_name));
    // url仅在微信（包括好友和朋友圈）中使用
    oks.setUrl(url);
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    oks.setSiteUrl(url);
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl(url);

    // 启动分享GUI
    oks.show(this);
  }
}
