package cn.edu.cqupt.nmid.headline.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by leon on 12/17/14.
 */
public class ObseverWebView extends WebView {

    ScrollListener mScrollListener;

    interface ScrollListener{
        void onMoving();
    }


    public void setmScrollListener(ScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }


    public ObseverWebView(Context context) {
        super(context);
    }

    public ObseverWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObseverWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @param l Current horizontal scroll origin.
     * @param t Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     */

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null){
            mScrollListener.onMoving();
        }
    }


}
