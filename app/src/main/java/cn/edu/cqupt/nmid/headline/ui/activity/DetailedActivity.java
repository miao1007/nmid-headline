package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.ui.widget.FloatingActionsMenuHidable;
import cn.edu.cqupt.nmid.headline.ui.widget.ObservableScrollViewCallbacks;
import cn.edu.cqupt.nmid.headline.ui.widget.ObservableWebView;
import cn.edu.cqupt.nmid.headline.ui.widget.ProgressBarCircular;
import cn.edu.cqupt.nmid.headline.ui.widget.ScrollState;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.socialization.Socialization;

/**
 * Useful @Link:http://developer.android.com/training/animation/crossfade.html
 */
public class DetailedActivity extends ActionBarActivity {


    static final String MIME_TYPE = "text/html";
    static final String ENCODING = "utf-8";

    String rawWebData = "Hello World悬浮颗粒的自组装是一种广泛观察到的现象，在生物、制药、制备等方面有广泛的应用。理想情况下，相同的浮动球体或圆柱体筏应聚集成一种密排的方式（图1中的a和b）。然而，实际观察到的组装筏，却呈现大的各种形式的缺陷（图1中的c）。对于这样一个具有普遍性的矛盾，人们似乎一直没有认真思考并作出合理解释。悬浮颗粒的自组装是一种广泛观察到的现象，在生物、制药、制备等方面有广泛的应用。理想情况下，相同的浮动球体或圆柱体筏应聚集成一种密排的方式（图1中的a和b）。然而，实际观察到的组装筏，却呈现大的各种形式的缺陷（图1中的c）。对于这样一个具有普遍性的矛盾，人们似乎一直没有认真思考并作出合理解释。悬浮颗粒的自组装是一种广泛观察到的现象，在生物、制药、制备等方面有广泛的应用。理想情况下，相同的浮动球体或圆柱体筏应聚集成一种密排的方式（图1中的a和b）。然而，实际观察到的组装筏，却呈现大的各种形式的缺陷（图1中的c）。对于这样一个具有普遍性的矛盾，人们似乎一直没有认真思考并作出合理解悬浮颗粒的自组装是一种广泛观察到的现象，在生物、制药、制备等方面有广泛的应用。理想情况下，相同的浮动球体或圆柱体筏应聚集成一种密排的方式（图1中的a和b）。然而，实际观察到的组装筏，却呈现大的各种形式的缺陷（图1中的c）。对于这样一个具有普遍性的矛盾，人们似乎一直没有认真思考并作出合理解释。悬浮颗粒的自组装是一种广泛观察到的现象，在生物、制药、制备等方面有广泛的应用。理想情况下，相同的浮动球体或圆柱体筏应聚集成一种密排的方式（图1中的a和b）。然而，实际观察到的组装筏，却呈现大的各种形式的缺陷（图1中的c）。对于这样一个具有普遍性的矛盾，人们似乎一直没有认真思考并作出合理解释。释。";
    private String TAG = LogUtils.makeLogTag(DetailedActivity.class);

    /**
     * Intent extra uesd for ShareSDK
     */
    private int id;
    private String title = "title";
    private String date = "date";
    private String author = "leon";

    OnekeyShare oks;
    Socialization socialization;

    private static final float SCROLLING_ALPHA = 33333;


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
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @InjectView(R.id.detailed_progressbar)
    ProgressBarCircular mProgressBarCircular;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
        ShareSDK.registerService(Socialization.class);
        socialization = ShareSDK.getService(Socialization.class);
        oks = new OnekeyShare();
        setContentView(R.layout.activity_detailed);
        ButterKnife.inject(this);

        mWebView.setWebViewClient(new DefalutWebViewClient());
        mWebView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            //正在滚动时，包括drag滚动和惯性滚动
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                // Log.i(TAG, "onScrollChanged" + String.valueOf(scrollY) + "/" + String.valueOf(dragging));

//                if (scrollY < 0) {
//                    mFloatingActionsMenu.hide();
//                } else {
//                    mFloatingActionsMenu.show();
//                }

                //mFloatingActionsMenu.animate().translationX(-100).setDuration(200).start();
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


        id = getIntent().getIntExtra("id", 980);
        if (getIntent().getStringExtra("content") != null) {
            rawWebData = getIntent().getStringExtra("content");
        }


        String htmlData;
        if (ThemeUtils.isNightMode(this)) {
            // Webview will use asserts/style_night.css
            htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style_night.css\" /> <body class= \"gloable\"> "
                    + rawWebData + "</body>";
        } else {
            // Webview will use asserts/style.css
            htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" /> <body class= \"gloable\"> "
                    + rawWebData + "</body>";
        }

        mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, MIME_TYPE, ENCODING, null);



    }


    private class DefalutWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            //do nothing
//            return true;
//        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            System.out.println("onPageStarted = " + view);
            mProgressBarCircular.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("onPageFinished = " + view);
            mProgressBarCircular.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }
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
