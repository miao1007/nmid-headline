package cn.edu.cqupt.nmid.headline.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.ui.widget.FloatingActionsMenuHidable;
import cn.edu.cqupt.nmid.headline.ui.widget.ObservableScrollViewCallbacks;
import cn.edu.cqupt.nmid.headline.ui.widget.ObservableWebView;
import cn.edu.cqupt.nmid.headline.ui.widget.ScrollState;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.NetworkUtils;
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


//    @OnClick(R.id.menu_share)
//    void menu_share() {
//        CommentListPage page = new CommentListPage();
//        page.setTopic(String.valueOf(id), title, date, author);
//        page.setOnekeyShare(oks);
//        page.show(DetailedActivity.this, null);
//    }
//
//    @OnClick(R.id.menu_like)
//    void menu_like(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                socialization.likeTopic(String.valueOf(id), title);
//            }
//        }).start();
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
        ShareSDK.registerService(Socialization.class);
        socialization = ShareSDK.getService(Socialization.class);
        oks = new OnekeyShare();
        setContentView(R.layout.activity_detailed);
        ButterKnife.inject(this);
        mFloatingActionsMenu.expand();

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


        if (NetworkUtils.isNetworkAvailable(this)) {

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


        } else {

        }


    }


    private class DefalutWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //do nothing
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            System.out.println("onLoadResource = " + view);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            System.out.println("onPageStarted = " + view);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            System.out.println("onPageFinished = " + view);
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
