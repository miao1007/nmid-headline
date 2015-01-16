package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.Constant;
import cn.edu.cqupt.nmid.headline.support.controller.Controller;
import cn.edu.cqupt.nmid.headline.support.task.WebContentGetTask;
import cn.edu.cqupt.nmid.headline.support.task.callback.WebContentGetTaskCallback;
import cn.edu.cqupt.nmid.headline.ui.widget.FloatingActionsMenuHidable;
import cn.edu.cqupt.nmid.headline.ui.widget.ObservableScrollViewCallbacks;
import cn.edu.cqupt.nmid.headline.ui.widget.ObservableWebView;
import cn.edu.cqupt.nmid.headline.ui.widget.ProgressBarCircular;
import cn.edu.cqupt.nmid.headline.ui.widget.ScrollState;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.NetworkUtils;
import cn.edu.cqupt.nmid.headline.utils.PreferenceUtils;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Useful @Link:http://developer.android.com/training/animation/crossfade.html
 */
public class DetailedActivity extends ActionBarActivity {


    static final String MIME_TYPE = "text/html";
    static final String ENCODING = "utf-8";

    private String TAG = LogUtils.makeLogTag(DetailedActivity.class);

    /**
     * Intent extra uesd for ShareSDK
     */
    private int id;
    private int category;
    private String title;
    private String excerpt;


    /**
     * Activity views
     */

    @InjectView(R.id.detailed_webview)
    ObservableWebView mWebView;

    @InjectView(R.id.detailed_multiple_actions)
    FloatingActionsMenuHidable mFloatingActionsMenu;

    private boolean isFabAnmation = false;


    @OnClick(R.id.detailed_action_share)
    void detailed_action_share() {
        mFloatingActionsMenu.toggle();
        OnekeyShare oks;
        ShareSDK.initSDK(this);
        oks = new OnekeyShare();
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
        //TODO
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

        oks.show(this);
    }


    @OnClick(R.id.detailed_action_favorite)
    void detailed_action_favorite(View v) {
        mController.likeIt(id, category, true);
    }

    @OnClick(R.id.detailed_action_settings)
    void detailed_action_settings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @InjectView(R.id.detailed_progressbar)
    ProgressBarCircular mProgressBarCircular;

    Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.inject(this);

        tryGetIntent();
        trySetupWebview();


        mController = new Controller(this);

    }

    private void tryGetIntent() {
        id = getIntent().getIntExtra("id", 1);
        category = getIntent().getIntExtra("category", Constant.TYPE_CLASSMATE);
        title = getIntent().getStringExtra("title");
        excerpt = getIntent().getStringExtra("excerpt");
    }

    private void trySetupWebview() {

        String url = Constant.ENDPOINT + "/txtt/public/api/android/newscontent?id=" + id + "&category=" + category;
        url = "http://bbs.pcbeta.com/";
        WebSettings settings = mWebView.getSettings();
        settings.setTextZoom(PreferenceUtils.getWebViewTextZoom(this));
        if (NetworkUtils.isWifiAviliable(this)) {

        }
        switch (PreferenceUtils.isAutoLoadImages(this)) {
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
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

            }

            //当按下touch
            @Override
            public void onDownMotionEvent() {
                ///Log.i(TAG, "onDownMotionEvent");

            }

            //当松开touch后，计算相对y
            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                switch (scrollState) {
                    case STOP:
                        Log.i(TAG, "stop");
                        mFloatingActionsMenu.show(true);
                        break;
                    case UP:
                        Log.i(TAG, "UP");
                        mFloatingActionsMenu.show(false);
                        break;
                    case DOWN:
                        Log.i(TAG, "DOWN");
                        mFloatingActionsMenu.show(true);
                }

            }
        });


        new WebContentGetTask(this,new WebContentGetTaskCallback() {
            @Override
            public void onPreExcute() {
                mProgressBarCircular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG,o.toString());
                mProgressBarCircular.setVisibility(View.GONE);
                String htmlData;
                if (ThemeUtils.isNightMode(DetailedActivity.this)) {
                    // Webview will use asserts/style_night.css
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style_night.css\" /> <body class= \"gloable\"> "
                            + o + "</body>";
                } else {
                    // Webview will use asserts/style.css
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" /> <body class= \"gloable\"> "
                            + o + "</body>";
                }

                //mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, MIME_TYPE, ENCODING, null);
                mWebView.loadDataWithBaseURL("file:///android_asset/", o.toString(), MIME_TYPE, ENCODING, null);
            }
        }).execute(url);

    }



    @Override
    public void onBackPressed() {
        if (mFloatingActionsMenu.isExpanded()) {
            mFloatingActionsMenu.collapse();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            mFloatingActionsMenu.toggle();
        }
        return super.onKeyUp(keyCode, event);
    }
}