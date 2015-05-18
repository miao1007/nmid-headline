package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cn.edu.cqupt.nmid.headline.support.task.WebContentGetTask;
import cn.edu.cqupt.nmid.headline.support.task.callback.WebContentGetTaskCallback;
import cn.edu.cqupt.nmid.headline.ui.activity.SettingsActivity;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.NetworkUtils;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

/**
 * Created by leon on 15/5/17.
 */
public class WebViewFragment extends Fragment {
  static final String MIME_TYPE = "text/html";
  static final String ENCODING = "utf-8";
  public static final String PARCELABLE_KEY = "key";
  String url;

  @InjectView(R.id.detailed_webview) ObservableWebView mWebView;
  @InjectView(R.id.detailed_multiple_actions) FloatingActionsMenu mFloatingActionsMenu;
  @InjectView(R.id.detailed_progressbar) ViewStub mViewStub;
  @InjectView(R.id.detailed_action_favorite) FloatingActionButton mFloatingActionButton;

  private String TAG = LogUtils.makeLogTag(WebViewFragment.class);
  /**
   * Intent extra uesd for ShareSDK
   */
  private Feed feed;
  private int idMember;
  private int category;
  private String title;
  private String excerpt;
  private boolean isLike = false;

  public WebViewFragment() {
  }

  public static WebViewFragment newInstance(Bundle bundle) {
    WebViewFragment fragment = new WebViewFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  @OnClick(R.id.detailed_action_share) void detailed_action_share() {
    mFloatingActionsMenu.toggle();

    dispatchOneKeyShare();
  }

  @OnClick(R.id.detailed_action_favorite) void detailed_action_favorite(View v) {

    trySetupFAB(!isLike);
    isLike = !isLike;
    mFloatingActionsMenu.toggle();
    Log.d(TAG, "now" + feed.isCollect());
  }

  @OnClick(R.id.detailed_action_settings) void detailed_action_settings() {
    startActivity(new Intent(getActivity(), SettingsActivity.class));
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.activity_detailed, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    tryGetIntent();
    trySetupFAB(isLike);
    trySetupWebview();
  }

  private void trySetupFAB(boolean isLike) {

    mFloatingActionButton.setIcon(
        isLike ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
  }

  private void tryGetIntent() {
    feed = getArguments().getParcelable(PARCELABLE_KEY);

    idMember = feed.getIdmember();
    category = feed.getCategory();
    title = feed.getTitle();
    excerpt = feed.getSimple_content();
    isLike = feed.isCollect();
    Log.d(TAG, "id" + idMember + "title" + title);
  }

  private void trySetupWebview() {

    //http://202.202.43.205:8086/api/android/newscontent?category=1&id=194
    url = HeadlineService.END_POINT
        + "/api/android/newscontent?id="
        + idMember
        + "&category="
        + category;

    WebSettings settings = mWebView.getSettings();

    settings.setTextZoom(WebViewPref.getWebViewTextZoom(getActivity()));
    switch (WebViewPref.isAutoLoadImages(getActivity())) {
      case 0:
        settings.setLoadsImagesAutomatically(false);
        break;
      case 1:

        break;
      case 2:
        if (!NetworkUtils.isWifiAviliable(getActivity())) {
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

    new WebContentGetTask(new WebContentGetTaskCallback() {
      @Override public void onPreExcute() {

      }

      @Override public void onSuccess(Object o) {
        String htmlData;
        if (ThemePref.isNightMode(getActivity())) {
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
    }).execute(url);
  }

  //@Override public void onBackPressed() {
  //  overridePendingTransition(R.anim.swipeback_stack_to_front, R.anim.swipeback_stack_right_out);
  //
  //  if (mFloatingActionsMenu.isExpanded()) {
  //    mFloatingActionsMenu.collapse();
  //  } else {
  //    super.onBackPressed();
  //  }
  //}

  //@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
  //  if (keyCode == KeyEvent.KEYCODE_MENU) {
  //    mFloatingActionsMenu.toggle();
  //  }
  //  return super.onKeyUp(keyCode, event);
  //}

  private void dispatchOneKeyShare() {
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字
    //oks.set(R.drawable.ic_launcher, getString(R.string.app_name));
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
    oks.show(getActivity());
  }
}
