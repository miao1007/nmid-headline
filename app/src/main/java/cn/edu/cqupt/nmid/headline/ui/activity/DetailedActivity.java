package cn.edu.cqupt.nmid.headline.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.ui.widget.ObseverWebView;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.NetworkUtils;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.socialization.Socialization;

/**
 *
 */
public class DetailedActivity extends ActionBarActivity {

    static final String MIME_TYPE = "text/html";
    static final String ENCODING = "utf-8";

    static final String END_POINT = "http://news.tsinghua.edu.cn";
    String rawWebData = "Hello World";
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


    /**
     * Activity views
     */

    @InjectView(R.id.detailed_webview)
    ObseverWebView mWebView;

    @InjectView(R.id.detailed_img_btn)
    ImageView mImageButton;


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

        mWebView.setWebViewClient(new DefalutWebViewClient());


        id = getIntent().getIntExtra("id", 980);
        rawWebData = getIntent().getStringExtra("content");


        if (NetworkUtils.isNetworkAvailable(this)) {

            String htmlData;
            if (ThemeUtils.isNightMode(this)) {
                // Webview will use asserts/style.css
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

}
